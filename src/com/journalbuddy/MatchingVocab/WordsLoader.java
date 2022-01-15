package com.journalbuddy.MatchingVocab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class WordsLoader {

	public static ConcurrentHashMap<String, String> load(String path) {
		
		Path file = Paths.get(path);
		ConcurrentHashMap<String, String> data = new ConcurrentHashMap<String, String>();
		try (InputStream in = Files.newInputStream(file);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] InvArr=line.split(":");
				data.put(InvArr[0], InvArr[1]);
			}
		} catch (IOException x) {
			x.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}
}
