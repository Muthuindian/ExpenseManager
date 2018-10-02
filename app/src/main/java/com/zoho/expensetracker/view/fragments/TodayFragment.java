package com.zoho.expensetracker.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zoho.expensetracker.R;
import com.zoho.expensetracker.controller.CoreController;
import com.zoho.expensetracker.controller.SharedPreference;
import com.zoho.expensetracker.model.Expense;
import com.zoho.expensetracker.view.activities.AddExpenseActivity;
import com.zoho.expensetracker.view.adapters.ExpenseAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodayFragment extends Fragment {

    RecyclerView recyclerView;
    List<Expense> expenses = new ArrayList<>();
    ExpenseAdapter adapter;
    TextView total;
    SharedPreference sharedPreference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_today, container, false);


        sharedPreference = new SharedPreference(getActivity());
        recyclerView = rootView.findViewById(R.id.list_expenses);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        total = rootView.findViewById(R.id.today_total);

        FloatingActionButton floatingActionButton = rootView.findViewById(R.id.addNewExpense);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddExpenseActivity.class));
            }
        });

        return rootView;
    }


    public void initializeData() {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String dateString = formatter.format(date);
        expenses = CoreController.getDBintstance(getActivity()).getTodayExpenses(dateString);


        int totalValue = CoreController.getDBintstance(getActivity()).getTodayExpense(dateString);

        String value = totalValue + " " + sharedPreference.getKeyValueType();
        total.setText(value);

        adapter = new ExpenseAdapter(expenses,getActivity(),true);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        initializeData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
