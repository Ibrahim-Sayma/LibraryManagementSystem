package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.BorrowBookDetaiels;

public class BorrowBookDetailsDatabaseHandler {

    public static boolean insertBorrowBookRequest(BorrowBookDetaiels borrowDetails) {
        Connection connection = DatabaseConnection.getInstance();
        String query = "INSERT INTO borrow_book_details (user_id, book_id, user_name, user_image, book_title, book_image, status, Deleiver_Date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, borrowDetails.getUserId());
            ps.setInt(2, borrowDetails.getBookID());
            ps.setString(3, borrowDetails.getUserName());
            ps.setString(4, borrowDetails.getUserImage());
            ps.setString(5, borrowDetails.getBookTitle());
            ps.setString(6, borrowDetails.getBookImage());
            ps.setString(7, borrowDetails.getStatus());
            ps.setString(8, borrowDetails.getDeleiverDate());

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            Logger.getLogger(BorrowBookDetailsDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static String getBorrowBookStatus(int userId, int bookId) {
        Connection connection = DatabaseConnection.getInstance();
        String query = "SELECT status FROM borrow_book_details WHERE user_id = ? AND book_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BorrowBookDetailsDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean updateBorrowBookStatus(int userId, int bookId, String status) {
        Connection connection = DatabaseConnection.getInstance();
        System.out.println("sd");
        String query = "UPDATE borrow_book_details SET status = ? WHERE user_id = ? AND book_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setInt(2, userId);
            ps.setInt(3, bookId);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(BorrowBookDetailsDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static ObservableList<BorrowBookDetaiels> getAllBorrowBookDetails() {
        Connection connection = DatabaseConnection.getInstance();
        ObservableList<BorrowBookDetaiels> borrowDetailsList = FXCollections.observableArrayList();
        String query = "SELECT * FROM borrow_book_details";
        try (PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                BorrowBookDetaiels details = new BorrowBookDetaiels(
                        rs.getInt("user_id"),
                        rs.getInt("book_id"),
                        rs.getString("user_name"),
                        rs.getString("user_image"),
                        rs.getString("book_title"),
                        rs.getString("book_image"),
                        rs.getString("status"),
                        rs.getString("Deleiver_Date")
                );
                borrowDetailsList.add(details);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BorrowBookDetailsDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return borrowDetailsList;
    }

    public static void deleteBorrowBookDetaiels(BorrowBookDetaiels borrowBookDetaiels) {
        Connection connection = DatabaseConnection.getInstance();
        String query = "DELETE FROM borrow_book_details WHERE user_id = ? AND book_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, borrowBookDetaiels.getUserId());
            ps.setInt(2, borrowBookDetaiels.getBookID());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BorrowBookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
