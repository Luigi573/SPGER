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
import javafx.scene.control.DialogPane;
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
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class ModifyResearchPaneController{
    private ArrayList<ComboBox<Director>> directorComboBoxes;
    private int researchId;
    private User user;
    
    @FXML
    private ComboBox<Director> directorComboBox;
    @FXML
    private ComboBox<Director> codirector1ComboBox;
    @FXML
    private ComboBox<Director> codirector2ComboBox;
    @FXML
    private DialogPane dialogPane;
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
            ArrayList<Student> studentList = studentDAO.getStudents();
            
            directorComboBoxes = new ArrayList<>();
            directorComboBoxes.add(directorComboBox);
            directorComboBoxes.add(codirector1ComboBox);
            directorComboBoxes.add(codirector2ComboBox);
            
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
        confirmationMessage.setHeaderText("Descartar cambios");
        confirmationMessage.setContentText("¿Está seguro que cancelar la modificación del anteproeycto?");

        dialogPane = confirmationMessage.getDialogPane();
        String css = getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
        dialogPane.getStylesheets().add(css);
        dialogPane.getStyleClass().add("dialog");
        
        Optional<ButtonType> choice = confirmationMessage.showAndWait();
        if(choice.isPresent() && choice.get() == ButtonType.OK){
            returnToResearchManager(event);
        }
    }
    @FXML
    private void saveChanges(ActionEvent event){
        Alert confirmationMessage = new Alert(Alert.AlertType.CONFIRMATION);        
        confirmationMessage.setHeaderText("Guardar cambios");
        confirmationMessage.setContentText("¿Está seguro que desea guardar las modificaciones del anteproyecto?");

        dialogPane = confirmationMessage.getDialogPane();
        String css = getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
        dialogPane.getStylesheets().add(css);
        dialogPane.getStyleClass().add("dialog");
        
        Optional<ButtonType> choice = confirmationMessage.showAndWait();
        if(choice.isPresent() && choice.get() == ButtonType.OK){
            ResearchDAO researchDAO = new ResearchDAO();
            ResearchProject research = new ResearchProject();
            
            research.setId(researchId);
            research.setTitle(titleTextField.getText());
            research.setDueDate(Date.valueOf(dueDatePicker.getValue()));
            research.setStartDate(Date.valueOf(startDatePicker.getValue()));
            research.setDescription(descriptionTextArea.getText());
            
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
            
            research.setExpectedResult(expectedResultTextArea.getText());
            research.setSuggestedBibliography(suggestedBibliographyTextArea.getText());
            research.setRequirements(requirementsTextArea.getText());
            
            if(researchDAO.isValidDate(research)){
                if(!researchDAO.isBlank(research)){
                    if(researchDAO.areDirectorsDifferent(research)){
                        try{
                            if(researchDAO.modifyResearch(research) > 0){
                                new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.INFORMATION, "", "Cambios guardados exitosamente");

                                returnToResearchManager(event);
                            }
                        }catch(DataInsertionException exception){
                            new AlertPopUpGenerator().showConnectionErrorMessage();
                        }
                    }else{
                        new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.WARNING, "No se puede modificar el anteproyecto", "Los directores tienen que ser diferentes");
                    }
                }else{
                    new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.WARNING, "No se puede modificar el anteproyecto", "Favor de llenar todos los campos obligatorios");
                }
            }else{
                new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.WARNING, "No se puede modificar el anteproyecto", "Favor de introducir una fecha válida");
            }
        }
    }

    public void setResearch(ResearchProject research){
        researchId = research.getId();
        
        titleTextField.setText(research.getTitle());
        
        if(research.getKgal().getDescription() != null){
            KGALComboBox.getSelectionModel().select(research.getKgal());
        }
        
        int directorComboBoxesIndex = 0;
        for(Director director : research.getDirectors()){
            directorComboBoxes.get(directorComboBoxesIndex).getSelectionModel().select(director);
            directorComboBoxesIndex++;
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
    
    public void setUser(User user){
        this.user = user;
    }
    
    private void returnToResearchManager(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/research/ResearchManager.fxml"));
            Parent parent = loader.load();
            
            if(user != null){
                ResearchManagerController controller = (ResearchManagerController)loader.getController();
                controller.setUser(user);
                controller.loadHeader();
                controller.loadResearches(0);
            }
            
            Scene scene = new Scene(parent);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("SPGER");
            stage.setScene(scene);
            
            stage.show();
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
}