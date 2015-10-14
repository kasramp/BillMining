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

@DatabaseTable(tableName = "bill_item")
public class BillItem implements Bean<BillItem>{

	public static final String TABLE_NAME = "bill_item";
	public static final String FIELDS = "pkid, bill_index_pkid, item_id, qty, price, gst_amount";
	public static final String FIELDS_NO_PKID = FIELDS.substring(5);
	@DatabaseField(generatedId = true, columnName = "pkid")
	private Integer pkid;
	@DatabaseField(columnName = "bill_index_pkid")
	private Integer billIndexPkid;
	@DatabaseField(columnName = "item_id")
	private Integer itemId;
	@DatabaseField(columnName = "qty")
	private Integer qty;
	@DatabaseField(columnName = "price")
	private BigDecimal price;
	@DatabaseField(columnName = "gst_amount")
	private BigDecimal gstAmount;
	
	private Dao<BillItem, Integer> billItemDao;

	////////////////////////////////
	public BillItem()
	{
		this.pkid = new Integer(0);
		this.billIndexPkid = new Integer(0);
		this.itemId = new Integer(0);
		this.qty = new Integer(0);
		this.price = new BigDecimal(0);
		this.gstAmount = new BigDecimal(0);
		this.createDao();
	}
	public Integer getPkid() {
		return pkid;
	}
	public void setPkid(Integer pkid) {
		this.pkid = pkid;
	}
	public Integer getBillIndexPkid() {
		return billIndexPkid;
	}
	public void setBillIndexPkid(Integer billIndexPkid) {
		this.billIndexPkid = billIndexPkid;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getGstAmount() {
		return gstAmount;
	}
	public void setGstAmount(BigDecimal gstAmount) {
		this.gstAmount = gstAmount;
	}
	// Implementing Nut
	/*public BillItem getObject(Integer pkid) {
		BillItem billItm = new BillItem();
		try {
			String query = "SELECT * FROM " + TABLE_NAME + " WHERE pkid = " + pkid.toString();
			List<TreeMap> rawResult = new ArrayList<TreeMap>();
			rawResult = Utilities.executeDirectSelectSql(query, FIELDS);
			for(int i=0; i<rawResult.size();i++) {
				TreeMap map = rawResult.get(i);
				billItm = mapValues(map);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return billItm;
	}*/
	// TODO implement this method later
	/*public BillItem getObject(String obj){ return null;}
	public List<BillItem> getObjects(String conditions) {
		List<BillItem> rtnResult = new ArrayList<BillItem>();
		try {
			String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + conditions;
			List<TreeMap> rawResult = new ArrayList<TreeMap>();
			rawResult = Utilities.executeDirectSelectSql(query, FIELDS);
			for(int i=0; i<rawResult.size();i++) {
				TreeMap map = rawResult.get(i);
				BillItem billItm  = new BillItem();
				billItm = mapValues(map);
				rtnResult.add(billItm);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return rtnResult;
	}*/
	private static BillItem mapValues(TreeMap map) {
		if(map == null || map.isEmpty()) {
			return null;
		}
		BillItem billItm = new BillItem();
		try {
			billItm.pkid = (Integer) map.get("pkid");
			billItm.billIndexPkid = (Integer) map.get("bill_index_pkid");
			billItm.itemId = (Integer) map.get("item_id");
			billItm.qty = (Integer) map.get("qty");
			try {
				billItm.price = new BigDecimal((Double) map.get("price"));
			} catch(Exception ex) {
				billItm.price = new BigDecimal(map.get("price").toString());
			}
			try {
				billItm.gstAmount = new BigDecimal((Double) map.get("gst_amount"));
			} catch(Exception ex) {
				billItm.gstAmount = new BigDecimal(map.get("gst_amount").toString());
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return billItm;
	}
	/*public Integer setObject(BillItem billItmObj) throws Exception{
		Integer pkid = new Integer(0);
		try {
			String values = getValuesInString(billItmObj);
			String query = "INSERT INTO " + TABLE_NAME + " (" + FIELDS_NO_PKID + ") VALUES (" + values + ")";
			Utilities.executeSqlQuery(query);
			pkid = Utilities.getLastPkid(TABLE_NAME);
			} catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return pkid;
	}*/
	private static String getValuesInString(BillItem billItmObj) {
		String values = "";
		try {
			values += billItmObj.billIndexPkid + ",";
			values += billItmObj.itemId + ",";
			values += billItmObj.qty + ",";
			values += billItmObj.price + ",";
			values += billItmObj.gstAmount;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return values;
	}
	
	public BillItem getObject(Integer pkid) {
		try {
			return this.billItemDao.queryForId(pkid);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	// TODO implement later
	public BillItem getObject(String obj) {
		/*try {
			return this.categoryDao.queryForEq("category_code", categoryCode).get(0);
		} catch(Exception ex) {
			ex.printStackTrace();
		}*/
		return null;
	}
	public BillItem getObjectByIndexPkid(Integer pkid) {
		try {
			return this.billItemDao.queryForEq("bill_index_pkid", pkid).get(0);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	public List<BillItem> getObjects(Map<String,Object> conditions) {
		List<BillItem> rtnResult = new ArrayList<BillItem>();
		try {
			rtnResult = this.billItemDao.queryForFieldValues(conditions);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return rtnResult;
	}
	public List<BillItem> getObjects() {
		try {
			return this.billItemDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Integer setObject(BillItem billItem) throws Exception{

		try {
			if (billItem != null) {
				if(this.billItemDao.isTableExists()) {
					this.billItemDao.create(billItem);
					return this.billItemDao.extractId(billItem);
				} else {
					new DbInit().createTables();
					this.billItemDao.create(billItem);
					return this.billItemDao.extractId(billItem);
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
			this.billItemDao = DaoManager.createDao(JDBCConnection.getConnection(), BillItem.class);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
