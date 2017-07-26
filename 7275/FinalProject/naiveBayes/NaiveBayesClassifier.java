package naivebayes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

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
import org.apache.log4j.BasicConfigurator;

public class NaiveBayesClassifier {
	
	public static class NaiveBayesClassifierMapper extends Mapper<Object, Text, Text, IntWritable>{
		
		private Text ntext = new Text();
		private IntWritable one = new IntWritable(1);
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer lines = new StringTokenizer(value.toString(), "\n");
			
			while (lines.hasMoreTokens()) {
				String line = lines.nextToken();
				if (line.contains("Brokerage")) continue;
				ntext.set(line);
				context.write(ntext, one);
			}
		}
	}
	
	public static class NaiveBayesClassifierReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		
		private IntWritable result = new IntWritable();
		
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			int sum = 0;
		    for (IntWritable val : values) {
		    	sum += val.get();
		    }
		    result.set(sum);
		    context.write(key, result);
		}
		
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		BasicConfigurator.configure();
		Configuration conf = new Configuration();
	    Job job = Job.getInstance(conf, "NaiveBayesClassifier");
	    
	    Path input = new Path(args[0]);
		Path output = new Path(args[1]);
		FileSystem fs = FileSystem.get(conf);
		if (fs.exists(output)) fs.delete(output, true);
		
		job.setJarByClass(NaiveBayesClassifier.class);
		job.setMapperClass(NaiveBayesClassifierMapper.class);
		job.setCombinerClass(NaiveBayesClassifierReducer.class);
		job.setReducerClass(NaiveBayesClassifierReducer.class);
		job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(IntWritable.class);
	    
	    FileInputFormat.addInputPath(job, input);
	    FileOutputFormat.setOutputPath(job, output);
//	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	    job.waitForCompletion(true);
	    
		FSDataInputStream inputStream = fs.open(new Path(args[2]+"/part-r-00000"));
	    HashMap<String, Integer> hm = new HashMap<String, Integer>();
	    String line = null;
	    while((line = inputStream.readLine()) != null){
	    	String[] strs = line.split("(\t)+");
	    	hm.put(strs[0], Integer.parseInt(strs[1]));
	    }
	    
	    int buySum = hm.get("CLASS,Buy");
	    int holdSum = hm.get("CLASS,Hold");
	    int sellSum = hm.get("CLASS,Sell");
	    
	    HashMap<String, Double> h = new HashMap<String, Double>();
	    Set<String> set = hm.keySet();
	    Iterator<String> itr = set.iterator();
	    
	    while(itr.hasNext()) {
	    	String k = itr.next();
	    	String[] strs = k.split(",");
	    	if (strs[1].equals("Buy")) {
	    		double d = (double)hm.get(k) / buySum;
	    		h.put(k, d);
	    	} 
	    	else if (strs[1].equals("Hold")) {
	    		double d = (double)hm.get(k) / holdSum;
	    		h.put(k, d);
	    	} else {
	    		double d = (double)hm.get(k) / sellSum;
	    		h.put(k, d);
	    	}
	    }
	    
	    inputStream = fs.open(new Path(args[1]+"/part-r-00000"));
	    
	    FSDataOutputStream outputStream = fs.create(new Path(args[3]));
	    PrintWriter writer = new PrintWriter("compare.txt", "UTF-8");
	    
	    line = null;
	    while((line = inputStream.readLine()) != null){
	    	String[] strs = line.split(",");
	    	if (strs.length < 5) continue;
	    	if (strs[0].equals("Brokerage")) continue;
	    	double buyRate = 0;
	    	for (int i = 0; i < 6; i = i + 2) {
	    		String k = strs[i] + "," + "Buy";
	    		if (h.get(k) == null) continue;
	    		if (buyRate == 0) buyRate = buyRate + h.get(k);
	    		else buyRate = buyRate * h.get(k);
	    	}
	    	double holdRate = 1;
	    	for (int i = 0; i < 6; i = i + 2) {
	    		String k = strs[i] + "," + "Hold";
	    		if (h.get(k) == null) continue;
	    		if (holdRate == 0) holdRate = holdRate + h.get(k);
	    		else holdRate = holdRate * h.get(k);
	    	}
	    	double sellRate = 1;
	    	for (int i = 0; i < 6; i = i + 2) {
	    		String k = strs[i] + "," + "Sell";
	    		if (h.get(k) == null) continue;
	    		if (sellRate == 0) sellRate = sellRate + h.get(k);
	    		else sellRate = sellRate * h.get(k);
	    	}
	    	String r = getResult(buyRate, holdRate, sellRate);
	    	String[] s = line.split("(\t)+");
	    	writer.println(s[0] + "," + r);
//	    	System.out.println(r);
	    	outputStream.writeUTF(strs[0] + "," + strs[1] + "," + strs[2] + "\t" + r);
	    	outputStream.writeUTF("\n");
	    }
	    writer.close();
	    outputStream.close();
	}
	
	private static String getResult(double d1, double d2, double d3) {
		if (d1 > d2) {
			if (d1 > d3) return "Buy";
			else return "Sell";
		} else {
			if (d2 > d3) return "Hold";
			else return "Sell";
		}
	}
}