package mx.uv.fei.gui.javafiles.guiuserscontrollers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mx.uv.fei.logic.daos.AcademicBodyHeadDAO;
import mx.uv.fei.logic.daos.DegreeBossDAO;
import mx.uv.fei.logic.daos.DirectorDAO;
import mx.uv.fei.logic.daos.ProfessorDAO;
import mx.uv.fei.logic.daos.StudentDAO;
import mx.uv.fei.logic.domain.AcademicBodyHead;
import mx.uv.fei.logic.domain.DegreeBoss;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.Student;

public class GuiUsersController {

    @FXML
    private ToggleButton onlyShowActiveUsersButton;

    @FXML
    private Button orderByButton;

    @FXML
    private ChoiceBox<?> orderByChoiceBox;

    @FXML
    private Button registerUserButton;

    @FXML
    private Button searchByNameButton;

    @FXML
    private ImageView spgerLogo;

    @FXML
    private Text spgerText;

    @FXML
    private ImageView userLogo;

    @FXML
    private Text userNameText;

    @FXML
    private VBox usersVBox;

    @FXML
    private Text windowText;

    @FXML
    void initialize(){
        StudentDAO studentDAO = new StudentDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();
        DirectorDAO directorDAO = new DirectorDAO();
        AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
        DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
        ArrayList<Student> students = studentDAO.getStudentsFromDatabase();
        ArrayList<Professor> professors = professorDAO.getProfessorsFromDatabase();
        ArrayList<Director> directors = directorDAO.getDirectorsFromDatabase();
        ArrayList<AcademicBodyHead> academicBodyHeads = academicBodyHeadDAO.getAcademicBodyHeadsFromDatabase();
        ArrayList<DegreeBoss> degreeBosses = degreeBossDAO.getDegreeBossesFromDatabase();

        try {
            FXMLLoader userItemControllerLoader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guiusers/User.fxml")
            );
            Button userItemButton = userItemControllerLoader.load();
            UserController userController = userItemControllerLoader.getController();
            for(Student student : students){
                userController.setName(student.getName());
                userController.setGuiUsersController(this);
                this.usersVBox.getChildren().add(userItemButton);
            }

            for(Professor professor : professors){
                userController.setName(professor.getName());
                userController.setGuiUsersController(this);
                this.usersVBox.getChildren().add(userItemButton);
            }

            for(Director director : directors){
                userController.setName(director.getName());
                userController.setGuiUsersController(this);
                this.usersVBox.getChildren().add(userItemButton);
            }

            for(AcademicBodyHead academicBodyHead : academicBodyHeads){
                userController.setName(academicBodyHead.getName());
                userController.setGuiUsersController(this);
                this.usersVBox.getChildren().add(userItemButton);
            }

            for(DegreeBoss degreeBoss : degreeBosses){
                userController.setName(degreeBoss.getName());
                userController.setGuiUsersController(this);
                this.usersVBox.getChildren().add(userItemButton);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void onlyShowActiveUsersButtonController(ActionEvent event) {

    }

    @FXML
    void orderByButtonController(ActionEvent event) {

    }

    @FXML
    void registerUserButtonController(ActionEvent event) {
        Parent guiRegisterUser;
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guiregisteruser/GuiRegisterUser.fxml")
            );
            guiRegisterUser = loader.load();
            Scene scene = new Scene(guiRegisterUser);
            Stage stage = new Stage();
            stage.setTitle("Register User");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }

    @FXML
    void searchByNameButtonController(ActionEvent event) {

    }

}
