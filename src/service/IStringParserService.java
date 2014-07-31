package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public interface IStringParserService {

	public String cleanString(String original);
	public HashMap<String, Integer> tokenisation(String cleanedString);
	public HashMap<String, Integer> reduceToBaseForm(HashMap<String, Integer> words);
	public String getBaseFormWord(String word);
	public HashMap<String, Integer> removeStopWords(HashMap<String, Integer> words);
}