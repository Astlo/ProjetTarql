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
	    		+ "?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://usefulinc.com/ns/doap#Project>;"
                + "<http://usefulinc.com/ns/doap#name> ?titre;"
                + "<https://www.w3.org/2006/time#hasBeginning> ?date_de_debut;"
                + "<https://www.w3.org/2006/time#hasEnd> ?date_de_fin;"
                + "<https://www.w3.org/2006/time#Duration> ?duree."
                + "BIND( strbefore( ?date_de_debut, \"-\" ) as ?anneeDebut )"
                + "BIND( strbefore( ?date_de_fin, \"-\" ) as ?anneeFin )"
                + "BIND( strafter( ?date_de_debut, \"-\" ) as ?moisjourDebut )"
                + "BIND( strafter( ?date_de_fin, \"-\" ) as ?moisjourFin )"
                + "BIND( strbefore( ?moisjourDebut, \"-\" ) as ?moisDebut )"
                + "BIND( strbefore( ?moisjourFin, \"-\" ) as ?moisFin )"
                + "BIND((<http://www.w3.org/2001/XMLSchema#integer>(?anneeFin) - <http://www.w3.org/2001/XMLSchema#integer>(?anneeDebut))*12 + <http://www.w3.org/2001/XMLSchema#integer>(?moisFin) - <http://www.w3.org/2001/XMLSchema#integer>(?moisDebut) AS ?mois_effectif)"
                + "BIND((<http://www.w3.org/2001/XMLSchema#integer>(?duree) - ?mois_effectif) AS ?difference )"
                + "}"
                + "ORDER BY desc(?difference) LIMIT 100" ;
        Query query = QueryFactory.create(queryString) ;
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect() ;
            ResultSetFormatter.out(System.out, results, query) ;
        }
    }
}