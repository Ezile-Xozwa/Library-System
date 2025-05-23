package za.ac.cput.librarysystem.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import za.ac.cput.librarysystem.dao.UserDAO;

/**
 *
 * @author User
 */
public class ForgotPassword extends JFrame implements ActionListener{
    private JPanel pnlCenter;
    private JLabel lblEmail, lblNewPassWord, lblConfirm;
    private JTextField txtEmail,txtNewPassWord, txtConfirm;
    private JButton btnReset,btnBack;
    private UserDAO dao = new UserDAO();
    
    public ForgotPassword(){
        pnlCenter = new JPanel();
        
        lblEmail = new JLabel("Enter your email:");
        lblNewPassWord = new JLabel("Enter your new password");
        lblConfirm = new JLabel("Confirm new password");
        
        txtEmail = new JTextField(20);
        txtNewPassWord = new JTextField(20);
        txtConfirm = new JTextField(20);
        
         btnReset = new JButton("Reset");
         btnBack = new JButton("Back");
    }
     public void setGUI(){
          pnlCenter.setLayout(new GridLayout(4, 1));

         pnlCenter.add(lblEmail);
         pnlCenter.add(txtEmail);
         
         pnlCenter.add(lblNewPassWord);
         pnlCenter.add(txtNewPassWord);
           
         pnlCenter.add(lblConfirm);
         pnlCenter.add(txtConfirm);
              
         pnlCenter.add(btnReset);
         pnlCenter.add(btnBack);
         
         btnReset.addActionListener(this);
         
         this.add(pnlCenter, BorderLayout.CENTER);
                
            
     }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnReset){
            String email = txtEmail.getText().trim();
            String newPassword = txtNewPassWord.getText().trim();
            String confirmPassword = txtConfirm.getText().trim();
             boolean isUpdated = false;
            
             if (email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                
             } else if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                } else{
                 
                try {
                    isUpdated = dao.updatePassword(email, newPassword);
                } catch (SQLException ex) {
                    Logger.getLogger(ForgotPassword.class.getName()).log(Level.SEVERE, null, ex);
                }
             
                if(isUpdated){
                        JOptionPane.showMessageDialog(this, "Password has been reset succesfully");
                        this.setVisible(false);
            RunLibrarySystem.loginGui();
                        }
        if(e.getSource() == btnBack){
            this.setVisible(false); 
            RunLibrarySystem.loginGui();
        }
        
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    
    
}}}}