package com.ittu;

public class UserVO {
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getPcIP() {
		return pcIP;
	}
	public void setPcIP(String pcIP) {
		this.pcIP = pcIP;
	}
	public String getPcMac() {
		return pcMac;
	}
	public void setPcMac(String pcMac) {
		this.pcMac = pcMac;
	}
	private String userName ;
	private String passWord ;
	private String pcIP;
	private String pcMac;

}
