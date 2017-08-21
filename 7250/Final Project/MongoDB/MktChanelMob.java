package neu.edu.data2;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;

public class MktChanelMob {

	public static void main(String[] args) {
		MongoClient client = new MongoClient();
		DB db = client.getDB("users");
		DBCollection collection = db.getCollection("expedia");
		String map = "function() { emit({chanel: this.channel, mobile: this.is_mobile}, "
				+ "{count: 1}); }";
		String reduce = "function(key, values) { total = 0; for (var i in values) "
				+ "{ total += values[i].count; } return {count: total}; }";
		MapReduceCommand cmd = new MapReduceCommand(collection, map, reduce, "mktChanelMob", MapReduceCommand.OutputType.REPLACE, null);
		MapReduceOutput out = collection.mapReduce(cmd);
		collection = db.getCollection("mktChanelMob");
		BasicDBObject sort = new BasicDBObject();
		sort.append("value", -1);
		DBCursor cursor = collection.find().sort(sort).limit(25);
		for (DBObject dbObject : cursor) {
	        System.out.println(dbObject);
	    }
	}

}
