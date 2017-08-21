package neu.edu.analysis;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class AverageSum {
	
	public static class Mapper1 extends Mapper<Object, Text, Text, IntWritable> {
		// initialize key
		private Text outputKey = new Text();
		private IntWritable result = new IntWritable();
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString(), "\n");
			while (itr.hasMoreTokens()) {
				String lines = itr.nextToken();
				String[] line = lines.split(",");
				if (line[0].equals("date_time")) continue;
				else {
					String country = line[3];
					String adults = line[14];
					String children = line[15];
					outputKey.set(country);
					result.set(Integer.parseInt(adults) + Integer.parseInt(children));
				}
				context.write(outputKey, result);
			}
		}
	}
	
	public static class Reducer1 extends Reducer<Text, IntWritable, Text, IntWritable> {

	    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
	      int sum = 0;
	      int count = 0;
	      for (IntWritable val : values) {
	        sum += val.get();
	        count++;
	      }
	      context.write(key, new IntWritable(sum/count));
	    }
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
	    Job job = Job.getInstance(conf, "average sum");
	    job.setJarByClass(AverageSum.class);
	    job.setMapperClass(Mapper1.class);
	    job.setCombinerClass(Reducer1.class);
	    job.setReducerClass(Reducer1.class);
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
