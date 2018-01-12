/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia_imagenes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opencv.core.Core;
import ia_imagenes.CLASES.*;
import org.opencv.core.Mat;
/**
 *
 * @author Valenzuela
 */
public class IA_IMAGENES extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
       /* FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = (Parent)loader.load();
        FXMLDocumentController controller = (FXMLDocumentController)loader.getController();
        controller.setStage(stage);
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        */
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMenu.fxml"));
        Parent root = (Parent)loader.load();
        FXMLMenuController controller = (FXMLMenuController)loader.getController();
       // controller.setStage(stage);
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
       
        launch(args);
    }
    
}
