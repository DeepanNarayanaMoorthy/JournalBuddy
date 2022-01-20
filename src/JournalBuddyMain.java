import com.journalbuddy.invertedindexing.InvertedIndexingMain;
import com.opencsv.CSVWriter;
import com.journalbuddy.MatchingVocab.MatchingIndex;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
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
import com.journalbuddy.JournalDatabase.InsertJournal;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;

public class JournalBuddyMain {

	public static void main(String[] args) throws IOException, InterruptedException, JSONException, SQLException, ParseException {
		
		
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
    			  .limit(5)
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
    	
		FileUtils.deleteDirectory(new File("E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\db"));
		InsertJournal.CreateTables();
		
        for (int i = 0; i < sampledata.size(); i++) {
//        	sampledata.get(i).PrintAll();
        	InsertJournal.InsertJournalFun(sampledata.get(i));
        }
    	
		String URL="jdbc:derby:E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\db";//create=true";
		Connection conn = DriverManager.getConnection(URL);
		Statement insertstmt = conn.createStatement();

		System.out.println("Connection created");
		ResultSet rs = insertstmt.executeQuery("SELECT * "
				+ "FROM journal, author, funder, subject, author_journal, funder_journal, subject_journal "
				+ "WHERE author.pk_author_id = author_journal.fk_author_id "
				+ "AND funder.pk_funder_id = funder_journal.fk_funder_id "
				+ "AND subject.pk_subject_id = subject_journal.fk_subject_id");


		CSVWriter writer = new CSVWriter(new FileWriter("E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\yourfile.csv"));
		Boolean includeHeaders = true;
		writer.writeAll(rs, includeHeaders);
		writer.close();
		
//		ResultSetMetaData rsmd = rs.getMetaData();
//		int columnsNumber = rsmd.getColumnCount();                     
//		while (rs.next()) {
//		for(int i = 1 ; i <= columnsNumber; i++){
//		      System.out.print(rs.getString(i) + " ");
//		}
//		  System.out.println();//Move to the next line to print the next row.           
//
//		    }
		
		
//        InvertedIndexingMain.GenerateInvertedIndex(TXTfilesLoc,InvertedIndexFile);
//
//    	ConcurrentHashMap<String, String[]> IndexLines=InvertedIndexParser.ReadIndexLines(InvertedIndexFile);
//    	System.out.print(IndexLines.size());
//    	
//    	ConcurrentHashMap<String, String> MatchWords =MatchingIndex.GetMatchingWordsFromII(InvertedIndexFile, "intr");
//    	System.out.println("The set is: " + MatchWords.toString());
    	
	}

}
