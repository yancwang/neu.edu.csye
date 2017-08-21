package neu.edu.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class TopCountry {

	public static void main(String[] args) throws FileNotFoundException {
		MongoClient client = new MongoClient();
		DB db = client.getDB("users");
		DBCollection collection = db.getCollection("expedia"); 
		String map = "function() { emit(this.user_location_country, {key: this.user_location_country, count: 1});}";
		String reduce = "function(key, values) { total = 0; for (var i in values) "
				+ "{ total += values[i].count; } return {key: key, count: total};}";
		MapReduceCommand cmd = new MapReduceCommand(collection, map, reduce, "topCty", MapReduceCommand.OutputType.REPLACE, null);
		MapReduceOutput out = collection.mapReduce(cmd);
		MongoDatabase database = client.getDatabase("users");
		MongoCollection<Document> col = database.getCollection("topCty");
		FindIterable<Document> itr = col.find().sort(new Document().append("value.count", -1)).limit(10);
		MongoCursor<Document> cursor = itr.iterator();
		PrintWriter pw = new PrintWriter(new File("data1.csv"));
		StringBuilder builder = new StringBuilder();
		String cName = "Id, Count";
		builder.append(cName + "\n");
		while (cursor.hasNext()) {
			Document temp = (Document) cursor.next().get("value");
			Double key =  (Double) temp.get("key");
			Double value = (Double) temp.get("count");
			builder.append(key.intValue() + ", " + value + "\n");
		}
		pw.write(builder.toString());
		pw.close();
	}

}
