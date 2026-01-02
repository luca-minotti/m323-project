package src.ch.bbzbl.m323;

public class DeathRecord {

    private int year;
    private int month;
    private String gender;
    private String ageGroup;
    private int deaths;

    public DeathRecord(int year, int month, String gender, String ageGroup, int deaths) {
        this.year = year;
        this.month = month;
        this.gender = gender;
        this.ageGroup = ageGroup;
        this.deaths = deaths;
    }

    public int getYear() { return year; }
    public int getMonth() { return month; }
    public String getGender() { return gender; }
    public String getAgeGroup() { return ageGroup; }
    public int getDeaths() { return deaths; }
}
