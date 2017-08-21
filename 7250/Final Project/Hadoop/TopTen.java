package neu.edu.analysis;

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

public class TopTen {
	
	public static class ExpediaMapper extends Mapper<Object, Text, Text, IntWritable> {
		// initialize key
		private Text outputKey = new Text();
		private final static IntWritable one = new IntWritable(1);
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString(), "\n");
			while (itr.hasMoreTokens()) {
				String lines = itr.nextToken();
				String[] line = lines.split(",");
				if (line[0].equals("date_time")) continue;
				else {
					String[] date = line[0].split(" ");
					String[] dates = date[0].split("-");
					outputKey.set(dates[1]);
				}
				context.write(outputKey, one);
			}
		}
	}
	
	public static class ExpediaReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
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
		Configuration conf = new Configuration();
	    Job job = Job.getInstance(conf, "top ten");
	    job.setJarByClass(TopTen.class);
	    job.setMapperClass(ExpediaMapper.class);
	    job.setCombinerClass(ExpediaReducer.class);
	    job.setReducerClass(ExpediaReducer.class);
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
