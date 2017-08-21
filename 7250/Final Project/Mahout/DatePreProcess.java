package neu.edu.mahout;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DatePreProcess {
	
	public static class HMapper extends Mapper<Object, Text, Text, NullWritable> {
		
		private Text outputKey = new Text();
		private final static IntWritable one = new IntWritable(1);
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString(), "\n");
			while (itr.hasMoreTokens()) {
				String lines = itr.nextToken();
				String[] line = lines.split(",");
				if (line[0].equals("date_time")) continue;
				else {
					String userID = line[7];
					String itemID = line[20] + line[21] + line[22] + line[23];
					String preference = line[18];
					outputKey.set(userID + "," + itemID + "," + preference);
				}
				context.write(outputKey, null);
			}
		}
		
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
	    Job job = Job.getInstance(conf, "pre process");
	    job.setJarByClass(DatePreProcess.class);
	    job.setMapperClass(HMapper.class);
	    job.setNumReduceTasks(0);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(NullWritable.class);
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileSystem fs = FileSystem.get(conf);
	    Path output = new Path(args[1]);
	    if (fs.exists(output)) fs.delete(output, true);
	    FileOutputFormat.setOutputPath(job, output);
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
