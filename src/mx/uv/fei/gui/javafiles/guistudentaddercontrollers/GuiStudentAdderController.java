package mx.uv.fei.gui.javafiles.guistudentaddercontrollers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import mx.uv.fei.gui.javafiles.guiuserscoursecontrollers.GuiUsersCourseController;
import mx.uv.fei.logic.daos.StudentDAO;
import mx.uv.fei.logic.daos.StudentsCoursesDAO;
import mx.uv.fei.logic.domain.Student;

public class GuiStudentAdderController {

    private GuiUsersCourseController guiUsersCourseController;

    @FXML
    private Button addStudentsButton;

    @FXML
    private Button showByMatricleButton;

    @FXML
    private TextField showByMatricleTextField;

    @FXML
    private VBox studentsVBox;

    @FXML
    private Text sucessText;

    @FXML
    void initialize() {
        StudentsCoursesDAO studentsCoursesDAO = new StudentsCoursesDAO();
        ArrayList<String> studentMatricles = studentsCoursesDAO.getStudentsMatriclesByCourseNRCFromDatabase(
            this.guiUsersCourseController.getCourseInformationController().getNrc()
        );
        StudentDAO studentDAO = new StudentDAO();
        ArrayList<Student> activeStudents = studentDAO.getActiveStudentsFromDatabase();

        if(studentMatricles.isEmpty()) {
            try {
                for(Student activeStudent : activeStudents) {
                    
                    FXMLLoader studentPaneControllerLoader = new FXMLLoader(
                        getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guistudentadder/StudentPane.fxml")
                    );
                    Pane studentPaneToAdd = studentPaneControllerLoader.load();
                    StudentPaneController studentPaneController = studentPaneControllerLoader.getController();
                    studentPaneController.setStudentName(activeStudent.getName());
                    studentPaneController.setMatricle(activeStudent.getMatricle());
                    this.studentsVBox.getChildren().add(studentPaneToAdd);
                    
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try {
            for(Student activeStudent : activeStudents) {
                boolean activeStudentIsAlreadyRegistedIntoTheCourse = false;
                for(String studentMatricle : studentMatricles) {
                    if(studentMatricle.equals(activeStudent.getMatricle())) {
                        activeStudentIsAlreadyRegistedIntoTheCourse = true;
                        break;
                    }
                }

                if(!activeStudentIsAlreadyRegistedIntoTheCourse) {
                    FXMLLoader studentPaneControllerLoader = new FXMLLoader(
                        getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guistudentadder/StudentPane.fxml")
                    );
                    Pane studentPaneToAdd = studentPaneControllerLoader.load();
                    StudentPaneController studentPaneController = studentPaneControllerLoader.getController();
                    studentPaneController.setStudentName(activeStudent.getName());
                    studentPaneController.setMatricle(activeStudent.getMatricle());
                    this.studentsVBox.getChildren().add(studentPaneToAdd);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addStudentsButtonController(ActionEvent event) {
        StudentsCoursesDAO studentCoursesDAO = new StudentsCoursesDAO();

        for(Node studentPane : this.studentsVBox.getChildren()) {
            if( ((RadioButton)((Pane)studentPane).getChildren().get(4)).isSelected() ) {
                studentCoursesDAO.addStudentCourseToDatabase(
                    ((Label)((Pane)studentPane).getChildren().get(3)).getText(), 
                    this.guiUsersCourseController.getCourseInformationController().getNrc()
                );
            }
        }

        this.guiUsersCourseController.refreshUsers();
        this.sucessText.setVisible(true);
        //PauseTransition delay = new PauseTransition(Duration.seconds(5));
        //delay.setOnFinished( this.guiUsersCourseController. stage.close() );
        //delay.play();

        this.addStudentsButton.setVisible(false);
        Stage stage = (Stage) addStudentsButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void showByMatricleButtonController(ActionEvent event) {
        this.studentsVBox.getChildren().clear();
        StudentsCoursesDAO studentsCoursesDAO = new StudentsCoursesDAO();
        ArrayList<String> studentMatricles = studentsCoursesDAO.getStudentsMatriclesByCourseNRCFromDatabase(
            this.guiUsersCourseController.getCourseInformationController().getNrc()
        );
        StudentDAO studentDAO = new StudentDAO();
        ArrayList<Student> activeStudents = studentDAO.getSpecifiedActiveStudentsFromDatabase(this.showByMatricleTextField.getText());

        if(studentMatricles.isEmpty()) {
            try {
                for(Student activeStudent : activeStudents) {
                    
                    FXMLLoader studentPaneControllerLoader = new FXMLLoader(
                        getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guistudentadder/StudentPane.fxml")
                    );
                    Pane studentPaneToAdd = studentPaneControllerLoader.load();
                    StudentPaneController studentPaneController = studentPaneControllerLoader.getController();
                    studentPaneController.setStudentName(activeStudent.getName());
                    studentPaneController.setMatricle(activeStudent.getMatricle());
                    this.studentsVBox.getChildren().add(studentPaneToAdd);
                    
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try {
            for(Student activeStudent : activeStudents) {
                boolean activeStudentIsAlreadyRegistedIntoTheCourse = false;
                for(String studentMatricle : studentMatricles) {
                    if(studentMatricle.equals(activeStudent.getMatricle())) {
                        activeStudentIsAlreadyRegistedIntoTheCourse = true;
                        break;
                    }
                }

                if(!activeStudentIsAlreadyRegistedIntoTheCourse) {
                    FXMLLoader studentPaneControllerLoader = new FXMLLoader(
                        getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guistudentadder/StudentPane.fxml")
                    );
                    Pane studentPaneToAdd = studentPaneControllerLoader.load();
                    StudentPaneController studentPaneController = studentPaneControllerLoader.getController();
                    studentPaneController.setStudentName(activeStudent.getName());
                    studentPaneController.setMatricle(activeStudent.getMatricle());
                    this.studentsVBox.getChildren().add(studentPaneToAdd);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GuiUsersCourseController getGuiUsersCourseController() {
        return guiUsersCourseController;
    }

    public void setGuiUsersCourseController(GuiUsersCourseController guiUsersCourseController) {
        this.guiUsersCourseController = guiUsersCourseController;
    }

}
