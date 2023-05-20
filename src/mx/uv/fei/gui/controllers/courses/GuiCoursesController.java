package mx.uv.fei.gui.controllers.courses;

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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mx.uv.fei.gui.controllers.AlertPaneController;
import mx.uv.fei.logic.daos.CourseDAO;
import mx.uv.fei.logic.daos.ProfessorDAO;
import mx.uv.fei.logic.daos.ScholarPeriodDAO;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.ScholarPeriod;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class GuiCoursesController {

    @FXML
    private ScrollPane courseInformationScrollPane;

    @FXML
    private VBox coursesVBox;

    @FXML
    private Pane backgroundPane;

    @FXML
    private Button registerCourseButton;

    @FXML
    private Button searchByNrcButton;

    @FXML
    private TextField searchByNrcTextField;

    @FXML
    void initialize() {
        //this.loadHeader();
        CourseDAO courseDAO = new CourseDAO();
        ArrayList<Course> courses;
        
        try {
            courses = courseDAO.getCoursesFromDatabase();
            this.courseButtonMaker(courses);
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

    @FXML
    void registerCourseButtonController(ActionEvent event) {
        Parent guiRegisterCourse;
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxml/courses/GuiRegisterCourse.fxml")
            );
            guiRegisterCourse = loader.load();
            Scene scene = new Scene(guiRegisterCourse);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = new Stage();
            stage.setTitle("Registrar Curso");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }   
    }

    @FXML
    void searchByNrcButtonController(ActionEvent event) {
        this.coursesVBox.getChildren().removeAll(this.coursesVBox.getChildren());
        CourseDAO courseDAO = new CourseDAO();
        ArrayList<Course> courses;

        try {
            courses = courseDAO.getSpecifiedCoursesFromDatabase(this.searchByNrcTextField.getText());
            this.courseButtonMaker(courses);
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

    private void courseButtonMaker(ArrayList<Course> courses){
        try {
            for(Course course : courses){
                FXMLLoader courseItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxml/courses/Course.fxml")
                );
                ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
                ScholarPeriod scholarPeriod = scholarPeriodDAO.getScholarPeriodFromDatabase(course.getIdScholarPeriod());
                Pane courseItemPane;
                courseItemPane = courseItemControllerLoader.load();
                CourseController courseController = courseItemControllerLoader.getController();
                courseController.setEEName(course.getEEName());
                courseController.setNrc(Integer.toString(course.getNrc()));
                courseController.setScholarPeriod(scholarPeriod.toString());
                courseController.setGuiCoursesController(this);
                this.coursesVBox.getChildren().add(courseItemPane);
            }
        } catch (IOException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }    
    }

    //This method only should be used by the CourseController Class.
    void openPaneWithCourseInformation(String courseNRC){
        CourseDAO courseDAO = new CourseDAO();
        try {
            Course course = courseDAO.getCourseFromDatabase(courseNRC);
            ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
            ScholarPeriod scholarPeriod = scholarPeriodDAO.getScholarPeriodFromDatabase(course.getIdScholarPeriod());
            ProfessorDAO professorDAO = new ProfessorDAO();
            Professor professor = professorDAO.getProfessorFromDatabase(course.getStaffNumber());
            FXMLLoader courseInformationControllerLoader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxml/courses/CourseInformation.fxml")
            );
            VBox courseInformationVBox = courseInformationControllerLoader.load();
            CourseInformationController courseInformationController = courseInformationControllerLoader.getController();
            courseInformationController.setEducativeExperience(course.getEEName());
            courseInformationController.setNrc(Integer.toString(course.getNrc()));
            courseInformationController.setSection(Integer.toString(course.getSection()));
            courseInformationController.setBlock(Integer.toString(course.getBlock()));
            courseInformationController.setProfessor(professor.getName());
            courseInformationController.setScholarPeriod(scholarPeriod.toString());
            courseInformationController.setGuiCoursesController(this);
            this.courseInformationScrollPane.setContent(courseInformationVBox);
            
        } catch (IOException e){
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

    //This method only should be used by the CourseInformationController Class.
    void openModifyCoursePane(CourseInformationController courseInformationController){
        CourseDAO courseDAO = new CourseDAO();
        try {
            Course course = courseDAO.getCourseFromDatabase(courseInformationController.getNrc());
            ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
            ScholarPeriod scholarPeriod = scholarPeriodDAO.getScholarPeriodFromDatabase(course.getIdScholarPeriod());
            ProfessorDAO professorDAO = new ProfessorDAO();
            Professor professor = professorDAO.getProfessorFromDatabase(course.getStaffNumber());
            FXMLLoader modifyCourseInformationControllerLoader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxml/courses/ModifyCourseInformation.fxml")
            );

            VBox modifyCourseInformationVBox = modifyCourseInformationControllerLoader.load();
            ModifyCourseInformationController modifyCourseInformationController = modifyCourseInformationControllerLoader.getController();
            modifyCourseInformationController.setEducativeExperience(course.getEEName());
            modifyCourseInformationController.setNrc(Integer.toString(course.getNrc()));
            modifyCourseInformationController.setSection(Integer.toString(course.getSection()));
            modifyCourseInformationController.setBlock(Integer.toString(course.getBlock()));
            modifyCourseInformationController.setProfessor(professor);
            modifyCourseInformationController.setScholarPeriod(scholarPeriod);
            modifyCourseInformationController.setGuiCoursesController(this);
            modifyCourseInformationController.setCourseInformationController(courseInformationController);
            this.courseInformationScrollPane.setContent(modifyCourseInformationVBox);
            
        } catch(IOException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        } catch(DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

    //private void loadHeader(){
    //    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
    //    
    //    try{
    //        Pane header = loader.load();
    //        mainPane.getChildren().add(header);
    //        HeaderPaneController headerController = loader.getController();
    //        
    //    }catch(IOException exception){
    //        AlertPaneController alertPaneController = new AlertPaneController();
    //        alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
    //    }
    //}

}