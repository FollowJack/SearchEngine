package client;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

import controller.SearchController;
import service.SearchService;
import service.StringPreparerService;
import model.Document;

public class ConsoleClient {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to the LOOKI LOOKI maschine.");
//		System.out.print("Enter Some Text:");
//		String searchedString = scanner.nextLine();
//		System.out.println("You just entered:" + searchedString);
		
		SearchController searcher = new SearchController();
		searcher.parseSourceFileToDocuments("ressources/german_news_example");
		System.out.println("Done. Bye Bye");
	}

	
	

}
