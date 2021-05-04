// All code by Laurent Weichberger ompoint@gmail.com

package com.scylladb.shop;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public interface ScyllaDAO {
	
	// Connect to the ScyllaDB cluster
	public void connect();
	
	// For CQL which returns a ResultSet
	public ResultSet executeQuery(String CQL); 
	
	// For all other CQL
	public void execute(String CQL);
	
	// Create the Cluster object
	public void buildCluster();
	
	// Show the contents of a ResultSet
	public void displayResults(ResultSet rs);
	
	//Show the contents of a Table
	public void displayTable(String t);
	
	// Close the session when done
	public void closeSession();

}
