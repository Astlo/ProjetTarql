import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.RDFSRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;

import org.apache.jena.rdf.model.*;

public class JenaReasoningWithRules
{
	public static void main(String[] args) 
	{
		Model model = ModelFactory.createDefaultModel();
		model.read( "donnees.ttl" );
		
		RDFSRuleReasoner reasoner = (RDFSRuleReasoner) new GenericRuleReasoner( Rule.rulesFromURL( "rules.txt" ) );
		
		InfModel infModel = ModelFactory.createInfModel( reasoner, model );
 
		StmtIterator it = infModel.listStatements();
		
		while ( it.hasNext() )
		{
			Statement stmt = it.nextStatement();
			
			Resource subject = stmt.getSubject();
			Property predicate = stmt.getPredicate();
			RDFNode object = stmt.getObject();
 
			System.out.println( subject.toString() + " " + predicate.toString() + " " + object.toString() );
		}
	}
}