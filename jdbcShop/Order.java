package jdbcShop;

public class Order {
	
	private String odcode;	  //주문코드
	private String odmid;	  //주문자아이디
	private String odgcode;	  //주문한상품코드
	private String odqty;	  //주문수량
	private String oddate;	  //주문일시
	
	public String getOdcode() {
		return odcode;
	}
	public void setOdcode(String odcode) {
		this.odcode = odcode;
	}
	public String getOdmid() {
		return odmid;
	}
	public void setOdmid(String odmid) {
		this.odmid = odmid;
	}
	public String getOdgcode() {
		return odgcode;
	}
	public void setOdgcode(String odgcode) {
		this.odgcode = odgcode;
	}
	public String getOdqty() {
		return odqty;
	}
	public void setOdqty(String odqty) {
		this.odqty = odqty;
	}
	public String getOddate() {
		return oddate;
	}
	public void setOddate(String oddate) {
		this.oddate = oddate;
	}

}
