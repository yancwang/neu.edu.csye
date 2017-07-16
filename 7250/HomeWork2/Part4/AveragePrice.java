package neu.edu.part3;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;

public class AveragePrice {

	public static void main(String[] args) {
		MongoClient client = new MongoClient();
		DB db = client.getDB("mydb");
		DBCollection collection = db.getCollection("NYSE"); 
		String map = "function() { if (this.stock_symbol && this.stock_price_high != null) {emit(this.stock_symbol, this.stock_price_high);}}";
		String reduce = "function(key, values) {var average = Array.sum(values) / values.length; return average;}";
		MapReduceCommand cmd = new MapReduceCommand(collection, map, reduce, "avgHighPrice", MapReduceCommand.OutputType.REPLACE, null);
		MapReduceOutput out = collection.mapReduce(cmd);
//        System.out.println("Mapreduce results");
//        for (DBObject o : out.results()) {
//        	System.out.println(o.toString());
//        }
	}

}
