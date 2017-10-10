package edu.orangecoastcollege.cs273.sbadajoz.todo2day;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * DBHelper serves as a hel;per class for managing SQLite databases
 * This includes functionality such as creating new rows, reading data, and replacing data
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

    /**
     * Creates the table
     *
     * @param sqLiteDatabase The database
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + DATABASE_TABLE
                +" (" + KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_DONE + " INTEGER" + ")";
        sqLiteDatabase.execSQL(createTable);

    }

    /**
     * Called when the database needs to be upgraded.
     * Drops an existing table and then calls onCreate
     *
     * @param sqLiteDatabase The database
     * @param i The old database version
     * @param i1 The new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE);
        onCreate(sqLiteDatabase);
    }

    /**
     * Adds a new task to the table from parameter
     * @param newTask The new task to add
     */
    public void addTask(Task newTask) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_DESCRIPTION, newTask.getDescription());
        values.put(FIELD_DONE, newTask.isDone() ? 1 : 0);

        database.insert(DATABASE_TABLE, null, values);

        database.close();
    }

    /**
     *  Fetches all tasks from the table
     * @return A list of all tasks
     */
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

    /**
     * Deletes the specified {@link Task} from the table
     * @param task The ask to be deleted
     */
    public void deleteTask(Task task) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(DATABASE_TABLE, KEY_FIELD_ID + "=" + task.getID(), null);
        database.close();
    }

    /**
     * Reuploads a specified Task to match new data
     * @param task A task to be deleted, must have same ID as the one in the table
     */
    public void updateTask(Task task) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIELD_DESCRIPTION, task.getDescription());
        values.put(FIELD_DONE, task.isDone() ? 1 : 0);
        database.update(DATABASE_TABLE, values, KEY_FIELD_ID + "=" + task.getID(), null);

        database.close();
    }

    /**
     * Fetches a task that matches the id
     * @param id ID of task
     * @return The task specified
     */
    public Task getSingleTask(int id) {
        SQLiteDatabase database = getReadableDatabase();
        Task task = null;
        Cursor cursor = database.query(DATABASE_TABLE,
                new String[]{KEY_FIELD_ID, FIELD_DESCRIPTION, FIELD_DONE},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                if(cursor.getInt(0) == id)
                    task = new Task(cursor.getInt(0), cursor.getString(1), (cursor.getInt(2) == 1));
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return task;
    }
}
