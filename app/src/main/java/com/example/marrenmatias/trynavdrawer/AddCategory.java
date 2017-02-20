package com.example.marrenmatias.trynavdrawer;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.DisplayMetrics;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

/**
 * Created by May Galang on 1/16/2017.
 */
public class AddCategory extends Activity {
    DatabaseHelper mydb;
    private EditText editTextCategoryName;
    private Button btnInsertCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_category);
        mydb = new DatabaseHelper(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.6),(int)(height*.5));


        editTextCategoryName = (EditText)findViewById(R.id.editTextCategoryName);
        btnInsertCategory = (Button)findViewById(R.id.btnInsertCategory);
        InsertCategory();
    }

    public void InsertCategory(){
        btnInsertCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mydb.AddCategory(editTextCategoryName.getText().toString());
                Log.i("insert", "Category Inserted");

                Intent intent = new Intent(AddCategory.this,Main4Activity.class);
                startActivity(intent);
                Toast.makeText(AddCategory.this, "Category Inserted", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
