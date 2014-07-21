
package com.andra.weather.android.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Hourly {

    @Expose
    private String chanceoffog;
    @Expose
    private String chanceoffrost;
    @Expose
    private String chanceofhightemp;
    @Expose
    private String chanceofovercast;
    @Expose
    private String chanceofrain;
    @Expose
    private String chanceofremdry;
    @Expose
    private String chanceofsnow;
    @Expose
    private String chanceofsunshine;
    @Expose
    private String chanceofthunder;
    @Expose
    private String chanceofwindy;
    @Expose
    private String cloudcover;
    @SerializedName("DewPointC")
    @Expose
    private String dewPointC;
    @SerializedName("DewPointF")
    @Expose
    private String dewPointF;
    @SerializedName("FeelsLikeC")
    @Expose
    private String feelsLikeC;
    @SerializedName("FeelsLikeF")
    @Expose
    private String feelsLikeF;
    @SerializedName("HeatIndexC")
    @Expose
    private String heatIndexC;
    @SerializedName("HeatIndexF")
    @Expose
    private String heatIndexF;
    @Expose
    private String humidity;
    @Expose
    private String precipMM;
    @Expose
    private String pressure;
    @Expose
    private String tempC;
    @Expose
    private String tempF;
    @Expose
    private String time;
    @Expose
    private String visibility;
    @Expose
    private String weatherCode;
    @Expose
    private List<WeatherDesc> weatherDesc = new ArrayList<WeatherDesc>();
    @Expose
    private List<WeatherIconUrl> weatherIconUrl = new ArrayList<WeatherIconUrl>();
    @SerializedName("WindChillC")
    @Expose
    private String windChillC;
    @SerializedName("WindChillF")
    @Expose
    private String windChillF;
    @Expose
    private String winddir16Point;
    @Expose
    private String winddirDegree;
    @SerializedName("WindGustKmph")
    @Expose
    private String windGustKmph;
    @SerializedName("WindGustMiles")
    @Expose
    private String windGustMiles;
    @Expose
    private String windspeedKmph;
    @Expose
    private String windspeedMiles;

    public String getChanceoffog() {
        return chanceoffog;
    }

    public void setChanceoffog(String chanceoffog) {
        this.chanceoffog = chanceoffog;
    }

    public String getChanceoffrost() {
        return chanceoffrost;
    }

    public void setChanceoffrost(String chanceoffrost) {
        this.chanceoffrost = chanceoffrost;
    }

    public String getChanceofhightemp() {
        return chanceofhightemp;
    }

    public void setChanceofhightemp(String chanceofhightemp) {
        this.chanceofhightemp = chanceofhightemp;
    }

    public String getChanceofovercast() {
        return chanceofovercast;
    }

    public void setChanceofovercast(String chanceofovercast) {
        this.chanceofovercast = chanceofovercast;
    }

    public String getChanceofrain() {
        return chanceofrain;
    }

    public void setChanceofrain(String chanceofrain) {
        this.chanceofrain = chanceofrain;
    }

    public String getChanceofremdry() {
        return chanceofremdry;
    }

    public void setChanceofremdry(String chanceofremdry) {
        this.chanceofremdry = chanceofremdry;
    }

    public String getChanceofsnow() {
        return chanceofsnow;
    }

    public void setChanceofsnow(String chanceofsnow) {
        this.chanceofsnow = chanceofsnow;
    }

    public String getChanceofsunshine() {
        return chanceofsunshine;
    }

    public void setChanceofsunshine(String chanceofsunshine) {
        this.chanceofsunshine = chanceofsunshine;
    }

    public String getChanceofthunder() {
        return chanceofthunder;
    }

    public void setChanceofthunder(String chanceofthunder) {
        this.chanceofthunder = chanceofthunder;
    }

    public String getChanceofwindy() {
        return chanceofwindy;
    }

    public void setChanceofwindy(String chanceofwindy) {
        this.chanceofwindy = chanceofwindy;
    }

    public String getCloudcover() {
        return cloudcover;
    }

    public void setCloudcover(String cloudcover) {
        this.cloudcover = cloudcover;
    }

    public String getDewPointC() {
        return dewPointC;
    }

    public void setDewPointC(String dewPointC) {
        this.dewPointC = dewPointC;
    }

    public String getDewPointF() {
        return dewPointF;
    }

    public void setDewPointF(String dewPointF) {
        this.dewPointF = dewPointF;
    }

    public String getFeelsLikeC() {
        return feelsLikeC;
    }

    public void setFeelsLikeC(String feelsLikeC) {
        this.feelsLikeC = feelsLikeC;
    }

    public String getFeelsLikeF() {
        return feelsLikeF;
    }

    public void setFeelsLikeF(String feelsLikeF) {
        this.feelsLikeF = feelsLikeF;
    }

    public String getHeatIndexC() {
        return heatIndexC;
    }

    public void setHeatIndexC(String heatIndexC) {
        this.heatIndexC = heatIndexC;
    }

    public String getHeatIndexF() {
        return heatIndexF;
    }

    public void setHeatIndexF(String heatIndexF) {
        this.heatIndexF = heatIndexF;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPrecipMM() {
        return precipMM;
    }

    public void setPrecipMM(String precipMM) {
        this.precipMM = precipMM;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTempC() {
        return tempC;
    }

    public void setTempC(String tempC) {
        this.tempC = tempC;
    }

    public String getTempF() {
        return tempF;
    }

    public void setTempF(String tempF) {
        this.tempF = tempF;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public List<WeatherDesc> getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(List<WeatherDesc> weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public List<WeatherIconUrl> getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public void setWeatherIconUrl(List<WeatherIconUrl> weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
    }

    public String getWindChillC() {
        return windChillC;
    }

    public void setWindChillC(String windChillC) {
        this.windChillC = windChillC;
    }

    public String getWindChillF() {
        return windChillF;
    }

    public void setWindChillF(String windChillF) {
        this.windChillF = windChillF;
    }

    public String getWinddir16Point() {
        return winddir16Point;
    }

    public void setWinddir16Point(String winddir16Point) {
        this.winddir16Point = winddir16Point;
    }

    public String getWinddirDegree() {
        return winddirDegree;
    }

    public void setWinddirDegree(String winddirDegree) {
        this.winddirDegree = winddirDegree;
    }

    public String getWindGustKmph() {
        return windGustKmph;
    }

    public void setWindGustKmph(String windGustKmph) {
        this.windGustKmph = windGustKmph;
    }

    public String getWindGustMiles() {
        return windGustMiles;
    }

    public void setWindGustMiles(String windGustMiles) {
        this.windGustMiles = windGustMiles;
    }

    public String getWindspeedKmph() {
        return windspeedKmph;
    }

    public void setWindspeedKmph(String windspeedKmph) {
        this.windspeedKmph = windspeedKmph;
    }

    public String getWindspeedMiles() {
        return windspeedMiles;
    }

    public void setWindspeedMiles(String windspeedMiles) {
        this.windspeedMiles = windspeedMiles;
    }

}
