package com.example.venkat.dingdong;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by venkat on 24/4/18.
 */

public class ContestAdapter extends RecyclerView.Adapter<ContestAdapter.MyViewHolder>{
    List<ContestInfo> contestlist;
    Context context;
    DataBase db;


    public ContestAdapter(List<ContestInfo> contestlist, Context context) {
        this.contestlist = contestlist;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(this.context instanceof Favourite){
           itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.favourite_list, parent, false);
        }
        else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.contest_list, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (this.context instanceof Favourite){
            ContestInfo contestInfo = contestlist.get(position);
            String s_d = contestInfo.getStartDate();
            String e_d = contestInfo.getEndDate();
            holder.fav_name.setText(contestInfo.getContestName());
            holder.fav_sd.setText(s_d);
            holder.fav_ed.setText(e_d);
        }
        else {
            ContestInfo contestInfo = contestlist.get(position);
            String s_d = contestInfo.getStartDate();
            String e_d = contestInfo.getEndDate();
            String[] arr_sd = s_d.split(" ");
            String[] arr_ed = e_d.split(" ");
            String start_Date = arr_sd[0] + " " + arr_sd[1] + " " + arr_sd[2];
            String start_Time = arr_sd[3];
            String end_Time = arr_ed[3];
            String end_Date = arr_ed[0] + " " + arr_ed[1] + " " + arr_ed[2];
            holder.contest_name.setText(contestInfo.getContestName());
            holder.start_date.setText(start_Date);
            holder.end_date.setText(end_Date);
            holder.start_time.setText(start_Time);
            holder.end_time.setText(end_Time);
        }
    }

    @Override
    public int getItemCount() {
        return contestlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView contest_name,start_date,end_date,start_time,end_time,fav_name,fav_sd,fav_ed;
        ImageView fav,alert;
        public MyViewHolder(View itemView) {
            super(itemView);
            contest_name = (TextView)itemView.findViewById(R.id.contest_name);
            start_date = (TextView)itemView.findViewById(R.id.start_date);
            end_date = (TextView)itemView.findViewById(R.id.end_date);
            start_time = (TextView)itemView.findViewById(R.id.start_time);
            end_time = (TextView)itemView.findViewById(R.id.end_time);
            fav = (ImageView)itemView.findViewById(R.id.fav);
            alert = (ImageView)itemView.findViewById(R.id.alert);

            fav_name = (TextView)itemView.findViewById(R.id.fav_name);
            fav_sd = (TextView)itemView.findViewById(R.id.fav_sd);
            fav_ed = (TextView)itemView.findViewById(R.id.fav_ed);

            if(!(itemView.getContext() instanceof Favourite)) {
                fav.setOnClickListener(this);
                alert.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.fav){
                db = new DataBase(v.getContext());
              //  Toast.makeText(v.getContext(),contest_name.getText().toString(),Toast.LENGTH_LONG).show();
                db.BookMark(contest_name.getText().toString(),start_date.getText().toString(),
                       end_date.getText().toString());
                Toast.makeText(v.getContext(),"Hello!! Sucessfully added to database",Toast.LENGTH_LONG).show();

            }
            else if(v.getId()==R.id.alert){
               // SQLiteDatabase mydatabase = SQLiteDatabase.openDatabase("Bookmark.db", null, 1);
                db = new DataBase(v.getContext());
               // ArrayList<ContestInfo> hl = db.getAllContest();
               // Toast.makeText(v.getContext(),hl.toString(),Toast.LENGTH_LONG).show();
            }
        }
    }
}