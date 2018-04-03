package com.example.wisienka.todomanager;

import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

import com.example.wisienka.todomanager.DB.FeedReaderContract;
import com.example.wisienka.todomanager.DB.FeedReaderDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    RecyclerViewAdapter adapter;
    Fragment recyclerFragment;
    Fragment settingsFragment;
    FeedReaderDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // connect to the Database
        dbHelper = new FeedReaderDbHelper(this.getApplicationContext());
        dbHelper.open();

        // PreferenceFragments
        recyclerFragment = new RecyclerFragment();
        settingsFragment = new SettingsPreferenceFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (savedInstanceState == null){
            // created for the first time
            fragmentTransaction.add(R.id.mainFrameLayout, recyclerFragment, "recyclerFragment");
            fragmentTransaction.commit();
            recyclerFragment.getFragmentManager().executePendingTransactions(); // force commit now
        }
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
            case R.id.action_delete_all:
                DeleteAllPreferenceFragmentClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
    * Called by FAB onClick handler
    * */
    private void CreateNewTask(){
        adapter.AddRecyclerViewItem(new TaskElement(getApplicationContext()));
    }
    private void RemoveTaskFromList(int position){
        adapter.RemoveRecyclerViewItem(position);
    }

    public void SettingsPreferenceFragmentClick(){

        getFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, settingsFragment).addToBackStack(null).commit();
    }

    public void DeleteAllPreferenceFragmentClick(){

        RecyclerFragment rf = (RecyclerFragment)recyclerFragment;
        rf.getAdapter().RemoveAllRecyclerViewItems();

        FeedReaderDbHelper.deleteAllTasks();
    }

//    @Override
//    public void onItemClick(View view, int position) {
//        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
//    }
}
