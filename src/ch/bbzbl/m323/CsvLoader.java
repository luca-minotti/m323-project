package src.ch.bbzbl.m323;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvLoader {

    public static List<DeathRecord> load(String filePath) {

        List<DeathRecord> records = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            reader.readLine();

            while ((line = reader.readLine()) != null) {

                String[] values = line.split(";");

                int year = Integer.parseInt(values[0]);
                int month = Integer.parseInt(values[1]);
                String gender = values[3];
                String ageGroup = values[4];

                int deaths = 0;
                if (!values[5].isEmpty()) {
                    deaths = Integer.parseInt(values[5]);
                }

                DeathRecord record = new DeathRecord(
                        year,
                        month,
                        gender,
                        ageGroup,
                        deaths
                );

                records.add(record);
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Fehler beim Lesen der CSV: " + e.getMessage());
        }

        return records;
    }
}
