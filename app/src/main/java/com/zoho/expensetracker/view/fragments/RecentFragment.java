package com.zoho.expensetracker.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.zoho.expensetracker.view.adapters.ExpenseAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecentFragment extends Fragment {

    RecyclerView recyclerView;
    List<Expense> expenses = new ArrayList<>();
    ExpenseAdapter adapter;
    TextView total;
    SharedPreference sharedPreference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recent, container, false);

        sharedPreference = new SharedPreference(getActivity());
        recyclerView = rootView.findViewById(R.id.recent_list_expenses);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        total = rootView.findViewById(R.id.total);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeData();
    }

    public void initializeData() {

        expenses = CoreController.getDBintstance(getActivity()).getAllExpenses();


        int totalValue = CoreController.getDBintstance(getActivity()).getTotalExpense();

        String value = totalValue + " " + sharedPreference.getKeyValueType();
        total.setText(value);

        adapter = new ExpenseAdapter(expenses,getActivity(),false);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
