import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        List<File> files = new ArrayList<>();
        String path;
        Scanner scanner = new Scanner(System.in);

//        reading and writing files to ArrayList
        for (int i = 0; !(path = scanner.nextLine()).equals("0"); ++i) {
            files.add(new File(path));
            if (!files.get(i).isFile()) {
                System.out.println("there is no file in the specified path");
                return;
            }
        }

        scanner.close();

        Set<String> documentNumbers = new HashSet<>();

//        adding non-duplicate numbers to HashSet
        for (File f : files) {
            scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                documentNumbers.add(scanner.nextLine());
            }
            scanner.close();
        }

//        created report-file
        File reportFile = new File( "Report file.txt");

        try {
            if (reportFile.createNewFile()) {
                System.out.println("report-file created");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        Map<String, String> verifiedNumbers = new HashMap<>();

//        adding verified numbers to HashMap
        String report;
        for (String num : documentNumbers) {
            if (isValidDocument(num)) {
                report = "Valid";
            } else {
                report = "Invalid, " + printDocReport(num);
            }
            verifiedNumbers.put(num, report);
        }

//        writing to a report-file
        try (Writer writerReportFile = new FileWriter(reportFile)) {
            for (Map.Entry<String, String> item : verifiedNumbers.entrySet()) {
                writerReportFile.write(item.getKey() + " - " + item.getValue() + '\n');
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

    }

    public static boolean isValidDocument(String doc) {
        if (doc.length() == 15 && isBeginWith(doc)) {
            return true;
        }
        return false;
    }

    public static boolean isBeginWith(String doc) {
        if (doc.indexOf("docnum") == 0 || doc.indexOf("contract") == 0) {
            return true;
        }
        return false;
    }

    public static String printDocReport(String doc) {
        String docReport = "";
        int cnt = 0;

        if (doc.length() < 15) {
            docReport = "name less than 15 characters";
            cnt++;
        } else if (doc.length() > 15){
            docReport = "name more than 15 characters";
            cnt++;
        }

        if (!isBeginWith(doc)) {
            if (cnt == 1) {
                docReport += ", name does not start with \"docum\" or \"contract\".";
            } else {
                docReport = "name does not start with \"docum\" or \"contract\".";
            }
        } else {
            docReport += ".";
        }

        return docReport;
    }

}
