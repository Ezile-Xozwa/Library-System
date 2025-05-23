
package za.ac.cput.librarysystem.gui;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import za.ac.cput.librarysystem.connection.Dbconnection;
import za.ac.cput.librarysystem.dao.BookDao;

public class RunLibrarySystem {

    public static Login gui;
    public static SignUp guiObj;
    public static HomeBeforeLogin hm;
    public static Book book;
    public static BorrowBook bb;
    public static Reservation g;
    public static BorrowBook AvelaBorrowBookClass;

    public static UserProfile userProfile;
    public static Help help;

    public static AdminBookGui adminBook;
    
    public static String userEmail;
    
    public static ForgotPassword fp;

    public static void main(String[] args) {
        loginGui();
        //BookDao dao = new BookDao();  
   //dao.createReservedBooksTable();  
        //BookDao dao = new BookDao();  
      //dao.createBorrowedBooksTable();  
//        BookDao dao = new BookDao();
//        dao.createTable();
//        UserDAO dao1 = new UserDAO();
//        dao1.createTable();
    }

    public static Login loginGui() {

        gui = new Login();
        gui.pack();
        gui.setVisible(true);
        gui.setSize(400, 350);
        gui.setResizable(false);
        gui.setLocationRelativeTo(null);
        gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gui.setLoginGui();
        return gui;
    }

    public static SignUp signupGui() {

        guiObj = new SignUp();
        guiObj.pack();
        guiObj.setSize(1200, 800);
        guiObj.setResizable(false);
        guiObj.setVisible(true);
        guiObj.setDefaultCloseOperation(EXIT_ON_CLOSE);
        guiObj.setLocationRelativeTo(null);
        guiObj.setSignUp();
        return guiObj;
    }

    public static void bookGui(String isbn) {
        Book book = new Book();
        book.setVisible(true);

        if (bookField == null) {
            bookField = book;
        }

        book.pack();
        book.setSize(900, 600);
        book.setResizable(false);
        book.setDefaultCloseOperation(EXIT_ON_CLOSE);
        book.setLocationRelativeTo(null);
        book.setGui(isbn);
    }

    private static Book bookField;

//     public static Reservation reserveGui(String isbn) {
//        Reservation reservation = new Reservation(RunLibrarySystem.book, isbn);
//        reservation.setVisible(true);
//        return reservation;
//    }
     
     public static void reserve(String isbn) throws SQLException {  
         
         //bookDao.updateBookStatus(isbn, returnDate);  
         
   // Get the book title  
   BookDao bookDao = new BookDao();  
   String bookTitle = bookDao.getBookTitle(isbn);  
  
   // Insert a new row into the ReservedBooks table  
   String query = "INSERT INTO ReservedBooks (userEmail, title) VALUES (?, ?)";  
   try (Connection con = new Dbconnection().derbyConnection();  
      PreparedStatement psmt = con.prepareStatement(query)) {  
  
      psmt.setString(1, userEmail);  
      psmt.setString(2, bookTitle);  
  
      psmt.executeUpdate();  
   } catch (SQLException e) {  
      JOptionPane.showMessageDialog(null, "Error reserving book: " + e.getMessage());  
   }  
  
//   // Update the book's availability  
//   String returnDate = getReturnDate(isbn);  
//   bookDao.updateBookAvailability(isbn, returnDate);  
//  
//   // Create and display the ReserveBook GUI  
//   ReserveBook reserveBook = new ReserveBook(RunLibrarySystem.book, isbn);  
//   reserveBook.setVisible(true);  
  
   // Update the user's profile to show the reserved book  
   UserProfile userProfile = RunLibrarySystem.getUserProfile(userEmail);  
   userProfile.fetchReservedBooks(userEmail);  
   
   HomeGui();
}
     
     public static String getReturnDate(String isbn) {  
   String query = "SELECT returnDate FROM BorrowedBooks WHERE isbn = ?";  
   try (Connection con = new Dbconnection().derbyConnection();  
      PreparedStatement psmt = con.prepareStatement(query)) {  
  
      psmt.setString(1, isbn);  
  
      try (ResultSet rs = psmt.executeQuery()) {  
        if (rs.next()) {  
           return rs.getString("returnDate");  
        }  
      }  
   } catch (SQLException e) {  
      JOptionPane.showMessageDialog(null, "Error getting return date: " + e.getMessage());  
   }  
   return null;  
}


    public static HomeBeforeLogin HomeGui() throws SQLException {

        hm = new HomeBeforeLogin();
        hm.setTitle("Welcome Page");
        hm.setSize(1200, 800);
        hm.setResizable(false);
        hm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hm.setLocationRelativeTo(null);
        hm.setGui();
        hm.setVisible(true);
        return hm;
    }

    public static void borrow(String isbn) throws SQLException {
        // Get today's date as issue date
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String issueDate = dateFormat.format(today);

        // Calculate return date (14-day return period)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_YEAR, 14);
        String returnDate = dateFormat.format(calendar.getTime());
        
        //Update book status to "Not Available"  
   BookDao bookDao = new BookDao();  
   bookDao.updateBookStatus(isbn, "Not Available");
   
   // Get the book title  
   String bookTitle = bookDao.getBookTitle(isbn);

    
     String query = "INSERT INTO BorrowedBooks (userEmail, title) VALUES (?, ?)";  
      try (Connection con = new Dbconnection().derbyConnection();  
         PreparedStatement psmt = con.prepareStatement(query)) {  
  
        psmt.setString(1, userEmail);  
        psmt.setString(2, bookTitle);  
  
        psmt.executeUpdate();  
      } catch (SQLException e) {  
        JOptionPane.showMessageDialog(null, "Error borrowing book: " + e.getMessage());  
      }  
      
 BorrowBook borrowBook = new BorrowBook(RunLibrarySystem.book, isbn, issueDate, returnDate);
    borrowBook.setVisible(true);
    borrowBook.setVisible(true);  
   borrowBook.dispose(); // Dispose of the BorrowBook GUI  
   HomeGui();
    
       // Update the user's profile to show the borrowed book  
   UserProfile userProfile = RunLibrarySystem.getUserProfile(userEmail);  
   userProfile.fetchBorrowedBooks(userEmail);  


    }


    public static UserProfile getUserProfile(String userEmail) {
    if (userProfile == null) {
        userProfile = new UserProfile(userEmail);  // Pass the email argument here
        userProfile.setSize(1200, 800);
        userProfile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userProfile.setLocationRelativeTo(null);
        userProfile.setupGUI();
        userProfile.setVisible(true);
    }
    return userProfile;
}

    public static Help getHelp() {
        if (help == null) {
            help = new Help();
        }
        
        return help;
    }

    public static void adminBookGui() {
        adminBook = new AdminBookGui();
        adminBook.pack();
        adminBook.setGui();
        adminBook.setSize(900, 600);
        adminBook.setVisible(true);
        adminBook.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminBook.setLocationRelativeTo(null);
       
    }
    
    public static ForgotPassword forgotPasswordGui() {

        fp = new ForgotPassword();
        fp.pack();
        fp.setSize(600, 400);
        fp.setVisible(true);
        fp.setDefaultCloseOperation(EXIT_ON_CLOSE);
        fp.setLocationRelativeTo(null);
        fp.setGUI();
        return fp;
    }
    
    public static EditProfile editGui() {

       EditProfile edit = new EditProfile();
        edit.pack();
        edit.setVisible(true);
        edit.setSize(400, 350);
        edit.setResizable(false);
        edit.setLocationRelativeTo(null);
        edit.setDefaultCloseOperation(EXIT_ON_CLOSE);
        edit.setGui();
        return edit;
    }
}
