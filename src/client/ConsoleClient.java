package client;

import java.util.ArrayList;
import java.util.Scanner;

import model.Document;
import controller.SearchController;

public class ConsoleClient {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to the LOOKI LOOKI maschine.");
		SearchController searcher = new SearchController(
				"ressources/german_news_example");
		while (true) {
			System.out.print("Enter your search phrase. You can use AND | OR:");

			String searchedString = scanner.nextLine();
			ArrayList<Document> searchedDocuments = searcher
					.search(searchedString);
			printResults(searchedDocuments);
		}
		// System.out.println("Done. Bye Bye");
	}

	public static void printResults(ArrayList<Document> results) {
		System.out.println("***** Start search *****");
		if (results == null || results.isEmpty()) {
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
