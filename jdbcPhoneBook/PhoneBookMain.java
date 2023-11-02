package jdbcPhoneBook;

import java.util.Scanner;

public class PhoneBookMain {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		PhoneBookService pbsvc = new PhoneBookService();
		boolean run = true;
		while(run) {
			//메뉴출력
			System.out.println("[1]등록 [2]조회 [3]수정 [4]삭제 [5]이름으로조회");
			System.out.print("메뉴선택>>");
			//메뉴선택
			int selectMenu = scan.nextInt();
			//기능호출
			switch(selectMenu) {
			case 1:
				pbsvc.insertPhoneBook();
				break;
			case 2:
				pbsvc.selectPhoneBook();
				break;
			case 3:
				pbsvc.updatePhoneBook();
				break;
			case 4:
				pbsvc.deletePhoneBook();
				break;
			case 5:
				pbsvc.selectPhoneBook_Name();
				break;
			default:
				run = false;
			}
			
		}
		
		
		

	}

}
