package edu.orangecoastcollege.cs273.sbadajoz.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Task> mAllTasksList = new ArrayList<>();

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deleteDatabase(DBHelper.DATABASE_NAME);

        mAllTasksList.add(new Task("Study for CS 273 Midterm", false));
        mAllTasksList.add(new Task("Play League of Legends", true));
        mAllTasksList.add(new Task("Sleep at Some Point", false));
        mAllTasksList.add(new Task("Complete IC #08", true));

        DBHelper db = new DBHelper(this);

        for (Task task : mAllTasksList) {
            db.addTask(task);
        }

        mAllTasksList.clear();

        mAllTasksList = db.getAllTasks();

        Log.i(TAG, "Showing all tasks:");
        for (Task task : mAllTasksList) {
            Log.i(TAG, task.toString());
        }

        Log.i(TAG, "After deleting task 4:");
        db.deleteTask(mAllTasksList.get(3));

        mAllTasksList = db.getAllTasks();
        for (Task task : mAllTasksList) {
            Log.i(TAG, task.toString());
        }
    }
}
