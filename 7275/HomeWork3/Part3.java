package neu.edu.csye;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MultiFileInputFormat;
import org.apache.hadoop.mapred.MultiFileSplit;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.CombineFileRecordReader;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.reduce.IntSumReducer;
import org.apache.hadoop.util.LineReader;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.BasicConfigurator;

public class Part3 extends Configured implements Tool {
	
	private static final String input_1 = "output/part-r-00000";
	private static final String input_2 = "input/input2.txt";
	private static final String input_3 = "input/input3.txt";
	
	public static class WordOffset implements WritableComparable {

	    private long word;
	    private String fileName;

	    public void readFields(DataInput in) throws IOException {
	      this.word = in.readLong();
	      this.fileName = Text.readString(in);
	    }

	    public void write(DataOutput out) throws IOException {
	      out.writeLong(word);
	      Text.writeString(out, fileName);
	    }

	    public int compareTo(Object o) {
	      WordOffset that = (WordOffset)o;

	      int f = this.fileName.compareTo(that.fileName);
	      if(f == 0) {
	        return (int)Math.signum((double)(this.word - that.word));
	      }
	      return f;
	    }
	    @Override
	    public boolean equals(Object obj) {
	      if(obj instanceof WordOffset)
	        return this.compareTo(obj) == 0;
	      return false;
	    }
	    @Override
	    public int hashCode() {
	      assert false : "hashCode not designed";
	      return 42; 
	    }
	  }

	  public static class InputFormat extends MultiFileInputFormat<WordOffset, Text>  {
	    public RecordReader<WordOffset,Text> getRecordReader(InputSplit split, JobConf job, Reporter reporter) throws IOException {
	      return null;//new MultiFileLineRecordReader(job, (MultiFileSplit)split);
	    }

		@Override
		public org.apache.hadoop.mapred.RecordReader<WordOffset, Text> getRecordReader(
				org.apache.hadoop.mapred.InputSplit split, JobConf job,
				Reporter reporter) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}
	  }

	public static class MyMapper extends Mapper<Object, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString());
			while (itr.hasMoreTokens()) {
				word.set(itr.nextToken());
				context.write(word, one);
			}
		}
	}
	
	public static class MyReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
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
	
	public static void main(String[] args) throws Exception {
		int ret = ToolRunner.run(new Part3(), args);
	    System.exit(ret);
	}

	public int run(String[] args) throws Exception {
		BasicConfigurator.configure();
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "part2");
	    job.setJobName("Part3");
	    job.setJarByClass(Part3.class);

//	    job.setInputFormatClass(Text.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(IntWritable.class);

	    job.setMapperClass(MyMapper.class);
	    job.setCombinerClass(MyReducer.class);
	    job.setReducerClass(MyReducer.class);

	    FileInputFormat.addInputPaths(job, args[0]);
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));

	    return job.waitForCompletion(true) ? 0 : 1;
	}
	
}