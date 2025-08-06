/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
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
import model.User;
import static scenbuilder.ScenBuilder.AdminStage;
import static scenbuilder.ScenBuilder.Users;
import static scenbuilder.ScenBuilder.UsersStatistaceStage;
import static scenbuilder.ScenBuilder.filteredUsers;

/**
 * FXML Controller class
 *
 * @author ibrah
 */
public class UsersStatistaceController implements Initializable {

    @FXML
    private ImageView logout;
    @FXML
    private TableColumn<User, ImageView> imageColumn;
    @FXML
    private TableColumn<User, String> fullNameColumn;
    @FXML
    private TableColumn<User, String> userNameColumn;
    @FXML
    private TableColumn<User, String> roleColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> phoneColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableView<User> tabelViewUserStatistace;
    @FXML
    private TextField searchTextField;
    private String currentRoleFilter = "All";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTabelViewUserStatistace();
    }

    private void setupTabelViewUserStatistace() {
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        imageColumn.setCellValueFactory(cellData -> {
            ImageView imageview = new ImageView(cellData.getValue().getProfileImagePath());
            imageview.setFitWidth(40);
            imageview.setFitHeight(40);

            return new SimpleObjectProperty<>(imageview);
        });
        tabelViewUserStatistace.setItems(Users);
    }

    @FXML
    private void logout(MouseEvent event) {
        UsersStatistaceStage.close();
        AdminStage.show();
    }

    public void setRoleFilter(String role) {
        filteredUsers.clear();

        if (role == null || role.equalsIgnoreCase("All")) {
            tabelViewUserStatistace.setItems(Users);
            currentRoleFilter = "All";
            return;
        }

        for (User user : Users) {
            if (user.getRole().equalsIgnoreCase(role)) {
                filteredUsers.add(user);
            }
        }
        tabelViewUserStatistace.setItems(filteredUsers);
        currentRoleFilter = role;

    }

    @FXML
    private void clearTF(ActionEvent event) {
        searchTextField.clear();
        filteredUsers.clear();
        setRoleFilter(currentRoleFilter);
    }

    @FXML
    private void SearchTextField(KeyEvent event) {
        String searchText = searchTextField.getText().toLowerCase();
        filteredUsers.clear();

        for (User user : Users) {
            boolean roleMatches = currentRoleFilter.equals("All") || user.getRole().equalsIgnoreCase(currentRoleFilter);

            boolean fullNameMatches = user.getFullName().toLowerCase().contains(searchText);
            boolean userNameMatches = user.getUserName().toLowerCase().contains(searchText);
            boolean roleTextMatches = user.getRole().toLowerCase().contains(searchText);
            boolean phoneMatches = user.getPhone().contains(searchText);
            boolean passwordMatches = user.getPassword().toLowerCase().contains(searchText);
            boolean emailMatches = user.getEmail().toLowerCase().contains(searchText);

            if (roleMatches && (fullNameMatches || userNameMatches || roleTextMatches || phoneMatches || passwordMatches || emailMatches)) {
                filteredUsers.add(user);
            }
        }
        tabelViewUserStatistace.setItems(filteredUsers);
    }

}
