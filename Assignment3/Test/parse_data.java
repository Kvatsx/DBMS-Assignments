import java.io.*;
import java.util.*;

public class parse_data {

    public static String getCSVString(Data item) {
        String finalString = item.getInstructorId() + "," + item.getInstructorName() + "," + item.getDepartment() + "," + String.valueOf(item.getSalary()) + '\n';
        return finalString;
    }

    public static void createCSV(ArrayList<Data> dataList, String fileName) throws IOException{
        // String fileName = "testDataFile.csv";
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.append("Validity Tag,Instructor ID,Instructor Name,Department,Salary");
        fileWriter.append("\n");
        for (Data data : dataList) {
            fileWriter.append(getCSVString(data));
        }
        fileWriter.flush();
        fileWriter.close();

    }

    public static ArrayList<Data> readCSV(String fileName) {
        BufferedReader input = new BufferedReader(new FileReader("datafile.csv"));
        String row = "";
        ArrayList<Data> dataList = new ArrayList<>();
        int counter = 0;
        while ((row = input.readLine()) != null) {
            if (row.length() > 0 && counter != 0) {
                String[] rowElements = row.split(",");
                String id = rowElements[1].replace("\"", "");
                String name = rowElements[2].replace("\"", "");
                String dept = rowElements[3].replace("\"", "");
                String salary = rowElements[4].replace("\"", "");
                Data item = new Data(id, name, dept, salary);
                dataList.add(item);
            }
            counter += 1;
        }
        input.close();
        return dataList;
    }
}