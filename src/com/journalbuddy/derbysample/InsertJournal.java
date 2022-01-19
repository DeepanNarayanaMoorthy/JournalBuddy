package com.journalbuddy.derbysample;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class InsertJournal {
	
	public static void CreateTables() throws SQLException {
		String URL="jdbc:derby:E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\db;create=true";
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
				+ "     fundername VARCHAR(255) PRIMARY KEY"
				+ "  ) ";
		
		stmt.execute(query);
		System.out.println("Funder Table created");
		
		query = "CREATE TABLE subject "
				+ "  ( "
				+ "     subjectname VARCHAR(255) PRIMARY KEY "
				+ "  ) ";
		stmt.execute(query);
		System.out.println("Subject Table created");
		
		query ="CREATE TABLE journal "
				+ "  ( "
				+ "     filename               VARCHAR(255) PRIMARY KEY, "
				+ "     doi                    VARCHAR(255), "
				+ "     title                  VARCHAR(255), "
				+ "     subtitle               VARCHAR(255), "
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
				+ "     fk_funder_id  VARCHAR(255) NOT NULL, "
				+ "     fk_journal_id VARCHAR(255) NOT NULL, "
				+ "     award         VARCHAR(255), "
				+ "     FOREIGN KEY (fk_funder_id) REFERENCES funder(fundername), "
				+ "     FOREIGN KEY (fk_journal_id) REFERENCES journal(filename), "
				+ "     PRIMARY KEY (fk_funder_id, fk_journal_id) "
				+ "  ) ";
		stmt.execute(query);
		System.out.println("funder_journal Table created");
		
		query = "CREATE TABLE subject_journal "
				+ "  ( "
				+ "     fk_subject_id VARCHAR(255) NOT NULL, "
				+ "     fk_journal_id VARCHAR(255) NOT NULL, "
				+ "     FOREIGN KEY (fk_subject_id) REFERENCES subject(subjectname), "
				+ "     FOREIGN KEY (fk_journal_id) REFERENCES journal(filename), "
				+ "     PRIMARY KEY (fk_subject_id, fk_journal_id) "
				+ "  ) ";
		
		stmt.execute(query);
		System.out.println("funder_journal Table created");
	}
	public static void main(String[] args) throws Exception {
		CreateTables();
//		String URL="jdbc:derby:E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\db;create=true";
//		Connection conn = DriverManager.getConnection(URL);
//		Statement stmt = conn.createStatement();
//		String query=new String();
//		
//		query="INSERT INTO Author ( name, country)"
//				+ "VALUES ('Larry Bird', 'USA')";
//		stmt.execute(query, Statement.RETURN_GENERATED_KEYS);
//		ResultSet keys = stmt.getGeneratedKeys();
//		keys.next();
//		System.out.print(keys.getString(1));
		
//		query= "INSERT INTO Book (Title)"
//				+ "VALUES ('Drive')";
//		stmt.execute(query);
//		System.out.println("Table Inserted");
		
//		query="INSERT INTO Author (Pk_author_id, name, country)"
//				+ "VALUES (2, 'Magic Johnson', 'USA')";
//		stmt.execute(query);
//		System.out.println("Table Inserted");
//		query= "INSERT INTO Book (Pk_journal_Id, Title)"
//				+ "VALUES (1, 'When the game was ours')";
//		stmt.execute(query);
//		System.out.println("Table Inserted");
//		
//		
//		query="INSERT INTO Author_Book(Fk_author_id, Fk_book_id) VALUES (1, 1)";
//		stmt.execute(query);
//		System.out.println("Table Inserted");
//		query="INSERT INTO Author_Book(Fk_author_id, Fk_book_id) VALUES (2, 1)";
//		stmt.execute(query);
//		System.out.println("Table Inserted");
//		query="INSERT INTO Author_Book(Fk_author_id, Fk_book_id) VALUES (1, 2)";
//		stmt.execute(query);
//		System.out.println("Table Inserted");
		
//		query="SELECT Author.Name, Book.Title "
//				+ "FROM Author, Book, Author_Book "
//				+ "WHERE Author.Pk_author_id = Author_Book.Fk_author_id "
//				+ "AND Book.Pk_journal_Id = Author_Book.Fk_book_id";
//		stmt.execute(query);
//		System.out.println("Table Table Fetched");
//	
//		ResultSet rs = stmt.executeQuery(query);
//		ResultSetMetaData rsmd = rs.getMetaData();
//		String name = rsmd.getColumnName(1);
//		System.out.println(name);
//		name = rsmd.getColumnName(2);
//		System.out.println(name);
//		
//		while(rs.next()) {
//			System.out.println("NAME: "+rs.getString("NAME"));
//			System.out.println("TITLE: "+rs.getString("TITLE"));
//			System.out.println(" ");
//		}
		
	}

}
