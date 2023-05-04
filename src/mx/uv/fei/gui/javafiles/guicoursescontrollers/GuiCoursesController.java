package mx.uv.fei.gui.javafiles.guicoursescontrollers;

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
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mx.uv.fei.logic.daos.CourseDAO;
import mx.uv.fei.logic.daos.ScholarPeriodDAO;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.ScholarPeriod;

public class GuiCoursesController {

    @FXML
    private ScrollPane courseInformationScrollPane;

    @FXML
    private VBox coursesVBox;

    @FXML
    private Button registerCourseButton;

    @FXML
    private Button searchByNrcButton;

    @FXML
    private TextField searchByNrcTextField;

    @FXML
    private ImageView spgerLogo;

    @FXML
    private Text spgerText;

    @FXML
    private ImageView userLogo;

    @FXML
    private Text userNameText;

    @FXML
    private Text windowText;

    @FXML
    void initialize() {
        CourseDAO courseDAO = new CourseDAO();
        ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();

        ArrayList<Course> courses = courseDAO.getCoursesFromDatabase();
        ArrayList<ScholarPeriod> scholarPeriods = scholarPeriodDAO.getScholarPeriodsFromDatabase();
        
        this.courseButtonMaker(courses);
    }

    @FXML
    void registerCourseButtonController(ActionEvent event) {
        Parent guiRegisterCourse;
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guiregistercourse/GuiRegisterCourse.fxml")
            );
            guiRegisterCourse = loader.load();
            Scene scene = new Scene(guiRegisterCourse);
            Stage stage = new Stage();
            stage.setTitle("Registrar Curso");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }

    @FXML
    void searchByNrcButtonController(ActionEvent event) {
        this.coursesVBox.getChildren().removeAll(this.coursesVBox.getChildren());
        CourseDAO courseDAO = new CourseDAO();
        ArrayList<Course> courses = courseDAO.getSpecifiedCoursesFromDatabase(this.searchByNrcTextField.getText());
        this.courseButtonMaker(courses);
    }

    private void courseButtonMaker(ArrayList<Course> courses){
        try {
            for(Course course : courses){
                FXMLLoader courseItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guicourses/Course.fxml")
                );
                ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
                ScholarPeriod scholarPeriod = scholarPeriodDAO.getScholarPeriodFromDatabase(course.getIdScholarPeriod());
                Button courseItemButton;
                courseItemButton = courseItemControllerLoader.load();
                CourseController courseController = courseItemControllerLoader.getController();
                courseController.setEEName(course.getEEName());
                courseController.setNrc(Integer.toString(course.getNrc()));
                //courseController.setScholarPeriod();
                courseController.setGuiCoursesController(this);
                this.coursesVBox.getChildren().add(courseItemButton);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }

    //This method only should be used by the CourseController Class.
    void openPaneWithCourseInformation(String courseNRC){
        CourseDAO courseDAO = new CourseDAO();
        Course course = courseDAO.getCourseFromDatabase(courseNRC);
        FXMLLoader courseInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guicourses/CourseInformation.fxml")
        );
        try {
            VBox CourseInformationVBox = courseInformationControllerLoader.load();
            CourseInformationController courseInformationController = courseInformationControllerLoader.getController();
            courseInformationController.setEducativeExperience(course.getEEName());
            courseInformationController.setNrc(Integer.toString(course.getNrc()));
            courseInformationController.setSection(course.getSection());
            courseInformationController.setBlock(Integer.toString(course.getBlock()));
            //courseInformationController.setProfessor(course.getPersonalNumber());
            //courseInformationController.setScholarPeriod(course.getIdScholarPeriod());
            courseInformationController.setGuiCoursesController(this);
            this.courseInformationScrollPane.setContent(CourseInformationVBox);
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //This method only should be used by the CourseInformationController Class.
    void openModifyCoursePane(CourseInformationController courseInformationController){
        FXMLLoader modifyCourseInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guicourses/ModifyCourseInformation.fxml")
        );

        try {
            VBox modifyCourseInformationVBox = modifyCourseInformationControllerLoader.load();
            ModifyCourseInformationController modifyCourseInformationController = modifyCourseInformationControllerLoader.getController();
            modifyCourseInformationController.setEducativeExperience(courseInformationController.getEducativeExperience());
            modifyCourseInformationController.setNrc(courseInformationController.getNrc());
            modifyCourseInformationController.setSection(courseInformationController.getSection());
            modifyCourseInformationController.setBlock(courseInformationController.getBlock());
            modifyCourseInformationController.setProfessor(courseInformationController.getProfessor());
            modifyCourseInformationController.setScholarPeriod(courseInformationController.getScholarPeriod());
            modifyCourseInformationController.setGuiCoursesController(this);
            modifyCourseInformationController.setCourseInformationController(courseInformationController);
            this.courseInformationScrollPane.setContent(modifyCourseInformationVBox);
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}