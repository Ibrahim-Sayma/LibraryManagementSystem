package scenbuilder;

import DataBase.BookDatabaseHandler;
import DataBase.BorrowBookDetailsDatabaseHandler;
import DataBase.DatabaseConnection;
import DataBase.UserDatabaseHandler;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Book;
import model.BorrowBook;
import model.BorrowBookDetaiels;
import model.User;

public class ScenBuilder extends Application {

    public static Stage LoginStage = new Stage();
    public static Stage RegisterStage = new Stage();
    public static Stage AdminStage = new Stage();
    public static Stage UserStage = new Stage();
    public static Stage LibrarianStage = new Stage();
    public static Stage newCatgoryStage = new Stage();
    public static Stage UserProfileStage = new Stage();
    public static Stage UsersStatistaceStage = new Stage();
    public static Stage BooksStatistaceStage = new Stage();

    public static User UserLogin = null;

    public static ObservableList<User> Users = FXCollections.observableArrayList();
    public static ObservableList<String> Categories = FXCollections.observableArrayList();
    public static ObservableList<Book> Books = FXCollections.observableArrayList();
    public static ObservableList<BorrowBook> BorrowBooks = FXCollections.observableArrayList();
    public static ObservableList<BorrowBookDetaiels> borrowBookDetaiels = FXCollections.observableArrayList();
    public static ObservableList<User> tabelViewUserStatistace = FXCollections.observableArrayList();
    public static ObservableList<BorrowBookDetaiels> tabelViewBookStatistace = FXCollections.observableArrayList();
    public static ObservableList<User> filteredUsers = FXCollections.observableArrayList();
    public static ObservableList<BorrowBookDetaiels> filteredStatus = FXCollections.observableArrayList();
 

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene loginScene = new Scene(root);
        setStageInfo(LoginStage, loginScene, "login_Screen.png", "Login Screen", 600, 250);
        LoginStage.show();
        DatabaseConnection.getInstance();
        Users.setAll(UserDatabaseHandler.getUserData());
        Books.setAll(BookDatabaseHandler.getAllBooks());
        borrowBookDetaiels.setAll(BorrowBookDetailsDatabaseHandler.getAllBorrowBookDetails());
        Categories.setAll(BookDatabaseHandler.getAllCategories());

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setStageIcon(Stage stage, String logo) {
        stage.getIcons().add(new Image(ScenBuilder.class.getResourceAsStream("/images/" + logo)));
    }

    public static void setStageInfo(Stage s, Scene sc, String logo, String STitle, int sX, int sY) {
        s.setTitle(STitle);
        s.setX(sX);
        s.setY(sY);
        s.setScene(sc);
        setStageIcon(s, logo);
        s.setResizable(false);

    }

}
