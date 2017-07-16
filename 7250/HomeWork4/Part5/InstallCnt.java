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

public class InstallCnt {
	
	public static class MyMapper extends Mapper<Object, Text, Text, IntWritable> {
		private static final IntWritable one = new IntWritable(1);
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer lines = new StringTokenizer(value.toString());
			while (lines.hasMoreTokens()) {
				String line = lines.nextToken();
				if (line.equals("removed")) context.write(new Text("Removed"), one);
				if (line.equals("Duplicated")) context.write(new Text("Duplicated"), one);
				if (line.equals("Rewrote")) context.write(new Text("Rewrote"), one);
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
		Job job = Job.getInstance(conf, "installCnt");
		job.setJarByClass(InstallCnt.class);
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
