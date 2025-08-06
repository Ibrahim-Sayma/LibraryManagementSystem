package DataBase;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DatabaseConnection {

    public static Connection connection = null;

    public static void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Driver Not Found ..!" + ex.getMessage());
        }
    }

    public static Connection getInstance() {
        if (connection == null) {
            try {
                loadDriver();
                connection = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1/libraryManagementSystem", "root", "");
                JOptionPane.showMessageDialog(null, "Connection Establish ..!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Connection Faild..!" + ex.getMessage());
            }
        } else {

        }
        return connection;
    }

}
