package za.ac.cput.librarysystem.gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import javax.swing.*;

public class Reservation extends JDialog implements ActionListener {

    private JLabel lblTitle, lblAvailabilityDate, lblReturnDate;
    private JButton btnConfirmReservation;
    private JPanel panelTop, panelInfo;
    private String bookTitle;
    private String cleanTitle;

    public Reservation(JFrame parent, String title) {
        super(parent, "Book Reservation", true);
        bookTitle = title;
        setGui();
        
        setSize(400, 250);
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public void setGui() {
        panelTop = new JPanel();
        panelInfo = new JPanel();

        // Title Panel (Book Title)
        JLabel lblTitleLabel = new JLabel("Book Title:");
        lblTitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle = new JLabel(bookTitle);
        lblTitle.setFont(new Font("Arial", Font.PLAIN, 16));
        panelTop.add(lblTitleLabel);
        panelTop.add(lblTitle);

        // Get today's date as availability date
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String availabilityDate = dateFormat.format(today);

        // Calculate return date (14-day return period)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_YEAR, 14);
        String returnDate = dateFormat.format(calendar.getTime());

        // Info Panel (Availability Date and Return Date)
        lblAvailabilityDate = new JLabel("Availability Date: " + availabilityDate);
        lblReturnDate = new JLabel("Return Date: " + returnDate);
        panelInfo.setLayout(new GridLayout(2, 2, 10, 10));
        panelInfo.add(lblAvailabilityDate);
        panelInfo.add(lblReturnDate);

        // Confirm Reservation button
        btnConfirmReservation = new JButton("Confirm Reservation");
        btnConfirmReservation.addActionListener(this);

        // Set up layout
        setLayout(new BorderLayout());
        add(panelTop, BorderLayout.NORTH);
        add(panelInfo, BorderLayout.CENTER);
        add(btnConfirmReservation, BorderLayout.SOUTH);

        // Remove HTML tags from the book title
        cleanTitle = bookTitle.replaceAll("<[^>]*>", "");
        lblTitle.setText(cleanTitle);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnConfirmReservation) {
            JOptionPane.showMessageDialog(this, "Book reserved successfully!");
            dispose();

            try {
        RunLibrarySystem.HomeGui();  // Open the home screen again
    } catch (SQLException se) {
        se.printStackTrace();
    }

        }
    }
}
