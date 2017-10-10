package edu.orangecoastcollege.cs273.sbadajoz.todo2day;

/**
 * Represents a task
 * Can be completed or not based on data
 * Has a description that explains what needs to be done
 */

public class Task {
    private int mID;
    private String mDescription;
    private boolean mIsDone;

    /**
     * Constructor to be used by database fetches
     *
     * @param id ID of task in database
     * @param description Description of task
     * @param isDone Whether the task has been completed or not
     */
    public Task(int id, String description, boolean isDone) {
        mID = id;
        mDescription = description;
        mIsDone = isDone;
    }

    /**
     * Constructor to be used by user data input
     *
     * @param description Description of task
     * @param isDone Whether the task has been completed or not
     */
    public Task(String description, boolean isDone) {
        mDescription = description;
        mIsDone = isDone;
    }

    /**
     * Returns the id of the task
     * @return task ID
     */
    public int getID() {
        return mID;
    }

    /**
     * Returns the description of the task
     * @return task description
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * Resets the description of the task
     * @param description New task description
     */
    public void setDescription(String description) {
        mDescription = description;
    }

    /**
     * Returns if the task has been completed or not
     * @return True if task has been completed
     */
    public boolean isDone() {
        return mIsDone;
    }

    /**
     * Sets if the task has been completed
     * @param done True if task has been completed
     */
    public void setDone(boolean done) {
        mIsDone = done;
    }

    /**
     * Basic string output of task data members
     * @return A string of all the data regarding the task
     */
    @Override
    public String toString() {
        return "Task{" +
                "mID=" + mID +
                ", mDescription='" + mDescription + '\'' +
                ", mIsDone=" + mIsDone +
                '}';
    }
}
