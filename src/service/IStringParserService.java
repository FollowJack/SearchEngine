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
	public HashSet<String> tokenisation(String cleanedString);
	public HashSet<String> reduceToBaseForm(HashSet<String> words);
	public String getBaseFormWord(String word);
	public HashSet<String> removeStopWords(HashSet<String> words);
}