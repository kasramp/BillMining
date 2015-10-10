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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

import com.outlet.common.Utilities;

public class BillMining implements Bean<BillMining> {

	public static final String TABLE_NAME = "bill_mining";
	public static final String FIELDS = "guid, item_id, price, outlet_pkid, date_of_suggestion";
	public static final String FIELDS_NO_PKID = FIELDS;
	public static final String HEADER_FIELDS = "No #, Item Id [Pkid], Item Code, Item Name, Optimal Price, "
			+ "Bill No # [Pkid], Company Name(Code), Outlet Name(Code), Outlet Address,Date of Purchase, "
			+ "Frequency of Purchase,FOP With Optimal Price ,Last Appearance, Item Image";
	
	public static final String[] HEADER_FIELDS_ARR = HEADER_FIELDS.split("[,]");
	private String guid;
	private Integer itemId;
	private BigDecimal price;
	private Integer outletPkid;
	private Timestamp dateOfSuggestion; 
	///////////////////////////////////
	public BillMining()
	{
		this.guid = UUID.randomUUID().toString();
		this.itemId = new Integer(0);
		this.price = new BigDecimal(0);
		this.outletPkid = new Integer(0);
		this.dateOfSuggestion = Utilities.getCurrentTime();
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getOutletPkid() {
		return outletPkid;
	}
	public void setOutletPkid(Integer outletPkid) {
		this.outletPkid = outletPkid;
	}
	public Timestamp getDateOfSuggestion() {
		return dateOfSuggestion;
	}
	public void setDateOfSuggestion(Timestamp dateOfSuggestion) {
		this.dateOfSuggestion = dateOfSuggestion;
	}
	// Implementing Nut
	public BillMining getObject(Integer pkid)
	{
		BillMining bm = new BillMining();
		try {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE "
				+ "pkid = " + pkid;
		//String fields = "pkid, company_pkid, outlet_pkid, bill_amount, gst_amount, total_amount, date_created";
		List<TreeMap> result = new ArrayList<TreeMap>();
		result = Utilities.executeDirectSelectSql(query, FIELDS);
		for(int i=0;i<result.size();i++) {
			TreeMap oneRow = result.get(i);
			bm = mapValues(oneRow);
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return bm;
	}
	public List<BillMining> getObjects(String conditions)
	{
		List<BillMining> rtnResult = new ArrayList<BillMining>();
		try {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + conditions;
		List<TreeMap> result = new ArrayList<TreeMap>();
		result = Utilities.executeDirectSelectSql(query, FIELDS);
		for(int i=0;i<result.size();i++) {
			TreeMap oneRow = result.get(i);
			BillMining bm = new BillMining();
			bm = mapValues(oneRow);
			rtnResult.add(bm);
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return rtnResult;
	}
	private static BillMining mapValues(TreeMap oneRow) {
		BillMining bm = new BillMining();
		try {
		if(oneRow == null || oneRow.isEmpty()){
			return null;
		}
		bm.guid = (String) oneRow.get("guid");
		bm.itemId = (Integer) oneRow.get("item_id");
		bm.price = new BigDecimal((Double)oneRow.get("price"));
		bm.outletPkid = (Integer) oneRow.get("outlet_pkid");
		bm.dateOfSuggestion = Utilities.getTime((String)oneRow.get("date_of_suggestion"));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return bm;
	}
	public Integer setObject(BillMining billMinObj) throws Exception{
		Integer pkid = new Integer(0);
		try {
			String values = getValuesInString(billMinObj);
			String query = "INSERT INTO " + TABLE_NAME + " (" + FIELDS_NO_PKID + ") VALUES (" + values + ")";
			Utilities.executeSqlQuery(query);
			pkid = Utilities.getLastPkid(TABLE_NAME);
		} catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return pkid;
	}
	private static String getValuesInString(BillMining billMinObj) {
		String values = "";
		try {
			values += "'" + billMinObj.guid + "',";
			values += billMinObj.itemId + ",";
			values += billMinObj.price + ",";
			values += billMinObj.outletPkid + ",";
			values += "'" + billMinObj.dateOfSuggestion.toString() + "'";
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return values;
	}
	public static List<RptRow> getMining()
	{
		List<RptRow> rtnResult = new ArrayList<RptRow>();
		// One Row, One Item Suggestion
		try {
			// First loop through items
			List<Item> itmLst = new Item().getObjects("1=1 ORDER BY PKID");
			for(int i=0;i<itmLst.size();i++) {
				Item oneItem = itmLst.get(i);
				// Now get all bills for this item
				List<BillItem> billItmLst = new BillItem().getObjects("item_id = " + oneItem.getPkid() + " ORDER BY PKID");
				if(billItmLst.size()>0) {
					RptRow oneRow = new RptRow();
					oneRow.itemId = oneItem.getPkid();
					oneRow.itemCode = oneItem.getItemCode();
					oneRow.itemName = oneItem.getItemName();
					oneRow.image = oneItem.getImage();
					oneRow.frequencyOfPurchase = new Integer(billItmLst.size());
					// Get Last purchase
					BillItem oneBill = billItmLst.get(billItmLst.size()-1);
					BillIndex billIndex = new BillIndex().getObject(oneBill.getBillIndexPkid());
					oneRow.lastAppearance = billIndex.getDateCreated();
					BillItem cheapestBill = billItmLst.get(0);
					oneRow.fopWithOptimalPrice = new Integer(1);
					for(int j=1;j<billItmLst.size();j++) {
						BillItem oneBillItm = billItmLst.get(j);
						if(cheapestBill.getPrice().compareTo(oneBillItm.getPrice())>0) {
							cheapestBill = oneBillItm;
							oneRow.fopWithOptimalPrice = new Integer(1);
						}
						else if(cheapestBill.getPrice().compareTo(oneBillItm.getPrice())==0) {
							cheapestBill = oneBillItm; // Still Replace to get latest bill No
							oneRow.fopWithOptimalPrice++;
						}
					}
					oneRow.billNo = cheapestBill.getBillIndexPkid();
					oneRow.optimalPrice = cheapestBill.getPrice();
					billIndex = new BillIndex().getObject(cheapestBill.getBillIndexPkid());
					oneRow.dateOfPurchase = billIndex.getDateCreated();
					Company comp = new Company().getObject(billIndex.getCompanyPkid());
					oneRow.companyNameCode = comp.getCompanyName() + " (" + comp.getCompanyCode() + ")";
					Outlet outlet = new Outlet().getObject(billIndex.getOutletPkid());
					oneRow.outletNameCode = outlet.getOutletName() + " (" + outlet.getOutletCode() + ")";
					oneRow.outletAddress = outlet.getOutletAddress();
					rtnResult.add(oneRow);
				}
			}
		
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return rtnResult;
	}
	public static class RptRow
	{
		public Integer itemId;
		public String itemCode;
		public String itemName;
		public BigDecimal optimalPrice;
		public Integer billNo;
		public String companyNameCode;
		public String outletNameCode;
		public String outletAddress;
		public Timestamp dateOfPurchase;
		public Integer frequencyOfPurchase;
		public Integer fopWithOptimalPrice;
		public Timestamp lastAppearance; // In general
		public byte[] image;
		
		public RptRow()
		{
			this.itemId = new Integer(0);
			this.itemCode = "";
			this.itemName = "";
			this.optimalPrice = new BigDecimal(0);
			this.billNo = new Integer(0);
			this.companyNameCode = "";
			this.outletNameCode = "";
			this.outletAddress = "";
			this.dateOfPurchase = Utilities.getCurrentTime();
			this.frequencyOfPurchase = new Integer(0);
			this.fopWithOptimalPrice = new Integer(0);
			this.lastAppearance = Utilities.getCurrentTime();
			this.image = null;
		}
	}
	
}
