package com.scylladb.shop;
import java.util.*;
import com.datastax.driver.core.*;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.PlainTextAuthProvider;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;

public class ScyllaDAOImpl implements ScyllaDAO {

	private Session m_session = null;
	private Cluster m_cluster = null;
	
	public ScyllaDAOImpl(){
		
		System.out.println("INFO: Scylla DAO Implemenation has been instantiated.");
		
	}
	
	public void connect() {
		
				this.buildCluster();
				
		        System.out.println("INFO: Connected to cluster " + m_cluster.getMetadata().getClusterName());

		        for (Host host: m_cluster.getMetadata().getAllHosts()) {
		          System.out.printf("INFO: Datacenter: %s, State: %s, Rack: %s\n", host.getDatacenter(), host.getState() , host.getRack());
		        }

		        m_session = m_cluster.connect("shopping");
		        System.out.println("INFO: Connected to cluster " + m_cluster.getClusterName());

		        /*
		        session.execute("CREATE KEYSPACE IF NOT EXISTS shopping WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'AWS_US_EAST_1' : 3 }");
		        session.execute("USE shopping;");
		        session.execute("CREATE TABLE carts (id text PRIMARY KEY, cart list<frozen <tuple<text, text, float>>>, isCheckedOut boolean, totalCost float);");
		        */
		        

	}

	public ResultSet executeQuery(String CQL) {
		// TODO Auto-generated method stub
		System.out.println("INFO: ScyllaDAOImpl: executeQuery(): The CQL I received was: " + CQL);

		return m_session.execute(CQL);
		
	}

	public void execute(String CQL) {
		// TODO Auto-generated method stub
		
		// For now just PRINT the CQL so we can see what is going on:
		System.out.println("INFO: ScyllaDAOImpl: execute(): The CQL I received was: " + CQL);
		m_session.execute(CQL);

	}

	public void buildCluster() {

		m_cluster = Cluster.builder()
		        .addContactPoints("54.83.172.40", "52.70.103.151", "54.144.172.97")
		                .withLoadBalancingPolicy(DCAwareRoundRobinPolicy.builder().withLocalDc("AWS_US_EAST_1").build()) // your local data center
		                .withAuthProvider(new PlainTextAuthProvider("scylla", "yF36UqOkQErHs9L"))
		                .build();
	}

	public void displayResults(ResultSet rs) {
		 for (Row row : rs) {
             String id = row.getString("id");
             List<TupleValue> cart = row.getList("cart", TupleValue.class);
             float total = row.getFloat("total");
             System.out.println("\n" + id + " " + cart.toString() + " " + total);
		 }
	}

	public void displayTable(String t) {

		ResultSet rs = m_session.execute("SELECT * FROM " + t + ";");
		this.displayResults(rs);
	}

	public void closeSession() {
		m_session.close();
        m_cluster.close();
	}

}
