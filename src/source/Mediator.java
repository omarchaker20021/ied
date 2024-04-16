package source;

import data.Movie;

import java.sql.Date;
import java.util.ArrayList;

public class Mediator {

    // Eager Singleton
    private static Mediator instance = new Mediator();

    private String search;


    private Mediator(){
    }

    public static Mediator getInstance(){
        return instance;
    }

    public ArrayList<Movie> getMoviesByMovieTitle(String movieTitle, boolean caseSensitive){

        ArrayList<Movie> movies = new ArrayList<>();
        /******************** JDBC Part *****************************************************/
        JDBCClient jdbcClient = new JDBCClient();
		ArrayList<ArrayList<Object>> filmsTable = jdbcClient.getMovieInfo(movieTitle);

        for (ArrayList<Object> filmTable : filmsTable){

            Movie movie = new Movie();
            //		System.out.println("Titre: " + filmTable.get(0) + "\nDate de sortie: " + filmTable.get(1)
            //		+ "\nDistributeur: " + filmTable.get(2) + "\nBudget: " + String.format("%.0f", filmTable.get(3))
            //		+ "\nRevenus USA: " + String.format("%.0f", filmTable.get(4)) + " $\nRevenus mondiaux: "
            //		+ String.format("%.0f", filmTable.get(5)) + " $");
            movie.setTitle((String)filmTable.get(0));
            movie.setReleaseDate((Date)filmTable.get(1));
            movie.setGenre((String)filmTable.get(2));
            movie.setDistributor((String)filmTable.get(3));
            movie.setBudget((double)filmTable.get(4));
            movie.setUsaRevenue((double)filmTable.get(5));
            movie.setWorldwideRevenue((double)filmTable.get(6));

            movies.add(movie);

        }



        /******************** DBpedia Part *****************************************************/
        // DBPedia Part
        // With movies I'm gonna extract actors, directors, producers
        for (Movie movie : movies){
            String filmTitle = movie.getTitle();
            // Case sensitive research or not
            ArrayList<ArrayList<String>> moviesDetails = DBpediaClient.getMoviesDetails(filmTitle, caseSensitive);
            ArrayList<String> actors = moviesDetails.get(0);
            ArrayList<String> directors = moviesDetails.get(1);
            ArrayList<String> producers = moviesDetails.get(2);
            if (actors.size() > 0){
                movie.setActors(actors);
            }
            if (directors.size() > 0){
                movie.setDirectors(directors);
            }
            if (producers.size() > 0){
                movie.setProducers(moviesDetails.get(2));
            }
        }




        /******************** OMDb API Part *****************************************************/
        // OMDb API Part
        for(Movie movie : movies) {
        	String movieTitleFormatted = movie.getTitle().replace(' ', '+');
        	String plot = OMDbClient.getMovieResume(movieTitleFormatted);
        	movie.setSummary(plot);
        }

        return movies;
    }

}
