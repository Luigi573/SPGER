package mx.uv.fei;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.embed.swt.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.gui.javafiles.guiresearchreportcontrollers.GuiResearchReportController;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.daos.AcademicBodyDAO;
import mx.uv.fei.logic.daos.AdvanceDAO;
import mx.uv.fei.logic.daos.ReceptionalWorkDAO;
import mx.uv.fei.logic.daos.ResearchesReportDAO;
import mx.uv.fei.logic.domain.Research;
import mx.uv.fei.logic.domain.ResearchReport;


public class Main extends Application{    

    @Override
    public void start(Stage arg0) throws Exception {
        Parent guiUsuarios;
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("gui/fxmlfiles/guicourses/GuiCourses.fxml")
            //getClass().getResource("gui/fxmlfiles/guiusers/GuiUsers.fxml")
            //getClass().getResource("gui/fxmlfiles/guiresearchreport/GuiResearchReport.fxml")
        );
        guiUsuarios = loader.load();
        
        Scene scene = new Scene(guiUsuarios);
        Stage stage = new Stage();
        
        stage.setTitle("SPGER");
        stage.setScene(scene);
        stage.show();
    }

//   @Override
//    public void start(Stage stage) {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/mx/uv/fei/gui/fxml/manageKGAL/KGALList.fxml"));
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.setResizable(false);
//            stage.show();
//        } catch (IOException e) {
//            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
//            errorMessage.setHeaderText("Error al mostrar la información");
//            errorMessage.setContentText("Ocurrió un error al intentar mostrar el menú principal.");
//            errorMessage.showAndWait();
//        }
//    }
    
    public static void main (String args[]) {
        launch(args);

   
    }
}
