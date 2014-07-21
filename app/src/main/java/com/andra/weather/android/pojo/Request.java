
package com.andra.weather.android.pojo;

import com.google.gson.annotations.Expose;

public class Request {

    @Expose
    private String query;
    @Expose
    private String type;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
