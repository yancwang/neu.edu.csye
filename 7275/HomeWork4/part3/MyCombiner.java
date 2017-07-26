package part3;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MyCombiner extends Reducer<Text,  AveragePriceWritable, Text, AveragePriceWritable> {
	
	

	public void reduce(Text key, Iterable<AveragePriceWritable> values, Context context) throws IOException, InterruptedException {
		for (AveragePriceWritable val : values) {
			String s = key.toString()+ " " + val.year.toString();
			context.write(new Text(s), val);
		}
	}

}
