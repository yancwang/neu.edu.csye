package part2;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MyReducer extends Reducer<Text, MaxValueDateWritable, Text, MaxValueDateWritable> {
	
	private MaxValueDateWritable result = new MaxValueDateWritable();
	
	public void reduce(Text key, Iterable<MaxValueDateWritable> values, Context context) throws IOException, InterruptedException {
		result.stock_Volumn_max = 0;
		result.stock_Volumn_min = 0;
		result.max = null;
		result.min = null;
		result.stock_price_adj_close = 0;
		
		for (MaxValueDateWritable val : values) {
			if (result.stock_Volumn_max == 0) {
				result.max = val.max;
				result.stock_Volumn_max = val.stock_Volumn_max;
			}
			else if (result.stock_Volumn_max < val.stock_Volumn_max) {
				result.max = val.max;
				result.stock_Volumn_max = val.stock_Volumn_max;
			}
			if (result.stock_Volumn_min == 0) {
				result.min = val.min;
				result.stock_Volumn_min = val.stock_Volumn_min;
			}
			else if (result.stock_Volumn_min > val.stock_Volumn_min) {
				result.min = val.min;
				result.stock_Volumn_min = val.stock_Volumn_min;
			}
			if (result.stock_price_adj_close == 0 || result.stock_price_adj_close < val.stock_price_adj_close) 
				result.stock_price_adj_close = val.stock_price_adj_close;
		}
		
		context.write(key, result);
	}

}
