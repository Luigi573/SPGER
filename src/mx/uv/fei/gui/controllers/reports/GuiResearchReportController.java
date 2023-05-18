package mx.uv.fei.gui.controllers.reports;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.WindowConstants;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import mx.uv.fei.logic.daos.ResearchesReportDAO;
import mx.uv.fei.logic.domain.Research;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class GuiResearchReportController {
    @FXML
    private Text errorMessageText;
    @FXML
    private Text findByTitleText;
    @FXML
    private TextField findByTitleTextField;
    @FXML
    private Button generateReportButton;
    @FXML
    private Pane guiCrearAnteproyectoBody;
    @FXML
    private VBox selectedResearchesVBox;
    @FXML
    private Text recentlySelectedResearchesText;
    @FXML
    private VBox researchesVBox;
    @FXML
    private Button searchResearchesButton;
    @FXML
    private Text showNotValidatedText;
    @FXML
    private ToggleButton showNotValidatedToggleButton;
    @FXML
    private Text showResearchesText;
    @FXML
    private ToggleButton showResearchesToggleButton;
    @FXML
    private Text showValidatedText;
    @FXML
    private ToggleButton showValidatedToggleButton;
    @FXML
    private ImageView spgerLogo;
    @FXML
    private Text spgerText;
    @FXML
    private ImageView userLogo;
    @FXML
    private Text userNameText;
    @FXML
    private Text windowText;

    @FXML
    void generateReportButtonController(ActionEvent event) {
        if(this.selectedResearchesVBox.getChildren().isEmpty()){
            this.errorMessageText.setText("No se puede generar un reporte vacío");
            this.errorMessageText.setVisible(true);
            return;
        }

        ArrayList<String> selectedResearches = new ArrayList<>();
        for(Node hbox : ((VBox)this.selectedResearchesVBox).getChildren()) {
            Node label = ((HBox)hbox).getChildren().get(1); 
            selectedResearches.add(((Label)label).getText());
        }

        ResearchesReportDAO researchesReportDAO = new ResearchesReportDAO();
        ArrayList<Research> researches = researchesReportDAO.getSelectedResearchesFromDatabase(selectedResearches);

        try {
            Path path = Paths.get("src/dependencies/resources/jasperreports/ResearchReport.jasper");
            InputStream inputStream = Files.newInputStream(path.toAbsolutePath());
            JasperReport report = (JasperReport) JRLoader.loadObject(inputStream);
            
            JRBeanArrayDataSource researchesReportDataSource = new JRBeanArrayDataSource(researches.toArray());
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("researchesReportDataSource", researchesReportDataSource);

            Path outputStreamPath = Paths.get("src/mx/uv/fei/ResearchReport.pdf");
            OutputStream reportFinale = Files.newOutputStream(outputStreamPath.toAbsolutePath());

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters);
            JasperViewer view = new JasperViewer(jasperPrint, false);
            view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            view.setVisible(true);
            JasperExportManager.exportReportToPdfStream(jasperPrint, reportFinale);
            
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (JRException jre) {
            jre.printStackTrace();
        }
    }

    @FXML
    void searchResearchesButtonController(ActionEvent event) {
        this.researchesVBox.getChildren().removeAll(this.researchesVBox.getChildren());
        ResearchesReportDAO researchesReportDAO = new ResearchesReportDAO();
        ArrayList<Research> researches = new ArrayList<>();
        if(!this.showNotValidatedToggleButton.isSelected() &&
           !this.showValidatedToggleButton.isSelected()){
            researches = researchesReportDAO.getResearchesFromDatabase(this.findByTitleTextField.getText(), "");
        }

        if(!this.showNotValidatedToggleButton.isSelected() &&
           this.showValidatedToggleButton.isSelected()){
            researches = researchesReportDAO.getValidatedResearchesFromDatabase(this.findByTitleTextField.getText());
        }

        if(this.showNotValidatedToggleButton.isSelected() &&
           !this.showValidatedToggleButton.isSelected()){
            researches = researchesReportDAO.getNotValidatedResearchesFromDatabase(this.findByTitleTextField.getText());
        }

        if(this.showNotValidatedToggleButton.isSelected() &&
           this.showValidatedToggleButton.isSelected()){
            researches = researchesReportDAO.getValidatedAndNotValidatedResearchesFromDatabase(this.findByTitleTextField.getText());
        }
        
        try {
            for(Research research : researches){
                FXMLLoader researchItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxml/reports/ResearchItem.fxml")
                );
                HBox researchItemHBox = researchItemControllerLoader.load();
                ResearchItemController researchItemController = researchItemControllerLoader.getController();
                researchItemController.setResearchNameLabel(research.getTitle());
                researchItemController.setGuiResearchReportController(this);
                this.researchesVBox.getChildren().add(researchItemHBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
            
    }

    //This method only should be executed by the ResearchItemController Class, do not execute this method in another class.
    void setElementToSelectedResearchesVBox(String selectedResearchTitle) {
        try {
            FXMLLoader selectedResearchItemControllerLoader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxml/reports/SelectedResearchItem.fxml")
            );
            HBox selectedResearchItemHBox;
            selectedResearchItemHBox = selectedResearchItemControllerLoader.load();
            SelectedResearchItemController selectedResearchItemController = selectedResearchItemControllerLoader.getController();
            selectedResearchItemController.setSelectedResearchNameLabel(selectedResearchTitle);
            this.selectedResearchesVBox.getChildren().add(selectedResearchItemHBox);
            selectedResearchItemController.setGuiResearchReportController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This method only should be executed by the SelectedResearchItemController Class, do not execute this method in another class.
    public void setElementToResearchesVBox(String researchTitle) {
        try {
            FXMLLoader researchItemControllerLoader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxml/reports/ResearchItem.fxml")
            );
            HBox researchItemHBox;
            researchItemHBox = researchItemControllerLoader.load();
            ResearchItemController researchItemController = researchItemControllerLoader.getController();
            researchItemController.setResearchNameLabel(researchTitle);
            this.researchesVBox.getChildren().add(researchItemHBox);
            researchItemController.setGuiResearchReportController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This method only should be executed by the ResearchItemController Class, do not execute this method in another class.
    void removeElementFromResearchesVBox(String researchItemControllerTitle){
        int researchesVBoxIndexForRemove = 0;
        for(Node hbox : ((VBox)this.researchesVBox).getChildren()) {
            Node label = ((HBox)hbox).getChildren().get(1); 
            if(((Label)label).getText() == researchItemControllerTitle) {
                this.researchesVBox.getChildren().remove(researchesVBoxIndexForRemove);
                break;
            }    
            researchesVBoxIndexForRemove++;
        }
    }

    //This method only should be executed by the SelectedResearchItemController Class, do not execute this method in another class.
    void removeElementFromSelectedResearchesVBox(String selectedResearchItemControllerTitle){
        int selectedResearchesVBoxIndexForRemove = 0;
        for(Node hbox : ((VBox)this.selectedResearchesVBox).getChildren()) {
            Node label = ((HBox)hbox).getChildren().get(1); 
            if(((Label)label).getText() == selectedResearchItemControllerTitle) {
                this.selectedResearchesVBox.getChildren().remove(selectedResearchesVBoxIndexForRemove);
                break;
            }    
            selectedResearchesVBoxIndexForRemove++;
        }
    }

    //This method only should be executed by the ResearchItemController Class, do not execute this method in another class.
    VBox getResearchesVBox() {
        return this.researchesVBox;
    }

    //This method only should be executed by the SelectedResearchItemController Class, do not execute this method in another class.
    VBox getSelectedResearchesVBox() {
        return this.selectedResearchesVBox;
    }

    //This method only should be executed by the SelectedResearchItemController and ResearchItemController Classes,
    //do not execute this method in another class.
    void showAndSetTextToErrorMessageText(String text) {
        this.errorMessageText.setVisible(true);
        this.errorMessageText.setText(text);
    }

    //This method only should be executed by the SelectedResearchItemController and ResearchItemController Classes,
    //do not execute this method in another class.
    void hideErrorMessageText(){
        this.errorMessageText.setVisible(false);
    }

}
