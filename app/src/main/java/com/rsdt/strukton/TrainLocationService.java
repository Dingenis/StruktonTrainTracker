package com.rsdt.strukton;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.rsdt.anl.WebRequest;
import com.rsdt.anl.WebResponse;

import java.util.Calendar;


/**
 * @author Dingenis Sieger Sinke
 * @version 1.0
 * @since 20-6-2016
 * Description...
 */
public class TrainLocationService extends Service implements LocationListener, SharedPreferences.OnSharedPreferenceChangeListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String PREFERENCE_LOCATION_UPDATES = "pref_location_updates";

    public static final String PREFERENCE_NAME = "pref_name";

    public static final String PREFERENCE_ICON = "pref_icon";

    public static final String PREFERENCE_COLOR = "pref_color";

    public static final String PREFERENCE_MODE = "pref_mode";

    public static final int REFERSH_DAY_TIME = 10 * 60 * 1000;

    public static final int REFERSH_NIGHT_TIME = 2 * 60 * 1000;

    /**
     * The client for requesting the location updates.
     * */
    private GoogleApiClient client;

    /**
     * The request that is determining our updates.
     * */
    private LocationRequest request;

    private Location lastLocation;

    private boolean missingSettings = false;

    private int refreshRate;

    @Override
    public void onCreate() {
        super.onCreate();

        initialize();
    }


    private void initialize()
    {
        buildGoogleApiClient();
        createRequest();

        Strukton.getPrefences().registerOnSharedPreferenceChangeListener(this);
    }

    /**
     * Builds the GoogleApiClient.
     * */
    protected synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        client.connect();
    }

    private void createRequest()
    {
        SharedPreferences preferences = Strukton.getPrefences();

        request = new LocationRequest();

        refreshRate = getRecommendRate();

        String text = "";
        switch (refreshRate)
        {
            case REFERSH_DAY_TIME:
                text = "EVERY 10 MINUTES";
                break;
            case REFERSH_NIGHT_TIME:
                text = "EVERY 2 MINUTES";
                break;
        }

        SharedPreferences.Editor editor = Strukton.getPrefences().edit();
        editor.putString(TrainLocationService.PREFERENCE_MODE, text);
        editor.apply();


        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private int getRecommendRate()
    {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);

        float minutes = hours * 60;

        if(minutes > (7 * 60) && minutes < (22.5f * 60))
        {
            return REFERSH_DAY_TIME;
        }
        else
        {
            return REFERSH_NIGHT_TIME;
        }
    }


    private boolean hasRateChanged()
    {
        int newRate = getRecommendRate();

        boolean changed = !(newRate == refreshRate);
        if(changed) refreshRate = newRate;
        return changed;
    }

    public void startUpdates()
    {
        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
    }

    public void stopUpdates()
    {
        LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
    }

    public void restartUpdates()
    {
        stopUpdates();
        startUpdates();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key)
        {
            case PREFERENCE_NAME:
                missingSettings = false;
                restartUpdates();
                break;
            case PREFERENCE_ICON:
                restartUpdates();
                break;
            case PREFERENCE_LOCATION_UPDATES:
                if(sharedPreferences.getBoolean(key, true))
                {
                    startUpdates();
                    showLocationUpdatesOnNotification();
                }
                else
                {
                    stopUpdates();
                    showLocationUpdatesOffNotification();
                }
                break;
        }
    }

    private void showLocationUpdatesOnNotification()
    {
        String content;
        if(lastLocation != null)
        {
            content = lastLocation.getLatitude() + " , " + lastLocation.getLongitude();
        }
        else
        {
            content = "Waiting for first location to be sent, interval is " + refreshRate / 1000 / 60 + "minutes";
        }

        SharedPreferences preferences = Strukton.getPrefences();
        Notification notification = new NotificationCompat.Builder(TrainLocationService.this)
                .setContentTitle("Stuktron - " + preferences.getString(PREFERENCE_NAME, "Unknown"))
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_action_place)
                .setColor(Color.parseColor("#669900"))
                .build();

        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(2, notification);
    }

    private void showLocatioinUpdatesOnButPreferenceMissingNotification()
    {
        Intent result = new Intent(this, MainActivity.class);
        PendingIntent intent = PendingIntent.getService(this, 0, result,PendingIntent.FLAG_UPDATE_CURRENT);

        SharedPreferences preferences = Strukton.getPrefences();
        Notification notification = new NotificationCompat.Builder(TrainLocationService.this)
                .setContentTitle("Stuktron - " + preferences.getString(PREFERENCE_NAME, "Unknown"))
                .setContentText("Train's location is not being send, please set all the settings, tab to resolve")
                .setSmallIcon(R.drawable.ic_action_place)
                .setColor(Color.parseColor("#cc3300"))
                .setContentIntent(intent)
                .build();

        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(2, notification);
        missingSettings = true;
    }

    private void showLocationUpdatesOffNotification()
    {
        Intent result = new Intent(this, MainActivity.class);
        PendingIntent intent = PendingIntent.getService(this, 0, result,PendingIntent.FLAG_UPDATE_CURRENT);
        SharedPreferences preferences = Strukton.getPrefences();
        Notification notification = new NotificationCompat.Builder(TrainLocationService.this)
                .setContentTitle("Stuktron - " + preferences.getString(PREFERENCE_NAME, "Unknown"))
                .setContentText("Train's location is not being send, tab to resolve")
                .setSmallIcon(R.drawable.ic_action_place)
                .setColor(Color.parseColor("#ffcc99"))
                .setContentIntent(intent)
                .build();

        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(2, notification);
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;

        if(hasRateChanged())
        {
            createRequest();
            restartUpdates();
        }

        SharedPreferences preferences = Strukton.getPrefences();

        if(preferences.getBoolean(PREFERENCE_LOCATION_UPDATES, true))
        {
            boolean succes = PostUtil.postTrainLocation(location, new WebRequest.OnWebRequestCompletedCallback() {
                @Override
                public void onWebRequestCompleted(WebResponse response) {
                    showLocationUpdatesOnNotification();
                }
            });
            if(!succes)
            {
                showLocatioinUpdatesOnButPreferenceMissingNotification();
            }
        }
        else
        {
            showLocationUpdatesOffNotification();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        SharedPreferences preferences = Strukton.getPrefences();
        if(preferences.getBoolean(PREFERENCE_LOCATION_UPDATES, true))
        {
            startUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("TrainLocationService", "Connection suspended, code: " + i);
        SharedPreferences preferences = Strukton.getPrefences();

        Notification notification = new NotificationCompat.Builder(TrainLocationService.this)
                .setContentTitle("Stuktron - " + "Connection Suspended")
                .setContentText("Not sending cause of connection suspended.")
                .setSmallIcon(R.drawable.ic_action_place)
                .setColor(Color.parseColor("#cc3300"))
                .build();

        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(2, notification);

        buildGoogleApiClient();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("TrainLocationService", connectionResult.getErrorMessage());

        SharedPreferences preferences = Strukton.getPrefences();
        Notification notification = new NotificationCompat.Builder(TrainLocationService.this)
                .setContentTitle("Stuktron - " + "Connection failed")
                .setContentText("Not sending cause of connection failure.")
                .setSmallIcon(R.drawable.ic_action_place)
                .setColor(Color.parseColor("#cc3300"))
                .build();

        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(2, notification);

        buildGoogleApiClient();
    }
}
