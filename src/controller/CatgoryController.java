package controller;

import DataBase.CategoryDatabaseHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import scenbuilder.ScenBuilder;
import static scenbuilder.ScenBuilder.Categories;
import static scenbuilder.ScenBuilder.newCatgoryStage;

/**
 * FXML Controller class
 *
 * @author ibrah
 */
public class CatgoryController implements Initializable {

    @FXML
    private TextField newCatg;
    @FXML
    private Button add;
    @FXML
    private Button cancel;
    private AdminDashBoardController adminDashBoardController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setAdminDashBoardController(AdminDashBoardController adminDashBoardController) {
        this.adminDashBoardController = adminDashBoardController;
    }

    @FXML
    private void addCatgory(ActionEvent event) {
        if (!newCatg.getText().isEmpty()) {
            CategoryDatabaseHandler.addCategory(newCatg.getText());
            ScenBuilder.Categories.setAll(CategoryDatabaseHandler.getAllCategories());
            adminDashBoardController.refreshCategoryCombos();

            newCatg.clear();
            newCatgoryStage.hide();
        }
    }

    @FXML
    private void cancelCatgory(ActionEvent event) {
        newCatg.clear();
        newCatgoryStage.hide();
    }

}
