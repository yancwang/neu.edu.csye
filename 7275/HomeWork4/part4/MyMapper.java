package part4;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MyMapper extends Mapper<Object, Text, Text, DoubleWritable> {
	
	private Text movieId = new Text();

	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		StringTokenizer lines = new StringTokenizer(value.toString());
		while (lines.hasMoreTokens()) {
			StringTokenizer line = new StringTokenizer(lines.nextToken(), "::");
			int count = 0;
			while (line.hasMoreTokens()) {
				count++;
				String s = line.nextToken();
				if (count == 1) continue;
				else if (count == 2) movieId.set(s);
				else if (count == 3) context.write(movieId, new DoubleWritable(Double.parseDouble(s)));
			}
		}
	}
	
}
