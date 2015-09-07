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

package com.outlet.objects;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.outlet.common.Utilities;
import com.outlet.db.JDBCConnection;

public class Item {
	public static final String TABLE_NAME = "item";
	public static final String FIELDS = "pkid, item_code, item_name, ma_cost, category_id, image";
	public static final String HEADER_FIELDS = "No #, Pkid, Item Code, Item Name, Ma Cost, Catrgory Name (Code), Image";
	public static final String FIELDS_NO_PKID = FIELDS.substring(5);
	private Integer pkid;
	private String itemCode;
	private String itemName;
	private BigDecimal maCost;
	private Integer categoryId;
	private byte[] image;
	///////////////////////////
	public Item() {
		this.pkid = new Integer(0);
		this.itemCode = "";
		this.itemName = "";
		this.maCost = new BigDecimal(0);
		this.categoryId = new Integer(0);
		this.image = null;
	}
	public Integer getPkid() {
		return pkid;
	}
	public void setPkid(Integer pkid) {
		this.pkid = pkid;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public BigDecimal getMaCost() {
		return maCost;
	}
	public void setMaCost(BigDecimal maCost) {
		this.maCost = maCost;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	// Implementing Nut
	/*public static Item getObject(Integer pkid)
	{
		Item itm = new Item();
		try {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE "
				+ "pkid = " + pkid;
		//String fields = "pkid, company_pkid, outlet_pkid, bill_amount, gst_amount, total_amount, date_created";
		List<TreeMap> result = new ArrayList<TreeMap>();
		result = Utilities.executeDirectSelectSql(query, FIELDS);
		for(int i=0;i<result.size();i++) {
			TreeMap oneRow = result.get(i);
			itm = mapValues(oneRow);
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return itm;
	}*/
	public static Item getObject(Integer pkid) {
		try {
			List<Item> itemtLst = Item.getObjects("pkid = " + pkid);
			if(itemtLst != null && ! itemtLst.isEmpty()) {
				return itemtLst.get(0);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public static Item getObject(String itemCode) {
		try {
			List<Item> itemtLst = Item.getObjects("item_code = '" + itemCode + "'");
			if(itemtLst != null && ! itemtLst.isEmpty()) {
				return itemtLst.get(0);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public static List<Item> getObjects(String conditions)
	{
		List<Item> rtnResult = new ArrayList<Item>();
		try {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + conditions;
		List<TreeMap> result = new ArrayList<TreeMap>();
		result = Utilities.executeDirectSelectSql(query, FIELDS);
		for(int i=0;i<result.size();i++) {
			TreeMap oneRow = result.get(i);
			Item itm = new Item();
			itm = mapValues(oneRow);
			rtnResult.add(itm);
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return rtnResult;
	}
	private static Item mapValues(TreeMap oneRow) {
		Item itm = new Item();
		try {
		if(oneRow == null || oneRow.isEmpty()){
			return null;
		}
		itm.pkid = (Integer) oneRow.get("pkid");
		itm.itemCode = (String) oneRow.get("item_code");
		itm.itemName = (String) oneRow.get("item_name");
		try {
			itm.maCost = new BigDecimal((Double)oneRow.get("ma_cost"));
		} catch (Exception ex) {
			itm.maCost = new BigDecimal((Integer)oneRow.get("ma_cost"));
		}
		itm.categoryId = (Integer) oneRow.get("category_id");
		try {
			itm.image = (byte[]) oneRow.get("image");
			//itm.image = (File) oneRow.get("image");
		} catch(Exception ex) {
			itm.image = null;
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return itm;
	}
	public static void setObject(Item itmObj) throws Exception{
		try {
			/*String values = getValuesInString(itmObj);
			String query = "INSERT INTO " + TABLE_NAME + " (" + FIELDS_NO_PKID + ") VALUES (" + values + ")";
			Utilities.executeSqlQuery(query);*/
			// Adding Image Blob
			String query = "INSERT INTO " + TABLE_NAME + " (" + FIELDS_NO_PKID + ") VALUES (";
			for(int i=0;i<FIELDS_NO_PKID.split("[,]").length;i++) {
				if(i == 0) {
					query += "?";
				} else {
					query += ",?";
				}
			}
			query += ")";
			Item itm = new Item();
			itm.insertObjectToDb(query, itmObj);
		} catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
	private static String getValuesInString(Item itmObj) {
		String values = "";
		try {
			values += "'" + itmObj.itemCode + "',";
			values += "'" + itmObj.itemName + "',";
			values += itmObj.maCost + ",";
			values += itmObj.categoryId;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return values;
	}
	private void insertObjectToDb(String sqlStatement, Item itmObj) throws Exception
	{
		Connection connection = null;
		connection = JDBCConnection.getConnection();
		PreparedStatement ps = null;
		//InputStream is = null;
		//Statement statement = null;
		try
		{
			ps = connection.prepareStatement(sqlStatement);
			ps.setString(1,itmObj.itemCode);
			ps.setString(2,itmObj.itemName);
			ps.setBigDecimal(3, itmObj.maCost);
			ps.setInt(4, itmObj.categoryId);
			try {
			//is = new ByteArrayInputStream(itmObj.image);
			ps.setBytes(5, itmObj.image);
			} catch(Exception ex) {
				ps.setBytes(5, itmObj.image);
			}
			//ps.setBinaryStream(5, fis,(int)image.length());
			
			//ps.setBlob(5, is);
			ps.executeUpdate();
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
				ps.close();
				//is.close();
				//connection.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				throw ex;
			}
		}
	}
}
