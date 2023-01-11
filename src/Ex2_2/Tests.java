package Ex2_2;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import java.util.concurrent.*;

public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);
    @Test
    public void partialTest(){
        CustomExecutor customExecutor = new CustomExecutor();
        Task task = Task.createTask(()->{
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        }, TaskType.COMPUTATIONAL);
        Future<Integer> sumTask = customExecutor.submit(task);
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
        // var is used to infer the declared type automatically
        Future<Double> priceTask = customExecutor.submit(()-> {
            return 1000 * Math.pow(1.02, 5);
        }, TaskType.COMPUTATIONAL);
        Future<String> reverseTask = customExecutor.submit(callable2, TaskType.IO);
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
        logger.info(()-> "Current maximum priority = " +
                customExecutor.getCurrentMax());
        customExecutor.gracefullyTerminate();
    }

    @Test
    public void secondTest(){
        CustomExecutor customExecutor = new CustomExecutor();

        for (int i = 0; i < 500; ++i)
        {
            Task task1 = Task.createTask(()->{
                int sum = 0;
                for (int j = 1; j <= 10; j++) {
                    sum += j;
                }
                return sum;
            }, TaskType.COMPUTATIONAL);

            Callable<Double> callable1 = ()-> {
                return 1000 * Math.pow(1.02, 5);
            };
            Callable<String> callable2 = ()-> {
                StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
                return sb.reverse().toString();
            };

            if(i%2 == 0) customExecutor.submit(task1);
            if(i%5 == 0)customExecutor.submit(callable1, TaskType.OTHER);
            if(i%3 == 0)customExecutor.submit(callable2, TaskType.IO);

            logger.info(()-> customExecutor.toString());
        }

        logger.info(()-> customExecutor.toString());
        customExecutor.gracefullyTerminate();

    }

}
