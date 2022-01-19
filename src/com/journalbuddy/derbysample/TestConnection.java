package com.journalbuddy.derbysample;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


public class TestConnection {
	
	public static void main(String[] args) throws Exception {
		String URL="jdbc:derby:E:\\BOOKS DUMP\\JAVA\\Parallel\\MainProjects\\db;create=true";
		Connection conn = DriverManager.getConnection(URL);
		Statement stmt = conn.createStatement();
		String query=new String();
//		query = "CREATE TABLE Author ("
//				+ "Pk_author_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1), "
//				+ "Name varchar(255),"
//				+ "Country varchar(255)"
//				+ ")";
//		stmt.execute(query);
//		System.out.println("Table created");
//		
//		query ="CREATE TABLE Book ("
//				+ "Pk_book_Id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1), "
//				+ "Title varchar(255)"
//				+ ")";
//		stmt.execute(query);
//		System.out.println("Table created");
//		
//		query ="CREATE TABLE Author_Book ("
//				+ "   Fk_author_id INTEGER NOT NULL,"
//				+ "   Fk_book_id INTEGER NOT NULL,"
//				+ "   Extra_value varchar(255),"
//				+ "   FOREIGN KEY (Fk_author_id) REFERENCES Author(Pk_author_id),"
//				+ "   FOREIGN KEY (Fk_book_id) REFERENCES Book(Pk_book_Id),"
//				+ "   PRIMARY KEY (Fk_author_id, Fk_book_id)"
//				+ ")";
//		stmt.execute(query);
//		System.out.println("Table created");
		
		query="INSERT INTO Author ( name, country)"
				+ "VALUES ('Larry Bird', 'USA')";
		stmt.execute(query, Statement.RETURN_GENERATED_KEYS);
		ResultSet keys = stmt.getGeneratedKeys();
		keys.next();
		System.out.print(keys.getString(1));
		ResultSetMetaData rsmd = keys.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (keys.next()) {
		    for (int i = 1; i <= columnsNumber; i++) {
		        if (i > 1) System.out.print(",  ");
		        String columnValue = keys.getString(i);
		        System.out.print(columnValue + " " + rsmd.getColumnName(i));
		    }
		    System.out.println("");
		}
		System.out.println("Table Inserted");
//		query= "INSERT INTO Book (Title)"
//				+ "VALUES ('Drive')";
//		stmt.execute(query);
//		System.out.println("Table Inserted");
		
//		query="INSERT INTO Author (Pk_author_id, name, country)"
//				+ "VALUES (2, 'Magic Johnson', 'USA')";
//		stmt.execute(query);
//		System.out.println("Table Inserted");
//		query= "INSERT INTO Book (Pk_book_Id, Title)"
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
//				+ "AND Book.Pk_book_Id = Author_Book.Fk_book_id";
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
