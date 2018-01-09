/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia_imagenes;

import ia_imagenes.CLASES.ImagenMetodos;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Valenzuela
 */
public class FXMLTarea3Controller implements Initializable {
    @FXML   private ImageView imageOriginal;
    @FXML   private ImageView imageNueva;
    public Stage stage;
    /**
     * Initializes the controller class.
     */
    private  ImagenMetodos metodos;
     @FXML
    private void abrirImagenes(ActionEvent event) {
        
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
   }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        metodos= new ImagenMetodos(imageOriginal,imageNueva,stage);
    }    
    
}
