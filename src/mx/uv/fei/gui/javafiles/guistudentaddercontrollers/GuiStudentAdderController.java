package mx.uv.fei.gui.javafiles.guistudentaddercontrollers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import mx.uv.fei.gui.javafiles.guiuserscoursecontrollers.GuiUsersCourseController;
import mx.uv.fei.logic.daos.StudentDAO;
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
    void initialize() {
        StudentDAO studentDAO = new StudentDAO();
        ArrayList<Student> students = studentDAO.getActiveStudentsFromDatabase();
        FXMLLoader studentPaneControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guistudentadder/StudentPane.fxml")
        );
        try {
            for(Student student : students) {
                Pane studentPane = studentPaneControllerLoader.load();
                StudentPaneController studentPaneController = studentPaneControllerLoader.getController();
                studentPaneController.setStudentName(student.getName());
                studentPaneController.setMatricle(student.getMatricle());
                this.studentsVBox.getChildren().add(studentPane);
            }
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void addStudentsButtonController(ActionEvent event) {
        for(Node studentPane : this.studentsVBox.getChildren()){
            //TODO : add students to studentcourse
        }
    }

    //Pendiente, no funciona
    @FXML
    void showByMatricleButtonController(ActionEvent event) {
        StudentDAO studentDAO = new StudentDAO();
        ArrayList<Student> students = studentDAO.getSpecifiedActiveStudentsFromDatabase(this.showByMatricleTextField.getText());
        FXMLLoader studentPaneControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guistudentadder/StudentPane.fxml")
        );
        try {
            for(Student student : students) {
                Pane studentPane = studentPaneControllerLoader.load();
                StudentPaneController studentPaneController = studentPaneControllerLoader.getController();
                studentPaneController.setStudentName(student.getName());
                studentPaneController.setMatricle(student.getMatricle());
                this.studentsVBox.getChildren().add(studentPane);
            }
            
        } catch (IOException e){
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
