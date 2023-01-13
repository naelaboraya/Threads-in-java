package Ex2_2;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**

 The ComparableTask class is a custom implementation of the FutureTask class that implements the Comparable interface.
 <p>It provides a way to compare tasks based on their priorities. It holds a Task object
 <p>It provides a constructor that takes a Callable task and creates a FutureTask and a Task object from it.
 <p>It also provides a getTask method that returns the Task object.
 <p>The compareTo method compares the priorities of two tasks and returns a negative integer, zero, or a positive integer as this task is less than,
 equal to, or greater than the specified task.
 <p>The ComparableTask class is used as an adapter.
 It adapts the FutureTask class to make it comparable based on the priorities of the tasks. The main purpose of this class is to allow tasks to be prioritized in a queue,
 so that the tasks with higher priorities are executed first.
 @author Nael Aboraya , Marwan Hresh
 @version 1.0
 */
public class ComparableTask<T> extends FutureTask <T> implements Comparable <ComparableTask<T>> {

    private Task <T> task;

    public ComparableTask(Callable <T> task) {
        super(task);
        this.task =  Task.of(task);
    }

    public Task <T> getTask() {
        return this.task;
    }

    @Override
    public int compareTo(ComparableTask<T> otherTask) {
        int ans = 0;
        if (this.getTask().getType() != null && otherTask.getTask().getType() != null)
            ans = Integer.compare(this.getTask().getPriority(), otherTask.getTask().getPriority());

        return ans;
    }
}