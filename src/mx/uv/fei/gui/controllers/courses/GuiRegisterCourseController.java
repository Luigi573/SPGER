package mx.uv.fei.gui.controllers.courses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import mx.uv.fei.gui.AlertPopUpGenerator;
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
    private void initialize(){
        ProfessorDAO professorDAO = new ProfessorDAO();
        try{
            professorComboBox.getItems().addAll(professorDAO.getProfessorsFromDatabase());
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
        
        professorComboBox.setConverter(new StringConverter<Professor>(){

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
        professorComboBox.setValue(professorComboBox.getItems().get(0));

        ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
        try{
            scholarPeriodComboBox.getItems().addAll(scholarPeriodDAO.getScholarPeriodsFromDatabase());
        }catch(DataRetrievalException e) {
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
        
        scholarPeriodComboBox.setConverter(new StringConverter<ScholarPeriod>(){
            
            @Override
            public ScholarPeriod fromString(String arg0){
                return null;
            }
            
            @Override
            public String toString(ScholarPeriod arg0){
                if(arg0 != null){
                    return arg0.getStartDate() + " " + arg0.getEndDate();
                }
                
                return null;
            }
            
        });

        educativeExperienceComboBox.getItems().add("Proyecto Guiado");
        educativeExperienceComboBox.getItems().add("Experiencia Recepcional");
        sectionComboBox.getItems().add("1");
        sectionComboBox.getItems().add("2");
        blockComboBox.getItems().add("7");
        blockComboBox.getItems().add("8");
    }
    @FXML
    private void registerButtonController(ActionEvent event){
        if(blockComboBox.getValue() != null &&
           educativeExperienceComboBox.getValue() != null &&
           !nrcTextField.getText().trim().isEmpty() &&
           professorComboBox.getValue() != null &&
           scholarPeriodComboBox.getValue() != null &&
           sectionComboBox.getValue() != null){

            if(allTextFieldsContainsCorrectValues()){
                errorMessajeText.setVisible(false);
                CourseDAO courseDAO = new CourseDAO();
                Course course = new Course();
                course.setEEName(educativeExperienceComboBox.getValue());
                course.setNrc(Integer.parseInt(nrcTextField.getText()));
                course.setSection(Integer.parseInt(sectionComboBox.getValue()));
                course.setBlock(Integer.parseInt(blockComboBox.getValue()));
                course.setStaffNumber(professorComboBox.getValue().getStaffNumber());
                course.setIdScholarPeriod(scholarPeriodComboBox.getValue().getIdScholarPeriod());
                
                try{
                    if(courseDAO.theCourseIsAlreadyRegisted(course)){
                        new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "El curso ya está registrado en el sistema");
                        return;
                    }
                }catch(DataRetrievalException e){
                    new AlertPopUpGenerator().showConnectionErrorMessage();
                }
                
                try{
                    courseDAO.addCourseToDatabase(course);
                    new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Éxito", "Curso registrado exitosamente");
                }catch(DataWritingException e) {
                    new AlertPopUpGenerator().showConnectionErrorMessage();
                }
            }else{
                new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "Algunos campos contienen datos inválidos");
            }
        }else{
            new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "Faltan campos por llenar");
        }
    }

    private boolean allTextFieldsContainsCorrectValues(){
        Pattern nrcPattern = Pattern.compile("^[0-9]{5}$");
        Matcher nrcMatcher = nrcPattern.matcher(nrcTextField.getText());
        return nrcMatcher.find();
    }
}