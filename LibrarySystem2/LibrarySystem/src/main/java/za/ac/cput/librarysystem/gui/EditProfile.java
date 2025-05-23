//package za.ac.cput.librarysystem.gui;
//
//import java.awt.*;
//import javax.swing.*;
//
///**
// *
// * @author alung
// */
//public class EditProfile extends JFrame{
//    private JPanel panelCenter;
//    private JLabel lblName, lblSurname, lblAddress, lblPhone, lblBlank;
//    private JTextField txtName, txtSurname, txtAddress, txtPhone;
//    private JButton updateBtn;
//    
//    public EditProfile(){
//        
//        panelCenter = new JPanel();
//        
//        lblName = new JLabel("Name");
//        lblSurname = new JLabel("Surname");
//        lblAddress = new JLabel("Address");
//        lblPhone = new JLabel("Phone");
//        lblBlank = new JLabel("");
//        
//        txtName = new JTextField(20);
//        txtSurname = new JTextField(20);
//        txtAddress = new JTextField(20);
//        txtPhone = new JTextField(20);
//        
//        updateBtn = new JButton("Update");
//        
//        
//    }
//    
//    public void setGui(){
//        
//         panelCenter.setLayout(new GridLayout(10, 1));
//         
//         panelCenter.add(lblName);
//         panelCenter.add(txtName);
//         panelCenter.add(lblSurname);
//         panelCenter.add(txtSurname);
//         panelCenter.add(lblAddress);
//         panelCenter.add(txtAddress);
//         panelCenter.add(lblPhone);
//         panelCenter.add(txtPhone);
//         panelCenter.add(lblBlank);
//         panelCenter.add(updateBtn);
//         
//         add(panelCenter, BorderLayout.CENTER);
//    }
//}


package za.ac.cput.librarysystem.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author alung
 */
public class EditProfile extends JFrame{
    private JPanel panelCenter;
    private JLabel lblName, lblSurname, lblAddress, lblPhone, lblBlank;
    private JTextField txtName, txtSurname, txtAddress, txtPhone;
    private JButton updateBtn;
    
    public EditProfile(){
        
        panelCenter = new JPanel();
        
        lblName = new JLabel("Name");
        lblSurname = new JLabel("Surname");
        lblAddress = new JLabel("Address");
        lblPhone = new JLabel("Phone");
        lblBlank = new JLabel("");
        
        txtName = new JTextField(20);
        txtSurname = new JTextField(20);
        txtAddress = new JTextField(20);
        txtPhone = new JTextField(20);
        
        updateBtn = new JButton("Update");
        
        
    }
    
    public void setGui(){
        
         panelCenter.setLayout(new GridLayout(10, 1));
         
         panelCenter.add(lblName);
         panelCenter.add(txtName);
         panelCenter.add(lblSurname);
         panelCenter.add(txtSurname);
         panelCenter.add(lblAddress);
         panelCenter.add(txtAddress);
         panelCenter.add(lblPhone);
         panelCenter.add(txtPhone);
         panelCenter.add(lblBlank);
         panelCenter.add(updateBtn);
         
         add(panelCenter, BorderLayout.CENTER);
         
           updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisibleFrame(false);
                try {
                    RunLibrarySystem.HomeGui();
                } catch (SQLException ex) {
                    Logger.getLogger(EditProfile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
       
        });
    }
    public void setVisibleFrame(Boolean False) {
        this.setVisible(False);
    }
}