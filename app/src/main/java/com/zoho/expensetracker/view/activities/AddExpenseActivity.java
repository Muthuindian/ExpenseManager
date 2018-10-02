package com.zoho.expensetracker.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.reginald.editspinner.EditSpinner;
import com.zoho.expensetracker.R;
import com.zoho.expensetracker.controller.CoreController;
import com.zoho.expensetracker.controller.SharedPreference;
import com.zoho.expensetracker.model.Category;
import com.zoho.expensetracker.model.Expense;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AddExpenseActivity extends AppCompatActivity {

    EditText expense_text,remarks_text;
    EditSpinner editSpinner_category;
    Button save;
    String category_id="";

    HashMap<String, String> categoryMap = new HashMap<>();
    ArrayList<String> category_namelist = new ArrayList<>();

    ListAdapter routeAdapt;

    SharedPreference sharedPreference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        sharedPreference = new SharedPreference(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        expense_text = findViewById(R.id.txt_expense);
        remarks_text = findViewById(R.id.txt_remarks);
        editSpinner_category = findViewById(R.id.category);
        save = findViewById(R.id.save);

        ArrayList<Category> categories = CoreController.getDBintstance(this).getAllCategories();
        for (int i = 0; i < categories.size(); i++) {
            categoryMap.put(categories.get(i).getId(), categories.get(i).getName());
        }
        category_namelist = new ArrayList<String>(categoryMap.values());

        routeAdapt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, category_namelist);
        editSpinner_category.setAdapter(routeAdapt);
        editSpinner_category.setSelected(true);
        editSpinner_category.setSelection(0);

        editSpinner_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                category_id = getKeyFromValue(categoryMap, editSpinner_category.getText().toString());
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = expense_text.getText().toString();
                String category_id =getKeyFromValue(categoryMap, editSpinner_category.getText().toString());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String dateString = formatter.format(date);
                String remarks = remarks_text.getText().toString();

                if(amount.equalsIgnoreCase("") || amount.equalsIgnoreCase("0")){
                    expense_text.setError("Please Enter Valid Amount");
                    return;
                }
                else if(remarks.equalsIgnoreCase("")){
                    remarks_text.setError("Please Add Remarks");
                    return;
                }
                else {
                    Expense expense = new Expense();
                    expense.setCategory_id(category_id);
                    expense.setValue(amount);
                    expense.setValue_type(sharedPreference.getKeyValueType());
                    expense.setRemarks(remarks);
                    expense.setDate(dateString);
                    CoreController.getDBintstance(AddExpenseActivity.this).insertExpense(expense);
                    Toast.makeText(AddExpenseActivity.this,"Expense Added",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddExpenseActivity.this,MainActivity.class));
                }
            }
        });
    }


    public String getKeyFromValue(HashMap hm, String value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return (String) o;
            }
        }
        return "";
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
