package mx.uv.fei.gui.javafiles.guicoursescontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import mx.uv.fei.logic.daos.CourseDAO;
import mx.uv.fei.logic.daos.ProfessorDAO;
import mx.uv.fei.logic.daos.ScholarPeriodDAO;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.ScholarPeriod;

public class ModifyCourseInformationController {

    private GuiCoursesController guiCoursesController;

    private CourseInformationController courseInformationController;

    @FXML
    private ComboBox<String> blockComboBox;

    @FXML
    private ComboBox<String> educativeExperinceComboBox;

    @FXML
    private Text errorMessageText;

    @FXML
    private Button exitButton;

    @FXML
    private Button modifyButton;

    @FXML
    private TextField nrcTextField;

    @FXML
    private ComboBox<Professor> professorComboBox;

    @FXML
    private ComboBox<ScholarPeriod> scholarPeriodComboBox;

    @FXML
    private ComboBox<String> sectionComboBox;

    public GuiCoursesController getGuiCoursesController() {
        return this.guiCoursesController;
    }

    public void setGuiCoursesController(GuiCoursesController guiCoursesController) {
        this.guiCoursesController = guiCoursesController;
    }

    public CourseInformationController getCourseInformationController() {
        return this.courseInformationController;
    }

    public void setCourseInformationController(CourseInformationController courseInformationController) {
        this.courseInformationController = courseInformationController;
    }

    public String getBlock() {
        return this.blockComboBox.getValue();
    }

    public void setBlock(String block) {
        this.blockComboBox.setValue(block);
    }

    public String getEducativeExperience() {
        return this.educativeExperinceComboBox.getValue();
    }

    public void setEducativeExperience(String educativeExperince) {
        this.educativeExperinceComboBox.setValue(educativeExperince);
    }

    public String getNrc() {
        return this.nrcTextField.getText();
    }

    public void setNrc(String nrc) {
        this.nrcTextField.setText(nrc);
    }
    
    public Professor getProfessor() {
        return this.professorComboBox.getValue();
    }

    public void setProfessor(Professor professor) {
        this.professorComboBox.setValue(professor);
    }

    public ScholarPeriod getScholarPeriod() {
        return this.scholarPeriodComboBox.getValue();
    }

    public void setScholarPeriod(ScholarPeriod scholarPeriod) {
        this.scholarPeriodComboBox.setValue(scholarPeriod);
    }

    public String getSection() {
        return this.sectionComboBox.getValue();
    }

    public void setSection(String section) {
        this.sectionComboBox.setValue(section);
    }

    @FXML
    void exitButtonController(ActionEvent event) {
        this.guiCoursesController.openPaneWithCourseInformation(this.nrcTextField.getText());
    }

    @FXML
    void modifyButtonController(ActionEvent event) {
        if(!this.nrcTextField.getText().trim().isEmpty()) {
            CourseDAO courseDAO = new CourseDAO();
            Course course = new Course();
            course.setEEName((String)this.educativeExperinceComboBox.getValue());
            course.setNrc(Integer.parseInt(this.nrcTextField.getText()));
            course.setSection((String)this.sectionComboBox.getValue());
            course.setBlock(Integer.parseInt(this.blockComboBox.getValue()));
            course.setPersonalNumber(Integer.parseInt(this.professorComboBox.getValue().getPersonalNumber()));
            course.setIdScholarPeriod(this.scholarPeriodComboBox.getValue().getIdScholarPeriod());
            if(courseDAO.theCourseIsAlreadyRegisted(course)){
                this.errorMessageText.setText("El curso ya está registrado en el sistema");
                this.errorMessageText.setVisible(true);
                return;
            }
            courseDAO.modifyCourseDataFromDatabase(course, this.createCourseObjectWithCourseInformationPaneLabelsData());
            this.errorMessageText.setText("Usuario registrado exitosamente");
            this.errorMessageText.setVisible(true);
        } else {
            this.errorMessageText.setText("Faltan campos por llenar");
            this.errorMessageText.setVisible(true);
        }
    }

    private Course createCourseObjectWithCourseInformationPaneLabelsData() {
        Course course = new Course();
        course.setEEName(this.courseInformationController.getEducativeExperience());
        course.setNrc(Integer.parseInt(this.courseInformationController.getNrc()));
        course.setSection(this.courseInformationController.getSection());
        course.setBlock(Integer.parseInt(this.courseInformationController.getBlock()));
        course.setPersonalNumber(Integer.parseInt(this.courseInformationController.getProfessor())); //error
        course.setIdScholarPeriod(this.courseInformationController.getScholarPeriod());
        return course;
    }

}