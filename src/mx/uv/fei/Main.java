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
import mx.uv.fei.gui.controllers.reports.GuiResearchReportController;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.daos.AcademicBodyDAO;
import mx.uv.fei.logic.daos.AdvanceDAO;
import mx.uv.fei.logic.daos.ReceptionalWorkDAO;
import mx.uv.fei.logic.daos.ResearchesReportDAO;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.Research;
import mx.uv.fei.logic.domain.ResearchReport;


public class Main extends Application {

    @Override
    public void start(Stage arg0) throws Exception {
        Parent guiUsuarios;
        FXMLLoader loader = new FXMLLoader(
            //getClass().getResource("/mx/uv/fei/gui/fxml/kgal/KGALList.fxml")
            getClass().getResource("/mx/uv/fei/gui/fxml/research/ResearchManager.fxml")
            //getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ModifyActivity.fxml")// tha xavier gui
            //getClass().getResource("/mx/uv/fei/gui/fxml/courses/students/GuiUsersCourse.fxml")
            //getClass().getResource("/mx/uv/fei/gui/fxml/courses/GuiCourses.fxml")
            //getClass().getResource("/mx/uv/fei/gui/fxml/users/GuiUsers.fxml")
            //getClass().getResource("/mx/uv/fei/gui/fxml/reports/GuiResearchReport.fxml")
        );
        guiUsuarios = loader.load();
        
        Scene scene = new Scene(guiUsuarios);
        String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        Stage stage = new Stage();
        //stage.setFullScreen(true);
        
        stage.setTitle("SPGER");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    public static void main (String args[]) {
        launch(args);
    }
}
