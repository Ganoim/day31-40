package jdbcShop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class ShopDao {

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

	public String select_idCheck(String mid) {
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

	public Member select_Login(String inputMid, String inputMpw) {

		return null;
	}

	public int insert(jdbcShop.Member mb) {
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB연결 실패");
			return 0;
		}
		String sql = " INSERT INTO  MEMBERS(MID, MPW, MNAME, MPHONE, MBIRTH) VALUES(?, ?, ?, ?, ?)";
		int result = 0;
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

	public jdbcShop.Member select_MemberInfo(String loginId) {
		// 1. DB 접속
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			return null;
		}
		// 2. 쿼리문 전송 결과값을 리턴
		String sql = "SELECT MID, MPW, MNAME, MPHONE, TO_CHAR(MBIRTH, 'YYYY-MM-DD') " + " FROM MEMBERS"
				+ " WHERE MID = ?";
		jdbcShop.Member mem = null;
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, loginId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				mem = new jdbcShop.Member();
				mem.setMid(rs.getString(1));
				mem.setMpw(rs.getString(2));
				mem.setMname(rs.getString(3));
				mem.setMphone(rs.getString(4));
				mem.setMbirth(rs.getString(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mem;
	}

	public jdbcShop.Member select_login(String inputMid, String inputMpw) {
		// 1. DB 접속
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			return null;
		}
		// 2. 쿼리문 실행
		String sql = "SELECT * FROM MEMBERS WHERE MID = ? AND MPW = ?";
		jdbcShop.Member loginMember = null;
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, inputMid);
			pstmt.setString(2, inputMpw);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				loginMember = new jdbcShop.Member();
				loginMember.setMid(rs.getString(1));
				loginMember.setMstate(rs.getString("MSTATE"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loginMember;
	}

	public ArrayList<Goods> selectGoods_All() {
		// 1. DB 접속
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			return null;
		}
		//2. 쿼리문 실행 결과값 반환
		String sql = "SELECT * FROM GOODS WHERE GSTOCK > 0";
		ArrayList<Goods> goodsList = new ArrayList<Goods>();
		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Goods goods = new Goods();
				goods.setGcode(rs.getString(1));
				goods.setGname(rs.getString(2));
				goods.setGprice(rs.getInt(3));
				goods.setGtype(rs.getString(4));
				goods.setGstock(rs.getInt(5));
				goods.setGstate(rs.getString(6));
				goodsList.add(goods);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return goodsList;
	}

	
	public String selectMaxOdcode() {
		Connection con = getConnection();

		if(con == null) {
			System.out.println("DB연결 실패");
		}else {
			System.out.println("DB연결 성공");
		}
		String sql = "SELECT NVL(MAX(ODCODE),'O0000') FROM ORDERS";
		String maxOdcode = null;
		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				maxOdcode = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maxOdcode;
	}

	public int InsertOdcode(String odcode,String loginId,String odgcode, int odqty) {
		Connection con = getConnection();

		if(con == null) {
			System.out.println("DB연결 실패");
		}else {
			System.out.println("DB연결 성공");
		}
		String sql = "INSERT INTO ORDERS(ODCODE,ODMID,ODGCODE,ODQTY,ODDATE) VALUES(?,?,?,?,SYSDATE)";
		int result = 0;

		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, odcode);
			pstmt.setString(2, loginId);
			pstmt.setString(3, odgcode);
			pstmt.setInt(4, odqty);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}

	public int UpdateGoods(int odqty,String odgcode) {
		Connection con = getConnection();

		if(con == null) {
			System.out.println("DB연결 실패");
		}else {
			System.out.println("DB연결 성공");
		}
		String sql = "UPDATE GOODS SET GSTOCK = GSTOCK - ? WHERE GCODE = ?";
		int result = 0;
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, odqty);
			pstmt.setString(2, odgcode);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
	}

	public ArrayList<Goods> selectGtype() {
		// 1. DB 접속
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			return null;
		}
		// 2. 쿼리문
		String sql = "SELECT GTYPE FROM GOODS GROUP BY GTYPE";
		ArrayList<Goods> gtypeList = new ArrayList<Goods>();

		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Goods goods = new Goods();
				goods.setGtype(rs.getString(1));
				gtypeList.add(goods);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return gtypeList;
	}

	public ArrayList<Goods> selectGoods_Type(String gtype) {
		// 1. DB 접속
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			return null;
		}
		String sql = "SELECT * FROM GOODS WHERE GTYPE = ?";
		ArrayList<Goods> goodsList = new ArrayList<Goods>();
		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, gtype);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Goods goods= new Goods();
				goods.setGcode(rs.getString("GCODE"));
				goods.setGname(rs.getString("GNAME"));
				goods.setGprice(rs.getInt("GPRICE"));
				goods.setGtype(rs.getString("GTYPE"));
				goods.setGstock(rs.getInt(5));
				goodsList.add(goods);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return goodsList;
	}
	
	//인기상품 조회(판매량이 많은순)
	public ArrayList<Goods> selectGoods_Best() {
		// 1. DB 접속
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			return null;
		}
		String sql = "SELECT * "
				+ " FROM GOODS GD INNER JOIN(SELECT ODGCODE, SUM(ODQTY) AS TOTALQTY"
				+ " FROM ORDERS"
				+ " GROUP BY ODGCODE) OD"
				+ " ON GD.GCODE = OD.ODGCODE"
				+ " ORDER BY OD.TOTALQTY DESC";
		ArrayList<Goods> goodsList = new ArrayList<Goods>();
		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Goods goods= new Goods();
				goods.setGcode(rs.getString("GCODE"));
				goods.setGname(rs.getString("GNAME"));
				goods.setGprice(rs.getInt("GPRICE"));
				goods.setGtype(rs.getString("GTYPE"));
				goods.setGstock(rs.getInt("GSTOCK"));
				goodsList.add(goods);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return goodsList;
	}

	public ArrayList<HashMap<String, String>> selectGoods_Map() {
		// 1. DB 접속
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			return null;
		}
		//2. 쿼리문 실행 결과값 반환
		String sql = "SELECT * FROM GOODS";
		ArrayList< HashMap<String, String> > goodsList = new ArrayList< HashMap<String, String> >();
		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				HashMap<String, String> goods = new HashMap<>();
				goods.put("gcode", rs.getString(1));		//GCODE
				goods.put("gname", rs.getString("GNAME"));	//2
				goods.put("gprice", rs.getString("GPRICE"));//3
				goods.put("gtype", rs.getString("GTYPE"));	//4
				goods.put("gstock", rs.getString("GSTOCK"));//5
				goodsList.add(goods);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return goodsList;
	}

	public int selectCountOrder(String loginId) {
		// 1. DB 접속
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			return 0;
		}
		String sql = "SELECT COUNT(*) FROM ORDERS WHERE ODMID = ?";
		int count = 0;
		
		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, loginId);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					count = rs.getInt(1);
				}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}

	public int selectTotalPrice(String loginId) {
		// 1. DB 접속
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			
		}
		
		String sql = "SELECT SUM(GD.GPRICE * OD.ODQTY) FROM ORDERS OD, GOODS GD WHERE (OD.ODGCODE = GD.GCODE) AND (OD.ODMID = ?)";
		int sum = 0;
		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, loginId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				sum = rs.getInt(1);
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return sum;
	}


	/*
	public ArrayList<HashMap<String, String>> selectOrder(String loginId) {
		// 1. DB 접속
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			return null;
		}
		String sql = "SELECT OD.ODCODE, GD.GNAME, GD.GPRICE, OD.ODQTY, (GD.GPRICE * OD.ODQTY), OD.ODDATE"
				+ " FROM ORDERS OD INNER JOIN GOODS GD ON (OD.ODGCODE = GD.GCODE)" + " WHERE OD.ODMID = ?"
				+ " ORDER BY OD.ODDATE DESC";
		ArrayList<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();

		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, loginId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				HashMap<String, String> orders = new HashMap<>();
				orders.put("odcode", rs.getString(1));
				orders.put("gname", rs.getString(2));
				orders.put("gprice", rs.getString(3));
				orders.put("odqty", rs.getString(4));
				orders.put("totalPrice", rs.getString(5));
				orders.put("oddate", rs.getString(6));
				orderList.add(orders);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return orderList;
	}*/

	public ArrayList<HashMap<String, String>> selectOrder(String loginId, int orderByOption) {
		// 1. DB 접속
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			return null;
		}
		String sql = "SELECT OD.ODCODE, GD.GNAME, GD.GPRICE, OD.ODQTY, (GD.GPRICE * OD.ODQTY) AS TOTALPRICE, OD.ODDATE, GD.GCODE"
				   + " FROM ORDERS OD INNER JOIN GOODS GD ON (OD.ODGCODE = GD.GCODE)" 
				   + " WHERE OD.ODMID = ?";
		if(orderByOption == 1) {
			sql += "ORDER BY OD.ODDATE DESC"; //+= 이어서
		}else {
			sql += "ORDER BY TOTALPRICE DESC";
		}
		ArrayList<HashMap<String, String>> orderList = new ArrayList<>();
		
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, loginId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				HashMap<String, String> odInfo = new HashMap<>();
				odInfo.put("odcode", rs.getString("ODCODE"));	//1
				odInfo.put("gcode", rs.getString("GCODE"));		//상품코드컬럼 추가 
				odInfo.put("gname", rs.getString("GNAME"));		//2
				odInfo.put("gprice", rs.getString("GPRICE"));	//3
				odInfo.put("odqty", rs.getString("ODQTY"));		//4
				odInfo.put("totalPrice", rs.getString("TOTALPRICE"));	//5
				odInfo.put("oddate", rs.getString("ODDATE"));	//6
				orderList.add(odInfo);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orderList;
	}

	public int order_delete(String odcode, int gstock, String gcode) {
		// 1. DB 접속
		Connection con = getConnection();
		if (con == null) {
			System.out.println("DB 연결 실패");
			return 0;
		}
		String delsql = "DELETE FROM ORDERS where ODCODE = ?";
		String upsql = "UPDATE GOODS SET GSTOCK = GSTOCK + ? WHERE GCODE = ?";
		int delResult = 0;
		int upResult = 0;
		
		try {
			con.setAutoCommit(false); //delsql, upsql이 모두 실행되고 commit(자동커밋 off)
			PreparedStatement pstmt = con.prepareStatement(delsql);
			pstmt.setString(1, odcode);
			delResult = pstmt.executeUpdate();  //쿼리문 실행
			
			pstmt = con.prepareStatement(upsql);
			pstmt.setInt(1, gstock);
			pstmt.setString(2, gcode);
			upResult = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		int result = 0;
		try {
			if(delResult > 0 && upResult > 0) {
				con.commit();
				result = 1;
			}else {
				con.rollback();
				//result = 0;
			}
		} catch (SQLException e) {
			
		}
		
		return result;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
}
