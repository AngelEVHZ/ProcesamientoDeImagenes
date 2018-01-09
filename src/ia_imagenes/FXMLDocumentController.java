/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia_imagenes;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;

import ia_imagenes.CLASES.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


/**
 *
 * @author Valenzuela
 */
public class FXMLDocumentController implements Initializable {
    
    
    @FXML   private ImageView imageView;
    @FXML   private ImageView imageViewNueva;
    @FXML   private TextField correspondenciaNoLinealY;
    @FXML   private TextField textR;
    @FXML   private TextField textG;
    @FXML   private TextField textB;
    
    private Stage stage;
    private  ImagenMetodos metodos;
    
    
    @FXML
    private void abrirImagen(ActionEvent event) {
        this.metodos.cargarImagen();
    }
    
    @FXML
    private void escalaGris(ActionEvent event) {
        this.metodos.escalaGrises();
    }
    @FXML
    private void correspondenciaNoLineal(ActionEvent event) {
        double y  = Double.parseDouble(correspondenciaNoLinealY.getText() );
        this.metodos.correspondenciaNoLineal(y);
       
    }
    @FXML
    private void correspondenciaNoLinealPorComponente(ActionEvent event) {
        double r  = Double.parseDouble(textR.getText() );
        double g  = Double.parseDouble(textG.getText() );
        double b  = Double.parseDouble(textB.getText() );
        this.metodos.correspondenciaNoLinealPorComponente(r,g,b);
       
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        metodos = new ImagenMetodos(imageView,imageViewNueva,stage);
        
    }    

    public void setStage(Stage stage) {
        this.stage = stage;
   }
    
}
