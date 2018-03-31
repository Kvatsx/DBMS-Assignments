import java.io.*;
import java.util.*;

public class parse_data {
    public static void main(String[] args) throws FileNotFoundException, IOException{
        BufferedReader input = new BufferedReader(new FileReader("datafile.csv"));
        String row = "";
        ArrayList<Data> dataList = new ArrayList<>();
        int counter = 0;
        while((row = input.readLine()) != null) {   
            if(row.length() > 0 && counter != 0) {
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
    }
}