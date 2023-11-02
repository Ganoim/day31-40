package jdbcMember;

import java.util.Scanner;

import jdbcPhoneBook.PhoneBook;

public class MemberService {
	
	MemberDao mdao = new MemberDao();
	Scanner scan = new Scanner(System.in);
	public void memberJoin() { //회원가입 기능
		System.out.println("[회원가입]");
		//1. 가입할 회원정보 입력
		System.out.print("가입할 아이디>>");
		String mid = scan.next();
		//아이디 중복체크(MEMBERS 테이블의 MID컬럼 - SELECT)
		String idCheck = mdao.select_idCheck(mid);
		
		if(idCheck != null) {
			System.out.println("사용할 수 없는 아이디 입니다.");
			System.out.println("회원가입을 다시 시도해주세요!");
			return;
		} else {
			System.out.println("사용 가능한 아이디 입니다.");
		}
		
		System.out.print("가입할 비밀번호>>");
		String mpw = scan.next();
		System.out.print("가입할 이름>>");
		String mname = scan.next();
		System.out.print("가입할 전화번호>>");
		String mphone = scan.next();
		System.out.print("가입할 생년월일(YYYYMMDD)>>");
		String mbirth = scan.next();
		
		Member mb = new Member();
		mb.setMid(mid);
		mb.setMpw(mpw);
		mb.setMname(mname);
		mb.setMphone(mphone);
		mb.setMbirth(mbirth);
		
		//2. 회원정보를 Members 테이블에 INSERT
		int joinResult = mdao.insert(mb);

		//3. INSERT(회원가입) 결과 출력
		if (joinResult > 0) {
			System.out.println("회웝가입이 완료 되었습니다.");
		} else {
			System.out.println("회원가입에 실패하였습니다");
		}
	}
	
	private String loginId = null; // 로그인 중인 아이디 저장
	
	public String getLoginId() {
		return loginId;
	}

	public void memberLogin() { // 로그인 기능 메소드
		System.out.println("[로그인]");
		//1. 로그인할 아이디, 비밀번호 입력
		System.out.println("로그인 아이디>>");
		String inputMid = scan.next();
		System.out.println("로그인 비밀번호");
		String inputMpw = scan.next();
		
		//2. 일치하는 회원정보가 있는지 조회
		// SELECT MID FROM MEMBERS WHERE MID = ? AND MPW = ?
		Member loginMember = mdao.select_login(inputMid, inputMpw);
		
		//3. 로그인처리
		if(loginMember != null) { 
			//로그인 정보 저장
			System.out.println("로그인 성공");
			loginId = loginMember.getMid();
		} else {
			System.out.println("아이디/비밀번호가 일치하지 않습니다.");
			System.out.println("로그인 실패");
		}
		
		
	}

	public void memberLogout() { // 로그아웃 기능 메소드
		loginId = null;
		System.out.println("로그아웃 되었습니다.");
	}
	
	
	
		
		
}