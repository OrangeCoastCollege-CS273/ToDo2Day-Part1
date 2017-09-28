package edu.orangecoastcollege.cs273.sbadajoz.todo2day;

/**
 * Created by sbadajoz on 9/28/2017.
 */

public class Task {
    private int mID;
    private String mDescription;
    private boolean mIsDone;

    public Task(int id, String description, boolean isDone) {
        mID = id;
        mDescription = description;
        mIsDone = isDone;
    }

    public int getID() {
        return mID;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isDone() {
        return mIsDone;
    }

    public void setDone(boolean done) {
        mIsDone = done;
    }

    @Override
    public String toString() {
        return "Task{" +
                "mID=" + mID +
                ", mDescription='" + mDescription + '\'' +
                ", mIsDone=" + mIsDone +
                '}';
    }
}
