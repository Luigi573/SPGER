package mx.uv.fei.gui.controllers.courses;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.daos.CourseDAO;
import mx.uv.fei.logic.daos.ProfessorDAO;
import mx.uv.fei.logic.daos.ScholarPeriodDAO;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.ScholarPeriod;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class GuiCoursesController{
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
    private void initialize(){
        loadHeader();
        CourseDAO courseDAO = new CourseDAO();
        ArrayList<Course> courses;
        
        try{
            courses = courseDAO.getCourses();
            courseButtonMaker(courses);
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    @FXML
    private void registerCourseButtonController(ActionEvent event){
        Parent guiRegisterCourse;
        try{
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxml/courses/GuiRegisterCourse.fxml")
            );
            guiRegisterCourse = loader.load();
            Scene scene = new Scene(guiRegisterCourse);
            String css = getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = new Stage();
            stage.setTitle("Registrar Curso");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
            new AlertPopUpGenerator().showMissingFilesMessage();
        }   
    }
    @FXML
    private void searchByNrcButtonController(ActionEvent event){
        coursesVBox.getChildren().removeAll(coursesVBox.getChildren());
        CourseDAO courseDAO = new CourseDAO();
        ArrayList<Course> courses;

        try{
            courses = courseDAO.getSpecifiedCourses(searchByNrcTextField.getText());
            courseButtonMaker(courses);
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }

    //This method only should be used by the CourseInformationController Class.
    public void openModifyCoursePane(CourseInformationController courseInformationController){
        CourseDAO courseDAO = new CourseDAO();
        try {
            Course course = courseDAO.getCourse(courseInformationController.getNrc());
            FXMLLoader modifyCourseInformationControllerLoader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxml/courses/ModifyCourseInformation.fxml")
            );
            VBox modifyCourseInformationVBox = modifyCourseInformationControllerLoader.load();
            ModifyCourseInformationController modifyCourseInformationController = modifyCourseInformationControllerLoader.getController();
            modifyCourseInformationController.setEducativeExperience(course.getName());
            modifyCourseInformationController.setNrc(Integer.toString(course.getNrc()));
            modifyCourseInformationController.setSection(Integer.toString(course.getSection()));
            modifyCourseInformationController.setBlock(Integer.toString(course.getBlock()));
            if(course.getProfessor() != null){
                ProfessorDAO professorDAO = new ProfessorDAO();
                Professor professor = professorDAO.getProfessor(course.getProfessor().getStaffNumber());
                modifyCourseInformationController.setProfessor(professor);
            }
            
            if(course.getScholarPeriod() != null){
                ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
                ScholarPeriod scholarPeriod = scholarPeriodDAO.getScholarPeriod(course.getScholarPeriod().getScholarPeriodId());
                modifyCourseInformationController.setScholarPeriod(scholarPeriod);
            }

            modifyCourseInformationController.setCourse(course);
            modifyCourseInformationController.setGuiCoursesController(this);
            modifyCourseInformationController.setCourseInformationController(courseInformationController);
            courseInformationScrollPane.setContent(modifyCourseInformationVBox);
        }catch(IOException e){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    //This method only should be used by the CourseController Class.
    public void openPaneWithCourseInformation(String courseNRC){
        CourseDAO courseDAO = new CourseDAO();
        try{
            Course course = courseDAO.getCourse(courseNRC);
            FXMLLoader courseInformationControllerLoader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxml/courses/CourseInformation.fxml")
            );
            VBox courseInformationVBox = courseInformationControllerLoader.load();
            CourseInformationController courseInformationController = courseInformationControllerLoader.getController();
            courseInformationController.setEducativeExperience(course.getName());
            courseInformationController.setNrc(Integer.toString(course.getNrc()));
            courseInformationController.setSection(Integer.toString(course.getSection()));
            courseInformationController.setBlock(Integer.toString(course.getBlock()));
            if(course.getProfessor() != null){
                ProfessorDAO professorDAO = new ProfessorDAO();
                Professor professor = professorDAO.getProfessor(course.getProfessor().getStaffNumber());
                courseInformationController.setProfessor(professor.getName());
            }else{
                courseInformationController.setProfessor("Sin Asignar");
            }

            if(course.getScholarPeriod() != null){
                ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
                ScholarPeriod scholarPeriod = scholarPeriodDAO.getScholarPeriod(course.getScholarPeriod().getScholarPeriodId());
                courseInformationController.setScholarPeriod(scholarPeriod.toString());
            }else{
                courseInformationController.setScholarPeriod("Sin Asignar");
            }

            courseInformationController.setGuiCoursesController(this);
            courseInformationScrollPane.setContent(courseInformationVBox);
            
        }catch(IOException e){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }

    private void courseButtonMaker(ArrayList<Course> courses){
        try {
            for(Course course : courses){
                FXMLLoader courseItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxml/courses/Course.fxml")
                );
                Pane courseItemPane = courseItemControllerLoader.load();
                CourseController courseController = courseItemControllerLoader.getController();
                courseController.setName(course.getName());
                courseController.setNrc(Integer.toString(course.getNrc()));
                
                if(course.getScholarPeriod() != null){
                    ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
                    ScholarPeriod scholarPeriod = scholarPeriodDAO.getScholarPeriod(course.getScholarPeriod().getScholarPeriodId());
                    courseController.setScholarPeriod(scholarPeriod.toString());
                }else{
                    courseController.setScholarPeriod("Sin Asignar");
                }

                courseController.setGuiCoursesController(this);
                coursesVBox.getChildren().add(courseItemPane);
            }
        }catch(IOException e){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }    
    }
    private void loadHeader(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
        
        try{
            Pane header = loader.load();
            header.getStyleClass().add("/mx/uv/fei/gui/stylesfiles/Styles.css");
            backgroundPane.getChildren().add(header);
            
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
}