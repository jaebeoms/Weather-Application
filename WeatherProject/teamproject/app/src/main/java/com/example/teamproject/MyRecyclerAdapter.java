package com.example.teamproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private ArrayList<FriendItem> mFriendList;

    @NonNull
    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(mFriendList.get(position));
    }

    public void setFriendList(ArrayList<FriendItem> list){
        this.mFriendList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mFriendList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView smallweather;
        TextView temp;
        TextView tv_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            smallweather = itemView.findViewById(R.id.smallweather);
            temp = itemView.findViewById(R.id.temp);
            tv_time = itemView.findViewById(R.id.tv_time);
        }

        void onBind(FriendItem item){
            smallweather.setImageResource(item.getResourceId());
            temp.setText(item.getTemp()+"");
            tv_time.setText(item.getHour());
        }
    }
}