package jdbcMember;

import java.util.Scanner;

public class MemberMain {

	public static void main(String[] args) {
		// MemberMain, MemberService, MemberDao
		Scanner scan = new Scanner(System.in);
		MemberService msvc = new MemberService();
		boolean run = true;
		while (run) {
			// 메뉴출력
			System.out.println("로그인아이디 : " + msvc.getLoginId());
			if (msvc.getLoginId() == null) { // 로그인이 되지 않은 경우 메뉴 출력
				System.out.println("[1]회원가입 [2]로그인"); // 로그인이 되지 않은경우 출력
			} else { // 로그인이 되어 있을 경우 메뉴 출력
				System.out.println("[1]정보확인 [2]로그아웃");
			}
			System.out.print("매뉴선택>>");
			// 메뉴선택
			int selectMenu = scan.nextInt();
			// 선택된 메뉴 호출
			switch (selectMenu) {
			case 1:
				if (msvc.getLoginId() == null) {
					// 로그인이 되지 않았을 경우: 회원가입 기능 호출
					msvc.memberJoin(); // 회원가입 기능
				} else {
					// 정보확인기능 호출
				}
				break;
			case 2:
				if (msvc.getLoginId() == null) {
					// 로그인X : 로그인 기능 호출
					msvc.memberLogin(); // 로그인 기능
				} else {
					// 로그아웃 기능 호출
					msvc.memberLogout();
				}
				break;
			default:
				run = false;
			}

		}

	}

}
