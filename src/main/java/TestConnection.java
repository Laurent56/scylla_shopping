
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.*;

public class TestConnection {

	public static void main(String[] args) {

		TestConnection tc = new TestConnection();
		
		tc.connect_and_query();
	}

	public void connect_and_query() {
        Cluster cluster = Cluster.builder()
        .addContactPoints("54.83.172.40", "52.70.103.151", "54.144.172.97") // ScyllaDB Cloud
                .withLoadBalancingPolicy(DCAwareRoundRobinPolicy.builder().withLocalDc("AWS_US_EAST_1").build()) // your local data center
                .withAuthProvider(new PlainTextAuthProvider("scylla", "yF36UqOkQErHs9L"))
                .build();

        System.out.println("Connected to cluster " + cluster.getMetadata().getClusterName());

        for (Host host: cluster.getMetadata().getAllHosts()) {
          System.out.printf("Datacenter: %s, State: %s, Rack: %s\n", host.getDatacenter(), host.getState() , host.getRack());
        }

        Session session = cluster.connect("system");
        System.out.println("Connected to cluster " + cluster.getClusterName());
        //ResultSet resultSet = session.execute("SELECT * FROM system.clients LIMIT 10"); // some query

        //session.execute("CREATE KEYSPACE IF NOT EXISTS shopping WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'AWS_US_EAST_1' : 3 }");
        session.execute("USE shopping;");
        //session.execute("DROP TABLE shopping.carts;");
        //session.execute("CREATE TABLE carts (id text PRIMARY KEY, cart list<frozen <tuple<text, text, float>>>, total float, isCheckedOut boolean);");
        System.out.println(session.execute("SELECT * FROM carts;"));
        session.close();
        cluster.close();
    }
	
	public void connect() {
		
		Cluster cluster = Cluster.builder().addContactPoints("54.83.172.40", "52.70.103.151", "54.144.172.97").build();

		Session session = cluster.connect("catalog");
		
		System.out.print("\n\nDisplaying Results:");
		
	       ResultSet results = session.execute("SELECT * FROM catalog.mutant_data");
	       
	       for (Row row : results) {
	               String first_name = row.getString("first_name");
	               String last_name = row.getString("last_name");
	               System.out.print("\n" + first_name + " " + last_name);
	       }
	}
	
}
