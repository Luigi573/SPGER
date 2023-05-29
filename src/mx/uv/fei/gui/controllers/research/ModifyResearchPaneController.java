package mx.uv.fei.gui.controllers.research;

import java.io.IOException;
import java.util.Optional;
import java.sql.Date;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.daos.DirectorDAO;
import mx.uv.fei.logic.daos.KGALDAO;
import mx.uv.fei.logic.daos.ResearchDAO;
import mx.uv.fei.logic.daos.StudentDAO;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.domain.KGAL;
import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class ModifyResearchPaneController{
    private ArrayList<ComboBox> directorComboBoxes;
    private int researchId;
    
    @FXML
    private ComboBox<Director> director1ComboBox;
    @FXML
    private ComboBox<Director> director2ComboBox;
    @FXML
    private ComboBox<Director> director3ComboBox;
    @FXML
    private ComboBox<KGAL> KGALComboBox;
    @FXML
    private ComboBox<Student> studentComboBox;
    @FXML
    private DatePicker dueDatePicker;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TextArea expectedResultTextArea;
    @FXML
    private TextArea requirementsTextArea;
    @FXML
    private TextArea suggestedBibliographyTextArea;
    @FXML
    private TextField titleTextField;
    
    @FXML
    private void initialize(){
        DirectorDAO directorDAO = new DirectorDAO();
        KGALDAO kgalDAO = new KGALDAO();
        StudentDAO studentDAO = new StudentDAO();
        
        try{
            ArrayList<Director> directorList = directorDAO.getDirectorList();
            ArrayList<KGAL> KGALList = kgalDAO.getKGALList();
            ArrayList<Student> studentList = studentDAO.getStudentsFromDatabase();
            
            directorComboBoxes = new ArrayList<>();
            directorComboBoxes.add(director1ComboBox);
            directorComboBoxes.add(director2ComboBox);
            directorComboBoxes.add(director3ComboBox);
            
            for(ComboBox<Director> directorComboBox : directorComboBoxes){
                directorComboBox.setItems(FXCollections.observableArrayList(directorList));
            }
            
            KGALComboBox.setItems(FXCollections.observableArrayList(KGALList));
            studentComboBox.setItems(FXCollections.observableArrayList(studentList));
        }catch(DataRetrievalException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    @FXML
    private void cancelChanges(ActionEvent event){
        Alert confirmationMessage = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationMessage.setHeaderText("Guardar cambios");
        confirmationMessage.setContentText("¿Está seguro que desea modificar la actividad y volver al cronograma?");
        
        Optional<ButtonType> choice = confirmationMessage.showAndWait();
        if(choice.isPresent() && choice.get() == ButtonType.OK){
            returnToResearchManager(event);
        }
    }
    @FXML
    private void saveChanges(ActionEvent event){
        Alert confirmationMessage = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationMessage.setHeaderText("Descartar cambios");
        confirmationMessage.setContentText("¿Está seguro que cancelar la modificación del anteproeycto?");
        
        Optional<ButtonType> choice = confirmationMessage.showAndWait();
        if(choice.isPresent() && choice.get() == ButtonType.OK){
            ResearchDAO researchDAO = new ResearchDAO();
            ResearchProject research = new ResearchProject();
            
            research.setId(researchId);
            research.setTitle(titleTextField.getText().trim());
            research.setDueDate(Date.valueOf(dueDatePicker.getValue()));
            research.setStartDate(Date.valueOf(startDatePicker.getValue()));
            research.setDescription(descriptionTextArea.getText().trim());
            
            for(ComboBox<Director> directorComboBox : directorComboBoxes){
                if(directorComboBox.getValue() != null){
                    research.addDirector(directorComboBox.getValue());
                }
            }
            
            if(KGALComboBox.getValue() != null){
                research.setKgal(KGALComboBox.getValue());
            }
            if(studentComboBox.getValue() != null){
                research.setStudent(studentComboBox.getValue());
            }
            
            research.setExpectedResult(expectedResultTextArea.getText().trim());
            research.setSuggestedBibliography(suggestedBibliographyTextArea.getText().trim());
            research.setRequirements(requirementsTextArea.getText().trim());
            
            if(researchDAO.isValidDate(research)){
                if(!researchDAO.isBlank(research)){
                    try{
                        if(researchDAO.modifyResearch(research) > 0){
                            Alert successMessage = new Alert(Alert.AlertType.INFORMATION);
                            successMessage.setHeaderText("Cambios guardados exitosamente");
                            successMessage.showAndWait();
                            
                            returnToResearchManager(event);
                        }
                    }catch(DataInsertionException exception){
                        new AlertPopUpGenerator().showConnectionErrorMessage();
                    }
                }else{
                    Alert warningMessage = new Alert(Alert.AlertType.WARNING);
                    warningMessage.setHeaderText("No se puede modificar el anteproyecto");
                    warningMessage.setContentText("Favor de llenar todos los campos");
                    warningMessage.showAndWait();
                }
            }else{
                Alert warningMessage = new Alert(Alert.AlertType.WARNING);
                warningMessage.setHeaderText("No se puede modificar el anteproyecto");
                warningMessage.setContentText("Favor de introducir una fecha válida");
                warningMessage.showAndWait();
            }
            
        }
    }

    public void setResearch(ResearchProject research){
        researchId = research.getId();
        
        titleTextField.setText(research.getTitle());
        
        if(research.getKgal().getDescription() != null){
            KGALComboBox.getSelectionModel().select(research.getKgal());
        }
        
        for(int i = 0; i < 3; i++){
            if(research.getDirector(i).getName() != null){
                directorComboBoxes.get(i).getSelectionModel().select(research.getDirector(i));
            }
        }
        
        if(research.getStudent().getName() != null){
            studentComboBox.getSelectionModel().select(research.getStudent());
        }
        
        startDatePicker.setValue(research.getStartDate().toLocalDate());
        dueDatePicker.setValue(research.getDueDate().toLocalDate());
        descriptionTextArea.setText(research.getDescription());
        requirementsTextArea.setText(research.getRequirements());
        suggestedBibliographyTextArea.setText(research.getSuggestedBibliography());
        expectedResultTextArea.setText(research.getExpectedResult());
    }
    
    private void returnToResearchManager(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/research/ResearchManager.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
        }catch(IllegalStateException | IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
}