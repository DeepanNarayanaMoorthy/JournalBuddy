import com.journalbuddy.invertedindexing.InvertedIndexingMain;
import com.journalbuddy.MatchingVocab.BestMatchingCalc;
import com.journalbuddy.MatchingVocab.BestMatchingData;
import com.journalbuddy.MatchingVocab.MatchingIndex;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import com.journalbuddy.invertedindexing.InvertedIndexParser;
import com.journalbuddy.PDFParser.PDFParserClass;
import org.json.JSONException;

public class InvertedMatchingDemo {

	public static void main(String[] args) throws IOException, InterruptedException, JSONException, SQLException, ParseException {
		
		
    	String TXTfilesLoc="E:\\Research Papers\\New folder";
    	String InvertedIndexFile="E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\op.txt";
    	String InvertedSearchWord="intr";
    	
    	List<String> directoryName= new ArrayList<String>();
    	directoryName.add("E:\\Research Papers\\Integrated Circuit");
    	directoryName.add("E:\\Research Papers\\Image Processing");
    	
    	Hashtable<String, String> doi_dict = PDFParserClass.PDFtoTXTMain(directoryName, TXTfilesLoc);
    	System.out.print("###########DONE###############");
    	System.out.println("The set is: " + doi_dict.toString());
    	
    	doi_dict.keys().asIterator()
        .forEachRemaining(s -> System.out.println(doi_dict.get(s)));
		
//        InvertedIndexingMain.GenerateInvertedIndex(TXTfilesLoc,InvertedIndexFile);
//
//    	ConcurrentHashMap<String, String[]> IndexLines=InvertedIndexParser.ReadIndexLines(InvertedIndexFile);
//    	System.out.print(IndexLines.size());
//    	
//    	ConcurrentHashMap<String, String> MatchWords =MatchingIndex.GetMatchingWordsFromII(InvertedIndexFile, InvertedSearchWord);
//    	System.out.println("Count of Matched Words: "+Integer.toString(MatchWords.size()));
//    	System.out.println("The set is: " + MatchWords.toString());
    	
//    	FOR JUST WORDS LIST
//    	BestMatchingData result;
//		result = BestMatchingCalc.getBestMatchingWords(word, dictionaryWords);
//		List<String> results=result.getWords();
    	
	}

}
