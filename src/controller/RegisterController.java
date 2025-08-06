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
import static scenbuilder.ScenBuilder.RegisterStage;
import static scenbuilder.ScenBuilder.Users;

public class RegisterController implements Initializable {

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
    @FXML
    private Button Register;
    @FXML
    private Button Login;
    private String imageName = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rolesCombo.getItems().addAll("User", "Librian", "Admin");
        rolesCombo.getSelectionModel().selectFirst();
        rolesCombo.setDisable(true);
    }

    @FXML
    private void UploadUserImageProfile(MouseEvent event) {
        Image[] profileImage = {null};
        profileImg.setOnMouseClicked(e -> {
            FileChooser fChosser = new FileChooser();
            File file = fChosser.showOpenDialog(RegisterStage);
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

    @FXML
    private void Register(ActionEvent event) {
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
        if (imageName == null) {
            imageLabelError.setText("Image is required");
            hasError = true;
        }
        if (!hasError) {
            boolean isFound = userExist(userNameF.getText(), passField.getText());
            if (!isFound) {
                User newUser = new User(fullNameF.getText(), userNameF.getText(), passField.getText(), emailField.getText(), phoneField.getText(), rolesCombo.getValue(), this.imageName.toString());
                Users.add(newUser);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "-!- User has been registered -!-");
                alert.showAndWait();
                fullNameF.clear();
                userNameF.clear();
                passField.clear();
                emailField.clear();
                phoneField.clear();
                this.imageName = null;
                ScenBuilder.LoginStage.show();
                ScenBuilder.RegisterStage.hide();

            } else {
                userNameLError.setText("!... User already exists with this user name and password ...!");
            }
        }
    }

    @FXML
    private void ShowLoginPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene loginScene = new Scene(root);
        ScenBuilder.setStageInfo(ScenBuilder.LoginStage, loginScene, "login_Screen.png", "Login Screen",  600, 250);
        ScenBuilder.LoginStage.show();
        ScenBuilder.RegisterStage.hide();
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
}
