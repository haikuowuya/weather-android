
package com.andra.weather.android.pojo;

import com.google.gson.annotations.Expose;

public class Astronomy {

    @Expose
    private String moonrise;
    @Expose
    private String moonset;
    @Expose
    private String sunrise;
    @Expose
    private String sunset;

    public String getMoonrise() {
        return moonrise;
    }

    public void setMoonrise(String moonrise) {
        this.moonrise = moonrise;
    }

    public String getMoonset() {
        return moonset;
    }

    public void setMoonset(String moonset) {
        this.moonset = moonset;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

}
