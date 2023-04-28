package mx.uv.fei.gui.javafiles.guiuserscontrollers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
    private Button registerUserButton;

    @FXML
    private Button searchByNameButton;

    @FXML
    private TextField searchByNameTextField;

    @FXML
    private ImageView spgerLogo;

    @FXML
    private Text spgerText;

    @FXML
    private ScrollPane userInformationScrollPane;

    @FXML
    private ImageView userLogo;

    @FXML
    private Text userNameText;

    @FXML
    private VBox usersVBox;

    @FXML
    private Text windowText;

    @FXML
    void initialize() {
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
        
        this.directorButtonMaker(directors);
        this.academicBodyHeadButtonMaker(academicBodyHeads);
        this.degreeBossButtonMaker(degreeBosses);
        this.professorButtonMaker(professors);
        this.studentButtonMaker(students);
        
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
        this.usersVBox.getChildren().removeAll(this.usersVBox.getChildren());
        StudentDAO studentDAO = new StudentDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();
        DirectorDAO directorDAO = new DirectorDAO();
        AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
        DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
        ArrayList<Student> students = studentDAO.getSpecifiedStudentsFromDatabase(this.searchByNameTextField.getText());
        ArrayList<Professor> professors = professorDAO.getSpecifiedProfessorsFromDatabase(this.searchByNameTextField.getText());
        ArrayList<Director> directors = directorDAO.getSpecifiedDirectorsFromDatabase(this.searchByNameTextField.getText());
        ArrayList<AcademicBodyHead> academicBodyHeads = academicBodyHeadDAO.getSpecifiedAcademicBodyHeadsFromDatabase(this.searchByNameTextField.getText());
        ArrayList<DegreeBoss> degreeBosses = degreeBossDAO.getSpecifiedDegreeBossesFromDatabase(this.searchByNameTextField.getText());

        this.directorButtonMaker(directors);
        this.academicBodyHeadButtonMaker(academicBodyHeads);
        this.degreeBossButtonMaker(degreeBosses);
        this.professorButtonMaker(professors);
        this.studentButtonMaker(students);
    }

    private void studentButtonMaker(ArrayList<Student> students){
        try {
            for(Student student : students){
                FXMLLoader userItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guiusers/User.fxml")
                );
                Button userItemButton;
                userItemButton = userItemControllerLoader.load();
                UserController userController = userItemControllerLoader.getController();
                userController.setName(student.getName());
                userController.setType("Estudiante");
                userController.setGuiUsersController(this);
                this.usersVBox.getChildren().add(userItemButton);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }

    private void professorButtonMaker(ArrayList<Professor> professors){
        try {
            for(Professor professor : professors){
                FXMLLoader userItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guiusers/User.fxml")
                );
                Button userItemButton = userItemControllerLoader.load();
                UserController userController = userItemControllerLoader.getController();
                userController.setName(professor.getName());
                userController.setType("Profesor");
                userController.setGuiUsersController(this);
                this.usersVBox.getChildren().add(userItemButton);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }

    private void directorButtonMaker(ArrayList<Director> directors){
        try {
            for(Director director : directors){
                FXMLLoader userItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guiusers/User.fxml")
                );
                Button userItemButton = userItemControllerLoader.load();
                UserController userController = userItemControllerLoader.getController();
                userController.setName(director.getName());
                userController.setType("Director");
                userController.setGuiUsersController(this);
                this.usersVBox.getChildren().add(userItemButton);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }

    private void academicBodyHeadButtonMaker(ArrayList<AcademicBodyHead> academicBodyHeads){
        try {
            for(AcademicBodyHead academicBodyHead : academicBodyHeads){
                FXMLLoader userItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guiusers/User.fxml")
                );
                Button userItemButton = userItemControllerLoader.load();
                UserController userController = userItemControllerLoader.getController();
                userController.setName(academicBodyHead.getName());
                userController.setType("Miembro de Cuerpo Académico");
                userController.setGuiUsersController(this);
                this.usersVBox.getChildren().add(userItemButton);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }

    private void degreeBossButtonMaker(ArrayList<DegreeBoss> degreeBosses){
        try {
            for(DegreeBoss degreeBoss : degreeBosses){
                FXMLLoader userItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guiusers/User.fxml")
                );
                Button userItemButton = userItemControllerLoader.load();
                UserController userController = userItemControllerLoader.getController();
                userController.setName(degreeBoss.getName());
                userController.setType("Jefe de Carrera");
                userController.setGuiUsersController(this);
                this.usersVBox.getChildren().add(userItemButton);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }

    //This method only should be used by the UserController Class.
    void openPaneWithUserInformation(String userName, String userType, String userStatus){
        switch(userType){
            case "Director":{
                DirectorDAO directorDAO = new DirectorDAO();
                Director director = directorDAO.getDirectorFromDatabase(userName);
                openPaneWithDirectorInformation(director);
                break;
            }

            case "Miembro de Cuerpo Académico":{
                AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
                AcademicBodyHead academicBodyHead = academicBodyHeadDAO.getAcademicBodyHeadFromDatabase(userName);
                openPaneWithAcademicBodyHeadInformation(academicBodyHead);
                break;
            }

            case "Jefe de Carrera":{
                DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
                DegreeBoss degreeBoss = degreeBossDAO.getDegreeBossFromDatabase(userName);
                openPaneWithDegreeBossInformation(degreeBoss);
                break;
            }

            case "Profesor":{
                ProfessorDAO professorDAO = new ProfessorDAO();
                Professor professor = professorDAO.getProfessorFromDatabase(userName);
                openPaneWithProfessorInformation(professor);
                break;
            }

            case "Estudiante":{
                StudentDAO studentDAO = new StudentDAO();
                Student student = studentDAO.getStudentFromDatabase(userName);
                openPaneWithStudentInformation(student);
                break;
            }
        }
    }

    private void openPaneWithDirectorInformation(Director director){
        FXMLLoader userInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guiusers/UserInformation.fxml")
        );
        try {
            VBox userInformationVBox = userInformationControllerLoader.load();
            UserInformationController userInformationController = userInformationControllerLoader.getController();
            userInformationController.setNames(director.getName());
            userInformationController.setFirstSurname(director.getFirstSurname());
            userInformationController.setSecondSurname(director.getSecondSurname());
            userInformationController.setEmail(director.getEmailAddress());
            userInformationController.setAlternateEmail(director.getAlternateEmail());
            userInformationController.setTelephoneNumber(director.getPhoneNumber());
            userInformationController.setType("Director");
            //userInformationController.setStatus(director.getStatus());
            userInformationController.setGuiUsersController(this);
            this.userInformationScrollPane.setContent(userInformationVBox);
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void openPaneWithAcademicBodyHeadInformation(AcademicBodyHead academicBodyHead){
        FXMLLoader userInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guiusers/UserInformation.fxml")
        );

        try {
            VBox userInformationVBox = userInformationControllerLoader.load();
            UserInformationController userInformationController = userInformationControllerLoader.getController();
            userInformationController.setNames(academicBodyHead.getName());
            userInformationController.setFirstSurname(academicBodyHead.getFirstSurname());
            userInformationController.setSecondSurname(academicBodyHead.getSecondSurname());
            userInformationController.setEmail(academicBodyHead.getEmailAddress());
            userInformationController.setAlternateEmail(academicBodyHead.getAlternateEmail());
            userInformationController.setTelephoneNumber(academicBodyHead.getPhoneNumber());
            userInformationController.setType("Miembro de Cuerpo Académico");
            //userInformationController.setStatus(academicBodyHead.getStatus());
            userInformationController.setGuiUsersController(this);
            this.userInformationScrollPane.setContent(userInformationVBox);
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void openPaneWithDegreeBossInformation(DegreeBoss degreeBoss){
        FXMLLoader userInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guiusers/UserInformation.fxml")
        );

        try {
            VBox userInformationVBox = userInformationControllerLoader.load();
            UserInformationController userInformationController = userInformationControllerLoader.getController();
            userInformationController.setNames(degreeBoss.getName());
            userInformationController.setFirstSurname(degreeBoss.getFirstSurname());
            userInformationController.setSecondSurname(degreeBoss.getSecondSurname());
            userInformationController.setEmail(degreeBoss.getEmailAddress());
            userInformationController.setAlternateEmail(degreeBoss.getAlternateEmail());
            userInformationController.setTelephoneNumber(degreeBoss.getPhoneNumber());
            userInformationController.setType("Jefe de Carrera");
            //userInformationController.setStatus(degreeBoss.getStatus());
            userInformationController.setGuiUsersController(this);
            this.userInformationScrollPane.setContent(userInformationVBox);
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void openPaneWithProfessorInformation(Professor professor){
        FXMLLoader userInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guiusers/UserInformation.fxml")
        );

        try {
            VBox userInformationVBox = userInformationControllerLoader.load();
            UserInformationController userInformationController = userInformationControllerLoader.getController();
            userInformationController.setNames(professor.getName());
            userInformationController.setFirstSurname(professor.getFirstSurname());
            userInformationController.setSecondSurname(professor.getSecondSurname());
            userInformationController.setEmail(professor.getEmailAddress());
            userInformationController.setAlternateEmail(professor.getAlternateEmail());
            userInformationController.setTelephoneNumber(professor.getPhoneNumber());
            userInformationController.setType("Profesor");
            //userInformationController.setStatus(professor.getStatus());
            userInformationController.setGuiUsersController(this);
            this.userInformationScrollPane.setContent(userInformationVBox);
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void openPaneWithStudentInformation(Student student){
        FXMLLoader userInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guiusers/UserInformation.fxml")
        );

        try {
            VBox userInformationVBox = userInformationControllerLoader.load();
            UserInformationController userInformationController = userInformationControllerLoader.getController();
            userInformationController.setNames(student.getName());
            userInformationController.setFirstSurname(student.getFirstSurname());
            userInformationController.setSecondSurname(student.getSecondSurname());
            userInformationController.setEmail(student.getEmailAddress());
            userInformationController.setAlternateEmail(student.getAlternateEmail());
            userInformationController.setTelephoneNumber(student.getPhoneNumber());
            userInformationController.setType("Estudiante");
            //userInformationController.setStatus(student.getStatus());
            userInformationController.setGuiUsersController(this);
            this.userInformationScrollPane.setContent(userInformationVBox);
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //This method only should be used by the UserInformationController Class.
    void openModifyUserPane(UserInformationController userInformationController){
        FXMLLoader modifyUserInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guiusers/ModifyUserInformation.fxml")
        );

        try {
            VBox modifyUserInformationVBox = modifyUserInformationControllerLoader.load();
            ModifyUserInformationController modifyUserInformationController = modifyUserInformationControllerLoader.getController();
            modifyUserInformationController.setNames(userInformationController.getNames());
            modifyUserInformationController.setFirstSurname(userInformationController.getFirstSurname());
            modifyUserInformationController.setSecondSurname(userInformationController.getSecondSurname());
            modifyUserInformationController.setEmail(userInformationController.getEmail());
            modifyUserInformationController.setAlternateEmail(userInformationController.getAlternateEmail());
            modifyUserInformationController.setTelephoneNumber(userInformationController.getTelephoneNumber());
            modifyUserInformationController.setType(userInformationController.getType());
            modifyUserInformationController.setStatus(userInformationController.getStatus());
            modifyUserInformationController.setGuiUsersController(this);
            this.userInformationScrollPane.setContent(modifyUserInformationVBox);
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
