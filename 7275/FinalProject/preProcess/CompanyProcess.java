package preprocess;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CompanyProcess {
	
	public static class PreProcessMapper extends Mapper<Object, Text, Text, IntWritable>{
		
		private Text compName = new Text();
		private final static IntWritable one = new IntWritable(1);
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer lines = new StringTokenizer(value.toString(), "\t\r\n");
			while (lines.hasMoreTokens()) {
				String line = lines.nextToken();
	    		StringTokenizer column = new StringTokenizer(line, ",");
	    		int count = 0;
	    		while(column.hasMoreTokens()) {
	    			count++;
	    			String s = column.nextToken();
	    			if (s.equals("Brokerage")) break;
	    			if (count == 4) {
	    				if (s.equals("Upgrades")) s = column.nextToken();
	    				compName.set(s);
	    				context.write(compName, one);
	    			}
	    		}
			}
		}
	}
	
	public static class SumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		private final static IntWritable result = new IntWritable();
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
		    context.write(key, result);
		}
	}
	
	private static<K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
		 Comparator<K> valueComparator =  new Comparator<K>() {
			 public int compare(K k1, K k2) {
				 int compare = map.get(k2).compareTo(map.get(k1));
				 if (compare == 0) return 1;
				 else return compare;
			 }
		 };
		 Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
		 sortedByValues.putAll(map);
		 return sortedByValues;
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
	    Job job = Job.getInstance(conf, "preprocess");
	    
	    Path input = new Path(args[0]);
		Path output = new Path(args[1]);
		FileSystem fs = FileSystem.get(conf);
		if (fs.exists(output)) fs.delete(output, true);
		
	    job.setJarByClass(CompanyProcess.class);
	    job.setMapperClass(PreProcessMapper.class);
	    job.setCombinerClass(SumReducer.class);
	    job.setReducerClass(SumReducer.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(IntWritable.class);
	    
	    FileInputFormat.addInputPath(job, input);
	    FileOutputFormat.setOutputPath(job, output);
	    job.waitForCompletion(true);
	    
	    Path out = new Path(args[1]+"/part-r-00000");
	    FSDataInputStream inputStream = fs.open(out);
	    
	    HashMap<String, Integer> hm = new HashMap<String, Integer>();
	    String line = null;
	    while((line = inputStream.readLine()) != null){
	    	String[] strs = line.split("(\t)+");
	    	hm.put(strs[0], Integer.parseInt(strs[1]));
	    }
	    
	    FSDataOutputStream outputStream = fs.create(new Path(args[2]));
	    
	    TreeMap<String, Integer> t = (TreeMap<String, Integer>) sortByValues(hm);
	    Set<String> set = t.keySet();
	    	    
	    Iterator<String> itr = set.iterator();
	    int count = 0;
	    while(itr.hasNext() && count < 20){
	    	String company = itr.next();
	    	Integer amount = (Integer)hm.get(company);
	    	outputStream.writeUTF(company + "\t" + amount);
	    	outputStream.writeUTF("\n");
	    	count++;
	    	System.out.println(company + "\t" + amount);
	    }	    
	    
	    outputStream.close();
	}

}