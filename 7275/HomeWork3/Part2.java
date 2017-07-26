package neu.edu.csye;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.BasicConfigurator;

public class Part2 extends Configured implements Tool {
	
	private static final String OUTPUT_PATH = "intermediate_output";
	
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
	
	public static class SortMapper extends Mapper<Object, Text, Text, DoubleWritable> {
		private Text word = new Text();
		private HashMap<String, Double> hm = new HashMap<String, Double>();
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString());
			while (itr.hasMoreTokens()) {
				String l = itr.nextToken(); 
				Double v = Double.parseDouble(itr.nextToken());
				hm.put(l, v);
			}
			
			List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(hm.entrySet());
			Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
				public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
					return (o2.getValue()).compareTo(o1.getValue());
	            }
	        });
			Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
			int i = 0;
	        for (Map.Entry<String, Double> entry : list) {
	        	if (i++ >= 25) break;
	        	sortedMap.put(entry.getKey(), entry.getValue());
	        }
	        for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
				word.set(entry.getKey());
				DoubleWritable rating = new DoubleWritable(entry.getValue());
				context.write(word, rating);
	        }
		}     
	}
	
	public static class SortReducer extends Reducer<Text,DoubleWritable,Text,DoubleWritable> {
		private DoubleWritable result = new DoubleWritable();
		
		public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
			double value = 0; 
			for (DoubleWritable val : values) {
				value = val.get();
			}
			result.set(value);
			context.write(key, result);
		}
	}
	
	public int run(String[] args) throws Exception {
		BasicConfigurator.configure();
		Configuration conf = new Configuration();
		FileSystem.getLocal(conf).delete(new Path(args[1]), true);
		FileSystem.getLocal(conf).delete(new Path(OUTPUT_PATH), true);
		
		// job 1
	    Job job1 = Job.getInstance(conf, "part2");
		job1.setJarByClass(Part2.class);
		job1.setMapperClass(TokenizerMapper.class);
	    job1.setCombinerClass(AverageReducer.class);
	    job1.setReducerClass(AverageReducer.class);
	    job1.setOutputKeyClass(Text.class);
	    job1.setOutputValueClass(DoubleWritable.class);
	    FileInputFormat.addInputPath(job1, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job1, new Path(OUTPUT_PATH));
	    job1.waitForCompletion(true);
	    
	    // job 2
	    Job job2 = Job.getInstance(conf, "part2");
	    job2.setJarByClass(Part2.class);
	    job2.setMapperClass(SortMapper.class);
	    job2.setCombinerClass(SortReducer.class);
	    job2.setReducerClass(SortReducer.class);
	    job2.setOutputKeyClass(Text.class);
	    job2.setOutputValueClass(DoubleWritable.class);
	    FileInputFormat.addInputPath(job2, new Path(OUTPUT_PATH));
	    FileOutputFormat.setOutputPath(job2, new Path(args[1]));
		return job2.waitForCompletion(true) ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Enter valid number of arguments <Inputdirectory>  <Outputlocation>");
			System.exit(0);
		}
		ToolRunner.run(new Configuration(), new Part2(), args);
	}

}
