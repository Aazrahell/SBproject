import java.io.File;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        String path = "D:\\logs";
        File dir = new File(path);

        File[] files = dir.listFiles();

        if(!dir.isDirectory()){
            System.out.println("Directory '"+path+"' doesn't exist." );
            System.exit(0);
        }
        else{
            System.out.println("Directory '"+path+"' exist.");


            if (files.length == 0) {
                System.out.println("Directory is empty.");
            }
            else{
                //sort files
                for(int i=0; i<files.length-1; i++){
                    for(int j=0; j<files.length-1; j++){
                        if(files[i].lastModified() < files[i+1].lastModified()){
                            File temp = files[i];
                            files[i] = files[i+1];
                            files[i+1] = temp;
                        }
                    }
                }

                //print files
                for (File file : files) {
                    Date date = new Date(file.lastModified());
                    System.out.println(file+"  :  "+date);
                }
            }
        }
    }
}