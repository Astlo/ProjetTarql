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

        String queryString = "SELECT ?anneeDebut ?pays (SUM(?money2) AS ?somme) (AVG(?money2) AS ?moyenne) { "
	    			+ "{"
	    				+ "?x <http://purl.org/cerif/frapo/BudgetedAmount> ?money;"
	    				+ "<https://www.w3.org/ns/org#linkedTo> ?y;"
	    				+ "<https://www.w3.org/2006/time#hasBeginning> ?date_de_debut."
	    				+ "?y <http://dbpedia.org/ontology/locationCountry> ?pays."
	                    + "BIND( strbefore( ?date_de_debut, \"-\" ) as ?anneeDebut )"
	                    + "BIND(<http://www.w3.org/2001/XMLSchema#float>(?money) AS ?money2)"
	    			+ "} UNION {"
	    				+ "GRAPH ?g {"
	    					+ "?x <http://purl.org/cerif/frapo/budgetedAmount> ?money;"
		    				+ "<https://www.w3.org/ns/org#linkedTo> ?y;"
	    					+ "<https://www.w3.org/2006/time#hasBeginning> ?anneeDebut."
	    					+ "?y <http://dbpedia.org/ontology/locationCountry> ?pays;"
	    					+ "<https://www.w3.org/2006/time#hasBeginning> ?anneeDebut."
		                    + "BIND(<http://www.w3.org/2001/XMLSchema#float>(?money) AS ?money2)"
	    				+ "}"
	    			+ "}"
    			+ "}"
    			+ "GROUP BY ?anneeDebut ?pays "
    			+ "ORDER BY ?anneeDebut ?pays" ;
    	
    	Query query = QueryFactory.create(queryString) ;
    	
    	
    	try (QueryExecution qexec = QueryExecutionFactory.create(query, dataset)) {
    		ResultSet results = qexec.execSelect() ;
    		ResultSetFormatter.out(System.out, results, query) ;
	    }
    }
}