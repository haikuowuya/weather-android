package com.andra.weather.android.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.andra.weather.android.R;
import com.andra.weather.android.WeatherConfig;
import com.andra.weather.android.adapter.ForecastListAdapter;
import com.andra.weather.android.dialog.ActionShareDialogFragment;
import com.andra.weather.android.pojo.WeatherForecast;
import com.andra.weather.android.utility.Utils;
import com.andra.weather.android.utility.WorldWeatherOnlineApiInterface;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ForecastFragment extends Fragment {

    private WeatherForecast mForecast;

    // Callback to receive the response from the GET method
    Callback<WeatherForecast> updateWeather = new Callback<WeatherForecast>() {
        @Override
        public void success(WeatherForecast forecast, Response response) {
            mForecast = forecast;

            // Set the list adapter for the new forecast data
            ForecastListAdapter adapter = new ForecastListAdapter(
                    ForecastFragment.this.getActivity(), mForecast);
            mForecastList.setAdapter(adapter);
        }

        @Override
        public void failure(RetrofitError retrofitError) {
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.no_response_from_api),
                    Toast.LENGTH_LONG);
        }
    };
    private ListView mForecastList;
    private double mLatitude;
    private double mLongitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forecast, container, false);

        // Set the window's title in the ActionBar
        getActivity().setTitle(getResources().getString(R.string.title_forecast));

        // Set the touch feedback on the list's elements to display a dialog for sharing the data
        mForecastList = (ListView) rootView.findViewById(R.id.forecastList);
        mForecastList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg) {
                ActionShareDialogFragment dialog = new ActionShareDialogFragment();

                // Get the item's string representation from the list's adapter
                String tempToShare = (String) mForecastList.getAdapter().getItem(position);

                // Show the new dialog
                Bundle args = new Bundle();
                args.putString(Utils.ARG_SHARED_TEMP, tempToShare);
                dialog.setArguments(args);
                dialog.show(getActivity().getSupportFragmentManager(), "share");
            }
        });

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

        // Get the forecast from the API
        retrieveWeather();
    }

    public void retrieveWeather() {

        // Create new adapter for the rest requests; specify the base of the URL
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(WeatherConfig.API_BASE_URL)
                .build();

        // Create new service for the API's interface
        WorldWeatherOnlineApiInterface service =
                restAdapter.create(WorldWeatherOnlineApiInterface.class);

        // Build the latlon string
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mLatitude);
        stringBuilder.append(Utils.SEPARATOR);
        stringBuilder.append(mLongitude);

        // Make the GET request asynchronously and set the callback for when it is done
        service.getForecast(stringBuilder.toString(),
                "json", 5, "yes", WeatherConfig.API_KEY, "no", "no", 24, updateWeather);
    }

    // Handle the change of orientation

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        LayoutInflater inflater = (LayoutInflater) getActivity().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View newView = inflater.inflate(R.layout.fragment_forecast, null);
        ViewGroup rootView = (ViewGroup) getView();

        mForecastList = (ListView) newView.findViewById(R.id.forecastList);
        mForecastList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg) {
                ActionShareDialogFragment dialog = new ActionShareDialogFragment();

                // Get the item's string representation from the list's adapter
                String tempToShare = (String) mForecastList.getAdapter().getItem(position);

                // Show the new dialog
                Bundle args = new Bundle();
                args.putString(Utils.ARG_SHARED_TEMP, tempToShare);
                dialog.setArguments(args);
                dialog.show(getActivity().getSupportFragmentManager(), "share");
            }
        });

        retrieveWeather();

        // Remove all the existing views from the root view.
        rootView.removeAllViews();
        rootView.addView(newView);
    }

}
