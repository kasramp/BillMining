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

import java.math.BigDecimal;

import com.outlet.db.DbInit;
import com.outlet.objects.Item;
public class Run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String[] myArr = new String[2];
		//fnChange(myArr);
		//for(int i=0;i<myArr.length;i++)
		//{
			//System.out.println(myArr[i]);
		//}
		try {
		DbInit newDb = new DbInit();
		//newDb.DbInit();
		Item itmObj = new Item();
		itmObj.setItemCode("MyCode");
		itmObj.setItemName("myName");
		itmObj.setMaCost(new BigDecimal(150));
		itmObj.setCategoryId(new Integer(12));
		try {
			new Item().setObject(itmObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		//BillIndex myBill = BillIndex.getObject(new Integer(1));
		//List<BillIndex> bills = BillIndex.getObjects("1=1");
		//System.out.println(bills.size());
		//TreeMap<String,String> data = new TreeMap<String,String>();
		//data.put("item_code", "'Kasra'");
		//data.put("item_name", "'Kasra_item'");
		//data.put("ma_cost", "100");
		//data.put("category_id", "500");
		//Utilities.executeDirectSelectSql("SELECT * FROM ITEM");
		//Utilities.executeSqlQuery(sqlStatement);
		//Utilities.executeUpdateSql("item", data, "PKID = 6");
		//Utilities.executeInsertSql("item", data);
		//String[] obj = new String[100];
		//Utilities.convertToCSV(obj);
	}
	public static void fnChange(String[] arr)
	{
		arr[0] = "hello";
		arr[1] = " kitty";
	}

}
