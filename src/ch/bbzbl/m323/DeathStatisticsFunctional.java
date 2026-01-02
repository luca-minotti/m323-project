package src.ch.bbzbl.m323;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DeathStatisticsFunctional {

    // kleine Hilfsmethode: schöner Monatsname optional
    private static String monthLabel(int month) {
        return String.valueOf(month); // oder z.B. "01", "02" ...
    }

    public static void topThreeMonths(List<DeathRecord> records, int year) {

        // 1) Nur die "Alle/Alle"-Zeilen für das Jahr
        // 2) Pro Monat -> deaths
        Map<Integer, Integer> monthToDeaths = records.stream()
                .filter(r -> r.getYear() == year)
                .filter(r -> r.getGender().equalsIgnoreCase("Alle"))
                .filter(r -> r.getAgeGroup().equalsIgnoreCase("Alle"))
                .collect(Collectors.toMap(
                        DeathRecord::getMonth,
                        DeathRecord::getDeaths,
                        (a, b) -> a // falls doppelt: nimm erstes
                ));

        // Top 3 ausgeben
        System.out.println("Top 3 Monate – Jahr " + year);
        System.out.println("------------------------------------");
        System.out.printf("%-10s | %-10s%n", "Monat", "Todesfälle");
        System.out.println("------------------------------------");

        monthToDeaths.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue())) // absteigend
                .limit(3)
                .forEach(e -> System.out.printf("%-10s | %-10d%n", monthLabel(e.getKey()), e.getValue()));
    }

    public static void compareGenders(List<DeathRecord> records, int year) {

        // Hier bleibt dein ursprüngliches Verhalten: Männer/Frauen summieren (nicht "Alle")
        // und Altersgruppe != Alle, damit es nicht doppelt zählt.
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

        System.out.println("Geschlechtervergleich – Jahr " + year);
        System.out.println("----------------------------------------");
        System.out.printf("%-12s | %-10s%n", "Geschlecht", "Todesfälle");
        System.out.println("----------------------------------------");
        System.out.printf("%-12s | %-10d%n", "Männer", male);
        System.out.printf("%-12s | %-10d%n", "Frauen", female);
    }

    public static void allAgeGroups(List<DeathRecord> records, int year) {

        // gleiche Reihenfolge wie vorher
        List<String> ageGroups = List.of(
                "0-4", "5-9", "10-14", "15-19", "20-24",
                "25-29", "30-34", "35-39", "40-44",
                "45-49", "50-54", "55-59", "60-64",
                "65-69", "70-74", "75-79", "80-84",
                "85-89", "90-94", "95-99", "100+"
        );

        // Nur Geschlecht=Alle, Altersgruppe!=Alle und Jahr passend
        // dann gruppieren nach Altersgruppe und summieren
        Map<String, Integer> ageGroupTotals = records.stream()
                .filter(r -> r.getYear() == year)
                .filter(r -> r.getGender().equalsIgnoreCase("Alle"))
                .filter(r -> !r.getAgeGroup().equalsIgnoreCase("Alle"))
                .collect(Collectors.groupingBy(
                        DeathRecord::getAgeGroup,
                        Collectors.summingInt(DeathRecord::getDeaths)
                ));

        System.out.println("Altersgruppen – Gesamtjahr " + year);
        System.out.println("------------------------------------------");
        System.out.printf("%-12s | %-10s%n", "Altersgruppe", "Todesfälle");
        System.out.println("------------------------------------------");

        // in definierter Reihenfolge ausgeben
        ageGroups.forEach(ag ->
                System.out.printf("%-12s | %-10d%n", ag, ageGroupTotals.getOrDefault(ag, 0))
        );
    }

    public static void totalDeathsOneYear(List<DeathRecord> records, int year) {

        int total = records.stream()
                .filter(r -> r.getYear() == year)
                .filter(r -> r.getGender().equalsIgnoreCase("Alle"))
                .filter(r -> r.getAgeGroup().equalsIgnoreCase("Alle"))
                .mapToInt(DeathRecord::getDeaths)
                .sum();

        System.out.println("Gesamtzahl Todesfälle – Jahr " + year);
        System.out.println("------------------------------------");
        System.out.printf("Total: %d%n", total);
    }

    public static void totalDeathsMultipleYears(List<DeathRecord> records, int fromYear, int toYear) {

        int total = records.stream()
                .filter(r -> r.getYear() >= fromYear && r.getYear() <= toYear)
                .filter(r -> r.getGender().equalsIgnoreCase("Alle"))
                .filter(r -> r.getAgeGroup().equalsIgnoreCase("Alle"))
                .mapToInt(DeathRecord::getDeaths)
                .sum();

        System.out.println("Gesamtzahl Todesfälle – Jahre " + fromYear + " bis " + toYear);
        System.out.println("-------------------------------------------");
        System.out.printf("Total: %d%n", total);
    }

    public static void compareAgeGroupsMultipleYears(
            List<DeathRecord> records,
            int fromYear,
            int toYear,
            String[] ageGroupsToCompare
    ) {

        System.out.println("Vergleich Altersgruppen über Jahre");
        System.out.println("Geschlecht: Alle");
        System.out.println("----------------------------------------------");

        System.out.printf("%-6s", "Jahr");
        for (String ageGroup : ageGroupsToCompare) {
            System.out.printf("| %-8s ", ageGroup);
        }
        System.out.println();
        System.out.println("----------------------------------------------");

        // Für jedes Jahr eine Zeile
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

    public static void compareAgeGroupTwoYears(
            List<DeathRecord> records,
            int year1,
            int year2,
            String[] ageGroupsToCompare
    ) {

        // helper: Map für ein Jahr bauen
        Map<String, Integer> totalsYear1 = totalsForAgeGroupsInYear(records, year1, ageGroupsToCompare);
        Map<String, Integer> totalsYear2 = totalsForAgeGroupsInYear(records, year2, ageGroupsToCompare);

        System.out.println("Vergleich Altersgruppen");
        System.out.println("Geschlecht: Alle");
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
