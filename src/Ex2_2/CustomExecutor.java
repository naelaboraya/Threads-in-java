package Ex2_2;

import java.util.concurrent.*;

/**
 The CustomExecutor class is a custom implementation of the ThreadPoolExecutor with the added feature of managing task priorities.
 Tasks are added to a PriorityBlockingQueue and executed according to their priority value.
 @author Nael Aboraya , Marwan Hresh
 @version 1.0
 */
public class CustomExecutor {
    private final int corePoolSize;
    private final int maximumPoolSize;
    private final long keepAliveTime;
    private final TimeUnit unit;
    private static PriorityBlockingQueue<Task<?>> queue;//priority queue that will be used in the executor , holds the tasks.
    private static ThreadPoolExecutor executor;


    /**
     Constructor for the CustomExecutor class , gives the priority queue to the constructor of ThreadPoolExecutor.
     <p>
     Default values are:
     corePoolSize = number of processors / 2
     <p>
     maximumPoolSize = number of processors - 1
     <p>
     keepAliveTime = 300ms
     <p>
     unit = TimeUnit.MILLISECONDS
     */
    public CustomExecutor() {
        int numProcessors = Runtime.getRuntime().availableProcessors();
        this.corePoolSize = numProcessors / 2;
        this.maximumPoolSize = numProcessors - 1;
        this.keepAliveTime = 300;
        this.unit = TimeUnit.MILLISECONDS;
        this.queue = new PriorityBlockingQueue<>();
        this.executor = new ThreadPoolExecutor(
                corePoolSize, maximumPoolSize, keepAliveTime, unit, (PriorityBlockingQueue)queue);
    }

    /**
     * @return the priority queue of this executor.
     */
    public PriorityBlockingQueue<Task<?>> getQueue() {
        return queue;
    }

    /**
     Submits a new task to the queue.
     @param <T> the type of the result returned by the callable task
     @param taskk the task to submit
     @return a Future of the task
     */
    public <T> Future<T> submit(Task<T> taskk) {
        queue.add(taskk);

        Future<T> f =  new Future<T>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return taskk.cancel(true);
            }
            @Override
            public boolean isCancelled() {
                return taskk.isCancelled();
            }
            @Override
            public boolean isDone() {
                return taskk.isDone();

            }
            @Override
            public T get() throws InterruptedException, ExecutionException {
                return taskk.get();
            }
            @Override
            public T get(long timeout, TimeUnit unit)
                    throws InterruptedException, ExecutionException, TimeoutException {
                return taskk.get(timeout, unit);
            }
        };
//        try {
//            f.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        return f;
    }

    /**
     Submits a new task with the specified task type to the queue.
     @param <T> the type of the result returned by the callable task
     @param callable the task to submit
     @param type the task type
     @return a Future of the task
     */
    public <T> Future<T> submit(Callable<T> callable, TaskType type) {
        return submit(Task.of(type, callable));
    }

    /**
     Submits a new task with the default task type (TaskType.OTHER) to the queue.
     @param <T> the type of the result returned by the callable task
     @param callable the task to submit
     @return a Future of the task
     */
    public <T> Future<T> submit(Callable<T> callable) {
        return submit(Task.of(callable));
    }


    /**
     * Executes all the tasks in the queue.
     * <p>Checks if the queue is empty , if it's not takes the first task in the queue (with the highest priority) ,
     * and executes it.
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void executeAllTasks() throws InterruptedException, ExecutionException {
        while (!queue.isEmpty()) {
            Task<?> task = queue.poll();
            task.execute();
            System.out.println(task.get());
        }
    }

    /**
     * Terminates the executor.
     */
    public void gracefullyTerminate() {
        //executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }


    /**
     *
     * @return the maximum priority of the tasks in the queue.
     */
    public int getCurrentMax(){
        return queue.poll().getPriority();
    }


    //toString , describes this executor
    @Override
    public String toString() {
        return  "PriorityQueue: " + this.getQueue().toString() + "\n"+
                "Max priority = " + getCurrentMax();
    }


}
