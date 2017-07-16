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

public class AccessCnt {
	
	public static class MyMapper extends Mapper<Object, Text, Text, IntWritable> {
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer lines = new StringTokenizer(value.toString(), "\n\r");
			while (lines.hasMoreTokens()) {
				String line = lines.nextToken();
				String[] l = line.split(" - - ");
				context.write(new Text(l[0]), new IntWritable(1));
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
		Job job = Job.getInstance(conf, "accessCnt");
		job.setJarByClass(AccessCnt.class);
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
