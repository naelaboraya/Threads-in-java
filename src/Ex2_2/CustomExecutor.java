package Ex2_2;

import java.util.concurrent.*;

public class CustomExecutor {
    private final int corePoolSize;
    private final int maximumPoolSize;
    private final long keepAliveTime;
    private final TimeUnit unit;
    private static PriorityBlockingQueue<Task<?>> queue;
    private static ThreadPoolExecutor executor ;



    public CustomExecutor() {
        int numProcessors = Runtime.getRuntime().availableProcessors();
        this.corePoolSize = numProcessors / 2;
        this.maximumPoolSize = numProcessors - 1;
        this.keepAliveTime = 300;
        this.unit = TimeUnit.MILLISECONDS;
        this.queue = new PriorityBlockingQueue<>();
        this.executor = new ThreadPoolExecutor(
                corePoolSize, maximumPoolSize, keepAliveTime, unit, (PriorityBlockingQueue)queue  );
    }


    public PriorityBlockingQueue<Task<?>> getQueue() {
        return queue;
    }

//    public static <T> Future<T> submit(Task<T> task) {
//        if (task == null) {
//            throw new NullPointerException("Task is null !");
//        }
//        executor.execute(() -> {
//            try {
//                task.execute();
//            } catch (Exception e) {
//                task.cancel(true);
//            }
//        });
//        queue.add(task);
//        return task;
//    }
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


    public <T> Future<T> submit(Callable<T> callable, TaskType type) {
        return submit(Task.of(type, callable));
    }

    public <T> Future<T> submit(Callable<T> callable) {
        return submit(Task.of(callable));
    }

    public void executeAllTasks() throws InterruptedException, ExecutionException {
        while (!queue.isEmpty()) {
            Task<?> task = queue.poll();
            task.execute();
            System.out.println(task.get());
        }
    }

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



    public int getCurrentMax(){
        return queue.poll().getPriority();
    }

    @Override
    public String toString() {
        return  "PriorityQueue: " + this.getQueue().toString() + "\n"+
                "Max priority = " + getCurrentMax();
    }


}
