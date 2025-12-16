package src;

import src.ch.bbzbl.m323.CsvLoader;
import src.ch.bbzbl.m323.DeathRecord;
import src.ch.bbzbl.m323.DeathStatisticsImperative;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<DeathRecord> records =
                CsvLoader.load("data/100173.csv");

        DeathStatisticsImperative.printGenderComparisonTable(records, 2024);
//        DeathStatisticsImperative.printAgeGroupSummaryForYear_AllGender(records, 2025);
//        DeathStatisticsImperative.printTopThreeMonths_AllGender_AllAge(records, 2024);
//        DeathStatisticsImperative.printTotalDeathsForYear_All_All(records, 2024);
//        DeathStatisticsImperative.printTotalDeathsForYears_All_All(records, 2024, 2025);

        /*String[] ageGroups = {
                "0-4", "5-9", "10-14", "15-19", "20-24",
                "25-29", "30-34", "35-39", "40-44",
                "45-49", "50-54", "55-59", "60-64",
                "65-69", "70-74", "75-79", "80-84",
                "85-89", "90-94", "95-99", "100+"
        };*/

//        String[] ageGroups = { "65-69", "70-74", "75-79" };

//        DeathStatisticsImperative.compareAgeGroupsAcrossYears_AllGender(records, 2018, 2022, ageGroups);
//        DeathStatisticsImperative.compareAgeGroupsForTwoYears_AllGender(records, 2019, 2023, ageGroups);
    }
}