package mx.uv.fei.gui.javafiles.guiuserscoursecontrollers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mx.uv.fei.gui.javafiles.guicoursescontrollers.CourseInformationController;
import mx.uv.fei.gui.javafiles.guicoursescontrollers.GuiCoursesController;
import mx.uv.fei.gui.javafiles.guistudentaddercontrollers.GuiStudentAdderController;
import mx.uv.fei.logic.daos.CourseDAO;
import mx.uv.fei.logic.daos.ProfessorDAO;
import mx.uv.fei.logic.daos.StudentsCoursesDAO;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Professor;

public class GuiUsersCourseController {

    private CourseInformationController courseInformationController;

    @FXML
    private Button addButton;

    @FXML
    private HBox professorsHBox;

    @FXML
    void addButtonController(ActionEvent event) {
        Parent guiStudentAdder;
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guistudentadder/GuiStudentAdder.fxml")
            );
            guiStudentAdder = loader.load();
            GuiStudentAdderController guiStudentAdderController = loader.getController();
            guiStudentAdderController.setGuiUsersCourseController(this);
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

    public void refreshUsers() {
        CourseDAO courseDAO = new CourseDAO();
        Course course = courseDAO.getCourseFromDatabase(this.courseInformationController.getNrc());
        ProfessorDAO professorDAO = new ProfessorDAO();
        Professor professor = professorDAO.getProfessorFromDatabase(course.getPersonalNumber());
        StudentsCoursesDAO studentsCoursesDAO = new StudentsCoursesDAO();
        
        
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

}
