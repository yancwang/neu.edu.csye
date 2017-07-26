public class AveragePriceWritable implements Writable, WritableComparable<AveragePriceWritable> {
	
	public Text year;
	public double stock_price_adj_close;
	public double average_stock_price_adj_close;

	public AveragePriceWritable() {
		
	}
	
	public AveragePriceWritable(Text year, double stock_price_adj_close, double average_stock_price_adj_close) {
		this.year = year;
		this.stock_price_adj_close = stock_price_adj_close;
		this.average_stock_price_adj_close = average_stock_price_adj_close;
	}

	public int compareTo(AveragePriceWritable o) {
		if (this.year.equals(o.year)) return 0;
		else return -1;
	}

	public void write(DataOutput out) throws IOException {
		out.writeUTF(year.toString());
		out.writeDouble(stock_price_adj_close);
	}

	public void readFields(DataInput in) throws IOException {
		this.year = new Text(in.readUTF());
		this.stock_price_adj_close = in.readDouble();
	}
	
	public String toString() {
		return (new StringBuilder().append(Double.toString(average_stock_price_adj_close)).toString());
	}

}