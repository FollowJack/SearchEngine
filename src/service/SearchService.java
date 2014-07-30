package service;

import model.Document;

public class SearchService {
	
	public boolean Find42(String searchedString){
		return true;
	}
	
	public Document parseToDocument(String original){
		Document document = new Document();
		StringPreparerService stringService = new StringPreparerService();
		//prepare the document
		original = stringService.cleanString(original);
		document.setParsed(stringService.tokenisation(original));
		document.setParsed(stringService.reduceToBaseForm(document.getParsed()));
		document.setParsed(stringService.removeStopWords(document.getParsed()));
		return document;
	}
	
}
