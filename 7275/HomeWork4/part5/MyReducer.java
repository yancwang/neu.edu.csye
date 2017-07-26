package part5;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import part5.MovieRatingWritable;

public class MyReducer extends Reducer<Text, MovieRatingWritable, Text, MovieRatingWritable> {
	
	private MovieRatingWritable result = new MovieRatingWritable();
	private TreeMap<Double, Integer> tm = new TreeMap<Double, Integer>();
	
	public void reduce(Text key, Iterable<MovieRatingWritable> values, Context context) throws IOException, InterruptedException {
		double sum = 0;
		int count = 0;
		result.stdDev = 0;
		tm.clear();
		
		for (MovieRatingWritable value : values) {
			TreeMap<Double, Integer> tmp = value.tm;
			
			for (Entry<Double, Integer> entry : tmp.entrySet()) {
				if (tm.get(entry.getKey()) == null) tm.put(entry.getKey(), entry.getValue());
				else {
					int sum_tmp = entry.getValue();
					tm.put(entry.getKey(), entry.getValue() + sum_tmp);
				}
			}
		}
		
		double medianIndex = (double) count / 2;
		int previous = 0;
		int temporary = 0;
		double preKey = 0;
		
		for (Entry<Double, Integer> entry : tm.entrySet()) {
			temporary = previous + entry.getValue();
			if (previous < medianIndex && medianIndex <= temporary) {
				result.median = entry.getKey();
			}
			previous = temporary;
			preKey = entry.getKey();
		}
		
		double mean = sum / count;
		double sumSquare = 0;
		
		for (Entry<Double, Integer> entry : tm.entrySet()) {
			sumSquare += (entry.getKey() - mean) * (entry.getKey() - mean) * entry.getValue();
		}
		
		result.stdDev = Math.sqrt(sumSquare / count);
		context.write(key, result);
	}

}
