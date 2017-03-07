package com.example.marrenmatias.trynavdrawer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewExpense extends Fragment {
    SQLiteDatabase db;
    DatabaseHelper mydb;
    private ListView listViewCategoryExp;
    private Button btnGraph;
    Cursor data;

    public ViewExpense() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.view_expense, container, false);
        openDatabase();
        mydb = new DatabaseHelper(getActivity());

        listViewCategoryExp = (ListView)v.findViewById(R.id.listViewCategoryExp);
        btnGraph = (Button)v.findViewById(R.id.btnGraph);

        CategoryListView();
        ButtonNext();
        onBackPressed();

        // Inflate the layout for this fragment
        return v;
    }

   public boolean onBackPressed(){
       return  false;
   }

    protected void openDatabase() {
        db = getActivity().openOrCreateDatabase("THRIFTY.db",android.content.Context.MODE_PRIVATE,null);
    }

    public void ButtonNext(){
        btnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewReports fragment = new ViewReports();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment,"fragment4");
                fragmentTransaction.commit();

            }
        });
    }


    public void CategoryListView(){
        final ArrayList<String> theList = new ArrayList<>();
        data = mydb.ContentsCategory();
        if(data.getCount() == 0){
            Toast.makeText(getActivity(), "No Category", Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){
                theList.add(data.getString(1));
                final String ID = data.getString(0);
                ListAdapter listAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,theList);
                listViewCategoryExp.setAdapter(listAdapter);

                listViewCategoryExp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        data.moveToPosition(position);
                        String result = data.getString(data.getColumnIndex("CategoryName"));
                        String categoryID = data.getString(data.getColumnIndex("ID"));


                        Intent intent = new Intent(getActivity(), AddExpense.class);
                        intent.putExtra("categoryName",result);
                        intent.putExtra("categoryID",categoryID);
                        startActivity(intent);
                    }
                });
            }
        }

        Log.i("View", "CategoryList");
    }

}
