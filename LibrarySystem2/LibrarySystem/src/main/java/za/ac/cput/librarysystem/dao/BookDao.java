
package za.ac.cput.librarysystem.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import za.ac.cput.librarysystem.connection.Dbconnection;
import za.ac.cput.librarysystem.gui.RunLibrarySystem;

public class BookDao {

    private Connection con;
     private Statement s; 
     
    public BookDao() {
        con = new Dbconnection().derbyConnection(); // Use your existing DbConnection class
    }
    
    public void createTable() {  
      String createTableStmt = "CREATE TABLE Books ("  
        + "bookId INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"  
        + "isbn VARCHAR(20) PRIMARY KEY,"  
        + "title VARCHAR(100) NOT NULL,"  
        + "author VARCHAR(100) NOT NULL,"  
        + "genre VARCHAR(100) NOT NULL,"  
        + "description VARCHAR(255),"  
        + "date VARCHAR(20),"  
        + "rating VARCHAR(10),"  
        + "image BLOB,"  
        + "status VARCHAR(20))";  
  
      try {  
        s = con.createStatement();  
        s.executeUpdate(createTableStmt);  
        JOptionPane.showMessageDialog(null, "Books table created successfully...");  
      } catch (SQLException ex) {  
        JOptionPane.showMessageDialog(null, "ERROR!!!!!");  
      }  
   }  

    public void insertBook(String title, String author, String genre, String isbn, String description, String date, String rating, InputStream imageStream, String status) {
        String sql = "INSERT INTO Books (title, author, genre, isbn, description, date, rating, image, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement psmt = con.prepareStatement(sql)) {
            psmt.setString(1, title);
            psmt.setString(2, author);
            psmt.setString(3, genre);
            psmt.setString(4, isbn);
            psmt.setString(5, description); // New parameter
            psmt.setString(6, date); // New parameter
            psmt.setString(7, rating); // New parameter
            psmt.setBlob(8, imageStream);
            psmt.setString(9, status); // Here you pass "Available" or "Not Available"
            psmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Book added successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding book: " + e.getMessage());
        }
    }

    public void updateBook(String title, String author, String genre, String isbn, String description, String date, String rating, String status, String oldIsbn) {
        String sql = "UPDATE Books SET title = ?, author = ?, genre = ?, isbn = ?, description = ?, date = ?, rating = ? WHERE isbn = ?";
        try (PreparedStatement psmt = con.prepareStatement(sql)) {
            psmt.setString(1, title);
            psmt.setString(2, author);
            psmt.setString(3, genre);
            psmt.setString(4, isbn);
            psmt.setString(5, description); // New parameter
            psmt.setString(6, date); // New parameter
            psmt.setString(7, rating); // New parameter
            psmt.setString(8, status);
            psmt.setString(9, oldIsbn);
            psmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Book updated successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating book: " + e.getMessage());
        }
    }

    public void deleteBook(String isbn) {
        String sql = "DELETE FROM Books WHERE isbn = ?";
        try (PreparedStatement psmt = con.prepareStatement(sql)) {
            psmt.setString(1, isbn);
            psmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Book deleted successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting book: " + e.getMessage());
        }
    }
    
        public void updateBookStatus(String isbn, String status) {  
   String sql = "UPDATE Books SET status = ? WHERE isbn = ?";  
   try (PreparedStatement psmt = con.prepareStatement(sql)) {  
      psmt.setString(1, status);  
      psmt.setString(2, isbn);  
      psmt.executeUpdate();  
      con.commit(); // Commit the changes
   } catch (SQLException e) {  
      JOptionPane.showMessageDialog(null, "Error updating book status: " + e.getMessage());  
   }  
}
        public String getBookTitle(String isbn) {  
   String sql = "SELECT title FROM Books WHERE isbn = ?";  
   try (PreparedStatement psmt = con.prepareStatement(sql)) {  
      psmt.setString(1, isbn);  
      try (ResultSet rs = psmt.executeQuery()) {  
        if (rs.next()) {  
           return rs.getString("title");  
        }  
      }  
   } catch (SQLException e) {  
      JOptionPane.showMessageDialog(null, "Error getting book title: " + e.getMessage());  
   }  
   return null;  
}
        
        public void createReservedBooksTable() {  
   String createTableStmt = "CREATE TABLE ReservedBooks ("   
      + "id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"   
      + "userEmail VARCHAR(100) NOT NULL,"   
      + "title VARCHAR(100) NOT NULL)";  
    
   try (Statement s = con.createStatement()) {  
      s.executeUpdate(createTableStmt);  
      JOptionPane.showMessageDialog(null, "ReservedBooks table created successfully...");  
   } catch (SQLException ex) {  
      JOptionPane.showMessageDialog(null, "ERROR!!!!!");  
   }  
}
        
        public void createBorrowedBooksTable() {  
      String createTableStmt = "CREATE TABLE BorrowedBooks ("   
        + "id INT GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"   
        + "userEmail VARCHAR(100) NOT NULL,"   
        + "title VARCHAR(100) NOT NULL)";  
       
      try (Statement s = con.createStatement()) {  
        s.executeUpdate(createTableStmt);  
        JOptionPane.showMessageDialog(null, "BorrowedBooks table created successfully...");  
      } catch (SQLException ex) {  
        JOptionPane.showMessageDialog(null, "ERROR!!!!!");  
      }  
   }  
        
        public void updateBookAvailability(String isbn, String availability) {  
   String sql = "UPDATE Books SET availability = ? WHERE isbn = ?";  
   try (PreparedStatement psmt = con.prepareStatement(sql)) {  
      psmt.setString(1, availability);  
      psmt.setString(2, isbn);  
      psmt.executeUpdate();  
   } catch (SQLException e) {  
      JOptionPane.showMessageDialog(null, "Error updating book availability: " + e.getMessage());  
   }  
}

}


