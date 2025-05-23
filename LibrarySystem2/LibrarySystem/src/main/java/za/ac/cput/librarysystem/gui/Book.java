package za.ac.cput.librarysystem.gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
import za.ac.cput.librarysystem.connection.Dbconnection;
import za.ac.cput.librarysystem.dao.BookDao;

public class Book extends JFrame implements ActionListener {
    private JPanel panelWest, panelCenter, panelNorth, hamburger, searchPanel, buttonPanel;
    private JLabel lblTitle, lblAuthor, lblRating, lblDescription1, lblGenre, lblDate, lblImage;
    private JButton btnBorrow, btnReserve, searchButton, openBtn;
    private JTextField searchInput;
    private JButton homeLink, profileLink, helpLink, logoutLink; //btnSign, closeBtn;
    private JLabel lblStatus;
    private String isbn;

    public Book() {
        super("Library System");
       
        //this.userEmail = userEmail;

        panelNorth = new JPanel();
        panelWest = new JPanel();
        panelCenter = new JPanel();
        hamburger = new JPanel();

        searchInput = new JTextField(20);
        searchButton = new JButton("Search");
        openBtn = new JButton("☰");
        
        //closeBtn = new JButton("X");
        homeLink = new JButton("Home");
        homeLink.setPreferredSize(new Dimension(150, 30));
        profileLink = new JButton("Profile");
        profileLink.setPreferredSize(new Dimension(150, 30));
        helpLink = new JButton("Help");
        helpLink.setPreferredSize(new Dimension(150, 30));
        logoutLink = new JButton("Log Out");
        logoutLink.setPreferredSize(new Dimension(150, 30));

        Border border = BorderFactory.createLineBorder(Color.BLACK);
        lblTitle = new JLabel();
        lblAuthor = new JLabel();
        lblRating = new JLabel();
        lblDescription1 = new JLabel();
        lblGenre = new JLabel();
        lblDate = new JLabel();
        lblImage = new JLabel();
        lblImage.setBorder(border);
        
        lblStatus = new JLabel();

        // Increase the image size
        lblImage.setPreferredSize(new Dimension(300, 400));

        btnBorrow = new JButton("Borrow");
        btnReserve = new JButton("Reserve");


    }

    public void setGui(String isbn) {
         this.isbn =  isbn;
        
        // Fetch book data from the database
        fetchBookData(isbn);
        
        setLayout(new BorderLayout());
        
        if (getParent() != null && getParent() instanceof JFrame) {
        ((JFrame) getParent()).dispose();
    }

        // North Panel
        panelNorth.setLayout(new BorderLayout());
        panelNorth.setBackground(Color.WHITE);
        panelNorth.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        ImageIcon headerImageIcon = new ImageIcon("C:\\Users\\books\\logo.png");
        Image image = headerImageIcon.getImage().getScaledInstance(150, 50, Image.SCALE_SMOOTH);
        JLabel headerImageLabel = new JLabel(new ImageIcon(image));
        panelNorth.add(headerImageLabel, BorderLayout.WEST);

        searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchInput.setPreferredSize(new Dimension(200, 30));
        //searchButton.addActionListener(e -> fetchBookData());
        searchPanel.add(searchInput);
        searchPanel.add(searchButton);
        panelNorth.add(searchPanel, BorderLayout.CENTER);

        openBtn.setPreferredSize(new Dimension(60, 55));
        openBtn.addActionListener(e -> toggleHamburger());
        panelNorth.add(openBtn, BorderLayout.EAST);

        hamburger.setLayout(new BoxLayout(hamburger, BoxLayout.Y_AXIS));
        hamburger.setBackground(Color.LIGHT_GRAY);
        hamburger.setPreferredSize(new Dimension(250, getHeight()));
        hamburger.setVisible(false);

        add(hamburger, BorderLayout.EAST);
        add(panelNorth, BorderLayout.NORTH);

        // West Panel (Image and buttons)
        panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.Y_AXIS));
        panelWest.setPreferredSize(new Dimension(300, 0));
        //panelWest.setBackground(Color.LIGHT_GRAY); // Set the background color

        // Add image to the west panel
        JPanel imagePanel = new JPanel(new FlowLayout()); 
        //imagePanel.setBackground(Color.LIGHT_GRAY); // Match the background color
        imagePanel.add(lblImage); 

        // Create a panel to hold the buttons side by side
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //buttonPanel.setBackground(Color.LIGHT_GRAY); // Match the background color
        buttonPanel.add(btnBorrow);
        buttonPanel.add(btnReserve);

        panelWest.add(imagePanel); // Image panel
        panelWest.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing between image and buttons
        panelWest.add(buttonPanel); // Add the buttons side by side

        // Center Panel (Book details)
        panelCenter.setLayout(new GridLayout(9, 1));
        //panelCenter.setBackground(Color.LIGHT_GRAY); // Match the background color
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lblAuthor.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblRating.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblDescription1.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblGenre.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblDate.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblStatus.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panelCenter.add(lblTitle);
        panelCenter.add(lblAuthor);
        panelCenter.add(lblRating);
        panelCenter.add(lblDescription1);
        panelCenter.add(lblGenre);
        panelCenter.add(lblDate);
        
        panelCenter.add(lblStatus);

        add(panelWest, BorderLayout.WEST);
        add(panelCenter, BorderLayout.CENTER);
        
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
        
        btnBorrow.addActionListener(this);
        btnReserve.addActionListener(this);
    }

    private void fetchBookData(String isbn) {
        String query = "SELECT title, author, genre, description, date, rating, status, image FROM Books WHERE isbn = ?";
        try (Connection con = new Dbconnection().derbyConnection();
             PreparedStatement psmt = con.prepareStatement(query)) {
            
            psmt.setString(1, isbn);


            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    lblTitle.setText("<html><h2>" + rs.getString("title") + "</h2></html>");
                    lblAuthor.setText("By " + rs.getString("author"));
                    lblGenre.setText("Genres: " + rs.getString("genre"));
                    lblRating.setText("Rating: " + rs.getString("rating")); 
                    lblDescription1.setText("<html>" + rs.getString("description") + "</html>");
                    lblDate.setText("Published: " + rs.getString("date"));

                    // Load image
                    Blob blob = rs.getBlob("image");
                    if (blob != null) {
                        byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                        ImageIcon bookImage = new ImageIcon(imageBytes);
                        Image scaledImage = bookImage.getImage().getScaledInstance(300, 400, Image.SCALE_SMOOTH);
                        lblImage.setIcon(new ImageIcon(scaledImage));
                    } else {
                        lblImage.setIcon(null); 
                    }
                    
                    
                    String status = rs.getString("status");
                    lblStatus.setText("Status: " + (status.equals("Available") ? "Available" : "Not Available"));

//                    if (!status.equals("Available")) {
//                        btnBorrow.setEnabled(false);  // Disable "Borrow" if the book is not available
//                    } else {
//                        btnBorrow.setEnabled(true);  // Enable "Borrow" if the book is available
//                    }
if (status.equals("Not Available")) {  
              lblStatus.setText("Status: Not Available");  
              btnBorrow.setEnabled(false);  
           } else {  
              lblStatus.setText("Status: Available");  
              btnBorrow.setEnabled(true);  
           }
                } else {
                    JOptionPane.showMessageDialog(null, "Book not found");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error retrieving book data: " + e.getMessage());
        }
    }
    
       private void toggleHamburger() {
        hamburger.setVisible(!hamburger.isVisible());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource() instanceof JButton clickedButton) {
        if (clickedButton.getText().equals("Home")) {
            try {
                RunLibrarySystem.HomeGui();
            } catch (SQLException ex) {
                Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
            }
            setVisible(false);
            }
            else if (clickedButton == btnBorrow) {
            try {
                //                // Update book status to "Not Available"
//        BookDao bookDao = new BookDao();
//        bookDao.updateBookStatus(isbn, "Not Available");
//
//        // Update the status label
//        lblStatus.setText("Status: Not Available");
//
//        // Disable the "Borrow" button
//        btnBorrow.setEnabled(false);
//            RunLibrarySystem.borrow(getBookTitle());
//            setVisible(false);
//            //borrowBook(isbn);
RunLibrarySystem.borrow(isbn);  
            } catch (SQLException ex) {
                Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
            }
        setVisible(false); 
        } else if (clickedButton == btnReserve) {
            if (lblStatus.getText().equals("Status: Not Available")) {  
                try {  
                    RunLibrarySystem.reserve(isbn);
                } catch (SQLException ex) {
                    Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
                }
           setVisible(false);  
        } else {  
           JOptionPane.showMessageDialog(null, "Book is available for borrowing.");  
        }
    }
       
}
    }
    String getBookTitle() {
        return lblTitle.getText().substring(6).trim(); // Assuming the title starts with "<html><h2>"
    }
}
