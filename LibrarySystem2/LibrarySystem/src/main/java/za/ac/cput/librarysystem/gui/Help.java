package za.ac.cput.librarysystem.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Help extends JFrame implements ActionListener {

    private JLabel lblFAQs, lblPrompt;
    private JTextArea txtHelp;
    private JButton btnSend;

    private JPanel panelNorth, hamburger, searchPanel;
    private final JTextField searchInput;
    private final JButton searchButton, openBtn;
    private final JButton homeLink, profileLink, helpLink, logoutLink;

    public Help() {
        panelNorth = new JPanel();
        hamburger = new JPanel();
        searchInput = new JTextField(20);
        searchButton = new JButton("Search");
        openBtn = new JButton("☰");

        homeLink = new JButton("Home");
        profileLink = new JButton("Profile");
        helpLink = new JButton("Help");
        logoutLink = new JButton("Log Out");

        setupGUI();
    }

  private void setupGUI() {
    setLayout(new BorderLayout());
    setTitle("Help");
    setSize(1200, 800);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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

    // FAQ Section
    lblFAQs = new JLabel("<html>" + 
                         "<h3>FAQs:</h3>" +
                         "<ul>" +
                         "<li>How do I create an account?<br>Click the 'sign up' button, enter the required information, and click sign up. You’ll then be referred to the 'login' page.</li>" +
                         "<li>What if I can't remember my password?<br>Click 'forgot my password' on the login page, then enter your email and follow instructions to reset your password.</li>" +
                         "<li>How can I search for books in the library?<br>Use the search bar on top of the page or choose from different genres after logging in.</li>" +
                         "<li>How do I view the details of a specific book?<br>Click the 'preview' button on the home page when choosing the book.</li>" +
                         "<li>How do I borrow or return a book?<br>Click the 'borrow' button after viewing the book details and follow the instructions.</li>" +
                         "<li>What happens if I encounter an error while borrowing a book?<br>Please contact support for assistance.</li>" +
                         "</ul>" +
                         "<h3>Please describe your issue or ask a question.<br>We will respond promptly to assist you.</h3>" +
                         "</html>");
    
    lblFAQs.setFont(new Font("Arial", Font.PLAIN, 16));

    // Create the main center panel
    JPanel pnlCenter = new JPanel();
    pnlCenter.setLayout(new GridLayout(2, 1, 10, 10)); // 3 rows, 1 column, 10px gaps
    
    pnlCenter.add(lblFAQs); // Scrollable FAQs

    // User Input Area
    txtHelp = new JTextArea(2, 30);
    txtHelp.setLineWrap(true);
    txtHelp.setWrapStyleWord(true);

    pnlCenter.add(txtHelp); // Add text area to center panel

    // Button Panel
    JPanel pnlButtons = new JPanel();
    pnlButtons.setLayout(new FlowLayout());
    pnlButtons.add(btnSend = new JButton("Send Question"));

    // Add components to the frame
    add(pnlCenter, BorderLayout.CENTER); // Center panel now contains FAQ and text area
    add(pnlButtons, BorderLayout.SOUTH); // Button panel at the bottom
    
    add(hamburger, BorderLayout.EAST);
    add(panelNorth, BorderLayout.NORTH);

    // Add buttons to hamburger menu
    homeLink.setAlignmentX(Component.CENTER_ALIGNMENT);
    profileLink.setAlignmentX(Component.CENTER_ALIGNMENT);
    helpLink.setAlignmentX(Component.CENTER_ALIGNMENT);
    logoutLink.setAlignmentX(Component.CENTER_ALIGNMENT);

    hamburger.add(homeLink);
    hamburger.add(profileLink);
    hamburger.add(helpLink);
    hamburger.add(logoutLink);

    // Add action listeners for buttons
    homeLink.addActionListener(this);
    profileLink.addActionListener(this);
    helpLink.addActionListener(this);
    logoutLink.addActionListener(this);
    searchButton.addActionListener(this);
    openBtn.addActionListener(this);
    btnSend.addActionListener(this);
}


    private void toggleHamburger() {
        hamburger.setVisible(!hamburger.isVisible());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openBtn) {
            toggleHamburger();
        } else if (e.getSource() instanceof JButton clickedButton) {
            if (clickedButton == profileLink || clickedButton == helpLink) {
                if (clickedButton == profileLink) {
                    RunLibrarySystem.getUserProfile("elijah@gmail.com").setVisible(true);
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

        if (e.getSource() == btnSend) {
            // Validate and send the help question
            String question = txtHelp.getText().trim();
            if (question.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a question or issue before submitting.", "Submission Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            System.out.println("Sending help request: " + question);
            JOptionPane.showMessageDialog(this, "Your question has been submitted!", "Submission Successful", JOptionPane.INFORMATION_MESSAGE);
            txtHelp.setText(""); // Clear input area after submission
        }
    }
}
