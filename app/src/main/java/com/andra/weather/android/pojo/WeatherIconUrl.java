
package com.andra.weather.android.pojo;

import com.google.gson.annotations.Expose;

public class WeatherIconUrl {

    @Expose
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
