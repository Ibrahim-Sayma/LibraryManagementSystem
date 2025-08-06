package DataBase;

import controller.UserDashBoardController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Book;
import model.BorrowBook;
import model.BorrowBookDetaiels;

public class BorrowBookDatabaseHandler {

    public static ObservableList<Book> getBooksByCategory(String category) {
        ObservableList<Book> books = FXCollections.observableArrayList();
        String query = "SELECT * FROM books WHERE category = ?";
        try (Connection conn = DatabaseConnection.getInstance();
                PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, category);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    books.add(new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("category"),
                            rs.getString("isbn"),
                            rs.getString("publish_date"),
                            rs.getString("language"),
                            rs.getString("publisher"),
                            rs.getString("image_path"),
                            rs.getInt("page_count"),
                            rs.getInt("copy_count")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static void addBorrowBook(BorrowBook borrowBook) {
        Connection connection = DatabaseConnection.getInstance();
        String query = "INSERT INTO borrow_book (user_id, book_id) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, borrowBook.getUserId());
            ps.setInt(2, borrowBook.getBookId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BorrowBookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateBorrowBook(BorrowBook borrowBook, int oldUserId, int oldBookId) {
        Connection connection = DatabaseConnection.getInstance();
        String query = "UPDATE borrow_book SET user_id = ?, book_id = ? WHERE user_id = ? AND book_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, borrowBook.getUserId());
            ps.setInt(2, borrowBook.getBookId());
            ps.setInt(3, oldUserId);
            ps.setInt(4, oldBookId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BorrowBookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteBorrowBook(BorrowBook borrowBook) {
        Connection connection = DatabaseConnection.getInstance();
        String query = "DELETE FROM borrow_book WHERE user_id = ? AND book_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, borrowBook.getUserId());
            ps.setInt(2, borrowBook.getBookId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BorrowBookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ObservableList<Book> searchBooks(String category, String searchTerm) {
        Connection connection = DatabaseConnection.getInstance();
        ObservableList<Book> searchResults = FXCollections.observableArrayList();
        String query = "SELECT * FROM books WHERE category = ? AND (title LIKE ? OR author LIKE ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, category);
            ps.setString(2, "%" + searchTerm + "%");
            ps.setString(3, "%" + searchTerm + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    searchResults.add(new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("category"),
                            rs.getString("isbn"),
                            rs.getString("publish_date"),
                            rs.getString("language"),
                            rs.getString("publisher"),
                            rs.getString("book_image_path"),
                            rs.getInt("page_count"),
                            rs.getInt("copy_count")
                    ));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return searchResults;
    }

    public static boolean returnBook(BorrowBookDetaiels borrowDetails) {
        Connection connection = DatabaseConnection.getInstance();
        String deleteQuery = "DELETE FROM borrow_book_details WHERE user_id = ? AND book_id = ?";
        String updateCopyQuery = "UPDATE books SET copy_count = copy_count + 1 WHERE id = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                deleteStmt.setInt(1, borrowDetails.getUserId());
                deleteStmt.setInt(2, borrowDetails.getBookID());
                int rowsDeleted = deleteStmt.executeUpdate();
                if (rowsDeleted == 0) {
                    connection.rollback();
                    return false;
                }
            }

            try (PreparedStatement updateStmt = connection.prepareStatement(updateCopyQuery)) {
                updateStmt.setInt(1, borrowDetails.getBookID());
                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated == 0) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(BorrowBookDetailsDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException e) {
                Logger.getLogger(BorrowBookDetailsDatabaseHandler.class.getName()).log(Level.SEVERE, null, e);
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                Logger.getLogger(BorrowBookDetailsDatabaseHandler.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

}
