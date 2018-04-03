package com.example.wisienka.todomanager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wisienka.todomanager.DB.FeedReaderDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wisienka on 2018-03-28.
 */

public class RecyclerFragment extends Fragment
{
    protected RecyclerViewAdapter adapter;
    protected FloatingActionButton fab;
    protected Activity a; // mainActivity
    protected FeedReaderDbHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);

        //View contentLayout = view.findViewById(R.id.relative_layout);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewTask();
                Toast.makeText(a.getApplicationContext(),
                        "New task created", Toast.LENGTH_SHORT).show();
            }
        });

        // data to populate the RecyclerView with
        List<TaskElement> tasks = FeedReaderDbHelper.getAllTasks();//new ArrayList<>();
//        tasks.add(new TaskElement(a.getApplicationContext()));
//        tasks.add(new TaskElement(a.getApplicationContext()));

        // set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.mainRecyclerViewer);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext())); // context from current view (content_main) - NOT activity_main
        adapter = new RecyclerViewAdapter(view.getContext(), tasks);
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(view.getContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(getApplicationContext(), adapter.getItem(position) + " is clicked!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                RemoveTaskFromList(position);
                Toast.makeText(a.getApplicationContext(), "Task deleted", Toast.LENGTH_SHORT).show();
            }
        }));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        a = activity;
    }

    /**
     * Updates list according to database's content
     */
    public void UpdateDataList(){
        adapter.RemoveAllRecyclerViewItems();
        adapter.AddRecyclerViewItems(FeedReaderDbHelper.getAllTasks());
    }

    public RecyclerViewAdapter getAdapter() {
        return adapter;
    }

    /**
    * Called by FAB onClick handler
    * */
    private void CreateNewTask(){
        TaskElement newTask = new TaskElement(a.getApplicationContext());
        adapter.AddRecyclerViewItem(newTask);
        long id = FeedReaderDbHelper.insertTask(newTask);
        newTask.setDbId(id);
    }
    private void RemoveTaskFromList(int position){
        TaskElement task = adapter.GetRecyclerViewItem(position);
        FeedReaderDbHelper.deleteTask(task);
        adapter.RemoveRecyclerViewItem(position);
    }
}