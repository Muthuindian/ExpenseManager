package com.zoho.expensetracker.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zoho.expensetracker.R;
import com.zoho.expensetracker.model.Category;
import com.zoho.expensetracker.model.Expense;

import java.util.ArrayList;


public class ExpenseDbHelper extends SQLiteOpenHelper {

    public SharedPreference sharedPreference;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "expense_tracker.db";

    public static final String CATEGORIES_TABLE_NAME = "categories";
    public static final String EXPENSES_TABLE_NAME = "expenses";

    public static final String CATEGOEY_ID="id";
    public static final String NAME = "name";


    public static final String EXPENSE_ID="id";
    public static final String VALUE = "value";
    public static final String DATE="date";
    public static final String REMARKS="remarks";
    public static final String CATEGORIES_ID = "catergory_id";

    public static final String CREATE_CATEGORY_TABLE_QUERY =
            "CREATE TABLE " + CATEGORIES_TABLE_NAME + " (" +
                    CATEGOEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " TEXT NOT NULL);";

    public static final String DELETE_CATEGORY_TABLE_QUERY =
            "DROP TABLE IF EXISTS " + CATEGORIES_TABLE_NAME + ";";


    public static final String CREATE_EXPENSE_TABLE_QUERY =
            "CREATE TABLE " + EXPENSES_TABLE_NAME + " (" +
                    EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    VALUE + " FLOAT NOT NULL, " +
                    DATE + " DATE NOT NULL, " +
                    REMARKS + " TEXT NOT NULL, " +
                    CATEGORIES_ID + " INTEGER NOT NULL);";

    public static final String DELETE_EXPENSE_TABLE_QUERY =
            "DROP TABLE IF EXISTS " + EXPENSES_TABLE_NAME + ";";



    private Context mContext;

    private SQLiteDatabase mDatabaseInstance;

    private synchronized SQLiteDatabase getDatabaseInstance() {
        if (mDatabaseInstance == null) {
            mDatabaseInstance = getWritableDatabase();
        }

        if (!mDatabaseInstance.isOpen()) {
            mDatabaseInstance = getWritableDatabase();
        }

        return mDatabaseInstance;
    }

    public synchronized void close() {
        if (mDatabaseInstance != null && mDatabaseInstance.isOpen()) {
            mDatabaseInstance.close();
        }
    }

    public ExpenseDbHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = ctx;
        sharedPreference = new SharedPreference(mContext);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CATEGORY_TABLE_QUERY);
        fillCategoryTable(db, mContext);
        db.execSQL(CREATE_EXPENSE_TABLE_QUERY);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.execSQL("PRAGMA auto_vacuum = FULL");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_CATEGORY_TABLE_QUERY);
        db.execSQL(DELETE_EXPENSE_TABLE_QUERY);
        onCreate(db);
    }

    public static void fillCategoryTable(SQLiteDatabase db, Context ctx) {
        String[] predefinedNames = ctx.getResources().getStringArray(R.array.predefined_categories);
        ContentValues values = new ContentValues();
        for (String name : predefinedNames) {
            values.put(NAME, name);
            db.insert(CATEGORIES_TABLE_NAME, null, values);
        }
    }

    public String getCategoryNameById(String id){
        String query = "SELECT * FROM " + CATEGORIES_TABLE_NAME + " WHERE " + CATEGOEY_ID + "='"
                + id + "'";

        Cursor cursor = getDatabaseInstance().rawQuery(query, null);
        String data ="";
        if(cursor != null){
            while (cursor.moveToNext()){
                data = cursor.getString(cursor.getColumnIndex(NAME));
            }
        }
        return data;
    }


    public ArrayList<Category> getAllCategories(){
        String query = "SELECT * FROM " + CATEGORIES_TABLE_NAME;

        ArrayList<Category> categories = new ArrayList<>();
        Cursor cursor = getDatabaseInstance().rawQuery(query, null);
        String id ="" , name ="";
        if(cursor != null){
            while (cursor.moveToNext()){
                id = cursor.getString(cursor.getColumnIndex(CATEGOEY_ID));
                name = cursor.getString(cursor.getColumnIndex(NAME));
                Category category = new Category();
                category.setId(id);
                category.setName(name);
                categories.add(category);
            }
        }
        return categories;
    }

    public ArrayList<Expense> getAllExpenses(){
        String query = "SELECT * FROM " + EXPENSES_TABLE_NAME;

        ArrayList<Expense> expenses = new ArrayList<>();
        Cursor cursor = getDatabaseInstance().rawQuery(query, null);
        String id ="" , value ="" , remarks ="", date ="", type ="",categoryId="";
        if(cursor != null){
            while (cursor.moveToNext()){
                id = cursor.getString(cursor.getColumnIndex(EXPENSE_ID));
                value = cursor.getString(cursor.getColumnIndex(VALUE));
                remarks = cursor.getString(cursor.getColumnIndex(REMARKS));
                date = cursor.getString(cursor.getColumnIndex(DATE));
                type = sharedPreference.getKeyValueType();
                categoryId = cursor.getString(cursor.getColumnIndex(CATEGORIES_ID));
                Expense expense = new Expense();
                expense.setId(id);
                expense.setDate(date);
                expense.setValue(value);
                expense.setRemarks(remarks);
                expense.setValue_type(type);
                expense.setCategory_id(categoryId);
                expenses.add(expense);
            }
        }
        return expenses;
    }

    public int getTodayExpense(String today){

        String query = "SELECT SUM(" + VALUE   +") FROM " + EXPENSES_TABLE_NAME + " WHERE " + DATE + "='"
                + today + "'";
        int sum = 0;
        Cursor cursor = getDatabaseInstance().rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
        {
            sum = cursor.getInt(0);
        }
        return sum;
    }

    public int getTotalExpense(){

        String query = "SELECT SUM(" + VALUE   +") FROM " + EXPENSES_TABLE_NAME ;
        int sum = 0;
        Cursor cursor = getDatabaseInstance().rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
        {
            sum = cursor.getInt(0);
        }
        return sum;
    }

    public ArrayList<Expense> getTodayExpenses(String today){
        String query = "SELECT * FROM " + EXPENSES_TABLE_NAME + " WHERE " + DATE + "='"
                + today + "'";;

        ArrayList<Expense> expenses = new ArrayList<>();
        Cursor cursor = getDatabaseInstance().rawQuery(query, null);
        String id ="" , value ="" , remarks ="", date ="", type ="",categoryId="";
        if(cursor != null){
            while (cursor.moveToNext()){
                id = cursor.getString(cursor.getColumnIndex(EXPENSE_ID));
                value = cursor.getString(cursor.getColumnIndex(VALUE));
                remarks = cursor.getString(cursor.getColumnIndex(REMARKS));
                date = cursor.getString(cursor.getColumnIndex(DATE));
                type = sharedPreference.getKeyValueType();
                categoryId = cursor.getString(cursor.getColumnIndex(CATEGORIES_ID));
                Expense expense = new Expense();
                expense.setId(id);
                expense.setDate(date);
                expense.setValue(value);
                expense.setRemarks(remarks);
                expense.setValue_type(type);
                expense.setCategory_id(categoryId);
                expenses.add(expense);
            }
        }
        return expenses;
    }

    public void insertExpense(Expense expense)
    {
        ContentValues values = new ContentValues();
        values.put(VALUE, expense.getValue());
        values.put(DATE,expense.getDate());
        values.put(REMARKS, expense.getRemarks());
        values.put(CATEGORIES_ID,expense.getCategory_id());
        getDatabaseInstance().insert(EXPENSES_TABLE_NAME, null, values);
    }
}
