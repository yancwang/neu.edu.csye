package part3;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MyMapper extends Mapper<Object, Text, Text, AveragePriceWritable> {
	
	private Text stockId = new Text();
	private AveragePriceWritable a = new AveragePriceWritable();

	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		StringTokenizer lines = new StringTokenizer(value.toString());
		while (lines.hasMoreTokens()) {
			String line = lines.nextToken();
			StringTokenizer column = new StringTokenizer(line, ",");
    		int count = 0;
    		while(column.hasMoreTokens()) {
    			count++;
    			String s = column.nextToken();
    			if(s.equals("exchange")) break;
    			if(count == 2) stockId.set(s);
    			if(count == 3) {
    				StringTokenizer date = new StringTokenizer(s, "-");
    				if (date.hasMoreTokens()) a.year = new Text(date.nextToken());
    			}
    			if(count == 9) {
    				a.stock_price_adj_close = Double.parseDouble(s);
    				context.write(stockId, a);
    			}
    		}
		}
	}
	
}
