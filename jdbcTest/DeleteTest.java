package jdbcTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteTest {

	public static void main(String[] args) {
		
		//1. DB 접속
		Connection con = null;
		String url = "jdbc:oracle:thin:@//localhost:1521/xe"; // 접속할 DB 아이피 및 포트 
		String userid = "JGH_DBA"; // 접속할 DB의 아이디
		String userpw = "1111"; // 접속할 DB의 비밀번호
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection(url, userid, userpw);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(con == null) {
			System.out.println("접속 실패");
		} else {
			System.out.println("접속 성공");
		}
		
		//2. 접속된 DB 쿼리문 전송 결과값 반환
		String sql = "DELETE FROM CRUDTEST"; //쿼리문 작성
		int deleteResult = 0; //실행 결과값 저장
		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql); // 쿼리문 실행 준비
			deleteResult = pstmt.executeUpdate(); // 쿼리문 실행 후 결과값 반환
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//3. 결과에 따른 처리
		if(deleteResult > 0) {
			System.out.println(deleteResult + "DELETE 성공");
		} else {
			System.out.println("DELETE 실패");
		}
		
		
		
		
		
		
	}

}
