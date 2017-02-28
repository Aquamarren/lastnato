package com.example.marrenmatias.trynavdrawer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class FirstPage extends AppCompatActivity {

    private TextView m,t,f,o;
    private Button li;
    private ImageButton si;
    DatabaseHelper helper;
    SQLiteDatabase db;
    Cursor c;
    private static final String SELECT_SQL = "SELECT count(*) FROM contact WHERE ACTIVE = 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first__page);
        helper = new DatabaseHelper(this);


        m= (TextView)findViewById(R.id.manage);
        Typeface myCustomFont =Typeface.createFromAsset(getAssets(),"fonts/BebasNeue.otf");
        m.setTypeface(myCustomFont);
        t= (TextView) findViewById(R.id.thrifty);
        Typeface sCustomFont = Typeface.createFromAsset(getAssets(),"fonts/King-Basil-Lite.otf");
        t.setTypeface(sCustomFont);
        f= (TextView) findViewById(R.id.forgotPass);
        f.setTypeface(myCustomFont);
        o= (TextView) findViewById(R.id.or);
        o.setTypeface(myCustomFont);
        li = (Button) findViewById(R.id.loginButton);
        li.setTypeface(myCustomFont);
        si = (ImageButton) findViewById(R.id.signUp);


        openDatabase();
        c = db.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        int count = c.getInt(0);
        if(count > 0)
        {
            Cursor check = db.rawQuery("SELECT * FROM contact WHERE ACTIVE = 1", null);
            check.moveToFirst();

            Intent i = new Intent(FirstPage.this, SetIncome.class);
            startActivity(i);
        }

        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FirstPage.this, SignUp.class);
                startActivity(i);
            }
        });
    }

    public void onButtonClick(View v)
    {
        if (v.getId() == R.id.loginButton)
        {

            EditText a = (EditText)findViewById(R.id.editTextUsername);
            String str = a.getText().toString();

            EditText b = (EditText)findViewById(R.id.editTextPassword);
            String pass = b.getText().toString();

            String password = helper.searchPass(str);
            if (pass.equals(password))
            {
                helper.activate();
                Intent i = new Intent(FirstPage.this, display.class);
                i.putExtra("Username", str);
                startActivity(i);
            }
            else
            {
                Toast temp = Toast.makeText(FirstPage.this, "Username and Password do not match!", Toast.LENGTH_SHORT);
                temp.show();
            }

        }
      /* else if (v.getId() == R.id.signUpButton)
        {
            Intent i = new Intent(FirstPage.this, SignUp.class);
            startActivity(i);
        }*/
    }

    protected void openDatabase() {
        db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
    }
}
