package com.journalbuddy.invertedindexing;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentHashMap;   

public class InvertedIndexParser {
 
	@SuppressWarnings("null")
	public static ConcurrentHashMap<String, String[]>  ReadIndexLines(String InvertedIndexFilePath) {
		
		BufferedReader IndexBufferReader = null;
		ConcurrentHashMap <String, String[]> InvertedIndexMap = null;
		try {
			IndexBufferReader = Files.newBufferedReader(Paths.get(InvertedIndexFilePath), StandardCharsets.ISO_8859_1);
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> IIList = IndexBufferReader != null ? IndexBufferReader.lines().collect(Collectors.toList()) : null;
		IIList.parallelStream().forEach((IIentries) -> {
			String[] tempList=IIentries.split(":");
			String[] tempList2=tempList[1].split(";");
			InvertedIndexMap.put(tempList[0], tempList2);
		});
		return InvertedIndexMap;
 
	}

 
}

