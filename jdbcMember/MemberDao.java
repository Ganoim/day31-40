package jdbcMember;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDao {

	Connection getConnection() {
		Connection con = null;
		String url = "jdbc:oracle:thin:@//localhost:1521/xe";
		String userid = "JGH_DBA";
		String userpw = "1111";
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection(url, userid, userpw);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 예외");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DB 예외");
		}
		return con;
	}

	public int insert(Member mb) {
		// 1. DB 접속
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			return 0;
		}
		// 2. 쿼리문 실행
		String sql = "INSERT INTO MEMBERS(MID, MPW, MNAME, MPHONE, MBIRTH)" + " VALUES(?, ?, ?, ?, ?)";
		int result = 0; // 결과값을 저장할 변수
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mb.getMid());
			pstmt.setString(2, mb.getMpw());
			pstmt.setString(3, mb.getMname());
			pstmt.setString(4, mb.getMphone());
			pstmt.setString(5, mb.getMbirth());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String select_idCheck(String mid) {
		// 1. DB 접속
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			return null;
		}
		String sql = "SELECT MID FROM MEMBERS WHERE MID = ?";
		String idCheck = null;
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				idCheck = rs.getString(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return idCheck;
	}

	public Member select_login(String inputMid, String inputMpw) {
		// 1. DB 접속
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			return null;
		}
		//2. 쿼리문 실행
		String sql = "SELECT * FROM MEMBERS WHERE MID = ? AND MPW = ?";
		Member loginMember = null;
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, inputMid);
			pstmt.setString(2, inputMpw);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				loginMember = new Member();
				loginMember.setMid(rs.getString(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return loginMember;
	}

}
