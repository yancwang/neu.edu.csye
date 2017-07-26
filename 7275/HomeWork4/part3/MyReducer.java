package part3;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MyReducer extends Reducer<Text,  AveragePriceWritable, Text, AveragePriceWritable> {
	
	private AveragePriceWritable result = new AveragePriceWritable();

	public void reduce(Text key, Iterable<AveragePriceWritable> values, Context context) throws IOException, InterruptedException {
		result.year = null;
		double sum = 0;
		int num = 0;
		for (AveragePriceWritable val : values) {
			sum += val.stock_price_adj_close;
			num++;
		}
		result.average_stock_price_adj_close = sum / num;
		context.write(key, result);
	}
	
}
