package jdbcPhoneBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhoneBookDao {

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

	public int insert(PhoneBook pb) { // PHONEBOOK 테이블 INSERT 수행
		// 1. DB 접속
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			return 0;
		}
		// 2. 쿼리문 실행
		String sql = "INSERT INTO PHONEBOOK(NAME, PHONENUM)" + " VALUES(?, ?)"; // 쿼리문 자석
		int result = 0; // 결과값을 저장할 변수
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pb.getName());
			pstmt.setString(2, pb.getPhonenum());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 결과값 반환
		return result;
	}

	public ArrayList<PhoneBook> select() {
		// 1. DB접속 (Connection)
		Connection con = getConnection();
		if (con == null) {
			System.out.println("접속 실패");
			return null;
		}
		// 2.쿼리문 실행 결과값 반환
		String sql = "SELECT * FROM PHONEBOOK"; // 쿼리문 작성
		ArrayList<PhoneBook> pbList = new ArrayList<PhoneBook>(); // 결과값을 저장할 변수(select)

		try {
			PreparedStatement pstmt = con.prepareStatement(sql); // 쿼리문 준비
			ResultSet rs = pstmt.executeQuery(); // 쿼리문 실행 결과값 반환
			while (rs.next()) { // ResultSet >> ArrayList 변환
				PhoneBook pb = new PhoneBook();
				pb.setName(rs.getString("NAME")); // NAME = 1
				pb.setPhonenum(rs.getString(2));
				pbList.add(pb);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pbList;
	}

	public ArrayList<PhoneBook> selectName(String inputName) {
		// 1. DB접속 (Connection)
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB접속 실패");
			return null;
		}
		// 2.쿼리문 실행 결과값 반환
		String sql = "SELECT * FROM PHONEBOOK WHERE name = ?"; // 쿼리문 작성
		ArrayList<PhoneBook> pbList = new ArrayList<PhoneBook>(); // 결과값을 저장할 변수(select)

		try {
			PreparedStatement pstmt = con.prepareStatement(sql); // 쿼리문 준비
			pstmt.setString(1, inputName); // ? 맵핑
			ResultSet rs = pstmt.executeQuery(); // 쿼리문 실행 결과값 반환
			while (rs.next()) { // ResultSet >> ArrayList 변환
				PhoneBook pb = new PhoneBook();
				pb.setName(rs.getString(1));
				pb.setPhonenum(rs.getString(2));
				pbList.add(pb);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pbList;
	}

	public int update(PhoneBook pb, String inputPhoneNum) {
		// 1. DB접속
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB접속 실패");
			return 0;
		}
		// 2. 쿼리문 실행 결과값 반환
		String sql = "UPDATE PHONEBOOK SET PHONENUM = ? WHERE NAME = ? AND PHONENUM = ?";
		int result = 0;
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, inputPhoneNum);
			pstmt.setString(2, pb.getName());
			pstmt.setString(3, pb.getPhonenum());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int delete(PhoneBook pb) {
		// 1. DB접속
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB접속 실패");
			return 0;
		}
		// 2. 쿼리문 실행 결과값 반환
		String sql = "DELETE FROM PHONEBOOK WHERE NAME = ? AND PHONENUM = ?";
		int result = 0;
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pb.getName());
			pstmt.setString(2, pb.getPhonenum());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

}
