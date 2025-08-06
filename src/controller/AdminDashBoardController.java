package controller;

import DataBase.BookDatabaseHandler;
import DataBase.CategoryDatabaseHandler;
import DataBase.UserDatabaseHandler;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import model.Book;
import model.User;
import scenbuilder.ScenBuilder;
import static scenbuilder.ScenBuilder.AdminStage;
import static scenbuilder.ScenBuilder.Books;
import static scenbuilder.ScenBuilder.Categories;
import static scenbuilder.ScenBuilder.LoginStage;
import static scenbuilder.ScenBuilder.UserLogin;
import static scenbuilder.ScenBuilder.UserProfileStage;
import static scenbuilder.ScenBuilder.Users;
import static scenbuilder.ScenBuilder.UsersStatistaceStage;
import static scenbuilder.ScenBuilder.setStageInfo;
import static scenbuilder.ScenBuilder.newCatgoryStage;

public class AdminDashBoardController implements Initializable {

    @FXML
    private Label ShowSidebarHome;
    @FXML
    private Label SidebarUserManagement;
    @FXML
    private Label SidebarBookManagement;
    @FXML
    private AnchorPane AnchorPaneHome;
    @FXML
    private AnchorPane AnchorPaneUserManagement;
    @FXML
    private AnchorPane AnchorPaneBookManagement;
    @FXML
    private ImageView UserProfileImage;
    @FXML
    private Label UserProfileFullName;
    @FXML
    private ImageView logout;
    @FXML
    private ImageView profileImg;
    @FXML
    private Label imageLabelError;
    @FXML
    private TextField fullNameF;
    @FXML
    private Label fullNameLError;
    @FXML
    private ComboBox<String> rolesCombo;
    @FXML
    private Label roleLabelError;
    @FXML
    private TextField userNameF;
    @FXML
    private Label userNameLE;
    @FXML
    private PasswordField passwordF;
    @FXML
    private Label passwordLE;
    @FXML
    private TextField emailF;
    @FXML
    private Label emailLE;
    @FXML
    private TextField phoneF;
    @FXML
    private Label phonoLE;
    @FXML
    private ComboBox<String> shufelCombo;
    @FXML
    private TableView<User> tabelViewUser;
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
    Image[] userImageName = {null};
    Image[] bookImage = {null};

    String userimageName = null;
    String bookimageName = null;
    @FXML
    private ComboBox<String> bookFilter;
    @FXML
    private ImageView bookCategoryImg;
    @FXML
    private TableView<Book> tabelViewBook;
    @FXML
    private TableColumn<Book, ImageView> imageBookColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> categoryColumn;
    @FXML
    private TableColumn<Book, String> languageColumn;
    @FXML
    private TableColumn<Book, String> isbnColumn;
    @FXML
    private TableColumn<Book, Integer> pageCountColumn;
    @FXML
    private TableColumn<Book, Integer> copyCountColumn;
    @FXML
    private ImageView forBookImg;
    @FXML
    private Label forBookImgLabelError;
    @FXML
    private Label forCategoryComboError;
    @FXML
    private Label forLanguagesComboError;
    @FXML
    private TextField titleTF;
    @FXML
    private Label titleLabelError;
    @FXML
    private TextField authorTF;
    @FXML
    private Label authorLabelError;
    @FXML
    private TextField IsbnTF;
    @FXML
    private Label IsbnLabelError;
    @FXML
    private TextField publishDataTF;
    @FXML
    private Label publishDataLabelError;
    @FXML
    private TextField pageCountTF;
    @FXML
    private Label PageCountLabelError;
    @FXML
    private TextField copyCountTF;
    @FXML
    private Label copyCountLabelError;
    @FXML
    private TextField publisherTF;
    @FXML
    private Label publisherLabelError;
    @FXML
    private ComboBox<String> LanguagesCombo;
    @FXML
    private ComboBox<String> FormCategoryCombo;
    @FXML
    private Label SidebarStatistice;
    @FXML
    private Label SidebarLogout;
    @FXML
    private AnchorPane AnchorPaneStatistice;
    @FXML
    private Label labelAllUser;
    @FXML
    private Label labelAdmins;
    @FXML
    private Label labelLibriran;
    @FXML
    private Label labelUser;
    private UsersStatistaceController usersStatistaceController;
    @FXML
    private Button addBook;
    @FXML
    private Button deleteBook;
    @FXML
    private Button updataBook;
    @FXML
    private Button cancelBook;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ShowSidebarHome.getStyleClass().add("selected-label");
        SidebarUserManagement.getStyleClass().add("selected-label");
        SidebarBookManagement.getStyleClass().add("selected-label");
        SidebarStatistice.getStyleClass().add("selected-label");
        SetSelectedSidebar(ShowSidebarHome, AnchorPaneHome);
//        -- set user login data --
//        UserProfileImage.setImage(new Image(UserLogin.getProfileImagePath()));
//        UserProfileFullName.setText(UserLogin.getFullName());
        UserProfileImage.imageProperty().bind(
                Bindings.createObjectBinding(
                        () -> new Image(UserLogin.getProfileImagePath()), UserLogin.profileImagePathProperty()
                ));
        UserProfileFullName.textProperty().bind(UserLogin.FullNameProperty());

//        -- set Role Data --
        rolesCombo.getItems().addAll("Admin", "User", "Librian");
        rolesCombo.setValue("User");
        shufelCombo.getItems().addAll("All", "Admin", "User", "Librarian");

//        -- set cell data for user tabel-
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

        tabelViewUser.setItems(Users);

//        -- set formater --
        copyCountTF.setTextFormatter(new TextFormatter<>(change
                -> change.getControlNewText().matches("^[0-9]*$") ? change : null));

        pageCountTF.setTextFormatter(new TextFormatter<>(change
                -> change.getControlNewText().matches("^[0-9]*$") ? change : null));

//        -- set book Languages --
        LanguagesCombo.getItems().addAll("AR", "EN");
//        -- set book Form Category --
        Books.setAll(BookDatabaseHandler.getBookData());
        tabelViewBook.setItems(Books);
//        LanguagesCombo.getItems().addAll("EN", "AR");
//        Categories.add("Story");
//        Categories.add("lang");
//        FormCategoryCombo.getItems().add("All");
//        FormCategoryCombo.getItems().addAll(ScenBuilder.Categories);
//        bookFilter.getItems().addAll(ScenBuilder.Categories);

        Categories.setAll(BookDatabaseHandler.getAllCategories());
        FormCategoryCombo.getItems().addAll(ScenBuilder.Categories);
        bookFilter.getItems().addAll(ScenBuilder.Categories);

//        Books.add(new Book(1, "java", "Ibrahim", FormCategoryCombo.getItems().get(0), "27", "20", LanguagesCombo.getItems().get(0), "dd", "/images/book1.png", 200, 400));
//        Books.add(new Book(2, "php", "ali", FormCategoryCombo.getItems().get(1), "27", "20", LanguagesCombo.getItems().get(1), "dd", "/images/book2.png", 200, 400));
//        -- set book Category filter -- 
//        bookFilter.getItems().addAll(ScenBuilder.Categories);
//        -- set cell data for book tabel-
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        pageCountColumn.setCellValueFactory(new PropertyValueFactory<>("pageCount"));
        copyCountColumn.setCellValueFactory(new PropertyValueFactory<>("copyCount"));
        languageColumn.setCellValueFactory(new PropertyValueFactory<>("language"));
        imageBookColumn.setCellValueFactory(cellData -> {
            ImageView imageview = new ImageView(cellData.getValue().getBookImagePath());
            imageview.setFitWidth(40);
            imageview.setFitHeight(40);

            return new SimpleObjectProperty<>(imageview);
        });

        tabelViewBook.setItems(Books);

    }

    public void SetSelectedSidebar(Label selectedLabel, AnchorPane selectedAnchorPane) {
        ShowSidebarHome.getStyleClass().remove("selectedLabel");
        SidebarUserManagement.getStyleClass().remove("selectedLabel");
        SidebarBookManagement.getStyleClass().remove("selectedLabel");
        SidebarStatistice.getStyleClass().remove("selectedLabel");
        selectedLabel.getStyleClass().add("selectedLabel");

//        -- AnchorPane --
        AnchorPaneHome.setVisible(false);
        AnchorPaneUserManagement.setVisible(false);
        AnchorPaneBookManagement.setVisible(false);
        AnchorPaneStatistice.setVisible(false);
        selectedAnchorPane.setVisible(true);
    }

    @FXML
    private void ShowSidebarHome(MouseEvent e) {
        SetSelectedSidebar(ShowSidebarHome, AnchorPaneHome);
    }

    @FXML
    private void SidebarUserManagement(MouseEvent e) {
        SetSelectedSidebar(SidebarUserManagement, AnchorPaneUserManagement);
    }

    @FXML
    private void SidebarBookManagement(MouseEvent e) {
        SetSelectedSidebar(SidebarBookManagement, AnchorPaneBookManagement);
    }

    @FXML
    private void SidebarStatistice(MouseEvent event) {
        SetSelectedSidebar(SidebarStatistice, AnchorPaneStatistice);
        ShowStatisticeStage();
    }

    @FXML
    private void SidebarLogout(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void logout(MouseEvent event) {
        AdminStage.close();
        LoginStage.show();
    }

    @FXML
    private void UploadUserImageProfile(MouseEvent event) {
        FileChooser fileChosser = new FileChooser();
        File file = fileChosser.showOpenDialog(AdminStage);
        if (file != null) {
            userImageName[0] = new Image(file.toURI().toString());
            profileImg.setImage(userImageName[0]);
            this.userimageName = "/images/" + file.getName();
            try {
                saveImage(userImageName[0], file.getName());
            } catch (IOException ex) {
                Logger.getLogger(ScenBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void addUser(ActionEvent event) {
        fullNameLError.setText("");
        userNameLE.setText("");
        passwordLE.setText("");
        emailLE.setText("");
        phonoLE.setText("");
        imageLabelError.setText("");
        roleLabelError.setText("");

        boolean hasError = false;
        if (fullNameF.getText().isEmpty()) {
            fullNameLError.setText("Full Name is required..!");
            hasError = true;
        }
        if (userNameF.getText().isEmpty()) {
            userNameLE.setText("User Name is required..!");
            hasError = true;
        }
        if (passwordF.getText().isEmpty()) {
            passwordLE.setText("Password is required..!");
            hasError = true;
        }
        if (emailF.getText().isEmpty()) {
            emailLE.setText("Email is required..!");
            hasError = true;
        }
        if (phoneF.getText().isEmpty()) {
            phonoLE.setText("Phone is required..!");
            hasError = true;
        }

        if (this.userimageName == null) {
            imageLabelError.setText("Image is required..!");
            hasError = true;
        }

        if (!hasError) {
            boolean isFound = userExist(userNameF.getText(), passwordF.getText());
            boolean sameUserNameFound = sameUserNameFound(userNameF.getText());

            if (!sameUserNameFound && !isFound) {
                User newUser = new User(fullNameF.getText(), userNameF.getText(), passwordF.getText(),
                        emailF.getText(), phoneF.getText(), rolesCombo.getValue(),
                        this.userimageName != null ? this.userimageName : "/images/addUser.png");

                UserDatabaseHandler.addUser(newUser);

                Users.setAll(UserDatabaseHandler.getUserData());
                tabelViewUser.setItems(Users);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "-!- User has been ADD in DB -!-");
                alert.showAndWait();

                fullNameF.clear();
                userNameF.clear();
                passwordF.clear();
                emailF.clear();
                phoneF.clear();
                profileImg.setImage(new Image(getClass().getResourceAsStream("/images/addUser.png")));
                this.userimageName = null;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "!... User already exists ...!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void deleteUser(ActionEvent event) {
        User selectedUser = tabelViewUser.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            UserDatabaseHandler.deleteUser(selectedUser);

            Users.setAll(UserDatabaseHandler.getUserData());
            tabelViewUser.setItems(Users);

            fullNameF.clear();
            userNameF.clear();
            passwordF.clear();
            emailF.clear();
            phoneF.clear();
            profileImg.setImage(new Image(getClass().getResourceAsStream("/images/addUser.png")));

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "User has been deleted from DB!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "!... Select user to delete ...!");
            alert.showAndWait();
        }
    }

    @FXML
    private void updataUser(ActionEvent event) {
        User selectedUser = tabelViewUser.getSelectionModel().getSelectedItem();

        boolean sameUserAuth = false;
        int userIndex = 0;

        if (selectedUser != null) {
            fullNameLError.setText("");
            userNameLE.setText("");
            passwordLE.setText("");
            emailLE.setText("");
            phonoLE.setText("");
            imageLabelError.setText("");
            roleLabelError.setText("");

            boolean hasError = false;

            if (fullNameF.getText().isEmpty()) {
                fullNameLError.setText("Full Name is Required..!");
                hasError = true;
            }
            if (userNameF.getText().isEmpty()) {
                userNameLE.setText("Username is Required..!");
                hasError = true;
            }
            if (passwordF.getText().isEmpty()) {
                passwordLE.setText("Password is Required..!");
                hasError = true;
            }
            if (emailF.getText().isEmpty()) {
                emailLE.setText("Email is Required..!");
                hasError = true;
            }
            if (phoneF.getText().isEmpty()) {
                phonoLE.setText("Phone is Required..!");
                hasError = true;
            }

            if (selectedUser != null) {
                if (!hasError) {

                    selectedUser.setFullName(fullNameF.getText());
                    selectedUser.setUserName(userNameF.getText());
                    selectedUser.setPassword(passwordF.getText());
                    selectedUser.setEmail(emailF.getText());
                    selectedUser.setPhone(phoneF.getText());
                    selectedUser.setRole(rolesCombo.getValue());

                    if (this.userimageName != null && !this.userimageName.isEmpty()) {
                        selectedUser.setProfileImagePath(this.userimageName);
                    }

                    UserDatabaseHandler.updateUser(selectedUser);

                    Users.setAll(UserDatabaseHandler.getUserData());
                    tabelViewUser.setItems(Users);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "User updated successfully in DB.");
                    alert.showAndWait();

                    this.userimageName = null;

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a user to update.");
                    alert.showAndWait();
                }
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a user to update.");
            alert.showAndWait();
        }

    }

    @FXML
    private void cancelUser(ActionEvent event) {
        fullNameF.clear();
        userNameF.clear();
        passwordF.clear();
        emailF.clear();
        phoneF.clear();
        profileImg.setImage(new Image(ScenBuilder.class.getResourceAsStream("/images/addUser.png")));
        fullNameLError.setText("");
        userNameLE.setText("");
        passwordLE.setText("");
        emailLE.setText("");
        phonoLE.setText("");
        imageLabelError.setText("");
        roleLabelError.setText("");
    }

    public void RefreshUserProfileDataSidebar(boolean sameUserAuth) {
        if (sameUserAuth) {
            UserProfileImage.setImage(new Image(ScenBuilder.class
                    .getResourceAsStream(UserLogin.getProfileImagePath())));
            UserProfileImage.setFitHeight(70);
            UserProfileImage.setFitWidth(70);
            UserProfileFullName.setText(UserLogin.getFullName());

        }
    }

    @FXML
    private void SetSelectedUserToForm(MouseEvent event) {
        User user = tabelViewUser.getSelectionModel().getSelectedItem();
        if (user != null) {

            fullNameF.setText(user.getFullName());
            userNameF.setText(user.getUserName());
            passwordF.setText(user.getPassword());
            emailF.setText(user.getEmail());
            phoneF.setText(user.getPhone());
            rolesCombo.setValue(user.getRole());
            userImageName[0] = new Image(ScenBuilder.class
                    .getResourceAsStream(user.getProfileImagePath()));
            profileImg.setImage(userImageName[0]);
        }
    }

    @FXML
    private void SetUserRoleFilterTabelData(ActionEvent event) {
        String selectedItem = shufelCombo.getSelectionModel().getSelectedItem();
        ObservableList<User> shufelList = FXCollections.observableArrayList();
        int countFilterdUserInTabel = 0;
        if (selectedItem.equals("Admin") || selectedItem.equals("User") || selectedItem.equals("Librarian")) {
            shufelList.clear();
            for (User user : Users) {
                if (user.getRole().equals(selectedItem)) {
                    shufelList.add(user);
                    countFilterdUserInTabel++;
                }
            }
            tabelViewUser.setItems(shufelList);
            if (countFilterdUserInTabel == 0) {
                tabelViewUser.getItems().clear();
            }

        } else {
            tabelViewUser.setItems(Users);
        }
    }

    public boolean userExist(String userName, String pass) {
        boolean userFound = false;
        for (User user : Users) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(pass)) {
                userFound = true;
                break;
            }
        }
        return userFound;
    }

    public boolean sameUserNameFound(String userNameField) {
        boolean sameUserNameFound = false;
        for (User user : Users) {
            if (user.getUserName().equals(userNameField)) {
                sameUserNameFound = true;
                break;
            }

        }
        return sameUserNameFound;
    }

    public boolean sameUserNameFound(User slectedUser, String userName) {
        boolean sameUserNameFound = false;
        for (User user : Users) {
            if (slectedUser.getUserName().equals(userName)) {
                continue;
            }
            if (user.getUserName().equals(userName)) {
                sameUserNameFound = true;
                break;
            }

        }
        return sameUserNameFound;
    }

    public void saveImage(Image img, String name) throws IOException {

        String projectPath = System.getProperty("user.dir");
        String imagesFolderPath = projectPath + "/src/images";

        File imagesFolder = new File(imagesFolderPath);
        if (!imagesFolder.exists()) {

            imagesFolder.mkdir();
        }
        String fullFilePath = imagesFolderPath + "/" + name;

        File file = new File(fullFilePath);
        BufferedImage BI = SwingFXUtils.fromFXImage(img, null);
        ImageIO.write(BI, "png", file);

    }

    @FXML
    private void SetCategoryFilterTabelData(ActionEvent event) {
        String selectedCategory = bookFilter.getValue();

        if (selectedCategory == null || selectedCategory.equals("All")) {
            tabelViewBook.setItems(Books);
        } else {
            ObservableList<Book> filteredList = FXCollections.observableArrayList();
            for (Book book : Books) {
                if (book.getCategory().equals(selectedCategory)) {
                    filteredList.add(book);
                }
            }
            tabelViewBook.setItems(filteredList);
        }
    }

    @FXML
    private void UploadBookImageProfile(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(AdminStage);
        if (file != null) {
            bookImage[0] = new Image(file.toURI().toString());
            forBookImg.setImage(bookImage[0]);
            this.bookimageName = "/images/" + file.getName();
            try {
                saveImage(bookImage[0], file.getName());

            } catch (IOException ex) {
                Logger.getLogger(ScenBuilder.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void ShowAddCategoryStage(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Catgory.fxml"));
        Parent catgoryStageroot = loader.load();
        CatgoryController catgoryController = loader.getController();
        catgoryController.setAdminDashBoardController(this);
        Scene CatgoryScene = new Scene(catgoryStageroot);
        setStageInfo(newCatgoryStage, CatgoryScene, "Register_Screen.png", "Catgory Screen", 500, 300);
        newCatgoryStage.show();
    }

    public void refreshCategoryCombos() {
        FormCategoryCombo.getItems().clear();
        bookFilter.getItems().clear();

        FormCategoryCombo.getItems().addAll(Categories);
        bookFilter.getItems().addAll(Categories);
    }

    @FXML
    private void addBook(ActionEvent event) {
        forBookImgLabelError.setText("");
        forCategoryComboError.setText("");
        forLanguagesComboError.setText("");
        titleLabelError.setText("");
        authorLabelError.setText("");
        IsbnLabelError.setText("");
        publishDataLabelError.setText("");
        PageCountLabelError.setText("");
        copyCountLabelError.setText("");
        publisherLabelError.setText("");

        boolean hasError = false;
        if (this.bookimageName == null) {
            forBookImgLabelError.setText("Image is Required ...!");
            hasError = true;
        }
        if (FormCategoryCombo.getValue() == null || FormCategoryCombo.getValue().isEmpty()) {
            forCategoryComboError.setText("Catgeory is Required ...!");
            hasError = true;
        }
        if (LanguagesCombo.getValue() == null || LanguagesCombo.getValue().isEmpty()) {
            forLanguagesComboError.setText("language is Required ...!");
            hasError = true;
        }

        if (titleTF.getText().isEmpty()) {
            titleLabelError.setText("Title is Required ...!");
            hasError = true;
        }
        if (authorTF.getText().isEmpty()) {
            authorLabelError.setText("Auther is Required ...!");
            hasError = true;
        }

        if (IsbnTF.getText().isEmpty()) {
            IsbnLabelError.setText("Isbn is Required ...!");
            hasError = true;
        }
        if (publishDataTF.getText().isEmpty()) {
            publishDataLabelError.setText("PublishData is Required ...!");
            hasError = true;
        }
        if (pageCountTF.getText().isEmpty()) {
            PageCountLabelError.setText("Publisher is Required ...!");
            hasError = true;
        }
        if (copyCountTF.getText().isEmpty()) {
            copyCountLabelError.setText("Publisher is Required ...!");
            hasError = true;
        }
        if (publisherTF.getText().isEmpty()) {
            publisherLabelError.setText("Publisher is Required ...!");
            hasError = true;
        }
        if (!hasError) {
            boolean isFoundBook = BookExiset(IsbnTF.getText());
            if (!isFoundBook) {
                Book newBook;
                newBook = new Book(titleTF.getText(), authorTF.getText(), FormCategoryCombo.getValue(),
                        IsbnTF.getText(), publishDataTF.getText(), LanguagesCombo.getValue(),
                        publishDataTF.getText(), this.bookimageName,
                        Integer.parseInt(pageCountTF.getText()), Integer.parseInt(copyCountTF.getText())
                );

                CategoryDatabaseHandler.addBookToCategory(newBook);
                Books.setAll(BookDatabaseHandler.getBookData());
                tabelViewBook.setItems(Books);
//  ------------ alert -------------
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book has Been Add ...");
                alert.showAndWait();

//  ------------ Clear input -------------
                titleTF.clear();
                authorTF.clear();
                publishDataTF.clear();
                IsbnTF.clear();
                publisherTF.clear();
                pageCountTF.clear();
                copyCountTF.clear();
                FormCategoryCombo.setValue("");
                LanguagesCombo.setValue("");
                this.bookImage = null;
                forBookImg
                        .setImage(new Image(ScenBuilder.class
                                .getResourceAsStream("/images/addBook.png")));
            } else {
                IsbnLabelError.setText("Book already exists with this ISBN");
            }
        }

    }

    public boolean BookExiset(String isbnTf) {
        boolean bookFound = false;
        for (Book book : Books) {
            if (book.getIsbn().equals(isbnTf)) {
                bookFound = true;
            }
        }
        return bookFound;

    }

    @FXML
    private void deleteBook(ActionEvent event) {
        Book selectedBook = tabelViewBook.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            for (Book book : Books) {
                if (book.getIsbn().equals(selectedBook.getIsbn())) {
                    Books.remove(book);
                    titleTF.clear();
                    authorTF.clear();
                    publishDataTF.clear();
                    IsbnTF.clear();
                    publisherTF.clear();
                    pageCountTF.clear();
                    copyCountTF.clear();
                    FormCategoryCombo.setValue("");
                    LanguagesCombo.setValue("");
                    this.bookImage = null;
                    forBookImg
                            .setImage(new Image(ScenBuilder.class
                                    .getResourceAsStream("/images/addBook.png")));
                    BookDatabaseHandler.deleteBook(selectedBook);
                    Books.setAll(BookDatabaseHandler.getBookData());
                    tabelViewBook.setItems(Books);
                    break;
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Pleas Select Book To Delete.");
            alert.showAndWait();
        }
    }

    @FXML
    private void updataBook(ActionEvent event) {
        forBookImgLabelError.setText("");
        forCategoryComboError.setText("");
        forLanguagesComboError.setText("");
        titleLabelError.setText("");
        authorLabelError.setText("");
        IsbnLabelError.setText("");
        publishDataLabelError.setText("");
        PageCountLabelError.setText("");
        copyCountLabelError.setText("");
        publisherLabelError.setText("");

        boolean hasError = false;
        if (this.bookimageName == null) {
            forBookImgLabelError.setText("Image is Required ...!");
            hasError = true;
        }
        if (FormCategoryCombo.getValue() == null || FormCategoryCombo.getValue().isEmpty()) {
            forCategoryComboError.setText("Catgeory is Required ...!");
            hasError = true;
        }
        if (LanguagesCombo.getValue() == null || LanguagesCombo.getValue().isEmpty()) {
            forLanguagesComboError.setText("language is Required ...!");
            hasError = true;
        }

        if (titleTF.getText().isEmpty()) {
            titleLabelError.setText("Title is Required ...!");
            hasError = true;
        }
        if (authorTF.getText().isEmpty()) {
            authorLabelError.setText("Auther is Required ...!");
            hasError = true;
        }

        if (IsbnTF.getText().isEmpty()) {
            IsbnLabelError.setText("Isbn is Required ...!");
            hasError = true;
        }
        if (publishDataTF.getText().isEmpty()) {
            publishDataLabelError.setText("PublishData is Required ...!");
            hasError = true;
        }
        if (pageCountTF.getText().isEmpty()) {
            PageCountLabelError.setText("Publisher is Required ...!");
            hasError = true;
        }
        if (copyCountTF.getText().isEmpty()) {
            copyCountLabelError.setText("Publisher is Required ...!");
            hasError = true;
        }
        if (publisherTF.getText().isEmpty()) {
            publisherLabelError.setText("Publisher is Required ...!");
            hasError = true;
        }
        if (!hasError) {
            Book selectedBook = tabelViewBook.getSelectionModel().getSelectedItem();
            int BookIndex = Books.indexOf(selectedBook);
            if (selectedBook != null) {
                boolean sameBookFound = false;
                for (Book Book : Books) {
                    if (selectedBook.getIsbn().equals(Book.getIsbn())) {
                        continue;
                    }
                    if (Book.getIsbn().equals(IsbnTF.getText())) {
                        sameBookFound = true;
                        break;
                    }
                }
                if (selectedBook != null) {
                    if (!sameBookFound) {
                        selectedBook.setTitle(titleTF.getText());
                        selectedBook.setAuthor(authorTF.getText());
                        selectedBook.setCategory(FormCategoryCombo.getValue());
                        selectedBook.setPublishDate(publishDataTF.getText());
                        selectedBook.setPublisher(publisherTF.getText());
                        selectedBook.setIsbn(IsbnTF.getText());
                        selectedBook.setLanguage(LanguagesCombo.getValue());
                        selectedBook.setCopyCount(Integer.parseInt(copyCountTF.getText()));
                        selectedBook.setPageCount(Integer.parseInt(pageCountTF.getText()));
                        if (this.bookimageName != null) {
                            selectedBook.setBookImagePath(bookImage[0].toString().replace("file:", ""));
                        }
                        CategoryDatabaseHandler.addBookToCategory(selectedBook);
                        Books.setAll(BookDatabaseHandler.getBookData());
                        tabelViewBook.setItems(Books);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Book Update.");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "The Book Used By Isbn Book.");
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Pleas Select Book To Update.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void cancelBook(ActionEvent event) {
        titleTF.clear();
        authorTF.clear();
        publishDataTF.clear();
        IsbnTF.clear();
        publisherTF.clear();
        pageCountTF.clear();
        copyCountTF.clear();
        FormCategoryCombo.setValue("");
        LanguagesCombo.setValue("");
        this.bookImage = null;
        forBookImg
                .setImage(new Image(ScenBuilder.class
                        .getResourceAsStream("/images/addBook.png")));
    }

    @FXML
    private void SetSelectedBookToForm(MouseEvent event) {
        Book book = tabelViewBook.getSelectionModel().getSelectedItem();

        if (book != null) {
            forBookImg.setImage(new Image(book.getBookImagePath()));
            bookCategoryImg.setImage(new Image(book.getBookImagePath()));

            FormCategoryCombo.setValue(book.getCategory());
            LanguagesCombo.setValue(book.getLanguage());
            titleTF.setText(book.getTitle());
            authorTF.setText(book.getAuthor());
            IsbnTF.setText(book.getIsbn());
            publishDataTF.setText(book.getPublishDate());
            copyCountTF.setText(book.getCopyCount() + "");
            pageCountTF.setText(book.getPageCount() + "");
            bookImage[0] = new Image(ScenBuilder.class
                    .getResourceAsStream(book.getBookImagePath()));
            publisherTF.setText(book.getPublisher());

        } else {
        }
    }

    @FXML
    private void ShowUserProfileStage(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/UserProfile.fxml"));
        Scene UserProfileScene = new Scene(root);
        setStageInfo(UserProfileStage, UserProfileScene, "login_Screen.png", "UserProfile Screen", 600, 150);
        AdminStage.close();
        UserProfileStage.show();
    }

    private void ShowStatisticeStage() {
        labelAllUser.setText("");
        labelAdmins.setText("");
        labelLibriran.setText("");
        labelUser.setText("");
        int numAllUser = 0;
        int numAdmin = 0;
        int numLibriran = 0;
        int numUser = 0;
        for (User user : Users) {
            if (user.getRole().equals("Admin")) {
                numAdmin += 1;
                labelAdmins.setText(Integer.toString(numAdmin));
                numAllUser += 1;
                labelAllUser.setText(Integer.toString(numAllUser));

            } else if (user.getRole().equals("Librarian")) {
                numLibriran += 1;
                labelLibriran.setText(Integer.toString(numLibriran));
                numAllUser += 1;
                labelAllUser.setText(Integer.toString(numAllUser));

            } else {
                numUser += 1;
                labelUser.setText(Integer.toString(numUser));
                numAllUser += 1;
                labelAllUser.setText(Integer.toString(numAllUser));

            }

        }
        boolean hasError = false;

        if (labelAllUser.getText() == null || labelAllUser.getText().isEmpty()) {
            numAllUser = 0;
            labelAllUser.setText(Integer.toString(numAllUser));
            hasError = true;
        }
        if (labelAdmins.getText() == null || labelAdmins.getText().isEmpty()) {
            numAdmin = 0;
            labelAdmins.setText(Integer.toString(numAdmin));
            hasError = true;
        }
        if (labelLibriran.getText() == null || labelLibriran.getText().isEmpty()) {
            numLibriran = 0;
            labelLibriran.setText(Integer.toString(numLibriran));
            hasError = true;
        }
        if (labelUser.getText() == null || labelUser.getText().isEmpty()) {
            numUser = 0;
            labelUser.setText(Integer.toString(numUser));
            hasError = true;
        }
        if (!hasError) {
            labelAllUser.setText(Integer.toString(numAllUser));
            labelAdmins.setText(Integer.toString(numAdmin));
            labelLibriran.setText(Integer.toString(numLibriran));
            labelUser.setText(Integer.toString(numUser));
        }
    }

    @FXML
    private void ShowAdmins(MouseEvent event) throws IOException {
        showUsersStatistace("Admin");
    }

    @FXML
    private void ShowLibriran(MouseEvent event) throws IOException {
        showUsersStatistace("Librarian");
    }

    @FXML
    private void ShowUser(MouseEvent event) throws IOException {
        showUsersStatistace("User");
    }

    private void showUsersStatistace(String role) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UsersStatistace.fxml"));
        Parent usersStatistaceStageroot = loader.load();
        UsersStatistaceController usersStatistace = loader.getController();
        usersStatistace.setRoleFilter(role);
        Scene UsersStatistaceScene = new Scene(usersStatistaceStageroot);
        setStageInfo(UsersStatistaceStage, UsersStatistaceScene, "team.png", "Users Statistace Screen", 200, 50);
        UsersStatistaceStage.show();
    }

}
