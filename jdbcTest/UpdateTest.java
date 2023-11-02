package jdbcTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateTest {

	public static void main(String[] args) {
		// CRUDTEST 테이블의 데이터 수정(UPDATE)
		Scanner scan = new Scanner(System.in);
		System.out.print("WHERE NUMTEST 값>>");
		int inputNum = scan.nextInt();
		System.out.println("CHARTEST컬럼 수정할 값>>");
		String inputStr = scan.next();
		

		// 1. DB접속
		Connection con = null;
		String url = "jdbc:oracle:thin:@//localhost:1521/xe";
		String userid = "JGH_DBA";
		String userpw = "1111";
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection(url, userid, userpw);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (con == null) {
			System.out.println("DB접속 실패");
		} else {
			System.out.println("DB접속 성공");
		}
		
		String sql = "UPDATE CRUDTEST"
				   + " SET CHARTEST = ?"
				   + " WHERE NUMTEST = ?";
		int updateResult = 0;
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, inputStr);
			pstmt.setInt(2, inputNum);
			updateResult = pstmt.executeUpdate(); //updateResult=업데이트가 된 행의 개수
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		//결과확인
		if(updateResult > 0) {
			System.out.println("UPDATE성공");
		} else {
			System.out.println("UPDATE실패");
		}
		
		
		
		
	}

}
