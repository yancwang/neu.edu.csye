package part5;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MyMapper extends Mapper<Object, Text, Text, MovieRatingWritable> {

	private Text movieId = new Text();
	private MovieRatingWritable result = new MovieRatingWritable();

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
				else if (count == 3) {
					result.ratings = Double.parseDouble(s);
					context.write(movieId, result);
				}
			}
		}
	}
}
