package com.example.chatbot;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter <MyAdapter.MyViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(RelativeLayout v) {
            super(v);
            textView = v.findViewById(R.id.textMessage);
        }
    }

    private List<MyModel> modelList;

    public MyAdapter(List<MyModel> modelList) {
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        if(modelList.get(position).isMe()){
            return R.layout.me_bubble;
        }
        else{
            return R.layout.bot_bubble;
        }
    }

    @Override
    public void onBindViewHolder(@Nullable MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(modelList.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
