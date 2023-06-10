package mx.uv.fei.gui.controllers.courses.students;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.daos.StudentDAO;
import mx.uv.fei.logic.daos.StudentsCoursesDAO;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class GuiStudentAdderController{
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
    private Text sucessText;

    @FXML
    private void initialize(){
        try{
            StudentsCoursesDAO studentsCoursesDAO = new StudentsCoursesDAO();
            ArrayList<String> studentMatricles = studentsCoursesDAO.getStudentsMatriclesByCourseNRC(
                guiUsersCourseController.getCourseInformationController().getNrc()
            );

        
            StudentDAO studentDAO = new StudentDAO();
            ArrayList<Student> availableStudents = studentDAO.getAvailableStudents();
            if(studentMatricles.isEmpty()) {
                try{
                    for(Student activeStudent : availableStudents){
                        
                        FXMLLoader studentPaneControllerLoader = new FXMLLoader(
                            getClass().getResource("/mx/uv/fei/gui/fxml/courses/students/StudentPane.fxml")
                        );
                        Pane studentPaneToAdd = studentPaneControllerLoader.load();
                        StudentPaneController studentPaneController = studentPaneControllerLoader.getController();
                        studentPaneController.setStudentName(
                            activeStudent.getName() + " " + 
                            activeStudent.getFirstSurname() + " " + 
                            activeStudent.getSecondSurname()
                        );
                        studentPaneController.setMatricle(activeStudent.getMatricle());
                        studentsVBox.getChildren().add(studentPaneToAdd);
                        
                    }
                }catch(IOException e){
                    new AlertPopUpGenerator().showMissingFilesMessage();
                }
                return;
            }

            try{
                for(Student activeStudent : availableStudents){
                    boolean activeStudentIsAlreadyRegistedIntoTheCourse = false;
                    for(String studentMatricle : studentMatricles){
                        if(studentMatricle.equals(activeStudent.getMatricle())){
                            activeStudentIsAlreadyRegistedIntoTheCourse = true;
                            break;
                        }
                    }
    
                    if(!activeStudentIsAlreadyRegistedIntoTheCourse){
                        FXMLLoader studentPaneControllerLoader = new FXMLLoader(
                            getClass().getResource("/mx/uv/fei/gui/fxml/courses/students/StudentPane.fxml")
                        );
                        Pane studentPaneToAdd = studentPaneControllerLoader.load();
                        StudentPaneController studentPaneController = studentPaneControllerLoader.getController();
                        studentPaneController.setStudentName(
                            activeStudent.getName() + " " + 
                            activeStudent.getFirstSurname() + " " + 
                            activeStudent.getSecondSurname()
                        );
                        studentPaneController.setMatricle(activeStudent.getMatricle());
                        studentsVBox.getChildren().add(studentPaneToAdd);
                    }
                }
            }catch(IOException e){
                new AlertPopUpGenerator().showMissingFilesMessage();
            }
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }

    @FXML
    private void addStudentsButtonController(ActionEvent event){
        StudentsCoursesDAO studentCoursesDAO = new StudentsCoursesDAO();

        for(Node studentPane : studentsVBox.getChildren()){
            if( ((RadioButton)((Pane)studentPane).getChildren().get(4)).isSelected() ){
                try{
                    studentCoursesDAO.addStudentCourse(
                        ((Label)((Pane)studentPane).getChildren().get(3)).getText(), 
                        guiUsersCourseController.getCourseInformationController().getNrc()
                    );
                }catch(DataInsertionException e){
                    new AlertPopUpGenerator().showConnectionErrorMessage();
                }
            }
        }

        guiUsersCourseController.refreshStudents();


        addStudentsButton.setVisible(false);
        Stage stage = (Stage) addStudentsButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void showByMatricleButtonController(ActionEvent event){
        try{
            studentsVBox.getChildren().clear();
            StudentsCoursesDAO studentsCoursesDAO = new StudentsCoursesDAO();
            ArrayList<String> studentMatricles = studentsCoursesDAO.getStudentsMatriclesByCourseNRC(
                guiUsersCourseController.getCourseInformationController().getNrc()
            );
            
            StudentDAO studentDAO = new StudentDAO();
            ArrayList<Student> availableStudents = studentDAO.getSpecifiedAvailableStudents(showByMatricleTextField.getText());
            if(studentMatricles.isEmpty()){
                try{
                    for(Student activeStudent : availableStudents){
                        
                        FXMLLoader studentPaneControllerLoader = new FXMLLoader(
                            getClass().getResource("/mx/uv/fei/gui/fxml/courses/students/StudentPane.fxml")
                        );
                        Pane studentPaneToAdd = studentPaneControllerLoader.load();
                        StudentPaneController studentPaneController = studentPaneControllerLoader.getController();
                        studentPaneController.setStudentName(
                            activeStudent.getName() + " " + 
                            activeStudent.getFirstSurname() + " " + 
                            activeStudent.getSecondSurname()
                        );
                        studentPaneController.setMatricle(activeStudent.getMatricle());
                        studentsVBox.getChildren().add(studentPaneToAdd);
                        
                    }
                }catch(IOException e){
                    new AlertPopUpGenerator().showMissingFilesMessage();
                }
                return;
            }
    
            try{
                for(Student activeStudent : availableStudents){
                    boolean activeStudentIsAlreadyRegistedIntoTheCourse = false;
                    for(String studentMatricle : studentMatricles){
                        if(studentMatricle.equals(activeStudent.getMatricle())){
                            activeStudentIsAlreadyRegistedIntoTheCourse = true;
                            break;
                        }
                    }
    
                    if(!activeStudentIsAlreadyRegistedIntoTheCourse){
                        FXMLLoader studentPaneControllerLoader = new FXMLLoader(
                            getClass().getResource("/mx/uv/fei/gui/fxml/courses/students/StudentPane.fxml")
                        );
                        Pane studentPaneToAdd = studentPaneControllerLoader.load();
                        StudentPaneController studentPaneController = studentPaneControllerLoader.getController();
                        studentPaneController.setStudentName(
                            activeStudent.getName() + " " + 
                            activeStudent.getFirstSurname() + " " + 
                            activeStudent.getSecondSurname()
                        );
                        studentPaneController.setMatricle(activeStudent.getMatricle());
                        studentsVBox.getChildren().add(studentPaneToAdd);
                    }
                }
            }catch(IOException e){
                new AlertPopUpGenerator().showMissingFilesMessage();
            }
        }catch(DataRetrievalException e) {
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }

    }

    public GuiUsersCourseController getGuiUsersCourseController(){
        return guiUsersCourseController;
    }

    public void setGuiUsersCourseController(GuiUsersCourseController guiUsersCourseController){
        this.guiUsersCourseController = guiUsersCourseController;
    }

}