/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DataBase.BorrowBookDetailsDatabaseHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.BorrowBookDetaiels;
import static scenbuilder.ScenBuilder.AdminStage;
import static scenbuilder.ScenBuilder.Books;
import static scenbuilder.ScenBuilder.BooksStatistaceStage;
import static scenbuilder.ScenBuilder.BorrowBooks;
import static scenbuilder.ScenBuilder.borrowBookDetaiels;
import static scenbuilder.ScenBuilder.filteredStatus;

/**
 * FXML Controller class
 *
 * @author ibrah
 */
public class BooksStatistaceController implements Initializable {

    @FXML
    private ImageView logout;
    @FXML
    private TableView<BorrowBookDetaiels> tabelViewBookStatistace;
    @FXML
    private TableColumn<BorrowBookDetaiels, Integer> userIdColumn;
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
    private TableColumn<BorrowBookDetaiels, String> BorrowStatusColumn;
    @FXML
    private TextField searchTextField;
    private String currentStatusFilter = "All";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableViewBookStatistace();
    }

    private void setupTableViewBookStatistace() {
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        BorrowStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

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

        tabelViewBookStatistace.setItems(borrowBookDetaiels);
    }

    @FXML
    private void logout(MouseEvent event) {
        BooksStatistaceStage.close();
        AdminStage.show();
    }

    public void setStatusFilter(String status) {
        filteredStatus.clear();

        if (status == null || status.equalsIgnoreCase("All")) {
            tabelViewBookStatistace.setItems(borrowBookDetaiels);
            currentStatusFilter = "All";
        } else {
            for (BorrowBookDetaiels book : borrowBookDetaiels) {
                if (book.getStatus().equalsIgnoreCase(status)) {
                    filteredStatus.add(book);
                }
            }
            tabelViewBookStatistace.setItems(filteredStatus);
            currentStatusFilter = status;
        }
    }

    @FXML
    private void Clear(ActionEvent event) {
        searchTextField.clear();
        filteredStatus.clear();
        setStatusFilter(currentStatusFilter);

    }

    @FXML
    private void SearchTextField(KeyEvent event) {
        String searchText = searchTextField.getText().toLowerCase();
        filteredStatus.clear();

        for (BorrowBookDetaiels book : borrowBookDetaiels) {
            boolean statusMatches = currentStatusFilter.equals("All") || book.getStatus().equalsIgnoreCase(currentStatusFilter);
            boolean userNameMatches = book.getUserName().toLowerCase().contains(searchText);
            boolean bookTitleMatches = book.getBookTitle().toLowerCase().contains(searchText);
            boolean userIdMatches = String.valueOf(book.getUserId()).contains(searchText);
            boolean bookIdMatches = String.valueOf(book.getBookID()).contains(searchText);
            boolean statusTextMatches = book.getStatus().toLowerCase().contains(searchText);

            if (statusMatches && (userNameMatches || bookTitleMatches || userIdMatches || bookIdMatches || statusTextMatches)) {
                filteredStatus.add(book);
            }
        }

        tabelViewBookStatistace.setItems(filteredStatus);
    }
}
