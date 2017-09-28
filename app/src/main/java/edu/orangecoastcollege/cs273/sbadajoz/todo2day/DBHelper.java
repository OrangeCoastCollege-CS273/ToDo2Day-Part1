package edu.orangecoastcollege.cs273.sbadajoz.todo2day;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sbadajoz on 9/28/2017.
 */

class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ToDo2Day";
    public static final String DATABASE_TABLE = "Tasks";
    public static final int DATABASE_VERSION = 1;

    public static final String KEY_FIELD_ID = "_id";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_DONE = "done";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + DATABASE_TABLE
                +" (" + KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_DONE + " INTEGER" + ")";
        sqLiteDatabase.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void addTask(Task newTask) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_DESCRIPTION, newTask.getDescription());
        values.put(FIELD_DONE, newTask.isDone() ? 1 : 0);

        database.insert(DATABASE_TABLE, null, values);

        database.close();
    }

    public List<Task> getAllTasks() {
        SQLiteDatabase database = getReadableDatabase();
        List<Task> taskList = new ArrayList<>();

        Cursor cursor = database.query(DATABASE_TABLE,
                new String[]{KEY_FIELD_ID, FIELD_DESCRIPTION, FIELD_DONE},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                taskList.add(new Task(cursor.getInt(0), cursor.getString(1), (cursor.getInt(2) == 1)));
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return taskList;
    }

    public void deleteTask(Task task) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(DATABASE_TABLE, KEY_FIELD_ID + "=" + task.getID(), null);
        database.close();
    }
}
