import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.* ;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;

public class Requete3 {

    static final String inputFileName  = "donnees.ttl";
    static final String inputFileName2  = "projet.ttl";
	
    public static void main (String args[]) {
    	Model model = ModelFactory.createDefaultModel();
    	Model model2 = ModelFactory.createDefaultModel();
        
        String dftGraphURI = "file:donnees.ttl" ;
        List namedGraphURIs = new ArrayList() ;
        namedGraphURIs.add("file:projet.ttl") ;

        Dataset dataset = DatasetFactory.create(dftGraphURI, namedGraphURIs) ;

        String queryString = "SELECT ?x { "
    			+ "{?x <http://purl.org/cerif/frapo/hasCode> \"661199\"}"
    			+ "UNION {"
    			+ "GRAPH ?g"
    			+ "{?x <http://purl.org/cerif/frapo/hasCode> \"219008\".}}}" ;
    	
    	Query query = QueryFactory.create(queryString) ;
    	
    	
    	try (QueryExecution qexec = QueryExecutionFactory.create(query, dataset)) {
    		ResultSet results = qexec.execSelect() ;
    		ResultSetFormatter.out(System.out, results, query) ;
	    }
    }
}