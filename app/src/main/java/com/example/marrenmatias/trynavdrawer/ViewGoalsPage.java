package com.example.marrenmatias.trynavdrawer;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewGoalsPage extends Fragment {
    DatabaseHelper mydb;
    SQLiteDatabase db;
    private ListView listViewGoalsList;
    Cursor data;
    Cursor detail;

    public ViewGoalsPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.goals_page, container, false);
        openDatabase();
        mydb = new DatabaseHelper(getActivity());
        listViewGoalsList = (ListView) v.findViewById(R.id.listViewGoalsList);
        showGoalsList();
        return v;
    }

    protected void openDatabase() {
        //db = openOrCreateDatabase("THRIFTY.db", Context.MODE_PRIVATE, null);
        db = getActivity().openOrCreateDatabase("THRIFTY.db",android.content.Context.MODE_PRIVATE,null); //RIGHT
    }

    public void showGoalsList(){
        final ArrayList<String> theList = new ArrayList<>();

        data = db.rawQuery("SELECT GoalRank FROM GOALS WHERE GoalAccomplished = 1 ORDER BY GoalRank ASC",null);
        while(data.moveToNext()) {
            theList.add(data.getString(0));
            ListAdapter listAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, theList);
            listViewGoalsList.setAdapter(listAdapter);

            listViewGoalsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //int result = (int)parent.getItemIdAtPosition(position);
                    //String result = (String)parent.getItemAtPosition(position);
                    data.moveToPosition(position);
                    String result = data.getString(data.getColumnIndex("GoalRank"));
                    detail = db.rawQuery("SELECT * FROM GOALS WHERE GoalAccomplished = 1 AND GoalRank = " + result, null);
                    detail.moveToFirst();
                    final String goalName = detail.getString(1);
                    if(goalName == null){
                        Intent intent = new Intent(getActivity(), AddGoal.class);
                        intent.putExtra("goalRank", result);
                        startActivity(intent);
                    }else{
                        Intent intent2 = new Intent(getActivity(), GoalDetails.class);
                        intent2.putExtra("goalRank",result);
                        startActivity(intent2);
                    }
                }
            });
        }
        Log.i("View", "CategoryList");
    }

}
