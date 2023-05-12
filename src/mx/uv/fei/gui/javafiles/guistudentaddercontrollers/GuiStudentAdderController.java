package mx.uv.fei.gui.javafiles.guistudentaddercontrollers;

import java.io.IOException;
import java.util.ArrayList;

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
    void initialize() {
        StudentDAO studentDAO = new StudentDAO();
        ArrayList<Student> students = studentDAO.getActiveStudentsFromDatabase();
        try {
            for(Student student : students) {
                for(Node hbox : this.guiUsersCourseController.getStudentsVbox().getChildren()) {
                    for(Node studentPane : ((HBox)hbox).getChildren()) {
                        Node studentMatricleLabel = ((Pane)studentPane).getChildren().get(1);
                        if(((Label)studentMatricleLabel).getText().equals(student.getMatricle()) ) {
                            continue;
                        }
                        FXMLLoader studentPaneControllerLoader = new FXMLLoader(
                            getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guistudentadder/StudentPane.fxml")
                        );
                        Pane studentPaneToAdd = studentPaneControllerLoader.load();
                        StudentPaneController studentPaneController = studentPaneControllerLoader.getController();
                        studentPaneController.setStudentName(student.getName());
                        studentPaneController.setMatricle(student.getMatricle());
                        this.studentsVBox.getChildren().add(studentPaneToAdd);
                    }
                }
            }
            
        } catch (IOException e){
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
