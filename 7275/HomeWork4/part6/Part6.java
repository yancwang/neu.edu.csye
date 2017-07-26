package part6;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

public class Part6 {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		BasicConfigurator.configure();
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "part6");
		Path input = new Path(args[0]);
		Path output = new Path(args[1]);
		FileSystem fs = FileSystem.get(conf);
		if (fs.exists(output)) fs.delete(output, true);
		job.setJarByClass(Part6.class);
	    job.setMapperClass(MyMapper.class);
	    job.setCombinerClass(MyReducer.class);
	    job.setReducerClass(MyReducer.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(IntWritable.class);
	    FileInputFormat.addInputPath(job, input);
	    FileOutputFormat.setOutputPath(job, output);
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}