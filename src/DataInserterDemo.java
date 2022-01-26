import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import com.journalbuddy.PDFParser.PDFParserClass;
import com.journalbuddy.JournalDatabase.JournalData;
import com.journalbuddy.JournalDatabase.DOIParsing;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import com.journalbuddy.JournalDatabase.InsertJournal;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;

public class DataInserterDemo {

	public static void main(String[] args) throws IOException, InterruptedException, JSONException, SQLException, ParseException {
		
		String DatabaseURL="jdbc:derby:E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\db;create=true";
		FileUtils.deleteDirectory(new File("E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\db"));
		InsertJournal.CreateTables(DatabaseURL);
		
		
    	String TXTfilesLoc="E:\\Research Papers\\New folder";
    	String CSVPath="E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\yourfile.csv";
		String databaseURL="jdbc:derby:E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\db";//create=true";

    	
    	List<String> directoryName= new ArrayList<String>();
    	directoryName.add("E:\\Research Papers\\Integrated Circuit");
    	directoryName.add("E:\\Research Papers\\Image Processing");
    	
    	Hashtable<String, String> doi_dict = PDFParserClass.PDFtoTXTMain(directoryName, TXTfilesLoc);
    	System.out.print("###########DONE###############");
    	System.out.println("The set is: " + doi_dict.toString());
    	
    	List<JournalData> sampledata=new ArrayList<JournalData>();
    	
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
    	

        for (int i = 0; i < sampledata.size(); i++) {
        	InsertJournal.InsertJournalFun(DatabaseURL, sampledata.get(i));
        }
    	
        InsertJournal.ExportToCSV(databaseURL, CSVPath);;
		
//		ResultSetMetaData rsmd = rs.getMetaData();
//		int columnsNumber = rsmd.getColumnCount();                     
//		while (rs.next()) {
//		for(int i = 1 ; i <= columnsNumber; i++){
//		      System.out.print(rs.getString(i) + " ");
//		}
//		  System.out.println();//Move to the next line to print the next row.           
//
//		    }
    	
	}

}
