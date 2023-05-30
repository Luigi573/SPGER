package mx.uv.fei.gui.controllers.reports;

import java.io.File;
import java.io.FileOutputStream;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.gui.controllers.HeaderPaneController;
import mx.uv.fei.logic.daos.ResearchReportDAO;
import mx.uv.fei.logic.domain.Research;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class GuiResearchReportController {
    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    
    @FXML
    private Pane backgroundPane;
    @FXML
    private Text errorMessageText;
    @FXML
    private Text findByTitleText;
    @FXML
    private TextField findByTitleTextField;
    @FXML
    private Button generateReportButton;
    @FXML
    private Text recentlySelectedResearchesText;
    @FXML
    private VBox researchesVBox;
    @FXML
    private VBox selectedResearchesVBox;
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
    private void initialize(){
        loadHeader();

        researchesVBox.getChildren().clear();
        ResearchReportDAO researchesReportDAO = new ResearchReportDAO();
        ArrayList<Research> researches = new ArrayList<>();
        try{
            researches = researchesReportDAO.getResearches(findByTitleTextField.getText(), "");
        }catch(DataRetrievalException e) {
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
        
        try{
            for(Research research : researches){
                FXMLLoader researchItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxml/reports/ResearchItem.fxml")
                );
                HBox researchItemHBox = researchItemControllerLoader.load();
                ResearchItemController researchItemController = researchItemControllerLoader.getController();
                researchItemController.setResearchNameLabel(research.getTitle());
                researchItemController.setGuiResearchReportController(this);
                researchesVBox.getChildren().add(researchItemHBox);
            }
        }catch(IOException e) {
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    @FXML
    private void generateReportButtonController(ActionEvent event){
        if(selectedResearchesVBox.getChildren().isEmpty()){
            new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "No se puede generar un reporte vacío.");
            return;
        }

        directoryChooser.setTitle("Seleccionar rrrrrrrrrrrrrrrrrrrrrrrrrrrruta");
        File choosedDirectory = directoryChooser.showDialog(null);
        if(choosedDirectory == null){
            return;
        }

        ArrayList<String> selectedResearches = new ArrayList<>();
        for(Node hbox : ((VBox)selectedResearchesVBox).getChildren()){
            Node label = ((HBox)hbox).getChildren().get(1); 
            selectedResearches.add(((Label)label).getText());
        }

        try{
            ResearchReportDAO researchesReportDAO = new ResearchReportDAO();
            ArrayList<Research> researches = researchesReportDAO.getSelectedResearches(selectedResearches);

            Path path = Paths.get("src/dependencies/resources/jasperreports/ResearchReport.jasper");
            InputStream inputStream = Files.newInputStream(path.toAbsolutePath());
            JasperReport report = (JasperReport) JRLoader.loadObject(inputStream);
            
            JRBeanArrayDataSource researchesReportDataSource = new JRBeanArrayDataSource(researches.toArray());
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("researchesReportDataSource", researchesReportDataSource);

            OutputStream reportFinale = new FileOutputStream(choosedDirectory.getAbsolutePath() + "/Reporte.pdf");

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters);
            JasperViewer view = new JasperViewer(jasperPrint, false);
            view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            view.setVisible(true);
            JasperExportManager.exportReportToPdfStream(jasperPrint, reportFinale);
            
        }catch(IOException ioe){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }catch(JRException jre){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    @FXML
    private void searchResearchesButtonController(ActionEvent event){
        researchesVBox.getChildren().removeAll(researchesVBox.getChildren());
        ResearchReportDAO researchesReportDAO = new ResearchReportDAO();
        ArrayList<Research> researches = new ArrayList<>();
        try{
            if(!showNotValidatedToggleButton.isSelected() &&
               !showValidatedToggleButton.isSelected()){
                researches = researchesReportDAO.getResearches(findByTitleTextField.getText(), "");
            }
        
            if(!showNotValidatedToggleButton.isSelected() &&
               showValidatedToggleButton.isSelected()){
                researches = researchesReportDAO.getValidatedResearches(findByTitleTextField.getText());
            }
        
            if(showNotValidatedToggleButton.isSelected() &&
               !showValidatedToggleButton.isSelected()){
                researches = researchesReportDAO.getNotValidatedResearches(findByTitleTextField.getText());
            }
        
            if(showNotValidatedToggleButton.isSelected() &&
               showValidatedToggleButton.isSelected()){
                researches = researchesReportDAO.getValidatedAndNotValidatedResearches(findByTitleTextField.getText());
            }

        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
        
        try{
            for(Research research : researches){
                FXMLLoader researchItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxml/reports/ResearchItem.fxml")
                );
                HBox researchItemHBox = researchItemControllerLoader.load();
                ResearchItemController researchItemController = researchItemControllerLoader.getController();
                researchItemController.setResearchNameLabel(research.getTitle());
                researchItemController.setGuiResearchReportController(this);
                researchesVBox.getChildren().add(researchItemHBox);
            }
        }catch(IOException e) {
            new AlertPopUpGenerator().showMissingFilesMessage();
        }  
    }
    @FXML
    private void showValidatedToggleButtonController(ActionEvent event){
        if(!showValidatedToggleButton.isSelected()){
            showValidatedToggleButton.setText("No");
        }else{
            showValidatedToggleButton.setText("Si");
        }
    }
    @FXML
    private void showNotValidatedToggleButtonController(ActionEvent event){
        if(!showNotValidatedToggleButton.isSelected()){
            showNotValidatedToggleButton.setText("No");
        }else{
            showNotValidatedToggleButton.setText("Si");
        }
    }

    //This method only should be executed by the ResearchItemController Class, do not execute  method in another class.
    public void setElementToSelectedResearchesVBox(String selectedResearchTitle){
        try{
            FXMLLoader selectedResearchItemControllerLoader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxml/reports/SelectedResearchItem.fxml")
            );
            HBox selectedResearchItemHBox;
            selectedResearchItemHBox = selectedResearchItemControllerLoader.load();
            SelectedResearchItemController selectedResearchItemController = selectedResearchItemControllerLoader.getController();
            selectedResearchItemController.setSelectedResearchNameLabel(selectedResearchTitle);
            selectedResearchesVBox.getChildren().add(selectedResearchItemHBox);
            selectedResearchItemController.setGuiResearchReportController(this);
        }catch(IOException e) {
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    //This method only should be executed by the SelectedResearchItemController Class, do not execute  method in another class.
    public void setElementToResearchesVBox(String researchTitle){
        try{
            FXMLLoader researchItemControllerLoader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxml/reports/ResearchItem.fxml")
            );
            HBox researchItemHBox;
            researchItemHBox = researchItemControllerLoader.load();
            ResearchItemController researchItemController = researchItemControllerLoader.getController();
            researchItemController.setResearchNameLabel(researchTitle);
            researchesVBox.getChildren().add(researchItemHBox);
            researchItemController.setGuiResearchReportController(this);
        }catch(IOException e){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    //This method only should be executed by the ResearchItemController Class, do not execute  method in another class.
    public void removeElementFromResearchesVBox(String researchItemControllerTitle){
        int researchesVBoxIndexForRemove = 0;
        for(Node hbox : ((VBox)researchesVBox).getChildren()){
            Node label = ((HBox)hbox).getChildren().get(1); 
            if(((Label)label).getText() == researchItemControllerTitle){
                researchesVBox.getChildren().remove(researchesVBoxIndexForRemove);
                break;
            }    
            researchesVBoxIndexForRemove++;
        }
    }
    //This method only should be executed by the SelectedResearchItemController Class, do not execute  method in another class.
    public void removeElementFromSelectedResearchesVBox(String selectedResearchItemControllerTitle){
        int selectedResearchesVBoxIndexForRemove = 0;
        for(Node hbox : ((VBox)selectedResearchesVBox).getChildren()){
            Node label = ((HBox)hbox).getChildren().get(1); 
            if(((Label)label).getText() == selectedResearchItemControllerTitle){
                selectedResearchesVBox.getChildren().remove(selectedResearchesVBoxIndexForRemove);
                break;
            }    
            selectedResearchesVBoxIndexForRemove++;
        }
    }
    //This method only should be executed by the ResearchItemController Class, do not execute  method in another class.
    public VBox getResearchesVBox(){
        return researchesVBox;
    }
    //This method only should be executed by the SelectedResearchItemController Class, do not execute  method in another class.
    public VBox getSelectedResearchesVBox(){
        return selectedResearchesVBox;
    }

    private void loadHeader(){
        FXMLLoader headerLoader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
        
        try{
            Pane header = headerLoader.load();
            header.getStyleClass().add("/mx/uv/fei/gui/stylesfiles/Styles.css");
            backgroundPane.getChildren().add(header);
            HeaderPaneController headerPaneController = headerLoader.getController();
            //headerPaneController.setTitle("Generar Reporte de Anteproshectos");
            //headerPaneController.setVisibleNRCLabel(false);
            
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
}
