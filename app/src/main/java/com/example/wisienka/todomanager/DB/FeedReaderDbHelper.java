package com.example.wisienka.todomanager.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.wisienka.todomanager.TaskElement;

import java.util.ArrayList;
import java.util.List;


public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";
    private static final String DEBUG_TAG = "SqLiteTodoManager";
    private static Context context;
    private static SQLiteDatabase db;
    private static SQLiteOpenHelper dbHelper;

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(DEBUG_TAG, "Database creating...");
        db.execSQL(FeedReaderContract.SQL_CREATE_ENTRIES);
        Log.d(DEBUG_TAG, "Table " + FeedReaderContract.FeedEntry.TABLE_NAME + " ver." + DATABASE_VERSION + " created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        Log.d(DEBUG_TAG, "Database updating...");
        db.execSQL(FeedReaderContract.SQL_DELETE_ENTRIES);
        Log.d(DEBUG_TAG, "Table " + FeedReaderContract.FeedEntry.TABLE_NAME + " updated from ver." + oldVersion + " to ver." + newVersion);
        Log.d(DEBUG_TAG, "All data is lost.");
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onUpgrade(db, oldVersion, newVersion);
    }

    // CRUD db API
    public void open(){
        //dbHelper = new FeedReaderDbHelper(context);
        try {
            db = this.getWritableDatabase();
        } catch (SQLException e) {
            db = this.getReadableDatabase();
            Log.d(FeedReaderDbHelper.DEBUG_TAG, "Database open only in read mode!");
        }
    }
    public void close() {
        db = null;
        this.close();
        Log.d(FeedReaderDbHelper.DEBUG_TAG, "Database closed");
    }
    public static long insertTask(TaskElement task) {

        ContentValues values = new ContentValues();
        // add values to columns
        values.put(FeedReaderContract.FeedEntry.COLUMN_TASK_HEADER, task.getHeader());
        values.put(FeedReaderContract.FeedEntry.COLUMN_TASK_PRIORITY, String.valueOf(task.getPriority()));
        values.put(FeedReaderContract.FeedEntry.COLUMN_TASK_CREATION_DATE, task.getCreationDate());
        values.put(FeedReaderContract.FeedEntry.COLUMN_TASK_TIMESTAMP, task.getTimestamp());

        // insert row
        long id = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

//            // close db connection
//            db.close();

        Log.d(FeedReaderDbHelper.DEBUG_TAG, "New task inserted into the database with an id: " + id);

        // return newly inserted row id
        return id;
    }
    public static TaskElement getTask(long id) {

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.COLUMN_TASK_HEADER,
                FeedReaderContract.FeedEntry.COLUMN_TASK_PRIORITY,
                FeedReaderContract.FeedEntry.COLUMN_TASK_CREATION_DATE,
                FeedReaderContract.FeedEntry.COLUMN_TASK_TIMESTAMP,
                FeedReaderContract.FeedEntry._ID
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = FeedReaderContract.FeedEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

//            // How you want the results sorted in the resulting Cursor
//            String sortOrder = FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        final Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,          // don't group the rows
                null,           // don't filter by row groups
                null//sortOrder               // The sort order
        );

        if (cursor != null)
            cursor.moveToFirst();

        // prepare task object
        TaskElement task = new TaskElement(context){{
            this.header = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_TASK_HEADER));
            String receivedType = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_TASK_PRIORITY));
            this.priority = Enum.valueOf(TaskElement.PriorityType.class, receivedType);
            this.creationDate = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_TASK_CREATION_DATE));
            this.timestamp = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_TASK_TIMESTAMP));
            this.dbId = cursor.getLong(cursor.getColumnIndex(FeedReaderContract.FeedEntry._ID));
        }};

        // close the db connection
        cursor.close();

        Log.d(FeedReaderDbHelper.DEBUG_TAG, "Task with an id: " + id + " retrieved from the database");

        return task;
    }
    public static List<TaskElement> getAllTasks() {
        List<TaskElement> tasks = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + FeedReaderContract.FeedEntry.TABLE_NAME + " ORDER BY " +
                FeedReaderContract.FeedEntry.COLUMN_TASK_TIMESTAMP + " DESC";

        final Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // prepare task object
                TaskElement task = new TaskElement(context){{
                    this.header = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_TASK_HEADER));
                    String receivedType = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_TASK_PRIORITY));
                    this.priority = Enum.valueOf(TaskElement.PriorityType.class, receivedType);
                    this.creationDate = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_TASK_CREATION_DATE));
                    this.timestamp = cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_TASK_TIMESTAMP));
                    this.dbId = cursor.getLong(cursor.getColumnIndex(FeedReaderContract.FeedEntry._ID));
                }};

                tasks.add(task);
            } while (cursor.moveToNext());
        }

//            // close db connection
//            db.close();

        Log.d(FeedReaderDbHelper.DEBUG_TAG, "All tasks retrieved from the database");

        // return notes list
        return tasks;
    }
    public static int getTasksCount() {
        String countQuery = "SELECT  * FROM " + FeedReaderContract.FeedEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        Log.d(FeedReaderDbHelper.DEBUG_TAG, "All tasks' count retrieved from the database");

        // return count
        return count;
    }
    public static int updateTask(TaskElement task, String columnName) {
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_TASK_HEADER, task.getHeader());
        values.put(FeedReaderContract.FeedEntry.COLUMN_TASK_PRIORITY, String.valueOf(task.getPriority()));

        // Which row to update, based on the title
        String selection = FeedReaderContract.FeedEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(task.getDbId()) };

        int count = db.update(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        Log.d(FeedReaderDbHelper.DEBUG_TAG, "Task with an id: " + task.getDbId() + " updated");

        return count;
    }
    public static void deleteTask(TaskElement task) {

        String selection = FeedReaderContract.FeedEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(task.getDbId()) };

        db.delete(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                selection,
                selectionArgs);

        Log.d(FeedReaderDbHelper.DEBUG_TAG, "Task with an id: " + task.getDbId() + " deleted");
        //db.close();
    }
    public static void deleteTask(long id) {

        String selection = FeedReaderContract.FeedEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        db.delete(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                selection,
                selectionArgs);

        Log.d(FeedReaderDbHelper.DEBUG_TAG, "Task with an id: " + id + " deleted");
        //db.close();
    }
    public static void deleteAllTasks() {
//        StringBuilder sb = new StringBuilder("DELETE * FROM ");
//        db.rawQuery(sb.toString() + FeedReaderContract.FeedEntry.TABLE_NAME, null);

        String selection = "1 = ?";
        String[] selectionArgs = { "1" };

        long count = db.delete(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                selection,
                selectionArgs);

        Log.d(FeedReaderDbHelper.DEBUG_TAG, "All tasks have been deleted");
    }
}
