package com.trigital.cm.domain;

public class MACDetails {

	private String mac_Address;
	private String ip_Address;
	private String i_f;
	private String mac_state;
	private String prim_Sid;
	private String rxpwr;
	private String timing_Offset;
	private String num_CPE;
	private String DIP;

	public MACDetails() {

	}

	public MACDetails(String mac_Address, String ip_Address, String i_f,
			String mac, String prim_RxPwr_Sid, String timing_Offset,
			String num_CPE, String dIP, String rxpwr) {
		this.mac_Address = mac_Address;
		this.ip_Address = ip_Address;
		this.i_f = i_f;
		this.mac_state = mac;
		this.prim_Sid = prim_RxPwr_Sid;
		this.timing_Offset = timing_Offset;
		this.num_CPE = num_CPE;
		this.rxpwr = rxpwr;
		this.DIP = dIP;
	}

	public String getPrim_Sid() {
		return prim_Sid;
	}

	public void setPrim_Sid(String prim_Sid) {
		this.prim_Sid = prim_Sid;
	}

	public String getRxpwr() {
		return rxpwr;
	}

	public void setRxpwr(String rxpwr) {
		this.rxpwr = rxpwr;
	}

	public String getMac_Address() {
		return mac_Address;
	}

	public void setMac_Address(String mac_Address) {
		this.mac_Address = mac_Address;
	}

	public String getIp_Address() {
		return ip_Address;
	}

	public void setIp_Address(String ip_Address) {
		this.ip_Address = ip_Address;
	}

	public String getI_f() {
		return i_f;
	}

	public void setI_f(String i_f) {
		this.i_f = i_f;
	}

	public String getMac_state() {
		return mac_state;
	}

	public void setMac_state(String mac) {
		this.mac_state = mac;
	}

	public String getTiming_Offset() {
		return timing_Offset;
	}

	public void setTiming_Offset(String timing_Offset) {
		this.timing_Offset = timing_Offset;
	}

	public String getNum_CPE() {
		return num_CPE;
	}

	public void setNum_CPE(String num_CPE) {
		this.num_CPE = num_CPE;
	}

	public String getDIP() {
		return DIP;
	}

	public void setDIP(String dIP) {
		DIP = dIP;
	}

}
