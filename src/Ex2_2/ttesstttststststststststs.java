package Ex2_2;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ttesstttststststststststs {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);
    @Test
    public void partialTest(){
        CustomExecutor customExecutor = new CustomExecutor();
        var task = Task.createTask(()->{
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        }, TaskType.COMPUTATIONAL);
        var sumTask = customExecutor.submit(task);
        final int sum;
        try {
            sum = sumTask.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        logger.info(()-> "Sum of 1 through 10 = " + sum);



        Callable<Double> callable1 = ()-> {
            return 1000 * Math.pow(1.02, 5);
        };
        Callable<String> callable2 = ()-> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };
        var priceTask = customExecutor.submit(()-> {
            return 1000 * Math.pow(1.02, 5);
        }, TaskType.COMPUTATIONAL);
        var reverseTask = customExecutor.submit(callable2, TaskType.IO);
        final Double totalPrice;
        final String reversed;
        try {
            totalPrice = priceTask.get();
            reversed = reverseTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        logger.info(()-> "Reversed String = " + reversed);
        logger.info(()->String.valueOf("Total Price = " + totalPrice));
        logger.info(()-> "Current maximum priority = " + customExecutor.getCurrentMax());
        logger.info(()-> String.valueOf(customExecutor.getQueue()));
        customExecutor.gracefullyTerminate();
        logger.info(()-> String.valueOf(customExecutor.getQueue()));
    }



        @Test
        public void testTaskExecutionOrder() throws Exception {
            // Create a CustomExecutor
            CustomExecutor executor =new CustomExecutor();

            // Create tasks with different priorities
            Callable<Integer> task1 = () -> {
                // Perform some computation
                return 1;
            };
            Task<Integer> task1WithPriority = Task.of(TaskType.COMPUTATIONAL, task1);

            Callable<String> task2 = () -> {
                // Perform some computation
                return "Task 2 result";
            };
            Task<String> task2WithPriority = Task.of(TaskType.IO, task2);

            Callable<Double> task3 = () -> {
                // Perform some computation
                return 3.14;
            };
            Task<Double> task3WithPriority = Task.of(TaskType.OTHER, task3);

            // Submit the tasks to the executor
            Future<Integer> future1 = executor.submit(task1WithPriority);
            Future<String> future2 = executor.submit(task2WithPriority);
            Future<Double> future3 = executor.submit(task3WithPriority);

            // Check that the tasks are completed in the correct order
            assertEquals(1, future1.get().intValue());
            assertEquals("Task 2 result", future2.get());
            assertEquals(3.14, future3.get(), 0.001);
        }
//
//    @Test
//    public void testTaskExecutionOrder2() throws InterruptedException, ExecutionException {
//        // Set up the priority queue and custom executor
//        PriorityBlockingQueue<Task<String>> queue = new PriorityBlockingQueue<>();
//        custom_executor executor = new custom_executor();
//
//
//        // Create the tasks with different priorities
//        Task<String> task1 = Task.createTask(() -> "Task 1", TaskType.COMPUTATIONAL);
//        Task<String> task2 = Task.createTask(() -> "Task 2", TaskType.IO);
//        Task<String> task3 = Task.createTask(() -> "Task 3", TaskType.OTHER);
//
//        // Submit the tasks to the executor
//        ArrayList<Future<String>> futures = new ArrayList<>();
//        futures.add(executor.submit(task1));
//        futures.add(executor.submit(task3));
//        futures.add(executor.submit(task2));
//        futures.add(executor.submit(task1));
//
//        // Check that the tasks are executed in the correct order
//        assertEquals("Task 1", futures.get(0).get());
//        assertEquals("Task 2", futures.get(1).get());
//        assertEquals("Task 3", futures.get(2).get());
//        assertEquals("Task 1", futures.get(3).get());
//
//        executor.shutdown();
//    }
//

}
