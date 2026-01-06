package src.ch.bbzbl.m323;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * DeathStatisticsFunctional
 *
 * Berechnet unterschiedliche Statistiken zu Todesfällen aus einem öffentlichen Datensatz (https://data.bs.ch/explore/dataset/100173)
 * mit funktionaler Implementation.
 *
 * @author Furkan Güner & Luca Minotti
 * @version 2.0
 */
public class DeathStatisticsFunctional {

    private static String monthLabel(int month) {
        return String.valueOf(month);
    }

    /**
     * Gibt die Top 3 Monate mit den meisten Todesfällen für ein bestimmtes Jahr aus.
     * Es werden alle Altersgruppen und Geschlechter berücksichtigt.
     *
     * @param records - Liste aller DeathRecords
     * @param year - Jahr, wofür die Ausgabe gemacht werden soll
     */
    public static void topThreeMonths(List<DeathRecord> records, int year) {
        Map<Integer, Integer> monthToDeaths = records.stream()
                .filter(r -> r.getYear() == year)
                .filter(r -> r.getGender().equalsIgnoreCase("Alle"))
                .filter(r -> r.getAgeGroup().equalsIgnoreCase("Alle"))
                .collect(Collectors.toMap(
                        DeathRecord::getMonth,
                        DeathRecord::getDeaths,
                        (a, b) -> a
                ));

        System.out.println("Top 3 Monate – Jahr " + year + " (Funktional)");
        System.out.println("------------------------------------");
        System.out.printf("%-10s | %-10s%n", "Monat", "Todesfälle");
        System.out.println("------------------------------------");

        monthToDeaths.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue())) // absteigend
                .limit(3)
                .forEach(e -> System.out.printf("%-10s | %-10d%n", monthLabel(e.getKey()), e.getValue()));
    }

    /**
     * Vergleicht die Todesfälle von Männern und Frauen für ein bestimmtes Jahr.
     * Es werden alle Altersgruppen berücksichtigt.
     *
     * @param records - Liste aller DeathRecords
     * @param year - Jahr, wofür die Ausgabe gemacht werden soll
     */
    public static void compareGenders(List<DeathRecord> records, int year) {
        Map<String, Integer> totals = records.stream()
                .filter(r -> r.getYear() == year)
                .filter(r -> !r.getAgeGroup().equalsIgnoreCase("Alle"))
                .filter(r -> r.getGender().equalsIgnoreCase("Männer") || r.getGender().equalsIgnoreCase("Frauen"))
                .collect(Collectors.groupingBy(
                        r -> r.getGender().toLowerCase(),
                        Collectors.summingInt(DeathRecord::getDeaths)
                ));

        int male = totals.getOrDefault("männer", 0);
        int female = totals.getOrDefault("frauen", 0);

        System.out.println("Geschlechtervergleich – Jahr " + year + " (Funktional)");
        System.out.println("----------------------------------------");
        System.out.printf("%-12s | %-10s%n", "Geschlecht", "Todesfälle");
        System.out.println("----------------------------------------");
        System.out.printf("%-12s | %-10d%n", "Männer", male);
        System.out.printf("%-12s | %-10d%n", "Frauen", female);
    }

    /**
     * Gibt die Todesfälle für bestimmte Altersgruppen für ein bestimmtes Jahr aus.
     * Es werden alle Geschlechter berücksichtigt.
     *
     * @param records - Liste aller DeathRecords
     * @param year - Jahr, wofür die Ausgabe gemacht werden soll
     */
    public static void allAgeGroups(List<DeathRecord> records, int year) {
        List<String> ageGroups = List.of(
                "0-4", "5-9", "10-14", "15-19", "20-24",
                "25-29", "30-34", "35-39", "40-44",
                "45-49", "50-54", "55-59", "60-64",
                "65-69", "70-74", "75-79", "80-84",
                "85-89", "90-94", "95-99", "100+"
        );

        Map<String, Integer> ageGroupTotals = records.stream()
                .filter(r -> r.getYear() == year)
                .filter(r -> r.getGender().equalsIgnoreCase("Alle"))
                .filter(r -> !r.getAgeGroup().equalsIgnoreCase("Alle"))
                .collect(Collectors.groupingBy(
                        DeathRecord::getAgeGroup,
                        Collectors.summingInt(DeathRecord::getDeaths)
                ));

        System.out.println("Altersgruppen – Gesamtjahr " + year + " (Funktional)");
        System.out.println("------------------------------------------");
        System.out.printf("%-12s | %-10s%n", "Altersgruppe", "Todesfälle");
        System.out.println("------------------------------------------");

        ageGroups.forEach(ag ->
                System.out.printf("%-12s | %-10d%n", ag, ageGroupTotals.getOrDefault(ag, 0))
        );
    }

    /**
     * Gibt die Gesamtzahl der Todesfälle für ein bestimmtes Jahr aus.
     * Es werden alle Geschlechter und Altersgruppen berücksichtigt.
     *
     * @param records - Liste aller DeathRecords
     * @param year - Jahr, wofür die Ausgabe gemacht werden soll
     */
    public static void totalDeathsOneYear(List<DeathRecord> records, int year) {
        int total = records.stream()
                .filter(r -> r.getYear() == year)
                .filter(r -> r.getGender().equalsIgnoreCase("Alle"))
                .filter(r -> r.getAgeGroup().equalsIgnoreCase("Alle"))
                .mapToInt(DeathRecord::getDeaths)
                .sum();

        System.out.println("Gesamtzahl Todesfälle – Jahr " + year + " (Funktional)");
        System.out.println("------------------------------------");
        System.out.printf("Total: %d%n", total);
    }


    /**
     * Gibt die Gesamtzahl der Todesfälle über mehrere Jahre aus.
     * Es werden alle Geschlechter und Altersgruppen berücksichtigt.
     *
     * @param records - Liste aller DeathRecords
     * @param fromYear - Startjahr der Auswertung
     * @param toYear - Endjahr der Auswertung
     */
    public static void totalDeathsMultipleYears(List<DeathRecord> records, int fromYear, int toYear) {
        int total = records.stream()
                .filter(r -> r.getYear() >= fromYear && r.getYear() <= toYear)
                .filter(r -> r.getGender().equalsIgnoreCase("Alle"))
                .filter(r -> r.getAgeGroup().equalsIgnoreCase("Alle"))
                .mapToInt(DeathRecord::getDeaths)
                .sum();

        System.out.println("Gesamtzahl Todesfälle – Jahre " + fromYear + " bis " + toYear + " (Funktional)");
        System.out.println("-------------------------------------------");
        System.out.printf("Total: %d%n", total);
    }

    /**
     * Vergleicht verschiedene Altersgruppen über mehrere Jahre.
     * Es werden alle Geschlechter berücksichtigt.
     *
     * @param records - Liste aller DeathRecords
     * @param fromYear - Startjahr der Auswertung
     * @param toYear - Endjahr der Auswertung
     * @param ageGroupsToCompare - Altersgruppen, die verglichen werden sollen
     */
    public static void compareAgeGroupsMultipleYears(
            List<DeathRecord> records,
            int fromYear,
            int toYear,
            String[] ageGroupsToCompare
    ) {
        System.out.println("Vergleich Altersgruppen über Jahre " + fromYear + " bis " + toYear + " (Funktional)");
        System.out.println("----------------------------------------------");

        System.out.printf("%-6s", "Jahr");
        for (String ageGroup : ageGroupsToCompare) {
            System.out.printf("| %-8s ", ageGroup);
        }
        System.out.println();
        System.out.println("----------------------------------------------");

        IntStream.rangeClosed(fromYear, toYear)
                .forEach(year -> {
                    Map<String, Integer> totalsForYear = records.stream()
                            .filter(r -> r.getYear() == year)
                            .filter(r -> r.getGender().equalsIgnoreCase("Alle"))
                            .filter(r -> !r.getAgeGroup().equalsIgnoreCase("Alle"))
                            .filter(r -> Arrays.asList(ageGroupsToCompare).contains(r.getAgeGroup()))
                            .collect(Collectors.groupingBy(
                                    DeathRecord::getAgeGroup,
                                    Collectors.summingInt(DeathRecord::getDeaths)
                            ));

                    System.out.printf("%-6d", year);
                    for (String ag : ageGroupsToCompare) {
                        System.out.printf("| %-8d ", totalsForYear.getOrDefault(ag, 0));
                    }
                    System.out.println();
                });
    }

    /**
     * Vergleicht verschiedene Altersgruppen für exakt zwei Jahre.
     * Es werden alle Geschlechter berücksichtigt.
     *
     * @param records - Liste aller DeathRecords
     * @param year1 - Erstes Jahr für den Vergleich
     * @param year2 - Zweites Jahr für den Vergleich
     * @param ageGroupsToCompare - Altersgruppen, die verglichen werden sollen
     */
    public static void compareAgeGroupTwoYears(
            List<DeathRecord> records,
            int year1,
            int year2,
            String[] ageGroupsToCompare
    ) {
        Map<String, Integer> totalsYear1 = totalsForAgeGroupsInYear(records, year1, ageGroupsToCompare);
        Map<String, Integer> totalsYear2 = totalsForAgeGroupsInYear(records, year2, ageGroupsToCompare);

        System.out.println("Vergleich Altersgruppen " + year1 + " zu " + year2 + " (Funktional)");
        System.out.println("----------------------------------------------");

        System.out.printf("%-6s", "Jahr");
        for (String ageGroup : ageGroupsToCompare) {
            System.out.printf("| %-8s ", ageGroup);
        }
        System.out.println();
        System.out.println("----------------------------------------------");

        System.out.printf("%-6d", year1);
        for (String ag : ageGroupsToCompare) {
            System.out.printf("| %-8d ", totalsYear1.getOrDefault(ag, 0));
        }
        System.out.println();

        System.out.printf("%-6d", year2);
        for (String ag : ageGroupsToCompare) {
            System.out.printf("| %-8d ", totalsYear2.getOrDefault(ag, 0));
        }
        System.out.println();
    }

    private static Map<String, Integer> totalsForAgeGroupsInYear(
            List<DeathRecord> records,
            int year,
            String[] ageGroupsToCompare
    ) {
        List<String> wanted = Arrays.asList(ageGroupsToCompare);

        return records.stream()
                .filter(r -> r.getYear() == year)
                .filter(r -> r.getGender().equalsIgnoreCase("Alle"))
                .filter(r -> !r.getAgeGroup().equalsIgnoreCase("Alle"))
                .filter(r -> wanted.contains(r.getAgeGroup()))
                .collect(Collectors.groupingBy(
                        DeathRecord::getAgeGroup,
                        Collectors.summingInt(DeathRecord::getDeaths)
                ));
    }
}
