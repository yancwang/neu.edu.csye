package preprocess;

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
import org.apache.log4j.BasicConfigurator;

public class Average {

	public static class AverageMapper extends Mapper<Object, Text, Text, DoubleWritable>{
		
		private Text stockRating = new Text();
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer lines = new StringTokenizer(value.toString(), "\t\r\n");
			while (lines.hasMoreTokens()) {
	    		String line = lines.nextToken();
	    		StringTokenizer column = new StringTokenizer(line, ",");
	    		int count = 0;
	    		String k = "";
	    		double sum = 0;
	    		while(column.hasMoreTokens()) {
	    			count++;
	    			String s = column.nextToken();
	    			if (s.equals("Brokerage")) break;
	    			if (count == 1) k = k.concat(s);
	    			if (count == 2) k = k.concat(s);
	    			if (count == 4) k = k.concat(s);
	    			if (count > 6 && count % 2 == 1) sum = sum + Double.parseDouble(s);
	    			if (!column.hasMoreTokens()) {
	    				DoubleWritable v = new DoubleWritable(sum / 10);
	    	    		stockRating.set(k);
	        			context.write(stockRating, v);
	    			}
	    		}
	        }
		}
		
	}
	
	public static class AverageReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
		
		private DoubleWritable result = new DoubleWritable();
		
		public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
			double value = 0;
			for (DoubleWritable val : values) {
				value = val.get();
			}
			result.set(value);
			context.write(key, result);
		}
		
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		BasicConfigurator.configure();
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "average");
		Path input = new Path(args[0]);
		Path output = new Path(args[1]);
		FileSystem fs = FileSystem.get(conf);
		if (fs.exists(output)) fs.delete(output, true);
		job.setJarByClass(Average.class);
		job.setMapperClass(AverageMapper.class);
		job.setCombinerClass(AverageReducer.class);
		job.setReducerClass(AverageReducer.class);
		job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(DoubleWritable.class);
	    FileInputFormat.addInputPath(job, input);
	    FileOutputFormat.setOutputPath(job, output);
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
