package mx.uv.fei.gui.javafiles.guiuserscoursecontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import mx.uv.fei.logic.daos.StudentsCoursesDAO;

public class UserPaneController {

    private GuiUsersCourseController guiUsersCourseController;

    @FXML
    private Button deleteButton;

    @FXML
    private Label matricleOrPersonalNumberLabel;

    @FXML
    private Label nameLabel;
    
    @FXML
    void deleteButtonController(ActionEvent event) {
        StudentsCoursesDAO studentsCoursesDAO = new StudentsCoursesDAO();
        studentsCoursesDAO.removeStudentCourseFromDatabase(
            this.matricleOrPersonalNumberLabel.getText(),
            this.guiUsersCourseController.getCourseInformationController().getNrc()
        );

        this.guiUsersCourseController.refreshUsers();
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
