package com.journalbuddy.MatchingVocab;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;


public class MatchingIndex {
	
	public static ConcurrentHashMap<String, String> GetMatchingWordsFromII(String IIFile, String word) {
		ConcurrentHashMap<String, String> MatchingWordsDict = new ConcurrentHashMap<>();
		try {
			Date startTime, endTime;
			ConcurrentHashMap<String, String> dictionary=WordsLoader.load(IIFile);
			
			System.out.println("Dictionary Size: "+dictionary.size());
			
			startTime=new Date();
			
			BestMatchingData result;
			List<String> dictionaryWords = Collections.list(dictionary.keys());

			result = BestMatchingCalc.getBestMatchingWords(word, dictionaryWords);
			List<String> results=result.getWords();
			endTime=new Date();
			System.out.println("Word: "+word);
			System.out.println("Minimun distance: "+result.getDistance());
			System.out.println("List of best matching words: "+results.size());
			for (String wordOut: results) {
				System.out.println(wordOut);
				System.out.println(dictionary.get(wordOut));
				MatchingWordsDict.put(wordOut, dictionary.get(wordOut));
			}
			System.out.println("Execution Time: "+(endTime.getTime()-startTime.getTime()));
			
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return MatchingWordsDict;


	}

}
