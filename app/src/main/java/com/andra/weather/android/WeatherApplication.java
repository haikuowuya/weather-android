package com.andra.weather.android;

import android.app.Application;

import com.andra.weather.android.fragment.ForecastFragment;
import com.andra.weather.android.fragment.SettingsFragment;
import com.andra.weather.android.fragment.TodayFragment;
import com.andra.weather.android.utility.Utils;

public class WeatherApplication extends Application {
    private static WeatherApplication mInstance;

    private static ForecastFragment mForecastFragment;
    private static TodayFragment mTodayFragment;
    private static SettingsFragment mSettingsFragment;

    private static int mCurrentFragment;
    private static int mPreviousFragment;

    public WeatherApplication()
    {
        mInstance = this;
    }

    public static ForecastFragment getForecastFragment() {
        return mForecastFragment;
    }

    public static TodayFragment getTodayFragment() {
        return mTodayFragment;
    }

    public static WeatherApplication getInstance()
    {
        return mInstance;
    }

    public SettingsFragment getSettingsFragment() {
        return mSettingsFragment;
    }

    public int getCurrentFragment() {
        return mCurrentFragment;
    }

    public void setCurrentFragment(int fragment) {
        this.mCurrentFragment = fragment;
    }

    public int getPreviousFragment() {
        return mPreviousFragment;
    }

    public void setPreviousFragment(int fragment) {
        this.mPreviousFragment = fragment;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        mInstance = this;

        mForecastFragment = new ForecastFragment();
        mTodayFragment = new TodayFragment();
        mSettingsFragment = new SettingsFragment();

        // Keep track of the current and previous fragments
        mCurrentFragment = Utils.FRAGMENT_TODAY;
        mPreviousFragment = Utils.FRAGMENT_TODAY;
    }

}