package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import model.Document;

public interface ISearchService {

	public Document parseToDocument(String original);
	public HashSet<Document> parseSourceFileToDocuments(String fileDirectory);
	public HashMap<String, ArrayList<Document>> createIndexMap(HashSet<Document> documents);

}
