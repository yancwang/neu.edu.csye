package neu.edu.part7;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;

public class Top25 {

	public static void main(String[] args) {
		MongoClient client = new MongoClient();
		DB db = client.getDB("mydb");
		DBCollection collection = db.getCollection("ratings"); 
		String map = "function() { emit(this.MovieID, 1);}";
		String reduce = "function(key, values) {var count = 0; for (var i in values) {count = count + 1;} return count;}";
		MapReduceCommand cmd = new MapReduceCommand(collection, map, reduce, "top25", MapReduceCommand.OutputType.REPLACE, null);
		MapReduceOutput out = collection.mapReduce(cmd);
		collection = db.getCollection("top25");
		BasicDBObject sort = new BasicDBObject();
		sort.append("value", -1);
		DBCursor cursor = collection.find().sort(sort).limit(25);
		for (DBObject dbObject : cursor) {
	        System.out.println(dbObject);
	    }
	}

}
