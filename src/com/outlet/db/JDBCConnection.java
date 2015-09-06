package com.outlet.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnection
{
	public static Connection getConnection()
	{
		try
		{
			return getConnection(DbInit.DB_NAME);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	public static Connection getConnection(String dbName) throws InstantiationException, IllegalAccessException
	{
		Connection connection = null;
		if(connection == null)
		{
			try
			{
				Class.forName("org.sqlite.JDBC");
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		    try
		    {
		      // create a database connection
		      connection = DriverManager.getConnection("jdbc:sqlite:"+ dbName +".db");
		    }
		    catch(SQLException e)
		    {
		      e.printStackTrace();
		      try
		      {
		    	  connection.close();
		      }
		      catch(SQLException ex)
		      {
		    	  ex.printStackTrace();;
		      }
		    }
		}   
	return connection;
  }
}

