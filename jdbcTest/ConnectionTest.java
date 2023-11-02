package jdbcTest;

import java.sql.Connection;
//import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionTest {

	public static void main(String[] args) {
		System.out.println("접속 테스트");
		// localhost >> 192.168.0.???
		String url = "jdbc:oracle:thin:@//localhost:1521/xe"; // DB접속주소
		String userid = "JGh_DBA"; //DB 아이디
		String userpw = "1111";    //DB 비밀번호
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection con = DriverManager.getConnection(url,userid,userpw);
			System.out.println("DB 접속 성공");
			//쿼리문 작성
			String sql = "SELECT * FROM EMP"; // ResultSet rs = pstmt.executeQuery();
//			String sql2 = "INSERT, UPDATE, DELETE"; -- int result = pstmt.executeUpdate(); -- 데이터조작어는 int로 설정
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			// rs.next() >> 레코드의 위치를 다음으로 이동
			//ArrayList<> empList;
			
			while(rs.next()) {
				System.out.println(rs.getString(1)+" "+rs.getString(2));
				
			}
			rs.close();
			pstmt.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 Exception");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB Exceotion");
			e.printStackTrace();
		}
		

	}

}
