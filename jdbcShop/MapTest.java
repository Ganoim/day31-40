package jdbcShop;

import java.util.HashMap;

public class MapTest {

	public static void main(String[] args) {
		// key, value 쌍으로 데이터를 저장
		HashMap<String, String> odInfo = new HashMap<>();
		//put(key, value);
		odInfo.put("GNAME", "모니터");
		odInfo.put("ODQTY", "1");
		//get()key;
		
		System.out.println("상품이름 : " + odInfo.get("GNAME"));
		System.out.println("주문수량 : " + odInfo.get("ODQTY"));

	}

}
