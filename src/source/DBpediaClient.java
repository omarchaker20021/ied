package source;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBpediaClient {

    public static ArrayList<ArrayList<Object>> getMoviesDetails(String movieLabel/*, boolean caseSensitive*/) {
        ArrayList<ArrayList<Object>> moviesDetails = new ArrayList<>(3);

        // Requête SPARQL pour récupérer les réalisateurs du film
        String queryStringDirectors = "PREFIX dbo: <http://dbpedia.org/ontology/>\n"
                + "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "PREFIX dbp: <http://dbpedia.org/property/>\n"
                + "SELECT DISTINCT ?value WHERE {\n"
                + "  ?film a dbo:Film ;\n";
//        if (caseSensitive){
//            queryStringDirectors +="    rdfs:label ?filmName.\n"
//                    + "FILTER (LCASE(str(?filmName)) = \"" + movieLabel.toLowerCase() + "\")\n";
//        }
//        else {
//            queryStringDirectors +="    rdfs:label \"" + movieLabel + "\"@en.\n";
//        }


        queryStringDirectors +="    rdfs:label\"" + movieLabel + "\"@en.\n"
                + "  ?film dbo:director|dbp:director ?d .\n"
                + "  ?d foaf:name|rdfs:label|dbp:name ?directorName .\n"
                + "  FILTER (lang(?directorName) = 'en')\n"
                + "BIND (STR(?directorName) AS ?value)\n"
                + "}";




        // Requête SPARQL pour récupérer les acteurs du film
        String queryStringActors = "PREFIX dbo: <http://dbpedia.org/ontology/>\n"
                + "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX dbp: <http://dbpedia.org/property/>\n"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "SELECT DISTINCT ?value WHERE {\n"
                + "  ?film a dbo:Film ;\n";
//        if (caseSensitive){
//            queryStringActors +="    rdfs:label ?filmName.\n"
//                    + "FILTER (LCASE(str(?filmName)) = \"" + movieLabel.toLowerCase() + "\")\n";
//        }
//        else {
//            queryStringActors +="    rdfs:label \"" + movieLabel + "\"@en.\n";
//        }
        queryStringActors+= "    rdfs:label\"" + movieLabel + "\"@en.\n"
                + "  ?film dbo:starring|dbp:starring ?a .\n"
                + "  ?a foaf:name|rdfs:label|dbp:name ?actorName .\n"
                + "  FILTER (lang(?actorName) = 'en')\n"
                + "BIND (STR(?actorName) AS ?value)\n"
                + "}";



        // Requête SPARQL pour récupérer les producteurs du film
        String queryStringProducers = "PREFIX dbo: <http://dbpedia.org/ontology/>\n"
                + "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX dbp: <http://dbpedia.org/property/>\n"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "SELECT DISTINCT ?value WHERE {\n"
                + "  ?film a dbo:Film ;\n";
//        if (caseSensitive){
//            queryStringProducers +="    rdfs:label ?filmName.\n"
//                    + "FILTER (LCASE(str(?filmName)) = \"" + movieLabel.toLowerCase() + "\")\n";
//        }
//        else {
//            queryStringProducers +="    rdfs:label \"" + movieLabel + "\"@en.\n";
//        }
        queryStringProducers+= "    rdfs:label\"" + movieLabel + "\"@en.\n"
                + "{"
                + "    ?film dbp:producers|dbo:producers|dbp:producer|dbo:producer ?producerName .\n"
                + "} UNION {"
                + "    ?film dbp:producers|dbo:producers|dbp:producer|dbo:producer ?p.\n"
                + "    ?p foaf:name|rdfs:label|dbp:name ?producerName .\n"
                + "}"
                + "  FILTER (lang(?producerName) = 'en')\n"
                + "BIND (STR(?producerName) AS ?value)\n"
                + "}";

        System.out.println(queryStringActors);
//        System.out.println(queryStringDirectors);
//        System.out.println(queryStringProducers);

        // Exécuter les requêtes SPARQL et récupérer les résultats
        ArrayList<Object> directors = executeQueryAndGetResults(queryStringDirectors, false);
        ArrayList<Object> actors = executeQueryAndGetResults(queryStringActors, false);
        ArrayList<Object> producers = executeQueryAndGetResults(queryStringProducers, false);
        // Ajouter les détails du film à la liste
        moviesDetails.add(actors);
        moviesDetails.add(directors);
        moviesDetails.add(producers);

        return moviesDetails;
    }
    public static ArrayList<Object> getMoviesByActor(String actorName, boolean caseSensitive, boolean year) {

        // Construire la requête SPARQL pour récupérer les films selon l'acteur
        String queryStringActorMovies = "PREFIX dbo: <http://dbpedia.org/ontology/>\n"
        		+ "PREFIX dbp: <http://dbpedia.org/property/>\n"
                + "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "SELECT DISTINCT ?value ?comment WHERE {\n"
                + "  ?film a dbo:Film ;\n"
                + "    rdfs:comment ?comment .\n"
                + "{\n"
                + "  ?film dbo:starring|dbp:starring ?actor .\n"
                + "} UNION {\n"
                + "  ?film dbo:starring|dbp:starring ?a .\n"
                + "  ?a foaf:name|rdfs:label ?actor.\n"
                + "}\n";
        if (caseSensitive) {
            queryStringActorMovies += "FILTER (LCASE(str(?actor)) = \"" + actorName.toLowerCase() + "\"";
        } else {
            queryStringActorMovies += "FILTER (str(?actor) = \"" + actorName + "\"@en";
        }

        queryStringActorMovies += ")\n"
                + "?film rdfs:label ?filmLabel .\n"
//				+ "?film foaf:name ?filmTitle .\n"
                + "  FILTER (lang(?filmLabel) = 'en')\n"
                + "  FILTER (lang(?comment) = 'en')\n"
                + "BIND (STR(?filmLabel) AS ?value)\n"
                + "}";
        System.out.println(queryStringActorMovies);
        // Exécuter la requête SPARQL et récupérer les résultats
        ArrayList<Object> actorMovies = executeQueryAndGetResults(queryStringActorMovies, true);
        
        return actorMovies;
    }


    public static String getMovie(String movieTitle, String year, boolean caseSensitive) {

        // Requête SPARQL pour récupérer les réalisateurs du film
        String queryStringMovie = "PREFIX dbo: <http://dbpedia.org/ontology/>\n"
                + "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "SELECT DISTINCT ?comment ?labelValue WHERE {\n"
                + "  ?film a dbo:Film ;\n"
                + "    rdfs:comment ?comment ;\n"
                + "    rdfs:label ?label ;\n";

        if (caseSensitive) {
            queryStringMovie += "    foaf:name ?filmName.\n"
                    + "FILTER (LCASE(str(?filmName)) = \"" + movieTitle.toLowerCase() + "\")\n"
                    + "FILTER (CONTAINS(LCASE(?label), \"" + movieTitle.toLowerCase() + "\"))\n";

        } else {
            queryStringMovie += "    foaf:name \"" + movieTitle + "\"@en.\n";
        }

//        if (caseSensitive) {
//            queryStringMovie += "    rdfs:label ?label.\n"
////                    + "FILTER (LCASE(str(?filmName)) = \"" + movieTitle.toLowerCase() + "\")\n"
//                    + "FILTER (CONTAINS(LCASE(?label), \"" + movieTitle.toLowerCase() + "\"))\n";
//
//        } else {
//            queryStringMovie += "    foaf:name \"" + movieTitle + "\"@en.\n";
//        }


        queryStringMovie += "    FILTER (lang(?comment) = 'en') \n"
                + "    FILTER (lang(?label) = 'en') \n"
                + "    BIND (STR(?label) AS ?labelValue)\n"
                + "}";


        System.out.println(queryStringMovie);

        HashMap<String, String> movieSummary = new HashMap<>();

        // Créer un objet Query
        Query query = QueryFactory.create(queryStringMovie);

        // Créer un objet QueryExecution pour exécuter la requête SPARQL sur DBpedia
        try (QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query)) {
            // Exécuter la requête et obtenir le ResultSet
            ResultSet results = qexec.execSelect();

            // Parcourir les résultats et ajouter les valeurs à la liste
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                String label = soln.get("?labelValue").toString();
                String comment = soln.get("?comment").toString();
                if (label != null && !label.equals("")
                        && comment != null && !label.equals("")) {
                    movieSummary.put(label, comment);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Afficher les détails du film à la liste
        for (Map.Entry<String, String> entry : movieSummary.entrySet()) {
//            System.out.println(entry);

            // Création du motif pour matcher une année
            Pattern pattern = Pattern.compile("\\b\\d{4}\\b");
            Matcher matcher = pattern.matcher(entry.getValue());

            // Vérification de la présence d'une année dans le texte
            if (matcher.find()) {
//                System.out.println("La première année trouvée est : " + matcher.group());
                if (matcher.group().equals(year)) {
                    return entry.getKey();
                }
            } else {
                System.out.println("Aucune année trouvée dans le texte.");
            }

        }
        return null;
    }

    // Méthode pour exécuter une requête SPARQL et retourner les résultats sous forme de liste de chaînes de caractères
    private static ArrayList<Object> executeQueryAndGetResults(String queryString, boolean year) {
        ArrayList<Object> resultsList = new ArrayList<>();

        // Créer un objet Query
        Query query = QueryFactory.create(queryString);

        // Créer un objet QueryExecution pour exécuter la requête SPARQL sur DBpedia
        try (QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query)) {
            // Exécuter la requête et obtenir le ResultSet
            ResultSet results = qexec.execSelect();

            // Parcourir les résultats et ajouter les valeurs à la liste
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                String value = soln.get("?value").toString();
                if (!year) {
                    if (value != null && !value.equals("") && !resultsList.contains(value)) {
                        resultsList.add(value);
                    }
                }
                else {
                    String comment = soln.get("?comment").toString();
                    if (value != null && !value.equals("")
                            && comment != null && !comment.equals("")) {
                        // Ici on extrait l'annnéeeeeeeeeeeeeeeeeeeeeee
                        String valueYear = "";
                        // Création du motif pour matcher une année
                        Pattern pattern = Pattern.compile("\\b\\d{4}\\b");
                        Matcher matcher = pattern.matcher(comment);

                        // Vérification de la présence d'une année dans le texte
                        if (matcher.find()) {
                            valueYear = matcher.group();
                        } else {
                            System.out.println("Aucune année trouvée dans le texte.");
                        }

                        resultsList.add(new String[]{value, valueYear});
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultsList;
    }

	public static ArrayList<String> getActorMovies(String actorName, boolean caseSensitive) {
		// TODO Auto-generated method stub
		return null;
	}


}
