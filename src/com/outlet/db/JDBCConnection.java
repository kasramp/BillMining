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

import com.j256.ormlite.jdbc.JdbcConnectionSource;

public class JDBCConnection
{
	public static JdbcConnectionSource getConnection()
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
	public static Connection getJdbcConnection() {
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
				connection = DriverManager.getConnection("jdbc:sqlite:"+ DbInit.DB_NAME +".db");
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}   
		return connection;
	}
	public static JdbcConnectionSource getConnection(String dbName) throws InstantiationException, IllegalAccessException
	{
		JdbcConnectionSource connection = null;
		if(connection == null)
		{
			/*try
			{
				Class.forName("org.sqlite.JDBC");
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}*/
			try
			{
				// create a database connection
				connection = new JdbcConnectionSource("jdbc:sqlite:"+ dbName +".db");
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}   
		return connection;
	}
}

