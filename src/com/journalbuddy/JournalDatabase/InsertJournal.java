package com.journalbuddy.JournalDatabase;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.io.FileUtils;
import com.opencsv.CSVWriter;


public class InsertJournal {
	
	public static void ExportToCSV(String databaseURL, String CSVPath) throws SQLException, IOException {
		Connection conn = DriverManager.getConnection(databaseURL);
		Statement insertstmt = conn.createStatement();

		System.out.println("Connection created");
		ResultSet rs = insertstmt.executeQuery("SELECT *   "
				+ "FROM journal, author, funder, subject, author_journal, funder_journal, subject_journal "
				+ "WHERE author.pk_author_id = author_journal.fk_author_id "
				+ "AND funder.pk_funder_id = funder_journal.fk_funder_id "
				+ "AND subject.pk_subject_id = subject_journal.fk_subject_id "
				+ "AND journal.filename = author_journal.fk_journal_id "
				+ "AND journal.filename = funder_journal.fk_journal_id "
				+ "AND journal.filename = subject_journal.fk_journal_id ");
		
		CSVWriter writer = new CSVWriter(new FileWriter(CSVPath));
		Boolean includeHeaders = true;
		writer.writeAll(rs, includeHeaders);
		writer.close();
	}
	
	public static void CreateTables(String URL) throws SQLException {
//		String URL="jdbc:derby:E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\db;create=true";
		Connection conn = DriverManager.getConnection(URL);
		Statement stmt = conn.createStatement();
		String query=new String();
		
		query = "CREATE TABLE author "
				+ "  ( "
				+ "     pk_author_id INTEGER PRIMARY KEY GENERATED always AS IDENTITY(START WITH 1, INCREMENT BY 1), "
				+ "     firstname    VARCHAR(255), "
				+ "     lastname     VARCHAR(255) "
				+ "  ) ";
		
		stmt.execute(query);
		System.out.println("Author Table created");
		
		query = "CREATE TABLE funder "
				+ "  ( "
				+ "     pk_funder_id INTEGER PRIMARY KEY GENERATED always AS IDENTITY(START WITH 1, INCREMENT BY 1), "
				+ "     fundername   VARCHAR(255) "
				+ "  ) ";
		
		stmt.execute(query);
		System.out.println("Funder Table created");
		
		query = "CREATE TABLE subject "
				+ "  ( "
				+ "     pk_subject_id INTEGER PRIMARY KEY GENERATED always AS IDENTITY(START WITH 1, INCREMENT BY 1), "
				+ "     subjectname   VARCHAR(255)  "
				+ "  ) ";
		stmt.execute(query);
		System.out.println("Subject Table created");
		
		query ="CREATE TABLE journal "
				+ "  ( "
				+ "     filename               VARCHAR(255) PRIMARY KEY, "
				+ "     doi                    VARCHAR(255), "
				+ "     title                  VARCHAR(255), "
				+ "     published_date         DATE, "
				+ "     containername          VARCHAR(255), "
				+ "     volume                 INT, "
				+ "     issue                  INT, "
				+ "     issued_date            DATE, "
				+ "     reference_count        INT, "
				+ "     is_referenced_by_count INT "
				+ "  ) ";
		
		stmt.execute(query);
		System.out.println("Journal Table created");
		
		query ="CREATE TABLE author_journal "
				+ "  ( "
				+ "     fk_author_id  INTEGER NOT NULL, "
				+ "     fk_journal_id VARCHAR(255) NOT NULL, "
				+ "     sequence      VARCHAR(255), "
				+ "     FOREIGN KEY (fk_author_id) REFERENCES author(pk_author_id), "
				+ "     FOREIGN KEY (fk_journal_id) REFERENCES journal(filename), "
				+ "     PRIMARY KEY (fk_author_id, fk_journal_id) "
				+ "  ) ";
		
		stmt.execute(query);
		System.out.println("Author_Journal Table created");
		
		query = "CREATE TABLE funder_journal "
				+ "  ( "
				+ "     fk_funder_id  INTEGER NOT NULL, "
				+ "     fk_journal_id VARCHAR(255) NOT NULL, "
				+ "     award         VARCHAR(255), "
				+ "     FOREIGN KEY (fk_funder_id) REFERENCES funder(pk_funder_id), "
				+ "     FOREIGN KEY (fk_journal_id) REFERENCES journal(filename), "
				+ "     PRIMARY KEY (fk_funder_id, fk_journal_id) "
				+ "  ) ";
		stmt.execute(query);
		System.out.println("funder_journal Table created");
		
		query = "CREATE TABLE subject_journal "
				+ "  ( "
				+ "     fk_subject_id INTEGER NOT NULL, "
				+ "     fk_journal_id VARCHAR(255) NOT NULL, "
				+ "     FOREIGN KEY (fk_subject_id) REFERENCES subject(pk_subject_id), "
				+ "     FOREIGN KEY (fk_journal_id) REFERENCES journal(filename), "
				+ "     PRIMARY KEY (fk_subject_id, fk_journal_id) "
				+ "  ) ";
		
		stmt.execute(query);
		System.out.println("funder_journal Table created");
	}
	
	public static void InsertJournalFun(String URL, JournalData journalclass) throws SQLException, ParseException {
//		String URL="jdbc:derby:E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\db;create=true";
		Connection conn = DriverManager.getConnection(URL);
		
		PreparedStatement insertstmt = conn.prepareStatement("INSERT INTO journal "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		
		insertstmt.setString(1, journalclass.getFilename());
		insertstmt.setString(2, journalclass.getDoi());
		insertstmt.setString(3, journalclass.getTitle());
		
		insertstmt.setString(5, journalclass.getContainer_name());
		insertstmt.setInt(6, journalclass.getVolume());
		insertstmt.setInt(7, journalclass.getIssue());
		
		insertstmt.setInt(9, journalclass.getReference_count());
		insertstmt.setInt(10, journalclass.getIs_referenced_by_count());
		
		String TempDate;
		
		if(journalclass.getIssue_year()!=(-1)) {
			if(journalclass.getIssue_month()!=(-1)) {
				TempDate=Integer.toString(journalclass.getIssue_year())+"-"+String.format("%02d", journalclass.getIssue_month())+"-01";
			} else {
				TempDate=Integer.toString(journalclass.getIssue_year())+"-01-01";
			}
		} else {
			TempDate=null;
		}
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd"); // New Pattern
		java.util.Date date = sdf1.parse(TempDate); // Returns a Date format object with the pattern
		java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
		insertstmt.setDate(8,  sqlStartDate);
		
		if(journalclass.getPub_year()!=(-1)) {
			if(journalclass.getPub_month()!=(-1)) {
				TempDate=Integer.toString(journalclass.getPub_year())+"-"+String.format("%02d", journalclass.getPub_month())+"-01";
			} else {
				TempDate=Integer.toString(journalclass.getPub_year())+"-01-01";
			}
		} else {
			TempDate=null;
		}
		
		sdf1 = new SimpleDateFormat("yyyy-mm-dd"); // New Pattern
		date = sdf1.parse(TempDate); // Returns a Date format object with the pattern
		sqlStartDate = new java.sql.Date(date.getTime());
		insertstmt.setDate(4,  sqlStartDate);
		insertstmt.execute();
		System.out.println("Normal Printing Completed");
		
		if(journalclass.getAuthors()!=null) {
			for(HashMap<String, String> authormap: journalclass.getAuthors()) {
				insertstmt = conn.prepareStatement("INSERT INTO author(firstname, lastname) "
						+ "VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
				insertstmt.setString(1, authormap.get("given"));
				insertstmt.setString(2, authormap.get("family"));
				insertstmt.execute();
				System.out.println("second Printing Completed");
				ResultSet rs = insertstmt.getGeneratedKeys();
				rs.next();
				int auto_id = rs.getInt(1);
				insertstmt = conn.prepareStatement("INSERT INTO author_journal "
						+ "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				insertstmt.setInt(1, auto_id);
				insertstmt.setString(2, journalclass.getFilename());
				insertstmt.setString(3, authormap.get("sequence"));
				insertstmt.execute();
				
			}
		}
		
		if(journalclass.getSubjects()!=null){
			for(String subslistval: journalclass.getSubjects()) {
				insertstmt = conn.prepareStatement("INSERT INTO subject(subjectname) "
						+ "VALUES (?)", Statement.RETURN_GENERATED_KEYS);
				insertstmt.setString(1, subslistval);
				insertstmt.execute();
				System.out.println("subslistval Printing Completed");
				ResultSet rs = insertstmt.getGeneratedKeys();
				rs.next();
				int auto_id = rs.getInt(1);
				insertstmt = conn.prepareStatement("INSERT INTO subject_journal "
						+ "VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
				insertstmt.setInt(1, auto_id);
				insertstmt.setString(2, journalclass.getFilename());
				insertstmt.execute();
				
			}
		}
		
		if(journalclass.getFunders()!=null) {
			for(HashMap<String, String> fundermap: journalclass.getFunders()) {
				insertstmt = conn.prepareStatement("INSERT INTO funder(fundername) "
						+ "VALUES (?)", Statement.RETURN_GENERATED_KEYS);
				insertstmt.setString(1, fundermap.get("funder"));
				insertstmt.execute();
				System.out.println("fundermap Printing Completed");
				ResultSet rs = insertstmt.getGeneratedKeys();
				rs.next();
				int auto_id = rs.getInt(1);
				insertstmt = conn.prepareStatement("INSERT INTO funder_journal "
						+ "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				insertstmt.setInt(1, auto_id);
				insertstmt.setString(2, journalclass.getFilename());
				insertstmt.setString(3, fundermap.get("award5"));
				insertstmt.execute();
				
			}
		}


		
	}
	public static void dummyInserter(String[] args) throws Exception {
		FileUtils.deleteDirectory(new File("E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\db"));
		String DatabaseURL="jdbc:derby:E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\db;create=true";
		CreateTables(DatabaseURL);
		List<String> dummysubs = new ArrayList<String>();
		dummysubs.add("asd");
		dummysubs.add("qwe");
		dummysubs.add("wer");
		
		HashMap<String, String> dummyfunders1=new HashMap<String, String>();
		dummyfunders1.put("funder", "qwe");
		dummyfunders1.put("award", "fgh");
		
		HashMap<String, String> dummyfunders2=new HashMap<String, String>();
		dummyfunders1.put("funder", "xcv");
		dummyfunders1.put("award", "fgcvbh");
		
		HashMap<String, String> dummyfunders3=new HashMap<String, String>();
		dummyfunders1.put("funder", "vbn");
		dummyfunders1.put("award", "bnm");
		
		List<HashMap<String, String>> finaldummyfunders=new ArrayList<HashMap<String, String>>();
		finaldummyfunders.add(dummyfunders3);
		finaldummyfunders.add(dummyfunders2);
		finaldummyfunders.add(dummyfunders1);
		
		HashMap<String, String> dummyhashauthor =new HashMap<String, String>();
		HashMap<String, String> dummyhashauthor2 =new HashMap<String, String>();
		List<HashMap<String, String>> dummyauthors=new ArrayList<HashMap<String, String>>();
		dummyhashauthor.put("given", "asd");
		dummyhashauthor.put("family", "qwe");
		dummyhashauthor.put("sequence", "sdf");
		dummyauthors.add(dummyhashauthor);
		
		dummyhashauthor2.put("given", "ert");
		dummyhashauthor2.put("family", "rty");
		dummyhashauthor2.put("sequence", "tyu");
		dummyauthors.add(dummyhashauthor2);
		
		System.out.println(dummyauthors.toString()+ "\n");
		System.out.println(finaldummyfunders.toString()+ "\n");
		System.out.println(dummysubs.toString()+ "\n");
		
		JournalData journalclass=new JournalData();
		journalclass.setDoi("asd");
		journalclass.setFilename("ytuuyiu");
		journalclass.setIs_referenced_by_count(23);
		journalclass.setIssue(34);
		journalclass.setIssue_month(6);
		journalclass.setIssue_year(2018);
		journalclass.setPub_month(5);
		journalclass.setPub_year(2016);
		journalclass.setReference_count(67);
		journalclass.setTitle("asd");
		journalclass.setVolume(78);
		journalclass.setContainer_name("asd");
		journalclass.setAuthors(dummyauthors);
		journalclass.setFunders(finaldummyfunders);
		journalclass.setSubjects(dummysubs);
		InsertJournalFun(DatabaseURL, journalclass);
		
		
		String URL="jdbc:derby:E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\db";//create=true";
		Connection conn = DriverManager.getConnection(URL);
		Statement insertstmt = conn.createStatement();

		System.out.println("Connection created");
//		ResultSet rs = insertstmt.executeQuery("SELECT * "
//				+ "FROM journal, author, funder, subject, author_journal, funder_journal, subject_journal "
//				+ "WHERE author.pk_author_id = author_journal.fk_author_id "
//				+ "AND funder.pk_funder_id = funder_journal.fk_funder_id "
//				+ "AND subject.pk_subject_id = subject_journal.fk_subject_id");
//
//		
//	
//		ResultSetMetaData rsmd = rs.getMetaData();
//		int columnsNumber = rsmd.getColumnCount();                     
//		while (rs.next()) {
//		for(int i = 1 ; i <= columnsNumber; i++){
//		      System.out.print(rs.getString(i) + " ");
//		}
//		  System.out.println();//Move to the next line to print the next row.           
//
//		    }
		
//		stmt.execute(query, Statement.RETURN_GENERATED_KEYS);
//		ResultSet keys = stmt.getGeneratedKeys();
//		keys.next();
//		System.out.print(keys.getString(1));
//		ResultSetMetaData rsmd = keys.getMetaData();
//		int columnsNumber = rsmd.getColumnCount();
//		while (keys.next()) {
//		    for (int i = 1; i <= columnsNumber; i++) {
//		        if (i > 1) System.out.print(",  ");
//		        String columnValue = keys.getString(i);
//		        System.out.print(columnValue + " " + rsmd.getColumnName(i));
//		    }
//		    System.out.println("");
//		}
		
	}

}
