package mx.uv.fei.gui.controllers.users;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.uv.fei.gui.controllers.AlertPaneController;
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
import mx.uv.fei.logic.exceptions.DataRetrievalException;

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
    private ScrollPane userInformationScrollPane;

    @FXML
    private VBox usersVBox;
    
    
    @FXML
    void initialize() {
        loadUserButtons();
    }

    @FXML
    void registerUserButtonController(ActionEvent event) {
        Parent guiRegisterUser;
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxml/users/GuiRegisterUser.fxml")
            );
            guiRegisterUser = loader.load();
            GuiRegisterUserController guiRegisterUserController = loader.getController();
            guiRegisterUserController.setGuiUsersController(this);
            Scene scene = new Scene(guiRegisterUser);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = new Stage();
            stage.setTitle("Registrar Usuario");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
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
        try {
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
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

    public void loadUserButtons() {
        StudentDAO studentDAO = new StudentDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();
        DirectorDAO directorDAO = new DirectorDAO();
        AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
        DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
        try {
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
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

    private void studentButtonMaker(ArrayList<Student> students){
        try {
            for(Student student : students){
                FXMLLoader userItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxml/users/User.fxml")
                );
                Pane userItemPane;
                userItemPane = userItemControllerLoader.load();
                UserController userController = userItemControllerLoader.getController();
                userController.setName(student.getName() + " " + student.getFirstSurname() + " " + student.getSecondSurname());
                userController.setType("Estudiante");
                userController.setMatricleOrPersonalNumber(student.getMatricule());
                userController.setMatricleOrPersonalNumberText("Matrícula: ");
                userController.setGuiUsersController(this);
                this.usersVBox.getChildren().add(userItemPane);
            }
        } catch (IOException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }    
    }

    private void professorButtonMaker(ArrayList<Professor> professors){
        try {
            for(Professor professor : professors) {
                boolean professorAlreadyInUserVbox = false;
                for(Node pane : ((VBox)this.usersVBox).getChildren()) {
                    Node label = ((Pane)pane).getChildren().get(5);
                    if(((Label)label).getText().equals(Integer.toString(professor.getStaffNumber()))) {
                        professorAlreadyInUserVbox = true;
                        break;
                    }
                }

                if(!professorAlreadyInUserVbox){
                    FXMLLoader userItemControllerLoader = new FXMLLoader(
                        getClass().getResource("/mx/uv/fei/gui/fxml/users/User.fxml")
                    );
                    Pane userItemPane = userItemControllerLoader.load();
                    UserController userController = userItemControllerLoader.getController();
                    userController.setName(professor.getName());
                    userController.setType("Profesor");
                    userController.setMatricleOrPersonalNumber(Integer.toString(professor.getStaffNumber()));
                    userController.setMatricleOrPersonalNumberText("Número de Personal: ");
                    userController.setGuiUsersController(this);
                    this.usersVBox.getChildren().add(userItemPane);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }    
    }

    private void directorButtonMaker(ArrayList<Director> directors){
        try {
            for(Director director : directors){
                FXMLLoader userItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxml/users/User.fxml")
                );
                Pane userItemPane = userItemControllerLoader.load();
                UserController userController = userItemControllerLoader.getController();
                userController.setName(director.getName());
                userController.setType("Director");
                userController.setMatricleOrPersonalNumber(Integer.toString(director.getStaffNumber()));
                userController.setMatricleOrPersonalNumberText("Número de Personal: ");
                userController.setGuiUsersController(this);
                this.usersVBox.getChildren().add(userItemPane);
            }
        } catch (IOException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }  
    }

    private void academicBodyHeadButtonMaker(ArrayList<AcademicBodyHead> academicBodyHeads){
        try {
            for(AcademicBodyHead academicBodyHead : academicBodyHeads){
                FXMLLoader userItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxml/users/User.fxml")
                );
                Pane userItemPane = userItemControllerLoader.load();
                UserController userController = userItemControllerLoader.getController();
                userController.setName(academicBodyHead.getName());
                userController.setType("Miembro de Cuerpo Académico");
                userController.setMatricleOrPersonalNumber(Integer.toString(academicBodyHead.getStaffNumber()));
                userController.setMatricleOrPersonalNumberText("Número de Personal: ");
                userController.setGuiUsersController(this);
                this.usersVBox.getChildren().add(userItemPane);
            }
        } catch (IOException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }  
    }

    private void degreeBossButtonMaker(ArrayList<DegreeBoss> degreeBosses){
        try {
            for(DegreeBoss degreeBoss : degreeBosses){
                FXMLLoader userItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxml/users/User.fxml")
                );
                Pane userItemPane = userItemControllerLoader.load();
                UserController userController = userItemControllerLoader.getController();
                userController.setName(degreeBoss.getName());
                userController.setType("Jefe de Carrera");
                userController.setMatricleOrPersonalNumber(Integer.toString(degreeBoss.getStaffNumber()));
                userController.setMatricleOrPersonalNumberText("Número de Personal: ");
                userController.setGuiUsersController(this);
                this.usersVBox.getChildren().add(userItemPane);
            }
        } catch (IOException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }  
    }

    //This method only should be used by the UserController Class.
    void openPaneWithUserInformation(UserController userController){
        try {
            switch(userController.getType()) {
                case "Director": {
                    DirectorDAO directorDAO = new DirectorDAO();
                    Director director;
                        director = directorDAO.getDirectorFromDatabase(Integer.parseInt(userController.getMatriculeOrPersonalNumber()));
                        openPaneWithDirectorInformation(director);
                        break;
                    }

                case "Miembro de Cuerpo Académico": {
                    AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
                    AcademicBodyHead academicBodyHead = academicBodyHeadDAO.getAcademicBodyHeadFromDatabase(Integer.parseInt(userController.getMatriculeOrPersonalNumber()));
                    openPaneWithAcademicBodyHeadInformation(academicBodyHead);
                    break;
                }

                case "Jefe de Carrera": {
                    DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
                    DegreeBoss degreeBoss = degreeBossDAO.getDegreeBossFromDatabase(Integer.parseInt(userController.getMatriculeOrPersonalNumber()));
                    openPaneWithDegreeBossInformation(degreeBoss);
                    break;
                }

                case "Profesor": {
                    ProfessorDAO professorDAO = new ProfessorDAO();
                    Professor professor = professorDAO.getProfessorFromDatabase(Integer.parseInt(userController.getMatriculeOrPersonalNumber()));
                    openPaneWithProfessorInformation(professor);
                    break;
                }

                case "Estudiante": {
                    StudentDAO studentDAO = new StudentDAO();
                    Student student = studentDAO.getStudentFromDatabase(userController.getMatriculeOrPersonalNumber());
                    openPaneWithStudentInformation(student);
                    break;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

    private void openPaneWithDirectorInformation(Director director){
        FXMLLoader userInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxml/users/UserInformation.fxml")
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
            userInformationController.setUserType("Director");
            userInformationController.setStatus(director.getStatus());
            userInformationController.setMatricleOrPersonalNumber(Integer.toString(director.getStaffNumber()));
            userInformationController.setGuiUsersController(this);
            userInformationController.setMatricleOrPersonalNumberText();
            this.userInformationScrollPane.setContent(userInformationVBox);
            
        } catch (IOException e){
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

    private void openPaneWithAcademicBodyHeadInformation(AcademicBodyHead academicBodyHead){
        FXMLLoader userInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxml/users/UserInformation.fxml")
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
            userInformationController.setUserType("Miembro de Cuerpo Académico");
            userInformationController.setStatus(academicBodyHead.getStatus());
            userInformationController.setMatricleOrPersonalNumber(Integer.toString(academicBodyHead.getStaffNumber()));
            userInformationController.setGuiUsersController(this);
            userInformationController.setMatricleOrPersonalNumberText();
            this.userInformationScrollPane.setContent(userInformationVBox);
            
        } catch (IOException e){
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

    private void openPaneWithDegreeBossInformation(DegreeBoss degreeBoss){
        FXMLLoader userInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxml/users/UserInformation.fxml")
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
            userInformationController.setUserType("Jefe de Carrera");
            userInformationController.setStatus(degreeBoss.getStatus());
            userInformationController.setMatricleOrPersonalNumber(Integer.toString(degreeBoss.getStaffNumber()));
            userInformationController.setGuiUsersController(this);
            userInformationController.setMatricleOrPersonalNumberText();
            this.userInformationScrollPane.setContent(userInformationVBox);
            
        } catch (IOException e){
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

    private void openPaneWithProfessorInformation(Professor professor){
        FXMLLoader userInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxml/users/UserInformation.fxml")
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
            userInformationController.setUserType("Profesor");
            userInformationController.setStatus(professor.getStatus());
            userInformationController.setMatricleOrPersonalNumber(Integer.toString(professor.getStaffNumber()));
            userInformationController.setGuiUsersController(this);
            userInformationController.setMatricleOrPersonalNumberText();
            this.userInformationScrollPane.setContent(userInformationVBox);
            
        } catch (IOException e){
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

    private void openPaneWithStudentInformation(Student student){
        FXMLLoader userInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxml/users/UserInformation.fxml")
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
            userInformationController.setUserType("Estudiante");
            userInformationController.setStatus(student.getStatus());
            userInformationController.setMatricleOrPersonalNumber(student.getMatricule());
            userInformationController.setGuiUsersController(this);
            userInformationController.setMatricleOrPersonalNumberText();
            this.userInformationScrollPane.setContent(userInformationVBox);
            
        } catch (IOException e){
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

    //This method only should be used by the UserInformationController Class.
    void openModifyUserPane(UserInformationController userInformationController){
        FXMLLoader modifyUserInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxml/users/ModifyUserInformation.fxml")
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
            modifyUserInformationController.setMatricleOrPersonalNumber(userInformationController.getMatriculeOrPersonalNumber());
            modifyUserInformationController.setStatus(userInformationController.getStatus());
            modifyUserInformationController.setUserInformationController(userInformationController);
            this.userInformationScrollPane.setContent(modifyUserInformationVBox);
            
        } catch (IOException e){
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

}
