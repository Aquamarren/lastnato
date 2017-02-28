package com.example.marrenmatias.trynavdrawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Andrea Mae on 12/18/2016.
 */
public class display extends Activity {
    SQLiteDatabase db;
    Cursor c;
    private static final String SELECT_SQL = "SELECT count(*) FROM contact WHERE ACTIVE = 1";
    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        openDatabase();

        String username = getIntent().getStringExtra("Username");

        TextView tv = (TextView) findViewById(R.id.textViewUsername);
        tv.setText(username);

        Button logout = (Button) findViewById(R.id.buttonLogOut);
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                SharedPreferences SM = getSharedPreferences("userrecord", 0);
                SharedPreferences.Editor edit = SM.edit();
                edit.putBoolean("userlogin", false);
                edit.commit();
                helper.deactivate();

                Intent intent = new Intent(display.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    protected void openDatabase() {
        db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
    }
}
