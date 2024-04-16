package source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class OMDbClient {
	public static final  String API_KEY = "aabeafb9";
	
	private static StringBuilder getResponse(String movieTitle) {
		StringBuilder response = null;
        try {
            // Effectuer la requête HTTP GET vers l'API OMDb
            URL url = new URL("http://www.omdbapi.com/?t=" + movieTitle + "&apikey=" + API_KEY + "&r=xml");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
	}
	private static String evaluateXPathExpression(StringBuilder httpResponse) {
		String plot = null;
		try {
			 // Convertir la réponse XML en arbre DOM
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(new InputSource(new StringReader(httpResponse.toString())));

	        // Créer un objet XPath pour naviguer dans l'arbre DOM
	        XPath xpath = XPathFactory.newInstance().newXPath();

	        // Définir l'expression XPath pour extraire le résumé (plot) du film
	        XPathExpression plotExpr = xpath.compile("/root/movie/@plot");

	        // Évaluer l'expression XPath pour récupérer le résumé du film
	        plot = (String) plotExpr.evaluate(doc, XPathConstants.STRING);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return plot;   
	}
	public static String getMovieResume(String movieTitle) {
		StringBuilder response = getResponse(movieTitle);
		String plot = evaluateXPathExpression(response);
		return plot;
	}
	
}
	