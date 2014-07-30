package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.sun.corba.se.spi.orb.StringPair;
import com.sun.org.apache.regexp.internal.recompile;

import model.Document;
import service.SearchService;
import service.StringPreparerService;

public class SearchController {

	private HashSet<Document> documents;
	private HashMap<String, ArrayList<Document>> indexes;
	private SearchService _searchService;
	private StringPreparerService _stringService;
	
	
	public SearchController(String fileDirectory) {
		_searchService = new SearchService();
		_stringService = new StringPreparerService();
		documents = _searchService.parseSourceFileToDocuments(fileDirectory); // Default
		indexes = _searchService.createIndexMap(documents);
	}

	public ArrayList<Document> search(String searchPhrase) {
		ArrayList<Document> searchedDocuments = new ArrayList<Document>();

		if (searchPhrase.isEmpty())
			return searchedDocuments;

		searchPhrase = _stringService.cleanString(searchPhrase);
		String[] splittedPhrase = searchPhrase.split("\\s");
		for (int i = 0; i < splittedPhrase.length; i++) {
			splittedPhrase[i] = _stringService.getBaseFormWord(splittedPhrase[i]);
		}
		
		if(splittedPhrase.length == 1)
			return indexes.get(splittedPhrase[0]);
		
		ArrayList<Document> phrase1 = indexes
				.get(splittedPhrase[0]);
		ArrayList<Document> phrase2 = indexes
				.get(splittedPhrase[2]);
		
		if(splittedPhrase[1].equals("and")){
			for (Document document : phrase2) {
				if (phrase1.contains(document))
					searchedDocuments.add(document);
			}
		}
		
		if(splittedPhrase[1].equals("or")){
			searchedDocuments = phrase1;
			for (Document document : phrase2) {
				if (!phrase1.contains(document))
					searchedDocuments.add(document);
			}
		}
		
		return searchedDocuments;
	}
}
