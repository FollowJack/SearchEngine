package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import model.Document;
import service.SearchService;

public class SearchController {

	private HashSet<Document> documents;
	private HashMap<String, List<Document>> indexes;
	
	public SearchController(){
		parseSourceFileToDocuments("ressources/german_news_example"); //Default 
		createIndexMap();
	}
	
	public SearchController(String fileDirectory){
		parseSourceFileToDocuments(fileDirectory);
		createIndexMap();
	}
	

	private void parseSourceFileToDocuments(String fileDirectory) {
		SearchService service = new SearchService();

		// load file
		try (BufferedReader reader = new BufferedReader(new FileReader(
				fileDirectory))) {
			String currentLine;
			String contentLine;
			while ((currentLine = reader.readLine()) != null) {
				contentLine = currentLine; // ToDo: cut index and tabulator
				Document document = service.parseToDocument(contentLine);
				documents.add(document);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createIndexMap() {
		indexes = new HashMap<String, List<Document>>();
		List<Document> mappedDocuments = null;

		for (Document document : documents) {
			for (String word : document.getParsed()) {
				mappedDocuments = indexes.containsKey(word) 
						? indexes.get(word)
						: new ArrayList<Document>();
						
				mappedDocuments.add(document);
				indexes.put(word, mappedDocuments);
			}
		}
	}
	
	public List<Document> search(String searchPhrase){
		List<Document> searchedDocuments = new ArrayList<Document>();
		if(searchPhrase.contains("&")){
			//case (word & word) | word
			if(searchPhrase.contains("|"));
			//case word & word
			else;
		}
		else if(searchPhrase.contains("|")); //case word | word
		else
			searchedDocuments = indexes.get(searchPhrase); //case single word
		
		return searchedDocuments;
	}
}
