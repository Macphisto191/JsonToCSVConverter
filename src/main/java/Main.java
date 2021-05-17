import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVWriter;
import entities.Report;
import entities.Seller;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static constants.GlobalConstants.SELLERS_PATH;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);


        Gson gson = new Gson();
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        Gson gson1 = gsonBuilder.create();


        System.out.println("Please enter sellers file path:");
//        String path = "src/main/resources/files/report.txt";
        String path = scanner.nextLine();
        System.out.println("Please enter report.txt file path:");
//        String reportPath = "src/main/resources/files/report.txt";
        String pathReport = scanner.nextLine();
        Report report = gson.fromJson(new FileReader(pathReport), Report.class);

        int periodLimit = report.getPeriodLimit();
        boolean useMultiplier = report.isUseExperienceMultiplier();

        Seller[] sellers = gson.fromJson(new FileReader(path), Seller[].class);

        for (Seller seller : sellers) {
            if (!report.isUseExperienceMultiplier()) {
                seller.setExperienceMultiplier(1.00);
            }
        }

        List<Seller> sortedSellers = sortingSellers(sellers);

        FileOutputStream fileOutputStream = new FileOutputStream("result.csv", true);
        PrintWriter printWriter = new PrintWriter(fileOutputStream);
        printWriter.println("Name, Score");


        double percentage = report.getTopPerformersThreshold();

        double threshold = Math.ceil((sortedSellers.size() * percentage) / 100);

        List<Seller> finalSellers = sortedSellers.subList(0, (int) threshold);


        fillingCSVFile(periodLimit, printWriter, finalSellers);

        printWriter.flush();
        printWriter.close();
        System.out.println("result.csv has been created.");


    }

    private static void fillingCSVFile(int periodLimit, PrintWriter printWriter, List<Seller> finalSellers) {
        finalSellers.stream().filter(s -> s.getSalesPeriod() <= periodLimit).forEach(s -> {

            String name = s.getName();
            double score = s.finalScore();
            printWriter.println(name + ", " + score);


        });
    }

    private static List<Seller> sortingSellers(Seller[] sellers) {
        List<Seller> sortedSellers = Arrays.stream(sellers)
                .sorted(Comparator.comparingDouble(Seller::finalScore)).collect(Collectors.toList());

        Collections.reverse(sortedSellers);
        sortedSellers.sort(Comparator.comparingInt(Seller::getSalesPeriod));
        return sortedSellers;
    }

}
