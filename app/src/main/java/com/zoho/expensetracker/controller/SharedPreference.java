package com.zoho.expensetracker.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreference {

    private SharedPreferences mySharedPreference;
    private SharedPreferences.Editor mySharedEditor;
    public static final String KEY_VALUE_TYPE = "pref_currency";


    public SharedPreference(Context aContext) {
        mySharedPreference = PreferenceManager.getDefaultSharedPreferences(aContext);
        mySharedEditor = mySharedPreference.edit();
    }

    public void clear() {
        mySharedEditor.clear();
        mySharedEditor.commit();
    }

    public String getKeyValueType() {
        return mySharedPreference.getString(KEY_VALUE_TYPE, "USD");
    }

    public void putKeyValueType(String type){
        mySharedEditor.putString(KEY_VALUE_TYPE, type);
        mySharedEditor.commit();
    }
}
