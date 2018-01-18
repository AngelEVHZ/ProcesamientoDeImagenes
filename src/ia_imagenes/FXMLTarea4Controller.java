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
import javafx.scene.control.TextField;
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
     @FXML   private TextField a;
      @FXML   private TextField b;
       @FXML   private TextField c;
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
       double cLimit=Double.parseDouble(a.getText());
       double rinicio=Double.parseDouble(b.getText());
       double rfinal=Double.parseDouble(c.getText());

      metodos.adaptativa(cLimit,rinicio,rfinal);
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
