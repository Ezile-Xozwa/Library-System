package za.ac.cput.librarysystem.gui;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import za.ac.cput.librarysystem.dao.*;
//import za.ac.cput.librarysystem.dao.BookDao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import za.ac.cput.librarysystem.connection.Dbconnection;

public class AdminBookGui extends JFrame implements ActionListener {
    private final JTextField bookTitleField, authorField, genreField, isbnField;
    private final JTextField descriptionField, dateField, ratingField; // New fields
    private final JButton addButton, updateButton, deleteButton, clearButton;
    private final JTable bookTable;
    private final DefaultTableModel tableModel;
    
    private final JRadioButton availableButton, notAvailableButton;
    private final ButtonGroup statusGroup;
    
    private final BookDao dao;
    
    private final JPanel panelSouth, panelNorth;

    public AdminBookGui() {
        
        super(); // Call the constructor of Bookgui
        setTitle("Admin Book Management");
        //setGui(); // Setup the GUI for the AdminBook
        
        dao = new BookDao();
        
        panelSouth = new JPanel();
        panelNorth = new JPanel();
        
        // Existing fields
        bookTitleField = new JTextField(20);
        authorField = new JTextField(20);
        genreField = new JTextField(20);
        isbnField = new JTextField(20);
        
        // New fields
        descriptionField = new JTextField(20);
        dateField = new JTextField(20);
        ratingField = new JTextField(5); // Assuming rating is a number

        addButton = new JButton("Add Book");
        updateButton = new JButton("Update Book");
        deleteButton = new JButton("Delete Book");
        clearButton = new JButton("Clear Fields");
        
        // Create radio buttons for status
        availableButton = new JRadioButton("Available");
        notAvailableButton = new JRadioButton("Not Available");

        // Group the radio buttons to allow only one to be selected at a time
        statusGroup = new ButtonGroup();
        statusGroup.add(availableButton);
        statusGroup.add(notAvailableButton);
        
        // Set default selection
        availableButton.setSelected(true); // Default to "Available"


        String[] columnNames = {"Title", "Author", "Genre", "ISBN", "Description", "Date", "Rating", "Status"}; // Updated column names
        tableModel = new DefaultTableModel(columnNames, 0);
        bookTable = new JTable(tableModel);
    }

    public void setGui() {
        panelNorth.setLayout(new GridLayout(9, 2)); // Update grid layout to accommodate new fields
        panelSouth.setLayout(new GridLayout(1, 4));

        // Existing fields
        panelNorth.add(new JLabel("Book Title:"));
        panelNorth.add(bookTitleField);
        panelNorth.add(new JLabel("Author:"));
        panelNorth.add(authorField);
        panelNorth.add(new JLabel("Genre:"));
        panelNorth.add(genreField); 
        panelNorth.add(new JLabel("ISBN:"));
        panelNorth.add(isbnField);
        
        // New fields
        panelNorth.add(new JLabel("Description:"));
        panelNorth.add(descriptionField);
        panelNorth.add(new JLabel("Date:"));
        panelNorth.add(dateField);
        panelNorth.add(new JLabel("Rating:"));
        panelNorth.add(ratingField);
        
        
                
        panelNorth.add(new JLabel("Status:"));
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.add(availableButton);
        statusPanel.add(notAvailableButton);
        panelNorth.add(statusPanel);
        

        JScrollPane scrollPane = new JScrollPane(bookTable);
        
        panelSouth.add(addButton);
        panelSouth.add(updateButton);
        panelSouth.add(deleteButton);
        panelSouth.add(clearButton);

        add(panelNorth, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelSouth, BorderLayout.SOUTH);
        
        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        clearButton.addActionListener(this);
        
         populateTable();  
    }

    @Override
    public void actionPerformed(ActionEvent e) {
           if (e.getSource() == addButton) {
    String title = bookTitleField.getText();
    String author = authorField.getText();
    String genre = genreField.getText();
    String isbn = isbnField.getText();
    String description = descriptionField.getText();
    String date = dateField.getText();
    String rating = ratingField.getText();
    
    String status = availableButton.isSelected() ? "Available" : "Not Available";

    if ((title.isEmpty() && isbn.isEmpty())
            || author.isEmpty() || genre.isEmpty() ||
        description.isEmpty() || date.isEmpty() || rating.isEmpty() || status.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all the fields before adding a book.");
    } else {
        // Attempt to load the image from a specified directory
        String imagePath = "C:\\Users\\books\\" + title.replaceAll(" ", "_") + ".jpg"; // Adjust path as necessary
        File imageFile = new File(imagePath);
        InputStream imageStream = null;

        try {
            imageStream = new FileInputStream(imageFile); // Create InputStream from the image file
            dao.insertBook(title, author, genre, isbn, description, date, rating, imageStream, status);
            tableModel.addRow(new Object[]{title, author, genre, isbn, description, date, rating, status});
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Image file not found: " + ex.getMessage());
        } finally {
            if (imageStream != null) {
                try {
                    imageStream.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error closing image stream: " + ex.getMessage());
                }
            }
        }
    }
}
        else if (e.getSource() == updateButton) {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow != -1) {
                
                String oldIsbn = (String) tableModel.getValueAt(selectedRow, 3); // Get the old ISBN
                String title = bookTitleField.getText();
                String author = authorField.getText();
                String genre = genreField.getText();
                String isbn = isbnField.getText();
                String description = descriptionField.getText(); // New field
                String date = dateField.getText(); // New field
                String rating = ratingField.getText(); // New field
                
                String status = availableButton.isSelected() ? "Available" : "Not Available";
                
                dao.updateBook(title, author, genre, isbn, description, date, rating, status, oldIsbn); // Updated method
                
                tableModel.setValueAt(title, selectedRow, 0);
                tableModel.setValueAt(author, selectedRow, 1);
                tableModel.setValueAt(genre, selectedRow, 2);
                tableModel.setValueAt(isbn, selectedRow, 3);
                tableModel.setValueAt(description, selectedRow, 4); // Update description
                tableModel.setValueAt(date, selectedRow, 5); // Update date
                tableModel.setValueAt(rating, selectedRow, 6); // Update rating
            } else {
                JOptionPane.showMessageDialog(this, "Select a book to update.");
            }
            
        } else if (e.getSource() == deleteButton) {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow != -1) {
                
                String isbn = (String) tableModel.getValueAt(selectedRow, 3); // Get ISBN to delete
                dao.deleteBook(isbn); // Delete from the database
                
                tableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Select a book to delete.");
            }
        } else if (e.getSource() == clearButton) {
            // Clear all input fields
            bookTitleField.setText("");
            authorField.setText("");
            genreField.setText("");
            isbnField.setText("");
            descriptionField.setText(""); // Clear new field
            dateField.setText(""); // Clear new field
            ratingField.setText(""); // Clear new field
        }
    }
    public void populateTable() {  
   String query = "SELECT title, author, genre, isbn, description, date, rating, status FROM Books";  
   try (Connection con = new Dbconnection().derbyConnection();  
      PreparedStatement psmt = con.prepareStatement(query);  
      ResultSet rs = psmt.executeQuery()) {  
  
      while (rs.next()) {  
        String title = rs.getString("title");  
        String author = rs.getString("author");  
        String genre = rs.getString("genre");  
        String isbn = rs.getString("isbn");  
        String description = rs.getString("description");  
        String date = rs.getString("date");  
        String rating = rs.getString("rating");  
        String status = rs.getString("status");  
  
        tableModel.addRow(new Object[]{title, author, genre, isbn, description, date, rating, status});  
      }  
   } catch (SQLException e) {  
      JOptionPane.showMessageDialog(null, "Error populating table: " + e.getMessage());  
   }  
}
}
