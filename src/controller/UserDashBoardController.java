package controller;

import DataBase.BookDatabaseHandler;
import DataBase.BorrowBookDatabaseHandler;
import DataBase.BorrowBookDetailsDatabaseHandler;
import DataBase.DatabaseConnection;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Book;
import model.BorrowBook;
import model.BorrowBookDetaiels;
import static scenbuilder.ScenBuilder.Books;
import static scenbuilder.ScenBuilder.BorrowBooks;
import static scenbuilder.ScenBuilder.Categories;
import static scenbuilder.ScenBuilder.LoginStage;
import static scenbuilder.ScenBuilder.UserLogin;
import static scenbuilder.ScenBuilder.UserProfileStage;
import static scenbuilder.ScenBuilder.UserStage;
import static scenbuilder.ScenBuilder.borrowBookDetaiels;
import static scenbuilder.ScenBuilder.setStageInfo;

public class UserDashBoardController implements Initializable {

    @FXML
    private Label ShowSidebarHome;
    @FXML
    private AnchorPane AnchorPaneHome;
    @FXML
    private ImageView UserProfileImage;
    @FXML
    private Label UserProfileFullName;
    @FXML
    private Label SidebarBorrowBook;
    @FXML
    private Label SidebarBorrowedBook;
    @FXML
    private AnchorPane AnchorPaneBorrowBook;
    @FXML
    private AnchorPane AnchorPaneBorrowedBook;
    @FXML
    private TextField publisherTFBorrow;
    @FXML
    private TextField copyCountTFBorrow;
    @FXML
    private TextField pageCountTFBorrow;
    @FXML
    private TextField publishDataTFBorrow;
    @FXML
    private TextField IsbnTFBorrow;
    @FXML
    private TextField authorTFBorrow;
    @FXML
    private TextField titleTFBorrow;
    @FXML
    private ImageView BorrowBookImage;
    @FXML
    private TextField CategoryTFBorrow;
    @FXML
    private Button btn_Borrow;
    @FXML
    private ComboBox<String> BorrowBookCategoryFilter;
    @FXML
    private ComboBox<String> BrorowBookSelectFilter;
    public static int book_id = -1;
    @FXML
    private Label SidebarLogout;
    @FXML
    private HBox borrowedBooksContainer;
    Button PendingBtn = new Button("Pending");
    Button ReturnBtn = new Button("Return");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ShowSidebarHome.getStyleClass().add("selected-label");
        SidebarBorrowBook.getStyleClass().add("selected-label");
        SidebarBorrowedBook.getStyleClass().add("selected-label");
        SetSelectedSidebar(ShowSidebarHome, AnchorPaneHome);
        UserProfileImage.imageProperty().bind(
                Bindings.createObjectBinding(
                        () -> new Image(UserLogin.getProfileImagePath()), UserLogin.profileImagePathProperty()
                )
        );
        UserProfileFullName.textProperty().bind(UserLogin.FullNameProperty());
        SetBorrowBtnStatus(btn_Borrow, "btn_Borrow_Borrow", "Borrow", false);

        Categories.setAll(BookDatabaseHandler.getAllCategories());

    }

    @FXML
    private void ShowSidebarHome(MouseEvent e) {
        SetSelectedSidebar(ShowSidebarHome, AnchorPaneHome);
    }

    @FXML
    private void SidebarBorrowBook(MouseEvent event) {
        SetSelectedSidebar(SidebarBorrowBook, AnchorPaneBorrowBook);
    }

    @FXML
    private void SidebarBorrowedBook(MouseEvent event) {
        SetSelectedSidebar(SidebarBorrowedBook, AnchorPaneBorrowedBook);
        loadBorrowedBooks();
        ShowborrowedBook();
    }

    private void ShowborrowedBook() {
        borrowedBooksContainer.getChildren().clear();

        for (BorrowBookDetaiels bbd : borrowBookDetaiels) {
            if ("Approved".equals(bbd.getStatus())) {
                AchorPaneRetuenBook(bbd.getBookImage(), bbd.getBookTitle(), bbd.getStatus(), bbd);
                ReturnBtn.setOnAction(e -> {
                    BorrowBookDatabaseHandler.returnBook(bbd);
                });
            } else if ("Pending".equals(bbd.getStatus())) {
                AchorPaneRetuenBook(bbd.getBookImage(), bbd.getBookTitle(), bbd.getStatus(), bbd);
            }
        }
    }

    @FXML
    private void SidebarLogout(MouseEvent event) {
        System.exit(0);
    }

    public void SetSelectedSidebar(Label selectedLabel, AnchorPane selectedAnchorPane) {
        ShowSidebarHome.getStyleClass().remove("selectedLabel");
        SidebarBorrowBook.getStyleClass().remove("selectedLabel");
        SidebarBorrowedBook.getStyleClass().remove("selectedLabel");
        selectedLabel.getStyleClass().add("selectedLabel");
        AnchorPaneHome.setVisible(false);
        AnchorPaneBorrowBook.setVisible(false);
        AnchorPaneBorrowedBook.setVisible(false);
        selectedAnchorPane.setVisible(true);
        BorrowBookCategoryFilter.getItems().clear();
        BorrowBookCategoryFilter.getItems().addAll(Categories);
    }

    @FXML
    private void FilterBookOfCategory(ActionEvent event) {
        String SelectedCategory = BorrowBookCategoryFilter.getValue();
        BrorowBookSelectFilter.getItems().clear();
        if (SelectedCategory != null && !SelectedCategory.isEmpty()) {
            ObservableList<Book> filteredBooks = BookDatabaseHandler.getBooksByCategory(SelectedCategory);
            for (Book b : filteredBooks) {
                BrorowBookSelectFilter.getItems().add(b.getId() + "-" + b.getTitle());
            }
        }
    }

    @FXML
    private void logout(MouseEvent event) {
        UserStage.close();
        LoginStage.show();
    }

    @FXML
    private void Search(ActionEvent event) {
        boolean hasError = false;

        if (!VaildateCombobox(BorrowBookCategoryFilter)) {
            hasError = true;
        }
        if (!VaildateCombobox(BrorowBookSelectFilter)) {
            hasError = true;
        }

        if (!hasError) {
            String bookSelection = BrorowBookSelectFilter.getValue();
            String[] bookData = bookSelection.split("-");

            if (bookData.length < 2) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid book selection.");
                alert.showAndWait();
                return;
            }

            int bookId;
            try {
                bookId = Integer.parseInt(bookData[0]);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The book ID is invalid.");
                alert.showAndWait();
                return;
            }

            Book selectedBook = BookDatabaseHandler.getBookById(bookId);

            if (selectedBook != null) {
                book_id = bookId;
                titleTFBorrow.setText(selectedBook.getTitle());
                authorTFBorrow.setText(selectedBook.getAuthor());
                IsbnTFBorrow.setText(selectedBook.getIsbn());
                publisherTFBorrow.setText(selectedBook.getPublisher());
                publishDataTFBorrow.setText(selectedBook.getPublishDate());
                copyCountTFBorrow.setText(String.valueOf(selectedBook.getCopyCount()));
                pageCountTFBorrow.setText(String.valueOf(selectedBook.getPageCount()));
                CategoryTFBorrow.setText(selectedBook.getCategory());
                BorrowBookImage.setImage(new Image(selectedBook.getBookImagePath()));

                String status = CheckBorrowBookStatus(UserLogin.getId(), bookId);

                if (status == null) {
                    SetBorrowBtnStatus(btn_Borrow, "btn_Borrow_Borrow", "Borrow", false);
                } else if ("Pending".equals(status)) {
                    SetBorrowBtnStatus(btn_Borrow, "btn_Borrow_pending", "Pending", true);
                } else if ("Approved".equals(status)) {
                    SetBorrowBtnStatus(btn_Borrow, "btn_Borrow_Approved", "Approved", true);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The book is not available.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void BorrowBook(ActionEvent event) {
        boolean hasError = false;

        if (titleTFBorrow.getText().isEmpty()) {
            hasError = true;
            Alert alert = new Alert(Alert.AlertType.ERROR, "Title Field is Empty");
            alert.showAndWait();
        }

        if (!hasError) {
            int userId = UserLogin.getId();
            String userName = UserLogin.getFullName();
            String userImage = UserLogin.getProfileImagePath();

            int bookId = book_id;
            Book selectedBook = BookDatabaseHandler.getBookById(bookId);

            if (selectedBook != null) {
                String bookTitle = selectedBook.getTitle();
                String bookImage = selectedBook.getBookImagePath();

                String expectedDeleiverDate = "08/02/2025";

                BorrowBookDetaiels borrowDetails = new BorrowBookDetaiels(
                        userId,
                        bookId,
                        userName,
                        userImage,
                        bookTitle,
                        bookImage,
                        "Pending",
                        expectedDeleiverDate
                );

                boolean isInserted = BorrowBookDetailsDatabaseHandler.insertBorrowBookRequest(borrowDetails);

                if (isInserted) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "The book borrowing request has been successfully submitted. Status: Pending");
                    alert.showAndWait();

                    SetBorrowBtnStatus(btn_Borrow, "btn_Borrow_pending", "Pending", true);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to submit the book borrowing request. Please try again.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The selected book does not exist.");
                alert.showAndWait();
            }
        }
    }

    public Book getBookDetailes(int bid) {
        Book sb = null;
        for (Book b : Books) {
            if (b.getId() == bid) {
                sb = b;
            }
        }
        return sb;
    }

    @FXML
    private void clearBorrowBook(ActionEvent event) {
        titleTFBorrow.setText("");
        authorTFBorrow.setText("");
        IsbnTFBorrow.setText("");
        publisherTFBorrow.setText("");
        publishDataTFBorrow.setText("");
        copyCountTFBorrow.setText("");
        pageCountTFBorrow.setText("");
        CategoryTFBorrow.setText("");
        BorrowBookImage.setImage(new Image("/images/addBook.png"));
        BorrowBookCategoryFilter.setValue(null);
        BrorowBookSelectFilter.setValue(null);
        BorrowBookCategoryFilter.getStyleClass().removeAll("valid", "invalid");
        BrorowBookSelectFilter.getStyleClass().removeAll("valid", "invalid");
        SetBorrowBtnStatus(btn_Borrow, "btn_Borrow_Borrow", "Borrow", false);
        book_id = -1;
    }

    public void SetBorrowBtnStatus(Button btn, String ccsClass, String Status, boolean btnDisabel) {
        btn.setText(Status);
        btn.getStyleClass().removeAll("btn_Borrow_pending", "btn_Borrow_Approved", "btn_Borrow_Borrow");
        btn.getStyleClass().add(ccsClass);
        btn.setDisable(btnDisabel);
    }

    public boolean VaildateCombobox(ComboBox<String> cb) {
        boolean Validate = false;
        if (cb.getValue() == null) {
            cb.getStyleClass().removeAll("valid", "invalid");
            cb.getStyleClass().add("invalid");
        } else {
            cb.getStyleClass().removeAll("valid", "invalid");
            cb.getStyleClass().add("valid");
            Validate = true;
        }
        return Validate;
    }

    public Book getSelectedBook(int bid) {
        Book book = null;
        for (Book b : Books) {
            if (b.getId() == bid) {
                book = b;
            }
        }
        return book;
    }

    public String CheckBorrowBookStatus(int userId, int bookId) {
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
            Logger.getLogger(BorrowBookDatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @FXML
    private void ShowUserProfileStage(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/UserProfile.fxml"));
        Scene UserProfileScene = new Scene(root);
        setStageInfo(UserProfileStage, UserProfileScene, "login_Screen.png", "UserProfile Screen", 600, 150);
        UserStage.close();
        UserProfileStage.show();
    }

    public void AchorPaneRetuenBook(String ImagePath, String BookTitle, String Status, BorrowBookDetaiels borrowDetails) {
        Image img = new Image(ImagePath);
        ImageView imgv = new ImageView(img);
        imgv.setFitHeight(150);
        imgv.setFitWidth(150);

        Label titleLabel = new Label(BookTitle);
        titleLabel.getStyleClass().add("titleLabel");

        Label statusLabel = new Label("Status: " + Status);
        if ("Pending".equals(Status)) {
            statusLabel.getStyleClass().add("Pending-status");
        } else if ("Approved".equals(Status)) {
            statusLabel.getStyleClass().add("Approved-status");
        }

        Label Deleiver_Date = new Label();
        Deleiver_Date.getStyleClass().add("Deleiver_Date");

        Button actionButton = new Button();
        if ("Approved".equals(Status)) {
            actionButton.setText("Return");
            actionButton.getStyleClass().add("Return_btnStatus");
            actionButton.setDisable(false);
            Deleiver_Date.setText("Deleiver Date: " + borrowDetails.getDeleiverDate());
            actionButton.setOnAction(e -> {
                boolean isReturned = BorrowBookDatabaseHandler.returnBook(borrowDetails);
                if (isReturned) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "The book has been successfully returned and the borrowing request has been deleted!");
                    alert.showAndWait();

                    borrowedBooksContainer.getChildren().clear();
                    loadBorrowedBooks();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred while deleting the borrowing request. Please try again.");
                    alert.showAndWait();
                }

                System.out.println("s");
            });
        } else if ("Pending".equals(Status)) {
            actionButton.setText("Pending");
            actionButton.getStyleClass().add("Pending_btnStatus");
            Deleiver_Date.setText("Deleiver Date: N/A");
            actionButton.setDisable(true);
        }

        VBox vb = new VBox(10, imgv, titleLabel, Deleiver_Date, statusLabel, actionButton);
        vb.setPadding(new Insets(25));
        vb.setAlignment(Pos.CENTER);

        vb.getStyleClass().add("borrowedBooksContainer");

        borrowedBooksContainer.getChildren().add(vb);
    }

    private void loadBorrowedBooks() {
        borrowedBooksContainer.getChildren().clear();

        borrowBookDetaiels = BorrowBookDetailsDatabaseHandler.getAllBorrowBookDetails();

        for (BorrowBookDetaiels bbd : borrowBookDetaiels) {
            if ("Approved".equals(bbd.getStatus())) {
                AchorPaneRetuenBook(bbd.getBookImage(), bbd.getBookTitle(), bbd.getStatus(), bbd);
            } else if ("Pending".equals(bbd.getStatus())) {
                AchorPaneRetuenBook(bbd.getBookImage(), bbd.getBookTitle(), bbd.getStatus(), bbd);
            }
        }
    }
}
