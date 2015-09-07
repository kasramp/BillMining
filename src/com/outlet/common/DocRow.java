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

import java.math.BigDecimal;

public class DocRow {
	private BigDecimal qty;
	private String itemCode;
	private Integer itemPKid; //itemId
	private String itemName;
	private BigDecimal itemCost;
	public static final String[] HEADER_FIELDS_ARR = {"No #", "Item Code", "Item Name", "Quantity", "Price", "Edit", "Remove"};
	public DocRow() {
		this.qty = new BigDecimal(0);
		this.itemCode = "";
		this.itemPKid = new Integer(0);
		this.itemName = "";
		this.itemCost = new BigDecimal(0);
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Integer getItemPKid() {
		return itemPKid;
	}

	public void setItemPKid(Integer itemPKid) {
		this.itemPKid = itemPKid;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public BigDecimal getItemCost() {
		return itemCost;
	}

	public void setItemCost(BigDecimal itemCost) {
		this.itemCost = itemCost;
	}
}
