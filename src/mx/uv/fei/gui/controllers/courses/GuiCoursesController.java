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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.daos.CourseDAO;
import mx.uv.fei.logic.daos.ProfessorDAO;
import mx.uv.fei.logic.daos.ScholarPeriodDAO;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.ScholarPeriod;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class GuiCoursesController {
    private User user;

    @FXML
    private ScrollPane courseInformationScrollPane;
    @FXML
    private Button registerCourseButton;
    @FXML
    private Button searchByNrcButton;
    @FXML
    private Pane headerPane;
    @FXML
    private TextField searchByNrcTextField;
    @FXML
    private Text spgerText;
    @FXML
    private Text userNameText;
    @FXML
    private Text windowText;
    @FXML
    private VBox coursesVBox;
    
    @FXML
    private void initialize() {
        try{
            CourseDAO courseDAO = new CourseDAO();
            ArrayList<Course> courses = courseDAO.getCourses();
            this.courseButtonMaker(courses);
        }catch(DataRetrievalException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }

    @FXML
    private void registerCourse(ActionEvent event) {
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
            new AlertPopUpGenerator().showMissingFilesMessage();
        }   
    }

    @FXML
    void searchByNrcButtonController(ActionEvent event) {
        try{
            this.coursesVBox.getChildren().removeAll(this.coursesVBox.getChildren());
            CourseDAO courseDAO = new CourseDAO();
            ArrayList<Course> courses = courseDAO.getSpecifiedCourses(this.searchByNrcTextField.getText());
            this.courseButtonMaker(courses);
        }catch(DataRetrievalException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    
    public void setUser(User user){
        this.user = user;
    }

    //This method only should be used by the CourseInformationController Class.
    public void openModifyCoursePane(CourseInformationController courseInformationController){
        try{
            CourseDAO courseDAO = new CourseDAO();
            Course course = courseDAO.getCourse(courseInformationController.getNrc());
            ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
            ScholarPeriod scholarPeriod = scholarPeriodDAO.getScholarPeriod(course.getScholarPeriod().getScholarPeriodId());
            ProfessorDAO professorDAO = new ProfessorDAO();
            Professor professor = professorDAO.getProfessor(course.getProfessor().getStaffNumber());
            FXMLLoader modifyCourseInformationControllerLoader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guicourses/ModifyCourseInformation.fxml")
            );

            try {
                VBox modifyCourseInformationVBox = modifyCourseInformationControllerLoader.load();
                ModifyCourseInformationController modifyCourseInformationController = modifyCourseInformationControllerLoader.getController();
                modifyCourseInformationController.setEducativeExperience(course.getName());
                modifyCourseInformationController.setNrc(Integer.toString(course.getNrc()));
                modifyCourseInformationController.setSection(Integer.toString(course.getSection()));
                modifyCourseInformationController.setBlock(Integer.toString(course.getBlock()));
                modifyCourseInformationController.setProfessor(professor);
                modifyCourseInformationController.setScholarPeriod(scholarPeriod);
                modifyCourseInformationController.setGuiCoursesController(this);
                modifyCourseInformationController.setCourseInformationController(courseInformationController);
                this.courseInformationScrollPane.setContent(modifyCourseInformationVBox);

            } catch (IOException e){
                new AlertPopUpGenerator().showMissingFilesMessage();
            }
        }catch(DataRetrievalException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    
    //This method only should be used by the CourseController Class.
    public void openPaneWithCourseInformation(String courseNRC){
        try{
            CourseDAO courseDAO = new CourseDAO();
            Course course = courseDAO.getCourse(courseNRC);
            ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
            ScholarPeriod scholarPeriod = scholarPeriodDAO.getScholarPeriod(course.getScholarPeriod().getScholarPeriodId());
            ProfessorDAO professorDAO = new ProfessorDAO();
            Professor professor = professorDAO.getProfessor(course.getProfessor().getStaffNumber());
            FXMLLoader courseInformationControllerLoader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guicourses/CourseInformation.fxml")
            );
            try {
                VBox courseInformationVBox = courseInformationControllerLoader.load();
                CourseInformationController courseInformationController = courseInformationControllerLoader.getController();
                courseInformationController.setEducativeExperience(course.getName());
                courseInformationController.setNrc(Integer.toString(course.getNrc()));
                courseInformationController.setSection(Integer.toString(course.getSection()));
                courseInformationController.setBlock(Integer.toString(course.getBlock()));
                courseInformationController.setProfessor(professor.getName());
                courseInformationController.setScholarPeriod(scholarPeriod.toString());
                courseInformationController.setGuiCoursesController(this);
                this.courseInformationScrollPane.setContent(courseInformationVBox);

            } catch (IOException e){
                new AlertPopUpGenerator().showMissingFilesMessage();
            }
        }catch(DataRetrievalException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }   
    
    private void courseButtonMaker(ArrayList<Course> courses){
        try {
            for(Course course : courses){
                FXMLLoader courseItemControllerLoader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guicourses/Course.fxml"));
                ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
                ScholarPeriod scholarPeriod = scholarPeriodDAO.getScholarPeriod(course.getScholarPeriod().getScholarPeriodId());
                
                Button courseItemButton;
                courseItemButton = courseItemControllerLoader.load();
                CourseController courseController = courseItemControllerLoader.getController();
                courseController.setName(course.getName());
                courseController.setNrc(Integer.toString(course.getNrc()));
                courseController.setScholarPeriod(scholarPeriod.toString());
                courseController.setGuiCoursesController(this);
                this.coursesVBox.getChildren().add(courseItemButton);
            }
        } catch (DataRetrievalException | IOException e) {
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
}