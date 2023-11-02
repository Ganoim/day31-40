package jdbcTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertTest {

	private static final int SQLException = 0;

	public static void main(String[] args) {
		System.out.println("INSERT 테스트");
		
		Connection con = null; // 접속 정보 저장
		//1. DB 접속(DB 접속 상태 확인)
		// 주소, 아이디, 비밀번호 
		String url = "jdbc:oracle:thin:@//localhost:1521/xe";  // 접속할 DB 아이피 및 포트 
		String userid = "JGH_DBA";  // 접속할 DB의 아이디
		String userpw = "1111";  	// 접속할 DB의 비밀번호
		try {
			Class.forName("oracle.jdbc.OracleDriver"); //  드라이버선언
			con = DriverManager.getConnection(url, userid, userpw);
		} catch (ClassNotFoundException e) { // 드라이버 예외
			e.printStackTrace();
			System.out.println("드라이버 예외");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DB 예외");
		}
		System.out.println("conn : " + con);
		if(con != null) {
			System.out.println("DB 접속");
		} else {
			System.out.println("DB 접속 실패");
		}
		//2. 실행할 퀴리문 전송 및 결과값 반환
		String sql = "INSERT INTO CRUDTEST(NUMTEST, CHARTEST, DATETEST)" + " VALUES(1, '테스트1', SYSDATE)";
		int insertResult = 0; // insert 실행 결과값 저장
		try {
			con.setAutoCommit(false);
			// 접속된 DB에 퀴리문 전송 준비
			PreparedStatement pstmt = con.prepareStatement(sql);
			// DB에서 퀴리문 실행 및 결과 확인
			insertResult = pstmt.executeUpdate();	
			con.commit();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("insertResult : " + insertResult);
		
		if(insertResult > 0) { // insert 실행 결과확인
			System.out.println("insert 성공");
		} else {
			System.out.println("insert 실패");
		}
		
		
		

	}

}
