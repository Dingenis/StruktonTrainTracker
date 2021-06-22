package com.rsdt.strukton;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;


/**
 * @author Dingenis Sieger Sinke
 * @version 1.0
 * @since 23-6-2016
 * Description...
 */
public class PreferenceFragment extends android.preference.PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    public void setIcon(String value)
    {
        EditTextPreference editTextPreference = (EditTextPreference) findPreference(TrainLocationService.PREFERENCE_ICON);
        editTextPreference.setText(value);
    }

    public static PreferenceFragment newInstance() { return new PreferenceFragment(); }

}
