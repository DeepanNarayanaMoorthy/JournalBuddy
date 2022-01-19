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
//		String query = "CREATE TABLE Author ("
//				+ "Pk_author_id INTEGER PRIMARY KEY,"
//				+ "Name varchar(255),"
//				+ "Country varchar(255)"
//				+ ")";
//		stmt.execute(query);
//		System.out.println("Table created");
//		
//		query ="CREATE TABLE Book ("
//				+ "Pk_book_Id INTEGER PRIMARY KEY,"
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
		
//		query="INSERT INTO Author (Pk_author_id, name, country)"
//				+ "VALUES (1, 'Larry Bird', 'USA')";
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
//		query= "INSERT INTO Book (Pk_book_Id, Title)"
//				+ "VALUES (2, 'Drive')";
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
		
		query="SELECT Author.Name, Book.Title "
				+ "FROM Author, Book, Author_Book "
				+ "WHERE Author.Pk_author_id = Author_Book.Fk_author_id "
				+ "AND Book.Pk_book_Id = Author_Book.Fk_book_id";
		stmt.execute(query);
		System.out.println("Table Table Fetched");
	
		ResultSet rs = stmt.executeQuery(query);
		ResultSetMetaData rsmd = rs.getMetaData();
		String name = rsmd.getColumnName(1);
		System.out.println(name);
		name = rsmd.getColumnName(2);
		System.out.println(name);
		
		while(rs.next()) {
			System.out.println("NAME: "+rs.getString("NAME"));
			System.out.println("TITLE: "+rs.getString("TITLE"));
			System.out.println(" ");
		}
		

//	       query = "INSERT INTO Employees("
//	    	         + "Name, Salary, Location) VALUES "
//	    	         + "('Amit', 30000, 'Hyderabad'), "
//	    	         + "('Kalyan', 40000, 'Vishakhapatnam'), "
//	    	         + "('Renuka', 50000, 'Delhi'), "
//	    	         + "('Archana', 15000, 'Mumbai'), "
//	    	         + "('Trupthi', 45000, 'Kochin'), "
//	    	         + "('Suchatra', 33000, 'Pune'), "
//	    	         + "('Rahul', 39000, 'Lucknow'), "
//	    	         + "('Trupti', 45000, 'Kochin')";
//	    	      stmt.execute(query);
//	    	      System.out.println("Values inserted");
		
//		 String query = "SELECT Id, Name, Salary FROM Employees";
//	      ResultSet rs = stmt.executeQuery(query);
//	      while(rs.next()) {
//	         System.out.println("Id: "+rs.getString("Id"));
//	         System.out.println("Name: "+rs.getString("Name"));
//	         System.out.println("Salary: "+rs.getString("Salary"));
//	         System.out.println(" ");

//	}
	}

}
