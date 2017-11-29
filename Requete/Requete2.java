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
        
        String queryString = "SELECT ?titre ?duree ?montant ?rapport { "
                + "?x <http://usefulinc.com/ns/doap#name> ?titre; "
                + "<https://www.w3.org/2006/time#Duration> ?duree;"
                + "<http://purl.org/cerif/frapo/BudgetedAmount> ?montant."
                + "BIND (<http://www.w3.org/2001/XMLSchema#integer>(?duree) AS ?duree2)"
                + "BIND (<http://www.w3.org/2001/XMLSchema#float>(?montant) AS ?montant2)"
                + "BIND(<http://www.w3.org/2001/XMLSchema#decimal>(?montant2/?duree2) AS ?rapport) }"
                + "ORDER BY DESC(?rapport) LIMIT 100" ;
        Query query = QueryFactory.create(queryString) ;
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect() ;
            ResultSetFormatter.out(System.out, results, query) ;
        }
    }
}