package com.example.venkat.dingdong;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by venkat on 24/4/18.
 */

public class ContestAdapter extends RecyclerView.Adapter<ContestAdapter.MyViewHolder>{
    List<ContestInfo> contestlist;

    public ContestAdapter(List<ContestInfo> contestlist) {
        this.contestlist = contestlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contest_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

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

    @Override
    public int getItemCount() {
        return contestlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView contest_name,start_date,end_date,start_time,end_time;
        public MyViewHolder(View itemView) {
            super(itemView);
            contest_name = (TextView)itemView.findViewById(R.id.contest_name);
            start_date = (TextView)itemView.findViewById(R.id.start_date);
            end_date = (TextView)itemView.findViewById(R.id.end_date);
            start_time = (TextView)itemView.findViewById(R.id.start_time);
            end_time = (TextView)itemView.findViewById(R.id.end_time);
        }
    }
}