package com.example.marrenmatias.trynavdrawer;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by May Galang on 3/3/2017.
 */
public class UpdateBudget extends Activity {
    SQLiteDatabase db;
    DatabaseHelper mydb;
    private EditText editTextBudgetCategoryAmount;
    private Button btnSaveBudgetAmount;
    private TextView budgetCategoryName;
    float budgetAmount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_budget);
        openDatabase();
        mydb = new DatabaseHelper(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.5));


        editTextBudgetCategoryAmount = (EditText)findViewById(R.id.editTextBudgetCategoryAmount);
        btnSaveBudgetAmount = (Button)findViewById(R.id.btnSaveBudgetAmount);
        budgetCategoryName = (TextView)findViewById(R.id.budgetCategoryName);
        SaveBudget();
    }

    protected void openDatabase() {
        db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
    }

    private void SaveBudget() {
        //float budgetAmount = Float.valueOf(editTextBudgetCategoryAmount.getText().toString());
        Bundle bundle = getIntent().getExtras();
        String categoryID = bundle.getString("categoryBudgetID");
        Toast.makeText(UpdateBudget.this, categoryID, Toast.LENGTH_LONG).show();


        Cursor cur = db.rawQuery("SELECT count(*),* FROM CATEGORY WHERE ID = " + categoryID, null);
        cur.moveToFirst();
        String catName = cur.getString(cur.getColumnIndex("CategoryName"));
        float catAmount = cur.getFloat(cur.getColumnIndex("Budget"));
        //cur.getString(cur.getColumnIndexOrThrow("_id"));
        //Toast.makeText(UpdateBudget.this,, Toast.LENGTH_LONG).show();
        int count = cur.getInt(0);
        if(count > 0) {
            budgetCategoryName.setText(catName);
            editTextBudgetCategoryAmount.setText(String.valueOf(catAmount));
            budgetAmount = Float.parseFloat(editTextBudgetCategoryAmount.getText().toString());

            btnSaveBudgetAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor = db.rawQuery("SELECT IncomeAmount FROM INCOME WHERE ACTIVE = 1", null);
                    cursor.moveToFirst();
                    float incomeAmount = Float.valueOf(cursor.getString(0));
                    if (budgetAmount > incomeAmount) {
                        Toast.makeText(UpdateBudget.this, "Income not enough", Toast.LENGTH_SHORT).show();
                    } else {
                        Bundle bundle = getIntent().getExtras();
                        String categoryIDd = bundle.getString("categoryBudgetID");

                        mydb.updateBudget(editTextBudgetCategoryAmount.getText().toString(),categoryIDd);
                        mydb.calculateIncome(Float.valueOf(editTextBudgetCategoryAmount.getText().toString()));
                        Log.i("insert", "Data Inserted");

                        Toast.makeText(UpdateBudget.this,"BudgetInserted " + editTextBudgetCategoryAmount.getText().toString() + categoryIDd, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }



}
