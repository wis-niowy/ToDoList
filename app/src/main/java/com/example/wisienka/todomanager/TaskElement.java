package com.example.wisienka.todomanager;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wisienka.todomanager.DB.FeedReaderDbHelper;
import com.example.wisienka.todomanager.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Wisienka on 2018-03-27.
 */

public class TaskElement {
    protected String creationDate;
    protected String timestamp; // extended creation date
    //protected Date deadlineDate;
    protected String header;
    protected String description;
    protected PriorityType priority;
    protected long dbId;

    public TaskElement(Context mainContext) {
        //this.creationDate = Calendar.getInstance().getTime();
        this.creationDate = GetDateInString();
        this.timestamp = GetTimestampInString();
        this.header = mainContext.getResources().getString(R.string.default_task_header);
        this.description = mainContext.getResources().getString(R.string.default_task_description);
        this.priority = PriorityType.undefined;
        this.dbId = -1;

        //Spinner mySpinner = (Spinner) mainContext.findViewById(R.id.cmbClothType);
        //mySpinner.setAdapter(new ArrayAdapter<MyType>(this, android.R.layou, MyType.values()));
    }

    public void BindData(RecyclerViewAdapter.ViewHolder holder, Context mainActivityContext){
        final EditText header = (EditText)holder.myTaskView.getChildAt(0);
        header.setText(this.header);
        header.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            /* When focus is lost check that the text field
            * has valid values.
            */
                if (!hasFocus) {
                    setHeader(header.getText().toString());
                    updateCurrentTaskInDatabase();
                }
            }
        });
        TextView date = (TextView)holder.myTaskView.getChildAt(1);
        date.setText(this.creationDate);
        Spinner deadline = (Spinner)holder.myTaskView.getChildAt(2);
        ArrayAdapter<PriorityType> dataAdapter = new ArrayAdapter<PriorityType>(mainActivityContext, android.R.layout.simple_spinner_item, PriorityType.values());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deadline.setAdapter(dataAdapter);
        setSpinnerSelection(this.priority, dataAdapter, deadline);
        deadline.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int pos, long id) {
                PriorityType selectedValue = (PriorityType) parent.getItemAtPosition(pos);
                setPriority(selectedValue);
                updateCurrentTaskInDatabase();
                if (selectedValue != PriorityType.undefined)
                    Toast.makeText(parent.getContext(), "Selected priority : " + selectedValue.toString(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView parent) {

            }
        });
    }

    private void updateCurrentTaskInDatabase(){
        FeedReaderDbHelper.updateTask(this, "");
    }

    private String GetDateInString(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c.getTime());
    }

    private String GetTimestampInString(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(c.getTime());
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public PriorityType getPriority() {
        return priority;
    }

    private void setPriority(PriorityType priority) {
        this.priority = priority;
    }

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    private void setSpinnerSelection(PriorityType type, ArrayAdapter<PriorityType> adapter, Spinner mSpinner){
        if (type != null) {
            int spinnerPosition = adapter.getPosition(type);
            mSpinner.setSelection(spinnerPosition);
        }
    }

    /**
     * Enum type for task's priority
     */
    public enum PriorityType{
        undefined,
        low,
        medium,
        high;

//        @Override
//        public String toString() {
//            return this.name;
//        }
    }
}