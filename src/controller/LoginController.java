package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.Book;
import model.User;
import scenbuilder.ScenBuilder;
import static scenbuilder.ScenBuilder.AdminStage;
import static scenbuilder.ScenBuilder.Books;
import static scenbuilder.ScenBuilder.LibrarianStage;
import static scenbuilder.ScenBuilder.LoginStage;
import static scenbuilder.ScenBuilder.UserStage;
import static scenbuilder.ScenBuilder.setStageInfo;

public class LoginController implements Initializable {

    @FXML
    private Label userNameLE;

    @FXML
    private Label passwordLE;

    @FXML
    private Label userNameL;

    @FXML
    private TextField userNameF;

    @FXML
    private Button Register;

    @FXML
    private TextField passwordF;

    @FXML
    private ImageView imageView;

    @FXML
    private Label passwordL;

    @FXML
    private Button LoginUser;

    @FXML
    void ShowAdminPage(ActionEvent event) throws IOException {
        userNameLE.setText("");
        passwordLE.setText("");

        String user_Name = userNameF.getText();
        String password = passwordF.getText();
        User user = validateUser(user_Name, password);

        if (user == null) {
            if (user_Name.isEmpty()) {
                userNameLE.setText("User Name is Required");
            } else if (password.isEmpty()) {
                passwordLE.setText("Password is Required");
            } else {
                userNameLE.setText("InValid User Name or Password");
            }
        } else {
            ScenBuilder.UserLogin = user;
            if (user.getRole().equals("Admin")) {
                Parent AdminStageroot = FXMLLoader.load(getClass().getResource("/view/AdminDashBoard.fxml"));
                Scene AdminScene = new Scene(AdminStageroot);
                setStageInfo(AdminStage, AdminScene, "userImage1.png", "Admin Screen", 50, 30);
                LoginStage.hide();
                AdminStage.show();

            } else if (user.getRole().equals("Librarian")) {
                Parent LibrarianStageroot = FXMLLoader.load(getClass().getResource("/view/librarianDashBoard.fxml"));
                Scene LibrarianScene = new Scene(LibrarianStageroot);
                setStageInfo(LibrarianStage, LibrarianScene, "userImage4.png", "Librarian Screen", 50, 30);
                LoginStage.hide();
                LibrarianStage.show();

            } else {
                Parent UserStageroot = FXMLLoader.load(getClass().getResource("/view/UserDashBoard.fxml"));
                Scene UserScene = new Scene(UserStageroot);
                setStageInfo(UserStage, UserScene, "userImage3.png", "User Screen", 50, 30);

                LoginStage.hide();
                UserStage.show();
            }
        }
    }

    @FXML
    void ShowRegisterPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Register.fxml"));
        Scene registerScene = new Scene(root);
        ScenBuilder.setStageInfo(ScenBuilder.RegisterStage, registerScene, "Register_Screen.png", "Register Screen", 600, 250);
        ScenBuilder.RegisterStage.show();
        ScenBuilder.LoginStage.hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        ScenBuilder.Users.add(new User(1,"Ibrahim", "admin", "admin123", "ibrahim@gmail.com", "0592313419", "Admin", "/images/userImage1.png"));
//        ScenBuilder.Users.add(new User(2,"Ali", "user", "user123", "ibrahim@gmail.com", "0592313419", "User", "/images/userImage4.png"));
//        ScenBuilder.Users.add(new User(3,"Omer", "lib", "lib123", "ibrahim@gmail.com", "0592313419", "Librarian", "/images/userImage3.png"));
        
  

    }

//    -- Helper Function --
    public User validateUser(String userName, String pass) {
        for (User user : ScenBuilder.Users) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(pass)) {
                return user;
            }
        }
        return null;
    }

}
