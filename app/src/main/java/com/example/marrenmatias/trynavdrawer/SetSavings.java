package com.example.marrenmatias.trynavdrawer;

        import android.content.Intent;
        import android.graphics.Typeface;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import org.w3c.dom.Text;

        import java.text.SimpleDateFormat;
        import java.util.Date;

public class SetSavings extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText txtSetSavings;
    Button btnSaving;
    TextView ss,tf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_savings);
        myDb = new DatabaseHelper(this);

        ss = (TextView) findViewById(R.id.setSavings);
        tf = (TextView) findViewById(R.id.thrift);
        txtSetSavings = (EditText)findViewById(R.id.editTextSetSavings);
        btnSaving = (Button)findViewById(R.id.btnAddSaving);

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/BebasNeue.otf");
        Typeface sCustomFont = Typeface.createFromAsset(getAssets(),"fonts/King-Basil-Lite.otf");
        ss.setTypeface(myCustomFont);
        tf.setTypeface(sCustomFont);
        txtSetSavings.setTypeface(myCustomFont);

        AddSavings();
    }

    public void AddSavings(){
        btnSaving.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String savingsAmount = txtSetSavings.getText().toString();
                        String timestamp = new SimpleDateFormat("MMMM dd, yyyy").format(new Date());
                        myDb.insertSavings(savingsAmount,timestamp);
                        Log.i("insert", "Data Inserted1");

                        float number = Float.parseFloat(savingsAmount);
                        myDb.calculateIncome(number);
                        Log.i("update", "Data Updated");

                        Intent intent = new Intent(SetSavings.this,SetBudget_first.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
