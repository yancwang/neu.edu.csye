package neu.edu.data;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;

public class AverageSum {

	public static void main(String[] args) {
		MongoClient client = new MongoClient();
		DB db = client.getDB("users");
		DBCollection collection = db.getCollection("expedia");
		String map = "function() { var value = this.srch_adults_cnt + "
				+ "this.srch_children_cnt; emit(this.user_location_country, "
				+ "{key: this.user_location_country, value: value});}";
		String reduce = "function(key, values) { var sum = 0; var count = 0; "
				+ "values.forEach(function (data) { count++; sum += data.value; }); "
				+ "return {key: key, value: sum / count};}";
		MapReduceCommand cmd = new MapReduceCommand(collection, map, reduce, "avgSum", MapReduceCommand.OutputType.REPLACE, null);
		MapReduceOutput out = collection.mapReduce(cmd);
		collection = db.getCollection("avgSum");
		BasicDBObject sort = new BasicDBObject();
		sort.append("value.count", -1);
		DBCursor cursor = collection.find().sort(sort).limit(25);
		for (DBObject dbObject : cursor) {
	        System.out.println(dbObject);
	    }
	}

}
