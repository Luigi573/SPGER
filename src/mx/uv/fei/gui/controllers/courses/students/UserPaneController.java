package mx.uv.fei.gui.controllers.courses.students;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.daos.StudentDAO;
import mx.uv.fei.logic.daos.StudentsCoursesDAO;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.domain.statuses.StudentStatus;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class UserPaneController{
    private GuiUsersCourseController guiUsersCourseController;

    @FXML
    private Button deleteButton;
    @FXML
    private Label matricleOrStaffNumberLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Pane userPane;
    
    @FXML
    private void deleteButtonController(ActionEvent event){
        StudentsCoursesDAO studentsCoursesDAO = new StudentsCoursesDAO();
        StudentDAO studentDAO = new StudentDAO();
        try{
            Student student = studentDAO.getStudent(matricleOrStaffNumberLabel.getText());
            studentsCoursesDAO.removeStudentCourse(
                matricleOrStaffNumberLabel.getText(),
                guiUsersCourseController.getCourseInformationController().getNrc()
            );
            studentDAO.updateStudentStatus(student, StudentStatus.AVAILABLE.getValue());
        }catch(DataInsertionException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
        guiUsersCourseController.refreshStudents();
    }
    
    public String getMatricleOrStaffNumber(){
        return matricleOrStaffNumberLabel.getText();
    }
    public void setMatricleOrStaffNumber(String matricleOrStaffNumber){
        matricleOrStaffNumberLabel.setText(matricleOrStaffNumber);
    }
    public String getName(){
        return nameLabel.getText();
    }
    public void setName(String name){
        nameLabel.setText(name);
    }
    public GuiUsersCourseController getGuiUsersCourseController(){
        return guiUsersCourseController;
    }
    public void setGuiUsersCourseController(GuiUsersCourseController guiUsersCourseController){
        this.guiUsersCourseController = guiUsersCourseController;
    }
}