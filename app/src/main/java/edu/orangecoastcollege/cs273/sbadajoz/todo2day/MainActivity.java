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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDescriptionEditText = (EditText) findViewById(R.id.taskEditText);
        mTaskListView = (ListView) findViewById(R.id.taskListView);

        mDB = new DBHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mAllTasksList = mDB.getAllTasks();
        mAdapter = new TaskListAdapter(this, R.layout.task_item, mAllTasksList);

        mTaskListView.setAdapter(mAdapter);
    }

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

    public void clearAllTasks(View v) {
        mAllTasksList.clear();
        mAdapter.notifyDataSetChanged();

        for (Task task : mDB.getAllTasks()) {
            mDB.deleteTask(task);
        }
    }

    public void updateTask(View view) {
        CheckBox checkBox = (CheckBox) view;
        Task selectedTask = (Task) checkBox.getTag();
        selectedTask.setDone(checkBox.isChecked());
        mDB.updateTask(selectedTask);
    }
}
