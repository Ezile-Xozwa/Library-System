
package za.ac.cput.librarysystem.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import za.ac.cput.librarysystem.connection.Dbconnection;
import za.ac.cput.librarysystem.domain.User;

public class UserDAO {

    private Connection con;
    private Statement s;
    private PreparedStatement ps;
    private ResultSet rs;

    public UserDAO() {
        con = new Dbconnection().derbyConnection();
    }

    public void createTable() {  
        String createTableStmt = "CREATE TABLE Users ("  
            + "userId INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"  
            + "name VARCHAR(100) NOT NULL,"  
            + "surname VARCHAR(100) NOT NULL,"  
            + "gender VARCHAR(10) NOT NULL,"  
            + "dateOfBirth VARCHAR(20) NOT NULL,"  
            + "address VARCHAR(255) NOT NULL,"  
            + "phone VARCHAR(20) NOT NULL,"  
            + "email VARCHAR(100) NOT NULL UNIQUE,"  
            + "password VARCHAR(100) NOT NULL,"  
            + "role VARCHAR(20) NOT NULL)";  
  
        try {  
            s = con.createStatement();  
            s.executeUpdate(createTableStmt);  
            JOptionPane.showMessageDialog(null, "Users table created successfully...");  
        } catch (SQLException ex) {  
            JOptionPane.showMessageDialog(null, "ERROR!!!!!");  
        }  
    }  

    public void insertValues(User user) {  
        String insertValuesStmt = "INSERT INTO Users (name, surname, gender, dateOfBirth, address, phone, email, password, role) "  
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";  
  
        try {  
            ps = con.prepareStatement(insertValuesStmt);  
            ps.setString(1, user.getName());  
            ps.setString(2, user.getSurname());  
            ps.setString(3, user.getGender());  
            ps.setString(4, user.getDateofbirth());  
            ps.setString(5, user.getAddress());  
            ps.setString(6, user.getPhone());  
            ps.setString(7, user.getEmail());  
            ps.setString(8, user.getPassword());  
            ps.setString(9, user.getRole());  
  
            ps.executeUpdate();  
            JOptionPane.showMessageDialog(null, "User successfully signed up");  
        } catch (SQLException ex) {  
            JOptionPane.showMessageDialog(null, "Error!!!!!");  
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);  
        }  
    }  
    
    public boolean updatePassword(String email, String newPassword) throws SQLException {
        String query = "UPDATE users SET password = ? WHERE email = ?";
        ps = con.prepareStatement(query);
        ps.setString(1, newPassword);  // Optionally hash the password here
        ps.setString(2, email);
         int rowsAffected = ps.executeUpdate();
         return rowsAffected > 0;
        }


    public boolean emailExists(String email) {  
        String query = "SELECT * FROM Users WHERE email = ?";  
        try {  
            ps = con.prepareStatement(query);  
            ps.setString(1, email);  
            rs = ps.executeQuery();  
            return rs.next();  // returns true if email exists  
        } catch (SQLException ex) {  
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);  
        }  
        return false;  
    }  

    public ArrayList<User> retrieveValues() {  
        String retrieveValueQry = "SELECT * FROM Users";  
        ArrayList<User> userList = new ArrayList<>();  
  
        try {  
            s = con.createStatement();  
            rs = s.executeQuery(retrieveValueQry);  
  
            while (rs.next()) {  
                User userObj = new User(rs.getString("email"), rs.getString("password"), rs.getString("role"));  
                userList.add(userObj);  
            }  
        } catch (SQLException ex) {  
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);  
        }  
        return userList;  
    }

    // New method to retrieve a user by email
    public User getUserByEmail(String email) {
        String query = "SELECT * FROM Users WHERE email = ?";
        User user = null;
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                user = new User(
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getString("gender"),
                    rs.getString("dateOfBirth"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
}

