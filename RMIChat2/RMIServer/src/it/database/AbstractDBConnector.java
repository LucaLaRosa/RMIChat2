package it.database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDBConnector {

	/**
	 * Proprietà di un oggetto di interfacciarsi ad un database:
	 * - createConnection();
	 * - disconnect();
	 */
	
    	private static final String dbURL = "jdbc:derby://127.0.0.1:1527/mydb";

		protected static java.sql.Connection getConnection()
	    {
			java.sql.Connection connection = null;
			
	        try
	        {
	            Class.forName("org.apache.derby.jdbc.ClientDriver");
	            //Get a connection
	            connection = DriverManager.getConnection(dbURL);
	            
	        }
	        catch (Exception except)
	        {
	            except.printStackTrace();
	        }
			return connection;
	    }
	   
	    protected static void disconnect(java.sql.Connection connection, Statement statement, ResultSet resultSet)
	    {
	    	try
	    	{
	    		if (resultSet != null)
	    		{
	    			resultSet.close();
	    		}
	    		if (connection != null)
	    		{
	    			connection.close();
	    		}      
	    		if(statement != null) {
	    			statement.close();
	    		}
	    	}
	    	catch (SQLException sqlExcept)
	    	{
	    		sqlExcept.getMessage();
	    		System.err.println("ATTENZIONE: Il database potrebbe essersi chiuso male.");
	    		sqlExcept.printStackTrace();
	    		
	    	}

	    }
	    
	    protected static void disconnect(java.sql.Connection connection, Statement statement) {
	    	disconnect(connection, statement, null);
	    	
	    }
	    
	    protected static void disconnect(java.sql.Connection connection) {
	    	disconnect(connection, null, null);
	    	
	    }
	

	
	
	
}
