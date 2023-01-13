package Ex2_2;

import java.util.concurrent.*;

/**
 ***
 *
 *  The Task class represents a task that can be executed by a ThreadPool.
 *  <p>
 *  It has a priority [1 ,10] that determines its order of execution , it has three types : "COMPUTATIONAL" , "IO" and "OTHER".
 *  <p>
 *  It implements the Callable interface.
 *  @param <T> the type of the result returned by the callable task
 *
 *  @author Nael Aboraya , Marwan Hresh
 *
 *  @version 1.0
 *  */
public class Task<T> implements Callable<T> {
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
    public TaskType getType() {
        return type;
    }

    /**
     * Gets this task's priority integer value.
     * @return
     */
    public int getPriority(){//gets the priority of this task
        return this.type.getPriorityValue();
    }

    /**
     * Sets the priority of this task to be "priority".
     * @param priority
     */
    public void setPriority(int priority){//sets the priority to this task (in case of change)
        this.type.setPriority(priority);
    }

    /**
     * calls the call function.
     * @return the call value of the callable operation of this task.
     * @throws Exception
     */
    @Override
    public T call() throws Exception {
        return this.callable.call();
    }

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

