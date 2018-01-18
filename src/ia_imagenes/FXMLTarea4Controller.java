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
public class FXMLTarea4Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    public Stage stage;
    @FXML   private ImageView imageOriginal;
    @FXML   private ImageView histograma;
    private  ImagenMetodos metodos;
    @FXML 
    private void abrirImagen(ActionEvent event) {
        metodos.cargarImagenT4();
    }
    
    @FXML 
    private void escalaGrises(ActionEvent event) {
       metodos.histograma();
    }
    @FXML 
    private void ecualizar(ActionEvent event) {
      metodos.ecualizar();
    }
    @FXML 
    private void adaptativa(ActionEvent event) {
      metodos.adaptativa();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            metodos= new ImagenMetodos(imageOriginal,histograma,stage);
        // TODO
    }    

    void setStage(Stage stage) {
        this.stage = stage;
    }
    
}
