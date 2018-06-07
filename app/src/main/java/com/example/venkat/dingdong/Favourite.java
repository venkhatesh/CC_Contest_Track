package com.example.venkat.dingdong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class Favourite extends AppCompatActivity {

    DataBase db;
    ContestAdapter contestAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        db = new DataBase(this);
        ArrayList<ContestInfo> ls = db.getAllContest();
        for(int i = 0; i < ls.size(); i++){
            Log.e("Array List",ls.get(i).getContestName() + ls.get(i).getStartDate() + ls.get(i).getEndDate() );
        }
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.fav_recycler);
        contestAdapter = new ContestAdapter(ls,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(contestAdapter);
        contestAdapter.notifyDataSetChanged();

    }
}
