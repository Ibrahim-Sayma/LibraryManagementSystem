package DataBase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Book;

public class CategoryDatabaseHandler {

    public static ObservableList<String> getAllCategories() {
        ObservableList<String> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM categories";

        try (Connection conn = DatabaseConnection.getInstance();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getString("category_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void addCategory(String categoryName) {
        String insertQuery = "INSERT INTO categories (category_name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getInstance();
                PreparedStatement ps = conn.prepareStatement(insertQuery)) {

            ps.setString(1, categoryName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCategory(String categoryName) {
        String deleteQuery = "DELETE FROM categories WHERE category_name = ?";
        try (Connection conn = DatabaseConnection.getInstance();
                PreparedStatement ps = conn.prepareStatement(deleteQuery)) {

            ps.setString(1, categoryName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addBookToCategory(Book book) {
        Connection connection = DatabaseConnection.getInstance();

        try {

            String checkCategoryQuery = "SELECT id FROM categories WHERE category_name = ?";
            try (PreparedStatement psCheck = connection.prepareStatement(checkCategoryQuery)) {
                psCheck.setString(1, book.getCategory());
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (!rs.next()) {

                        addCategory(book.getCategory());
                    }
                }
            }

            BookDatabaseHandler.addBook(book);

        } catch (Exception e) {
            Logger.getLogger(CategoryDatabaseHandler.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
