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

/**
 * Created by Wisienka on 2018-04-02.
 */

public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
//    private static SQLiteDatabase db;
//    private static SQLiteOpenHelper dbHelper;

    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_TASK_HEADER = "header";
        public static final String COLUMN_TASK_TIMESTAMP = "timestamp";
        public static final String COLUMN_TASK_PRIORITY = "priority";
        public static final String COLUMN_TASK_DESCRIPTION = "description";
        public static final String COLUMN_TASK_CREATION_DATE = "creation_date";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_TASK_HEADER + " TEXT," +
                    FeedEntry.COLUMN_TASK_TIMESTAMP + " TEXT," +
                    FeedEntry.COLUMN_TASK_PRIORITY + " TEXT," +
                    FeedEntry.COLUMN_TASK_DESCRIPTION + " TEXT," +
                    FeedEntry.COLUMN_TASK_CREATION_DATE + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    /**
     * Subclass responsible for creating/updating the database
     */
//    public class FeedReaderDbHelper extends SQLiteOpenHelper {
//        // If you change the database schema, you must increment the database version.
//        public static final int DATABASE_VERSION = 1;
//        public static final String DATABASE_NAME = "FeedReader.db";
//        private static final String DEBUG_TAG = "SqLiteTodoManager";
//        private Context context;
//
//        public FeedReaderDbHelper(Context context) {
//            super(context, DATABASE_NAME, null, DATABASE_VERSION);
//            this.context = context;
//        }
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            Log.d(DEBUG_TAG, "Database creating...");
//            db.execSQL(SQL_CREATE_ENTRIES);
//            Log.d(DEBUG_TAG, "Table " + FeedEntry.TABLE_NAME + " ver." + DATABASE_VERSION + " created");
//        }
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            // This database is only a cache for online data, so its upgrade policy is
//            // to simply to discard the data and start over
//            Log.d(DEBUG_TAG, "Database updating...");
//            db.execSQL(SQL_DELETE_ENTRIES);
//            Log.d(DEBUG_TAG, "Table " + FeedEntry.TABLE_NAME + " updated from ver." + oldVersion + " to ver." + newVersion);
//            Log.d(DEBUG_TAG, "All data is lost.");
//            onCreate(db);
//        }
//        @Override
//        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//            onUpgrade(db, oldVersion, newVersion);
//        }
//
//        // CRUD db API
//        public void open(){
//            dbHelper = new FeedReaderDbHelper(context);
//            try {
//                db = dbHelper.getWritableDatabase();
//            } catch (SQLException e) {
//                db = dbHelper.getReadableDatabase();
//                Log.d(FeedReaderDbHelper.DEBUG_TAG, "Database open only in read mode!");
//            }
//        }
//        public void close() {
//            db = null;
//            this.close();
//            Log.d(FeedReaderDbHelper.DEBUG_TAG, "Database closed");
//        }
//        public long insertTask(TaskElement task) {
//            // get writable database as we want to write data
//            SQLiteDatabase db = this.getWritableDatabase();
//
//            ContentValues values = new ContentValues();
//            // add values to columns
//            values.put(FeedEntry.COLUMN_TASK_HEADER, task.getHeader());
//            values.put(FeedEntry.COLUMN_TASK_PRIORITY, String.valueOf(task.getPriority()));
//            values.put(FeedEntry.COLUMN_TASK_CREATION_DATE, task.getCreationDate());
//            values.put(FeedEntry.COLUMN_TASK_TIMESTAMP, task.getTimestamp());
//
//            // insert row
//            long id = db.insert(FeedEntry.TABLE_NAME, null, values);
//
////            // close db connection
////            db.close();
//
//            Log.d(FeedReaderDbHelper.DEBUG_TAG, "New task inserted into the database with an id: " + id);
//
//            // return newly inserted row id
//            return id;
//        }
//        public TaskElement getNote(long id) {
//
//            // Define a projection that specifies which columns from the database
//            // you will actually use after this query.
//            String[] projection = {
//                    BaseColumns._ID,
//                    FeedEntry.COLUMN_TASK_HEADER,
//                    FeedEntry.COLUMN_TASK_PRIORITY,
//                    FeedEntry.COLUMN_TASK_CREATION_DATE,
//                    FeedEntry.COLUMN_TASK_TIMESTAMP
//            };
//
//            // Filter results WHERE "title" = 'My Title'
//            String selection = FeedEntry._ID + " = ?";
//            String[] selectionArgs = { String.valueOf(id) };
//
////            // How you want the results sorted in the resulting Cursor
////            String sortOrder = FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";
//
//            final Cursor cursor = db.query(
//                    FeedEntry.TABLE_NAME,   // The table to query
//                    projection,             // The array of columns to return (pass null to get all)
//                    selection,              // The columns for the WHERE clause
//                    selectionArgs,          // The values for the WHERE clause
//                    null,          // don't group the rows
//                    null,           // don't filter by row groups
//                    null//sortOrder               // The sort order
//            );
//
//            if (cursor != null)
//                cursor.moveToFirst();
//
//            // prepare task object
//            TaskElement task = new TaskElement(context){{
//                this.header = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_TASK_HEADER));
//                String receivedType = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_TASK_PRIORITY));
//                this.priority = Enum.valueOf(TaskElement.PriorityType.class, receivedType);
//                this.creationDate = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_TASK_CREATION_DATE));
//                this.timestamp = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_TASK_TIMESTAMP));
//            }};
//
//            // close the db connection
//            cursor.close();
//
//            Log.d(FeedReaderDbHelper.DEBUG_TAG, "Task with an id: " + id + " retrieved from the database");
//
//            return task;
//        }
//        public List<TaskElement> getAllTasks() {
//            List<TaskElement> notes = new ArrayList<>();
//
//            // Select All Query
//            String selectQuery = "SELECT  * FROM " + FeedEntry.TABLE_NAME + " ORDER BY " +
//                    FeedEntry.COLUMN_TASK_TIMESTAMP + " DESC";
//
//            final Cursor cursor = db.rawQuery(selectQuery, null);
//
//            // looping through all rows and adding to list
//            if (cursor.moveToFirst()) {
//                do {
//                    // prepare task object
//                    TaskElement task = new TaskElement(context){{
//                        this.header = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_TASK_HEADER));
//                        String receivedType = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_TASK_PRIORITY));
//                        this.priority = Enum.valueOf(TaskElement.PriorityType.class, receivedType);
//                        this.creationDate = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_TASK_CREATION_DATE));
//                        this.timestamp = cursor.getString(cursor.getColumnIndex(FeedEntry.COLUMN_TASK_TIMESTAMP));
//                    }};
//
//                    notes.add(task);
//                } while (cursor.moveToNext());
//            }
//
////            // close db connection
////            db.close();
//
//            Log.d(FeedReaderDbHelper.DEBUG_TAG, "All tasks retrieved from the database");
//
//            // return notes list
//            return notes;
//        }
//        public int getTasksCount() {
//            String countQuery = "SELECT  * FROM " + FeedEntry.TABLE_NAME;
//            Cursor cursor = db.rawQuery(countQuery, null);
//
//            int count = cursor.getCount();
//            cursor.close();
//
//            Log.d(FeedReaderDbHelper.DEBUG_TAG, "All tasks' count retrieved from the database");
//
//            // return count
//            return count;
//        }
//        public int updateTask(TaskElement task, String columnName) {
//            // New value for one column
//            ContentValues values = new ContentValues();
//            values.put(FeedEntry.COLUMN_TASK_HEADER, task.getHeader());
//            values.put(FeedEntry.COLUMN_TASK_PRIORITY, String.valueOf(task.getPriority()));
//
//            // Which row to update, based on the title
//            String selection = FeedEntry._ID + " = ?";
//            String[] selectionArgs = { String.valueOf(task.getDbId()) };
//
//            int count = db.update(
//                    FeedEntry.TABLE_NAME,
//                    values,
//                    selection,
//                    selectionArgs);
//
//            Log.d(FeedReaderDbHelper.DEBUG_TAG, "Task with an id: " + task.getDbId() + " updated");
//
//            return count;
//        }
//        public void deleteTask(TaskElement task) {
//
//            String selection = FeedEntry._ID + " = ?";
//            String[] selectionArgs = { String.valueOf(task.getDbId()) };
//
//            db.delete(
//                    FeedEntry.TABLE_NAME,
//                    selection,
//                    selectionArgs);
//
//            Log.d(FeedReaderDbHelper.DEBUG_TAG, "Task with an id: " + task.getDbId() + " deleted");
//            //db.close();
//        }
//
//    }



}