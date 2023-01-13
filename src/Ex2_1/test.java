package Ex2_1;

import org.junit.jupiter.api.Test;

import java.io.File;

public class test {
    static int number_of_files = 100;//number of files to generate
    int seed = 1;//seed
    int bound = 100000;//max number of lines
    String[] file_names = Ex2_1.createTextFiles(number_of_files,seed,bound);//generating the files


    private static String[] get_files_names(){
        File folder = new File("./OutputFiles");
        File[] listOfFiles = folder.listFiles();
        int length = listOfFiles.length;
        String[] ans = new String[number_of_files];
        for (int i = 0; i < number_of_files; i++) {

            ans[i]=listOfFiles[i].getName();

        }

        return ans;
    }



    @Test
    void testnthread(){

        System.out.println("Second function (Threads) :");
        //calculating the time for function 2
        long start_function_2 = System.nanoTime();
        int b = Ex2_1.getNumOfLinesThreads(get_files_names());
        long end_function_2 = System.nanoTime();
        System.out.println("Total number of lines = "+b);
        System.out.println("Time for second function = "+(double)(end_function_2 - start_function_2)/1000000000.0+" seconds");
        System.out.println("-----------------------------------------------------------");

    }

    @Test
    void testnormal(){

        System.out.println("First function (normal) :");
        //calculating the time for function 1
        long start_function_1 = System.nanoTime();
        int a = Ex2_1.getNumOfLines(get_files_names());
        long end_function_1 = System.nanoTime();
        System.out.println("Total number of lines = "+a);
        System.out.println("Time for first function = "+(double)(end_function_1 - start_function_1)/1000000000.0+" seconds");
        System.out.println("-----------------------------------------------------------");
    }

    @Test
    void testnthreadpool(){

        System.out.println("Third function (ThreadPool) :");
        //calculating the time for function 3
        long start_function_3 = System.nanoTime();
        int c = Ex2_1.getNumOfLinesThreadPool(get_files_names());
        long end_function_3 = System.nanoTime();
        System.out.println("Total number of lines = "+c);
        System.out.println("Time for third function = "+(double)(end_function_3 - start_function_3)/1000000000.0+" seconds");
        System.out.println("-----------------------------------------------------------");


    }




}
