import java.io.*;
import java.util.Random;


public class Ex2_1 {


    public static String[] createTextFiles(int n, int seed, int bound){
        String[] names = new String[n];//The output : array of strings that will contain the names of the files
        Random rand = new Random(seed);//a Random object with the seed value

        for(int i=0;i<n;i++){
            int numOfLines = rand.nextInt(bound) + 1;//generating a random number of lines between 1 and seed
            try {
                FileWriter fw = new FileWriter("file_" + (i + 1)+".txt");//creating a file

                for (int line = 0; line < numOfLines; line++) {//for each line in the text file :
                    StringBuilder sb = new StringBuilder();
                    int length = rand.nextInt(21) + 10;  // random length between 10 and 30 (length of line)
                    for (int ch = 0; ch < length; ch++) {
                        char c = (char)(rand.nextInt(26) + 'a');
                        sb.append(c);//appending to every line a random string
                    }
                    fw.write(sb.toString() + "\n");//writing the random string to the text file
                }

                names[i] = "file_" + (i + 1);//adding thee name of file i to the array at index i
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return names;
    }


    //A private helping function that calculates the number of lines in a single Text file
    private static int TextFileNumOfLines(String filename){
        int count = 0 ;
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            while(br.readLine()!=null){
                count++;
            }
            br.close();
            fr.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
       return count;
    }


    public static int getNumOfLines(String[] fileNames){
        int num_of_total_files_lines = 0;

        for (String file : fileNames){
            num_of_total_files_lines += TextFileNumOfLines(file);
        }
        return num_of_total_files_lines;
    }

   //class
   static class NumOfLinesThreads extends Thread{
        private String file_name;
        private int num_of_lines;

        public NumOfLinesThreads(String file_name){
            this.file_name = file_name;
            this.num_of_lines = 0;
        }

        public int getNum_of_lines() {
            return this.num_of_lines;
        }

        public void run(){
            try {
                FileReader fr = new FileReader(this.file_name);
                BufferedReader br = new BufferedReader(fr);

                while(br.readLine()!=null){
                    this.num_of_lines++;
                }
                br.close();
                fr.close();
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //

    private static int TextFileNumOfLinesThreads(String filename){

        NumOfLinesThreads thread = new NumOfLinesThreads(filename);

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int num_of_lines = thread.getNum_of_lines();
        return num_of_lines;
    }


    public static int getNumOfLinesThreads(String[] fileNames){
        int num_of_total_files_lines = 0;

        for (String file : fileNames){
            num_of_total_files_lines += TextFileNumOfLinesThreads(file);
        }
        return num_of_total_files_lines;
    }





}
