package com.andra.weather.android.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andra.weather.android.R;
import com.andra.weather.android.pojo.Data;
import com.andra.weather.android.pojo.Hourly;
import com.andra.weather.android.pojo.NearestArea;
import com.andra.weather.android.pojo.WeatherForecast;
import com.andra.weather.android.utility.Utils;
import com.andra.weather.android.utility.WorldWeatherOnlineApiInterface;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TodayFragment extends Fragment {

    private ImageView mWeatherImageView;
    private ImageView mOverlayImageView;
    private TextView mLocationTextView;
    private TextView mTemperatureTextView;
    private TextView mHumidityTextView;
    private TextView mPrecipitationTextView;
    private TextView mPressureTextView;
    private TextView mSpeedTextView;
    private TextView mDirectionTextView;
    private View mDelimiterView;

    private double mLatitude;
    private double mLongitude;

    private String mLengthPreference;
    private String mTemperaturePreference;

    // Callback to receive the response from the GET method
    Callback<WeatherForecast> updateWeather = new Callback<WeatherForecast>() {
        @Override
        public void success(WeatherForecast forecast, Response response) {
            Data forecastData = forecast.getData();
            NearestArea area = forecastData.getNearestArea().get(0);
            Hourly today = forecastData.getWeather().get(0).getHourly().get(0);

            // Get the data of interest from the response
            String region = area.getAreaName().get(0).getValue();
            String country = area.getCountry().get(0).getValue();
            String temperature = mTemperaturePreference.equals(
                    getResources().getString(R.string.weather_celsius_value)) ?
                    today.getTempC() : today.getTempF();
            String description = today.getWeatherDesc().get(0).getValue();
            String precipitation = today.getPrecipMM();
            String humidity = today.getHumidity();
            String pressure = today.getPressure();
            String speed = today.getWindspeedKmph();
            String direction = today.getWinddir16Point();

            // Retrieve the image asynchronously using Picasso
            Uri imageUri = Uri.parse(today.getWeatherIconUrl().get(0).getValue());
            Picasso.with(getActivity()).load(imageUri).into(mWeatherImageView);
            mOverlayImageView.setImageDrawable
                    (getResources().getDrawable(R.drawable.overlay_wsymbols));

            // Set the new values for the widgets
            mLocationTextView.setText(region + ", " + country);
            mTemperatureTextView.setText(temperature + mTemperaturePreference + " | " + description);
            mPrecipitationTextView.setText(" " + precipitation + " " + Utils.MILLIMETERS_STR);
            mHumidityTextView.setText(" " + humidity + " " + Utils.PERCENT_STR);
            mPressureTextView.setText(" " + pressure + " " + Utils.PRESSURE_STR);
            mSpeedTextView.setText(" " + speed + " " + mLengthPreference);
            mDirectionTextView.setText(" " + direction);

            setDetailsDrawables();

            mDelimiterView.setVisibility(View.VISIBLE);

        }

        @Override
        public void failure(RetrofitError retrofitError) {
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.no_response_from_api),
                    Toast.LENGTH_LONG);
        }
    };

    private void setDetailsDrawables() {

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {

            // Show the drawables for the detail widgets
            mPrecipitationTextView.setCompoundDrawablesWithIntrinsicBounds
                    (0, R.drawable.ic_precipitation, 0, 0);
            mHumidityTextView.setCompoundDrawablesWithIntrinsicBounds
                    (0, R.drawable.ic_humidity, 0, 0);
            mPressureTextView.setCompoundDrawablesWithIntrinsicBounds
                    (0, R.drawable.ic_pressure, 0, 0);
            mSpeedTextView.setCompoundDrawablesWithIntrinsicBounds
                    (0, R.drawable.ic_wind_speed, 0, 0);
            mDirectionTextView.setCompoundDrawablesWithIntrinsicBounds
                    (0, R.drawable.ic_wind_direction, 0, 0);
        }
        else {
            // Show the drawables for the detail widgets
            mPrecipitationTextView.setCompoundDrawablesWithIntrinsicBounds
                    (R.drawable.ic_precipitation, 0, 0, 0);
            mHumidityTextView.setCompoundDrawablesWithIntrinsicBounds
                    (R.drawable.ic_humidity, 0, 0, 0);
            mPressureTextView.setCompoundDrawablesWithIntrinsicBounds
                    (R.drawable.ic_pressure, 0, 0, 0);
            mSpeedTextView.setCompoundDrawablesWithIntrinsicBounds
                    (R.drawable.ic_wind_speed, 0, 0, 0);
            mDirectionTextView.setCompoundDrawablesWithIntrinsicBounds
                    (R.drawable.ic_wind_direction, 0, 0, 0);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_today, container, false);

        // Set the window's title in the ActionBar
        getActivity().setTitle(getResources().getString(R.string.title_today));

        instantiateWidgets(rootView);

        // Retrieve location from the extras set by the activity
        mLatitude = getArguments().getDouble(Utils.ARG_LAT);
        mLongitude = getArguments().getDouble(Utils.ARG_LON);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Announce the parent activity that this fragment is now in command
        getActivity().supportInvalidateOptionsMenu();

        // Get the current preferences regarding the units of measurement
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mLengthPreference = sharedPref.getString(getResources().getString(R.string.key_weather_length_preference), "");
        mTemperaturePreference = sharedPref.getString(getResources().getString(R.string.key_weather_temperature_preference), "");

        // Get the forecast from the API
        retrieveWeather();
    }

    private void instantiateWidgets(View rootView) {
        // Handles to UI widgets
        mWeatherImageView = (ImageView) rootView.findViewById(R.id.weatherImage);
        mOverlayImageView = (ImageView) rootView.findViewById(R.id.overlayImage);
        mLocationTextView = (TextView) rootView.findViewById(R.id.locationText);
        mTemperatureTextView = (TextView) rootView.findViewById(R.id.temperatureText);
        mHumidityTextView = (TextView) rootView.findViewById(R.id.humidityText);
        mPrecipitationTextView = (TextView) rootView.findViewById(R.id.precipitationText);
        mPressureTextView = (TextView) rootView.findViewById(R.id.pressureText);
        mSpeedTextView = (TextView) rootView.findViewById(R.id.speedText);
        mDirectionTextView = (TextView) rootView.findViewById(R.id.directionText);
        mDelimiterView = rootView.findViewById(R.id.horizontalDelimiter);
    }

    public void retrieveWeather() {

        // Create new adapter for the rest requests; specify the base of the URL
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.worldweatheronline.com/premium/v1")
                .build();

        // Create new service for the API's interface
        WorldWeatherOnlineApiInterface service =
                restAdapter.create(WorldWeatherOnlineApiInterface.class);

        // Build the latlon string
        StringBuilder latlonBuilder = new StringBuilder();
        latlonBuilder.append(mLatitude);
        latlonBuilder.append(Utils.SEPARATOR);
        latlonBuilder.append(mLongitude);

        // Make the GET request asynchronously and set the callback for when it is done
        service.getForecast(latlonBuilder.toString(),
                "json", 1, "yes", Utils.API_KEY, "no", "no", 24, updateWeather);
    }

    // Handle the change of orientation
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View newView = inflater.inflate(R.layout.fragment_today, null);
        ViewGroup rootView = (ViewGroup) getView();

        instantiateWidgets(newView);

        retrieveWeather();

        // Remove all the existing views from the root view.
        rootView.removeAllViews();
        rootView.addView(newView);
    }
}
