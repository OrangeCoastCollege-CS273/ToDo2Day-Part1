package edu.orangecoastcollege.cs273.sbadajoz.todo2day;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;


public class TaskListAdapter extends ArrayAdapter<Task> {
    public TaskListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull Task[] objects) {
        super(context, resource, objects);
    }
}
