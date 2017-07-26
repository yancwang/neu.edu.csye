package part2;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MyMapper extends Mapper<Object, Text, Text, MaxValueDateWritable>{
	
	private Text stockId = new Text();
	private MaxValueDateWritable m = new MaxValueDateWritable();

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
    				m.max = new Text(s);
    				m.min = new Text(s);
    			}
    			if(count == 8) {
    				m.stock_Volumn_max = Long.parseLong(s);
    				m.stock_Volumn_min = Long.parseLong(s);
    			}
    			if(count == 9) {
    				m.stock_price_adj_close = Double.parseDouble(s);
    				context.write(stockId, m);
    			}
    		}
		}
	}

}
