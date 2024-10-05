package com.example.todoassignment.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoassignment.HomeActivity;
import com.example.todoassignment.Model.todoModel;
import com.example.todoassignment.R;
import com.example.todoassignment.addNewTask;
import com.example.todoassignment.utils.DBHelper;

import java.util.List;

public class todoAdapter extends RecyclerView.Adapter<todoAdapter.MyViewHolder> {
    private List<todoModel> mlist;
    private HomeActivity activity;
    private DBHelper myDB;

    public todoAdapter(DBHelper myDB,HomeActivity activity){
        this.activity=activity;
        this.myDB=myDB;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final todoModel item=mlist.get(position);
        holder.mCheckBox.setText(item.getTask());
        holder.mCheckBox.setChecked(toBoolean(item.getStatus()));
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    myDB.updateStatus(item.getId(),1);
                }
                else{
                    myDB.updateStatus(item.getId(),0);
                }
            }
        });
    }

    public boolean toBoolean(int num){
        return num!=0;
    }
    public Context getContext(){
        return activity;
    }

    public void setTasks(List<todoModel>mlist){
        this.mlist=mlist;
        notifyDataSetChanged();
    }

    public void deleteTask(int pos){
        todoModel item=mlist.get(pos);
        myDB.deleteTask(item.getId());
        mlist.remove(pos);
        notifyItemRemoved(pos);
    }

    public void editItem(int pos){
        todoModel item=mlist.get(pos);
        Bundle bundle=new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getTask());

        addNewTask task=new addNewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager(),task.getTag());
    }
    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox mCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox=itemView.findViewById(R.id.checkbox);
        }
    }
}
