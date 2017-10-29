package com.example.compass.model;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/10/22.
 */

public class WeatherCondition {
    private String tempLow;
    private String tempHigh;
    private String publishTime;
    private String weatherDesp;
    private String img1;
    private String img2;
    private Bitmap bm;
    public WeatherCondition(){

    }
    public String getTempLow(){
        return this.tempLow;
    }
    public String getTempHigh(){
        return this.tempHigh;
    }
    public String getPublishTime(){
        return this.publishTime;
    }
    public String getWeatherDesp(){
        return this.weatherDesp;
    }
    public String getURLImg1(){
        return this.img1;
    }
    public String getURLImg2(){return this.img2;}
    public Bitmap getBm(){
        return this.bm;
    }
    public void setTempLow(String tempLow){
        this.tempLow=tempLow;
    }
    public void setTempHigh(String tempHigh){
        this.tempHigh=tempHigh;
    }
    public void setPublishTime(String publishTime){
        this.publishTime=publishTime;
    }
    public void setWeatherDesp(String weatherDesp){
        this.weatherDesp=weatherDesp;
    }
    public void setURLImg1(String img1){
        this.img1=img1;
    }
    public void setURLImg2(String img2){
        this.img2=img2;
    }
    public void setBm(Bitmap bm){
        this.bm=bm;
    }

}
