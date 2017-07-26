package part4;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class MovieRatingWritable implements Writable, WritableComparable<MovieRatingWritable> {
	
	public double median;
	public double stdDev;

	public MovieRatingWritable() {
		
	}
	
	public MovieRatingWritable(double median, double stdDev) {
		this.median = median;
		this.stdDev = stdDev;
	}

	public int compareTo(MovieRatingWritable o) {
		return 0;
	}

	public void write(DataOutput out) throws IOException {
		out.writeDouble(median);
		out.writeDouble(stdDev);
	}

	public void readFields(DataInput in) throws IOException {
		this.median = in.readDouble();
		this.stdDev = in.readDouble();
	}
	
	public String toString() {
		return (new StringBuilder().append(Double.toString(median)).append("\t").append(Double.toString(stdDev)).toString());
	}

}