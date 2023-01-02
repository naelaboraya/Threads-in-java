/**
 * This class is only for testing the results of applying the functions of class {@link Ex2_1}
 */
public class Test_Ex2_1 {

    public static void main(String[] args) {
        
        int seed = (int) System.currentTimeMillis();
        String[] file_names_100 = (Ex2_1.createTextFiles(10000,seed,100));
        System.out.println("-----------------------------------------------------------");
//        String[] file_names = {"file_1.txt","file_2.txt","file_3.txt","file_4.txt","file_5.txt"
//        ,"file_6.txt","file_7.txt","file_8.txt"};
        

        System.out.println("First function (normal) :");
       long start_function_1 = System.currentTimeMillis();
       int a = Ex2_1.getNumOfLines(file_names_100);
       long end_function_1 = System.currentTimeMillis();
       System.out.println(a);
       System.out.println("Time for first function = "+(double)(end_function_1 - start_function_1)/1000.0+" seconds");
       System.out.println("-----------------------------------------------------------");

        System.out.println("Second function (Threads) :");
        long start_function_2 = System.currentTimeMillis();
        int b = Ex2_1.getNumOfLinesThreads(file_names_100);
        long end_function_2 = System.currentTimeMillis();
        System.out.println(b);
        System.out.println("Time for second function = "+(double)(end_function_2 - start_function_2)/1000.0+" seconds");
        System.out.println("-----------------------------------------------------------");

        System.out.println("Third function (ThreadPool) :");
        long start_function_3 = System.currentTimeMillis();
        int c = Ex2_1.getNumOfLinesThreadPool(file_names_100);
        long end_function_3 = System.currentTimeMillis();
        System.out.println(c);
        System.out.println("Time for third function = "+(double)(end_function_3 - start_function_3)/1000.0+" seconds");
        System.out.println("-----------------------------------------------------------");
    }
}
