import java.io.*;

import org.apache.jena.query.* ;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;

public class Requete2 {

    static final String inputFileName  = "donnees.ttl";
	
    public static void main (String args[]) {
    	Model model = ModelFactory.createDefaultModel();
    	
        InputStream in = FileManager.get().open( inputFileName );
        if (in == null) {
            throw new IllegalArgumentException( "File: " + inputFileName + " not found");
        }
        
        // read the RDF/XML file
        model.read(in, null, "Turtle");
    	
    	String queryString = "SELECT ?titre { "
    			+ "?x <http://usefulinc.com/ns/doap#name> ?titre; "
    			+ "BIND( (year(?date_de_fin) - year(?date_de_debut))*12 + month(?date_de_fin) - month(?date_de_debut) AS ?Mois_effectif)"
    			+ "BIND (?Mois_effectif - ?duree AS ?difference ) }"
    			+ "ORDER BY ?difference LIMIT 100" ;
    	Query query = QueryFactory.create(queryString) ;
    	try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
    		ResultSet results = qexec.execSelect() ;
    		ResultSetFormatter.out(System.out, results, query) ;
	    }
    }
}