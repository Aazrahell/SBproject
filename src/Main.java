import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

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

    public static void logsDate(StringBuilder text){
        String[] find = text.toString().split("\\s+");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder dates = new StringBuilder();

        for(String d : find){
            if (d.matches("\\d{4}-\\d{2}-\\d{2}")) {
                try {
                    format.parse(d);
                    Date date2 = format.parse(d);
                    long millis = date2.getTime();
                    int days = ((int) (millis / (1000*60*60*24)));
                    dates.append(days).append(" ");
                    //System.out.println(days);
                } catch (ParseException e) {
                    System.out.println("Error: "+e);
                }

            }
        }
        String[] datesStrings = dates.toString().split("\\s+");
        int newestNumber = 0;
        int oldestNumber = 0;

        for(int i=0; i<datesStrings.length-1; i++){
            int n1 = Integer.parseInt(datesStrings[i]);
            int n2 = Integer.parseInt(datesStrings[i+1]);
            //System.out.println(n1);

            if(n1 > n2){
                newestNumber = n1;
            }
            if(n1 > n2){
                oldestNumber = n2;
            }
        }

        int diffDate = newestNumber - oldestNumber;
        System.out.println("\nDifference between newest and oldest log = "+diffDate+" days.");
    }
    public static int[] countSeverity(StringBuilder text){
        String[] find = text.toString().split("\\s+");

        //System.out.println();
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

                    logsDate(lines);
                }
            }
        }
    }
}
