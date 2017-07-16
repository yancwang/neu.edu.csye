package hadoop.part4;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Part4 {
	
	public static class MyMapper extends Mapper<Object, Text, Text, DoubleWritable> {
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer lines = new StringTokenizer(value.toString());
			while (lines.hasMoreTokens()) {
				String line = lines.nextToken();
				String[] ls = line.split(",");
				if (ls.length == 9) {
					if (!ls[0].equals("exchange")) {
						double price = Double.parseDouble(ls[4]);
						context.write(new Text(ls[1]), new DoubleWritable(price));
					}
				}
			}
		}
	}
	
	public static class MyReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
		public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
			int count = 0;
			double sum = 0;
			for (DoubleWritable val: values) {
				count++;
				sum += val.get();
			}
			context.write(key, new DoubleWritable(sum/count));
		}
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "price average calculate");
		job.setJarByClass(Part4.class);
		job.setMapperClass(MyMapper.class);
		job.setReducerClass(MyReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileSystem fs = FileSystem.get(conf);
		Path output = new Path(args[1]);
		if (fs.exists(output)) fs.delete(output, true);
		FileOutputFormat.setOutputPath(job, output);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
