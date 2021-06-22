package com.rsdt.strukton;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author Dingenis Sieger Sinke
 * @version 1.0
 * @since 20-6-2016
 * Description...
 */
public class Strukton extends Application {

    private static Strukton instance;

    public static Strukton getInstance()
    {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static SharedPreferences getPrefences()
    {
        return PreferenceManager.getDefaultSharedPreferences(instance);
    }


}
