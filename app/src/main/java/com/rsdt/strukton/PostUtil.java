package com.rsdt.strukton;

import android.content.SharedPreferences;
import android.location.Location;

import com.google.gson.JsonObject;
import com.rsdt.anl.UrlBuilder;
import com.rsdt.anl.WebRequest;
import com.rsdt.anl.WebRequestMethod;

/**
 * @author Dingenis Sieger Sinke
 * @version 1.0
 * @since 20-6-2016
 * Description...
 */
public class PostUtil {




    public static boolean postTrainLocation(Location location, WebRequest.OnWebRequestCompletedCallback callback)
    {
        SharedPreferences preferences = Strukton.getPrefences();

        String name = preferences.getString(TrainLocationService.PREFERENCE_NAME, "");

        if(name != null && !name.isEmpty())
        {
            JsonObject object = new JsonObject();
            object.addProperty("key", "123");
            object.addProperty("name", name);
            object.addProperty("latitude", location.getLatitude());
            object.addProperty("longitude", location.getLongitude());
            object.addProperty("icon", Integer.parseInt(preferences.getString("pref_icon", "1")));
            object.addProperty("tail", "#000000");

            WebRequest request = new WebRequest.Builder()
                    .setUrl(new UrlBuilder().append("http://strukton.area348.nl/API/train").build())
                    .setMethod(WebRequestMethod.POST)
                    .setData(object.toString())
                    .create();
            request.executeAsync(callback);
            return true;
        }
        return false;
    }

}
