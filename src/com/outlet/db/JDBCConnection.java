/*
* This file is part of BillMining.
* 
* BillMining is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License version 3
* as published by the Free Software Foundation.
*
* BillMining is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.  <http://www.gnu.org/licenses/>
*
* Author(s):
*
* © 2015 Kasra Madadipouya <kasra@madadipouya.com>
*/

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

