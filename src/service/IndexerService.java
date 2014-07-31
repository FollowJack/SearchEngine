package service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

import model.Document;
import util.comparer.VectorAngleComparator;
import util.comparer.VectorAngleComparer;

public class IndexerService {
	
	TreeMap<Document, Double> matchingResults;
	
	public IndexerService(){
		matchingResults = new TreeMap<Document, Double>(new VectorAngleComparator());
	}
	
	public ArrayList<Document> getVectorAngleMatchingResult(Document source, HashSet<Document> documents, int amountOfResults) {
		generateMatchingResults(source,documents);
		ArrayList<Document> results = takeAmountOfResults(amountOfResults);
		
		return results;
	}

	private void generateMatchingResults(Document source,HashSet<Document> documents) {
		if(documents.isEmpty())
			return;
		
		for (Document document : documents) {
			Double angle = VectorAngleComparer.getAngle(source, document);
			matchingResults.put(document, angle);
		}
		
	}

	
}
