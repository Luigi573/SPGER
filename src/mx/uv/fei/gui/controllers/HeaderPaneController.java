package mx.uv.fei.gui.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HeaderPaneController{

    @FXML
    private Pane headerPane;
    @FXML
    private Label titleLabel;
    @FXML
    private Label NRCLabel;

    @FXML
    private void goHome(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/Chronogram.fxml"));
            Parent parent = loader.load();
            
            Scene scene = new Scene(parent);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
        }catch(IllegalStateException | IOException exception){
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setHeaderText("Error de carga");
            errorMessage.setContentText("No se pudo abrir la ventana, verifique que el archivo .fxml esté en su ubicación correcta");
            errorMessage.showAndWait();
        }
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public void setTitle(String title) {
        this.titleLabel.setText(title);
    }

    public String getNRC() {
        return NRCLabel.getText();
    }

    public void setNRC(String nRC) {
        NRCLabel.setText(nRC);
    }

    public void setVisibleNRCLabel(boolean setVisible) {
        NRCLabel.setVisible(setVisible);
    }

    //public void setTitleSize() {
    //    titleLabel.getText().set
    //}
}