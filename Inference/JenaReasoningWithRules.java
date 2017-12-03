import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.rdf.model.*;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.File;

public class JenaReasoningWithRules
{
	public static void main(String[] args) 
	{
		Model model = ModelFactory.createDefaultModel();
		model.read( "donnees.ttl" );
		
		GenericRuleReasoner reasoner = new GenericRuleReasoner( Rule.rulesFromURL( "rules.txt" ) );
		
		InfModel infModel = ModelFactory.createInfModel( reasoner, model );
 
		StmtIterator it = infModel.listStatements();
		try{
			File ff=new File("resultat.txt");
			ff.createNewFile();
			FileWriter ffw=new FileWriter(ff);
			while ( it.hasNext() )
			{
				Statement stmt = it.nextStatement();
				
				Resource subject = stmt.getSubject();
				Property predicate = stmt.getPredicate();
				RDFNode object = stmt.getObject();
				ffw.write( subject.toString() + " " + predicate.toString() + " " + object.toString());  // écrire une ligne dans le fichier resultat.txt
				ffw.write("\n"); // forcer le passage à la ligne
			}

		ffw.close(); // fermer le fichier à la fin des traitements
		} catch (Exception e) {}
	}
}