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

@DatabaseTable(tableName = "bill_index")
public class BillIndex implements Bean<BillIndex>{

	public static final String TABLE_NAME = "bill_index";
	public static final String FIELDS = "pkid, company_pkid, outlet_pkid, bill_amount, gst_amount, total_amount, date_created";
	public static final String FIELDS_NO_PKID = FIELDS.substring(5);
	
	@DatabaseField(generatedId = true, columnName = "pkid")
	private Integer pkid;
	@DatabaseField(columnName = "company_pkid")
	private Integer companyPkid;
	@DatabaseField(columnName = "outlet_pkid")
	private Integer outletPkid;
	@DatabaseField(columnName = "bill_amount")
	private BigDecimal billAmount;
	@DatabaseField(columnName = "gst_amount")
	private BigDecimal gstAmount;
	@DatabaseField(columnName = "total_amount")
	private BigDecimal totalAmount;
	@DatabaseField(columnName = "date_created")
	private Timestamp dateCreated;
	
	private Dao<BillIndex, Integer> billIndexDao;

	////////////////////////////////
	public BillIndex()
	{
		this.pkid = new Integer(0);
		this.companyPkid = new Integer(0);
		this.outletPkid = new Integer(0);
		this.billAmount = new BigDecimal(0);
		this.gstAmount = new BigDecimal(0);
		this.totalAmount = new BigDecimal(0);
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
	public Integer getOutletPkid() {
		return outletPkid;
	}
	public void setOutletPkid(Integer outletPkid) {
		this.outletPkid = outletPkid;
	}
	public BigDecimal getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}
	public BigDecimal getGstAmount() {
		return gstAmount;
	}
	public void setGstAmount(BigDecimal gstAmount) {
		this.gstAmount = gstAmount;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	// Implementing Nut
	/*public BillIndex getObject(Integer pkid)
	{
		BillIndex bill = new BillIndex();
		try {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE "
				+ "pkid = " + pkid;
		//String fields = "pkid, company_pkid, outlet_pkid, bill_amount, gst_amount, total_amount, date_created";
		List<TreeMap> result = new ArrayList<TreeMap>();
		result = Utilities.executeDirectSelectSql(query, FIELDS);
		for(int i=0;i<result.size();i++) {
			TreeMap oneRow = result.get(i);
			bill.pkid = (Integer) oneRow.get("pkid");
			bill.companyPkid = (Integer) oneRow.get("company_pkid");
			bill.outletPkid = (Integer) oneRow.get("outlet_pkid");
			bill.billAmount = new BigDecimal((Double)oneRow.get("bill_amount"));
			bill.gstAmount = new BigDecimal((Double) oneRow.get("gst_amount"));
			bill.totalAmount = new BigDecimal((Double) oneRow.get("total_amount"));
			bill.dateCreated = Utilities.getTime((String)oneRow.get("date_created"));
			bill = mapValues(oneRow);
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return bill;
	}*/
	/*// TODO implement this method later
	public BillIndex getObject(String obj){ return null;}
	public List<BillIndex> getObjects(String conditions)
	{
		List<BillIndex> rtnResult = new ArrayList<BillIndex>();
		try {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + conditions;
		List<TreeMap> result = new ArrayList<TreeMap>();
		result = Utilities.executeDirectSelectSql(query, FIELDS);
		for(int i=0;i<result.size();i++) {
			TreeMap oneRow = result.get(i);
			BillIndex bill = new BillIndex();
			bill = mapValues(oneRow);
			rtnResult.add(bill);
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return rtnResult;
	}*/
	private static BillIndex mapValues(TreeMap oneRow) {
		BillIndex bill = new BillIndex();
		try {
		if(oneRow == null || oneRow.isEmpty()){
			return null;
		}
		bill.pkid = (Integer) oneRow.get("pkid");
		bill.companyPkid = (Integer) oneRow.get("company_pkid");
		bill.outletPkid = (Integer) oneRow.get("outlet_pkid");
		try {
			bill.billAmount = new BigDecimal((Double)oneRow.get("bill_amount"));
		} catch(Exception ex) {
			bill.billAmount = new BigDecimal(oneRow.get("bill_amount").toString());
		}
		try {
			bill.gstAmount = new BigDecimal((Double) oneRow.get("gst_amount"));
		} catch(Exception ex) {
			bill.gstAmount = new BigDecimal(oneRow.get("gst_amount").toString());
		}
		try {
			bill.totalAmount = new BigDecimal((Double) oneRow.get("total_amount"));
		} catch(Exception ex) {
			bill.totalAmount = new BigDecimal(oneRow.get("total_amount").toString());
		}
		bill.dateCreated = Utilities.getTime((String)oneRow.get("date_created"));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return bill;
	}
	/*public Integer setObject(BillIndex billObj) throws Exception{
		Integer pkid = new Integer(0);
		try {
			String values = getValuesInString(billObj);
			String query = "INSERT INTO " + TABLE_NAME + " (" + FIELDS_NO_PKID + ") VALUES (" + values + ")";
			Utilities.executeSqlQuery(query);
			pkid = Utilities.getLastPkid(TABLE_NAME);
		} catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return pkid;
	}*/
	private static String getValuesInString(BillIndex billObj) {
		String values = "";
		try {
			values += billObj.companyPkid + ",";
			values += billObj.outletPkid + ",";
			values += billObj.billAmount + ",";
			values += billObj.gstAmount + ",";
			values += billObj.totalAmount + ",";
			values += "'" + billObj.dateCreated.toString() + "'" ;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return values;
	}
	
	public BillIndex getObject(Integer pkid) {
		try {
			return this.billIndexDao.queryForId(pkid);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public BillIndex getObject(String obj) {
		/*try {
			return this.billIndexDao.queryForEq("category_code", categoryCode).get(0);
		} catch(Exception ex) {
			ex.printStackTrace();
		}*/
		return null;
	}
	public List<BillIndex> getObjects(Map<String,Object> conditions) {
		List<BillIndex> rtnResult = new ArrayList<BillIndex>();
		try {
			rtnResult = this.billIndexDao.queryForFieldValues(conditions);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return rtnResult;
	}
	public List<BillIndex> getObjects() {
		try {
			return this.billIndexDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Integer setObject(BillIndex billIndex) throws Exception{

		try {
			if (billIndex != null) {
				if(this.billIndexDao.isTableExists()) {
					this.billIndexDao.create(billIndex);
					return this.billIndexDao.extractId(billIndex);
				} else {
					new DbInit().createTables();
					this.billIndexDao.create(billIndex);
					return this.billIndexDao.extractId(billIndex);
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
			this.billIndexDao = DaoManager.createDao(JDBCConnection.getConnection(), BillIndex.class);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
