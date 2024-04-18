package source;

import org.apache.jena.query.*;

import java.sql.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JDBCClient {
    private static final String HOST = "127.0.0.1";
    private static final String BASE = "movies_budgets";
    private static final String USER = "omar";
//    private static final String USER = "root";
    private static final String PASSWORD = "Omar2002--";
//    private static final String PASSWORD = "";

    private Connection connection;

    public JDBCClient(){
        // Create a connection
        createConnection();

    }

    private void createConnection(){
        Connection connection = null;
        // Construire l'URL de connexion JDBC
        String url = "jdbc:mysql://" + HOST + "/" + BASE;
        try {
            // Établir la connexion à la base de données
            connection = DriverManager.getConnection(url, USER, PASSWORD);
            System.out.println("Connexion réussie à la base de données!");
        } catch (SQLException e) {
            // Gérer les erreurs liées à la connexion
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }

        this.connection = connection;
    }

    private static Statement createStatement(Connection connection){
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Error creating statement");
            e.printStackTrace();
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return statement;
    }

        public ArrayList<ArrayList<Object>> getMovieInfo(String search){
        Statement statement = createStatement(this.connection);

        String query = "SELECT * FROM film" +
                " WHERE film.title LIKE \"%" + search +"%\" ";

        ArrayList<ArrayList<Object>> filmsTable = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Récupérer les données par nom de colonne
                String title = resultSet.getString("title");
                Date releaseDate = resultSet.getDate("release_date");
                String genre = resultSet.getString("genre");
                String distributor = resultSet.getString("distributor");
                double budget = resultSet.getDouble("budget");
                double usaRevenue = resultSet.getDouble("usa_revenue");
                double worldwideRevenue = resultSet.getDouble("worldwide_revenue");
                ArrayList<Object> filmTable = new ArrayList<>(6);
                filmTable.add(title);
                filmTable.add(releaseDate);
                filmTable.add(genre);
                filmTable.add(distributor);
                filmTable.add(budget);
                filmTable.add(usaRevenue);
                filmTable.add(worldwideRevenue);

                filmsTable.add(filmTable);

                // Afficher les résultats
//                System.out.println("Titre: " + title + "\nDate de sortie: " + releaseDate
//                        + "\nDistributeur: " + distributor + "\nBudget: " + String.format("%.0f", budget)
//                        + "\nRevenus USA: " + String.format("%.0f", usaRevenue) + " $\nRevenus mondiaux: "
//                        + String.format("%.0f", worldwideRevenue) + " $");
            }
        } catch (SQLException e) {
            System.out.println("Error executing query");
            e.printStackTrace();
        }

        return filmsTable;

    }

    public String getMovie(String movieTitle, String year, boolean caseSensitive) {
        Statement statement = createStatement(this.connection);

        String query = "SELECT * FROM film" +
                " WHERE film.title LIKE \"%" + movieTitle +"%\" ";

        ArrayList<ArrayList<Object>> filmsTable = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Récupérer les données par nom de colonne
                String title = resultSet.getString("title");
                Date releaseDate = resultSet.getDate("release_date");
                String genre = resultSet.getString("genre");
                String distributor = resultSet.getString("distributor");
                double budget = resultSet.getDouble("budget");
                double usaRevenue = resultSet.getDouble("usa_revenue");
                double worldwideRevenue = resultSet.getDouble("worldwide_revenue");
                ArrayList<Object> filmTable = new ArrayList<>(6);
                filmTable.add(title);
                filmTable.add(releaseDate);
                filmTable.add(genre);
                filmTable.add(distributor);
                filmTable.add(budget);
                filmTable.add(usaRevenue);
                filmTable.add(worldwideRevenue);

                filmsTable.add(filmTable);

                // Afficher les résultats
//                System.out.println("Titre: " + title + "\nDate de sortie: " + releaseDate
//                        + "\nDistributeur: " + distributor + "\nBudget: " + String.format("%.0f", budget)
//                        + "\nRevenus USA: " + String.format("%.0f", usaRevenue) + " $\nRevenus mondiaux: "
//                        + String.format("%.0f", worldwideRevenue) + " $");
            }
        } catch (SQLException e) {
            System.out.println("Error executing query");
            e.printStackTrace();
        }

        // Étape de vérification

        // Afficher les détails du film à la liste
        for(ArrayList<Object> filmInfo : filmsTable){
//            System.out.println(entry);
//                System.out.println("La première année trouvée est : " + matcher.group());
            String releaseYear = "" + (((Date)filmInfo.get(1)).getYear() + 1900) + "";
            if (releaseYear.equals(year)){
                return (String) filmInfo.get(0);
            }

        }
        return null;
    }

}
