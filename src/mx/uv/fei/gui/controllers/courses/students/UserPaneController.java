package mx.uv.fei.gui.controllers.courses.students;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import mx.uv.fei.gui.controllers.AlertPaneController;
import mx.uv.fei.logic.daos.StudentsCoursesDAO;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class UserPaneController {
    private GuiUsersCourseController guiUsersCourseController;

    @FXML
    private Button deleteButton;
    @FXML
    private Label matricleOrPersonalNumberLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Pane userPane;
    
    @FXML
    void deleteButtonController(ActionEvent event) {
        StudentsCoursesDAO studentsCoursesDAO = new StudentsCoursesDAO();
        try {
            studentsCoursesDAO.removeStudentCourseFromDatabase(
                this.matricleOrPersonalNumberLabel.getText(),
                this.guiUsersCourseController.getCourseInformationController().getNrc()
            );
        } catch (DataWritingException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }

        this.guiUsersCourseController.refreshStudents();
    }
    
    public String getMatricleOrPersonalNumber() {
        return this.matricleOrPersonalNumberLabel.getText();
    }

    public void setMatricleOrPersonalNumber(String matricleOrPersonalNumber) {
        this.matricleOrPersonalNumberLabel.setText(matricleOrPersonalNumber);
    }

    public String getName() {
        return this.nameLabel.getText();
    }

    public void setName(String name) {
        this.nameLabel.setText(name);
    }

    public GuiUsersCourseController getGuiUsersCourseController() {
        return guiUsersCourseController;
    }

    public void setGuiUsersCourseController(GuiUsersCourseController guiUsersCourseController) {
        this.guiUsersCourseController = guiUsersCourseController;
    }

}
