package com.zoho.expensetracker.controller;

import android.app.Application;
import android.content.Context;

public class CoreController extends Application{

    private static CoreController mInstance;
    private static Context mContext;
    public static ExpenseDbHelper expenseDbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = this;
        expenseDbHelper = new ExpenseDbHelper(mContext);
    }


    synchronized public static ExpenseDbHelper getDBintstance(Context context) {
        if (expenseDbHelper == null) {
            expenseDbHelper = new ExpenseDbHelper(context);
        }
        return expenseDbHelper;
    }
}
