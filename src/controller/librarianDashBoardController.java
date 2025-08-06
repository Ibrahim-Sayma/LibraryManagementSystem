package controller;

import DataBase.BookDatabaseHandler;
import DataBase.BorrowBookDetailsDatabaseHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.Book;
import model.BorrowBookDetaiels;
import static scenbuilder.ScenBuilder.Books;
import static scenbuilder.ScenBuilder.BooksStatistaceStage;
import static scenbuilder.ScenBuilder.LibrarianStage;
import static scenbuilder.ScenBuilder.LoginStage;
import static scenbuilder.ScenBuilder.UserLogin;
import static scenbuilder.ScenBuilder.UserProfileStage;
import static scenbuilder.ScenBuilder.borrowBookDetaiels;
import static scenbuilder.ScenBuilder.setStageInfo;

public class librarianDashBoardController implements Initializable {

    @FXML
    private Label ShowSidebarHome;
    @FXML
    private AnchorPane AnchorPaneHome;
    @FXML
    private ImageView UserProfileImage;
    @FXML
    private Label UserProfileFullName;
    @FXML
    private Label SidebarBorrowBookMangement;
    @FXML
    private Label SidebarStatistisc;
    @FXML
    private TableView<BorrowBookDetaiels> BorrowBookMangemenTabelView;
    @FXML
    private TableColumn<BorrowBookDetaiels, Integer> userIDColumn;
    @FXML
    private TableColumn<BorrowBookDetaiels, String> userNameColumn;
    @FXML
    private TableColumn<BorrowBookDetaiels, ImageView> userImageColumn;
    @FXML
    private TableColumn<BorrowBookDetaiels, Integer> bookIdColumn;
    @FXML
    private TableColumn<BorrowBookDetaiels, String> bookTitleColumn;
    @FXML
    private TableColumn<BorrowBookDetaiels, ImageView> bookImageColumn;
    @FXML
    private TableColumn<BorrowBookDetaiels, Label> borrowStatusColumn;
    @FXML
    private TableColumn<BorrowBookDetaiels, HBox> actionColumn;
    @FXML
    private AnchorPane AnchorPaneBorrowBookMangement;
    @FXML
    private AnchorPane AnchorPaneStatistisc;
    @FXML
    private ComboBox<String> filterStatus;
    @FXML
    private Label SidebarLogout;
    @FXML
    private Label labelAllBook;
    @FXML
    private Label labelAllBorowedBook;
    @FXML
    private Label labelPendingBook;
    @FXML
    private Label labelAprovedBook;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ShowSidebarHome.getStyleClass().add("selected-label");
        SidebarBorrowBookMangement.getStyleClass().add("selected-label");
        SidebarStatistisc.getStyleClass().add("selected-label");
        SetSelectedSidebar(ShowSidebarHome, AnchorPaneHome);

//        -- set user login data --
//        UserProfileImage.setImage(new Image(UserLogin.getProfileImagePath()));
//        UserProfileFullName.setText(UserLogin.getFullName());
        UserProfileImage.imageProperty().bind(
                Bindings.createObjectBinding(
                        () -> new Image(UserLogin.getProfileImagePath()), UserLogin.profileImagePathProperty()
                ));
        UserProfileFullName.textProperty().bind(UserLogin.FullNameProperty());

        loadBorrowBookDetailsFromDatabase();

    }

    private void loadBorrowBookDetailsFromDatabase() {
        ObservableList<BorrowBookDetaiels> dbBorrowDetails = BorrowBookDetailsDatabaseHandler.getAllBorrowBookDetails();
        borrowBookDetaiels.setAll(dbBorrowDetails);
    }

    
    @FXML
    private void ShowSidebarHome(MouseEvent e) {
        SetSelectedSidebar(ShowSidebarHome, AnchorPaneHome);
    }

    @FXML
    private void SidebarBorrowBookMangement(MouseEvent event) {
        SetSelectedSidebar(SidebarBorrowBookMangement, AnchorPaneBorrowBookMangement);

    }

    @FXML
    private void SidebarStatistisc(MouseEvent event) {
        SetSelectedSidebar(SidebarStatistisc, AnchorPaneStatistisc);
        ShowStatisticeStage();
    }

    @FXML
    private void SidebarLogout(MouseEvent event) {
        System.exit(0);
    }

    public void SetSelectedSidebar(Label selectedLabel, AnchorPane selectedAnchorPane) {
        ShowSidebarHome.getStyleClass().remove("selectedLabel");
        SidebarBorrowBookMangement.getStyleClass().remove("selectedLabel");
        SidebarStatistisc.getStyleClass().remove("selectedLabel");
        selectedLabel.getStyleClass().add("selectedLabel");

//        -- AnchorPane --
        AnchorPaneHome.setVisible(false);
        AnchorPaneBorrowBookMangement.setVisible(false);
        AnchorPaneStatistisc.setVisible(false);
        selectedAnchorPane.setVisible(true);

     
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        borrowStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        userImageColumn.setCellValueFactory(cellData -> {
            ImageView imageview = new ImageView(cellData.getValue().getUserImage());
            imageview.setFitWidth(40);
            imageview.setFitHeight(40);

            return new SimpleObjectProperty<>(imageview);
        });
        bookImageColumn.setCellValueFactory(cellData -> {
            ImageView imageview = new ImageView(cellData.getValue().getBookImage());
            imageview.setFitWidth(40);
            imageview.setFitHeight(40);

            return new SimpleObjectProperty<>(imageview);
        });
        filterStatus.getItems().clear();
        filterStatus.getItems().addAll("All", "Pending", "Approved", "Reject");

//        -- label Column --
        borrowStatusColumn.setCellValueFactory(cellData -> {
            String statusText = cellData.getValue().getStatus();
            Label borrowStatus = new Label(statusText);
            borrowStatus.getStyleClass().removeAll("Pending_borrowStatus", "Approved_borrowStatus");
            if (statusText.equals("Pending")) {
                borrowStatus.getStyleClass().add("Pending_borrowStatus");
            }
            if (statusText.equals("Approved")) {
                borrowStatus.getStyleClass().add("Approved_borrowStatus");
            }
            if (statusText.equals("Reject")) {
                borrowStatus.getStyleClass().add("Rejected_borrowStatus");
            }
            return new SimpleObjectProperty<>(borrowStatus);
        });

        actionColumn.setCellValueFactory(cellData -> {
            HBox hb = new HBox();
            String statusText = cellData.getValue().getStatus();
            BorrowBookDetaiels bd = cellData.getValue();

            if (statusText.equals("Pending")) {
                Button approvedBtn = new Button("Approve");
                Button rejectBtn = new Button("Reject");
                approvedBtn.getStyleClass().add("Approved_btnStatus");
                rejectBtn.getStyleClass().add("Rejected_btnStatus");

                approvedBtn.setOnAction(e -> {
                    boolean enoughCopy = changeBookCopyNumber(bd.getBookID());
                    if (!enoughCopy) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "There are not enough copies of the book.");
                        alert.showAndWait();
                        boolean updated = BorrowBookDetailsDatabaseHandler.updateBorrowBookStatus(bd.getUserId(), bd.getBookID(), "Reject");
                        if (updated) {
                            bd.setStatus("Reject");
                            BorrowBookMangemenTabelView.refresh();
                        }
                    } else {
                        boolean updated = BorrowBookDetailsDatabaseHandler.updateBorrowBookStatus(bd.getUserId(), bd.getBookID(), "Approved");
                        if (updated) {
                            bd.setStatus("Approved");
                            BorrowBookMangemenTabelView.refresh();
                        }
                    }
                });

                rejectBtn.setOnAction(e -> {
                    BorrowBookDetailsDatabaseHandler.deleteBorrowBookDetaiels(bd);
                    borrowBookDetaiels.remove(bd);
                    BorrowBookMangemenTabelView.getItems().remove(bd);
                });

                hb.setSpacing(10);
                hb.getChildren().addAll(approvedBtn, rejectBtn);
            } else if (statusText.equals("Approved")) {
                Button approvedBtn = new Button("Approved Book");
                approvedBtn.setDisable(true);
                approvedBtn.getStyleClass().add("Approved_btnStatus");
                hb.getChildren().add(approvedBtn);
            } else if (statusText.equals("Reject")) {
                Button rejectBtn = new Button("Rejected Book");
                rejectBtn.setDisable(true);
                rejectBtn.getStyleClass().add("Rejected_btnStatus");
                hb.getChildren().add(rejectBtn);
            }

            return new SimpleObjectProperty<>(hb);
        }
        );
        BorrowBookMangemenTabelView.setItems(borrowBookDetaiels);
    }

    @FXML

    private void logout(MouseEvent event) {
        LibrarianStage.close();
        LoginStage.show();
    }

    @FXML
    private void ShowUserProfileStage(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/UserProfile.fxml"));
        Scene UserProfileScene = new Scene(root);
        setStageInfo(UserProfileStage, UserProfileScene, "login_Screen.png", "UserProfile Screen", 600, 150);
        LibrarianStage.close();
        UserProfileStage.show();
    }

    @FXML
    private void SetFilterStatus(ActionEvent event) {
        String status = filterStatus.getValue();
        ObservableList<BorrowBookDetaiels> tempborrowBookDetaiels = FXCollections.observableArrayList();
        tempborrowBookDetaiels.clear();
        for (BorrowBookDetaiels bbd : borrowBookDetaiels) {
            if (status.equals("All")) {
                tempborrowBookDetaiels.add(bbd);
                continue;
            }
            if (bbd.getStatus().equals(status)) {
                tempborrowBookDetaiels.add(bbd);
            }
        }
        BorrowBookMangemenTabelView.setItems(tempborrowBookDetaiels);
    }

    public boolean changeBookCopyNumber(int bid) {
        Book book = BookDatabaseHandler.getBookById(bid);
        if (book != null && book.getCopyCount() > 0) {
            int newCopyCount = book.getCopyCount() - 1;
            boolean updated = BookDatabaseHandler.updateCopyCount(bid, newCopyCount);
            if (updated) {
                for (int i = 0; i < Books.size(); i++) {
                    if (Books.get(i).getId() == bid) {
                        Books.get(i).setCopyCount(newCopyCount);
                        Books.set(i, Books.get(i));
                        return true;
                    }
                }
                Alert alert = new Alert(Alert.AlertType.ERROR, "The book was not found in the list: ID " + bid);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to update the number of copies for the book: ID " + bid);
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The book does not exist or there are no available copies: ID " + bid);
            alert.showAndWait();
        }
        return false;
    }

    private void ShowStatisticeStage() {
        labelAllBook.setText("");
        labelAllBorowedBook.setText("");
        labelAprovedBook.setText("");
        labelPendingBook.setText("");
        int numAllBook = 0;
        int numBorowed = 0;
        int numAproved = 0;
        int numPending = 0;
        for (BorrowBookDetaiels book : borrowBookDetaiels) {
            if (book.getStatus().equals("Borrow")) {
                numBorowed += 1;
                labelAllBorowedBook.setText(Integer.toString(numBorowed));
                numAllBook += 1;
                labelAllBook.setText(Integer.toString(numAllBook));

            } else if (book.getStatus().equals("Approved")) {
                numAproved += 1;
                labelAprovedBook.setText(Integer.toString(numAproved));
                numAllBook += 1;
                labelAllBook.setText(Integer.toString(numAllBook));

            } else if (book.getStatus().equals("Pending")) {
                numPending += 1;
                labelPendingBook.setText(Integer.toString(numPending));
                numAllBook += 1;
                labelAllBook.setText(Integer.toString(numAllBook));
            }
        }

        boolean hasError = false;

        if (labelAllBook.getText() == null || labelAllBook.getText().isEmpty()) {
            numAllBook = 0;
            labelAllBook.setText(Integer.toString(numAllBook));
            hasError = true;
        }
        if (labelAllBorowedBook.getText() == null || labelAllBorowedBook.getText().isEmpty()) {
            numBorowed = 0;
            labelAllBorowedBook.setText(Integer.toString(numBorowed));
            hasError = true;
        }
        if (labelAprovedBook.getText() == null || labelAprovedBook.getText().isEmpty()) {
            numAproved = 0;
            labelAprovedBook.setText(Integer.toString(numAproved));
            hasError = true;
        }
        if (labelPendingBook.getText() == null || labelPendingBook.getText().isEmpty()) {
            numPending = 0;
            labelPendingBook.setText(Integer.toString(numPending));
            hasError = true;
        }
        if (!hasError) {
            labelAllBook.setText(Integer.toString(numAllBook));
            labelAllBorowedBook.setText(Integer.toString(numBorowed));
            labelAprovedBook.setText(Integer.toString(numAproved));
            labelPendingBook.setText(Integer.toString(numPending));
        }
    }

    @FXML
    private void ShowAllBorowedBook(MouseEvent event) throws IOException {
        loadBooksStatistace("Borrow");
    }

    @FXML
    private void ShowPendingBook(MouseEvent event) throws IOException {
        loadBooksStatistace("Pending");
    }

    @FXML
    private void ShowAprovedBook(MouseEvent event) throws IOException {
        loadBooksStatistace("Approved");
    }

    private void loadBooksStatistace(String filter) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BooksStatistace.fxml"));
        Parent booksStatistaceRoot = loader.load();
        BooksStatistaceController booksStatistaceController = loader.getController();
        booksStatistaceController.setStatusFilter(filter);
        Scene booksStatistaceScene = new Scene(booksStatistaceRoot);
        setStageInfo(BooksStatistaceStage, booksStatistaceScene, "library.png", "Book Statistace Screen", 200, 50);
        BooksStatistaceStage.show();
    }

}
