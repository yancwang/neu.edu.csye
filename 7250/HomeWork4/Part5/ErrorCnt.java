package hadoop.part5;

import java.io.IOException;
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

public class ErrorCnt {
	
	public static class MyMapper extends Mapper<Object, Text, Text, IntWritable> {
		private static final IntWritable one = new IntWritable(1);
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer lines = new StringTokenizer(value.toString());
			while (lines.hasMoreTokens()) {
				String line = lines.nextToken();
				if (line.equals("[notice]")) context.write(new Text("notice"), one);
				if (line.equals("[error]")) context.write(new Text("error"), one);
				if (line.equals("[warn]")) context.write(new Text("warn"), one);
			}
		}
	}
	
	public static class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			int count = 0;
			for (IntWritable val: values) {
				count += val.get();
			}
			context.write(key, new IntWritable(count));
		}
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "errorCnt");
		job.setJarByClass(ErrorCnt.class);
		job.setMapperClass(MyMapper.class);
		job.setReducerClass(MyReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileSystem fs = FileSystem.get(conf);
		Path output = new Path(args[1]);
		if (fs.exists(output)) fs.delete(output, true);
		FileOutputFormat.setOutputPath(job, output);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
