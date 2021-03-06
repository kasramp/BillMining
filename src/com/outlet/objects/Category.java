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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.outlet.common.Utilities;
import com.outlet.db.DbInit;
import com.outlet.db.JDBCConnection;

@DatabaseTable(tableName = "category")
public class Category implements Bean<Category>{
	
	public static final String TABLE_NAME = "category";
	public static final String FIELDS = "pkid, category_code, category_name, category_description";
	public static final String HEADER_FIELDS = "No #, Pkid, Category Code, Category Name, Category Description";
	public static final String FIELDS_NO_PKID = FIELDS.substring(5);
	@DatabaseField(generatedId = true, columnName = "pkid")
	private Integer pkid;
	@DatabaseField(columnName = "category_code")
	private String categoryCode;
	@DatabaseField(columnName = "category_name")
	private String categoryName;
	@DatabaseField(columnName = "category_description")
	private String categoryDesc;
	////////////////////////////
	private Dao<Category, Integer> categoryDao;

	public Category()
	{
		this.pkid = new Integer(0);
		this.categoryCode = "";
		this.categoryName = "";
		this.categoryDesc = "";
		this.createDao();
	}
	public Integer getPkid() {
		return pkid;
	}
	public void setPkid(Integer pkid) {
		this.pkid = pkid;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryDesc() {
		return categoryDesc;
	}
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	// Implementing Nut
	/*public static Category getObject(Integer pkid)
	{
		Category cat = new Category();
		try {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE "
				+ "pkid = " + pkid;
		//String fields = "pkid, company_pkid, outlet_pkid, bill_amount, gst_amount, total_amount, date_created";
		List<TreeMap> result = new ArrayList<TreeMap>();
		result = Utilities.executeDirectSelectSql(query, FIELDS);
		for(int i=0;i<result.size();i++) {
			TreeMap oneRow = result.get(i);
			cat = mapValues(oneRow);
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return cat;
	}*/
	/*public Category getObject(Integer pkid) {
		try {
			List<Category> catLst = new Category().getObjects("pkid = " + pkid);
			if(catLst != null && ! catLst.isEmpty()) {
				return catLst.get(0);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}*/
	/*public Category getObject(String categoryCode) {
		try {
			List<Category> catLst = new Category().getObjects("category_code = '" + categoryCode + "'");
			if(catLst != null && ! catLst.isEmpty()) {
				return catLst.get(0);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}*/
	/*public List<Category> getObjects(String conditions)
	{
		List<Category> rtnResult = new ArrayList<Category>();
		try {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + conditions;
		List<TreeMap> result = new ArrayList<TreeMap>();
		result = Utilities.executeDirectSelectSql(query, FIELDS);
		for(int i=0;i<result.size();i++) {
			TreeMap oneRow = result.get(i);
			Category cat = new Category();
			cat = mapValues(oneRow);
			rtnResult.add(cat);
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return rtnResult;
	}*/
	
	private static Category mapValues(TreeMap oneRow) {
		Category cat = new Category();
		try {
		if(oneRow == null || oneRow.isEmpty()){
			return null;
		}
		cat.pkid = (Integer) oneRow.get("pkid");
		cat.categoryCode = (String) oneRow.get("category_code");
		cat.categoryName = (String) oneRow.get("category_name");
		cat.categoryDesc = (String) oneRow.get("category_description");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return cat;
	}
	/*public Integer setObject(Category cat) throws Exception{
		Integer pkid = new Integer(0);
		try {
			String values = getValuesInString(cat);
			String query = "INSERT INTO " + TABLE_NAME + " (" + FIELDS_NO_PKID + ") VALUES (" + values + ")";
			Utilities.executeSqlQuery(query);
			pkid = Utilities.getLastPkid(TABLE_NAME);
		} catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return pkid;
	}*/
	private static String getValuesInString(Category cat) {
		String values = "";
		try {
			values += "'" + cat.categoryCode + "',";
			values += "'" + cat.categoryName + "',";
			values += "'" + cat.categoryDesc + "'";
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return values;
	}
	public Category getObject(Integer pkid) {
		try {
			return this.categoryDao.queryForId(pkid);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public Category getObject(String categoryCode) {
		try {
			List<Category> cats = this.categoryDao.queryForEq("category_code", categoryCode); 
			if(cats != null && !cats.isEmpty()) {
				return cats.get(0);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public List<Category> getObjects(Map<String,Object> conditions) {
		List<Category> rtnResult = new ArrayList<Category>();
		try {
			rtnResult = this.categoryDao.queryForFieldValues(conditions);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return rtnResult;
	}
	public List<Category> getObjects() {
		try {
			return this.categoryDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Integer setObject(Category category) throws Exception{

		try {
			if (category != null) {
				if(this.categoryDao.isTableExists()) {
					this.categoryDao.create(category);
					return this.categoryDao.extractId(category);
				} else {
					new DbInit().createTables();
					this.categoryDao.create(category);
					return this.categoryDao.extractId(category);
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	private void createDao()
	{
		try {
			this.categoryDao = DaoManager.createDao(JDBCConnection.getConnection(), Category.class);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
