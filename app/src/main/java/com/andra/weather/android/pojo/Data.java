
package com.andra.weather.android.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data {

    @SerializedName("nearest_area")
    @Expose
    private List<NearestArea> nearestArea = new ArrayList<NearestArea>();
    @Expose
    private List<Request> request = new ArrayList<Request>();
    @Expose
    private List<Weather> weather = new ArrayList<Weather>();

    public List<NearestArea> getNearestArea() {
        return nearestArea;
    }

    public void setNearestArea(List<NearestArea> nearestArea) {
        this.nearestArea = nearestArea;
    }

    public List<Request> getRequest() {
        return request;
    }

    public void setRequest(List<Request> request) {
        this.request = request;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

}
