package com.example.compass.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.compass.model.City;
import com.example.compass.model.CoolWeatherDB;
import com.example.compass.model.County;
import com.example.compass.model.Province;
import com.example.compass.model.WeatherCondition;

public class Utility {
	//analyze the returned data from service
	public synchronized static boolean handleProvinceResponse(CoolWeatherDB coolWeatherDB,String response){
		if(!TextUtils.isEmpty(response)){
			String[] allProvince=response.split(",");
			if(allProvince!=null && allProvince.length>0){
				for(String p:allProvince){
					String[] data=p.split("\\|");
					Province province =new Province();
					province.setProvinceCode(data[0]);
					province.setProvinceName(data[1]);
					coolWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}
	//analyze city data
	public static boolean handleCityResponse(CoolWeatherDB coolWeatherDB,String response,int provinceID){
		if(!TextUtils.isEmpty(response)){
			String[] allCity=response.split(",");
			if(allCity!=null && allCity.length>0){
				for(String c:allCity)
				{
					String[] data =c.split("\\|");
					City city=new City();
					city.setCityCode(data[0]);
					city.setCityName(data[1]);
					city.setProvinceId(provinceID);
					coolWeatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}
	public static boolean handleCountyResponse(CoolWeatherDB coolWeatherDB,String response,int cityID){
		if(!TextUtils.isEmpty(response)){
			String[] allCounty=response.split(",");
			if(allCounty!=null && allCounty.length>0){
				for(String c:allCounty){
					String[] data=c.split("\\|");
					County county=new County();
					county.setCountyCode(data[0]);
					county.setCountyName(data[1]);
					county.setCityId(cityID);
					coolWeatherDB.saveCounty(county);
				}
				return true;
			}
		}
		return false;
	}
	public static void handleWeatherResponse(Context context, String response){
		try{
			JSONObject jsonObject =new JSONObject(response);
			JSONObject weatherInfo=jsonObject.getJSONObject("weatherinfo");
			WeatherCondition weatherCondition=new WeatherCondition();
			String cityName=weatherInfo.getString("city");
			String weatherCode=weatherInfo.getString("cityid");
			weatherCondition.setTempLow(weatherInfo.getString("temp1"));
			weatherCondition.setTempHigh(weatherInfo.getString("temp2"));
			weatherCondition.setWeatherDesp(weatherInfo.getString("weather"));
			weatherCondition.setPublishTime(weatherInfo.getString("ptime"));
			weatherCondition.setURLImg1(weatherInfo.getString("img1"));
			weatherCondition.setURLImg2(weatherInfo.getString("img2"));
			saveWeatherInfo(context,cityName,weatherCode,weatherCondition);
		}
		catch(JSONException e){
			e.printStackTrace();
		}
	}
	public static void saveWeatherInfo(Context context, String cityName, String weatherCode, WeatherCondition weatherCondition){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy年M月d日",Locale.CHINA);
		SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code",weatherCode);
		editor.putString("temp1",weatherCondition.getTempLow());
		editor.putString("temp2",weatherCondition.getTempHigh());
		editor.putString("weather_desp",weatherCondition.getWeatherDesp());
		editor.putString("publish_time",weatherCondition.getPublishTime());
		editor.putString("img1",weatherCondition.getURLImg1());
		editor.putString("img2",weatherCondition.getURLImg2());
		editor.putString("current_date",sdf.format(new Date()));
		editor.commit();
	}
}
