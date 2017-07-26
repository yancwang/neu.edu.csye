package naivebayes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
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

public class NaiveBayesBuildClassifier {

	public static class NaiveBayesClassifierMapper extends Mapper<Object, Text, Text, IntWritable>{
		
		private Text classifier = new Text();
		private IntWritable one = new IntWritable(1);
		private List<String> list = new ArrayList<String>();
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer lines = new StringTokenizer(value.toString(), "\n");
			
			while (lines.hasMoreTokens()) {
				StringTokenizer line = new StringTokenizer(lines.nextToken(), ",");
				int count = 0;
				while (line.hasMoreTokens()) {
					count++;
					String s = line.nextToken();
					if (s.equals("Brokerage")) break;
					if (count == 1) list.add(s);
					if (count == 3) list.add(s);
					if (count == 5) list.add(s);
					if (!line.hasMoreTokens()) {
						Iterator<String> itr = list.iterator();
						while (itr.hasNext()) {
							String word = itr.next() + "," + s;
							if (word.contains("/") || word.contains("\"")) continue;
							classifier.set(word);
							context.write(classifier, one);
						}
						String word = "CLASS," + s;
						classifier.set(word);
						context.write(classifier, one);
						list.clear();
					}
				}				
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
	    Job job = Job.getInstance(conf, "NaiveBayesClassifierBuilder");
	    
	    Path input = new Path(args[0]);
		Path output = new Path(args[1]);
		FileSystem fs = FileSystem.get(conf);
		if (fs.exists(output)) fs.delete(output, true);
		
		job.setJarByClass(NaiveBayesBuildClassifier.class);
		job.setMapperClass(NaiveBayesClassifierMapper.class);
		job.setCombinerClass(NaiveBayesClassifierReducer.class);
		job.setReducerClass(NaiveBayesClassifierReducer.class);
		
		job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(IntWritable.class);
	    FileInputFormat.addInputPath(job, input);
	    FileOutputFormat.setOutputPath(job, output);
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}