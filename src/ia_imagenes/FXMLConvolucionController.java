/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia_imagenes;

import ia_imagenes.CLASES.ImagenMetodos;
import ia_imagenes.CLASES.Metodos2;
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
public class FXMLConvolucionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML   private ImageView imageView;
     @FXML   private TextField m;
      @FXML   private TextField n;
     private Stage stage;
    private  Metodos2 metodos;
      @FXML
    private void imagen(ActionEvent event) {
        metodos.cargarImagen();
    }
    
    @FXML
    private void convolucion(ActionEvent event) {
        int m = Integer.parseInt(this.m.getText());
        int n = Integer.parseInt(this.n.getText());
        if(m%2==0)m+=1;
        if(n%2==0)n+=1;
        metodos.addBlur(n,m);
    }
    
     @FXML
    private void integralImg(ActionEvent event) {
        
        metodos.integral();
    }
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          // TODO
        metodos = new Metodos2(imageView,stage);
        
        
    }    

    public void setStage(Stage stage) {
        this.stage = stage;
   }   // TODO
        
    
}
