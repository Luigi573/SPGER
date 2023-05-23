package mx.uv.fei.gui.controllers.courses.students;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.uv.fei.gui.controllers.AlertPaneController;
import mx.uv.fei.gui.controllers.courses.CourseInformationController;
import mx.uv.fei.logic.daos.StudentDAO;
import mx.uv.fei.logic.daos.StudentsCoursesDAO;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class GuiUsersCourseController {

    private CourseInformationController courseInformationController;
    private int students = 0;

    @FXML
    private Button addButton;
    @FXML
    private Pane backgroundPane;
    @FXML
    private HBox professorsHBox;
    @FXML
    private VBox studentsVbox;
    @FXML
    void initialize() {
        loadHeader();
        refreshUsers();
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
            String css = getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = new Stage();
            stage.setTitle("Agregar Estudiante");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
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
        refreshStudents();
    }

    private void refreshStudents() {
        studentsVbox.getChildren().clear();
        students = 0;

        StudentsCoursesDAO studentCoursesDAO = new StudentsCoursesDAO();
        StudentDAO studentDAO = new StudentDAO();
        try {
            ArrayList<String> students = studentCoursesDAO.getStudentsMatriclesByCourseNRCFromDatabase(
            courseInformationController.getNrc());

            for(String studentMatricle : students) {
                FXMLLoader studentPaneControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxml/courses/students/UserPane.fxml")
                );
                Pane studentPane = studentPaneControllerLoader.load();
                UserPaneController studentPaneController = studentPaneControllerLoader.getController();

                try {
                    studentPaneController.setName(studentDAO.getStudentFromDatabase(studentMatricle).getName());
                } catch (DataRetrievalException e) {
                    e.printStackTrace();
                    AlertPaneController alertPaneController = new AlertPaneController();
                    alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
                }

                studentPaneController.setMatricleOrPersonalNumber(studentMatricle);
                studentPaneController.setGuiUsersCourseController(this);
                
                if(this.students % 7 == 0) {
                    HBox studentHBox = new HBox();
                    studentsVbox.getChildren().add(studentHBox);
                    ( (HBox)studentsVbox.getChildren().get(studentsVbox.getChildren().size() - 1) ).getChildren().add(studentPane);
                    this.students++;
                } else {
                    ( (HBox)studentsVbox.getChildren().get(studentsVbox.getChildren().size() - 1) ).getChildren().add(studentPane);
                    this.students++;
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        } catch (DataRetrievalException e1) {
            e1.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

    private void loadHeader(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
        
        try{
            Pane header = loader.load();
            header.getStyleClass().add("/mx/uv/fei/gui/stylesfiles/Styles.css");
            backgroundPane.getChildren().add(header);
            
        }catch(IOException exception){
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

}