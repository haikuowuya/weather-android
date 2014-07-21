
package com.andra.weather.android.pojo;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class NearestArea {

    @Expose
    private List<AreaName> areaName = new ArrayList<AreaName>();
    @Expose
    private List<Country> country = new ArrayList<Country>();
    @Expose
    private String latitude;
    @Expose
    private String longitude;
    @Expose
    private List<Region> region = new ArrayList<Region>();

    public List<AreaName> getAreaName() {
        return areaName;
    }

    public void setAreaName(List<AreaName> areaName) {
        this.areaName = areaName;
    }

    public List<Country> getCountry() {
        return country;
    }

    public void setCountry(List<Country> country) {
        this.country = country;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<Region> getRegion() {
        return region;
    }

    public void setRegion(List<Region> region) {
        this.region = region;
    }

}
