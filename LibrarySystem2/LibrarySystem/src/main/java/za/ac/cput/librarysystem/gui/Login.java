package za.ac.cput.librarysystem.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import za.ac.cput.librarysystem.dao.UserDAO;
import za.ac.cput.librarysystem.domain.User;

public class Login extends JFrame {

    private JPanel panelWest, panelCenter;// panelEast;
    private JLabel lblUsername, lblPassword, lblNotMember, lblRole, lblImage, lblUsernameError, lblPasswordError;
    JTextField txtUsername;
    private JTextField txtPassword;
    private JButton btnLogin, btnSignUp, btnForgot;
    private JComboBox cmbRole;
    private ImageIcon loginImage;
    private UserDAO dao;
    private ArrayList<User> userList;

    public Login() {
        //panelEast = new JPanel();
        panelWest = new JPanel();
        panelCenter = new JPanel();

        ImageIcon originalImage = new ImageIcon("C:\\Users\\Public\\Pictures\\potential\\Potential laptop wallpapers\\gumball");
        Image scaledImage = originalImage.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        loginImage = new ImageIcon(scaledImage);
        lblImage = new JLabel(loginImage);

        lblUsername = new JLabel("Email:");
        lblPassword = new JLabel("Password:");
        lblNotMember = new JLabel("Not a member? Click here to sign up!");
        lblRole = new JLabel("Role");
        lblUsernameError = new JLabel("");
        lblPasswordError = new JLabel("");

        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);


        btnLogin = new JButton("LOGIN");
        btnSignUp = new JButton("SIGNUP");
        btnForgot = new JButton("Forgot Password");

        cmbRole = new JComboBox(new String[]{"--Select--", "Admin", "Student"});

        dao = new UserDAO();
        lblUsernameError.setForeground(Color.red);
        lblPasswordError.setForeground(Color.red);


          btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblUsernameError.setText("");
                lblPasswordError.setText("");

                String username = txtUsername.getText().trim();
                String password = txtPassword.getText().trim();
                String selectedRole = cmbRole.getSelectedItem().toString();

                boolean valid = true;

                // Check if username and password fields are empty
                if (username.isEmpty()) {
                    lblUsernameError.setText("This field is required");
                    valid = false;
                }
                if (password.isEmpty()) {
                    lblPasswordError.setText("This field is required");
                    valid = false;
                }
                if (selectedRole.equals("--Select--")) {
                    JOptionPane.showMessageDialog(null, "Please select a role");
                    valid = false;
                }

                if (valid) {
                    userList = dao.retrieveValues(); // Retrieve the list of users from the DAO
                    boolean loginSuccess = false;

                    // Check if the entered credentials match any user in the list
                    for (User user : userList) {
                        if (user.getEmail().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                            // Check if the role matches
                            if (user.getRole().equalsIgnoreCase(selectedRole)) {
                                loginSuccess = true;
                                
                                RunLibrarySystem.userEmail = username;  
                                
                                // Transition based on user role
                                if (user.getRole().equalsIgnoreCase("Admin")) {
                                    JOptionPane.showMessageDialog(null, "You have successfully logged in as Admin");
                                    setVisibleFrame(false);
                                    RunLibrarySystem.adminBookGui(); // Call the AdminBookGui
                                } else {
                                    // Transition to the home GUI for students
                                    JOptionPane.showMessageDialog(null, "You have successfully logged in");
                                    setVisibleFrame(false);
                                    try {
                                        RunLibrarySystem.HomeGui();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                break;
                            } else {
                                // Show message if the role doesn't match
                                JOptionPane.showMessageDialog(null, "Your role does not match the selected role");
                                return; // Exit the loop if roles don't match
                            }
                        }
                    }

                    if (!loginSuccess) {
                        // Display error if login credentials are incorrect
                        JOptionPane.showMessageDialog(null, "Invalid username or password");
                    }
                }
            }
        });


        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisibleFrame(false);
                RunLibrarySystem.signupGui();
            }
        });
        
        btnForgot.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            setVisibleFrame(false);
            RunLibrarySystem.forgotPasswordGui();
        }
    });
    }

    public void setLoginGui() {
        panelWest.setLayout(new GridLayout(7, 1));
        panelCenter.setLayout(new GridLayout(10, 1));

        panelCenter.add(createPanelWithComponent(lblImage));
        panelCenter.add(createPanelWithComponent(txtUsername));
        panelCenter.add(createPanelWithComponent(lblUsernameError));
        panelCenter.add(createPanelWithComponent(txtPassword));
        panelCenter.add(createPanelWithComponent(lblPasswordError));
        panelCenter.add(createPanelWithComponent(cmbRole));
        panelCenter.add(createPanelWithComponent(btnLogin));
        panelCenter.add(createPanelWithComponent(lblNotMember));
        panelCenter.add(createPanelWithComponent(btnSignUp));
        panelCenter.add(createPanelWithComponent(btnForgot));
        this.add(panelCenter, BorderLayout.CENTER);
        panelWest.add(new JLabel(""));
        panelWest.add(lblUsername);
        panelWest.add(lblPassword);
        panelWest.add(lblRole);
        panelWest.add(new JLabel(""));
        panelWest.add(new JLabel(""));
        panelWest.add(new JLabel(""));
        this.add(panelWest, BorderLayout.WEST);
    }

    private JPanel createPanelWithComponent(JComponent component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(component);
        return panel;

    }

    private JPanel createVerticalStack(JComponent topComponent, JComponent bottomComponent) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(topComponent);
        panel.add(bottomComponent);
        return panel;
    }

    public void setVisibleFrame(Boolean x) {
        this.setVisible(x);
    }

}

//package za.ac.cput.librarysystem.gui;
//
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.swing.*;
//import za.ac.cput.librarysystem.dao.UserDAO;
//import za.ac.cput.librarysystem.domain.User;
//
//public class Login extends JFrame {
//
//    private JPanel panelWest, panelCenter;// panelEast;
//    private JLabel lblUsername, lblPassword, lblNotMember, lblRole, lblImage, lblUsernameError, lblPasswordError;
//    private JTextField txtUsername, txtPassword;
//    private JButton btnLogin, btnSignUp,btnForgot;
//    private JComboBox cmbRole;
//    private ImageIcon loginImage;
//    private UserDAO dao;
//    private ArrayList<User> userList;
//
//    public Login() {
//        //panelEast = new JPanel();
//        panelWest = new JPanel();
//        panelCenter = new JPanel();
//
//        ImageIcon originalImage = new ImageIcon("C:\\Users\\Public\\Pictures\\potential\\Potential laptop wallpapers\\gumball");
//        Image scaledImage = originalImage.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
//        loginImage = new ImageIcon(scaledImage);
//        lblImage = new JLabel(loginImage);
//
//        lblUsername = new JLabel("Email:");
//        lblPassword = new JLabel("Password:");
//        lblNotMember = new JLabel("Not a member? Click here to sign up!");
//        lblRole = new JLabel("Role");
//        lblUsernameError = new JLabel("");
//        lblPasswordError = new JLabel("");
//
//        txtUsername = new JTextField(20);
//        txtPassword = new JPasswordField(20);
//
//
//        btnLogin = new JButton("LOGIN");
//        btnSignUp = new JButton("SIGNUP");
//        btnForgot = new JButton("Forgot Password");
//
//        cmbRole = new JComboBox(new String[]{"--Select--", "Admin", "Student"});
//
//        dao = new UserDAO();
//        lblUsernameError.setForeground(Color.red);
//        lblPasswordError.setForeground(Color.red);
//
//
//          btnLogin.addActionListener(new ActionListener()
//          {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                lblUsernameError.setText("");
//                lblPasswordError.setText("");
//
//                String username = txtUsername.getText().trim();
//                String password = txtPassword.getText().trim();
//                String selectedRole = cmbRole.getSelectedItem().toString();
//
//                boolean valid = true;
//
//                // Check if username and password fields are empty
//                if (username.isEmpty()) {
//                    lblUsernameError.setText("This field is required");
//                    valid = false;
//                }
//                if (password.isEmpty()) {
//                    lblPasswordError.setText("This field is required");
//                    valid = false;
//                }
//                if (selectedRole.equals("--Select--")) {
//                    JOptionPane.showMessageDialog(null, "Please select a role");
//                    valid = false;
//                }
//
//                if (valid) {
//                    userList = dao.retrieveValues(); // Retrieve the list of users from the DAO
//                    boolean loginSuccess = false;
//
//                    // Check if the entered credentials match any user in the list
//                    for (User user : userList) {
//                        if (user.getEmail().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
//                            // Check if the role matches
//                            if (user.getRole().equalsIgnoreCase(selectedRole)) {
//                                loginSuccess = true;
//                                
//                                // Transition based on user role
//                                if (user.getRole().equalsIgnoreCase("Admin")) {
//                                    JOptionPane.showMessageDialog(null, "You have successfully logged in as Admin");
//                                    setVisibleFrame(false);
//                                    RunLibrarySystem.adminBookGui(); // Call the AdminBookGui
//                                } else {
//                                    // Transition to the home GUI for students
//                                    JOptionPane.showMessageDialog(null, "You have successfully logged in");
//                                    setVisibleFrame(false);
//                                    try {
//                                        RunLibrarySystem.HomeGui();
//                                    } catch (SQLException ex) {
//                                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//                                }
//                                break;
//                            } else {
//                                // Show message if the role doesn't match
//                                JOptionPane.showMessageDialog(null, "Your role does not match the selected role");
//                                return; // Exit the loop if roles don't match
//                            }
//                        }
//                    }
//
//                    if (!loginSuccess) {
//                        // Display error if login credentials are incorrect
//                        JOptionPane.showMessageDialog(null, "Invalid username or password");
//                    }
//                }
//            }
//        });
//
//
//        btnSignUp.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                setVisibleFrame(false);
//                RunLibrarySystem.signupGui();
//            }
//        });
//        
//        btnForgot.addActionListener(new ActionListener(){
//        @Override
//        public void actionPerformed(ActionEvent e){
//            setVisibleFrame(false);
//            RunLibrarySystem.forgotPasswordGui();
//        }
//    });
//    }
//
//    public void setLoginGui() {
////        panelEast.setLayout(new GridLayout(7, 1));
//        panelWest.setLayout(new GridLayout(7, 1));
//        panelCenter.setLayout(new GridLayout(10, 1));
//
////        panelEast.add(new JLabel(""));
////        panelEast.add(new JLabel(""));
////        panelEast.add(new JLabel(""));
////        panelEast.add(new JLabel(""));
////        panelEast.add(new JLabel(""));
////        panelEast.add(new JLabel(""));
////        panelEast.add(new JLabel(""));
////        this.add(panelEast, BorderLayout.EAST);
//        panelCenter.add(createPanelWithComponent(lblImage));
//        panelCenter.add(createPanelWithComponent(txtUsername));
//        panelCenter.add(createPanelWithComponent(lblUsernameError));
//        panelCenter.add(createPanelWithComponent(txtPassword));
//        panelCenter.add(createPanelWithComponent(lblPasswordError));
//        panelCenter.add(createPanelWithComponent(cmbRole));
//        panelCenter.add(createPanelWithComponent(btnLogin));
//        panelCenter.add(createPanelWithComponent(lblNotMember));
//        panelCenter.add(createPanelWithComponent(btnSignUp));
//        panelCenter.add(createPanelWithComponent(btnForgot));
//        this.add(panelCenter, BorderLayout.CENTER);
//        panelWest.add(new JLabel(""));
//        panelWest.add(lblUsername);
//        panelWest.add(lblPassword);
//        panelWest.add(lblRole);
//        panelWest.add(new JLabel(""));
//        panelWest.add(new JLabel(""));
//        panelWest.add(new JLabel(""));
//        this.add(panelWest, BorderLayout.WEST);
//    }
//
//    private JPanel createPanelWithComponent(JComponent component) {
//        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        panel.add(component);
//        return panel;
//
//    }
//
//    private JPanel createVerticalStack(JComponent topComponent, JComponent bottomComponent) {
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        panel.add(topComponent);
//        panel.add(bottomComponent);
//        return panel;
//    }
//
//    public void setVisibleFrame(Boolean x) {
//        this.setVisible(x);
//    }
//
//}