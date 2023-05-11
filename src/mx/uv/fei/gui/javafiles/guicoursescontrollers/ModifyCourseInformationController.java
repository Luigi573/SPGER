package mx.uv.fei.gui.javafiles.guicoursescontrollers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
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
    private ComboBox<String> educativeExperienceComboBox;

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
        return this.educativeExperienceComboBox.getValue();
    }

    public void setEducativeExperience(String educativeExperince) {
        this.educativeExperienceComboBox.setValue(educativeExperince);
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


    public void setProfessorToDefaultSelect(Professor professor) {
        this.professorComboBox.setValue(professor);
    }

    public void setScholarPeriodToDefaultSelect(ScholarPeriod scholarPeriod) {
        this.scholarPeriodComboBox.setValue(scholarPeriod);
    }


    @FXML
    void initialize() {
        ProfessorDAO professorDAO = new ProfessorDAO();
        this.professorComboBox.getItems().addAll(professorDAO.getProfessorsFromDatabase());
        this.professorComboBox.setConverter(new StringConverter<Professor>() {

            @Override
            public Professor fromString(String arg0) {
                return null;
            }

            @Override
            public String toString(Professor arg0) {
                if(arg0 != null){
                    return arg0.getName();
                }
                
                return null;  
            }
            
        });

        ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
        this.scholarPeriodComboBox.getItems().addAll(scholarPeriodDAO.getScholarPeriodsFromDatabase());
        this.scholarPeriodComboBox.setConverter(new StringConverter<ScholarPeriod>() {
            
            @Override
            public ScholarPeriod fromString(String arg0) {
                return null;
            }
            
            @Override
            public String toString(ScholarPeriod arg0) {
                if(arg0 != null){
                    return arg0.getStartDate() + " " + arg0.getEndDate();
                }
                
                return null;
            }
            
        });

        this.educativeExperienceComboBox.getItems().add("Proyecto Guiado");
        this.educativeExperienceComboBox.getItems().add("Experiencia Recepcional");
        this.educativeExperienceComboBox.setValue("Proyecto Guiado");
        this.sectionComboBox.getItems().add("7");
        this.sectionComboBox.getItems().add("8");
        this.sectionComboBox.setValue("7");
        this.blockComboBox.getItems().add("1");
        this.blockComboBox.getItems().add("2");
        this.blockComboBox.setValue("1");
    }

    @FXML
    void exitButtonController(ActionEvent event) {
        this.guiCoursesController.openPaneWithCourseInformation(this.courseInformationController.getNrc());
    }

    @FXML
    void modifyButtonController(ActionEvent event) {
        if(!this.nrcTextField.getText().trim().isEmpty()) {

            if(allTextFieldsContainsCorrectValues()){
                CourseDAO courseDAO = new CourseDAO();
                Course newCourseData = new Course();
                Course oldCourseData = courseDAO.getCourseFromDatabase(this.courseInformationController.getNrc());
                newCourseData.setEEName((String)this.educativeExperienceComboBox.getValue());
                newCourseData.setNrc(Integer.parseInt(this.nrcTextField.getText()));
                newCourseData.setSection(Integer.parseInt(this.sectionComboBox.getValue()));
                newCourseData.setBlock(Integer.parseInt(this.blockComboBox.getValue()));
                newCourseData.setPersonalNumber(this.professorComboBox.getValue().getPersonalNumber());
                newCourseData.setIdScholarPeriod(this.scholarPeriodComboBox.getValue().getIdScholarPeriod());
                if(courseDAO.theCourseIsAlreadyRegisted(newCourseData)) {
                    this.errorMessageText.setText("El curso ya está registrado en el sistema");
                    this.errorMessageText.setVisible(true);
                    return;
                }
                courseDAO.modifyCourseDataFromDatabase(newCourseData, oldCourseData);
                this.errorMessageText.setText("Usuario modificado exitosamente");
                this.errorMessageText.setVisible(true);
            } else {
                this.errorMessageText.setText("Algunos campos contienen datos inválidos");
                this.errorMessageText.setVisible(true);
            }
        } else {
            this.errorMessageText.setText("Faltan campos por llenar");
            this.errorMessageText.setVisible(true);
        }
    }

    private boolean allTextFieldsContainsCorrectValues(){
        Pattern nrcPattern = Pattern.compile("^[0-9]{5}$");
        Matcher nrcMatcher = nrcPattern.matcher(this.nrcTextField.getText());

        if(nrcMatcher.find()){
            return true;
        }

        return false;
    }

}