package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import model.Document;

public class SearchService implements ISearchService{
	public Document parseToDocument(String original){
		Document document = new Document(original);
		IStringParserService stringService = new StringParserService();
		//prepare the document
		original = stringService.cleanString(original);
		document.setParsed(stringService.tokenisation(original));
		document.setParsed(stringService.reduceToBaseForm(document.getParsed()));
		document.setParsed(stringService.removeStopWords(document.getParsed()));
		return document;
	}
	
	public HashSet<Document> parseSourceFileToDocuments(String fileDirectory) {
		HashSet<Document> documents = new HashSet<Document>();
		// load file
		try (BufferedReader reader = new BufferedReader(new FileReader(
				fileDirectory))) {
			String currentLine;
			String contentLine;
			while ((currentLine = reader.readLine()) != null) {
				contentLine = currentLine; // ToDo: cut index and tabulator
				Document document = parseToDocument(contentLine);
				documents.add(document);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return documents;
	}
	
	public HashMap<String, ArrayList<Document>> createIndexMap(HashSet<Document> documents) {
		HashMap<String, ArrayList<Document>> indexes = new HashMap<String, ArrayList<Document>>();

		for (Document document : documents) {
			for (String word : document.getParsed()) {
				ArrayList<Document> mappedDocuments = indexes.containsKey(word) 
						? indexes.get(word)
						: new ArrayList<Document>();
						
				mappedDocuments.add(document);
				indexes.put(word, mappedDocuments);
			}
		}
		
		return indexes;
	}
}
