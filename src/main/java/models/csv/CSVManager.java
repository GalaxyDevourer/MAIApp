package models.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface CSVManager {
    default void writeToCSV (String[][] tableArray, String path, String fileName) {
        int size = tableArray[0].length;
        StringBuilder table = new StringBuilder();

        try(FileWriter writer = new FileWriter(path + "/" + fileName, false))
        {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    table.append(tableArray[i][j]).append(";");
                }
                table.append("\n");
            }

            writer.write(table.toString());
            writer.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    default String[][] readFromCSV (String path, String fileName, int size) {
        List<List<String>> lists = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path + "/" + fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";", -1);
                //System.out.println(Arrays.toString(values));
                lists.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[][] table = new String[size][size];
        for (int i = 0; i < lists.size(); i ++) {
            table[i] = (String[]) lists.get(i).toArray();
        }

        return table;
    }

}