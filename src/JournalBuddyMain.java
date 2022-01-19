import com.journalbuddy.invertedindexing.InvertedIndexingMain;
import com.journalbuddy.MatchingVocab.MatchingIndex;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import com.journalbuddy.invertedindexing.InvertedIndexParser;
import com.journalbuddy.PDFParser.PDFParserClass;

public class JournalBuddyMain {

	public static void main(String[] args) throws IOException {
		
		
    	String TXTfilesLoc="E:\\Research Papers\\New folder";
    	String InvertedIndexFile="E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\op.txt";
    	
    	List<String> directoryName= new ArrayList<String>();
    	directoryName.add("E:\\Research Papers\\Integrated Circuit");
    	directoryName.add("E:\\Research Papers\\Image Processing");
    	
    	Hashtable<String, String> doi_dict = PDFParserClass.PDFtoTXTMain(directoryName, TXTfilesLoc);
    	System.out.print("###########DONE###############");
    	System.out.println("The set is: " + doi_dict.toString());
    	
        InvertedIndexingMain.GenerateInvertedIndex(TXTfilesLoc,InvertedIndexFile);

    	ConcurrentHashMap<String, String[]> IndexLines=InvertedIndexParser.ReadIndexLines(InvertedIndexFile);
    	System.out.print(IndexLines.size());
    	
    	ConcurrentHashMap<String, String> MatchWords =MatchingIndex.GetMatchingWordsFromII(InvertedIndexFile, "intr");
    	System.out.println("The set is: " + MatchWords.toString());
    	
	}

}
