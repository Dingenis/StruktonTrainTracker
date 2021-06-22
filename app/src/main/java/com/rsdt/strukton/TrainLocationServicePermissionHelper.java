package com.rsdt.strukton;

import android.*;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * @author Dingenis Sieger Sinke
 * @version 1.0
 * @since 14-3-2016
 * Class providing helper tools for the LocationService.
 */
public class TrainLocationServicePermissionHelper {

    public static boolean isStarted = false;

    /**
     * Defines the permission request code for the LOCATION.
     * */
    public static final int PERMISSION_GROUP_LOCATION = 2;

    public static int hasPermission(Context context, String permission)
    {
        return ContextCompat.checkSelfPermission(context, permission);
    }

    /**
     * Checks if the LocationService can be started.
     *
     * @param activity The Activity where the LocationService will be started on.
     * @return The value indicating if the LocationService can be started.
     */
    public static boolean canStart(Activity activity)
    {
        /**
         * Check if we have the LOCATION permissions.
         * */
        if(hasPermission(activity, android.Manifest.permission_group.LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_GROUP_LOCATION);
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Checks if the permission is granted, of a permission request result
     *
     * @param requestCode The request code of the request.
     * @param permissions The permissions requested.
     * @param grantResults The permissions granted.
     * @return The value indicating if the permission is grantend.
     */
    public static boolean hasPermissionOfPermissionRequestResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case PERMISSION_GROUP_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
                else
                {
                    return false;
                }
        }
        return false;
    }




}
