package Ex2_2;

import java.util.HashMap;
import java.util.concurrent.*;
/**

 CustomExecutor class is a custom implementation of the ThreadPoolExecutor.
 It provides a fixed number of threads and uses a PriorityBlockingQueue to hold the tasks.
 The tasks are also stored in a HashMap where the key is the task's priority and the value is the task itself.
 <p>The thread pool is initialized with a minimum pool size of {@code MinPoolSize} (which is the number of the cores in the computer divided by 2),
 a maximum pool size of {@code MaxPoolSize} (which is the number of the cores in the computer minus 1),
 a keep-alive time of {@code keepAlive} and a time unit of {@code unit}.
 <p>This class also provides methods for getting the tasks, setting max priority and submitting tasks to the queue.
 <p>The class also overrides the newTaskFor method, in order to provide a custom RunnableFuture implementation, which allows the task
 to be prioritized based on its natural ordering or a comparator provided at construction time.
 <p>The class also provides a submit method which adds the submitted task to the HashMap and updates the max priority if necessary.
 @version 1.0
 @author Nael Aboraya , Marwan Hresh
 */
public class CustomExecutor extends ThreadPoolExecutor {

    private HashMap <Integer, Task> tasks;//A hashMap that stores the tasks (key is the task's priority's integer value and the value is the task itself).
    private static final int numCores = Runtime.getRuntime().availableProcessors();//number of cores in computer.
    //Min and Max pool size
    private static final int MinPoolSize = numCores / 2;
    private static final int MaxPoolSize = numCores - 1;
    private static final TimeUnit unit = TimeUnit.MILLISECONDS;
    private static final long keepAlive = 300;
    private int MaxPriority;//The highest priority in the pool's queue.


    /**
     * Creates a new thread pool with a fixed number of threads.
     * <p>The thread pool is initialized with a minimum pool size of {@code MinPoolSize} (which is the number of the
     * cores in the computer divided by 2), a maximum pool size of {@code MaxPoolSize} (which is the number of the
     * cores in the computer minus 1), a keep-alive time of {@code keepAlive} and a time unit of {@code unit}.
     * <p>The internal task queue is implemented as a {@link PriorityBlockingQueue},
     * which allows tasks to be prioritized based on their natural ordering or a comparator provided at
     * construction time.
     * <p>A {@link HashMap} is also created to store the tasks and their priorities.
     * <p>The default maximum priority is set to 1.
     */
    public CustomExecutor() {
        super(MinPoolSize, MaxPoolSize, keepAlive, unit, new PriorityBlockingQueue<>());
        this.tasks = new HashMap <> ();
        this.MaxPriority = 1;//default max priority is 1.
    }




    /**
     * Sets the current max priority to be 'setMax'
     * @param setMax
     */
    public void setMaxPriority(int setMax) {
        if (setMax >= 1 && setMax <= 10)//checking if the priority's value is valid
            this.MaxPriority = setMax;
    }



    /**
     Submits a new task to the queue.
     <p>Adds the submitted task to the hashmap.
     <p>Checks and updates the max priority.
     @param <T> the type of the result returned by the callable task
     @param task the task to submit
     @return a Future of the task
     */
    public synchronized <T> Future <T> submit(Task <T> task) {
        try {
            this.tasks.put(task.getPriority(), task);
            if (task.getPriority() >= this.MaxPriority)
                this.setMaxPriority(task.getPriority());
            return super.submit(task.getCallable());
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getCause());
        }
    }


    /**
     Submits a new task with the specified task type to the queue.
     @param <T> the type of the result returned by the callable task
     @param callable the task to submit
     @param type the task type
     @return a Future of the task
     */
    public synchronized <T> Future <T> submit(Callable <T> callable, TaskType type) {
        return this.submit(Task.createTask (callable, type));
    }

    /**
     Submits a new task with the default task type (TaskType.OTHER) to the queue.
     @param <T> the type of the result returned by the callable task
     @param callable the task to submit
     @return a Future of the task
     */
    public synchronized <T> Future <T> submit(Callable <T> callable) {
        return this.submit(Task.of(callable));
    }


    /**
     Creates a new {@link RunnableFuture} for the given {@link Callable}.
     <p>This method is protected and intended to be overridden by subclasses in order to provide a custom
     {@link RunnableFuture} implementation. The returned {@link RunnableFuture} is wrapped in a
     {@link ComparableTask} class, which allows the task to be prioritized based on its
     natural ordering or a comparator provided at construction time.
     @param <T> the type of the task's result
     @param task the {@link Callable} to be wrapped
     @return a new {@link RunnableFuture} wrapping the given task
     */
    protected <T> RunnableFuture <T> newTaskFor(Callable <T> task) {
        return new ComparableTask <> (task);
    }

    /**
     *
     * @return the maximum priority of the tasks in the queue.
     */
    public synchronized int getCurrentMax() {
        return this.MaxPriority;
    }


    /**
     * Terminates the executor.
     */
    public synchronized void gracefullyTerminate() {
        super.shutdown();
    }

    /**
     * @return the map that contains the tasks.
     */
    public HashMap <Integer, Task> getTasks() {
        return this.tasks;
    }

    //toString , describes this executor
    @Override
    public String toString() {
        return "Submitted tasks : {" +
                "tasks=" + tasks +
                ", MaxPriority=" + MaxPriority +
                '}';
    }
}