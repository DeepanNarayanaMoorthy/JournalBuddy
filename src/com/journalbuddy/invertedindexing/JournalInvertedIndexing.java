package com.journalbuddy.invertedindexing;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class JournalInvertedIndexing implements Runnable {

	private CompletionService<List<Vocab>> completionService;
	private ConcurrentHashMap<String, StringBuffer> invertedIndex;

	public JournalInvertedIndexing(
			CompletionService<List<Vocab>> completionService,
			ConcurrentHashMap<String, StringBuffer> invertedIndex) {
		this.completionService = completionService;
		this.invertedIndex = invertedIndex;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				try {
					List<Vocab> documents = completionService.take().get();
					for (Vocab document : documents) {
						updateInvertedIndex(document.getVoc(), invertedIndex, document.getFileName());
						//indexWriter.updateInvertedIndex(document.getVoc(), document.getFileName());
					}
				} catch (InterruptedException e) {
					break;
				}
			}
			while (true) {
				Future<List<Vocab>> future = completionService.poll();
				if (future == null)
					break;
				List<Vocab> documents = future.get();
				for (Vocab document : documents) {
					updateInvertedIndex(document.getVoc(), invertedIndex, document.getFileName());
					//indexWriter.updateInvertedIndex(document.getVoc(), document.getFileName());
				}
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	private void updateInvertedIndex(
			Map<String, Integer> voc,
			ConcurrentHashMap<String, StringBuffer> invertedIndex,
			String fileName) {

		for (String word : voc.keySet()) {
			if (word.length() >= 3) {
				StringBuffer buffer=invertedIndex.computeIfAbsent(word, k -> new StringBuffer());
				synchronized (buffer) {
					buffer.append(fileName).append(";");
				}
			}
		}
	}

	
	

}
