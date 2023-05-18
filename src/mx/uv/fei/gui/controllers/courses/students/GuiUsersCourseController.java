package mx.uv.fei.gui.controllers.courses.students;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mx.uv.fei.gui.controllers.courses.CourseInformationController;
import mx.uv.fei.logic.daos.StudentDAO;
import mx.uv.fei.logic.daos.StudentsCoursesDAO;

public class GuiUsersCourseController {

    private int students = 0;

    private CourseInformationController courseInformationController;

    @FXML
    private Button addButton;

    @FXML
    private HBox professorsHBox;

    @FXML
    private VBox studentsVbox;

    @FXML
    void initialize() {
        this.refreshUsers();
    }

    @FXML
    void addButtonController(ActionEvent event) {
        Parent guiStudentAdder;
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxml/courses/students/GuiStudentAdder.fxml")
            );
            GuiStudentAdderController guiStudentAdderController = new GuiStudentAdderController();
            guiStudentAdderController.setGuiUsersCourseController(this);

            loader.setController(guiStudentAdderController);
            guiStudentAdder = loader.load();
            Scene scene = new Scene(guiStudentAdder);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = new Stage();
            stage.setTitle("Agregar Estudiante");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }

    public CourseInformationController getCourseInformationController() {
        return courseInformationController;
    }

    public void setCourseInformationController(CourseInformationController courseInformationController) {
        this.courseInformationController = courseInformationController;
    }

    public VBox getStudentsVbox() {
        return studentsVbox;
    }

    public void refreshUsers() {
        this.refreshStudents();
    }

    private void refreshStudents() {
        this.studentsVbox.getChildren().clear();
        this.students = 0;

        StudentsCoursesDAO studentCoursesDAO = new StudentsCoursesDAO();
        StudentDAO studentDAO = new StudentDAO();
        ArrayList<String> students = studentCoursesDAO.getStudentsMatriclesByCourseNRCFromDatabase(
            this.courseInformationController.getNrc());

        try {
            for(String studentMatricle : students) {
                FXMLLoader studentPaneControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxml/courses/students/UserPane.fxml")
                );
                Pane studentPane = studentPaneControllerLoader.load();
                UserPaneController studentPaneController = studentPaneControllerLoader.getController();
                studentPaneController.setName(studentDAO.getStudentFromDatabase(studentMatricle).getName());
                studentPaneController.setMatricleOrPersonalNumber(studentMatricle);
                studentPaneController.setGuiUsersCourseController(this);
                if(this.students % 6 == 0) {
                    HBox studentHBox = new HBox();
                    this.studentsVbox.getChildren().add(studentHBox);
                    ( (HBox)this.studentsVbox.getChildren().get(this.studentsVbox.getChildren().size() - 1) ).getChildren().add(studentPane);
                    this.students++;
                } else {
                    ( (HBox)this.studentsVbox.getChildren().get(this.studentsVbox.getChildren().size() - 1) ).getChildren().add(studentPane);
                    this.students++;
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
