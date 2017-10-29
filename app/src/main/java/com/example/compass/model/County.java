package com.example.compass.model;

public class County {
	private int id;
	private String countyname;
	private String countycode;
	private int cityId;
	public int getId(){
		return id;
	}
	public String getCountyName(){
		return countyname;
	}
	public String getCountyCode(){
		return countycode;
	}
	public int getCityId(){
		return cityId;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public void setCountyName(String countyName){
		this.countyname=countyName;
	}
	public void setCountyCode(String countyCode){
		this.countycode=countyCode;
	}
	public void setCityId(int cityId){
		this.cityId=cityId;
	}
}
