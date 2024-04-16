package data;

import java.sql.Date;
import java.util.ArrayList;

public class Movie {

    private String title;

    private Date releaseDate;

    private String genre;

    private String distributor;

    private double budget;

    private double usaRevenue;

    private double worldwideRevenue;

    private ArrayList<String> directors = new ArrayList<String>();

    private ArrayList<String> actors = new ArrayList<String>();

    private ArrayList<String> producers = new ArrayList<String>();

    private String summary;

    public Movie(){
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getUsaRevenue() {
        return usaRevenue;
    }

    public void setUsaRevenue(double usaRevenue) {
        this.usaRevenue = usaRevenue;
    }

    public double getWorldwideRevenue() {
        return worldwideRevenue;
    }

    public void setWorldwideRevenue(double worldwideRevenue) {
        this.worldwideRevenue = worldwideRevenue;
    }

    public ArrayList<String> getDirectors() {
        return directors;
    }

    public void setDirectors(ArrayList<String> directors) {
        this.directors = directors;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getProducers() {
        return producers;
    }

    public void setProducers(ArrayList<String> producers) {
        this.producers = producers;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return String.format(
                        "+----------------------------------------+----------------------------------------------------+\n" +
                        "| Title                                  | %50s |\n" +
                        "+----------------------------------------+----------------------------------------------------+\n" +
                        "| Release Date                           | %50s |\n" +
                        "+----------------------------------------+----------------------------------------------------+\n" +
                        "| Genre                                  | %50s |\n" +
                        "+----------------------------------------+----------------------------------------------------+\n" +
                        "| Distributor                            | %50s |\n" +
                        "+----------------------------------------+----------------------------------------------------+\n" +
                        "| Budget                                 | %50.2f |\n" +
                        "+----------------------------------------+----------------------------------------------------+\n" +
                        "| USA Revenue                            | %50.2f |\n" +
                        "+----------------------------------------+----------------------------------------------------+\n" +
                        "| Worldwide Revenue                      | %50.2f |\n" +
                        "+----------------------------------------+----------------------------------------------------+\n" +
                        "| Director                               | %50s |\n" +
                        "+----------------------------------------+----------------------------------------------------+\n" +
                        "| Actor                                  | %50s |\n" +
                        "+----------------------------------------+----------------------------------------------------+\n" +
                        "| Producer                               | %50s |\n" +
                        "+----------------------------------------+----------------------------------------------------+\n" +
                        "| Summary                                | %50s |\n" +
                        "+----------------------------------------+----------------------------------------------------+",
                title, releaseDate, genre, distributor, budget, usaRevenue, worldwideRevenue,
                String.join(", ", directors), String.join(", ", actors),
                String.join(", ", producers), summary);
    }

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(String.format("%-15s: %s\n", "Title", title));
//        sb.append(String.format("%-15s: %s\n", "Release Date", releaseDate));
//        sb.append(String.format("%-15s: %s\n", "Genre", genre));
//        sb.append(String.format("%-15s: %s\n", "Distributor", distributor));
//        sb.append(String.format("%-15s: $%.2f\n", "Budget", budget));
//        sb.append(String.format("%-15s: $%.2f\n", "US Revenue", usaRevenue));
//        sb.append(String.format("%-15s: $%.2f\n", "Worldwide Revenue", worldwideRevenue));
//        sb.append(String.format("%-15s: %s\n", "Directors", String.join(", ", directors)));
//        sb.append(String.format("%-15s: %s\n", "Actors", String.join(", ", actors)));
//        sb.append(String.format("%-15s: %s\n", "Producers", String.join(", ", producers)));
//        sb.append(String.format("%-15s: %s\n", "Summary", summary));
//        return sb.toString();
//    }

}
