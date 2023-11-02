package jdbcShop_Admin;

import java.util.ArrayList;
import java.util.Scanner;

import jdbcShop.Goods;
import jdbcShop.Member;
import jdbcShop.ShopService;

public class MstateService {
	
	MstateDao mstdao = new MstateDao();
	ShopService shopsvc = new ShopService();
	Scanner scan = new Scanner(System.in);
	
	public void admin() {
		
		ArrayList<Member> memList = mstdao.selectMember();
		if(memList != null) {
			for(int i = 0; i < memList.size(); i++) {
				System.out.print("["+i+"]"+ "["+memList.get(i).getMstate()+"]");
				System.out.print(" [아이디]"+memList.get(i).getMid());
				System.out.println(" [이름]"+memList.get(i).getMname());
			}
		}else {
			System.out.println("회원정보가 없습니다");
		}
		System.out.print("관리할 회원선택>>");
		int mem = scan.nextInt();
		String mid = memList.get(mem).getMid();
		String mstate = memList.get(mem).getMstate();
		int result = mstdao.update(mid, mstate);
		
		if(result > 0) {
			if(mstate.equals("Y")) {
				System.out.println("["+mid+"]회원을 이용정지 처리하였습니다");
			} else {
				System.out.println("["+mid+"]회원을 이용가능 처리하였습니다");
			}
		}else {
			System.out.println("회원 상태 변경실패");
		}
		
//		Member manager = memInfo.get(mem);
//		String mstate = manager.getMstate();
//		String mid = manager.getMid();
		
	}

	public void administration() {
		System.out.println("[상품관리]");
		
		ArrayList<Goods> goods = mstdao.selectGoods();
		System.out.println("d");
		if (goods != null) {
			for (int i = 0; i < goods.size(); i++) {
				System.out.print("[" + i + "][" + goods.get(i).getGstate() + "]");
				System.out.print("[" + goods.get(i).getGname() + "]");
				System.out.print("[" + goods.get(i).getGprice() + "원]");
				System.out.println("[" + goods.get(i).getGstock() + " 남음]");
			}
		} else {
			System.out.println("주문목록이 없습니다");
		}
		
		System.out.println("상품선택>>");
		int sgd = scan.nextInt();
		String gcode = goods.get(sgd).getGcode();
		String gname = goods.get(sgd).getGname();
		String gstate = goods.get(sgd).getGstate();
		
		System.out.println("선택한 삼품["+gname+"]은 ");
		if(gstate.equals("Y")) {
			System.out.println("현재 판매중인 상품입니다.");
		} else {
			System.out.println("현재 판매가 중지된 상품입니다.");
		}
		System.out.println("판매 상태를 변경하겠습니까?");
		System.out.println("[1]예 [2]아니요");
		int confirm = scan.nextInt();
		if(confirm != 1) {
			return;
		} 
		int result = mstdao.updateGoods(gcode, gstate);
		if(result > 0) {
			if(gstate.equals("Y")) {
				System.out.println("["+gname+"] 상품을 판매중지 처리하였습니다.");
			} else {
				System.out.println("["+gname+"] 상품을 판매가능 처리하였습니다.");
			}
		} else {
			System.out.println("판매 상태 변경실패");
		}
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	

}
