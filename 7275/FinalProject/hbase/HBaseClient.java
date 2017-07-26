package hbase;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class HBaseClient {
	
	public static class StockMapper extends Mapper<Object, Text, Text, NullWritable>{
		
		private Text stock = new Text();
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer lines = new StringTokenizer(value.toString());
			while (lines.hasMoreTokens()) {
				String line = lines.nextToken();
				String[] cols = line.split(",");
				if (cols.length != 9) continue;
				stock.set(line);
				context.write(stock, null);
			}
		}
		
	}
	
	public static class StockReducer extends Reducer<Text, NullWritable, ImmutableBytesWritable, Put> {
		
		private byte[] family = null;
	    private byte[] qualifier = null;
		
		protected void setup(Context context) {
			String column = context.getConfiguration().get("conf.column");
			byte[][] colkey = KeyValue.parseColumn(Bytes.toBytes(column));
			family = colkey[0];
		    if (colkey.length > 1) {
		    	qualifier = colkey[1];
		    }
		}
		
		@SuppressWarnings("deprecation")
		public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
			String line = key.toString();
			String[] cols = line.split(",");
			String rowkey = cols[3] + cols[7];
			Put put = new Put(rowkey.getBytes());
			put.add(family, qualifier, Bytes.toBytes(line));
			context.write(new ImmutableBytesWritable(Bytes.toBytes(rowkey)), put);
		}
		
	}

	@SuppressWarnings("deprecation")
	/*
	 * args[0] input path
	 * args[1] output path
	 * args[2]
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration hbconfig = HBaseConfiguration.create();
		Connection connection = ConnectionFactory.createConnection(hbconfig);
		Admin admin = connection.getAdmin();
		TableName name = TableName.valueOf(args[0]);
		HTableDescriptor table = new HTableDescriptor();
		HColumnDescriptor column = new HColumnDescriptor("records");
		if (!admin.tableExists(name)) {
			table.addFamily(column);
			admin.createTable(table);
		}
		table.addFamily(column);
		Job job = Job.getInstance(hbconfig, "HBaseClient");
		Path input = new Path(args[0]);
		job.setJarByClass(HBaseClient.class);
		job.setMapperClass(StockMapper.class);
		job.setCombinerClass(StockReducer.class);
		job.setReducerClass(StockReducer.class);
		job.setOutputKeyClass(ImmutableBytesWritable.class);
		job.setOutputValueClass(Put.class);
		FileInputFormat.addInputPath(job, input);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}