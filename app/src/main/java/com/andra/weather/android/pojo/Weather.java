
package com.andra.weather.android.pojo;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Weather {

    @Expose
    private List<Astronomy> astronomy = new ArrayList<Astronomy>();
    @Expose
    private String date;
    @Expose
    private List<Hourly> hourly = new ArrayList<Hourly>();
    @Expose
    private String maxtempC;
    @Expose
    private String maxtempF;
    @Expose
    private String mintempC;
    @Expose
    private String mintempF;

    public List<Astronomy> getAstronomy() {
        return astronomy;
    }

    public void setAstronomy(List<Astronomy> astronomy) {
        this.astronomy = astronomy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Hourly> getHourly() {
        return hourly;
    }

    public void setHourly(List<Hourly> hourly) {
        this.hourly = hourly;
    }

    public String getMaxtempC() {
        return maxtempC;
    }

    public void setMaxtempC(String maxtempC) {
        this.maxtempC = maxtempC;
    }

    public String getMaxtempF() {
        return maxtempF;
    }

    public void setMaxtempF(String maxtempF) {
        this.maxtempF = maxtempF;
    }

    public String getMintempC() {
        return mintempC;
    }

    public void setMintempC(String mintempC) {
        this.mintempC = mintempC;
    }

    public String getMintempF() {
        return mintempF;
    }

    public void setMintempF(String mintempF) {
        this.mintempF = mintempF;
    }

}
