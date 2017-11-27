import java.io.*;

import org.apache.jena.query.* ;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;

public class Api {

    static final String inputFileName  = "vc2.ttl";
	
    public static void main (String args[]) {
    	Model model = ModelFactory.createDefaultModel();
    	
        InputStream in = FileManager.get().open( inputFileName );
        if (in == null) {
            throw new IllegalArgumentException( "File: " + inputFileName + " not found");
        }
        
        // read the RDF/XML file
        model.read(in, null, "N-Triples");
    	
    	String queryString = "SELECT ?person ?name { ?x <http://www.w3.org/2001/vcard-rdf/3.0#Given> ?name; <http://www.w3.org/2001/vcard-rdf/3.0#Family> ?person}" ;
    	Query query = QueryFactory.create(queryString) ;
    	try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
    		ResultSet results = qexec.execSelect() ;
    		ResultSetFormatter.out(System.out, results, query) ;
	    }
    }
}