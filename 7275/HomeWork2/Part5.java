package neu.edu;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

public class Part5 {

	public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable>{
		private Text word = new Text();
		private final static IntWritable one = new IntWritable(1);
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer lines = new StringTokenizer(value.toString(), "\n");
			while (lines.hasMoreTokens()) {
				StringTokenizer line = new StringTokenizer(lines.nextToken());
				while (line.hasMoreTokens()) {
					word.set(line.nextToken());
					context.write(word, one);
					break;
				}
			}
		}
	}
	
	public static class SumReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
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
	
	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "part5");
		job.setJarByClass(Part5.class);
	    job.setMapperClass(TokenizerMapper.class);
	    job.setCombinerClass(SumReducer.class);
	    job.setReducerClass(SumReducer.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(IntWritable.class);
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
	
}

