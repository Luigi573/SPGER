package mx.uv.fei.gui.controllers.courses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.daos.CourseDAO;
import mx.uv.fei.logic.daos.ProfessorDAO;
import mx.uv.fei.logic.daos.ScholarPeriodDAO;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.ScholarPeriod;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DuplicatedPrimaryKeyException;

public class GuiRegisterCourseController {
    private GuiCoursesController guiCoursesController;

    @FXML
    private ComboBox<String> blockComboBox;
    @FXML
    private ComboBox<String> educativeExperienceComboBox;
    @FXML
    private TextField nrcTextField;
    @FXML
    private ComboBox<Professor> professorComboBox;
    @FXML
    private Button registerButton;
    @FXML
    private ComboBox<ScholarPeriod> scholarPeriodComboBox;
    @FXML
    private ComboBox<String> sectionComboBox;
    
    @FXML
    private void initialize() {
        try{
            ProfessorDAO professorDAO = new ProfessorDAO();
            professorComboBox.getItems().addAll(professorDAO.getProfessors());
            professorComboBox.setConverter(new StringConverter<Professor>() {

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
            scholarPeriodComboBox.getItems().addAll(scholarPeriodDAO.getScholarPeriods());
            scholarPeriodComboBox.setConverter(new StringConverter<ScholarPeriod>() {

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

            educativeExperienceComboBox.getItems().add("Proyecto Guiado");
            educativeExperienceComboBox.getItems().add("Experiencia Recepcional");
            sectionComboBox.getItems().add("1");
            sectionComboBox.getItems().add("2");
            sectionComboBox.getItems().add("3");
            blockComboBox.getItems().add("7");
            blockComboBox.getItems().add("8");
            blockComboBox.getItems().add("9");
            blockComboBox.getItems().add("10");
        }catch(DataRetrievalException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }

    @FXML
    private void registerButtonController(ActionEvent event) {
        try{
            if(!nrcTextField.getText().isEmpty() &&
               blockComboBox.getValue() != null &&
               educativeExperienceComboBox.getValue() != null &&
               sectionComboBox.getValue() != null &&
               professorComboBox.getValue() != null &&
               scholarPeriodComboBox.getValue() != null){
                if(allTextFieldsContainsCorrectValues()){
                    CourseDAO courseDAO = new CourseDAO();
                    Course course = new Course();
                    course.setName(educativeExperienceComboBox.getValue());
                    course.setNrc(Integer.parseInt(nrcTextField.getText()));
                    course.setSection(Integer.parseInt(sectionComboBox.getValue()));
                    course.setBlock(Integer.parseInt(blockComboBox.getValue()));
                    course.setProfessor(professorComboBox.getValue());
                    course.setScholarPeriod(scholarPeriodComboBox.getValue());
                    courseDAO.addCourse(course);
                    new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Éxito", "Curso registrado exitosamente");

                    guiCoursesController.loadCourseButtons();
                    Stage stage = (Stage) registerButton.getScene().getWindow();
                    stage.close();
                }else{
                    new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "Algunos campos contienen datos inválidos");
                }
            }else{
                new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "Faltan campos por llenar");
            }
        }catch(DataInsertionException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DuplicatedPrimaryKeyException exception){
            new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "El NRC ya está usado");
        }
    }

    public GuiCoursesController getGuiCoursesController() {
        return guiCoursesController;
    }
    public void setGuiCoursesController(GuiCoursesController guiCoursesController) {
        this.guiCoursesController = guiCoursesController;
    }

    private boolean allTextFieldsContainsCorrectValues(){
        Pattern nrcPattern = Pattern.compile("^[0-9]{5}$");
        Matcher nrcMatcher = nrcPattern.matcher(nrcTextField.getText());

        return nrcMatcher.find();
    }
}