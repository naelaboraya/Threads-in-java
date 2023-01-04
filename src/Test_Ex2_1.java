import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * This class is only for testing the results of applying the functions of class {@link Ex2_1}
 */
public class Test_Ex2_1 {



    /**
     * A main method that contains tests for all 4 public functions of class EX2_1.
     * <p>We have created n text files using the first method (createTextFiles) , and applied the other
     * three methods on these files (getNumOfLines , getNumOfLinesThreads and getNumOfLinesThreadPool).
     * <p>We have tested if the results were the same , and tested the time that was taken for each method
     * to finish its work by calculating the time before and after calling each method , and calculating
     * the difference between the end time and the start time.
     */
    public static void main(String[] args) {
        int number_of_files = 1000;//number of files to generate
        int seed = 12;//seed
        int bound = 1000;//max number of lines
        String[] file_names = Ex2_1.createTextFiles(number_of_files,seed,bound);//generating the files
//        String  File_names = Arrays.toString(get_files_names());
//        System.out.println(File_names);//printing the names of files (output of function 1)
//        System.out.println("-----------------------------------------------------------");

        System.out.println("Second function (Threads) :");
        //calculating the time for function 2
        long start_function_2 = System.currentTimeMillis();
        int b = Ex2_1.getNumOfLinesThreads(get_files_names());
        long end_function_2 = System.currentTimeMillis();
        System.out.println("Total number of lines = "+b);
        System.out.println("Time for second function = "+(double)(end_function_2 - start_function_2)/1000.0+" seconds");
        System.out.println("-----------------------------------------------------------");

        System.out.println("Third function (ThreadPool) :");
        //calculating the time for function 3
        long start_function_3 = System.currentTimeMillis();
        int c = Ex2_1.getNumOfLinesThreadPool(get_files_names());
        long end_function_3 = System.currentTimeMillis();
        System.out.println("Total number of lines = "+c);
        System.out.println("Time for third function = "+(double)(end_function_3 - start_function_3)/1000.0+" seconds");
        System.out.println("-----------------------------------------------------------");

        System.out.println("First function (normal) :");
        //calculating the time for function 1
       long start_function_1 = System.currentTimeMillis();
       int a = Ex2_1.getNumOfLines(get_files_names());
       long end_function_1 = System.currentTimeMillis();
       System.out.println("Total number of lines = "+a);
       System.out.println("Time for first function = "+(double)(end_function_1 - start_function_1)/1000.0+" seconds");
       System.out.println("-----------------------------------------------------------");




    }

    //a private helping function that returns all the names of the files in the directory/folder "OutputFiles"
    //could be used as input to the main methods
    private static String[] get_files_names(){
        File folder = new File("./Outputfiles");
        File[] listOfFiles = folder.listFiles();
        int length = listOfFiles.length;
        String[] ans = new String[length];
        for (int i = 0; i < length; i++) {
            if (listOfFiles[i].isFile()) {
                ans[i]=listOfFiles[i].getName();
            }
        }

        return ans;
    }
}

