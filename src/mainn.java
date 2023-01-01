import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class mainn {

    public static void main(String[] args) {
        int seed = (int) System.currentTimeMillis();
        System.out.println(Arrays.toString(Ex2_1.createTextFiles(100,seed,1000)));
        System.out.println("-----------------------------------------------------------");
        String[] file_names = {"file_1.txt","file_2.txt","file_3.txt","file_4.txt","file_5.txt"
        ,"file_6.txt","file_7.txt","file_8.txt"};

        System.out.println("First function (normal) :");
       long start_function_1 = System.currentTimeMillis();
       int a = Ex2_1.getNumOfLines(file_names);
       long end_function_1 = System.currentTimeMillis();
       System.out.println(a);
       System.out.println("Time for first function = "+(double)(end_function_1 - start_function_1)/1000.0+" seconds");
       System.out.println("-----------------------------------------------------------");

        System.out.println("Second function (Threads) :");
        long start_function_2 = System.currentTimeMillis();
        int b = Ex2_1.getNumOfLinesThreads(file_names);
        long end_function_2 = System.currentTimeMillis();
        System.out.println(b);
        System.out.println("Time for second function = "+(double)(end_function_2 - start_function_2)/1000.0+" seconds");
        System.out.println("-----------------------------------------------------------");
    }
}
