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
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.outlet.common.Utilities;
import com.outlet.db.DbInit;
import com.outlet.db.JDBCConnection;

@DatabaseTable(tableName = "outlet")
public class Outlet implements Bean<Outlet>{
	public static final String TABLE_NAME = "outlet";
	public static final String FIELDS = "pkid, company_pkid, outlet_name, outlet_code, outlet_address, date_created";
	public static final String HEADER_FIELDS = "No #, Pkid, Outlet Code, Outlet Name"
			+ ", Outlet Address, Company Name(Code), Creation Date";
	public static final String FIELDS_NO_PKID = FIELDS.substring(5);

	@DatabaseField(generatedId = true, columnName = "pkid")
	private Integer pkid;
	@DatabaseField(columnName = "company_pkid")
	private Integer companyPkid;
	@DatabaseField(columnName = "outlet_name")
	private String outletName;
	@DatabaseField(columnName = "outlet_code")
	private String outletCode;
	@DatabaseField(columnName = "outlet_address")
	private String outletAddress;
	@DatabaseField(columnName = "date_created")
	private Timestamp dateCreated;

	private Dao<Outlet, Integer> outletDao;
	//////////////////////////////
	/*Outlet() {

	}*/

	public Outlet() {
		this.pkid = new Integer(0);
		this.companyPkid = new Integer(0);
		this.outletName = "";
		this.outletCode = "";
		this.outletAddress = "";
		this.dateCreated = Utilities.getCurrentTime();
		this.createDao();
	}
	public Integer getPkid() {
		return pkid;
	}
	public void setPkid(Integer pkid) {
		this.pkid = pkid;
	}
	public Integer getCompanyPkid() {
		return companyPkid;
	}
	public void setCompanyPkid(Integer companyPkid) {
		this.companyPkid = companyPkid;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getOutletCode() {
		return outletCode;
	}
	public void setOutletCode(String outletCode) {
		this.outletCode = outletCode;
	}
	public String getOutletAddress() {
		return outletAddress;
	}
	public void setOutletAddress(String outletAddress) {
		this.outletAddress = outletAddress;
	}
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	// Implementing Nut
	/*public static Outlet getObject(Integer pkid) {
		Outlet outlet = new Outlet();
		try {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE "
				+ "pkid = " + pkid;
		//String fields = "pkid, company_pkid, outlet_pkid, bill_amount, gst_amount, total_amount, date_created";
		List<TreeMap> result = new ArrayList<TreeMap>();
		result = Utilities.executeDirectSelectSql(query, FIELDS);
		for(int i=0;i<result.size();i++) {
			TreeMap oneRow = result.get(i);
			outlet = mapValues(oneRow);
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return outlet;
	}
	public static Outlet getObject(String outletCode) {
		Outlet outlet = new Outlet();
		try {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE "
				+ "outlet_code = '" + outletCode + "'";
		//String fields = "pkid, company_pkid, outlet_pkid, bill_amount, gst_amount, total_amount, date_created";
		List<TreeMap> result = new ArrayList<TreeMap>();
		result = Utilities.executeDirectSelectSql(query, FIELDS);
		if(result != null && !result.isEmpty()) {
			for(int i=0;i<result.size();i++) {
				TreeMap oneRow = result.get(i);
				outlet = mapValues(oneRow);
			}
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return outlet;
	}*/
	public Outlet getObject(Integer pkid) {
		try {
			return this.outletDao.queryForId(pkid);
			/*this.outletDao.queryForId(this.outletDao.queryBuilder().where().eq("pkid", pkid));
			List<Outlet> outletLst = new Outlet().getObjects("pkid = " + pkid);
			if(outletLst != null && ! outletLst.isEmpty()) {
				return outletLst.get(0);
			}*/
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public Outlet getObject(String outletCode) {
		try {
			/*List<Outlet> outletLst = new Outlet().getObjects("outlet_code = '" + outletCode + "'");
			if(outletLst != null && ! outletLst.isEmpty()) {
				return outletLst.get(0);
			}*/
			return this.outletDao.queryForEq("outlet_code", outletCode).get(0);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public List<Outlet> getObjects(Map<String,Object> conditions) {
		List<Outlet> rtnResult = new ArrayList<Outlet>();
		try {
			/*String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + conditions;
			rtnResult = (List<Outlet>)Utilities.executeDirectSelectSql(query, Outlet.class);*/
			rtnResult = this.outletDao.queryForFieldValues(conditions);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return rtnResult;
	}
	public List<Outlet> getObjects() {
		try {
			return this.outletDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*public List<Outlet> getObjects(String conditions) {
		List<Outlet> rtnResult = new ArrayList<Outlet>();
		try {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + conditions;
		List<TreeMap> result = new ArrayList<TreeMap>();
		result = Utilities.executeDirectSelectSql(query, FIELDS);
		if(result != null && !result.isEmpty()) {
			for(int i=0;i<result.size();i++) {
				TreeMap oneRow = result.get(i);
				Outlet outlet = new Outlet();
				outlet = mapValues(oneRow);
				rtnResult.add(outlet);
			}
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return rtnResult;
	}*/
	private static Outlet mapValues(TreeMap oneRow) {
		Outlet outlet = new Outlet();
		try {
			if(oneRow == null || oneRow.isEmpty()){
				return null;
			}
			outlet.pkid = (Integer) oneRow.get("pkid");
			outlet.companyPkid = (Integer) oneRow.get("company_pkid");
			outlet.outletName = (String) oneRow.get("outlet_name");
			outlet.outletCode = (String) oneRow.get("outlet_code");
			outlet.outletAddress = (String) oneRow.get("outlet_address");
			outlet.dateCreated = Utilities.getTime((String)oneRow.get("date_created"));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return outlet;
	}
	public Integer setObject(Outlet outlet) throws Exception{

		try {
			if (outlet != null) {
				if(this.outletDao.isTableExists()) {
					this.outletDao.create(outlet);
					return this.outletDao.extractId(outlet);
				} else {
					new DbInit().createTables();
					this.outletDao.create(outlet);
					return this.outletDao.extractId(outlet);
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
		/*Integer pkid = new Integer(0);
		try {
			String values = getValuesInString(outlet);
			String query = "INSERT INTO " + TABLE_NAME + " (" + FIELDS_NO_PKID + ") VALUES (" + values + ")";
			Utilities.executeSqlQuery(query);
			pkid = Utilities.getLastPkid(TABLE_NAME);
		} catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return pkid;*/
	}
	private void createDao()
	{
		try {
			this.outletDao = DaoManager.createDao(JDBCConnection.getConnection(), Outlet.class);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private static String getValuesInString(Outlet outlet) {
		String values = "";
		try {
			values += outlet.companyPkid + ",";
			values += "'" + outlet.outletName + "',";
			values += "'" + outlet.outletCode + "',";
			values += "'" + outlet.outletAddress + "',";
			values += "'" + outlet.dateCreated.toString() + "'";
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return values;
	}

}
