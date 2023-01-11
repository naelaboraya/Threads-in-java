package Ex2_2;
import java.util.concurrent.*;

/**

 The Task class represents a task that can be executed by a threadpool.
 <p>
 It has a priority that determines its order of execution , (1/COMPUTATIONAL is the highest and 3/OTHER is the lowest).
 <p>
 It implements the Comparable and Future interfaces
 @param <T> the type of the result returned by the callable task

 @author Nael Aboraya , Marwan Hresh

 @version 1.0
 */
public class Task<T> implements Comparable<Task<T>>, Future<T> {
    private final TaskType type;
    private final Callable<T> callable;
    private volatile T result;
    private volatile Exception exception;
    private volatile boolean isDone;
    private final CountDownLatch latch;


    /**
     Constructor (private constructor) for the Task class. It receives a type of task and a callable task.
     @param type the type of task
     @param callable the callable task
     */
    private Task(TaskType type, Callable<T> callable) {
        this.type = type;
        this.callable = callable;
        this.latch = new CountDownLatch(1);
    }

    /**
     Create a new task from a callable , sets the priority as default (OTHER).
     @param <T> the type of the result returned by the callable task
     @param callable the callable task
     @return a new Task instance
     */
    public static <T> Task<T> of(Callable<T> callable) {
        return new Task<>(TaskType.OTHER, callable);//Default TaskType (Unknown task)
    }

    /**
     Create a new task of the specified type
     @param <T> the type of the result returned by the callable task
     @param type the type of the task
     @param callable the callable task
     @return a new Task instance
     */
    public static <T> Task<T> of(TaskType type, Callable<T> callable) {
        return new Task<>(type, callable);
    }

    /**
     Create a new task of the specified type (the same as of(TaskType type, Callable<T> callable) , but accepting callable first).
     @param <T> the type of the result returned by the callable task
     @param callable the callable task
     @param type the type of the task
     @return a new Task instance
     */
    public static <T> Task<T> createTask(Callable<T> callable, TaskType type) {
        return new Task<>(type, callable);
    }

    //getters :
    /**
     * Gets this task callable.
     * @return
     */
    public Callable getCallable (){
        return this.callable;
    }

    /**
     * Gets this task's type.
     * @return
     */
    private TaskType getTaskType() {
        return this.type;
    }

    /**
     * Gets this task's priority integer value.
     * @return
     */
    public int getPriority(){//gets the priority of this task
        return this.type.getPriorityValue();
    }

    /**
     * Execute this task , calls the 'call' function of this task's callable , and makes 'isDone' true
     * indicating that the task has been executed.
     */
    public void execute() {
        try {
            result = callable.call();
        } catch (Exception e) {
            exception = e;
        } finally {
            isDone = true;
            latch.countDown();
        }
    }

    /**
     * Compares this task to the given task 'o' according to priority.
     * <p>Calls the method 'compare' that takes two tasks.
     * @param o
     * @return 0 if the tasks' priorities are equal , otherwise returns -1 or 1 depending on which task's priority is higher or lower.
     */
    @Override
    public int compareTo(Task<T> o) {
        return compare(this,o);
    }

    //A private helping method that is used in compareTo method.
    private int compare(Task t1, Task t2) {
        if (t1.getTaskType() == t2.getTaskType()) {
            return 0;
        }
        switch (t1.getTaskType()) {
            case COMPUTATIONAL:
                return -1;
            case IO:
                if (t2.getTaskType() == TaskType.COMPUTATIONAL) {
                    return 1;
                } else {
                    return -1;
                }
            case OTHER:
                return 1;
            default:
                throw new IllegalArgumentException("Invalid task type");
        }
    }


    /**
     * Cancels the task.
     * @param mayInterruptIfRunning
     * @return boolean value.
     */
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (isDone) {
            return false;
        }
        isDone = true;
        return true;
    }

    /**
     * @return true if the task is canceled.
     */
    @Override
    public boolean isCancelled() {
        return isDone && result == null && exception == null;
    }

    /**
     *
     * @return true if the task is done (executed).
     */
    @Override
    public boolean isDone() {
        return isDone;
    }

    /**
     * Gets the value that was returned from the callable of this task.
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Override
    public T get() throws InterruptedException, ExecutionException {
        if (exception != null) {
            throw new ExecutionException(exception);
        }
        this.execute();
        return result;
    }

    /**
     * Waits if necessary for at most the given time for the computation to complete, and then retrieves its result, if available
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    @Override
    public T get(long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        // Wait for the task to complete
        this.execute();
        latch.await(timeout, unit);

        if (exception != null) {
            throw new ExecutionException(exception);
        }
        return result;
    }
    //toString , describes this task
    @Override
    public String toString() {
        return "Task{" +
                "type=" + type +
                ", result=" + result +
                ", isDone=" + isDone +
                '}';
    }



}


