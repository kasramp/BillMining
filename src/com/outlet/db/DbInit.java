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
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import com.outlet.common.Utilities;
import com.outlet.db.JDBCConnection;
public class DbInit
{
	// 	Connect to local dbs
	private Connection connection = null;
	public static final String DB_NAME = "OUTLET_PURCHASE";
	public DbInit() throws Exception
	{
		try {
			initializeDb();
			createTables();
		} catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	private void initializeDb()
	{
		try
		{
			connection = JDBCConnection.getConnection(DB_NAME);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	private void createTables() throws Exception
	{
		File folder = new File(".\\sql");
		File[] listOfFiles = folder.listFiles();
		try
		{
			if(listOfFiles == null || listOfFiles.length ==0) {
				throw new FileNotFoundException();
			}
		    for (int i = 0; i < listOfFiles.length; i++) 
		    {
		    	if (listOfFiles[i].isFile())
		    	{
		    		System.out.println(listOfFiles[i].toString());
		    		String fileContent = Utilities.convertSqlFileToString(listOfFiles[i].toString());
		    		Utilities.executeSqlQuery(fileContent);
		    	}
		    	// If it is stored in directory
		    	/*else if (listOfFiles[i].isDirectory()) 
		      	{
		        	System.out.println("Directory " + listOfFiles[i].getName());
		      	}*/
		    }
		}
		catch(FileNotFoundException fex) {
			try {
				DbInit.createTablesManually();
			} catch(Exception ex) {
				ex.printStackTrace();
				throw ex;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	private static synchronized void createTablesManually() throws Exception{
		try {
			String billIndex = "CREATE TABLE IF NOT EXISTS bill_index (" +
					"pkid INTEGER PRIMARY KEY AUTOINCREMENT," +
					"company_pkid INTEGER NOT NULL," +
					"outlet_pkid INTEGER NOT NULL," +
					"bill_amount DECIMAL NOT NULL," +
					"gst_amount DECIMAL NOT NULL," +
					"total_amount DECIMAL NOT NULL," +
					"date_created TEXT NOT NULL);";
			Utilities.executeSqlQuery(billIndex);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		try {
			String billItem = "CREATE TABLE IF NOT EXISTS bill_item (" +
					"pkid INTEGER PRIMARY KEY AUTOINCREMENT," +
					"bill_index_pkid INTEGER NOT NULL," +
					"item_id INTEGER NOT NULL," +
					"qty INTEGER NOT NULL," +
					"price DECIMAL NOT NULL," +
					"gst_amount DECIMAL NOT NULL);";
			Utilities.executeSqlQuery(billItem);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		try {
			String billMining = "CREATE TABLE IF NOT EXISTS bill_mining (" +
					"guid TEXT NOT NULL, " +
					"item_id INTEGER NOT NULL," +
					"price DECIMAL NOT NULL," +
					"outlet_pkid INTEGER NOT NULL," +
					"date_of_suggestion TEXT NOT NULL" +
					");";
			Utilities.executeSqlQuery(billMining);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		
		try {
			String category = "CREATE TABLE IF NOT EXISTS category ("+
					"pkid INTEGER PRIMARY KEY AUTOINCREMENT,"+
					"category_code TEXT NOT NULL," +
					"category_name TEXT NOT NULL," +
					"category_description TEXT NOT NULL" +
					");";
			Utilities.executeSqlQuery(category);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		try {
			String company = "CREATE TABLE IF NOT EXISTS company (" +
					"pkid INTEGER PRIMARY KEY AUTOINCREMENT," +
					"company_name TEXT NOT NULL," +
					"company_code TEXT NOT NULL," +
					"date_created TEXT NOT NULL" +
					");";
			Utilities.executeSqlQuery(company);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		
		try {
			String item = "CREATE TABLE IF NOT EXISTS item (" +
					"pkid INTEGER PRIMARY KEY AUTOINCREMENT," +
					"item_code TEXT NOT NULL," +
					"item_name TEXT NOT NULL," +
					"ma_cost DECIMAL NOT NULL," +
					"category_id INTEGER NOT NULL," +
					"image BLOB);";
			Utilities.executeSqlQuery(item);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		
		try {
			String outlet = "CREATE TABLE IF NOT EXISTS outlet (" +
					"pkid INTEGER PRIMARY KEY AUTOINCREMENT," +
					"company_pkid INTEGER NOT NULL,"+
					"outlet_name TEXT NOT NULL,"+
					"outlet_code TEXT NOT NULL,"+
					"outlet_address TEXT NOT NULL,"+
					"date_created TEXT NOT NULL"+
					");";
			Utilities.executeSqlQuery(outlet);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		
	}
}