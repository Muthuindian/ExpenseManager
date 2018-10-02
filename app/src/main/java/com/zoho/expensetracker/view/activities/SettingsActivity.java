package com.zoho.expensetracker.view.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.zoho.expensetracker.R;

public class SettingsActivity extends PreferenceActivity{

    public static final String KEY_VALUE_TYPE = "value_type";

    private SharedPreferences.OnSharedPreferenceChangeListener mSharedPrefChangeListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                public void onSharedPreferenceChanged(SharedPreferences sharedPrefs, String key) {
                    updateSinglePreference(findPreference(key),key );
                }
            };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(mSharedPrefChangeListener);
        updatePreferences();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(mSharedPrefChangeListener);
    }

    private void updatePreferences() {
        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); ++i) {
            Preference pref = getPreferenceScreen().getPreference(i);
            if (pref instanceof PreferenceGroup) {
                PreferenceGroup prefGroup = (PreferenceGroup) pref;
                for (int j = 0; j < prefGroup.getPreferenceCount(); ++j) {
                    Preference singlePref = prefGroup.getPreference(j);
                    updateSinglePreference(singlePref, singlePref.getKey());
                }
            } else {
                updateSinglePreference(pref, pref.getKey());
            }
        }
    }

    private void updateSinglePreference(Preference pref, String key) {
        if (pref == null) return;
        SharedPreferences sharedPrefs = getPreferenceManager().getSharedPreferences();
        String defaultValue = getResources().getString(R.string.default_string);
        if (pref instanceof ListPreference) {
            ListPreference listPref = (ListPreference) pref;
            listPref.setSummary(listPref.getEntry() + " ("
                    + sharedPrefs.getString(key, defaultValue) + ")");
            return;
        }
        pref.setSummary(sharedPrefs.getString(key, defaultValue));
    }

}
