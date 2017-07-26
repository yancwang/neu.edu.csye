package part2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class MaxValueDateWritable implements Writable, WritableComparable<MaxValueDateWritable> {
	
	public long stock_Volumn_max = 0;
	public long stock_Volumn_min = 0;
	public Text max;
	public Text min;
	public double stock_price_adj_close;
	
	public MaxValueDateWritable() {
		
	}
	
	public MaxValueDateWritable(long stock_Volumn_max, long stock_Volumn_min, Text max, Text min, double stock_price_adj_close) {
		this.stock_Volumn_max = stock_Volumn_max;
		this.stock_Volumn_min = stock_Volumn_min;
		this.max = max;
		this.min = min;
		this.stock_price_adj_close = stock_price_adj_close;
	}

	public int compareTo(MaxValueDateWritable m) {
		int result = 0;
		if (this.stock_Volumn_min > m.stock_Volumn_min) result = 1;
		else if (this.stock_Volumn_min < m.stock_Volumn_min) result = -1;
		return result;
	}

	public void write(DataOutput out) throws IOException {
		out.writeLong(stock_Volumn_max);
		out.writeLong(stock_Volumn_min);
		out.writeUTF(max.toString());
		out.writeUTF(min.toString());
		out.writeDouble(stock_price_adj_close);
	}

	public void readFields(DataInput in) throws IOException {
		this.stock_Volumn_max = in.readLong();
		this.stock_Volumn_min = in.readLong();
		this.max = new Text(in.readUTF());
		this.min = new Text(in.readUTF());
		this.stock_price_adj_close = in.readDouble();
	}
	
	public String toString() {
		return (new StringBuilder().append(Long.toString(this.stock_Volumn_max)).append("\t").append(max).append("\t").append(Long.toString(stock_Volumn_min)).append("\t").
				append(min).append("\t").append(Double.toString(stock_price_adj_close)).toString());
	}

}
