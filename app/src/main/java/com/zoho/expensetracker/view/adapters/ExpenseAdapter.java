package com.zoho.expensetracker.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zoho.expensetracker.R;
import com.zoho.expensetracker.controller.CoreController;
import com.zoho.expensetracker.model.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private boolean isToday = false;
    private List<Expense> expenses = new ArrayList<>();
    private Context context;
    private String prevDate = "";


    public ExpenseAdapter(List<Expense> expenses, Context context, boolean isToday) {
        this.expenses = expenses;
        this.context = context;
        this.isToday = isToday;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_expense, viewGroup, false);
        return new ExpenseViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder expenseViewHolder, int i) {

        Expense expense = expenses.get(i);

        if(isToday) {
            expenseViewHolder.date.setVisibility(View.GONE);
        }
        else {
            if(prevDate == "" || !prevDate.equalsIgnoreCase(expense.getDate())){
                prevDate = expense.getDate();
                expenseViewHolder.date.setText(prevDate);
            }
            else {
                expenseViewHolder.date.setVisibility(View.GONE);
            }
        }

        expenseViewHolder.category_name.setText(CoreController.getDBintstance(context).getCategoryNameById(expense.getCategory_id()));
        expenseViewHolder.remarks.setText(expense.getRemarks());
        expenseViewHolder.amount.setText(expense.getValue());
        expenseViewHolder.value_type.setText(expense.getValue_type());
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }


    public class ExpenseViewHolder extends RecyclerView.ViewHolder{

        private TextView category_name, amount, remarks, value_type , date;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            category_name = itemView.findViewById(R.id.category_name);
            amount = itemView.findViewById(R.id.amount_value);
            remarks = itemView.findViewById(R.id.remarks);
            value_type= itemView.findViewById(R.id.value_type);
        }
    }
}
