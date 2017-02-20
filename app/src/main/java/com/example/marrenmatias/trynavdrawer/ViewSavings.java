package com.example.marrenmatias.trynavdrawer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewSavings extends Fragment {
    DatabaseHelper mydb;
    SQLiteDatabase db;
    private Button buttonIncreaseSavings;
    private Button buttonUseSavings;
    private TextView textViewTotalSavings;
    Cursor cursor;

    public ViewSavings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_savings, container, false);
        openDatabase();
        mydb = new DatabaseHelper(getActivity());

        buttonIncreaseSavings = (Button)v.findViewById(R.id.buttonIncreaseSavings);
        textViewTotalSavings = (TextView)v.findViewById(R.id.textViewTotalSavings);
        buttonUseSavings = (Button)v.findViewById(R.id.buttonUseSavings);


        Cursor data = db.rawQuery("SELECT SavingsAmount FROM SAVINGS", null);
        data.moveToFirst();
        final String SavingsAmount = data.getString(0);
        textViewTotalSavings.setText(SavingsAmount);

        cursor = db.rawQuery("SELECT IncomeAmount FROM INCOME WHERE ACTIVE = 1", null);
        cursor.moveToFirst();
        final String IncomeAmount = cursor.getString(0);

        buttonIncreaseSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Float.valueOf(IncomeAmount) > 0) {
                    Intent intent = new Intent(getActivity(), AddSavings.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Income not enough", Toast.LENGTH_SHORT).show();
                }
            }
        });


        buttonUseSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Float.valueOf(SavingsAmount) > 0) {
                    Intent intent = new Intent(getActivity(), UseSavings.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "Savings not enough", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Inflate the layout for this fragment
        return v;
    }

    protected void openDatabase() {
        db = getActivity().openOrCreateDatabase("THRIFTY.db",android.content.Context.MODE_PRIVATE,null);
    }

}

