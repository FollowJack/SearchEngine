package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class StringParserService implements IStringParserService {

	// private static String blackList = "[.,:;!%&/()=?]";
	private static HashMap<String, String> stemWords = getStemWordsFromFile("ressources/wordlist_german");
	private static Set<String> stopWords = getStopWordsFromFile("ressources/stopwords_german");
	private static String whiteList = "[^a-zA-Z0-9\\sÄäÖöÜüß]";
	private static HashMap<String, String> specialCharacters = getSpecialCharacters();

	public StringParserService() {
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
	public HashMap<String, Integer> tokenisation(String cleanedString) {
		HashMap<String, Integer> tokens = new HashMap<String, Integer>();
		String[] words = cleanedString.split("\\s+");
		// Add each word to set
		for (String word : words) {
			int wordCounter = 0;
			if (tokens.containsKey(word)) {
				wordCounter = tokens.get(word) + 1;
				tokens.put(word, wordCounter);
			}
		}
		return tokens;
	}

	/**
	 * map words in hashSet<String> to base form
	 * 
	 * @param words
	 */
	public HashMap<String, Integer> reduceToBaseForm(
			HashMap<String, Integer> words) {
		HashMap<String, Integer> baseFormSet = new HashMap<String, Integer>();
		Iterator it = words.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Integer> pairs = (Map.Entry<String, Integer>) it
					.next();
			baseFormSet.put(getBaseFormWord(pairs.getKey()), pairs.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}
		return baseFormSet;
	}

	public String getBaseFormWord(String word) {
		if (stemWords.containsKey(word))
			return stemWords.get(word);

		return word;
	}

	public HashMap<String, Integer> removeStopWords(
			HashMap<String, Integer> words) {
		HashMap<String, Integer> cleanWords = new HashMap<String, Integer>();

		if (words == null || words.isEmpty())
			return cleanWords;

		Iterator it = words.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Integer> pairs = (Map.Entry<String, Integer>) it
					.next();
			if (!stopWords.contains(pairs.getKey()))
				cleanWords.put(pairs.getKey(), pairs.getValue());
			it.remove(); // avoids a ConcurrentModificationException
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
