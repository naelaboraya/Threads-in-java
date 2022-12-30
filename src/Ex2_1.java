import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class Ex2_1 {

    public static String[] createTextFiles(int n, int seed, int bound){
        String[] names = new String[n];
        for(int i=0;i<n;i++){
            try {
                String nameOfFile = "file_"+(i+1)+".txt";
                FileWriter fw = new FileWriter(nameOfFile);
                names[i] = nameOfFile;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return names;
    }

}
