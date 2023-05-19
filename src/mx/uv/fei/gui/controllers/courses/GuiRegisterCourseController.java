package mx.uv.fei.gui.controllers.courses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import mx.uv.fei.gui.controllers.AlertPaneController;
import mx.uv.fei.logic.daos.CourseDAO;
import mx.uv.fei.logic.daos.ProfessorDAO;
import mx.uv.fei.logic.daos.ScholarPeriodDAO;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.ScholarPeriod;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class GuiRegisterCourseController {
    @FXML
    private ComboBox<String> blockComboBox;
    @FXML
    private ComboBox<String> educativeExperienceComboBox;
    @FXML
    private Text errorMessajeText;
    @FXML
    private TextField nrcTextField;
    @FXML
    private ComboBox<Professor> professorComboBox;
    @FXML
    private ComboBox<ScholarPeriod> scholarPeriodComboBox;
    @FXML
    private ComboBox<String> sectionComboBox;
    
    @FXML
    void initialize() {
        ProfessorDAO professorDAO = new ProfessorDAO();
        try {
            this.professorComboBox.getItems().addAll(professorDAO.getProfessorsFromDatabase());
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
        
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
        this.professorComboBox.setValue(this.professorComboBox.getItems().get(0));

        ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
        try {
            this.scholarPeriodComboBox.getItems().addAll(scholarPeriodDAO.getScholarPeriodsFromDatabase());
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
        
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
        this.scholarPeriodComboBox.setValue(this.scholarPeriodComboBox.getItems().get(0));

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
    void registerButtonController(ActionEvent event) {
        if(!this.nrcTextField.getText().trim().isEmpty()) {

            if(allTextFieldsContainsCorrectValues()){
                this.errorMessajeText.setVisible(false);
                CourseDAO courseDAO = new CourseDAO();
                Course course = new Course();
                course.setEEName(this.educativeExperienceComboBox.getValue());
                course.setNrc(Integer.parseInt(this.nrcTextField.getText()));
                course.setSection(Integer.parseInt(this.sectionComboBox.getValue()));
                course.setBlock(Integer.parseInt(this.blockComboBox.getValue()));
                course.setStaffNumber(this.professorComboBox.getValue().getStaffNumber());
                course.setIdScholarPeriod(this.scholarPeriodComboBox.getValue().getIdScholarPeriod());
                
                try {
                    if(courseDAO.theCourseIsAlreadyRegisted(course)) {
                        AlertPaneController alertPaneController = new AlertPaneController();
                        alertPaneController.openWarningPane("El curso ya está registrado en el sistema");
                        return;
                    }
                } catch (DataRetrievalException e) {
                    e.printStackTrace();
                    AlertPaneController alertPaneController = new AlertPaneController();
                    alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
                }
                
                try {
                    courseDAO.addCourseToDatabase(course);
                    AlertPaneController alertPaneController = new AlertPaneController();
                    alertPaneController.openWarningPane("Curso registrado exitosamente");
                } catch (DataWritingException e) {
                    e.printStackTrace();
                    AlertPaneController alertPaneController = new AlertPaneController();
                    alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
                }
            } else {
                AlertPaneController alertPaneController = new AlertPaneController();
                alertPaneController.openWarningPane("Algunos campos contienen datos inválidos");
            }

        } else {
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openWarningPane("Faltan campos por llenar");
        }
    }

    private boolean allTextFieldsContainsCorrectValues(){
        Pattern nrcPattern = Pattern.compile("^[0-9]{5}$");
        Matcher nrcMatcher = nrcPattern.matcher(this.nrcTextField.getText());

        return nrcMatcher.find();
        
    }

}