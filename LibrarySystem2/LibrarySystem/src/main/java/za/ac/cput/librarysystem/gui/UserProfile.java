
package za.ac.cput.librarysystem.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import za.ac.cput.librarysystem.connection.Dbconnection;
import za.ac.cput.librarysystem.dao.BookDao;
import za.ac.cput.librarysystem.dao.UserDAO;
import za.ac.cput.librarysystem.domain.User;

public class UserProfile extends JFrame implements ActionListener {
    private JPanel panelNorth, panelWest, hamburger, panelSouth;
    private JPanel searchPanel;
    private JButton searchButton, openBtn,editBtn;
    private JTextField searchInput;
    private JLabel lblLabel1, lblLabel2, lblLabel3, lblLabel4, lblLabel5, lblLabel6, lblLabel7, lblLabel8, lblLabel9, lblLabel10, lblLabel11, lblLabel12, lblLabel13, lblLabel14;
    private JLabel lblName, lblBorrowedBooks;
    private String userEmail;
    
    private final JButton homeLink; //aboutUsLink //btnSign, closeBtn;
    private JButton profileLink, helpLink, logoutLink;
    
    public UserProfile(String userEmail) {
        this.userEmail = userEmail;
        
        panelNorth = new JPanel();
        panelWest = new JPanel();
        hamburger = new JPanel();
        
        panelSouth = new JPanel();

        searchInput = new JTextField(20);
        searchButton = new JButton("Search");
        openBtn = new JButton("☰");
        
        editBtn = new JButton("Edit Profile");
        
        homeLink = new JButton("Home");
        //aboutUsLink = new JButton("About Us");
        profileLink = new JButton("Profile");
        helpLink = new JButton("Help");
        logoutLink = new JButton("Log Out");

        lblLabel3 = new JLabel("NAME");
        lblLabel8 = new JLabel("");  // Will be updated with user name
        lblLabel4 = new JLabel("SURNAME");
        lblLabel9 = new JLabel("");  // Will be updated with user surname
        lblLabel5 = new JLabel("ADDRESS ");
        lblLabel10 = new JLabel("");  // Will be updated with user address
        lblLabel6 = new JLabel("EMAIL");
        lblLabel11 = new JLabel("");  // Will be updated with user email
        lblLabel7 = new JLabel("PHONE");
        lblLabel12 = new JLabel("");  // Will be updated with user phone
        lblLabel1 = new JLabel("BOOKS RESERVED");
        lblLabel13 = new JLabel("");  // For book reserved
        lblLabel2 = new JLabel("BOOKS BORROWED");
        lblLabel14 = new JLabel("");  // For books borrowed

        UserDAO userDao = new UserDAO();
        User user = userDao.getUserByEmail(userEmail);
        if (user != null) {
            lblLabel8.setText(user.getName());
            lblLabel9.setText(user.getSurname());
            lblLabel10.setText(user.getAddress());
            lblLabel11.setText(user.getEmail());
            lblLabel12.setText(user.getPhone());
            // Update reserved and borrowed book counts if applicable
            lblLabel13.setText("N/A");  // Placeholder for books reserved
            lblLabel14.setText("N/A");  // Placeholder for books borrowed
        } else {
            JOptionPane.showMessageDialog(null, "User not found");
        }
        
        fetchBorrowedBooks(userEmail); 
        
        //loadUserProfile();
        
        //setupGUI();
    }

    public void setupGUI() {
        setLayout(new BorderLayout());

        panelNorth.setLayout(new BorderLayout());
        panelNorth.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        panelNorth.setBackground(Color.WHITE);
        panelWest.setLayout(new GridLayout(7, 2));

        ImageIcon headerImageIcon = new ImageIcon("C:\\Users\\books\\logo.png");
        Image image = headerImageIcon.getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH);
        JLabel headerImageLabel = new JLabel(new ImageIcon(image));
        panelNorth.add(headerImageLabel, BorderLayout.WEST);

        searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchInput.setPreferredSize(new Dimension(200, 30));
        searchPanel.add(searchInput);
        searchPanel.add(searchButton);
        panelNorth.add(searchPanel, BorderLayout.CENTER);
        panelSouth.add(editBtn, BorderLayout.CENTER);

        panelWest.add(lblLabel3);
        panelWest.add(lblLabel8);
        panelWest.add(lblLabel4);
        panelWest.add(lblLabel9);
        panelWest.add(lblLabel5);
        panelWest.add(lblLabel10);
        panelWest.add(lblLabel6);
        panelWest.add(lblLabel11);
        panelWest.add(lblLabel7);
        panelWest.add(lblLabel12);
        panelWest.add(lblLabel1);
        panelWest.add(lblLabel13);
        panelWest.add(lblLabel2);
        panelWest.add(lblLabel14);

        openBtn.setPreferredSize(new Dimension(60, 55));
        panelNorth.add(openBtn, BorderLayout.EAST);

        hamburger.setLayout(new BoxLayout(hamburger, BoxLayout.Y_AXIS));
        hamburger.setBackground(Color.LIGHT_GRAY);
        hamburger.setPreferredSize(new Dimension(250, getHeight()));
        hamburger.setVisible(false);

        add(panelNorth, BorderLayout.NORTH);
        add(panelWest, BorderLayout.WEST);
        add(hamburger, BorderLayout.EAST);
        add(panelSouth, BorderLayout.SOUTH);
        
        homeLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        helpLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutLink.setAlignmentX(Component.CENTER_ALIGNMENT);

        //hamburger.add(closeBtn);
        hamburger.add(homeLink);
        hamburger.add(profileLink);
        hamburger.add(helpLink);
        hamburger.add(logoutLink);

        // Add other event listeners as necessary (e.g., searchButton, openBtn)
        homeLink.addActionListener(this);
        profileLink.addActionListener(this);
        helpLink.addActionListener(this);
        logoutLink.addActionListener(this);
        
        searchButton.addActionListener(this);
        openBtn.addActionListener(this);
        
         // Add other event listeners as necessary (e.g., searchButton, openBtn)
           editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisibleFrame(false);
                RunLibrarySystem.editGui();
            }
       
        });
    }
    public void setVisibleFrame(Boolean False) {
        this.setVisible(False);
    }
    
    void fetchBorrowedBooks(String userEmail) {  
      String query = "SELECT title FROM BorrowedBooks WHERE userEmail = ?";  
      try (Connection con = new Dbconnection().derbyConnection();  
         PreparedStatement psmt = con.prepareStatement(query)) {  
  
        psmt.setString(1, userEmail);  
  
        try (ResultSet rs = psmt.executeQuery()) {  
           StringBuilder borrowedBooks = new StringBuilder();  
           while (rs.next()) {  
              borrowedBooks.append(rs.getString("title")).append(", ");  
           }  
           lblLabel14.setText(borrowedBooks.toString());  
        }  
      } catch (SQLException e) {  
        JOptionPane.showMessageDialog(null, "Error fetching borrowed books: " + e.getMessage());  
      }  
   }  
    
   
void fetchReservedBooks(String userEmail) {  
   String query = "SELECT title FROM ReservedBooks WHERE userEmail = ?";  
   try (Connection con = new Dbconnection().derbyConnection();  
      PreparedStatement psmt = con.prepareStatement(query)) {  
  
      psmt.setString(1, userEmail);  
  
      try (ResultSet rs = psmt.executeQuery()) {  
        StringBuilder reservedBooks = new StringBuilder();  
        while (rs.next()) {  
           reservedBooks.append(rs.getString("title")).append(", ");  
        }  
        lblLabel13.setText(reservedBooks.toString());  
      }  
   } catch (SQLException e) {  
      JOptionPane.showMessageDialog(null, "Error fetching reserved books: " + e.getMessage());  
   }  
}
    
//    public void addReservedBook(String isbn) {  
//   // Get the book title from the database  
//   BookDao bookDao = new BookDao();  
//   String bookTitle = bookDao.getBookTitle(isbn);  
//  
//   // Add the book title to the "Books Reserved" label  
//   lblLabel13.setText(bookTitle);  
//}
     private void toggleHamburger() {
        hamburger.setVisible(!hamburger.isVisible());
    }
    @Override
public void actionPerformed(ActionEvent e) {
//            if(e.getSource() == editBtn){
//             this.setVisible(false); 
//                //RunLibrarySystem.editGui();
//        }
    if (e.getSource() == openBtn) {
        toggleHamburger();
    } else if (e.getSource() instanceof JButton clickedButton) {
        if (clickedButton.getText().equals("Home")) {
            try {
                RunLibrarySystem.HomeGui();
            } catch (SQLException ex) {
                Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
            }
            setVisible(false);
            }
         if (clickedButton == profileLink || clickedButton == helpLink) {
            if (clickedButton == profileLink) {
                //RunLibrarySystem.getUserProfile().setVisible(true);
                //RunLibrarySystem.getUserProfile("elijah@gmail.com").setVisible(true);
//                String userEmail = RunLibrarySystem.loginGui().txtUsername.getText();
//                RunLibrarySystem.getUserProfile(userEmail).setVisible(true);
RunLibrarySystem.getUserProfile(RunLibrarySystem.userEmail).setVisible(true);
            } else {
                RunLibrarySystem.getHelp().setVisible(true);
            }
        } else if (clickedButton == logoutLink) {
            int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to log out?",
                "Logout Confirmation",
                JOptionPane.OK_CANCEL_OPTION
            );
            if (result == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        }
    }
}

}


   