package com.example.compass.model;

public class City {
	private int id;
	private String cityname;
	private String citycode;
	private int provinceId;
	public int getId(){
		return id;
	}
	public String getCityName(){
		return cityname;
	}
	public String getCityCode(){
		return citycode;
	}
	public int getProvinceId(){
		return provinceId;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public void setCityName(String cityName){
		this.cityname=cityName;
	}
	public void setCityCode(String cityCode){
		this.citycode=cityCode;
	}
	public void setProvinceId(int ProvinceId){
		this.provinceId=ProvinceId;
	}
}
