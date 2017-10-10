package edu.orangecoastcollege.cs273.sbadajoz.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Task> mAllTasksList;

    private DBHelper mDB;

    private EditText mDescriptionEditText;
    private ListView mTaskListView;

    TaskListAdapter mAdapter;

    /**
     * Creates an instance of the activity
     * Connects the Views to the backend
     * Instantiates the {@link DBHelper}
     *
     * @param savedInstanceState A bundle of the saved state from last run if applicable
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDescriptionEditText = (EditText) findViewById(R.id.taskEditText);
        mTaskListView = (ListView) findViewById(R.id.taskListView);

        mDB = new DBHelper(this);
    }

    /**
     * Called on a resume of the activity
     *
     * Recalls all tasks from the database
     * Connects the {@link TaskListAdapter}to the {@link ListView}
     */
    @Override
    protected void onResume() {
        super.onResume();

        mAllTasksList = mDB.getAllTasks();
        mAdapter = new TaskListAdapter(this, R.layout.task_item, mAllTasksList);

        mTaskListView.setAdapter(mAdapter);
    }

    /**
     * Adds a {@link Task} to the {@link ListView}
     * updates the database to match the {@link ListView}
     *
     * Creates toast if user did not input a description
     *
     * @param view The view which calls the method
     */
    public void addTask(View view) {
        String mDescription = mDescriptionEditText.getText().toString();
        if (TextUtils.isEmpty(mDescription)) {
            Toast.makeText(this, "Please enter a description", Toast.LENGTH_SHORT);
        } else {
            Task newTask = new Task(mDescription, false);
            mDB.addTask(newTask);
            mAllTasksList.add(newTask);
            mAdapter.notifyDataSetChanged();
            mDescriptionEditText.setText("");
        }
    }

    /**
     * Removes all {@link Task}s from the {@link ListView}
     * Updates the adapter

     * @param v View which calls the method
     */
    public void clearAllTasks(View v) {
        mAllTasksList.clear();
        mAdapter.notifyDataSetChanged();

        for (Task task : mDB.getAllTasks()) {
            mDB.deleteTask(task);
        }
    }

    /**
     * Updates the completion value of a {@link Task}
     * Then calls the {@link DBHelper} to update the database version of the {@link Task}

     * @param view {@link CheckBox} which is checked to call the method
     */
    public void updateTask(View view) {
        CheckBox checkBox = (CheckBox) view;
        Task selectedTask = (Task) checkBox.getTag();
        selectedTask.setDone(checkBox.isChecked());
        mDB.updateTask(selectedTask);
    }
}
