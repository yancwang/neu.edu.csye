package movingaverage;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

public class MovingAverage {
	
	public static class MovingAverageMapper extends Mapper<Object, Text, StockCompositeKey, DoubleWritable> {
		
		private DoubleWritable price = new DoubleWritable();
		private StockCompositeKey skey = new StockCompositeKey();
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer lines = new StringTokenizer(value.toString(), "\t\r\n");
			
			while (lines.hasMoreTokens()) {
				String line = lines.nextToken();
	    		StringTokenizer column = new StringTokenizer(line, ",");
	    		int count = 0;
	    		String val = new String();
	    		while(column.hasMoreTokens()) {
	    			count++;
	    			String s = column.nextToken();
	    			if (s.equals("Adj_Close")) break;
	    			if (count == 2) val = s;
	    			if (count == 4) skey.setTimestamp(s);
	    			if (count == 8) {
	    				skey.setName(s);
	    				price.set(Double.parseDouble(val));
	    				context.write(skey, price);
	    			}
	    		}
			}
		}
		
	}
	
	public static class MovingAverageReducer extends Reducer<StockCompositeKey, DoubleWritable, StockCompositeKey, DoubleWritable> {
		
		private AverageCalculate calculate = new AverageCalculate(30);
		private DoubleWritable result = new DoubleWritable();
		
		public void reduce(StockCompositeKey key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
			for (DoubleWritable val : values) {
				calculate.addNewNumber(val.get());
				result.set(calculate.getSum());
				context.write(key, result);
			}
		}
		
	}
	
	public static class NaturalKeyPartitioner extends Partitioner<StockCompositeKey, NullWritable>{

		public int getPartition(StockCompositeKey key, NullWritable value, int numPartitions) {
			return (key.getName().hashCode()%numPartitions);
		}

	}
	
	public static class NaturalGroupingComparator extends WritableComparator {

		protected NaturalGroupingComparator() {
			super(StockCompositeKey.class, true);
		}
		
		public int compare(StockCompositeKey w1, StockCompositeKey w2) {
			StockCompositeKey cw1 = (StockCompositeKey) w1;
			StockCompositeKey cw2 = (StockCompositeKey) w2;
			return cw1.getName().compareTo(cw2.getName());
		}
		
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		if(args.length != 2) {
			System.out.println("Parameters required");
			System.exit(-1);
		}
		
		BasicConfigurator.configure();
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "MovingAverage");
		Path input = new Path(args[0]);
		Path output = new Path(args[1]);
		FileSystem fs = FileSystem.get(conf);
		if (fs.exists(output)) fs.delete(output, true);
		job.setJarByClass(MovingAverage.class);
		job.setMapperClass(MovingAverageMapper.class);
		job.setMapOutputKeyClass(StockCompositeKey.class);
		job.setMapOutputValueClass(DoubleWritable.class);
		job.setReducerClass(MovingAverageReducer.class);
		job.setOutputKeyClass(StockCompositeKey.class);
		job.setOutputValueClass(DoubleWritable.class);
		job.setPartitionerClass(NaturalKeyPartitioner.class);
		job.setGroupingComparatorClass(NaturalGroupingComparator.class);
		FileInputFormat.addInputPath(job, input);
	    FileOutputFormat.setOutputPath(job, output);
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}