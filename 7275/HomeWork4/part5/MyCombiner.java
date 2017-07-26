package part5;

import java.io.IOException;
import java.util.TreeMap;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MyCombiner extends Reducer<Text, MovieRatingWritable, Text, MovieRatingWritable> {
	
	private MovieRatingWritable result = new MovieRatingWritable();
	private TreeMap<Double, Integer> tm = new TreeMap<Double, Integer>();

	public void reduce(Text key, Iterable<MovieRatingWritable> values, Context context) throws IOException, InterruptedException {
		
		tm.clear();
		
		for (MovieRatingWritable value : values) {
			if (tm.get(value.ratings) == null) tm.put(value.ratings, 1);
			else {
				int tmp = tm.get(value.ratings);
				tm.put(value.ratings, tmp + 1);
			}
		}
		
		result.tm = tm;
		context.write(key, result);
	}
}
