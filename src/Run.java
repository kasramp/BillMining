import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;

import com.outlet.*;
import com.outlet.common.Utilities;
import com.outlet.common.Utilities.*;
import com.outlet.db.DbInit;
import com.outlet.objects.*;
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
			Item.setObject(itmObj);
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
