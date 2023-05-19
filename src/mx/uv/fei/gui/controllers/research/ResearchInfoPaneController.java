package mx.uv.fei.gui.controllers.research;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import mx.uv.fei.logic.daos.ResearchDAO;
import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class ResearchInfoPaneController {
    private ArrayList<Label> directorLabels;
    private ResearchProject research;
    private ScrollPane container;
    @FXML
    private Label KGALLabel;
    @FXML
    private Text descriptionText;
    @FXML
    private Label director1Label;
    @FXML
    private Label director2Label;
    @FXML
    private Label director3Label;
    @FXML
    private Label dueDateLabel;
    @FXML
    private Text expectedResultText;
    @FXML
    private Text requirementsText;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label studentLabel;
    @FXML
    private Text suggestedBibliographyText;
    @FXML
    private Label titleLabel;
    
    @FXML
    private void initialize(){
       directorLabels = new ArrayList<>();
       directorLabels.add(director1Label);
       directorLabels.add(director2Label);
       directorLabels.add(director3Label);
    }
    
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
        ResearchDAO researchDAO = new ResearchDAO();
        try {
            researchDAO.validateResearch(research);
        } catch (DataWritingException e) {
            e.printStackTrace();
        }
    }
    
    public void setContainer(ScrollPane container){
        this.container = container;
    }
    public void setResearch(ResearchProject research){
        this.research = research;
        titleLabel.setText(research.getTitle());
        
        if(research.getKgal().getDescription() != null){
            KGALLabel.setText(research.getKgal().getDescription());
        }
        
        for(int i = 0; i < 3; i++){
            if(research.getDirector(i).getName() != null){
                directorLabels.get(i).setText(research.getDirector(i).getName());
            }
        }
        
        if(research.getStudent().getName() != null){
            studentLabel.setText(research.getStudent().getName());
        }
        
        startDateLabel.setText(research.getStartDate().toString());
        dueDateLabel.setText(research.getDueDate().toString());
        descriptionText.setText(research.getDescription());
        requirementsText.setText(research.getRequirements());
        suggestedBibliographyText.setText(research.getSuggestedBibliography());
        expectedResultText.setText(research.getExpectedResult());
    }
}