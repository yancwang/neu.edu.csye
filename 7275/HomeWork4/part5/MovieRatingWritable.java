package part5;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.TreeMap;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class MovieRatingWritable implements Writable, WritableComparable<MovieRatingWritable> {

	public double median;
	public double stdDev;
	public TreeMap<Double, Integer> tm;
	public double ratings;
	
	public MovieRatingWritable() {
		
	}
	
	public MovieRatingWritable(double median, double stdDev, double ratings) {
		this.median = median;
		this.stdDev = stdDev;
		this.ratings = ratings;
	}

	public int compareTo(MovieRatingWritable o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void write(DataOutput out) throws IOException {
		out.writeDouble(median);
		out.writeDouble(stdDev);
		out.writeDouble(ratings);
	}

	public void readFields(DataInput in) throws IOException {
		this.median = in.readDouble();
		this.stdDev = in.readDouble();
		this.ratings = in.readDouble();
	}

	public String toString() {
		return (new StringBuilder().append(Double.toString(median)).append("\t").append(Double.toString(stdDev)).toString());
	}
	
}