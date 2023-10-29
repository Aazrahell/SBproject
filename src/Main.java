import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;
import java.util.HashSet;



public class Main {

    public static File[] sortFiles(File[] files){
        for(int i=0; i<files.length-1; i++){
            for(int j=0; j<files.length-1; j++){
                if(files[i].lastModified() < files[i+1].lastModified()){
                    File temp = files[i];
                    files[i] = files[i+1];
                    files[i+1] = temp;
                }
            }
        }
        return files;
    }
    public static int[] countSeverity(StringBuilder text){
        String[] find = text.toString().split("\\s+");
        int[] counter = new int[6];
        HashSet<String> uniqueLibs = new HashSet<>();

        for (String word : find) {
            switch (word) {
                case "FATAL":
                    counter[0]++;
                    break;
                case "ERROR":
                    counter[1]++;
                    break;
                case "WARN":
                    counter[2]++;
                    break;
                case "INFO":
                    counter[3]++;
                    break;
                case "DEBUG":
                    counter[4]++;
                    break;
                case "TRACE":
                    counter[5]++;
                    break;
            }
            if(word.charAt(0) == '[' && word.charAt((word.length()) - 1) == ']'){
                uniqueLibs.add(word);
            }
        }

        System.out.println(
                "\nFATAL: "+counter[0]+
                "\nERROR: "+counter[1]+
                "\nWARN: "+counter[2]+
                "\nINFO: "+counter[3]+
                "\nDEBUG: "+counter[4]+
                "\nTRACE: "+counter[5]);

        System.out.println("\nUnique libraries(number:"+uniqueLibs.size()+"):\n"+uniqueLibs);

        return counter;
    }

    public static void main(String[] args) {
        String path = "D:\\logs";
        File dir = new File(path);

        File[] files = dir.listFiles();

        if(!dir.isDirectory()){
            System.out.println("Directory '"+path+"' doesn't exist.\n" );
            System.exit(0);
        }
        else{
            if(files == null){
                System.out.println("Error");
                System.exit(0);
            }

            System.out.println("Directory '"+path+"' exist.\n");

            if (files.length == 0) {
                System.out.println("Directory is empty.\n");
                System.exit(0);
            }
            else{
                //sort files
                File[] sortedFiles = sortFiles(files);

                //print files
                for (File file : sortedFiles) {
                    Date date = new Date(file.lastModified());
                    System.out.println(file+"  :  "+date+"\n");
                }

                for (File file : sortedFiles) {
                    Scanner scan;

                    try {
                        scan = new Scanner(new File(file.toString()));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    StringBuilder lines = new StringBuilder();

                    // start timer
                    double startTimer = System.currentTimeMillis();

                    while (scan.hasNextLine()) {
                        String line = scan.nextLine();
                        //System.out.println(line);
                        lines.append(line).append("\n");
                    }

                    //end Timer
                    double endTimer = System.currentTimeMillis();

                    double calculateTime = endTimer - startTimer;

                    //print entire content of log
                    //System.out.println(lines);

                    System.out.println("\n#################### FILE "+file.getName()+" ####################");

                    //print read time
                    System.out.println("\nRead time: "+calculateTime + "ms");

                    //print severity count
                    //countSeverity(lines);
                    int[] severity = countSeverity(lines);

                    int fatalerrorCounter = severity[0] + severity[1];
                    int sumSeverity = 0;

                    for (int val : severity) {
                        sumSeverity += val;
                    }

                    System.out.println("\nERROR and higher/All logs\n"+fatalerrorCounter+"/"+sumSeverity);
                }
            }
        }
    }
}
