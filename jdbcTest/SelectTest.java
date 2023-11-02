package jdbcTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class SelectTest {

	public static void main(String[] args) {
		//CRUDTEST  테이블의 모든 데이터 조회(SELECT)
		Scanner scan = new Scanner(System.in);
		System.out.print("NUMTEST 입력>>");
		int inputNum = scan.nextInt();
		
		
		//1. DB접속 
		Connection con = null;
		String url = "jdbc:oracle:thin:@//localhost:1521/xe";
		String userid = "JGH_DBA";
		String userpw = "1111";  	
		
		try {
			Class.forName("oracle.jdbc.OracleDriver"); //  드라이버선언
			con = DriverManager.getConnection(url, userid, userpw);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(con == null) {
			System.out.println("DB접속 실패");
		} else {
			System.out.println("DB접속 성공");
		}
		
		//2. 쿼리문 실핼 및 결과값 반환
		String sql = "SELECT * FROM CRUDTEST";
		// 결과값을 저장할 변수
		ArrayList<CrudDto> crudList = new ArrayList<CrudDto>();
		
		try {
			// 접속된 DB에 쿼리문 전송 준비
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, inputNum);
			// 쿼리문 실행 및 결과 값 반환
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) { //select했을때 전체 결과물에 행개수만큼 나옴
				CrudDto crud = new CrudDto();
				crud.setNumtest(rs.getInt(1)); // 컬럼의 데이터 타입에 따라 rs.get타입써주기
				crud.setChartest(rs.getString(2)); // (2)=CHARTEST(컬럼순서)
				crud.setDatetest(rs.getString(3)); // rs.getString(DATETEST) 컬럼명을 직접써도됨
				crudList.add(crud); // 추가
				
			}
			
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		//3. 출력
		for(int i = 0; i < crudList.size(); i++) {
			System.out.print(crudList.get(i).getNumtest());
			System.out.print(" " + crudList.get(i).getChartest());
			System.out.println(" " + crudList.get(i).getDatetest());
		}
		
		
		
	}

}
