package com.example.wisienka.todomanager;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    RecyclerViewAdapter adapter;
    ImageButton floatButton;
    Fragment recyclerFragment;
    Fragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // set handler for FAB
//        floatButton = (ImageButton) findViewById(R.id.fab);
//        floatButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CreateNewTask();
//                Toast.makeText(getApplicationContext(),
//                        "New task created", Toast.LENGTH_SHORT).show();
//            }
//        });

        // PreferenceFragments
        recyclerFragment = new RecyclerFragment();
        settingsFragment = new SettingsPreferenceFragment();
        //Fragment fragment = new MyPreferenceFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (savedInstanceState == null){
            // created for the first time
            fragmentTransaction.add(R.id.relative_layout, recyclerFragment, "recyclerFragment");
            fragmentTransaction.commit();
        }

//        // data to populate the RecyclerView with
//        ArrayList<TaskElement> tasks = new ArrayList<>();
//        tasks.add(new TaskElement(this));
//        tasks.add(new TaskElement(this));
//
//        // set up the RecyclerView
//        RecyclerView recyclerView = findViewById(R.id.mainRecyclerViewer);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new RecyclerViewAdapter(this, tasks);
//        //adapter.setClickListener(this);
//        recyclerView.setAdapter(adapter);
//        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                //Toast.makeText(getApplicationContext(), adapter.getItem(position) + " is clicked!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//                RemoveTaskFromList(position);
//                Toast.makeText(getApplicationContext(), "Task deleted", Toast.LENGTH_SHORT).show();
//            }
//        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                SettingsPreferenceFragmentClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    * Called by FAB onClick handler
    * */
    private void CreateNewTask(){
        adapter.AddRecyclerViewItem(new TaskElement(getApplicationContext()));
    }
    private void RemoveTaskFromList(int position){
        adapter.RemoveRecyclerViewItem(position);
    }

    public void SettingsPreferenceFragmentClick(){
        // PreferenceFragment
        //Fragment fragment = new MyPreferenceFragment();
        //FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        //fragmentTransaction.add(R.id.relative_layout, fragment, "checkBoxPrefSortByTaskName");
        //fragmentTransaction.commit();
        //fragment = getFragmentManager().findFragmentByTag("checkBoxPrefSortByTaskName");
        getFragmentManager().beginTransaction().replace(android.R.id.content, settingsFragment).addToBackStack(null).commit();
    }

//    @Override
//    public void onItemClick(View view, int position) {
//        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
//    }
}
