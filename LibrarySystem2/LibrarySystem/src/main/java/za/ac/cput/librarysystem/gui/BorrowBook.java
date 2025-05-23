
package za.ac.cput.librarysystem.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class BorrowBook extends JDialog {

    private JLabel lblTitle, lblIssueDate, lblReturnDate;
    private JButton btnConfirmBorrow;
    private JPanel panelTop, panelBottom, panelInfo;
    private String bookTitle;
    public String issueDate;
    public String returnDate;

public BorrowBook(JFrame parent, String title, String issueDate, String returnDate) {
    super(parent, "Borrow Book", true);
    this.bookTitle = title;
    this.issueDate = issueDate;
    this.returnDate = returnDate;  // Ensure these fields are set
    setGui();
        
        setSize(400, 250);
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public void setGui() {
        panelTop = new JPanel();
        panelBottom = new JPanel();
        panelInfo = new JPanel();

        // Top panel (Book Title)
        JLabel lblTitle = new JLabel("Book Title:");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel lblBookTitle = new JLabel(bookTitle);
        lblBookTitle.setFont(new Font("Arial", Font.PLAIN, 16));
        panelTop.add(lblTitle);
        panelTop.add(Box.createHorizontalStrut(10));
        panelTop.add(lblBookTitle);

        // Bottom panel (Buttons)
        btnConfirmBorrow = new JButton("Confirm Borrow");
        btnConfirmBorrow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmBorrow();
            }
        });

        // Info panel (Issue Date and Return Date)
        JLabel lblIssueDate = new JLabel("Issue Date:");
        lblIssueDate.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel lblReturnDate = new JLabel("Return Date:");
        lblReturnDate.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel lblIssueDateValue = new JLabel(issueDate);
        JLabel lblReturnDateValue = new JLabel(returnDate);
        lblIssueDateValue.setFont(new Font("Arial", Font.PLAIN, 16));
        lblReturnDateValue.setFont(new Font("Arial", Font.PLAIN, 16));
        panelInfo.setLayout(new GridLayout(2, 2, 10, 10));
        panelInfo.add(lblIssueDate);
        panelInfo.add(lblIssueDateValue);
        panelInfo.add(lblReturnDate);
        panelInfo.add(lblReturnDateValue);

        // Set up layout
        setLayout(new BorderLayout());
        add(panelTop, BorderLayout.NORTH);
        add(panelInfo, BorderLayout.CENTER);
        add(btnConfirmBorrow, BorderLayout.SOUTH);

        // Remove HTML tags from the book title
        String cleanTitle = bookTitle.replaceAll("<[^>]*>", "");
        lblBookTitle.setText(cleanTitle);
    }

    private void confirmBorrow() {
        JOptionPane.showMessageDialog(this, "Book borrowed successfully!");
        dispose();
        try {
        RunLibrarySystem.HomeGui();  // Open the home screen again
    } catch (SQLException e) {
        //e.printStackTrace();
    }
    }
}

