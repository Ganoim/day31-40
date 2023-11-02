package jdbcShop;
	import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

	public class ShopService {
		ShopDao shopdao = new ShopDao();
		Scanner scan = new Scanner(System.in);
		private String loginId = null; // 로그인 중인 아이디 저장
		
		public String getLoginId() {
			return loginId;
		}
		
		
		/* 회원가입 */
		public void shopJoin() {
			System.out.println("[회원가입]");
			
			System.out.print("가입할 아이디>>");
			String mid = scan.next();
			String idCheck = shopdao.select_idCheck(mid);
			
			if (idCheck != null) {
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
			System.out.print("가입할 생년월일>>");
			String mbirth = scan.next();
			
			Member mb = new Member();
			mb.setMid(mid);
			mb.setMpw(mpw);
			mb.setMname(mname);
			mb.setMphone(mphone);
			mb.setMbirth(mbirth);
			
			int ResultJoin = shopdao.insert(mb);
			
			if (ResultJoin > 0) {
				System.out.println("회원가입이 완료되었습니다");
			} else {
				System.out.println("회원가입에 실패하였습니다");
			}
			
		}
		
		/* 로그인 */
		public void memberLogin() {
			System.out.println("[로그인]");
			//1. 로그인할 아이디, 비밀번호 입력
			System.out.print("로그인 아이디>>");
			String inputMid = scan.next();
			System.out.print("로그인 비밀번호");
			String inputMpw = scan.next();
			
			//2. 일치하는 회원정보가 있는지 조회
			// SELECT MID FROM MEMBERS WHERE MID = ? AND MPW = ?
			Member loginMember = shopdao.select_login(inputMid, inputMpw);
			
			//3. 로그인처리
			if(loginMember != null) { 
				//로그인 정보 저장
				if(loginMember.getMstate().equals("Y")) {
					loginId = loginMember.getMid();
					System.out.println("로그인 되었습니다.");
				}else {
					System.out.println("이용이 정지된 계정입니다");
					System.out.println("관리자에게 문의해주세요!");
					return;
				}
			
			} else {
				System.out.println("아이디/비밀번호가 일치하지 않습니다.");
				System.out.println("로그인 실패");
			}
			
		}
		
		/* 로그아웃 */
		public void memberLogout() {
			loginId = null;
			System.out.println("로그아웃 되엇습니다.");
		}
		
		/* 내정보확인 */
		public void memberInfo() {
			System.out.println("[내정보확인]");
			/* 회원정보출력, 주문정보출력
			 * 1. 회원정보 조회(SELECT ..... FROM MEMBERS MID = ?)
			 *  - 현재 로그인중인 회원의 정보를 조회
			 *  */
			Member memInfo = shopdao.select_MemberInfo(loginId);
			if(memInfo == null) {
				System.out.println("회원정보를 찾을 수 없습니다.");
			} else {
				System.out.print("[아이디]" + memInfo.getMid());
				System.out.print("[비밀번호]" + memInfo.getMpw());
				System.out.print("[이름]" + memInfo.getMname());
				System.out.print("[전화번호]" + memInfo.getMphone());
				System.out.println("[생년월일]" + memInfo.getMbirth());
				
				int countOrder = shopdao.selectCountOrder(loginId);
				int totalPrice = shopdao.selectTotalPrice(loginId);
				
				System.out.println("[총 주문수]" + countOrder);
				System.out.println("[총 결제금액]" +  totalPrice);
			}
			
		}
		
		
		ArrayList<Goods> getGoodsList(){
			ArrayList<Goods> goodsList = null;
			System.out.println("[1]전체상품 [2]종류별상품 [3]인기상품");
			System.out.println("선택>>");
			int selectList = scan.nextInt();
			switch(selectList) { // 상품목록 조회
			case 1: //SELECT * FROM GOODS WHERE GSTOCK > 0;
				System.out.println("[전체상품목록]");
				goodsList = shopdao.selectGoods_All();
				break;
			case 2:
				System.out.println("[종류별상품목록]");
				//1. GTYPE 조회 (SELECT GTYPE FROM GOODS GROUP BY GTYPE)
				ArrayList<Goods> gtypeList = shopdao.selectGtype();
				
				if(gtypeList != null) {
					for(int i = 0; i < gtypeList.size(); i++) {
						System.out.print("["+i+"]"+gtypeList.get(i).getGtype()+" ");
					}
					System.out.println("\n선택>>");
					int selectType = scan.nextInt();
					String gtype = gtypeList.get(selectType).getGtype();
					goodsList = shopdao.selectGoods_Type(gtype);
					//SELECT * FROM GOODS WHERE GTYPE = ?
				}
				break;
			case 3:
				System.out.println("[인기상품목록]");
				goodsList = shopdao.selectGoods_Best();
				break;
			} //switch문 종료
			return goodsList;
			
		}
		
		public void goodsOrder() {
			/* 
			 * 1. 판매중인 상품 목록 출력
			 * 	- 전체상품목록, 종류별상품목록, 인기상품목록
			 * 2. 구매할 상품선택(상품코드선택)
			 * 3. 구매수량입력(주문수량입력)
			 * 	-- 주문코드 생성 (ORDERS(SELECT))
			 * 주문코드, 주문자아이디, 상품코드, 주문수량
			 * 4. 주문 처리(Orders = INSERT) 후 결과 출력
			 * 	-- GOODS (UPDATE) - 상품재고 수정
			 * 	-- ORDERS(INSERT) - 주문정보 입력
			 * */
			ArrayList<Goods> goodsList = getGoodsList();
			
			if(goodsList == null || goodsList.size() <= 0) {
				System.out.println("상품목록 조회를 실패하였습니다.");
				System.out.println("다시 선택 해주세요!");
				return;
			}
			//1. 상품목록 출력
			for(int i = 0; i < goodsList.size(); i++) {
				System.out.print("["+i+"}"+goodsList.get(i).getGname());
				
				if(goodsList.get(i).getGstate().equals("Y")) {
					System.out.print(" ["+goodsList.get(i).getGprice() + "원]" );
					System.out.println(" ["+ goodsList.get(i).getGstock() + "개]");	
				} else {
					System.out.println("[현재 주문이 불가능한 상품입니다]");
				}
			}
			System.out.println("상품선택>>");
			int selectGoods = scan.nextInt();
			Goods goods = goodsList.get(selectGoods);
			System.out.println(goods.getGname() + "[가격]"+goods.getGprice());
			
			if(goodsList.get(selectGoods).getGstate().equals("N")) {
				System.out.println("["+goods.getGname()+"] 판매중지 상품입니다");
				System.out.println("다시 선택 해주세요");
				return;
			}
			
			System.out.println("상품을 선택했습니다.");  //주문상품코드
			String odgcode = goods.getGcode();
			
			System.out.println("주문할 수량 입력>>");
			int odqty = scan.nextInt();  //주문수량
			if(odqty > goods.getGstock()) {
				System.out.println("상품재고가 부족합니다");
				System.out.println("다시 선택 해주세요.");
				return;
			}
			
			String maxOdcode = shopdao.selectMaxOdcode();
			if(maxOdcode==null)
			{
				System.out.println("주문처리중에 문제가 발생했습니다.");
				System.out.println("다시 주문해주세요.");
				return;
			}System.out.println("maxOdcode : "+maxOdcode);
			// 'O0000' >> [0][1][2][3][4]
			String strCode = maxOdcode.substring(0, 1); // 'O'
			int numCode = Integer.parseInt(maxOdcode.substring(1)) + 1; // +1 을 함으로써 새로운 코드를 지정함.

			String odcode = strCode + String.format("%04d", numCode); // d라는 자리에 4자리 숫자형식으로 (0을 안쓰면 빈자리에 공백이 나옴.)
			/* %4d >> "0  1" || %04d >> "0001" */
			System.out.println("odcode : "+odcode);
			Order od = new Order();
			od.setOdcode(odcode);
			od.setOddate(odcode);
			od.setOdgcode(odgcode);
			od.setOdmid(loginId);
			od.setOdqty(odcode);
			int Odinsert = shopdao.InsertOdcode(odcode, loginId, odgcode, odqty);

			if(Odinsert>0)
			{
				int Gdupdate = shopdao.UpdateGoods(odqty, odgcode);
				System.out.println("주문이 정상적으로 처리되었습니다.");
			}else
			{
				System.out.println("주문 실패하였습니다. 다시 시도해주십시오.");
			}
			
			// 주문자아이디(odmid) = 로그인아이디(loginId), 주문시간(oddate) = SYSDATE
			// 주문코드생성 'O0001', 'O0002', 'O0003'
			/* 1. 현재 주문코드 최대값 조회 ('O0001') --SELECT NVL(MAX(ODCODE),'O0001') FROM ORDERS
			 * 2. 문자를 숫자로 변경 'A0001' >> 'A','0001' 분리
			 * 3. '0001' 을 숫자 1로 변환
			 * 4. 숫자1에 +1
			 * 5. 숫자를 4자리의 '0002' 형식으로 변환
			 * 6. 'A' + '0002' >> 'A0002'
			 * */
			
			// 1.현재 주문코드 최대값 조회('A0001') --SELECT NVL(MAX(ODCODE),'O0001') FROM ORDERS
			// 주문처리 (ORDERS - INSERT , GOODS - UPDATE)
			// SELECT 'O'||LPAD(NVL(SUBSTR(MAX(ODCODE,2),'0000')+1,4,0) FROM ORDERS
			
		}


		//Map으로 상품목록 조회
		public void goodsList_Map() {
			System.out.println("[상품목록(Map)]");
			//1. 상품목록 조회(dao-SELECT >> service 리턴)
			ArrayList<HashMap<String, String>> goodsList = shopdao.selectGoods_Map();
			
			//2. 조회된 상품목록 출력
			for(int i = 0; i < goodsList.size(); i++) {
				System.out.print(goodsList.get(i).get("gcode")+" ");
				System.out.print(goodsList.get(i).get("gname")+" ");
				System.out.print(goodsList.get(i).get("gprice")+" ");
				System.out.print(goodsList.get(i).get("gtype")+" ");
				System.out.println(goodsList.get(i).get("gstock")+" ");
				
			}
			
			
			
			
		}
		/* 
		 * 주문취소 - delete from orders where ODCODE = ?
		 * 상품재고수정 - update goods set gstock = gstock + ? WHERE GCODE = ?
		 * 
		 * */


		public void ordersList_Map() {
			System.out.println("[주문내역]");
			System.out.println("[1]최근주문순 [2]총액이높은순");
			System.out.print("선택");
			int orderByOption = scan.nextInt();
			
			//2. dao - 주문내역 SELECT
			ArrayList<HashMap<String, String>> orderList = shopdao.selectOrder(loginId, orderByOption);
			if(orderList == null) {
				System.out.println("주문 내역 조회에 실패 하였습니다.");
				return;
			}else if(orderList.size() == 0) {
				System.out.println("주문 내역이 없습니다.");
				return;
			}
			// 3. 주문내역 출력
			for (int i = 0; i < orderList.size(); i++) {
				System.out.print("["+i+"}"+orderList.get(i).get("odcode") + " ");
				System.out.print(orderList.get(i).get("gname") + " ");
				System.out.print(orderList.get(i).get("gprice") + " ");
				System.out.print(orderList.get(i).get("odqty") + " ");
				System.out.print(orderList.get(i).get("totalPrice") + " ");
				System.out.println(orderList.get(i).get("oddate") + " ");

			}
			System.out.println("[1]주문취소 [2]나가기");
			int memu = scan.nextInt();
			if(memu == 1) {
				System.out.print("취소할 주문 선택>>"); 
				int sod = scan.nextInt();
				HashMap<String, String> order = orderList.get(sod); //Map에 orderList 저장된 주문내역을 입력한 인덱스번호에 있는 주문내역을 가져오고
				String odcode = order.get("odcode");  // 가져온 주문내역에서 취소할 주문코드
				int gstock = Integer.parseInt(order.get("odqty"));  //증가 시킬 재고(Map애 저장할때 key, value를 String으로 가져왔기 때문에 int타입으로 변환)
				String gcode = order.get("gcode");  //재고를 증가 시킬 상품코드
				//주문취소 -  dao
				int odd = shopdao.order_delete(odcode, gstock, gcode);
				if(odd > 0) {
					System.out.println("주문취소가 완료되었습니다.");				
				}else {
					System.out.println("주문취소에 실패 하였습니다.");
				}
				
			}else {
				return;
			}
			
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}