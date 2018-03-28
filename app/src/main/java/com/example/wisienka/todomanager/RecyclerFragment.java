package com.example.wisienka.todomanager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Wisienka on 2018-03-28.
 */

public class RecyclerFragment extends Fragment
{
    RecyclerViewAdapter adapter;
    Activity a; // mainActivity

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        View contentLayout = a.findViewById(R.id.relative_layout);
//        ImageButton fab = contentLayout.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CreateNewTask();
//                Toast.makeText(a.getApplicationContext(),
//                        "New task created", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // data to populate the RecyclerView with
//        ArrayList<TaskElement> tasks = new ArrayList<>();
//        tasks.add(new TaskElement(a.getApplicationContext()));
//        tasks.add(new TaskElement(a.getApplicationContext()));
//
//        // set up the RecyclerView
//        RecyclerView recyclerView = a.findViewById(R.id.mainRecyclerViewer);
//        recyclerView.setLayoutManager(new LinearLayoutManager(a.getApplicationContext()));
//        adapter = new RecyclerViewAdapter(a.getApplicationContext(), tasks);
//        //adapter.setClickListener(this);
//        recyclerView.setAdapter(adapter);
//        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(a.getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                //Toast.makeText(getApplicationContext(), adapter.getItem(position) + " is clicked!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//                RemoveTaskFromList(position);
//                Toast.makeText(a.getApplicationContext(), "Task deleted", Toast.LENGTH_SHORT).show();
//            }
//        }));
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);

        //View contentLayout = view.findViewById(R.id.relative_layout);
        ImageButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewTask();
                Toast.makeText(a.getApplicationContext(),
                        "New task created", Toast.LENGTH_SHORT).show();
            }
        });

        // data to populate the RecyclerView with
        ArrayList<TaskElement> tasks = new ArrayList<>();
        tasks.add(new TaskElement(a.getApplicationContext()));
        tasks.add(new TaskElement(a.getApplicationContext()));

        // set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.mainRecyclerViewer);
        recyclerView.setLayoutManager(new LinearLayoutManager(a.getApplicationContext()));
        adapter = new RecyclerViewAdapter(a.getApplicationContext(), tasks);
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(a.getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
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

    /*
    * Called by FAB onClick handler
    * */
    private void CreateNewTask(){
        adapter.AddRecyclerViewItem(new TaskElement(a.getApplicationContext()));
    }
    private void RemoveTaskFromList(int position){
        adapter.RemoveRecyclerViewItem(position);
    }
}