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
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.outlet.common.Utilities;

public class BillItem implements Bean<BillItem>{

	public static final String TABLE_NAME = "bill_item";
	public static final String FIELDS = "pkid, bill_index_pkid, item_id, qty, price, gst_amount";
	public static final String FIELDS_NO_PKID = FIELDS.substring(5);
	private Integer pkid;
	private Integer billIndexPkid;
	private Integer itemId;
	private Integer qty;
	private BigDecimal price;
	private BigDecimal gstAmount;
	////////////////////////////////
	public BillItem()
	{
		this.pkid = new Integer(0);
		this.billIndexPkid = new Integer(0);
		this.itemId = new Integer(0);
		this.qty = new Integer(0);
		this.price = new BigDecimal(0);
		this.gstAmount = new BigDecimal(0);
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
	public BillItem getObject(Integer pkid) {
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
	}
	// TODO implement this method later
	public BillItem getObject(String obj){ return null;}
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
	}
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
	public Integer setObject(BillItem billItmObj) throws Exception{
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
	}
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
}
