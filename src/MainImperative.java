package src;

import src.ch.bbzbl.m323.CsvLoader;
import src.ch.bbzbl.m323.DeathRecord;
import src.ch.bbzbl.m323.DeathStatisticsImperative;

import java.util.List;

public class MainImperative {

    public static void main(String[] args) {

        List<DeathRecord> records = CsvLoader.load("data/100173.csv");

        // DeathStatisticsImperative.compareGenders(records, 2024);
        // DeathStatisticsImperative.allAgeGroups(records, 2025);
        // DeathStatisticsImperative.topThreeMonths(records, 2024);
        // DeathStatisticsImperative.totalDeathsOneYear(records, 2024);
        // DeathStatisticsImperative.totalDeathsMultipleYears(records, 2019, 2025);

        /*
        String[] allAgeGroups = {
                "0-4", "5-9", "10-14", "15-19", "20-24",
                "25-29", "30-34", "35-39", "40-44",
                "45-49", "50-54", "55-59", "60-64",
                "65-69", "70-74", "75-79", "80-84",
                "85-89", "90-94", "95-99", "100+"
        };
        */

        // String[] ageGroups = { "65-69", "70-74", "75-79" };

        // DeathStatisticsImperative.compareAgeGroupsMultipleYears(records, 2018, 2022, ageGroups);
        // DeathStatisticsImperative.compareAgeGroupTwoYears(records, 2016, 2020, ageGroups);
    }
}