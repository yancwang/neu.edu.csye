package neu.edu;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

public class Part2 {
	
	public static class TokenizerMapper extends Mapper<Object, Text, Text, DoubleWritable>{
		private Text word = new Text();
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer lines = new StringTokenizer(value.toString());
			while (lines.hasMoreTokens()) {
				StringTokenizer line = new StringTokenizer(lines.nextToken(), "::");
				int count = 0;
				while (line.hasMoreTokens()) {
					count++;
					String s = line.nextToken();
					if (count == 2) word.set(s);
					if (count == 3) {
						DoubleWritable rating = new DoubleWritable(Double.parseDouble(s));
	        			context.write(word, rating);
					}
				}
			}
		}
	}
	
	public static class AverageReducer extends Reducer<Text,DoubleWritable,Text,DoubleWritable> {
		private DoubleWritable result = new DoubleWritable();
		private HashMap<String, Double> hm = new HashMap<String, Double>();
		
		public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
			double sum = 0; 
			int count = 0;
			for (DoubleWritable val : values) {
				sum += val.get();
				count++;
			}
			result.set(sum/count);
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

	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		Configuration conf = new Configuration();
	    Job job = Job.getInstance(conf, "part2");
	    
	    Path outputPath = new Path(args[1]);
	    
	    
	    job.setJarByClass(Part2.class);
	    job.setMapperClass(TokenizerMapper.class);
	    job.setCombinerClass(AverageReducer.class);
	    job.setReducerClass(AverageReducer.class);
	    
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(DoubleWritable.class);
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, outputPath);
	    job.waitForCompletion(true);
	    Path out = new Path(args[1]+"/part-r-00000");
	    FileSystem fs = FileSystem.get(conf);
	    FSDataInputStream inputStream = fs.open(out);
	    
	    HashMap<String, Double> hm = new HashMap<String, Double>();
	    String line = null;
	    
	    while((line = inputStream.readLine()) != null){
	    	String[] strs = line.split("(\t)+");
	    	hm.put(strs[0], Double.parseDouble(strs[1]));
	    }
	    TreeMap<String, Double> t = (TreeMap<String, Double>) sortByValues(hm);
	    Set<String> set = t.keySet();
	    FSDataOutputStream outputStream = fs.create(new Path(args[2]));
	    
	    Iterator<String> itr = set.iterator();
	    int count = 1;
	    while(itr.hasNext() && count <= 25){
	    	String movie = itr.next();
	    	Double rating = (Double)hm.get(movie);
	    	outputStream.writeUTF(movie + "\t" + rating);
	    	outputStream.writeUTF("\n");
	    	count++;
	    	System.out.println(movie + "\t" + rating);
	    }	    
	    
	    outputStream.close();
	}

}

