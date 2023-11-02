package jdbcShop_Admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbcShop.Goods;
import jdbcShop.Member;

public class MstateDao {
	
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
	
	public ArrayList<Member> selectMember() {
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			return null;
		}
		String sql = "SELECT * FROM MEMBERS";
		ArrayList<Member> memList = new ArrayList<Member>(); 
		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Member member = new Member();
				member.setMid(rs.getString("MID"));
				member.setMpw(rs.getString("MPW"));
				member.setMname(rs.getString("MNAME"));
				member.setMphone(rs.getString("MPHONE"));
				member.setMbirth(rs.getString("MBIRTH"));
				member.setMstate(rs.getString("MSTATE"));
				memList.add(member);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return memList;
	}

//	public int update(String mstate, String mid, String mname) {
//		Connection con = getConnection();
//		if (con == null) {
//			System.out.println("DB 연결 실패");
//			return 0;
//		}
//		String nsql = "UPDATE MEMBERS SET MSTATE = 'N' WHERE MID = ?";
//		String ysql = "UPDATE MEMBERS SET MSTATE = 'Y' WHERE MID = ?";
//		int Nresult = 0;
//		int Yresult = 0;
//
//		try {
//			if (mstate.equals("Y")) {
//				con.setAutoCommit(false);
//				PreparedStatement pstmt = con.prepareStatement(nsql);
//				pstmt.setString(1, mid);
//				Nresult = pstmt.executeUpdate();
//				
//				
//			} else if (mstate.equals("N")) {
//				con.setAutoCommit(false);
//				PreparedStatement pstmt = con.prepareStatement(ysql);
//				pstmt.setString(1, mid);
//				Yresult = pstmt.executeUpdate();
//				
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		int result = 0;
//		try {
//			if(Nresult > 0 || Yresult > 0) {
//				con.commit();
//				result = 1;
//			}else {
//				con.rollback();
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return result;
//	}

	public int update(String mid, String mstate) {
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			return 0;
		}
		String sql = "UPDATE MEMBERS ";
		if(mstate.equals("Y")) {
			sql += " SET MSTATE = 'N'";
		}else {
			sql += " SET MSTATE = 'Y'";
		}
		sql += " WHERE MID = ?";
		int result = 0;
		
		 try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mid);
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		return result;
	}

	public ArrayList<Goods> selectGoods() {
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			return null;
		}		
		String sql = "SELECT * FROM GOODS";
		ArrayList<Goods> goodsList = new ArrayList<Goods>();
		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Goods goods = new Goods();
				goods.setGcode(rs.getString("GCODE"));
				goods.setGname(rs.getString("GNAME"));
				goods.setGprice(rs.getInt("GPRICE"));
				goods.setGtype(rs.getString("GTYPE"));
				goods.setGstock(rs.getInt("GSTOCK"));
				goods.setGstate(rs.getString("GSTATE"));
				goodsList.add(goods);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return goodsList;
	}

	public int updateGoods(String gcode, String gstate) {
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			return 0;
		}
		String sql = "UPDATE GOODS ";
		if(gstate.equals("Y")) {
			sql += " SET GSTATE = 'N'";
		}else {
			sql += " SET GSTATE = 'Y'";
		}
		sql += " WHERE GCODE = ?";
		int result = 0;
		
		 try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, gcode);
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		
		return result;
	}



	
	
	
	
	
	

}
