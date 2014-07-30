package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import model.Document;
import service.ISearchService;
import service.IStringParserService;
import service.SearchService;
import service.StringParserService;

public class SearchController {

	private HashSet<Document> documents;
	private HashMap<String, ArrayList<Document>> indexes;
	private ISearchService _searchService;
	private IStringParserService _stringService;

	public SearchController(String fileDirectory) {
		_searchService = new SearchService();
		_stringService = new StringParserService();
		documents = _searchService.parseSourceFileToDocuments(fileDirectory); // Default
		indexes = _searchService.createIndexMap(documents);
	}

	public ArrayList<Document> search(String searchPhrase) {
		ArrayList<Document> searchedDocuments = new ArrayList<Document>();

		// case: is empty
		if (searchPhrase.isEmpty())
			return searchedDocuments;

		// parse searched phrases
		searchPhrase = _stringService.cleanString(searchPhrase);
		String[] splittedPhrase = searchPhrase.split("\\s");
		for (int i = 0; i < splittedPhrase.length; i++) {
			splittedPhrase[i] = _stringService
					.getBaseFormWord(splittedPhrase[i]);
		}

		// case: only searched phrase
		if (splittedPhrase.length == 1)
			return indexes.get(splittedPhrase[0]);

		ArrayList<Document> phrase1 = indexes.get(splittedPhrase[0]);
		ArrayList<Document> phrase2 = indexes.get(splittedPhrase[2]);
		// case: 3 searched phrase
		if (splittedPhrase.length == 5) {
			ArrayList<Document> phrase3 = indexes.get(splittedPhrase[4]);
			if (splittedPhrase[3].equals("and")) {
				searchedDocuments = getConnectedDocuments("and", phrase2,
						phrase3);
				searchedDocuments = getConnectedDocuments(splittedPhrase[1],
						phrase1, searchedDocuments);
			} else {
				searchedDocuments = getConnectedDocuments(splittedPhrase[1],
						phrase1, phrase2);
				searchedDocuments = getConnectedDocuments(splittedPhrase[1],
						phrase3, searchedDocuments);
			}
		}
		// case: 2 searched phrase
		else if (splittedPhrase.length == 3) {
			searchedDocuments = getConnectedDocuments(splittedPhrase[1],
					phrase1, phrase2);

		}

		return searchedDocuments;
	}
	
	//***** Helper *****
	private ArrayList<Document> getConnectedDocuments(String connector,
			ArrayList<Document> phrase1, ArrayList<Document> phrase2) {
		
		ArrayList<Document> searchedDocuments = new ArrayList<Document>();

		if (connector.equals("and")) {
			for (Document document : phrase2) {
				if (phrase1.contains(document))
					searchedDocuments.add(document);
			}
		}

		if (connector.equals("or")) {
			searchedDocuments = phrase1;
			for (Document document : phrase2) {
				if (!phrase1.contains(document))
					searchedDocuments.add(document);
			}
		}
		return searchedDocuments;
	}
}
