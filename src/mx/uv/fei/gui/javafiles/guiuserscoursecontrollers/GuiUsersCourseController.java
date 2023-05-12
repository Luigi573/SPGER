package mx.uv.fei.gui.javafiles.guiuserscoursecontrollers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mx.uv.fei.gui.javafiles.guicoursescontrollers.CourseInformationController;
import mx.uv.fei.gui.javafiles.guicoursescontrollers.GuiCoursesController;
import mx.uv.fei.gui.javafiles.guistudentaddercontrollers.GuiStudentAdderController;
import mx.uv.fei.gui.javafiles.guistudentaddercontrollers.StudentPaneController;
import mx.uv.fei.logic.daos.CourseDAO;
import mx.uv.fei.logic.daos.ProfessorDAO;
import mx.uv.fei.logic.daos.StudentDAO;
import mx.uv.fei.logic.daos.StudentsCoursesDAO;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.Student;

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
                getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guistudentadder/GuiStudentAdder.fxml")
            );
            GuiStudentAdderController guiStudentAdderController = new GuiStudentAdderController();
            guiStudentAdderController.setGuiUsersCourseController(this);

            loader.setController(guiStudentAdderController);
            guiStudentAdder = loader.load();
            Scene scene = new Scene(guiStudentAdder);
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
        this.refreshProfessors();
        this.refreshStudents();
    }

    private void refreshProfessors() {
        this.professorsHBox.getChildren().removeAll(this.professorsHBox.getChildren());

        CourseDAO courseDAO = new CourseDAO();
        Course course = courseDAO.getCourseFromDatabase(this.courseInformationController.getNrc());
        ProfessorDAO professorDAO = new ProfessorDAO();
        Professor professor = professorDAO.getProfessorFromDatabase(course.getPersonalNumber());        
         
        try {
            FXMLLoader userPaneControllerLoader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guiuserscourse/UserPane.fxml")
            );
            Pane professorPane = userPaneControllerLoader.load();
            UserPaneController userPaneController = userPaneControllerLoader.getController();
            userPaneController.setName(professor.getName());
            userPaneController.setMatricleOrPersonalNumber(Integer.toString(professor.getPersonalNumber()));
            userPaneController.setGuiUsersCourseController(this);
            this.professorsHBox.getChildren().add(professorPane);
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshStudents() {
        for(int i = 0; i < this.studentsVbox.getChildren().size() - 1;  i++){
            this.studentsVbox.getChildren().remove(i);
        }

        StudentsCoursesDAO studentCoursesDAO = new StudentsCoursesDAO();
        StudentDAO studentDAO = new StudentDAO();
        ArrayList<String> students = studentCoursesDAO.getStudentsMatriclesByCourseNRCFromDatabase(
            this.courseInformationController.getNrc());

        try {
            for(String studentMatricle : students) {
                FXMLLoader studentPaneControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guiuserscourse/UserPane.fxml")
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
