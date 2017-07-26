package part6;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MyMapper extends Mapper<Object, Text, Text, IntWritable> {

	private Text word = new Text();
	private final static IntWritable one = new IntWritable(1);
	
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		StringTokenizer lines = new StringTokenizer(value.toString(), "\n");
		while (lines.hasMoreTokens()) {
			StringTokenizer line = new StringTokenizer(lines.nextToken());
			while (line.hasMoreTokens()) {
				word.set(line.nextToken());
				context.write(word, one);
				break;
			}
		}
	}
}
