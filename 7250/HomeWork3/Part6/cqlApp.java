package cassandra.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class cqlApp {

	public static void main(String[] args) {
		//connect to cassandra cluster
		Cluster cluster = Cluster.builder()
				.withClusterName("Test Cluster")
				.addContactPoint("127.0.0.1")
				.build();
		Session session = cluster.connect();
		//create keyspace
		session.execute("CREATE KEYSPACE IF NOT EXISTS ratings_cql WITH replication = {"
				+ "'class': 'SimpleStrategy', "
				+ "'replication_factor': '2'};");
		session.execute("use ratings_cql");
		//create table for BX-Book-Ratings.csv
		session.execute("CREATE TABLE ratings (UserID text PRIMARY KEY, ISBN text, "
				+ "BookRating text)");
		//read in file: BX-Book-Ratings.csv
		Scanner scanner;
		try {
			scanner = new Scanner(new File("BX-Book-Ratings.csv"));
			String column_line = scanner.nextLine();
			String[] column_name = column_line.split(";");
			//insert data
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] columns = line.split(";");
				if (columns.length == column_name.length) {
					String exe = "INSERT INTO ratings (";
					for (int i = 0; i < column_name.length; i++) {
						exe += column_name[i].replaceAll("\"", "").replaceAll("-", "");
						if (i != column_name.length - 1)
							exe += ",";
					}
					exe += ") VALUES (";
					for (int i = 0; i < columns.length; i++) {
						columns[i] = columns[i].replaceAll("'", "");
						exe += "'" + columns[i].substring(1, columns[i].length() - 1) 
								+ "'";
						if (i != columns.length - 1)
							exe += ",";
					}
					exe += ")";
					session.execute(exe);
				}
			}
			System.out.println("Finished inserting ratings");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//create table for BX-Book.csv
		session.execute("CREATE TABLE books (ISBN text PRIMARY KEY, BookTitle text, "
				+ "BookAuthor text, YearOfPublication text, Publisher text, "
				+ "ImageURLS text, ImageURLM text, ImageURLL text)");
		//read in file: BX-Book.csv
		try {
			scanner = new Scanner(new File("BX-Book.csv"));
			String column_line = scanner.nextLine();
			String[] column_name = column_line.split(";");
			//insert data
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] columns = line.split(";");
				if (columns.length == column_name.length) {
					String exe = "INSERT INTO books (";
					for (int i = 0; i < column_name.length; i++) {
						exe += column_name[i].replaceAll("\"", "").replaceAll("-", "");
						if (i != column_name.length - 1)
							exe += ",";
					}
					exe += ") VALUES (";
					for (int i = 0; i < columns.length; i++) {
						columns[i] = columns[i].replaceAll("'", "");
						exe += "'" + columns[i].substring(1, columns[i].length() - 1) 
								+ "'";
						if (i != columns.length - 1)
							exe += ",";
					}
					exe += ")";
					session.execute(exe);
				}
			}
			System.out.println("Finished inserting books");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//create table for BX-Users.csv
		session.execute("CREATE TABLE users (UserID text PRIMARY KEY, "
				+ "Location text, Age text)");
		//read in file: BX-Users.csv
		try {
			scanner = new Scanner(new File("BX-Users.csv"));
			String column_line = scanner.nextLine();
			String[] column_name = column_line.split(";");
			//insert data
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] columns = line.split(";");
				if (columns.length == column_name.length) {
					String exe = "INSERT INTO users (";
					for (int i = 0; i < column_name.length; i++) {
						exe += column_name[i].replaceAll("\"", "").replaceAll("-", "");
						if (i != column_name.length - 1)
							exe += ",";
					}
					exe += ") VALUES (";
					for (int i = 0; i < columns.length; i++) {
						columns[i] = columns[i].replaceAll("'", "");
						exe += "'" + columns[i].substring(1, columns[i].length() - 1) 
								+ "'";
						if (i != columns.length - 1)
							exe += ",";
					}
					exe += ")";
					session.execute(exe);
				}
			}
			System.out.println("Finished inserting users");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		cluster.close();
	}

}
