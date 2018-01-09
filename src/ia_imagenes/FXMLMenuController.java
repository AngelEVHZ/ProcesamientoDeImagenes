/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia_imagenes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Valenzuela
 */
public class FXMLMenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    public Stage stage;
    
    
    @FXML
    private void tarea1(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = (Parent)loader.load();
        FXMLDocumentController controller = (FXMLDocumentController)loader.getController();
        controller.setStage(this.stage);
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void tarea2(ActionEvent event) {
        
    }
    @FXML
    private void tarea3(ActionEvent event) {
        
    }
    @FXML
    private void tarea4(ActionEvent event) {
        
    }
    
    
    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.stage = new Stage();
        
    }    

    void setStage(Stage stage) {
    }
    
}
