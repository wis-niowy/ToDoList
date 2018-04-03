package com.example.wisienka.todomanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wisienka on 2018-03-26.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private List<TaskElement> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private AdapterView.OnItemLongClickListener mLongClickListener;

    // data is passed into the constructor -- context is a main activity's context
    public RecyclerViewAdapter(Context context, List<TaskElement> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TaskElement task = mData.get(position);

        // bind all data to view here
        task.BindData(holder, mInflater.getContext());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/  {
        GridLayout myTaskView;

        public ViewHolder(View itemView) {
            super(itemView);
            myTaskView = itemView.findViewById(R.id.taskViewGrid);
            //itemView.setOnClickListener(this);
//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    Toast.makeText(this, "You clicked " + this.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//            });
        }

//        @Override
//        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//        }
    }

    // convenience method for getting DATA at click position
    TaskElement getItem(int id) {
        return mData.get(id);
    }

    public void AddRecyclerViewItem(TaskElement newTask){
        mData.add(0, newTask);
        this.notifyItemInserted(0);
    }

    public void AddRecyclerViewItems(List<TaskElement> newTasks){
        mData.addAll(0, newTasks);
        this.notifyItemInserted(0);
    }

    public void RemoveRecyclerViewItem(int position){
        mData.remove(position);
        this.notifyItemRemoved(position);
    }

    public void RemoveAllRecyclerViewItems(){
        mData = new ArrayList<TaskElement>();
    }

    public TaskElement GetRecyclerViewItem(int position){
        return mData.get(position);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // allows clicks events to be caught
    void setLongClickListener(AdapterView.OnItemLongClickListener itemClickListener) {
        this.mLongClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
//        void onItemClick(View view, int position);
        //void onLongItemClick(View view,int position);
    }
}
