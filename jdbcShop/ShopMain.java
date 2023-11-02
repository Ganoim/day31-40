package jdbcShop;

import java.util.Scanner;

/*
 * 1. Member(회원정보), Goods(상품정보), Order(주문정보)
 * 2. ShopService( 기능 )
 * 3. ShopDao( DB )
 * 4. ShowMain
 * */

public class ShopMain {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		boolean run = true;
		ShopService shopsvc = new ShopService();
		while(run) {
			// 메뉴츌력( 로그인상태에 따라 다른 메뉴 출력 )
			String loginId = shopsvc.getLoginId(); //로그인 확인 변수
			if(loginId == null) { // 로그인 X
				System.out.println("[1]회원가입 [2]로그인 [9]상품목록");
				
			} else { // 로그인 O
				System.out.println("[1]내정보확인 [2]로그아웃 [3]상품주문 [4]주문내역");
				
			}
			System.out.print("메뉴선택>>");
			int selectMenu = scan.nextInt();
			switch(selectMenu) {
			case 9:
				shopsvc.goodsList_Map();
				break;
			case 1:
				if(loginId == null) { // 회원가입 기능 호출
					shopsvc.shopJoin();
					
				} else { // 내정보확인 기능 호출
					shopsvc.memberInfo();
				}
				break;
			case 2:
				if(loginId == null) { // 로그인 기능 호출
					shopsvc.memberLogin();
				} else { // 로그아웃 기능 호출
					shopsvc.memberLogout();
				}
				break;
			case 3:
				if(loginId != null) {
					shopsvc.goodsOrder();
				}
				break;
			case 4:
				if(loginId != null) {
					shopsvc.ordersList_Map();
				}
				break;
			default:
				run = false;
			}
			
		}
		
		
		
		

	}

}
