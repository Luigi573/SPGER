package mx.uv.fei.gui.controllers.research;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import mx.uv.fei.logic.domain.ResearchProject;

public class ResearchInfoPaneController {
    private ResearchProject research;
    private ScrollPane container;
    @FXML
    private Label KGALLabel;
    @FXML
    private Label codirectorLabel;
    @FXML
    private Text descriptionText;
    @FXML
    private Label directorLabel;
    @FXML
    private Label dueDateLabel;
    @FXML
    private Text expectedResultText;
    @FXML
    private Text requirementsText;
    @FXML
    private Button saveButton;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label studentLabel;
    @FXML
    private Text suggestedBibliographyText;
    @FXML
    private Label titleLabel;
    @FXML
    private Button validateButton;
    
    @FXML
    private void modifyResearch(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/research/ModifyResearchPane.fxml"));
        
        try{
            Pane researchInfoPane = loader.load();
            ModifyResearchPaneController controller = (ModifyResearchPaneController)loader.getController();
            controller.setResearch(research);
            
            container.setContent(researchInfoPane);
        }catch(IOException exception){
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setContentText("Error al cargar, faltan archivos");
            errorMessage.showAndWait();
        }
    }
    @FXML
    private void validateResearch(ActionEvent event) {
        
    }
    
    public void setContainer(ScrollPane container){
        this.container = container;
    }
    public void setResearch(ResearchProject research){
        this.research = research;
        titleLabel.setText(research.getTitle());
        
        if(research.getKgal().getDescription() != null){
            KGALLabel.setText("LGAC: " + research.getKgal().getDescription());
        }
        
        if(research.getDirector().getName() != null){
            directorLabel.setText("Director(es): " + research.getDirector().getName());
        }
        
        codirectorLabel.setText(research.getCodirector());
        
        if(research.getStudent().getName() != null){
            studentLabel.setText("Estudiante asignado: " + research.getStudent().getName());
        }
        
        startDateLabel.setText("Fecha de inicio: " + research.getStartDate().toString());
        dueDateLabel.setText("Fecha fin: " + research.getDueDate().toString());
        descriptionText.setText(research.getDescription());
        requirementsText.setText(research.getRequirements());
        suggestedBibliographyText.setText(research.getSuggestedBibliography());
        expectedResultText.setText(research.getExpectedResult());
    }
}