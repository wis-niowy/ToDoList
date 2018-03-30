package com.example.wisienka.todomanager;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wisienka.todomanager.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Wisienka on 2018-03-27.
 */

public class TaskElement {
    protected Date creationDate;
    //protected Date deadlineDate;
    protected String header;
    protected String description;
    protected PriorityType priority;

    public TaskElement(Context mainContext) {
        this.creationDate = Calendar.getInstance().getTime();
        this.header = mainContext.getResources().getString(R.string.default_task_header);
        this.description = mainContext.getResources().getString(R.string.default_task_description);
        this.priority = PriorityType.undefined;

        //Spinner mySpinner = (Spinner) mainContext.findViewById(R.id.cmbClothType);
        //mySpinner.setAdapter(new ArrayAdapter<MyType>(this, android.R.layou, MyType.values()));
    }

    public void BindData(RecyclerViewAdapter.ViewHolder holder, Context mainActivityContext){
        EditText header = (EditText)holder.myTaskView.getChildAt(0);
        header.setText(this.header);
        TextView date = (TextView)holder.myTaskView.getChildAt(1);
        date.setText(GetDateInString());

        Spinner deadline = (Spinner)holder.myTaskView.getChildAt(2);
        ArrayAdapter<PriorityType> dataAdapter = new ArrayAdapter<PriorityType>(mainActivityContext, android.R.layout.simple_spinner_item, PriorityType.values());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deadline.setAdapter(dataAdapter);
        deadline.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int pos, long id) {
                if (parent.getItemAtPosition(pos) != PriorityType.undefined)
                    Toast.makeText(parent.getContext(), "Selected priority : " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView parent) {

            }
        });
    }

    private String GetDateInString(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c.getTime());
    }

    enum PriorityType{
        undefined,
        low,
        medium,
        high
    }
}