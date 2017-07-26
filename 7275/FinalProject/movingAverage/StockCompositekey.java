package movingaverage;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

public class StockCompositeKey implements Writable, WritableComparable<StockCompositeKey> {
	
	private String name;
	private String timestamp;

	public StockCompositeKey() {
		
	}
	
	public StockCompositeKey(String name, String timestamp) {
		this.name = name;
		this.timestamp = timestamp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int compareTo(StockCompositeKey o) {
		int result = 0;
		String[] s1 = timestamp.split("/");
		String[] s2 = o.getTimestamp().split("/");
		if (s1.length == 3 && s2.length == 3) {
			if (s1[2].compareTo(s2[2]) == 0) {
				if (s1[2].compareTo(s2[1]) == 0) {
					if (s1[0].compareTo(s2[0]) == 0) result = 0;
					else result = s1[0].compareTo(s2[0]);
				} else {
					result = s1[2].compareTo(s2[1]);
				}
			} else {
				result = s1[2].compareTo(s2[2]);
			}
		} else {
			result = name.compareTo(o.getName());
		}
		if(result == 0) {
			result = name.compareTo(o.getName());
		}
		return result;
	}

	public void write(DataOutput out) throws IOException {
		WritableUtils.writeString(out, name);
		WritableUtils.writeString(out, timestamp);
	}

	public void readFields(DataInput in) throws IOException {
		this.name = WritableUtils.readString(in);
		this.timestamp = WritableUtils.readString(in);
	}
	
	public String toString() {
		return (new StringBuilder().append(name).append("\t").append(timestamp).toString());
	}

}