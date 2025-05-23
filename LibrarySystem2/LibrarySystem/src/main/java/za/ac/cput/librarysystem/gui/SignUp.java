
package za.ac.cput.librarysystem.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import za.ac.cput.librarysystem.dao.UserDAO;
import za.ac.cput.librarysystem.domain.User;

public class SignUp extends JFrame implements ActionListener {

    private JPanel panelCenter, panelNorth;
    private JLabel lblName, lblSurname, lblGender, lblDOB, lblAddress, lblPhone, lblEmail, lblPassword, lblRole, lblEmailError, lblPasswordError;
    private JTextField txtName, txtSurname, txtDOB, txtAddress, txtPhone, txtEmail;
    private JPasswordField txtPassword;
    private JButton btnSign;
    private JComboBox<String> comboGender, comboRole;
    private UserDAO dao = new UserDAO();

    public SignUp() {
        panelCenter = new JPanel();
        panelNorth = new JPanel();

        lblName = new JLabel("Name: ");
        lblSurname = new JLabel("Surname: ");
        lblGender = new JLabel("Gender:");
        lblDOB = new JLabel("Date of Birth: ");
        lblAddress = new JLabel("Address: ");
        lblPhone = new JLabel("Phone: ");
        lblEmail = new JLabel("Email:");
        lblPassword = new JLabel("Password: ");
        lblRole = new JLabel("Role:");
        lblEmailError = new JLabel("This field is required");
        lblPasswordError = new JLabel("This field is required");

        txtName = new JTextField(15);
        txtSurname = new JTextField(15);
        txtDOB = new JTextField(20);
        txtAddress = new JTextField(20);
        txtPhone = new JTextField(20);
        txtEmail = new JTextField(20);
        txtPassword = new JPasswordField(20);

        btnSign = new JButton("SIGNUP");
        btnSign.setBackground(Color.BLUE);
        btnSign.setForeground(Color.WHITE);

        comboGender = new JComboBox<>(new String[]{"Select", "Female", "Male"});
        comboRole = new JComboBox<>(new String[]{"Select", "Student", "Admin"});

        panelCenter.setBackground(Color.LIGHT_GRAY);
        lblEmailError.setForeground(Color.red);
        lblPasswordError.setForeground(Color.red);

        setSignUp();
    }

    public void setSignUp() {
        panelCenter.setLayout(new GridLayout(24, 2));

        panelCenter.add(lblName);
        panelCenter.add(txtName);

        panelCenter.add(lblSurname);
        panelCenter.add(txtSurname);

        panelCenter.add(lblGender);
        panelCenter.add(comboGender);

        panelCenter.add(lblDOB);
        panelCenter.add(txtDOB);

        panelCenter.add(lblAddress);
        panelCenter.add(txtAddress);

        panelCenter.add(lblPhone);
        panelCenter.add(txtPhone);

        panelCenter.add(lblEmail);
        panelCenter.add(txtEmail);
        panelCenter.add(lblEmailError);
        

        panelCenter.add(lblPassword);
        panelCenter.add(txtPassword);
        panelCenter.add(lblPasswordError);

        panelCenter.add(lblRole);
        panelCenter.add(comboRole);

        panelCenter.add(new JLabel(""));
        panelCenter.add(btnSign);

        setLayout(new BorderLayout());
        panelNorth.setBackground(Color.WHITE);
        panelNorth.setLayout(new BorderLayout());

        add(panelNorth, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);

        btnSign.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String role = comboRole.getSelectedItem().toString();
        boolean valid = true;

        lblEmailError.setText("");
        lblPasswordError.setText("");

        if (email.isEmpty()) {
            lblEmailError.setText("This field is required");
            valid = false;
        }
        if (password.isEmpty()) {
            lblPasswordError.setText("This field is required");
            valid = false;
        }
        if (role.equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please select a role");
            valid = false;
        }

        if (e.getSource() == btnSign && valid) {
            if (!dao.emailExists(email)) {  // Check if the email already exists
                User user = new User(txtName.getText(), txtSurname.getText(), comboGender.getSelectedItem().toString(),
                        txtDOB.getText(), txtAddress.getText(), txtPhone.getText(), email, password, role);

                dao.insertValues(user);
                JOptionPane.showMessageDialog(this, "Successfully signed up");
                this.setVisible(false);
                RunLibrarySystem.loginGui();
            } //else {
//                JOptionPane.showMessageDialog(this, "Email already exists. Please use another email.");
//            }
        }
    }
}
