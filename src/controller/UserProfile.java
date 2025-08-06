package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.R;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import model.User;
import scenbuilder.ScenBuilder;
import static scenbuilder.ScenBuilder.AdminStage;
import static scenbuilder.ScenBuilder.LibrarianStage;
import static scenbuilder.ScenBuilder.RegisterStage;
import static scenbuilder.ScenBuilder.UserLogin;
import static scenbuilder.ScenBuilder.UserProfileStage;
import static scenbuilder.ScenBuilder.UserStage;
import static scenbuilder.ScenBuilder.Users;

public class UserProfile implements Initializable {

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
    private Label userNameLError;
    @FXML
    private PasswordField passField;
    @FXML
    private Label passLabelError;
    @FXML
    private TextField emailField;
    @FXML
    private Label emailLabelErorr;
    @FXML
    private TextField phoneField;
    @FXML
    private Label phonoLabelErorr;
    private String imageName = null;
    private Image userImageName[] = {null};

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rolesCombo.getItems().addAll("User", "Librarian", "Admin");
        rolesCombo.setDisable(true);

        if (UserLogin != null) {
            fullNameF.setText(UserLogin.getFullName());
            userNameF.setText(UserLogin.getUserName());
            passField.setText(UserLogin.getPassword());
            emailField.setText(UserLogin.getEmail());
            phoneField.setText(UserLogin.getPhone());
            rolesCombo.setValue(UserLogin.getRole());
            userImageName[0] = new Image(ScenBuilder.class.getResourceAsStream(UserLogin.getProfileImagePath()));
            profileImg.setImage(userImageName[0]);
        }
    }

    @FXML
    private void UploadUserImageProfile(MouseEvent event) {
        Image[] profileImage = {null};
        profileImg.setOnMouseClicked(e -> {
            FileChooser fChosser = new FileChooser();
            File file = fChosser.showOpenDialog(UserProfileStage);
            if (file != null) {
                profileImage[0] = new Image(file.toURI().toString());
                profileImg.setImage(profileImage[0]);
                this.imageName = "/images/" + file.getName();
                try {
                    saveImage(profileImage[0], file.getName());
                } catch (IOException ex) {
                    Logger.getLogger(ScenBuilder.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

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
    private void UpdateUserProfile(ActionEvent event) {
        fullNameLError.setText("");
        userNameLError.setText("");
        passLabelError.setText("");
        emailLabelErorr.setText("");
        phonoLabelErorr.setText("");
        imageLabelError.setText("");
        roleLabelError.setText("");

        boolean hasError = false;
        if (fullNameF.getText().isEmpty()) {
            fullNameLError.setText("Full Name is required");
            hasError = true;
        }
        if (userNameF.getText().isEmpty()) {
            userNameLError.setText("User Name is required");
            hasError = true;
        }
        if (passField.getText().isEmpty()) {
            passLabelError.setText("Password is required");
            hasError = true;
        }
        if (emailField.getText().isEmpty()) {
            emailLabelErorr.setText("Email is required");
            hasError = true;
        }
        if (phoneField.getText().isEmpty()) {
            phonoLabelErorr.setText("Phone is required");
            hasError = true;
        }

        if (!hasError) {

            boolean sameUserFound = false;
            for (User user : Users) {
                if (UserLogin.getUserName().equals(userNameF.getText())) {
                    continue;
                }
                if (user.getUserName().equals(userNameF.getText())) {
                    sameUserFound = true;
                    break;
                }
            }

            if (!sameUserFound) {
                UserLogin.setFullName(fullNameF.getText());
                UserLogin.setUserName(userNameF.getText());
                UserLogin.setPassword(passField.getText());
                UserLogin.setPhone(phoneField.getText());
                UserLogin.setEmail(emailField.getText());
                UserLogin.setRole(rolesCombo.getValue());

                if (this.imageName != null) {
                    UserLogin.setProfileImagePath(userImageName[0].toString());
                }

                Users.set(Users.indexOf(UserLogin), UserLogin);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "User Profile updated successfully.");
                alert.showAndWait();
                UserProfileStage.close();
                if (UserLogin.getRole().equals("Admin")) {
                    AdminStage.show();
                } else if (UserLogin.getRole().equals("User")) {
                    UserStage.show();
                } else {
                    LibrarianStage.show();
                }
            }

        } else {
            userNameLError.setText("!... User Profile already exists with this user name and password ...!");
        }
    }

    @FXML
    private void ShowDashboard(ActionEvent event) {
        UserProfileStage.close();
        AdminStage.show();
    }
}
