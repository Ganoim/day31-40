package jdbcShop_Admin;

import java.util.Scanner;

public class MstateMain {

	public static void main(String[] args) {
		MstateService mstsvc = new MstateService();
		Scanner scan = new Scanner(System.in);
		boolean run = true;
		while (run) {
			System.out.println("[1]회원관리 [2]상품관리");
			System.out.print("메뉴선택>>");
			int selectMenu = scan.nextInt();
			switch (selectMenu) {
			case 1:
				mstsvc.admin();
				break;
			case 2:
				mstsvc.administration();
				break;
			default:
				run = false;
			}

		}
	}

}
