package za.ac.cput.librarysystem.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lamot
 */
public class Dbconnection {

    private Connection con;
    private String url;// = "jdbc:derby://localhost:1527/LibrarySystem";
    private String username;// = "Administrator";
    private String password;// = "12345";

    public Connection derbyConnection() {
        url = "jdbc:derby://localhost:1527/LibrarySystem";
        username = "Administrator";
        password = "12345";
//        url = "jdbc:derby://localhost:1527/libraryTest";
//        username = "Username";
//        password = "password";
        
        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(Dbconnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
}
