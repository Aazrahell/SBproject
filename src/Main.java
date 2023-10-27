import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String path = "D:\\logs";
        File dir = new File(path);

        File[] files = dir.listFiles();

        if(dir.isDirectory()){
            System.out.println("Directory '"+path+"' exist" );
        }
        else{
            System.out.println("Directory '"+path+"' doesn't exist");
        }
    }
}