import com.journalbuddy.invertedindexing.InvertedIndexingMain;
import com.journalbuddy.MatchingVocab.MatchingIndex;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.journalbuddy.invertedindexing.InvertedIndexParser;
import com.journalbuddy.PDFParser.PDFParserClass;
import com.journalbuddy.JournalDatabase.JournalData;
import com.journalbuddy.JournalDatabase.DOIParsing;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.json.JSONException;

public class JournalBuddyMain {

	public static void main(String[] args) throws IOException, InterruptedException, JSONException {
		
		
    	String TXTfilesLoc="E:\\Research Papers\\New folder";
    	String InvertedIndexFile="E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\op.txt";
    	
    	List<String> directoryName= new ArrayList<String>();
    	directoryName.add("E:\\Research Papers\\Integrated Circuit");
    	directoryName.add("E:\\Research Papers\\Image Processing");
    	
    	Hashtable<String, String> doi_dict = PDFParserClass.PDFtoTXTMain(directoryName, TXTfilesLoc);
    	System.out.print("###########DONE###############");
    	System.out.println("The set is: " + doi_dict.toString());
    	
    	List<JournalData> sampledata=new ArrayList();
    	
    	List<String> keys = doi_dict.entrySet().stream()
    			  .map(Map.Entry::getKey)
    			  .sorted()
    			  .limit(doi_dict.size())
    			  .collect(Collectors.toList());
    	
    	int countt=0;
    	for (int i = 0; i < keys.size(); i++) {
    		countt=countt+1;
    	    String key = keys.get(i);
    	    String value = doi_dict.get(keys.get(i));
    	    System.out.print(value);
    	    System.out.println (countt+" Key: " + key + "\nValue: " + value+"\n");
    	    sampledata.add(DOIParsing.getJournalData(key, value));
    	    TimeUnit.SECONDS.sleep(1);
    	}
    	
   
        for (int i = 0; i < sampledata.size(); i++) {
        	 
            // Print all elements of List
            sampledata.get(i).PrintAll();
        }
    	
//        InvertedIndexingMain.GenerateInvertedIndex(TXTfilesLoc,InvertedIndexFile);
//
//    	ConcurrentHashMap<String, String[]> IndexLines=InvertedIndexParser.ReadIndexLines(InvertedIndexFile);
//    	System.out.print(IndexLines.size());
//    	
//    	ConcurrentHashMap<String, String> MatchWords =MatchingIndex.GetMatchingWordsFromII(InvertedIndexFile, "intr");
//    	System.out.println("The set is: " + MatchWords.toString());
    	
	}

}
