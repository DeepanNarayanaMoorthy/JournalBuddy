package com.journalbuddy.DataFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class JournalDataLoader {

	static List<String> readFirst(final Path path, final int numLines) throws IOException {
	    try (final BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.ISO_8859_1)) {
	        final List<String> lines = new ArrayList<>(numLines);
	        int lineNum = 0;
	        String line;
	        while ((line = reader.readLine()) != null && lineNum < numLines) {
	            lines.add(line);
	            lineNum++;
	        }
	        return lines;
	    }
	}
	
	public static java.sql.Date StringtoDate(String startDate) throws ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy");
		java.sql.Date sqlStartDate;
		if(startDate!=null) {
			java.util.Date date = sdf1.parse(startDate);
			sqlStartDate = new java.sql.Date(date.getTime()); 
			
		} else {
			sqlStartDate=null;
		}
		return sqlStartDate;
	}
	static String[] CleanALine(String line){
		String[] DataFirstLine=line.split(",");
		IntStream.range(0, DataFirstLine.length-1).parallel().forEach(i ->  DataFirstLine[i]=DataFirstLine[i].replace("\"", ""));
		return DataFirstLine;
	}
	
	public static void PutInHash(HashMap<String,String> TempJournal, String[] FirstLineData, String[] CurrentLineData, int index) {
		try {
			TempJournal.put(FirstLineData[index], CurrentLineData[index]);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static int NullIntParser(String integrtstr) {
		int parseintegerr;
		try {
			parseintegerr=Integer.parseInt(integrtstr);
		} catch (Exception e) {
			// TODO: handle exception
			parseintegerr=-1;
		}
		return parseintegerr;
	}
	public static JournalData[] load(Path path) throws IOException {
		List<JournalData> list = new ArrayList<JournalData>();
		
		
		try (InputStream in = Files.newInputStream(path);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in, StandardCharsets.ISO_8859_1))) {
			System.out.println("StandardCharsets working");
			String line = null;
			String[] FirstLineData=CleanALine(reader.readLine());
			while ((line = reader.readLine()) != null) {
				String[] CurrentLineData=CleanALine(line);
				HashMap<String,String> TempJournal=new HashMap<String,String>();
				IntStream.range(0, CurrentLineData.length-1).parallel().forEach(i -> PutInHash(TempJournal,FirstLineData,CurrentLineData,i) );
				JournalData item = processItem(TempJournal);
				list.add(item);
			}
		} catch (IOException x) {
			x.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		JournalData ret[] = new JournalData[list.size()];
		System.out.println("Returning Works");
		return list.toArray(ret);

	}

	private static JournalData processItem(HashMap<String, String> tempJournal) throws ParseException {
		JournalData ret = new JournalData();
		ret.setAuthor_firstname(tempJournal.get("FIRSTNAME"));
		ret.setAuthor_lastname(tempJournal.get("LASTNAME"));
		ret.setDoi(tempJournal.get("DOI"));
		ret.setFilename(tempJournal.get("FILENAME"));
		ret.setIs_referenced_by_count(NullIntParser(tempJournal.get("IS_REFERENCED_BY_COUNT")));
		ret.setIssue(NullIntParser(tempJournal.get("ISSUE")));
		ret.setIssue_date(StringtoDate(tempJournal.get("ISSUED_DATE")));
		ret.setPub_date(StringtoDate(tempJournal.get("PUBLISHED_DATE")));
		ret.setReference_count(NullIntParser(tempJournal.get("REFERENCE_COUNT")));
		ret.setSingle_funder(tempJournal.get("FUNDERNAME"));
		ret.setSingle_subject(tempJournal.get("SUBJECTNAME"));
		ret.setTitle(tempJournal.get("TITLE"));
		ret.setVolume(NullIntParser(tempJournal.get("VOLUME")));
		ret.setAuth_seq(tempJournal.get("SEQUENCE"));
		ret.setSingle_award(tempJournal.get("AWARD"));
		return ret;
	}
//	public static void main(String[] args) throws IOException {
////		List<String> readFirstData=readFirst(Paths.get("E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\yourfile.csv"),5 );
////		for(String asd: readFirstData) {
////			String tokens[] = CleanALine(asd);
////			System.out.println(Integer.toString(tokens.length)+" "+Arrays.toString(tokens)+"\n");
////		}
//		
//		JournalData[] loaddatas=load(Paths.get("E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\yourfile.csv"));
//		
//		for(JournalData asd: loaddatas) {
//			System.out.println(asd.PrintAll());
//		}
//	}

}
