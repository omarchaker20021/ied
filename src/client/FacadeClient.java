package client;
import source.JDBCClient;
import source.Mediator;
import data.Movie;

import java.util.ArrayList;

public class FacadeClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Mediator mediator = Mediator.getInstance();

		String search = "Raise the Titanic";
		boolean caseSensitive = true;

		ArrayList<Movie> movies = mediator.getMoviesByMovieTitle(search, caseSensitive);

		for (Movie movie : movies){
			System.out.println(movie);
			System.out.println("");
			System.out.println("--------------------------------------------------------------------------------------------");
			System.out.println("");
		}


	}

}
