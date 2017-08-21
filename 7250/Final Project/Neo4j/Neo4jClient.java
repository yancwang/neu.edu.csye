package neu.edu.neo4j;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Neo4jClient {
	
	private static final String DATABASE_NAME = "users";
	private static final String COLLECTION_NAME = "expedia";
	private Map<String, Object> params;
	private Session session;
	private MongoCollection<Document> collection;

	public Neo4jClient() {
		params = new HashMap<String, Object>();
		
		Driver driver = GraphDatabase
				.driver( "bolt://localhost:7687", AuthTokens.basic("neo4j", "1"));
		session = driver.session();
		
		MongoClient client = new MongoClient();
		MongoDatabase database = client.getDatabase(DATABASE_NAME);
		collection = database.getCollection(COLLECTION_NAME);
		
		FindIterable<Document> itr = collection.find();
		MongoCursor<Document> cursor = itr.iterator();
		
		while (cursor.hasNext()) {
			Document temp = cursor.next();
			if (!userExistInNeo4j(temp.get("user_id"))) {
				insertUser(temp);
//				System.out.println("create user: " + temp.get("user_id"));
			}
			String hotelID = Integer.toString((Integer) temp.get("hotel_continent"))
					+ Integer.toString((Integer) temp.get("hotel_country"))
					+ Integer.toString((Integer) temp.get("hotel_market"))
					+ Integer.toString((Integer) temp.get("hotel_cluster"));
			if (!hotelExistInNeo4j(hotelID)) {
				insertHotel(temp, hotelID);
//				System.out.println("create hotel: " + hotelID);
			}
			if ((Integer) temp.get("is_booking") == 1) {
				createRelationShip(temp.get("user_id"), hotelID, temp);
//				System.out.println("create relationship between: "
//				+ temp.get("user_id") + " and " + hotelID);
			}
		}
		session.close();
		driver.close();
	}

	public static void main(String[] args) {
		Neo4jClient client = new Neo4jClient();
	}

	private boolean userExistInNeo4j(Object userID) {
		String query = "MATCH (a:User) WHERE a.id = {id} RETURN a";
		params.clear();
		params.put("id", userID);
		StatementResult result = session.run(query, params);
		if (result.hasNext()) return true;
		else return false;
	}

	private boolean hotelExistInNeo4j(String hotelID) {
		String query = "MATCH (a:Hotel) WHERE a.id = {id} RETURN a";
		params.clear();
		params.put("id", hotelID);
		StatementResult result = session.run(query, params);
		if (result.hasNext()) return true;
		else return false;
	}

	private void insertUser(Document temp) {
		String query = "CREATE (a:User {id: {id}, country: {country}, "
				+ "city: {city}, person: {person}})";
		params.clear();
		params.put("id", temp.get("user_id"));
		params.put("country", temp.get("user_location_country"));
		params.put("city", temp.get("user_location_city"));
		params.put("person", (Integer) temp.get("srch_adults_cnt") + (Integer) temp.get("srch_children_cnt"));
		session.run(query, params);
	}

	private void insertHotel(Document temp, String hotelID) {
		String query = "CREATE (a:Hotel {id: {id}, country: {country}, "
				+ "market: {market}, cluster: {cluster}})";
		params.clear();
		params.put("id", hotelID);
		params.put("country", temp.get("hotel_country"));
		params.put("market", temp.get("hotel_market"));
		params.put("cluster", temp.get("hotel_cluster"));
		session.run(query, params);
	}

	private void createRelationShip(Object userID, String hotelID, Document temp) {
		String query = "MATCH (a:User), (b:Hotel) WHERE a.id = {i1} AND "
				+ "b.id = {i2} CREATE (a)-[r:BOOKING {site: {site}, "
				+ "channel: {channel}, mobile: {mobile}, package: {package}}]->(b) RETURN r";
		params.clear();
		params.put("i1", userID);
		params.put("i2", hotelID);
		params.put("site", temp.get("site_name"));
		params.put("channel", temp.get("channel"));
		params.put("mobile", temp.get("is_mobile"));
		params.put("package", temp.get("is_package"));
		session.run(query, params);
	}

}
