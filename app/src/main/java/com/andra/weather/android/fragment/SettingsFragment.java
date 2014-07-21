package com.andra.weather.android.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.preference.PreferenceFragment;

import com.andra.weather.android.R;

public class SettingsFragment extends PreferenceFragment {

    private Preference mLengthPreference;
    private Preference mTemperaturePreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        // Set the window's title in the ActionBar
        getActivity().setTitle(getResources().getString(R.string.weather_list_preference));

        // Get the current preference
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String lengthPreferenceValue = sharedPref.getString(getResources().getString(R.string.key_weather_length_preference), "");
        String temperaturePreferenceValue = sharedPref.getString(getResources().getString(R.string.key_weather_temperature_preference), "");

        // Set the preview for the preferences in the list
        mLengthPreference = findPreference(getResources().getString(R.string.key_weather_length_preference));
        mLengthPreference.setSummary(lengthPreferenceValue.equals(getResources().getString(R.string.weather_meters_value)) ? getResources().getString(R.string.weather_meters_preference) : getResources().getString(R.string.weather_miles_preference));

        mTemperaturePreference = findPreference(getResources().getString(R.string.key_weather_temperature_preference));
        mTemperaturePreference.setSummary(temperaturePreferenceValue.equals(getResources().getString(R.string.weather_celsius_value)) ? getResources().getString(R.string.weather_celsius_preference) : getResources().getString(R.string.weather_fahrenheit_preference));

        // Change the previews when the preferences change
        mLengthPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newPref) {
                mLengthPreference.setSummary(((String) newPref).equals(getResources().getString(R.string.weather_meters_value)) ? getResources().getString(R.string.weather_meters_preference) : getResources().getString(R.string.weather_miles_preference));

                return true;
            }
        });

        mTemperaturePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newPref) {
                mTemperaturePreference.setSummary(((String) newPref).equals(getResources().getString(R.string.weather_celsius_value)) ? getResources().getString(R.string.weather_celsius_preference) : getResources().getString(R.string.weather_fahrenheit_preference));

                return true;
            }
        });
    }

}
