package com.andra.weather.android.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andra.weather.android.R;
import com.andra.weather.android.pojo.Hourly;
import com.andra.weather.android.pojo.WeatherForecast;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ForecastListAdapter extends BaseAdapter {

    String[] weekdays = { "Sunday",
                          "Monday",
                          "Tuesday",
                          "Wednesday",
                          "Thursday",
                          "Friday",
                          "Saturday"};

    private Context mContext;
    private WeatherForecast mForecast;

    private String mTemperaturePreference;

    public ForecastListAdapter(Context context, WeatherForecast forecast) {
        super();
        mContext = context;
        mForecast = forecast;

        // Get the preference for the weather unit of measurement
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        mTemperaturePreference = sharedPref.getString(
                mContext.getResources().getString(R.string.key_weather_temperature_preference), "");
    }

    @Override
    public int getCount() {

        // How many elements are in the list
        return mForecast.getData().getWeather().size();
    }

    // Gets the String representation of the element in that position
    @Override
    public Object getItem(int position) {
        String date =  mForecast.getData().getWeather().get(position).getDate();

        String temperature =
                mTemperaturePreference.
                        equals(mContext.getResources().getString(R.string.weather_celsius_value)) ?
                mForecast.getData().getWeather().get(position).getHourly().get(0).getTempC() :
                mForecast.getData().getWeather().get(position).getHourly().get(0).getTempF();

        String description = mForecast.getData().getWeather().
                get(position).getHourly().get(0).getWeatherDesc().get(0).getValue();

        return ( "On " + date +
                " the temperature will be " + temperature + mTemperaturePreference +
                " and the prognosis is: " + description);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        int orientation = mContext.getResources().getConfiguration().orientation;

        ViewHolder holder;
        Hourly weather = mForecast.getData().getWeather().get(position).getHourly().get(0);

        if (convertView == null) {
            // inflate the layout for each item of listView
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.forecast_list_item, viewGroup, false);
            holder = new ViewHolder();

            holder.dayTextView         = (TextView)  convertView.findViewById(R.id.dayText);
            holder.descriptionTextView = (TextView)  convertView.findViewById(R.id.descriptionText);
            holder.weatherImageView    = (ImageView) convertView.findViewById(R.id.weatherImage);

            // In landscape mode there is no TextView for the temperature
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                holder.temperatureTextView = (TextView) convertView.findViewById(R.id.temperatureText);
            }

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        // Get the image asynchronously with Picasso
        Picasso.with(mContext).
                load(Uri.parse(weather.getWeatherIconUrl().get(0).getValue())).
                into(holder.weatherImageView);

        // Parse the date into a weekday format
        SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date;
        try {
            date = sdf.parse(mForecast.getData().getWeather().get(position).getDate());
            calendar.setTime(date);
            int weekday = calendar.get(Calendar.DAY_OF_WEEK);

            // In landscape mode there is no TextView for the temperature
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                holder.dayTextView.setText(weekdays[weekday - 1]);
                holder.temperatureTextView.setText(
                        (mTemperaturePreference.equals(
                                mContext.getResources().getString(R.string.weather_celsius_value)) ?
                                weather.getTempC() : weather.getTempF())
                        + mTemperaturePreference);
            }
            else {
                holder.dayTextView.setText(weekdays[weekday - 1] + " | " +
                        (mTemperaturePreference.equals(
                                mContext.getResources().getString(R.string.weather_celsius_value)) ?
                                weather.getTempC() : weather.getTempF())
                        + mTemperaturePreference);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.descriptionTextView.setText(weather.getWeatherDesc().get(0).getValue());

        return convertView;
    }

    private static class ViewHolder {
        TextView dayTextView;
        TextView temperatureTextView;
        TextView descriptionTextView;
        ImageView weatherImageView;
    }
}
