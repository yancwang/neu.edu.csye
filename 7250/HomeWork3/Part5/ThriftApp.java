package cassandra.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.KsDef;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class ThriftApp {

	public static void main(String[] args) {
		try {
			//connect to cassandra
			TSocket socket = new TSocket("localhost", 9160);
			TTransport transport = new TFramedTransport(socket);
			TProtocol proto = new TBinaryProtocol(transport);
			transport.open();
			Cassandra.Client client = new Cassandra.Client(proto);
			//drop keyspace if exists
			String keySpace = "ratings_thrift";
			for (KsDef k : client.describe_keyspaces()) {
				if (k.getName().equals(keySpace)) 
					client.system_drop_keyspace(keySpace);
			}
			//create keyspace
			KsDef ksdef = new KsDef();
			ksdef.setName(keySpace);
			Map<String, String> strategy = new HashMap<String, String>();
			strategy.put("class", "SimpleStrategy");
			strategy.put("replication_factor", String.valueOf(1));
			ksdef.setStrategy_class("org.apache.cassandra.locator.SimpleStrategy");
			ksdef.setStrategy_options(strategy);
			//create column family
			CfDef cfdef = new CfDef();
			cfdef.setKeyspace(ksdef.getName());
			cfdef.setName("rcf");
			ksdef.addToCf_defs(cfdef);
			//connect to cassandra to create keyspace and column family
			client.system_add_keyspace(ksdef);
			//create columns
			ColumnParent parent = new ColumnParent();
			parent.setColumn_family(cfdef.getName());
			ColumnPath path = new ColumnPath();
			path.setColumn_family(cfdef.getName());
			path.setColumn("UserID".getBytes("UTF-8"));
			path.setColumn("ISBN".getBytes("UTF-8"));
			path.setColumn("BookRating".getBytes("UTF-8"));
			//create column
			client.set_keyspace(ksdef.getName());
			//create user key id
			long keyUserId = 0;
			//read in file: BX-Book-Ratings.csv
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(new File("BX-Book-Ratings.csv"));
			scanner.nextLine();
			//insert data
			while (scanner.hasNextLine()) {
				byte[] key = ((keyUserId++) + "").getBytes("UTF-8");
				ByteBuffer keyBuf = ByteBuffer.wrap(key);
				String line = scanner.nextLine();
				String[] columns = line.split(";");
				if (columns.length == 3) {
					for (int i = 0; i < columns.length; i++) {
						Column c = new Column();
						if (i == 0) c.setName("UserID".getBytes("UTF-8"));
						else if (i == 1) c.setName("ISBN".getBytes("UTF-8"));
						else if (i == 2) c.setName("BookRating".getBytes("UTF-8"));
						c.setTimestamp(System.currentTimeMillis());
						columns[i] = columns[i].replaceAll("'", "");
						columns[i] = "'" + columns[i].substring(1, columns[i].length()
								- 1) + "'";
						c.setValue(columns[i].getBytes("UTF-8"));
						client.insert(keyBuf, parent, c, ConsistencyLevel.ALL);
					}
				}
			}
			transport.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			e.printStackTrace();
		} catch (UnavailableException e) {
			e.printStackTrace();
		} catch (TimedOutException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
