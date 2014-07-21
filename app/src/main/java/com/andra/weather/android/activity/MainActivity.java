package com.andra.weather.android.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.andra.weather.android.R;
import com.andra.weather.android.WeatherApplication;
import com.andra.weather.android.adapter.DrawerListAdapter;
import com.andra.weather.android.fragment.ForecastFragment;
import com.andra.weather.android.fragment.SettingsFragment;
import com.andra.weather.android.fragment.TodayFragment;
import com.andra.weather.android.utility.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;


public class MainActivity extends ActionBarActivity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    private WeatherApplication mApp;

    private ForecastFragment   mForecastFragment;
    private TodayFragment      mTodayFragment;
    private SettingsFragment mSettingsFragment;

    // Handles to UI widgets
    private FrameLayout  mFragmentContainer;
    private ListView     mDrawerList;
    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    private LocationClient    mLocationClient;
    private Location          mCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the default preferences from the XML file, only if there aren't other ones stored already
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        mApp = (WeatherApplication) getApplication();

        mForecastFragment = mApp.getForecastFragment();
        mTodayFragment = mApp.getTodayFragment();
        mSettingsFragment = mApp.getSettingsFragment();

        // Get the UI handles
        mFragmentContainer = (FrameLayout) findViewById(R.id.fragmentContainer);

        mDrawerList = (ListView) findViewById(R.id.leftDrawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        setupDrawer();

        // Enable the home button in the ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Create a new location client, using the enclosing class to handle callbacks
        mLocationClient = new LocationClient(this, this, this);
    }

    private void setupDrawer() {
        // Used to tie together the DrawerLayout's functionality and the ActionBar
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_navigation_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                // Change the title of the screen to the one of the current fragment
                setTitle(getResources().getString(
                        (mApp.getCurrentFragment() == Utils.FRAGMENT_TODAY) ?
                                R.string.title_today : R.string.title_forecast));

                // Makes a call to onPrepareOptionsMenu
                supportInvalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                // Change the title of the screen
                setTitle(getResources().getString(R.string.app_name));

                // Makes a call to onPrepareOptionsMenu
                supportInvalidateOptionsMenu();
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        // Set the adapter for the drawer's list
        mDrawerList.setAdapter(new DrawerListAdapter(this));

        // Set the drawer's list's click listener
        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                // If the requested item is different from the current one, change it
                if (position != mApp.getCurrentFragment()) {
                    mApp.setPreviousFragment(mApp.getCurrentFragment());
                    mApp.setCurrentFragment(position);
                    getLocation();
                }

                // Automatically close the drawer after an item selection
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });
    }

    /**
     * Calls getLastLocation() to get the current location
     */
    public void getLocation() {

        // If Google Play Services is available
        if (servicesConnected()) {

            // Update the current location
            mCurrentLocation = mLocationClient.getLastLocation();

            // Prepare the fragment to display, according to the tracker
            Fragment fragment;

            // Provide the fragment with location data
            if (mApp.getCurrentFragment() != Utils.FRAGMENT_SETTINGS) {
                if (mApp.getCurrentFragment() == Utils.FRAGMENT_TODAY) {
                    fragment = mTodayFragment;
                }
                else {
                    fragment = mForecastFragment;
                }

                Bundle args;

                if (fragment.getArguments() != null) {
                    args = fragment.getArguments();
                    args.clear();
                    args.putDouble(Utils.ARG_LAT, mCurrentLocation.getLatitude());
                    args.putDouble(Utils.ARG_LON, mCurrentLocation.getLongitude());
                } else {
                    args = new Bundle();
                    args.putDouble(Utils.ARG_LAT, mCurrentLocation.getLatitude());
                    args.putDouble(Utils.ARG_LON, mCurrentLocation.getLongitude());
                    fragment.setArguments(args);
                }
            }
            else {
                fragment = mSettingsFragment;
            }

            // Add the fragment to the 'fragmentContainer' FrameLayout
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();

        }
    }

    // Handles the action of the back button pressing
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            // From Today screen, always exit on back
            if (mApp.getCurrentFragment() == Utils.FRAGMENT_TODAY) {
                this.finish();
                return false;
            }

            // From Forecast screen, always back to today
            else if (mApp.getCurrentFragment() == Utils.FRAGMENT_FORECAST) {
                mApp.setCurrentFragment(Utils.FRAGMENT_TODAY);
                getLocation();
                return false;
            }

            // From Forecast screen, back to the previous fragment
            else {
                mApp.setCurrentFragment(mApp.getPreviousFragment());
                mApp.setPreviousFragment(Utils.FRAGMENT_TODAY);
                getLocation();
                return false;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // If the current fragment is the settings one, hide the settings action
        if (mApp.getCurrentFragment() == Utils.FRAGMENT_SETTINGS) {
            menu.findItem(R.id.action_settings).setVisible(false);
        }

        else {
            // Enable the drawer, for when a fragment is restored
            mDrawerToggle.setDrawerIndicatorEnabled(true);

            // If the nav drawer is open, hide settings action
            boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
            menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        }
        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle the settings item
        switch (item.getItemId()) {
            case R.id.action_settings:
                // Update the current fragment and the action bar items
                mApp.setPreviousFragment(mApp.getCurrentFragment());
                mApp.setCurrentFragment(Utils.FRAGMENT_SETTINGS);
                supportInvalidateOptionsMenu();
                mDrawerToggle.setDrawerIndicatorEnabled(false);

                // Display the PreferenceFragment as the main content.
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, mSettingsFragment);
                transaction.commit();

                return true;
            case android.R.id.home:
                // Return to the previous fragment
                mApp.setCurrentFragment(mApp.getPreviousFragment());
                mApp.setPreviousFragment(Utils.FRAGMENT_TODAY);
                getLocation();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mLocationClient.connect();
    }

    @Override
    protected void onStop() {

        // After disconnect() is called, the client is considered "dead".
        mLocationClient.disconnect();

        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get the latest location, every time the activity is resumed
        if (mLocationClient.isConnected()) {
            getLocation();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Update the drawer for the new configuration
        mDrawerToggle.onConfigurationChanged(newConfig);


        if (mApp.getCurrentFragment() == Utils.FRAGMENT_SETTINGS) {
            mDrawerToggle.setDrawerIndicatorEnabled(false);
        }

        getLocation();
    }


    @Override
    public void onConnected(Bundle bundle) {
        // When the client is connected, proceed with retrieving the location and setting up the fragment
        getLocation();
    }

    @Override
    public void onDisconnected() {
        // Reconnect the client.
        mLocationClient.connect();
    }



    /**
     * Handles the result of the request to Google Play services resolution
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // Choose what to do based on the request code
        switch (requestCode) {

            // If the request code matches the code sent in onConnectionFailed
            case Utils.CONNECTION_FAILURE_RESOLUTION_REQUEST :

                switch (resultCode) {
                    // If Google Play services resolved the problem
                    case Activity.RESULT_OK:

                        // Log the result
                        Log.d(Utils.APPTAG, getString(R.string.resolved));

                        // Get current location
                        getLocation();

                        break;

                    // If any other result was returned by Google Play services
                    default:
                        // Log the result
                        Log.d(Utils.APPTAG, getString(R.string.no_resolution));

                        // TODO: unable to resolve issue

                        break;
                }

                // If any other request code was received
            default:
                // Report that this Activity received an unknown requestCode
                Log.d(Utils.APPTAG,
                        getString(R.string.unknown_activity_request_code, requestCode));

                break;
        }
    }

    /**
     * Verify that Google Play services is available before making a request.
     */
    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d(Utils.APPTAG, getString(R.string.play_services_available));

            // Continue
            return true;
            // Google Play services was not available for some reason
        } else {
            // Display an error dialog
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
            if (dialog != null) {
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(dialog);
                errorFragment.show(getSupportFragmentManager(), Utils.APPTAG);
            }
            return false;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {

                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        Utils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

                /*
                * Thrown if Google Play services canceled the original
                * PendingIntent
                */

            } catch (IntentSender.SendIntentException e) {

                // Log the error
                e.printStackTrace();
            }
        } else {

            // If no resolution is available, display a dialog to the user with the error.
            showErrorDialog(connectionResult.getErrorCode());
        }

    }

    private void showErrorDialog(int errorCode) {

        // Get the error dialog from Google Play services
        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                errorCode,
                this,
                Utils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

        // If Google Play services can provide an error dialog
        if (errorDialog != null) {

            // Create a new DialogFragment in which to show the error dialog
            ErrorDialogFragment errorFragment = new ErrorDialogFragment();

            // Set the dialog in the DialogFragment
            errorFragment.setDialog(errorDialog);

            // Show the error dialog in the DialogFragment
            errorFragment.show(getSupportFragmentManager(), Utils.APPTAG);
        }
    }

    public static class ErrorDialogFragment extends DialogFragment {
        // Global field to contain the error dialog
        private Dialog mDialog;
        // Default constructor. Sets the dialog field to null
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }
        // Set the dialog to display
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }
        // Return a Dialog to the DialogFragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }

}
