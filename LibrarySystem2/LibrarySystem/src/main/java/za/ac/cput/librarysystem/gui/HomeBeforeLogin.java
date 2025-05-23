package za.ac.cput.librarysystem.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import za.ac.cput.librarysystem.connection.Dbconnection;
import static za.ac.cput.librarysystem.gui.RunLibrarySystem.book;

public class HomeBeforeLogin extends JFrame implements ActionListener {

    private final JButton cmbCombo0, cmbCombo1, cmbCombo2, cmbCombo3, cmbCombo4, cmbCombo5, cmbCombo6, cmbCombo7, cmbCombo8, cmbCombo9, cmbCombo10, cmbCombo11, cmbCombo12, cmbCombo13, cmbCombo14;
    private final JLabel titleLbl1, titleLbl2, titleLbl3, lblImage0, lblImage1, lblImage2, lblImage3, lblImage4, lblImage5, lblImage6, lblImage7, lblImage8, lblImage9, lblImage10, lblImage11, lblImage12, lblImage13, lblImage14;
    private JPanel panelNorth, hamburger, panelCenter; 
    private JPanel searchPanel;
    private final JTextField searchInput;
    private final JButton searchButton, openBtn;
    private final JButton homeLink; //aboutUsLink //btnSign, closeBtn;
    private JButton profileLink, helpLink, logoutLink;
    
//    private JLabel lblImage20, lblImage21, lblImage22, lblImage23, lblImage24;
//    private final JButton cmbCombo20, cmbCombo21, cmbCombo22, cmbCombo23, cmbCombo24;
//    
//    private JLabel titleLbl4;
    
    //private final JLabel titleLabel0, titleLabel1, titleLabel2, titleLabel3, titleLabel4, titleLabel5, titleLabel6, titleLabel7, titleLabel8, titleLabel9, titleLabel10, titleLabel11, titleLabel12, titleLabel13, titleLabel14;
    

    // Database connection object (ensure it's initialized elsewhere)
    //private Connection conn;

    public HomeBeforeLogin() throws SQLException {
        //this.conn = conn;  // Pass connection when constructing the Homegui

        panelNorth = new JPanel();
        hamburger = new JPanel();
        panelCenter = new JPanel();
        searchInput = new JTextField(20);
        searchButton = new JButton("Search");
        openBtn = new JButton("☰");
        
        homeLink = new JButton("Home");
        //aboutUsLink = new JButton("About Us");
        profileLink = new JButton("Profile");
        helpLink = new JButton("Help");
        logoutLink = new JButton("Log Out");
        
        titleLbl1 = new JLabel("THRILLER");
        titleLbl2 = new JLabel("HORROR");
        titleLbl3 = new JLabel("ROMANCE");
        //titleLbl4 = new JLabel("ACTION");

        // Initialize labels (images will be fetched from the database)
        lblImage0 = new JLabel();
        //titleLabel0 = new JLabel();
        cmbCombo0 = new JButton("Preview");
        cmbCombo0.setPreferredSize(new Dimension(150, 25));

        lblImage1 = new JLabel();
        //titleLabel1 = new JLabel();
        cmbCombo1 = new JButton("Preview");
        cmbCombo1.setPreferredSize(new Dimension(150, 25));

        lblImage2 = new JLabel();
        //titleLabel2 = new JLabel();
        cmbCombo2 = new JButton("Preview");
        cmbCombo2.setPreferredSize(new Dimension(150, 25));

        lblImage3 = new JLabel();
        //titleLabel3 = new JLabel();
        cmbCombo3 = new JButton("Preview");
        cmbCombo3.setPreferredSize(new Dimension(150, 25));

        lblImage4 = new JLabel();
        //titleLabel4 = new JLabel();
        cmbCombo4 = new JButton("Preview");
        cmbCombo4.setPreferredSize(new Dimension(150, 25));

        lblImage5 = new JLabel();
        //titleLabel5 = new JLabel();
        cmbCombo5 = new JButton("Preview");
        cmbCombo5.setPreferredSize(new Dimension(150, 25));

        lblImage6 = new JLabel();
        //titleLabel6 = new JLabel();
        cmbCombo6 = new JButton("Preview");
        cmbCombo6.setPreferredSize(new Dimension(150, 25));

        lblImage7 = new JLabel();
        //titleLabel7 = new JLabel();
        cmbCombo7 = new JButton("Preview");
        cmbCombo7.setPreferredSize(new Dimension(150, 25));

        lblImage8 = new JLabel();
        //titleLabel8 = new JLabel();
        cmbCombo8 = new JButton("Preview");
        cmbCombo8.setPreferredSize(new Dimension(150, 25));

        lblImage9 = new JLabel();
        //titleLabel9 = new JLabel();
        cmbCombo9 = new JButton("Preview");
        cmbCombo9.setPreferredSize(new Dimension(150, 25));

        lblImage10 = new JLabel();
        //titleLabel10 = new JLabel();
        cmbCombo10 = new JButton("Preview");
        cmbCombo10.setPreferredSize(new Dimension(150, 25));

        lblImage11 = new JLabel();
        //titleLabel11 = new JLabel();
        cmbCombo11 = new JButton("Preview");
        cmbCombo11.setPreferredSize(new Dimension(150, 25));

        lblImage12 = new JLabel();
        //titleLabel12 = new JLabel();
        cmbCombo12 = new JButton("Preview");
        cmbCombo12.setPreferredSize(new Dimension(150, 25));

        lblImage13 = new JLabel();
        //titleLabel13 = new JLabel();
        cmbCombo13 = new JButton("Preview");
        cmbCombo13.setPreferredSize(new Dimension(150, 25));

        lblImage14 = new JLabel();
        //titleLabel14 = new JLabel();
        cmbCombo14 = new JButton("Preview");
        cmbCombo14.setPreferredSize(new Dimension(150, 25));
        
//        lblImage20 = new JLabel();
//        //titleLabel14 = new JLabel();
//        cmbCombo20 = new JButton("Preview");
//        cmbCombo20.setPreferredSize(new Dimension(150, 25));
//        
//        lblImage21 = new JLabel();
//        //titleLabel14 = new JLabel();
//        cmbCombo21 = new JButton("Preview");
//        cmbCombo21.setPreferredSize(new Dimension(150, 25));
//        
//        lblImage22 = new JLabel();
//        //titleLabel14 = new JLabel();
//        cmbCombo22 = new JButton("Preview");
//        cmbCombo22.setPreferredSize(new Dimension(150, 25));
//        
//        lblImage23 = new JLabel();
//        //titleLabel14 = new JLabel();
//        cmbCombo23 = new JButton("Preview");
//        cmbCombo23.setPreferredSize(new Dimension(150, 25));
//        
//        lblImage24 = new JLabel();
//        //titleLabel14 = new JLabel();
//        cmbCombo24 = new JButton("Preview");
//        cmbCombo24.setPreferredSize(new Dimension(150, 25));

        fetchBookData();
        //setGui();
        
        
        
    }
   
    
    private void fetchBookData() throws SQLException {
    String query = "SELECT title, image, isbn FROM (SELECT title, image, isbn FROM books FETCH FIRST 15 ROWS ONLY) AS sub";
    
    try (Connection con = new Dbconnection().derbyConnection();
         PreparedStatement psmt = con.prepareStatement(query);
         ResultSet rs = psmt.executeQuery()) {

        JLabel[] imageLabels = {lblImage0, lblImage1, lblImage2, lblImage3, lblImage4, lblImage5, lblImage6, lblImage7, lblImage8, lblImage9, lblImage10, lblImage11, lblImage12, lblImage13, lblImage14};
        //lblImage21, lblImage20, lblImage22, lblImage23, lblImage24};
        //JLabel[] titleLabels = {titleLabel0, titleLabel1, titleLabel2, titleLabel3, titleLabel4, titleLabel5, titleLabel6, titleLabel7, titleLabel8, titleLabel9, titleLabel10, titleLabel11, titleLabel12, titleLabel13, titleLabel14};
        JButton[] previewButtons = {cmbCombo0, cmbCombo1, cmbCombo2, cmbCombo3, cmbCombo4, cmbCombo5, cmbCombo6, cmbCombo7, cmbCombo8, cmbCombo9, cmbCombo10, cmbCombo11, cmbCombo12, cmbCombo13, cmbCombo14};
        //cmbCombo20, cmbCombo21, cmbCombo22, cmbCombo23, cmbCombo24};

        int index = 0;
        while (rs.next() && index < imageLabels.length) {
            String title = rs.getString("title");
            Blob imageBlob = rs.getBlob("image");
            String isbn = rs.getString("isbn");  // Assuming you have an isbn or unique identifier

            // Convert Blob to ImageIcon
            ImageIcon bookImage = getImageIconFromBlob(imageBlob);
            if (bookImage != null) {
                imageLabels[index].setIcon(new ImageIcon(bookImage.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
            }
            
            //titleLabels[index].setText(title);  
      previewButtons[index].setActionCommand(isbn);  // Set the ISBN as the action command  
      previewButtons[index].addActionListener(this);  // Add the action listener  
  
      index++;  
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

 
    // Helper method to convert Blob to ImageIcon
    // Helper method to convert Blob to ImageIcon
    private ImageIcon getImageIconFromBlob(Blob imageBlob) {
        try {
            if (imageBlob != null) {
                byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
                return new ImageIcon(imageBytes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setGui() {
        setLayout(new BorderLayout());
        
        panelNorth.setLayout(new BorderLayout());
        panelNorth.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        panelNorth.setBackground(Color.WHITE);
        
        ImageIcon headerImageIcon = new ImageIcon("C:\\Users\\books\\logo.png");
        Image image = headerImageIcon.getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH);
        JLabel headerImageLabel = new JLabel(new ImageIcon(image));
        panelNorth.add(headerImageLabel, BorderLayout.WEST);

        searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchInput.setPreferredSize(new Dimension(200, 30));
        searchPanel.add(searchInput);
        searchPanel.add(searchButton);
        panelNorth.add(searchPanel, BorderLayout.CENTER);

        openBtn.setPreferredSize(new Dimension(60, 55));
        panelNorth.add(openBtn, BorderLayout.EAST);
              
        hamburger.setLayout(new BoxLayout(hamburger, BoxLayout.Y_AXIS));
        hamburger.setBackground(Color.LIGHT_GRAY);
        hamburger.setPreferredSize(new Dimension(250, getHeight()));
        hamburger.setVisible(false);

        panelCenter.setLayout(new GridLayout(3, 6, 10, 10));
        panelCenter.add(titleLbl1);
        panelCenter.add(createImagePanel(lblImage0, cmbCombo0));
        panelCenter.add(createImagePanel(lblImage1, cmbCombo1));
        panelCenter.add(createImagePanel(lblImage2, cmbCombo2));
        panelCenter.add(createImagePanel(lblImage3, cmbCombo3));
        panelCenter.add(createImagePanel(lblImage4, cmbCombo4));

        panelCenter.add(titleLbl2);
        panelCenter.add(createImagePanel(lblImage5, cmbCombo5));
        panelCenter.add(createImagePanel(lblImage6, cmbCombo6));
        panelCenter.add(createImagePanel(lblImage7, cmbCombo7));
        panelCenter.add(createImagePanel(lblImage8, cmbCombo8));
        panelCenter.add(createImagePanel(lblImage9, cmbCombo9));

        panelCenter.add(titleLbl3);  
        panelCenter.add(createImagePanel(lblImage10, cmbCombo10));
        panelCenter.add(createImagePanel(lblImage11, cmbCombo11));
        panelCenter.add(createImagePanel(lblImage12, cmbCombo12));
        panelCenter.add(createImagePanel(lblImage13, cmbCombo13));
        panelCenter.add(createImagePanel(lblImage14, cmbCombo14));
        
//        panelCenter.add(titleLbl4);  
//        panelCenter.add(createImagePanel(lblImage20, cmbCombo20));
//        panelCenter.add(createImagePanel(lblImage21, cmbCombo21));
//        panelCenter.add(createImagePanel(lblImage22, cmbCombo22));
//        panelCenter.add(createImagePanel(lblImage23, cmbCombo23));
//        panelCenter.add(createImagePanel(lblImage24, cmbCombo24));
        
        add(hamburger, BorderLayout.EAST);
        add(panelCenter, BorderLayout.CENTER);
        add(panelNorth, BorderLayout.NORTH);
        
        searchButton.addActionListener(this);
        openBtn.addActionListener(this);
        
        cmbCombo0.addActionListener(this);
        cmbCombo1.addActionListener(this);
        cmbCombo2.addActionListener(this);
        cmbCombo3.addActionListener(this);
        cmbCombo4.addActionListener(this);
        cmbCombo5.addActionListener(this);
        cmbCombo6.addActionListener(this);
        cmbCombo7.addActionListener(this);
        cmbCombo8.addActionListener(this);
        cmbCombo9.addActionListener(this);
        cmbCombo10.addActionListener(this);
        cmbCombo11.addActionListener(this);
        cmbCombo12.addActionListener(this);
        cmbCombo13.addActionListener(this);
        cmbCombo14.addActionListener(this);
        
//        cmbCombo20.addActionListener(this);
//        cmbCombo21.addActionListener(this);
//        cmbCombo22.addActionListener(this);
//        cmbCombo23.addActionListener(this);
//        cmbCombo24.addActionListener(this);




searchButton.addActionListener(this);  

        
        
        
        homeLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        helpLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutLink.setAlignmentX(Component.CENTER_ALIGNMENT);

        //hamburger.add(closeBtn);
        hamburger.add(homeLink);
        hamburger.add(profileLink);
        hamburger.add(helpLink);
        hamburger.add(logoutLink);
        
        homeLink.addActionListener(this);
        profileLink.addActionListener(this);
        helpLink.addActionListener(this);
        logoutLink.addActionListener(this);
    }

    private JPanel createImagePanel(JLabel lblImage, JButton comboBox) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(lblImage, BorderLayout.CENTER);
        panel.add(comboBox, BorderLayout.SOUTH);
        return panel;
    }
    
        private void toggleHamburger() {
        hamburger.setVisible(!hamburger.isVisible());
    }


@Override  
public void actionPerformed(ActionEvent e) {  
   if (e.getSource() instanceof JButton clickedButton) {  
      if (clickedButton.getText().equals("Preview")) {  
        String isbn = clickedButton.getActionCommand();  
        RunLibrarySystem.bookGui(isbn);  
        setVisibleFrame(false);  
      } else if (clickedButton == searchButton) {  
        String searchQuery = searchInput.getText();  
        if (searchQuery.isEmpty()) {  
           JOptionPane.showMessageDialog(null, "Please enter a search query");  
           return;  
        }  
        // Call the search method  
        searchBooks(searchQuery);  
      } else if (clickedButton == openBtn) {  
        toggleHamburger();  
      } else if (clickedButton == profileLink || clickedButton == helpLink) {  
        if (clickedButton == profileLink) {  
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
//public void actionPerformed(ActionEvent e) {
//if (e.getSource() instanceof JButton clickedButton) {  
//      if (clickedButton.getText().equals("Preview")) {  
//        String isbn = clickedButton.getActionCommand();  
//        RunLibrarySystem.bookGui(isbn);  
//        setVisibleFrame(false);  
//      }  
//}
//    if (e.getSource() == searchButton) {  
//        String searchQuery = searchInput.getText();  
//        if (searchQuery.isEmpty()) {  
//           JOptionPane.showMessageDialog(null, "Please enter a search query");  
//           return;  
//        }  
//        // Call the search method  
//        searchBooks(searchQuery); 
//    if (e.getSource() == openBtn) {
//        toggleHamburger();
//}
////    } else if (e.getSource() instanceof JButton clickedButton) {
////        if (clickedButton.getText().equals("Preview")) {
////            String isbn = clickedButton.getActionCommand();
////            RunLibrarySystem.bookGui(isbn);
////            setVisibleFrame(false);
//         else if (clickedButton == profileLink || clickedButton == helpLink) {
//            //if (clickedButton == profileLink) {
//                //RunLibrarySystem.getUserProfile().setVisible(true);
//                //RunLibrarySystem.getUserProfile("elijah@gmail.com").setVisible(true);
////                String userEmail = RunLibrarySystem.loginGui().txtUsername.getText();
////                RunLibrarySystem.getUserProfile(userEmail).setVisible(true);
//RunLibrarySystem.getUserProfile(RunLibrarySystem.userEmail).setVisible(true);
//            } else {
//                RunLibrarySystem.getHelp().setVisible(true);
//            }
//        } else if (clickedButton == logoutLink) {
//            int result = JOptionPane.showConfirmDialog(
//                this,
//                "Are you sure you want to log out?",
//                "Logout Confirmation",
//                JOptionPane.OK_CANCEL_OPTION
//            );
//            if (result == JOptionPane.OK_OPTION) {
//                System.exit(0);
//            }
//        }
//    }



private void searchBooks(String searchQuery) {  
      String query = "SELECT isbn FROM Books WHERE title LIKE ? OR author LIKE ? OR genre LIKE ?";  
      try (Connection con = new Dbconnection().derbyConnection();  
         PreparedStatement psmt = con.prepareStatement(query)) {  
  
        psmt.setString(1, "%" + searchQuery + "%");  
        psmt.setString(2, "%" + searchQuery + "%");  
        psmt.setString(3, "%" + searchQuery + "%");  
  
        try (ResultSet rs = psmt.executeQuery()) {  
           if (rs.next()) {  
              String isbn = rs.getString("isbn");  
              RunLibrarySystem.bookGui(isbn);  
              setVisible(false);  
           } else {  
              JOptionPane.showMessageDialog(null, "No books found");  
           }  
        }  
      } catch (SQLException e) {  
        JOptionPane.showMessageDialog(null, "Error searching books: " + e.getMessage());  
      }  
   }  
                private void setVisibleFrame(boolean X) {
        this.setVisible(X);
    }

}

