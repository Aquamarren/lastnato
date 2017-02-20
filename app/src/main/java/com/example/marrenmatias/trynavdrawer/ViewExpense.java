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


        // Inflate the layout for this fragment
        return v;
    }

    protected void openDatabase() {
        db = getActivity().openOrCreateDatabase("THRIFTY.db",android.content.Context.MODE_PRIVATE,null);
    }

    public void ButtonNext(){
        btnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(getActivity(), ViewReports.class);
                startActivity(intent);*/


                ViewReports fragment = new ViewReports();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment,"fragment4");
                fragmentTransaction.commit();
                //getActivity().getActionBar().setTitle("Reports");

                /*
                Intent redirect=new Intent(getActivity(),Cloud.class);
                    getActivity().startActivity(redirect);
                 */
                /*
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ViewReports llf = new ViewReports();
                ft.replace(R.id.frame, llf);
                ft.commit();*/
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
                        //String result =    data.getString(data.getColumnIndex("ID"));
                        String result = data.getString(data.getColumnIndex("CategoryName"));
                        String categoryID = data.getString(data.getColumnIndex("ID"));

                        //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();

                        //Toast.makeText(ViewExpense.this,ID,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), AddExpense.class);
                        intent.putExtra("categoryName",result);
                        intent.putExtra("categoryID", categoryID);
                        startActivity(intent);
                    }
                });
            }
        }

        Log.i("View", "CategoryList");
    }

}
