package neu.edu.part7;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;

public class NumRatings {

	public static void main(String[] args) {
		MongoClient client = new MongoClient();
		DB db = client.getDB("mydb");
		DBCollection collection = db.getCollection("ratings");
		String map = "function() {emit(this.UserID, this.MovieID);}";
		String reduce = "function(key, values) {return values.length;}";
		MapReduceCommand cmd = new MapReduceCommand(collection, map, reduce, "numRatings", MapReduceCommand.OutputType.REPLACE, null);
		MapReduceOutput out = collection.mapReduce(cmd);
	}

}
