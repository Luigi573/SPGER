package mx.uv.fei.gui.controllers.courses;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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

public class ModifyCourseInformationController{
    private CourseInformationController courseInformationController;
    private GuiCoursesController guiCoursesController;
    private Course course;

    @FXML
    private ComboBox<String> blockComboBox;
    @FXML
    private DialogPane dialogPane;
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

    @FXML
    private void initialize(){
        ProfessorDAO professorDAO = new ProfessorDAO();

        try{
            professorComboBox.getItems().addAll(professorDAO.getProfessors());
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }

        professorComboBox.setConverter(new StringConverter<Professor>(){

            @Override
            public Professor fromString(String arg0){
                return null;
            }

            @Override
            public String toString(Professor arg0){
                if(arg0 != null){
                    return arg0.getName();
                }
                
                return null;  
            }
            
        });

        ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
        try{
            scholarPeriodComboBox.getItems().addAll(scholarPeriodDAO.getScholarPeriods());
        }catch(DataRetrievalException e){
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
    private void exitButtonController(ActionEvent event){
        returnToGuiCourses(event);
    }
    @FXML
    private void modifyButtonController(ActionEvent event){

        try{
            if(blockComboBox.getValue() != null &&
               educativeExperienceComboBox.getValue() != null &&
               !nrcTextField.getText().trim().isEmpty() &&
               professorComboBox.getValue() != null &&
               scholarPeriodComboBox.getValue() != null &&
               sectionComboBox.getValue() != null){
                if(allTextFieldsContainsCorrectValues()){
                    CourseDAO courseDAO = new CourseDAO();
                    course.setName((String)educativeExperienceComboBox.getValue());
                    course.setNrc(Integer.parseInt(nrcTextField.getText()));
                    course.setSection(Integer.parseInt(sectionComboBox.getValue()));
                    course.setBlock(Integer.parseInt(blockComboBox.getValue()));
                    course.setProfessor(professorComboBox.getValue());
                    course.setScholarPeriod(scholarPeriodComboBox.getValue());
                    courseDAO.modifyCourseData(course);
                    new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Éxito", "Curso modificado exitosamente");
                    returnToGuiCourses(event);
                }else{
                    new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "Algunos campos contienen datos inválidos");
                }
            }else{
                new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "Faltan campos por llenar");
            }
        }catch(DataInsertionException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DuplicatedPrimaryKeyException exception){
            new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "El NRC ya está usado");
        }
    }


    public Course getCourse() {
        return course;
    }
    public void setCourse(Course course) {
        this.course = course;
    }
    public GuiCoursesController getGuiCoursesController(){
        return guiCoursesController;
    }
    public void setGuiCoursesController(GuiCoursesController guiCoursesController){
        this.guiCoursesController = guiCoursesController;
    }
    public CourseInformationController getCourseInformationController(){
        return courseInformationController;
    }
    public void setCourseInformationController(CourseInformationController courseInformationController){
        this.courseInformationController = courseInformationController;
    }
    public String getBlock(){
        return blockComboBox.getValue();
    }
    public void setBlock(String block){
        blockComboBox.setValue(block);
    }
    public String getEducativeExperience(){
        return educativeExperienceComboBox.getValue();
    }
    public void setEducativeExperience(String educativeExperince){
        educativeExperienceComboBox.setValue(educativeExperince);
    }
    public String getNrc() {
        return nrcTextField.getText();
    }
    public void setNrc(String nrc){
        nrcTextField.setText(nrc);
    }
    public Professor getProfessor(){
        return professorComboBox.getValue();
    }
    public void setProfessor(Professor professor){
        professorComboBox.setValue(professor);
    }
    public ScholarPeriod getScholarPeriod(){
        return scholarPeriodComboBox.getValue();
    }
    public void setScholarPeriod(ScholarPeriod scholarPeriod){
        scholarPeriodComboBox.setValue(scholarPeriod);
    }
    public String getSection(){
        return sectionComboBox.getValue();
    }
    public void setSection(String section){
        sectionComboBox.setValue(section);
    }
    public void setProfessorToDefaultSelect(Professor professor){
        professorComboBox.setValue(professor);
    }
    public void setScholarPeriodToDefaultSelect(ScholarPeriod scholarPeriod){
        scholarPeriodComboBox.setValue(scholarPeriod);
    }

    private boolean allTextFieldsContainsCorrectValues(){
        Pattern nrcPattern = Pattern.compile("^[0-9]{5}$");
        Matcher nrcMatcher = nrcPattern.matcher(nrcTextField.getText());
        if(nrcMatcher.find()){
            return true;
        }
        return false;
    }
    private void returnToGuiCourses(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/courses/GuiCourses.fxml"));
            Parent parent = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            String css = getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
        }catch(IllegalStateException | IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
}