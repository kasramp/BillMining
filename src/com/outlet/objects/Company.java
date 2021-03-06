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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.outlet.common.Utilities;
import com.outlet.db.DbInit;
import com.outlet.db.JDBCConnection;
@DatabaseTable(tableName = "company")
public class Company implements Bean<Company>{
	public static final String TABLE_NAME = "company";
	public static final String FIELDS = "pkid, company_name, company_code, date_created";
	public static final String HEADER_FIELDS = "No #, Pkid, Company Code, Company Name, Creation Date";
	public static final String FIELDS_NO_PKID = FIELDS.substring(5);
	@DatabaseField(generatedId = true, columnName = "pkid")
	private Integer pkid;
	@DatabaseField(columnName = "company_name")
	private String companyName;
	@DatabaseField(columnName = "company_code")
	private String companyCode;
	@DatabaseField(columnName = "date_created", dataType = DataType.TIME_STAMP)
	private Timestamp dateCreated;
	
	private Dao<Company, Integer> companyDao;
	//////////////////////////////
	public Company() {
		this.pkid = new Integer(0);
		this.companyName = "";
		this.companyCode = "";
		this.dateCreated = Utilities.getCurrentTime();
		this.createDao();
	}
	public Integer getPkid() {
		return pkid;
	}
	public void setPkid(Integer pkid) {
		this.pkid = pkid;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	// Implementing Nut
	/*public static Company getObject(Integer pkid)
	{
		Company comp = new Company();
		try {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE "
				+ "pkid = " + pkid;
		//String fields = "pkid, company_pkid, outlet_pkid, bill_amount, gst_amount, total_amount, date_created";
		List<TreeMap> result = new ArrayList<TreeMap>();
		result = Utilities.executeDirectSelectSql(query, FIELDS);
		for(int i=0;i<result.size();i++) {
			TreeMap oneRow = result.get(i);
			comp = mapValues(oneRow);
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return comp;
	}*/
	/*public Company getObject(Integer pkid) {
		try {
			List<Company> companyLst = new Company().getObjects("pkid = " + pkid);
			if(companyLst != null && ! companyLst.isEmpty()) {
				return companyLst.get(0);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public Company getObject(String companyCode) {
		try {
			List<Company> companyLst = new Company().getObjects("company_code = '" + companyCode + "'");
			if(companyLst != null && ! companyLst.isEmpty()) {
				return companyLst.get(0);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public List<Company> getObjects(String conditions)
	{
		List<Company> rtnResult = new ArrayList<Company>();
		try {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + conditions;
		List<TreeMap> result = new ArrayList<TreeMap>();
		result = Utilities.executeDirectSelectSql(query, FIELDS);
		for(int i=0;i<result.size();i++) {
			TreeMap oneRow = result.get(i);
			Company comp = new Company();
			comp = mapValues(oneRow);
			rtnResult.add(comp);
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return rtnResult;
	}*/
	public Company getObject(Integer pkid) {
		try {
			return this.companyDao.queryForId(pkid);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public Company getObject(String companyCode) {
		try {
			List<Company> companyLst = this.companyDao.queryForEq("company_code", companyCode);
			if(companyLst != null && !companyLst.isEmpty()) {
				return companyLst.get(0);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public List<Company> getObjects(Map<String,Object> conditions) {
		List<Company> rtnResult = new ArrayList<Company>();
		try {
			rtnResult = this.companyDao.queryForFieldValues(conditions);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return rtnResult;
	}
	public List<Company> getObjects() {
		try {
			return this.companyDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	private static Company mapValues(TreeMap oneRow) {
		Company comp = new Company();
		try {
		if(oneRow == null || oneRow.isEmpty()){
			return null;
		}
		comp.pkid = (Integer) oneRow.get("pkid");
		comp.companyName = (String) oneRow.get("company_name");
		comp.companyCode = (String) oneRow.get("company_code");
		comp.dateCreated = Utilities.getTime((String) oneRow.get("date_created"));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return comp;
	}
	public Integer setObject(Company company) throws Exception{

		try {
			if (company != null) {
				if(this.companyDao.isTableExists()) {
					this.companyDao.create(company);
					return this.companyDao.extractId(company);
				} else {
					new DbInit().createTables();
					this.companyDao.create(company);
					return this.companyDao.extractId(company);
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	/*public Integer setObject(Company comp) throws Exception {
		Integer pkid = new Integer(0);
		try {
			String values = getValuesInString(comp);
			String query = "INSERT INTO " + TABLE_NAME + " (" + FIELDS_NO_PKID + ") VALUES (" + values + ")";
			Utilities.executeSqlQuery(query);
			pkid = Utilities.getLastPkid(TABLE_NAME);
		} catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return pkid;
	}*/
	private void createDao()
	{
		try {
			this.companyDao = DaoManager.createDao(JDBCConnection.getConnection(), Company.class);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	private static String getValuesInString(Company comp) {
		String values = "";
		try {
			values += "'" + comp.companyName + "',";
			values += "'" + comp.companyCode + "',";
			values += "'" + comp.dateCreated.toString() + "'";
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return values;
	}

}
