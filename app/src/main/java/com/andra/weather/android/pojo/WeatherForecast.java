
package com.andra.weather.android.pojo;

import com.google.gson.annotations.Expose;

public class WeatherForecast {

    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
