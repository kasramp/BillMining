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

package com.outlet.common;
import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.outlet.db.JDBCConnection;
import com.outlet.objects.BillMining;
import com.outlet.objects.Category;
import com.outlet.objects.Company;
import com.outlet.objects.Item;
import com.outlet.objects.Outlet;

public class Utilities
{
	public static boolean isNullOrEmpty(String str)
	{
		if(str == null || str.isEmpty() || str.length() < 1)
		{
			return true;
		}
		return false;
	}
	
	public static String convertSqlFileToString(String fileName)
	{
		BufferedReader br = null; 
		StringBuilder sb = null;
	    try 
	    {
	    	br = new BufferedReader(new FileReader(fileName));
	        sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) 
	        {
	            sb.append(line);
	            line = br.readLine();
	        }
	    }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    }
	    finally 
	    {
	    	try
	    	{
	    		br.close();
	    	}
	    	catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    	}
	    }
	    return sb.toString();
	}
	public static void executeSqlQuery(String sqlStatement) throws Exception
	{
		Connection connection = null;
		connection = JDBCConnection.getConnection();
		
		Statement statement = null;
		Integer createdPkid = new Integer(0);
		try
		{
			statement = connection.createStatement();
			statement.executeUpdate(sqlStatement);
			/*statement.executeUpdate(sqlStatement, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = statement.getGeneratedKeys();
			if(rs.next()) {
				createdPkid = new Integer (rs.getInt(1));
				return createdPkid;
			} else {
				return createdPkid; 
			}*/
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			try
			{
				statement.close();
				//connection.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				throw ex;
			}
		}
	}
	public static Integer getLastPkid(String tableName) {
		try {
			ArrayList<LinkedHashMap> rst = (ArrayList<LinkedHashMap>) 
					Utilities.executeDirectSelectSql("SELECT MAX(pkid) AS last_row From " + tableName);
			LinkedHashMap map = rst.get(0);
			String lastRow = map.get("last_row").toString();
			return new Integer(lastRow);
		} catch(Exception ex) {
			ex.printStackTrace();
			return new Integer(-1);
		}
	}
	public static <T> String[] convertToCSV(T obj, String... opts)
	{
		String delimiter = ",";
		if(opts != null && opts.length > 0 && opts[0] != null && !opts[0].isEmpty())
		{
			delimiter = opts[0];
		}
		String[] csvString = new String[1];
		try {
			if(obj.getClass() == Object[].class) {
				
				Object[] data = (Object[]) obj;
				for(int i = 0; i<data.length;i++){ 
					if(i == 0) {
						csvString[0] = data[i].toString();
					} else {
						csvString[0] += delimiter + data[i].toString();
					}
				}
			} else if(obj.getClass() == List.class || obj.getClass() == ArrayList.class) {
				ArrayList<?> data = (ArrayList<?>) obj;
				int i = 0;
				for(Object e: data){
					if(i++ == 0){
						csvString[0] = e.toString();
					} else {
						csvString[0] += delimiter + e.toString();
					}
				}
			} else if(obj.getClass() == TreeMap.class) {
				csvString = new String[2];
				TreeMap<?,?> data = (TreeMap<?,?>) obj;
				int i = 0;
				for(Map.Entry<?,?> entry : data.entrySet()) {
					  //String key = entry.getKey().toString();
					  if(i++ == 0) {
						  csvString[0] = entry.getKey().toString();
						  csvString[1] = entry.getValue().toString();
					  } else {
						  csvString[0] += delimiter + entry.getKey().toString();
						  csvString[1] += delimiter + entry.getValue().toString();
					  }
			   } 
			} else if(obj.getClass() == Category.class) {
				String isPkid = opts[1];
				boolean hasPkid = false;
				Category  cat = (Category) obj;
				if(!Utilities.isNullOrEmpty(isPkid)) {
					if(isPkid.equalsIgnoreCase("true")) {
						csvString[0] = cat.getPkid() + ",";
						hasPkid = true;
					}
				}
				if(hasPkid) {
					csvString[0] += cat.getCategoryCode() + "," + cat.getCategoryName() + "," + cat.getCategoryDesc();
				} else {
					csvString[0] = cat.getCategoryCode() + "," + cat.getCategoryName() + "," + cat.getCategoryDesc();
				}
			} else if(obj.getClass() == Item.class) {
				String isPkid = opts[1];
				boolean hasPkid = false;
				boolean categoryConvert = false;
				Item  itm = (Item) obj;
				if(!Utilities.isNullOrEmpty(isPkid)) {
					if(isPkid.equalsIgnoreCase("true")) {
						csvString[0] = itm.getPkid() + ",";
						hasPkid = true;
					}
				}
				if(!Utilities.isNullOrEmpty(opts[2])) {
					if(opts[2].equalsIgnoreCase("convertCategory")) {
						categoryConvert = true;
					}
				}
				if(hasPkid) {
					if(categoryConvert) {
						Category cat = Category.getObject(itm.getCategoryId());
						csvString[0] += itm.getItemCode() + "," + itm.getItemName() + "," +itm.getMaCost() + 
						"," + cat.getCategoryName() + "(" + cat.getCategoryCode() + ")";
					} else {
						csvString[0] += itm.getItemCode() + "," + itm.getItemName() + "," +itm.getMaCost() + 
						"," + itm.getCategoryId();
					}
					//csvString[0] += cat.getCategoryCode() + "," + cat.getCategoryName() + "," + cat.getCategoryDesc();
				} else {
					if(categoryConvert) {
						Category cat = Category.getObject(itm.getCategoryId());
						csvString[0] = itm.getItemCode() + "," + itm.getItemName() + "," +itm.getMaCost() + 
						"," + cat.getCategoryName() + "(" + cat.getCategoryCode() + ")";
					} else {
						csvString[0] = itm.getItemCode() + "," + itm.getItemName() + "," +itm.getMaCost() + 
						"," + itm.getCategoryId();
					}
				} 
			} else if(obj.getClass() == Outlet.class) {
					String isPkid = opts[1];
					boolean hasPkid = false;
					boolean companyConvert = false;
					Outlet  outlet = (Outlet) obj;
					if(!Utilities.isNullOrEmpty(isPkid)) {
						if(isPkid.equalsIgnoreCase("true")) {
							csvString[0] = outlet.getPkid() + ",";
							hasPkid = true;
						}
					}
					if(!Utilities.isNullOrEmpty(opts[2])) {
						if(opts[2].equalsIgnoreCase("convertCompany")) {
							companyConvert = true;
						}
					}
					if(hasPkid) {
						if(companyConvert) {
							Company comp = Company.getObject(outlet.getCompanyPkid());
							csvString[0] += outlet.getOutletCode() + "," + outlet.getOutletName()
							+ "," + outlet.getOutletAddress() + "," + comp.getCompanyName() + " ("
							+ comp.getCompanyCode() + ")," + outlet.getDateCreated();
							
						} else {
							csvString[0] += outlet.getOutletCode() + "," + outlet.getOutletName()
							+ "," + outlet.getOutletAddress() + "," + outlet.getCompanyPkid() +","+ outlet.getDateCreated();
						}
					} else {
						if(companyConvert) {
							Company comp = Company.getObject(outlet.getCompanyPkid());
							csvString[0] = outlet.getOutletCode() + "," + outlet.getOutletName()
							+ "," + outlet.getOutletAddress() + "," + comp.getCompanyName() + " ("
							+ comp.getCompanyCode() + ")," + outlet.getDateCreated();
						} else {
							csvString[0] = outlet.getOutletCode() + "," + outlet.getOutletName()
							+ "," + outlet.getOutletAddress() + "," + outlet.getCompanyPkid() +","+ outlet.getDateCreated();
						}
					}
			} else if(obj.getClass() == Company.class) {
				String isPkid = opts[1];
				boolean hasPkid = false;
				Company  comp = (Company) obj;
				if(!Utilities.isNullOrEmpty(isPkid)) {
					if(isPkid.equalsIgnoreCase("true")) {
						csvString[0] = comp.getPkid() + ",";
						hasPkid = true;
					}
				}
				if(hasPkid) {
					csvString[0] += comp.getCompanyCode() + "," + comp.getCompanyName() + "," + comp.getDateCreated();
				} else {
					csvString[0] = comp.getCompanyCode() + "," + comp.getCompanyName() + "," + comp.getDateCreated();
				}
			} else if(obj.getClass() == DocRow.class) {
				String includeAddRemove = opts[1];
				boolean addRemove = false;
				DocRow  row = (DocRow) obj;
				if(!Utilities.isNullOrEmpty(includeAddRemove)) {
					if(includeAddRemove.equalsIgnoreCase("true")) {
						addRemove = true;
					}
				}
				csvString[0] = row.getItemCode() + "," + row.getItemName() + "," + row.getQty() + "," + row.getItemCost();
				if(addRemove) {
					csvString[0] += ", Edit, Remove";
				}
			} else if(obj.getClass() == BillMining.RptRow.class) {
				BillMining.RptRow oneRow = (BillMining.RptRow) obj;
				csvString[0] = oneRow.itemId + "," + oneRow.itemCode + "," + oneRow.itemName + "," + oneRow.optimalPrice
				+ "," + oneRow.billNo + "," + oneRow.companyNameCode + "," + oneRow.outletNameCode + "," + oneRow.outletAddress 
				+ "," + oneRow.dateOfPurchase + "," +oneRow.frequencyOfPurchase + "," + oneRow.fopWithOptimalPrice + "," + oneRow.lastAppearance;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvString;
	}
	/*public static String[] mapToKeyValueCSV(TreeMap<String,String> contents)
	{
		String[] keyValue = new String[2];
		int i=0;
		for(Map.Entry<String,String> entry : contents.entrySet()) 
		{
			if(i++ == 0)
			{
				keyValue[0] = entry.getKey();
				keyValue[1] = entry.getValue();
			}
			else
			{
				keyValue[0] += "," + entry.getKey();
				keyValue[1] += "," + entry.getValue();
			}
		}
		return keyValue;
	}*/
	public static boolean executeInsertSql(String tableName, TreeMap<String,String> contents)
	{
		Connection connection = null;
		connection = JDBCConnection.getConnection();
		Statement statement = null;
		boolean isSuccessful = false;
		//String[] keyValue = new String[2];
		String[] keyValue  =   convertToCSV(contents);//mapToKeyValueCSV(contents/*,keyValue*/);
		String sqlStatement = "INSERT INTO " + tableName;
		sqlStatement += " (" + keyValue[0] + ")";
		sqlStatement += " VALUES (" + keyValue[1] + ")";
		try
		{
			statement = connection.createStatement();
			statement.executeUpdate(sqlStatement);
			isSuccessful = true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			isSuccessful = false;
		}
		finally
		{
			try
			{
				statement.close();
				//connection.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return isSuccessful;
	}
	// Execute Update statement
	public static boolean executeUpdateSql(String tableName, TreeMap<String,String> contents, String condition)
	{
		Connection connection = null;
		connection = JDBCConnection.getConnection();
		Statement statement = null;
		boolean isSuccessful = false;
		//String[] keyValue = new String[2];
		String[] keyValue  =   convertToCSV(contents);//mapToKeyValueCSV(contents/*,keyValue*/);
		String sqlStatement = "UPDATE " + tableName + " SET ";
		String[] columns = keyValue[0].split(",");
		String[] values = keyValue[1].split(",");
		for(int i=0;i<columns.length;i++)
		{
			if(i == 0)
			{
				sqlStatement += columns[i] + " = " + values[i];
			}
			else
			{
				sqlStatement += "," + columns[i] + " = " + values[i];
			}
		}
		if(!Utilities.isNullOrEmpty(condition))
		{
			sqlStatement += " WHERE " + condition;
		}
		try
		{
			statement = connection.createStatement();
			statement.executeUpdate(sqlStatement);
			isSuccessful = true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			isSuccessful = false;
		}
		finally
		{
			try
			{
				statement.close();
				//connection.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return isSuccessful;
	}
	public static List<TreeMap> executeDirectSelectSql(String query, String... requiredFields)
	{
		Connection connection = null;
		connection = JDBCConnection.getConnection();
		Statement statement = null;
		ResultSet rs = null;
		List<TreeMap> result = new ArrayList<TreeMap>();
		try {
		// If required fields are not empty or exists
		if(requiredFields != null && requiredFields.length > 0 && requiredFields[0] != null && !requiredFields[0].isEmpty())
		{
			String[] strLength = requiredFields[0].split(",");
			for(int i=0;i<strLength.length;i++) {
				strLength[i] = strLength[i].replaceAll(" ","");
			}
			statement  = connection.createStatement();
			rs = statement.executeQuery(query);
			while(rs.next())
			{
				//Object[] oneRow = new Object[strLength.length];
				TreeMap oneRow = new TreeMap();
				for(int i=0;i<strLength.length;i++)
				{
					//oneRow[i] = rs.getObject(strLength[i]);
/*					if("image".equalsIgnoreCase(strLength[i])){
						oneRow.put(strLength[i],rs.getBytes(strLength[i]));
					}else {
*/						oneRow.put(strLength[i], rs.getObject(strLength[i]));
//					}
				}
				result.add(oneRow);
			}
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				statement.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return result;
		
	}
	public static Collection executeDirectSelectSql(String query) throws Exception{
		Connection connection = null;
		connection = JDBCConnection.getConnection();
		Statement statement = null;
		ResultSet rs = null;
		Collection result = new ArrayList();
		try {
			statement  = connection.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()) 
		 	{	
				LinkedHashMap treeColumn = new LinkedHashMap();
		 	    
		 	    ResultSetMetaData rsMetaData = rs.getMetaData();
			    int numberOfColumns = rsMetaData.getColumnCount();
			    // get the column names; column indexes start from 1
			    for (int i = 1; i < numberOfColumns + 1; i++) 
			    {
			    	String columnName = rsMetaData.getColumnName(i);
			    	String className =  rsMetaData.getColumnClassName(i);
			    	Object obj = rs.getObject(columnName);
			    	if (className.equals("java.math.BigDecimal") && obj!=null)
			    	{
			    		BigDecimal n = (BigDecimal) obj;
			    		if (n.signum()==0) n = new BigDecimal(0);
			    		treeColumn.put(columnName, n);
			    	}
			    	else
			    	{
			    		treeColumn.put(columnName, obj);
			    	}
			    }
		 		result.add(treeColumn);
		 	}
		} catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return result;
	}
	/*public static List<Object[]> executeDirectSelectSql(String query)
	{
		Connection connection = null;
		connection = JDBCConnection.getConnection();
		Statement statement = null;
		ResultSet rs = null;
		List<Object[]> result = new ArrayList<Object[]>();
		try {
			  statement  = connection.createStatement();
			  rs = statement.executeQuery(query);
		      ResultSetMetaData meta = rs.getMetaData();
		      int columnCount = meta.getColumnCount();
		      while(rs.next())
		      {
		    	  Object[] oneRow = new Object[columnCount];
		    	  for (int j = 1; j <= columnCount; j++) {
		    		  oneRow[j-1] = rs.getObject(j);
		          }
		    	  result.add(oneRow);
		      }
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				statement.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return result;
	}*/
	public static Timestamp getTime(String time) {
		Timestamp timestamp = null;
		try {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		    java.util.Date parsedDate = dateFormat.parse(time);
		    timestamp = new java.sql.Timestamp(parsedDate.getTime());
		} catch(Exception e){//this generic but you can control another types of exception
			e.printStackTrace();
		}
		return timestamp;
	}
	public static Timestamp getCurrentTime()
	{
		Calendar calendar = Calendar.getInstance();	 
		java.util.Date now = calendar.getTime();
		Timestamp currentTimestamp = new Timestamp(now.getTime());
		return currentTimestamp;
	}
}
