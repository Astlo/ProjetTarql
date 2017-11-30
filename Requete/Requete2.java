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
        
        String queryString = "SELECT ?titre ?difference { "
                + "?x <http://usefulinc.com/ns/doap#name> ?titre;"
                + "<https://www.w3.org/2006/time#HasBeginning> ?date_de_debut;"
                + "<https://www.w3.org/2006/time#hasEnd> ?date_de_fin."
                + "BIND ((year (<http://www.w3.org/2001/XMLSchema#date>(?date_de_fin)) - year (<http://www.w3.org/2001/XMLSchema#date>(?date_de_debut)))*12 + month (<http://www.w3.org/2001/XMLSchema#date>(?date_de_fin)) - month (<http://www.w3.org/2001/XMLSchema#date>(?date_de_debut)) AS ?mois_effectif)"
                + "BIND (?mois_effectif - ?duree AS ?difference )}"
                + "ORDER BY ?difference LIMIT 100" ;
        Query query = QueryFactory.create(queryString) ;
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect() ;
            ResultSetFormatter.out(System.out, results, query) ;
        }
    }
}