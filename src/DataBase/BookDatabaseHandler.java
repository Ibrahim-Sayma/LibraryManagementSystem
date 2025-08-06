package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Book;

public class BookDatabaseHandler {

    public static ObservableList<Book> getBookData() {
        Connection connection = DatabaseConnection.getInstance();
        ObservableList<Book> tempBooks = FXCollections.observableArrayList();

        String query = "SELECT * FROM books";
        try (PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                tempBooks.add(new Book(
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
        } catch (SQLException ex) {
            Logger.getLogger(BookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tempBooks;
    }

    public static void addBook(Book book) {
        Connection connection = DatabaseConnection.getInstance();
        String query = "INSERT INTO books (title, author, category, isbn, publish_date, language, publisher, book_image_path, page_count, copy_count) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setString(4, book.getIsbn());
            ps.setString(5, book.getPublishDate());
            ps.setString(6, book.getLanguage());
            ps.setString(7, book.getPublisher());
            ps.setString(8, book.getBookImagePath());
            ps.setInt(9, book.getPageCount());
            ps.setInt(10, book.getCopyCount());

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateBook(Book book) {
        Connection connection = DatabaseConnection.getInstance();
        String query = "UPDATE books "
                + "SET title = ?, author = ?, category = ?, isbn = ?, publish_date = ?, language = ?, publisher = ?, book_image_path = ?, page_count = ?, copy_count = ? "
                + "WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setString(4, book.getIsbn());
            ps.setString(5, book.getPublishDate());
            ps.setString(6, book.getLanguage());
            ps.setString(7, book.getPublisher());
            ps.setString(8, book.getBookImagePath());
            ps.setInt(9, book.getPageCount());
            ps.setInt(10, book.getCopyCount());
            ps.setInt(11, book.getId());

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean updateCopyCount(int bookId, int newCopyCount) {
        Connection connection = DatabaseConnection.getInstance();
        String query = "UPDATE books SET copy_count = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, newCopyCount);
            ps.setInt(2, bookId);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            Logger.getLogger(BookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static void deleteBook(Book book) {
        Connection connection = DatabaseConnection.getInstance();
        String query = "DELETE FROM books WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, book.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ObservableList<String> getAllCategories() {
        Connection connection = DatabaseConnection.getInstance();
        ObservableList<String> categories = FXCollections.observableArrayList();

        String query = "SELECT category_name FROM categories";
        try (PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categories.add(rs.getString("category_name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    public static ObservableList<Book> getBooksByCategory(String category) {
        Connection connection = DatabaseConnection.getInstance();
        ObservableList<Book> booksByCategory = FXCollections.observableArrayList();

        String query = "SELECT * FROM books WHERE category = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, category);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    booksByCategory.add(new Book(
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
        return booksByCategory;
    }

    public static ObservableList<Book> getAllBooks() {
        Connection connection = DatabaseConnection.getInstance();
        ObservableList<Book> allBooks = FXCollections.observableArrayList();
        String query = "SELECT * FROM books";
        try (PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                allBooks.add(new Book(
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
        } catch (SQLException ex) {
            Logger.getLogger(BookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allBooks;
    }

    public static Book getBookById(int bookId) {
        Connection connection = DatabaseConnection.getInstance();
        String query = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Book(
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
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
