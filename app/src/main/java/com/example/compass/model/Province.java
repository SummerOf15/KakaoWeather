package com.example.compass.model;

public class Province {
	private int id;
	private String provincename;
	private String provincecode;
	public int getId(){
		return id;
	}
	public String getProvinceName(){
		return provincename;
	}
	public String getProvinceCode(){
		return provincecode;
	}
	public void setId(int id){
		this.id=id;
	}
	public void setProvinceName(String Province_name){
		this.provincename=Province_name;
	}
	public void setProvinceCode(String Province_code){
		this.provincecode=Province_code;
	}
}
