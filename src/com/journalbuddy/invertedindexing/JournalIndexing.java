package com.journalbuddy.invertedindexing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class JournalIndexing implements Callable<List<Vocab>> {

	private List<File> files;

	public JournalIndexing(List<File> files) {
		this.files = files;
	}

	@Override
	public List<Vocab> call() throws Exception {
		List<Vocab> documents = new ArrayList<Vocab>();
		VocabParser parser = new VocabParser();
		for (File file : files) {
			Map<String, Integer> voc = parser.parse(file
					.getAbsolutePath());

			Vocab document = new Vocab();
			document.setFileName(file.getName());
			document.setVoc(voc);
			
			documents.add(document);
			
		}
		return documents;
	}

}
