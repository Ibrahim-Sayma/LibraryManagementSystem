package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

public class UserDatabaseHandler {

    public static ObservableList<User> getUserData() {
        Connection connection = DatabaseConnection.getInstance();
        ObservableList<User> tempUser = FXCollections.observableArrayList();
        String query = "SELECT * FROM users";

        try (PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                tempUser.add(new User(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("user_name"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("role"),
                        rs.getString("profile_image_path")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tempUser;
    }

    public static void addUser(User user) {
        Connection connection = DatabaseConnection.getInstance();
        String query = "INSERT INTO users (full_name, user_name, password, email, phone, role, profile_image_path)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getRole());
            ps.setString(7, user.getProfileImagePath());

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateUser(User user) {
        Connection connection = DatabaseConnection.getInstance();
        String query = "UPDATE users SET "
                + "full_name = ?, "
                + "user_name = ?, "
                + "password = ?, "
                + "email = ?, "
                + "phone = ?, "
                + "role = ?, "
                + "profile_image_path = ? "
                + "WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getRole());
            ps.setString(7, user.getProfileImagePath());
            ps.setInt(8, user.getId());

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteUser(User user) {
        Connection connection = DatabaseConnection.getInstance();
        String query = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, user.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
