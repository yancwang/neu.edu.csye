package part4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MyReducer extends Reducer<Text, DoubleWritable, Text, MovieRatingWritable> {
	
	private MovieRatingWritable result = new MovieRatingWritable();
	private ArrayList<Double> ratings = new ArrayList<Double>();
	
	public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
		double sum = 0;
		int count = 0;
		result.stdDev = 0;
		ratings.clear();
		
		for (DoubleWritable val : values) {
			ratings.add(val.get());
			sum += val.get();
			count++;
		}
		
		Collections.sort(ratings);
		
		if (count % 2 == 0) result.median = (ratings.get(count / 2 - 1) + ratings.get(count / 2)) / 2;
		else result.median = ratings.get((int)count / 2);
		
		double mean = sum / count;
		double sumSquare = 0;
		for (double r : ratings) {
			sumSquare += (r - mean) * (r - mean);
		}
		result.stdDev = Math.sqrt(sumSquare / count);
		context.write(key, result);
	}
}
