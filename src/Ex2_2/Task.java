package Ex2_2;

import java.util.concurrent.*;

public class Task<T> implements Comparable<Task<T>>, Future<T> {
    private final TaskType type;
    private final Callable<T> callable;
    private volatile T result;
    private volatile Exception exception;
    private volatile boolean isDone;
    private final CountDownLatch latch;

    private Task(TaskType type, Callable<T> callable) {
        this.type = type;
        this.callable = callable;
        this.latch = new CountDownLatch(1);
    }

    public Callable getCallable (){
        return this.callable;
    }

    public static <T> Task<T> of(Callable<T> callable) {
        return new Task<>(TaskType.OTHER, callable);//Default TaskType (Unknown task)
    }

    public static <T> Task<T> of(TaskType type, Callable<T> callable) {
        return new Task<>(type, callable);
    }

    public static <T> Task<T> createTask(Callable<T> callable, TaskType type) {
        return new Task<>(type, callable);
    }


    public TaskType getType() {
        return type;
    }

    public int getPriority(){//gets the priority of this task
        return this.type.getPriorityValue();
    }

    public void setPriority(int priority){//sets the priority to this task (in case of change)
        this.type.setPriority(priority);

    }

//    public T execute() throws Exception {
//        try {
//            result = callable.call();
//            return result;
//        } catch (Exception e) {
//            exception = e;
//            throw e;
//        } finally {
//            isDone = true;
//        }
//    }

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


//    @Override
//    public int compareTo(Task<T> other) {
//        return Integer.compare(type.getPriorityValue(), other.getType().getPriorityValue());
//    }

public int compare(Task t1, Task t2) {
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

    private TaskType getTaskType() {
        return this.type;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (isDone) {
            return false;
        }
        isDone = true;
        return true;
    }

    @Override
    public boolean isCancelled() {
        return isDone && result == null && exception == null;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    ///////////////////////////////////////////////////////////////////////
    @Override
    public T get() throws InterruptedException, ExecutionException {
        if (exception != null) {
            throw new ExecutionException(exception);
        }
        this.execute();
        return result;
    }

//    @Override
//    public T get(long timeout, TimeUnit unit)
//            throws InterruptedException, ExecutionException, TimeoutException {
//        if (exception != null) {
//            throw new ExecutionException(exception);
//        }
//        if (isDone) {
//            return result;
//        }
//        throw new TimeoutException();
//    }

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

    @Override
    public String toString() {
        return "Task{" +
                "type=" + type +
                ", result=" + result +
                ", isDone=" + isDone +
                '}';
    }

    @Override
    public int compareTo(Task<T> o) {
        return compare(this,o);
    }
}

