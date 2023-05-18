package mx.uv.fei.gui.controllers.research;

import java.sql.Date;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.uv.fei.logic.daos.DirectorDAO;
import mx.uv.fei.logic.daos.KGALDAO;
import mx.uv.fei.logic.daos.ResearchDAO;
import mx.uv.fei.logic.daos.StudentDAO;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.domain.KGAL;
import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class AddResearchController{
    private ArrayList<ComboBox> directorComboBoxes;
    
    @FXML
    private TextField titleTextField;
    @FXML
    private ComboBox<KGAL> KGALComboBox;
    @FXML
    private ComboBox<Director> director1ComboBox;
    @FXML
    private ComboBox<Director> director2ComboBox;
    @FXML
    private ComboBox<Director> director3ComboBox;
    @FXML
    private ComboBox<Student> studentComboBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker dueDatePicker;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TextArea requirementsTextArea;
    @FXML
    private TextArea suggestedBibliographyTextArea;
    @FXML
    private TextArea expectedResultTextArea;
    
    @FXML
    private void initialize(){
        DirectorDAO directorDAO = new DirectorDAO();
        KGALDAO kgalDAO = new KGALDAO();
        StudentDAO studentDAO = new StudentDAO();
        
        try{
            ArrayList<Director> directorList = directorDAO.getDirectorList();
            ArrayList<KGAL> KGALList = kgalDAO.getKGALList();
            ArrayList<Student> studentList = studentDAO.getStudentsFromDatabase();
            
            directorComboBoxes = new ArrayList();
            directorComboBoxes.add(director1ComboBox);
            directorComboBoxes.add(director2ComboBox);
            directorComboBoxes.add(director3ComboBox);
            
            for(ComboBox<Director> directorComboBox : directorComboBoxes){
                directorComboBox.setItems(FXCollections.observableArrayList(directorList));
            }
            
            KGALComboBox.setItems(FXCollections.observableArrayList(KGALList));
            studentComboBox.setItems(FXCollections.observableArrayList(studentList));
        }catch(DataRetrievalException exception){
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setContentText(exception.getMessage());
            errorMessage.showAndWait();
        }
    }
    
    @FXML
    private void addResearch(ActionEvent event) {
        //In case the date is NULL, setting other attributes is pointless
        if(startDatePicker.getValue() != null && dueDatePicker.getValue() != null){
            ResearchProject research = new ResearchProject();
            
            research.setStartDate(Date.valueOf(startDatePicker.getValue()));
            research.setDueDate(Date.valueOf(dueDatePicker.getValue()));
            
            for(ComboBox<Director> directorComboBox : directorComboBoxes){
                if(directorComboBox.getValue() != null){
                    research.addDirector(directorComboBox.getValue());
                }
            }
            
            if(studentComboBox.getValue() != null){
                research.setStudent(studentComboBox.getValue());
            }
            
            if(KGALComboBox.getValue() != null){
                research.setKgal(KGALComboBox.getValue());
            }
            
            research.setTitle(titleTextField.getText().trim());
            research.setDescription(descriptionTextArea.getText().trim());
            research.setRequirements(requirementsTextArea.getText().trim());
            research.setSuggestedBibliography(suggestedBibliographyTextArea.getText().trim());
            research.setExpectedResult(expectedResultTextArea.getText().trim());
            
            ResearchDAO researchDAO = new ResearchDAO();
            
            if(researchDAO.isValidDate(research)){
                if(!researchDAO.isBlank(research)){
                    try{
                        if(researchDAO.addResearch(research) > 0){
                            Alert successMessage = new Alert(Alert.AlertType.INFORMATION);
                            successMessage.setHeaderText("Anteproyecto creado exitosamente");
                            successMessage.showAndWait();
                            
                            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                            stage.close();
                        }
                    }catch(DataWritingException exception){
                        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                        errorMessage.setContentText(exception.getMessage());
                        errorMessage.showAndWait();
                    }
                }else{
                    Alert warningMessage = new Alert(Alert.AlertType.WARNING);
                    warningMessage.setHeaderText("No se puede crear el anteproyecto");
                    warningMessage.setContentText("Favor de llenar todos los campos");
                    warningMessage.showAndWait();
                }
            }else{
                Alert warningMessage = new Alert(Alert.AlertType.WARNING);
                warningMessage.setHeaderText("No se puede crear el anteproyecto");
                warningMessage.setContentText("Favor de introducir una fecha válida");
                warningMessage.showAndWait();
            }
        }else{
            Alert warningMessage = new Alert(Alert.AlertType.WARNING);
            warningMessage.setHeaderText("Datos inválidos");
            warningMessage.setContentText("Favor de seleccionar una fecha válida");
            warningMessage.showAndWait();
        }
    }
}