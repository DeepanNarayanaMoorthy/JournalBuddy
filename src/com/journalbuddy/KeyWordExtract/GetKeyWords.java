package com.journalbuddy.KeyWordExtract;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Phaser;

public class GetKeyWords {
	
	public static void WriteToFile(List<String> filenamespaths, String FilePath, String KeyWordFilePath) throws IOException {

		Date start, end;

		ConcurrentHashMap<String, Word> globalVoc = new ConcurrentHashMap<>();
		ConcurrentHashMap<String, Integer> globalKeywords = new ConcurrentHashMap<>();
		
		List<File> tempfilelist = new ArrayList<File>();
		File dummyfile;
		for(String filepath: filenamespaths) {
			dummyfile=new File(filepath);
			tempfilelist.add(dummyfile);
		}
		start = new Date();

//		File[] files = source.listFiles(f -> f.getName().endsWith(".txt"));
		File[] files=tempfilelist.toArray(new File[0]);
		if (files == null) {
			System.err.println("The 'data' folder not found!");
			return;
		}
		ConcurrentLinkedDeque<File> concurrentFileListPhase1 = new ConcurrentLinkedDeque<>(Arrays.asList(files));
		ConcurrentLinkedDeque<File> concurrentFileListPhase2 = new ConcurrentLinkedDeque<>(Arrays.asList(files));

		int numDocuments = files.length;
		
		System.out.println(concurrentFileListPhase1.size());
		System.out.println(concurrentFileListPhase2.size());

		int factor = 1;

		int numTasks = factor * Runtime.getRuntime().availableProcessors();
		Phaser phaser = new Phaser();

		Thread threads[] = new Thread[numTasks];
		KeywordTaskManager tasks[] = new KeywordTaskManager[numTasks];

		
		for (int i = 0; i < numTasks; i++) {
			tasks[i] = new KeywordTaskManager(concurrentFileListPhase1, concurrentFileListPhase2, phaser, globalVoc,
					globalKeywords, concurrentFileListPhase1.size(), "Task " + i, i==0);
			phaser.register();
			System.out.println(phaser.getRegisteredParties() + " tasks arrived to the Phaser.");
		}

		for (int i = 0; i < numTasks; i++) {
			threads[i] = new Thread(tasks[i]);
			threads[i].start();
		}

		for (int i = 0; i < numTasks; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Is Terminated: " + phaser.isTerminated());

		end = new Date();
		
        FileWriter f0 = new FileWriter(FilePath);
        String newLine = System.getProperty("line.separator");
        
        String writestr;
		Iterator hmIterator = globalVoc.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            Word tempwrd=globalVoc.get(mapElement.getKey());            
            writestr=tempwrd.getWord()+","+tempwrd.getTf()+","+tempwrd.getDf()+","+tempwrd.getTfIdf()+ newLine;
            f0.write(writestr);
        }
        f0.close();
        
        FileWriter f1 = new FileWriter(KeyWordFilePath);  

		hmIterator = globalKeywords.entrySet().iterator();
        while (hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            writestr=mapElement.getKey()+","+mapElement.getValue()+newLine;
            f1.write(writestr);
        }
        f1.close();

	}
	
	public static ConcurrentHashMap<String, Word> ReadVocabs(String FilePath) throws IOException {
		ConcurrentHashMap<String, Word> globalVoc = new ConcurrentHashMap<>();
		List<String> list = Files.readAllLines(new File(FilePath).toPath(), StandardCharsets.ISO_8859_1 );

		list.parallelStream().forEach((userlist) -> {
//			System.out.println(userlist);
			String[] tempvocabb=userlist.split(",");
			Word dummyword=new Word(tempvocabb[0]);
			dummyword.setDf(Integer.parseInt(tempvocabb[1]));
			dummyword.setTf(Integer.parseInt(tempvocabb[2]));
			globalVoc.put(tempvocabb[0], dummyword);
		});
		return globalVoc;
	}
	
	public static ConcurrentHashMap<String, Integer> ReadKeywords(String FilePath) throws IOException {
		ConcurrentHashMap<String, Integer> globalVoc = new ConcurrentHashMap<>();
		List<String> list = Files.readAllLines(new File(FilePath).toPath(), StandardCharsets.ISO_8859_1 );

		list.parallelStream().forEach((userlist) -> {
//			System.out.println(userlist);
			String[] tempvocabb=userlist.split(",");
			globalVoc.put(tempvocabb[0], Integer.parseInt(tempvocabb[1]));
		});
		return globalVoc;
	}
}
