package source;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;

import java.util.ArrayList;

public class DBpediaClient {

    public static ArrayList<ArrayList<String>> getMoviesDetails(String movieTitle, boolean caseSensitive) {
        ArrayList<ArrayList<String>> moviesDetails = new ArrayList<>(3);

        // Requête SPARQL pour récupérer les réalisateurs du film
        String queryStringDirectors = "PREFIX dbo: <http://dbpedia.org/ontology/>\n"
                + "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "PREFIX dbp: <http://dbpedia.org/property/>\n"
                + "SELECT DISTINCT ?value WHERE {\n"
                + "  ?film a dbo:Film ;\n";
        if (caseSensitive){
            queryStringDirectors +="    foaf:name ?filmName.\n"
                    + "FILTER (LCASE(str(?filmName)) = \"" + movieTitle.toLowerCase() + "\")\n";
        }
        else {
            queryStringDirectors +="    foaf:name \"" + movieTitle + "\"@en.\n";
        }

        queryStringDirectors +="  ?film dbo:director|dbp:director ?d .\n"
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
        if (caseSensitive){
            queryStringActors +="    foaf:name ?filmName.\n"
                    + "FILTER (LCASE(str(?filmName)) = \"" + movieTitle.toLowerCase() + "\")\n";
        }
        else {
            queryStringActors +="    foaf:name \"" + movieTitle + "\"@en.\n";
        }
        queryStringActors+="  ?film dbo:starring|dbp:starring ?a .\n"
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
        if (caseSensitive){
            queryStringProducers +="    foaf:name ?filmName.\n"
                    + "FILTER (LCASE(str(?filmName)) = \"" + movieTitle.toLowerCase() + "\")\n";
        }
        else {
            queryStringProducers +="    foaf:name \"" + movieTitle + "\"@en.\n";
        }
        queryStringProducers+= "{"
                + "    ?film dbp:producers|dbo:producers|dbp:producer|dbo:producer ?producerName .\n"
                + "} UNION {"
                + "    ?film dbp:producers|dbo:producers|dbp:producer|dbo:producer ?p.\n"
                + "    ?p foaf:name|rdfs:label|dbp:name ?producerName .\n"
                + "}"
                + "  FILTER (lang(?producerName) = 'en')\n"
                + "BIND (STR(?producerName) AS ?value)\n"
                + "}";

//        System.out.println(queryStringActors);
//        System.out.println(queryStringDirectors);
        System.out.println(queryStringProducers);

        // Exécuter les requêtes SPARQL et récupérer les résultats
        ArrayList<String> directors = executeQueryAndGetResults(queryStringDirectors);
        ArrayList<String> actors = executeQueryAndGetResults(queryStringActors);
        ArrayList<String> producers = executeQueryAndGetResults(queryStringProducers);
        // Ajouter les détails du film à la liste
        moviesDetails.add(actors);
        moviesDetails.add(directors);
        moviesDetails.add(producers);

        return moviesDetails;
    }
    public static ArrayList<String> getMoviesByActor(String actorName, boolean caseSensitive) {

        // Construire la requête SPARQL pour récupérer les films selon l'acteur
        String queryStringActorMovies = "PREFIX dbo: <http://dbpedia.org/ontology/>\n"
        		+ "PREFIX dbp: <http://dbpedia.org/property/>\n"
                + "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "SELECT DISTINCT ?value WHERE {\n"
                + "  ?film a dbo:Film .\n"
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
//                + "?film rdfs:label ?filmTitle .\n"
				+ "?film foaf:name ?filmTitle .\n"
                + "  FILTER (lang(?filmTitle) = 'en')\n"
                + "BIND (STR(?filmTitle) AS ?value)\n"
                + "}";
        System.out.println(queryStringActorMovies);
        // Exécuter la requête SPARQL et récupérer les résultats
        ArrayList<String> actorMovies = executeQueryAndGetResults(queryStringActorMovies);
        
        return actorMovies;
    }


    // Méthode pour exécuter une requête SPARQL et retourner les résultats sous forme de liste de chaînes de caractères
    private static ArrayList<String> executeQueryAndGetResults(String queryString) {
        ArrayList<String> resultsList = new ArrayList<>();

        // Créer un objet Query
        Query query = QueryFactory.create(queryString);

        // Créer un objet QueryExecution pour exécuter la requête SPARQL sur DBpedia
        try (QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query)) {
            // Exécuter la requête et obtenir le ResultSet
            ResultSet results = qexec.execSelect();

            // Parcourir les résultats et ajouter les valeurs à la liste
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                String value = soln.get("?value").toString().split("\\(")[0].trim();
                if (value != null && !value.equals("") && !resultsList.contains(value)) {
                    resultsList.add(value);
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
