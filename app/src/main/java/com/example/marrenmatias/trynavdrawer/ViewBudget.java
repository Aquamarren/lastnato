package com.example.marrenmatias.trynavdrawer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewBudget extends Fragment {
    DatabaseHelper mydb;
    SQLiteDatabase db;
    private ListView listViewCategoryWithExpense;
    SimpleCursorAdapter adapter;
    Cursor data;
    private Button btnCheckBudget;
    private EditText categoryAmount;

    public ViewBudget() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.view_budget, container, false);
        mydb = new DatabaseHelper(getActivity());
        openDatabase();


        //listViewCategoryWithExpense = (ListView) v.findViewById(R.id.listViewCategoryWithExpense);
        btnCheckBudget = (Button) v.findViewById(R.id.btnCheckBudget);
        categoryAmount = (EditText)v.findViewById(R.id.firstCategoryAmount);
        ShowCategoryList();
        // Inflate the layout for this fragment
        return v;
    }


    protected void openDatabase() {
        db = getActivity().openOrCreateDatabase("THRIFTY.db",android.content.Context.MODE_PRIVATE,null);
    }


    public void ShowCategoryList(){
        data = db.rawQuery("SELECT ID AS _id, * FROM CATEGORY WHERE ACTIVE = 1", null);

        String[] columns = new String[]{DatabaseHelper.KEY_TASK,DatabaseHelper.KEY_TASK2};
        int[] to = new int[]{R.id.firstCategoryName, R.id.firstCategoryAmount};

        adapter = new SimpleCursorAdapter(getActivity(),R.layout.view_budget,data,columns,to,0);
        listViewCategoryWithExpense.setAdapter(adapter);

        btnCheckBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Cursor cursor = db.rawQuery("SELECT IncomeAmount FROM INCOME WHERE ACTIVE = 1",null);
                cursor.moveToFirst();
                float incomeAmount = Float.valueOf(cursor.getString(0));
                if(R.id.CategoryAmount > incomeAmount) {
                    Toast.makeText(ViewBudget.this, "Income not enough", Toast.LENGTH_SHORT).show();
                }else{*/
                mydb.updateBudget(categoryAmount.getText().toString());
                Toast.makeText(getActivity(), "Budget Updated", Toast.LENGTH_SHORT).show();
                   /* Intent intent = new Intent(ViewBudget.this, MenuButtons.class);
                    startActivity(intent);*/
                //     }
            }
        });
    }
}
