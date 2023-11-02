package jdbcShop;

import java.util.ArrayList;
import java.util.HashMap;

public class MapTest2 {

	public static void main(String[] args) {
		/* 
		 SELECT GNAME, GPRICE, ODQTY, (GPRICE + ODQTY), ODDATE
		 while(rs.next()){
		 
		 }
		 */
		// key, value 쌍으로 데이터를 저장
		// rs.next() >> 레코드가 선택
		ArrayList<Goods> goodsList = new ArrayList<Goods>();
		
		ArrayList<HashMap<String, String>> odList = new ArrayList <HashMap<String, String>>();
//		while(rs.next()) {
//			HashMap<String, String> odInfo = new HashMap<>();
//			odInfo.put("GNAME", rs.getString("GNAME")); //odInfo.put("GNAME", "사이다");
//			odInfo.put("GPRICE", rs.getInt("GPRICE")); //odInfo.put("GPRICE", 1500+"");
//			odInfo.put("ODQTY", 2+"");
//			odList.add(odInfo);
		
			
		
		
		
		
	}

}
