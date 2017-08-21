package neu.edu.neo4j;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public class Neo4jRecommendation {

	public static void main(String[] args) {
		Driver driver = GraphDatabase
				.driver( "bolt://localhost:7687", AuthTokens.basic("neo4j", "1"));
		Session session = driver.session();
		Map<String, Object> params = new HashMap<String, Object>();
		
		String query = "MATCH (a:User)-[:BOOKING]->(hotel1)<-[:BOOKING]-(b:User)"
				+ "WHERE a.id = {id} RETURN b.id, count(distinct b) as frequency "
				+ "ORDER BY frequency DESC LIMIT 5";
		params.clear();
		params.put("id", 344735);
		
		StatementResult result = session.run(query, params);
		while (result.hasNext()) {
			System.out.println(result.next().toString());
		}
	}

}
