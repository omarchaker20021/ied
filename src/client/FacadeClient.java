package client;
import source.JDBCClient;
import source.Mediator;
import data.Movie;

import java.util.ArrayList;

public class FacadeClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Mediator mediator = Mediator.getInstance();

		String searchMovie = "Avatar";
		String searchActor = "Matt Lattanzi";
		boolean caseSensitive = true;

//		ArrayList<Movie> moviesByTitle = mediator.getMoviesByMovieTitle(searchMovie, caseSensitive);
//		ArrayList<Movie> moviesByActor = mediator.getMoviesByActorName(searchActor, caseSensitive);
		ArrayList<Movie> moviesByActor = mediator.getMoviesByActorName(searchActor, caseSensitive);

//		System.out.println("Movies by title:");
//		for (Movie movie : moviesByTitle){
//			System.out.println(movie);
//			System.out.println("");
//			System.out.println("--------------------------------------------------------------------------------------------");
//			System.out.println("");
//		}

		System.out.println("Movies by actor:");
		for (Movie movie : moviesByActor){
			System.out.println(movie);
			System.out.println("");
			System.out.println("--------------------------------------------------------------------------------------------");
			System.out.println("");
		}

	}

}
