package com.journalbuddy.invertedindexing;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class InvertedIndexParser {
 
	public static List<String>  ReadIndexLines(String InvertedIndexFilePath) {
		
		BufferedReader IndexBufferReader = null;
		try {
			IndexBufferReader = Files.newBufferedReader(Paths.get(InvertedIndexFilePath));
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> crunchifyList = IndexBufferReader != null ? IndexBufferReader.lines().collect(Collectors.toList()) : null;
		return crunchifyList;
 
	}

 
}

