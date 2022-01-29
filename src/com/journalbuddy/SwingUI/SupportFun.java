package com.journalbuddy.SwingUI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.journalbuddy.KeyWordExtract.GetKeyWords;
import com.journalbuddy.KeyWordExtract.Word;
import com.journalbuddy.MatchingVocab.BestMatchingCalc;
import com.journalbuddy.MatchingVocab.BestMatchingData;

public class SupportFun {
	public static void RefreshKeywordFun(String TXTfilesLoc, String vocabsfile, String Keywordsfile,DefaultTableModel allkeytablemodel, boolean forkeytable) {
		List<String> results = new ArrayList<String>();
		File[] files = new File(TXTfilesLoc).listFiles();
		for (File file : files) {
		    if (file.isFile()) {
		    	results.add(file.getAbsolutePath());
		    }
		}

		System.out.println(results.toString());
		try {
			GetKeyWords.WriteToFile(results, vocabsfile, Keywordsfile);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		ConcurrentHashMap<String, Integer> ReadKeywordsmap = new ConcurrentHashMap<String, Integer>();
		ConcurrentHashMap<String, Word> ReadVocabsmap = new ConcurrentHashMap<String, Word>();
		try {
			ReadKeywordsmap = GetKeyWords.ReadKeywords(Keywordsfile);
			ReadVocabsmap = GetKeyWords.ReadVocabs(vocabsfile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(forkeytable) {
			List<Word> finaldisplay= new ArrayList< Word>();
			for (String key : ReadKeywordsmap.keySet()) {
				finaldisplay.add(ReadVocabsmap.get(key));
			}	
			allkeytablemodel.setRowCount(0);
			for(Word finalkeys: finaldisplay) {
				allkeytablemodel.addRow(new Object[] {finalkeys.getWord(), finalkeys.getTf(), finalkeys.getDf()});
			}
		} else {
			allkeytablemodel.setRowCount(0);
			for(String finalkeys: ReadVocabsmap.keySet()) {
				allkeytablemodel.addRow(new Object[] {finalkeys, ReadVocabsmap.get(finalkeys).getTf(), ReadVocabsmap.get(finalkeys).getDf()});
			}
		}
	}
	
	public static void KeySearchFun(JTable allkeytable, JTextField allkeysearchword, String vocabsfile, DefaultTableModel allkeytablemodel) {
		List<String> wordslist=new ArrayList<String>();
		for(int i = 0;i<allkeytable.getRowCount();i++) {
			wordslist.add((String) allkeytable.getValueAt(i, 0));
		}
    	BestMatchingData result=new BestMatchingData();
		try {
			result = BestMatchingCalc.getBestMatchingWords(allkeysearchword.getText(), wordslist);
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		List<String> results=result.getWords();
		ConcurrentHashMap<String, Word> ReadVocabsmap = new ConcurrentHashMap<String, Word>();
		try {
			ReadVocabsmap = GetKeyWords.ReadVocabs(vocabsfile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		List<Word> finaldisplay= new ArrayList< Word>();
		for (String key : results) {
			finaldisplay.add(ReadVocabsmap.get(key));
		}	
		allkeytablemodel.setRowCount(0);
		for(Word finalkeys: finaldisplay) {
			allkeytablemodel.addRow(new Object[] {finalkeys.getWord(), finalkeys.getTf(), finalkeys.getDf()});
		}
	}
	
	public static void ShowKeywordFun(String Keywordsfile, String vocabsfile, DefaultTableModel allkeytablemodel, boolean forkeytable ) {
		ConcurrentHashMap<String, Integer> ReadKeywordsmap = new ConcurrentHashMap<String, Integer>();
		ConcurrentHashMap<String, Word> ReadVocabsmap = new ConcurrentHashMap<String, Word>();
		try {
			ReadKeywordsmap = GetKeyWords.ReadKeywords(Keywordsfile);
			ReadVocabsmap = GetKeyWords.ReadVocabs(vocabsfile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		List<Word> finaldisplay= new ArrayList< Word>();
		for (String key : ReadKeywordsmap.keySet()) {
			finaldisplay.add(ReadVocabsmap.get(key));
		}	
		allkeytablemodel.setRowCount(0);
		if(forkeytable) {
			for(Word finalkeys: finaldisplay) {
				allkeytablemodel.addRow(new Object[] {finalkeys.getWord(), finalkeys.getTf(), finalkeys.getDf()});
			}
		} else {
			for(String finalkeys: ReadVocabsmap.keySet()) {
				allkeytablemodel.addRow(new Object[] {finalkeys, ReadVocabsmap.get(finalkeys).getTf(), ReadVocabsmap.get(finalkeys).getDf()});
			}
		}
	}
}
