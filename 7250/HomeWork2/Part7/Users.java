package neu.edu.part7;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;

public class Users {

	public static void main(String[] args) {
		MongoClient client = new MongoClient();
		DB db = client.getDB("mydb");
		DBCollection collection = db.getCollection("users");
		String map = "function() {"
				+ "var key = this.Gender;"
				+ "value = {cnt: 1};"
				+ "emit(key, value);"
				+ "}";
		String reduce = "function(key, values) {"
				+ "value = {cnt: 0}; "
				+ "for (var idx = 0; idx < values.length; idx++) {"
				+ "value.cnt = value.cnt + values[idx].cnt;} "
				+ "return value;}";
		MapReduceCommand cmd = new MapReduceCommand(collection, map, reduce, "userSum", MapReduceCommand.OutputType.REPLACE, null);
		MapReduceOutput out = collection.mapReduce(cmd);
	}

}
