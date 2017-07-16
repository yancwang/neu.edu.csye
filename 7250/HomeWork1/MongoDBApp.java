package neu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBApp {

	public static void main(String[] args) {
		//create mongo connection
		MongoClient client = new MongoClient();
		MongoDatabase database = client.getDatabase("mydb");
		MongoCollection<Document> col = database.getCollection("movies");
		//read in movies.dat file
		List<Document> documents = new ArrayList<Document>();
		try {
			Scanner scanner = new Scanner(new File("movies.dat"));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
//				System.out.println("read in line: " + line);
				String[] columns = line.split("::");
				if (columns.length == 3) {
					Document document = new Document("MovieID", columns[0]).append("Title",
							columns[1]).append("Genres", columns[2]);
					documents.add(document);
				}
			}
			col.insertMany(documents);
			System.out.println("Finished inserting into movies");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//read in ratings.dat file
		col = database.getCollection("ratings");
		try {
			Scanner scanner = new Scanner(new File("ratings.dat"));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
//				System.out.println("read in line: " + line);
				String[] columns = line.split("::");
				if (columns.length == 4) {
					Document document = new Document("UserID", columns[0]).append("MovieID",
							columns[1]).append("Rating", columns[2]).append("Timestamp", 
									columns[3]);
					col.insertOne(document);
				}
			}
			System.out.println("Finished inserting into ratings");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//read in tags.dat file
		col = database.getCollection("tags");
		try {
			Scanner scanner = new Scanner(new File("tags.dat"));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
//				System.out.println("read in line: " + line);
				String[] columns = line.split("::");
				if (columns.length == 4) {
					Document document = new Document("UserID", columns[0]).append("MovieID",
							columns[1]).append("Tag", columns[2]).append("Timestamp", 
									columns[3]);
					col.insertOne(document);
				}
			}
			System.out.println("Finished inserting into tags");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
