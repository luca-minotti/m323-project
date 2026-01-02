package src.ch.bbzbl.m323;

import java.util.List;

public class DeathStatisticsImperative {

    public static void topThreeMonths(List<DeathRecord> records, int year) {
        int[] monthTotals = new int[12];

        for (DeathRecord r : records) {
            if (r.getYear() != year) {
                continue;
            }

            if (!r.getGender().equalsIgnoreCase("Alle")) {
                continue;
            }

            if (!r.getAgeGroup().equalsIgnoreCase("Alle")) {
                continue;
            }

            monthTotals[r.getMonth() - 1] = r.getDeaths();
        }

        System.out.println("Top 3 Monate – Jahr " + year);
        System.out.println("------------------------------------");
        System.out.printf("%-10s | %-10s%n", "Monat", "Todesfälle");
        System.out.println("------------------------------------");

        for (int i = 0; i < 3; i++) {
            int max = -1;
            int maxMonth = -1;

            for (int m = 0; m < 12; m++) {
                if (monthTotals[m] > max) {
                    max = monthTotals[m];
                    maxMonth = m;
                }
            }

            if (maxMonth != -1) {
                System.out.printf(
                        "%-10d | %-10d%n",
                        maxMonth + 1,
                        max
                );

                monthTotals[maxMonth] = -1;
            }
        }
    }

    public static void compareGenders(List<DeathRecord> records, int year) {
        int male = 0;
        int female = 0;

        for (DeathRecord r : records) {
            if (r.getYear() == year) {
                if (r.getAgeGroup().equalsIgnoreCase("Alle")) {
                    continue;
                }

                if (r.getGender().equalsIgnoreCase("Männer")) {
                    male += r.getDeaths();
                } else if (r.getGender().equalsIgnoreCase("Frauen")) {
                    female += r.getDeaths();
                }
            }
        }

        System.out.println("Geschlechtervergleich – Jahr " + year);
        System.out.println("----------------------------------------");
        System.out.printf("%-12s | %-10s%n", "Geschlecht", "Todesfälle");
        System.out.println("----------------------------------------");
        System.out.printf("%-12s | %-10d%n", "Männer", male);
        System.out.printf("%-12s | %-10d%n", "Frauen", female);
    }

    public static void allAgeGroups(List<DeathRecord> records, int year) {
        String[] ageGroups = {
                "0-4", "5-9", "10-14", "15-19", "20-24",
                "25-29", "30-34", "35-39", "40-44",
                "45-49", "50-54", "55-59", "60-64",
                "65-69", "70-74", "75-79", "80-84",
                "85-89", "90-94", "95-99", "100+"
        };

        int[] totals = new int[ageGroups.length];

        for (DeathRecord r : records) {
            if (r.getYear() != year) {
                continue;
            }

            if (!r.getGender().equalsIgnoreCase("Alle")) {
                continue;
            }

            if (r.getAgeGroup().equalsIgnoreCase("Alle")) {
                continue;
            }

            for (int i = 0; i < ageGroups.length; i++) {
                if (r.getAgeGroup().equals(ageGroups[i])) {
                    totals[i] += r.getDeaths();
                    break;
                }
            }
        }

        System.out.println("Altersgruppen – Gesamtjahr " + year);
        System.out.println("------------------------------------------");
        System.out.printf("%-12s | %-10s%n", "Altersgruppe", "Todesfälle");
        System.out.println("------------------------------------------");

        for (int i = 0; i < ageGroups.length; i++) {
            System.out.printf(
                    "%-12s | %-10d%n",
                    ageGroups[i],
                    totals[i]
            );
        }
    }

    public static void totalDeathsOneYear(List<DeathRecord> records, int year) {
        int total = 0;

        for (DeathRecord r : records) {
            if (r.getYear() != year) {
                continue;
            }

            if (!r.getGender().equalsIgnoreCase("Alle")) {
                continue;
            }

            if (!r.getAgeGroup().equalsIgnoreCase("Alle")) {
                continue;
            }

            total += r.getDeaths();
        }

        System.out.println("Gesamtzahl Todesfälle – Jahr " + year);
        System.out.println("------------------------------------");
        System.out.printf("Total: %d%n", total);
    }

    public static void totalDeathsMultipleYears(List<DeathRecord> records, int fromYear, int toYear) {
        int total = 0;

        for (DeathRecord r : records) {
            if (r.getYear() < fromYear || r.getYear() > toYear) {
                continue;
            }

            if (!r.getGender().equalsIgnoreCase("Alle")) {
                continue;
            }

            if (!r.getAgeGroup().equalsIgnoreCase("Alle")) {
                continue;
            }

            total += r.getDeaths();
        }

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

        for (int year = fromYear; year <= toYear; year++) {
            int[] totals = new int[ageGroupsToCompare.length];

            for (DeathRecord r : records) {
                if (r.getYear() != year) {
                    continue;
                }

                if (!r.getGender().equalsIgnoreCase("Alle")) {
                    continue;
                }

                if (r.getAgeGroup().equalsIgnoreCase("Alle")) {
                    continue;
                }

                for (int i = 0; i < ageGroupsToCompare.length; i++) {
                    if (r.getAgeGroup().equals(ageGroupsToCompare[i])) {
                        totals[i] += r.getDeaths();
                        break;
                    }
                }
            }

            System.out.printf("%-6d", year);
            for (int value : totals) {
                System.out.printf("| %-8d ", value);
            }
            System.out.println();
        }
    }

    public static void compareAgeGroupTwoYears(
            List<DeathRecord> records,
            int year1,
            int year2,
            String[] ageGroupsToCompare
    ) {
        int[] totalsYear1 = new int[ageGroupsToCompare.length];
        int[] totalsYear2 = new int[ageGroupsToCompare.length];

        for (DeathRecord r : records) {
            if (!r.getGender().equalsIgnoreCase("Alle")) {
                continue;
            }

            if (r.getAgeGroup().equalsIgnoreCase("Alle")) {
                continue;
            }

            if (r.getYear() == year1) {
                for (int i = 0; i < ageGroupsToCompare.length; i++) {
                    if (r.getAgeGroup().equals(ageGroupsToCompare[i])) {
                        totalsYear1[i] += r.getDeaths();
                        break;
                    }
                }
            }

            if (r.getYear() == year2) {
                for (int i = 0; i < ageGroupsToCompare.length; i++) {
                    if (r.getAgeGroup().equals(ageGroupsToCompare[i])) {
                        totalsYear2[i] += r.getDeaths();
                        break;
                    }
                }
            }
        }

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
        for (int v : totalsYear1) {
            System.out.printf("| %-8d ", v);
        }
        System.out.println();

        System.out.printf("%-6d", year2);
        for (int v : totalsYear2) {
            System.out.printf("| %-8d ", v);
        }
        System.out.println();
    }
}
