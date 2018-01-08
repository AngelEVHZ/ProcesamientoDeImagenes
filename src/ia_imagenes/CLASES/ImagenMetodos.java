/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia_imagenes.CLASES;


import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Valenzuela
 */
public class ImagenMetodos {
    private ImageView imageView;
    private ImageView imageViewNueva;
    private Mat imageBlack;
    private Mat imageOriginal;
    private FileChooser fileChooser;
    private Stage stage;
    public ImagenMetodos(ImageView imageView,ImageView imageViewNueva, Stage stage){
        this.imageView = imageView;
        this.imageViewNueva = imageViewNueva;
        this.stage = stage;
        
          
    }
    
    public void cargarImagen(){
        init();
        File file = new File("./src/recursos/");
        this.fileChooser.setInitialDirectory(file);
        file = this.fileChooser.showOpenDialog(stage);
        this.imageOriginal = Imgcodecs.imread(file.getAbsolutePath());
        this.imageView.setImage(Utils.mat2Image(this.imageOriginal));
        this.imageViewNueva.setImage(Utils.mat2Image(this.imageOriginal));
    
    
       
    }
    
    public void prueba(){
        Mat median = new Mat();
       median = imageOriginal.clone();
       
        
        /* Imgproc.medianBlur(imageOriginal,median,5);
        this.imageViewNueva.setImage(Utils.mat2Image(median));*/
   /*
        
       for(int i=0; i < imageOriginal.rows();i++)
           for(int j=0;j<imageOriginal.cols();j++){
               double [] data = imageOriginal.get(i, j);
               data[0] = data[0]* 0.3;//b
               data[1] = data[1]* 0;//g
               data[2] = data[2]* 0;//r
               median.put(i, j, data);
        }*/
     
       this.imageViewNueva.setImage(Utils.mat2Image(median));
       
    }
    
    
    public void escalaGrises(){
         
        Imgproc.cvtColor(imageOriginal,imageBlack , Imgproc.COLOR_BGR2GRAY);
        this.imageViewNueva.setImage(Utils.mat2Image(this.imageBlack));
        
        
       
    }
    public void init(){
        this.fileChooser = new FileChooser();
        this.imageOriginal = new Mat();
        this.imageBlack= new Mat();
     
    }

    public void correspondenciaNoLineal() {
        escalaGrises();
    
    }
    
}