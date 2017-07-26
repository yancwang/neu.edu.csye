package neu.edu;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
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
import org.apache.log4j.BasicConfigurator;


public class Part1 {
	
	public static class TokenizerMapper extends Mapper<Object, Text, Text, DoubleWritable>{
	    private Text word = new Text();

	    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
	    	StringTokenizer lines = new StringTokenizer(value.toString());
	    	while (lines.hasMoreTokens()) {
	    		String line = lines.nextToken();
	    		StringTokenizer column = new StringTokenizer(line, ",");
	    		int count = 0;
	    		while(column.hasMoreTokens()) {
	    			count++;
	    			String s = column.nextToken();
	    			if(s.equals("exchange")) break;
	    			if(count == 2) word.set(s);
	    			if(count == 5) {
	    				DoubleWritable price = new DoubleWritable(Double.parseDouble(s));
	        			context.write(word, price);
	    			}
	    			
	    		}
	        }
	    }
	}
	
	public static class DoubleSumReducer extends Reducer<Text,DoubleWritable,Text,DoubleWritable> {
		private DoubleWritable result = new DoubleWritable();
		public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
		double sum = 0;
		int count = 0;
			for (DoubleWritable val : values) {
				sum += val.get();
				count++;
			}
		result.set(sum/count);
		context.write(key, result);
		}
	}

	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		Configuration conf = new Configuration();
		FileSystem hdfs = FileSystem.get(conf);
		FileSystem local = FileSystem.getLocal(conf);
		Path inputDir = new Path(args[0]);
		Path hdfsFile = new Path(args[1]);
		try {
			FileStatus[] inputFiles = local.listStatus(inputDir);
			FSDataOutputStream out = hdfs.create(hdfsFile);
			for (int i=0; i<inputFiles.length; i++) {
				System.out.println(inputFiles[i].getPath().getName());
				FSDataInputStream in = local.open(inputFiles[i].getPath());
				byte buffer[] = new byte[256];
				int bytesRead = 0;
				while( (bytesRead = in.read(buffer)) > 0) {
					out.write(buffer, 0, bytesRead);
				}
				in.close();
			}
			out.close();
			long startTime = System.currentTimeMillis();
			Job job = Job.getInstance(conf, "part1");
		    job.setJarByClass(Part1.class);
		    job.setMapperClass(TokenizerMapper.class);
		    job.setCombinerClass(DoubleSumReducer.class);
		    job.setReducerClass(DoubleSumReducer.class);
		    job.setOutputKeyClass(Text.class);
		    job.setOutputValueClass(DoubleWritable.class);
		    FileSystem.getLocal(conf).delete(new Path(args[2]), true);
		    FileInputFormat.addInputPath(job, hdfsFile);
		    FileOutputFormat.setOutputPath(job, new Path(args[2]));
		    job.waitForCompletion(true);
		    long endTime   = System.currentTimeMillis();
		    long totalTime = endTime - startTime;
		    System.out.println(totalTime);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
