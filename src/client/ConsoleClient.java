package client;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import controller.SearchController;
import service.SearchService;
import service.StringPreparerService;
import model.Document;

public class ConsoleClient {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to the LOOKI LOOKI maschine.");
		System.out.print("Enter your search phrase. You can use AND | OR:");
		String searchedString = scanner.nextLine();
		
		SearchController searcher = new SearchController("ressources/german_news_example");
		ArrayList<Document> searchedDocuments = searcher.search(searchedString);
		printResults(searchedDocuments);
		System.out.println("Done. Bye Bye");
	}
	
	public static void printResults(ArrayList<Document> results) {
		System.out.println("***** Start search *****");
		if(results == null || results.isEmpty()){
			System.out.println("Nothing found!");
			System.out.println("***** End search *****");
			return;
		}
		for (Document document : results) {
			System.out.println(document);
		}
		System.out.println("***** End search *****");
	}

	
	

}
