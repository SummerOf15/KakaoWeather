package com.example.compass.activity;

import com.bumptech.glide.Glide;
import com.example.compass.R;
import com.example.compass.service.AutoUpdateService;
import com.example.compass.util.HttpCallbackListener;
import com.example.compass.util.HttpUtil;
import com.example.compass.util.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.util.Calendar;
import java.util.TimeZone;

public class WeatherActivity extends Activity implements OnClickListener {
	public Button switchCity;
	public Button refreshWeather;
	private ImageView weatherImg;
	public LinearLayout sunMainlayout;
    private LinearLayout weatherInfoLayout;
	public TextView cityNameText;
	public TextView weatherDespText;
	public TextView temp1Text;
	public TextView temp2Text;
	public TextView currentDateText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_layout);

		switchCity=(Button)findViewById(R.id.switch_city);
		refreshWeather=(Button)findViewById(R.id.refresh_weather);

		sunMainlayout=(LinearLayout)findViewById(R.id.sun_main);
		cityNameText=(TextView)findViewById(R.id.city_name);

		currentDateText=(TextView)findViewById(R.id.current_date);
		weatherImg=(ImageView)findViewById(R.id.wImg1);
		weatherDespText=(TextView)findViewById(R.id.weather_desp);
		temp1Text=(TextView)findViewById(R.id.temp1);
		temp2Text=(TextView)findViewById(R.id.temp2);

		switchCity.setOnClickListener(this);
		refreshWeather.setOnClickListener(this);

        weatherInfoLayout=(LinearLayout)findViewById(R.id.weather_info_layout);

		String countyCode=getIntent().getStringExtra("county_code");//get info from another activity
        if(atNight())
            sunMainlayout.setBackgroundResource(R.drawable.night);
        else
            sunMainlayout.setBackgroundResource(R.drawable.daytime);
		if(!TextUtils.isEmpty(countyCode)){
			//if countyCode is not empty, search info about the weather
			weatherInfoLayout.setVisibility(View.INVISIBLE);
			cityNameText.setVisibility(View.INVISIBLE);
			queryWeatherCode(countyCode);
		}
		else{
			//empty: show local weather
			showWeather();
		}
	}
	public void onClick(View v){
		switch (v.getId()){
			case R.id.switch_city:
				Intent intent=new Intent(this,ChooseAreaActivity.class);
				intent.putExtra("from_weather_activity",true);
				startActivity(intent);
				finish();
				break;
			case R.id.refresh_weather:
				SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
				String weatherCode=prefs.getString("weather_code","");
				if(!TextUtils.isEmpty(weatherCode)){
					queryWeatherInfo(weatherCode);
				}
				break;
			default:break;
		}
	}
	private void queryWeatherCode(String countyCode){
		String address="http://www.weather.com.cn/data/list3/city"+countyCode+".xml";
		queryFromServer(address,"countyCode");
	}
	private void queryWeatherInfo(String weatherCode){
		String address="http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
		queryFromServer(address,"weatherCode");
	}
	private void queryFromServer(final String address,final String type){
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				if("countyCode".equals(type)){
					if(!TextUtils.isEmpty(response)){
						String[] array=response.split("\\|");
						if(array!=null && array.length==2){
							String weatherCode=array[1];
							queryWeatherInfo(weatherCode);
						}
					}
				}
				else if("weatherCode".equals(type)){
					Utility.handleWeatherResponse(WeatherActivity.this, response);
					runOnUiThread(new Runnable(){
						@Override
						public void run(){
							showWeather();
						}
						
					});
				}
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "update failed", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
	private void showWeather (){
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
		cityNameText.setText(prefs.getString("city_name", ""));
		temp1Text.setText(prefs.getString("temp1", ""));
		temp2Text.setText(prefs.getString("temp2", ""));
		weatherDespText.setText(prefs.getString("weather_desp", ""));
		currentDateText.setText(prefs.getString("current_date", ""));
        //get gif resource from the internet
		String imgAddress=prefs.getString("img1","");
		loadGifFromInternet(this,imgAddress);
		weatherInfoLayout.setVisibility(View.VISIBLE);
		cityNameText.setVisibility(View.VISIBLE);
		Intent intent=new Intent(this, AutoUpdateService.class);
		startService(intent);
	}
    private boolean atNight(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int hour;
        hour = cal.get(Calendar.HOUR_OF_DAY);
        Log.d("hhhh","time:"+hour);
        if(hour<6 || hour>18)
            return true;
        else
            return false;
    }
    private void loadGifFromInternet(Context context, String imgAddress){
        URL imgURL=null;
        int numImg=0;
        if(imgAddress.length()==6){
            numImg=imgAddress.charAt(1);
			numImg=numImg-48;
            Log.d("hhhh",""+numImg);
        }
        try{
            imgURL=new URL("http://m.weather.com.cn/img/a"+numImg+".gif");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Glide.with(context).load(imgURL).asGif().error( R.drawable.failed ).into( weatherImg );
    }
    private void loadImgLocally(Context context,String imgAddress){
        if(imgAddress.length()==6){
            int num=imgAddress.charAt(1);
            Log.v("hhhh",""+num);
        }
        Glide.with(context).load(imgAddress).into(weatherImg);
    }
}
