package com.journalbuddy.invertedindexing;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class VocabParser {
	
	public Map<String, Integer>  parse(String route) {
		Map<String, Integer> ret=new HashMap<String,Integer>();
		Path file=Paths.get(route);
		try {
			List<String> lines = Files.readAllLines(file, StandardCharsets.ISO_8859_1);
			for (String line : lines) {
				parseLine(line,ret);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return ret;
		
	}

	private static final Pattern PATTERN = Pattern.compile("\\P{IsAlphabetic}+");

	private void parseLine(String line, Map<String, Integer> ret) {
	  for(String word: PATTERN.split(line)) {
	    if(!word.isEmpty())
	      ret.merge(Normalizer.normalize(word, Normalizer.Form.NFKD).toLowerCase(), 1, (a, b) -> a+b);
	  }
	}
	
}
