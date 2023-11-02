package jdbcMember;

public class Member {
	//MEMBER
	//아이디, 비밀번호, 이름, 전화번호, 생년월일
	private String mid;		//아이디
	private String mpw;		//비밀번호
	private String mname;	//이름
	private String mphone;  //전화번호
	private String mbirth;	//생년월일
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getMpw() {
		return mpw;
	}
	public void setMpw(String mpw) {
		this.mpw = mpw;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getMphone() {
		return mphone;
	}
	public void setMphone(String mphone) {
		this.mphone = mphone;
	}
	public String getMbirth() {
		return mbirth;
	}
	public void setMbirth(String mbirth) {
		this.mbirth = mbirth;
	}
	
	
}
