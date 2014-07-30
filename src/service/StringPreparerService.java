package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class StringPreparerService {

	// private static String blackList = "[.,:;!%&/()=?]";
	private static HashMap<String, String> stemWords = getStemWordsFromFile("ressources/wordlist_german");
	private static Set<String> stopWords = getStopWordsFromFile("ressources/stopwords_german");
	private static String whiteList = "[^a-zA-Z0-9\\sÄäÖöÜüß]";
	private static HashMap<String, String> specialCharacters = getSpecialCharacters();

	public StringPreparerService() {
	}

	/**
	 * remove punctuation mark,special character and make lower case
	 * 
	 * @param original
	 *            string
	 * @return cleanedString
	 */
	public String cleanString(String original) {
		// remove all !whitelist words
		String cleanedString = original.replaceAll(whiteList, "").toLowerCase();
		// remove all special characters
		Iterator it = specialCharacters.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> pairs = (Map.Entry<String, String>) it
					.next();
			cleanedString = cleanedString.replaceAll(pairs.getKey(),
					pairs.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}

		return cleanedString;
	}

	/**
	 * split all words
	 * 
	 * @param cleanedString
	 * @return
	 */
	public HashSet<String> tokenisation(String cleanedString) {
		HashSet<String> tokens = new HashSet<String>();
		String[] words = cleanedString.split("\\s+");
		// Add each word to set
		for (String word : words) {
			tokens.add(word);
		}
		return tokens;
	}

	/**
	 * map words in hashSet<String> to base form
	 * 
	 * @param words
	 */
	public HashSet<String> reduceToBaseForm(HashSet<String> words) {
		HashSet<String> baseFormSet = new HashSet<>();
		for (String word : words) {
			if (stemWords.containsKey(word))
				baseFormSet.add(stemWords.get(word));
			else
				baseFormSet.add(word);
		}
		return baseFormSet;
	}

	public HashSet<String> removeStopWords(HashSet<String> words) {
		HashSet<String> cleanWords = new HashSet<String>();

		if (words == null || words.isEmpty())
			return cleanWords;

		for (String word : words) {
			if (!stopWords.contains(word))
				cleanWords.add(word);
		}
		return cleanWords;
	}

	// ***** Helper *****
	private static HashMap<String, String> getSpecialCharacters() {
		HashMap<String, String> specialCharacterMap = new HashMap<String, String>();
		specialCharacterMap.put("ä", "ae");
		specialCharacterMap.put("Ä", "ae");
		specialCharacterMap.put("ö", "oe");
		specialCharacterMap.put("Ö", "oe");
		specialCharacterMap.put("ü", "ue");
		specialCharacterMap.put("Ü", "ue");
		specialCharacterMap.put("ß", "ss");
		return specialCharacterMap;
	}

	private static HashMap<String, String> getStemWordsFromFile(
			String fileDirectory) {
		stemWords = new HashMap<String, String>();

		// load file
		try (BufferedReader reader = new BufferedReader(new FileReader(
				fileDirectory))) {
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				// skip # for comments
				if (currentLine.contains("#"))
					continue;

				String[] keyValuePair = currentLine.split("\t");
				stemWords.put(keyValuePair[0], keyValuePair[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stemWords;
	}

	private static Set<String> getStopWordsFromFile(String fileDirectory) {
		HashSet<String> stopWords = new HashSet<String>();

		// load file
		try (BufferedReader reader = new BufferedReader(new FileReader(
				fileDirectory))) {
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				stopWords.add(currentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return stopWords;
	}
}
