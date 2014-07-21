/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.andra.weather.android.utility;

/**
 * Defines app-wide constants and utilities
 */
public final class Utils {

    // Weather API key for the application
    public static final String API_KEY = "7b38eb333252a70636f3edd76fde9ca5b63da87b";

    // Separator for latlon
    public static final String SEPARATOR = ",";

    // Debugging tag for the application
    public static final String APPTAG = "WeatherApp";

    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    // Strings used for describing the weather
    public static final String PERCENT_STR = "%";
    public static final String MILLIMETERS_STR = "mm";
    public static final String PRESSURE_STR = "hPa";

    // Arguments set for the fragments
    public static final String ARG_LAT = "lat";
    public static final String ARG_LON = "lon";
    public static final String ARG_SHARED_TEMP = "shared";

    // The identifiers of the three fragments of the application
    public static final int FRAGMENT_TODAY    = 0;
    public static final int FRAGMENT_FORECAST = 1;
    public static final int FRAGMENT_SETTINGS = 2;

}
