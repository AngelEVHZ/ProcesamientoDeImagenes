/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia_imagenes.CLASES;


import java.io.File;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static org.opencv.core.CvType.CV_8U;
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
    private Mat picture1, picture2;
    private FileChooser fileChooser;
    private Stage stage;
    public ImagenMetodos(ImageView imageView,ImageView imageViewNueva, Stage stage){
        this.imageView = imageView;
        this.imageViewNueva = imageViewNueva;
        this.stage = stage;
        
          
    }
    
  
    //tarea 3
    public void cargarImagenes(){//tarea 3
         this.picture1 = new Mat();
         this.picture2 = new Mat();
         this.fileChooser = new FileChooser();
        File file1=null,file2=null;
        this.fileChooser.setInitialDirectory(new File("./src/recursos/"));
        List<File> list = fileChooser.showOpenMultipleDialog(this.stage);
        if (list!=null) {
            file1 = list.get(0);
            if (list.size() > 1){
                file2 = list.get(1);
                this.picture1 = Imgcodecs.imread(file1.getAbsolutePath());
                this.picture2 = Imgcodecs.imread(file2.getAbsolutePath());
                
                this.imageView.setImage(Utils.mat2Image(this.picture1));
                this.imageViewNueva.setImage(Utils.mat2Image(this.picture2));
            }
        
        }
   
    }
    
    public void diferencia(){
        Mat diference = new Mat();
        
        Mat p1 = new Mat();
        Mat p2 = new Mat();
        Imgproc.cvtColor(picture1,p1 , Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(picture2,p2 , Imgproc.COLOR_BGR2GRAY);
        diference = p1.clone();
        
       
        for(int i=0; i < p1.rows();i++)
           for(int j=0;j<p1.cols();j++){
               double [] data = p1.get(i, j);
               double [] data2 = p2.get(i, j);
               
               data[0] = data[0] - data2[0] ;
       //        data[1] = data[1] - data2[1] ;
       //        data[2] = data[2] - data2[2] ;
               
               diference.put(i, j, data);
        }
        
        Imgproc.threshold(diference,diference, 123,255, Imgproc.THRESH_BINARY);
        
        
       this.imageViewNueva.setImage(Utils.mat2Image(diference));
       
    
    }
    
    public void prueba(){
 
    }
    
    
    //tarea 1
    public void cargarImagen(){//tarea 1
        init();
        File file = new File("./src/recursos/");
        this.fileChooser.setInitialDirectory(file);
        file = this.fileChooser.showOpenDialog(stage);
        this.imageOriginal = Imgcodecs.imread(file.getAbsolutePath());
        this.imageView.setImage(Utils.mat2Image(this.imageOriginal));
        this.imageViewNueva.setImage(Utils.mat2Image(this.imageOriginal));
   
    }
    public void escalaGrises(){//tarea 1
         
        Imgproc.cvtColor(imageOriginal,imageBlack , Imgproc.COLOR_BGR2GRAY);
        this.imageViewNueva.setImage(Utils.mat2Image(this.imageBlack));
       
       
    }
    public void init(){ //tarea 1
        this.fileChooser = new FileChooser();
        this.imageOriginal = new Mat();
        this.imageBlack= new Mat();
     
    }
    public void correspondenciaNoLineal(double y) { //tarea 1
        
        escalaGrises();
        Mat median = new Mat();
        median = imageBlack.clone();
        
       for(int i=0; i < imageBlack.rows();i++)
           for(int j=0;j<imageBlack.cols();j++){
               double [] data = imageBlack.get(i, j);
               data[0] = data[0] *y;
               median.put(i, j, data);
        }
     
       this.imageViewNueva.setImage(Utils.mat2Image(median));
       
    }
    public void correspondenciaNoLinealPorComponente(double r, double g, double b) {//tarea 1
    
        Mat median = new Mat();
        median = imageOriginal.clone();
        
       for(int i=0; i < imageOriginal.rows();i++)
           for(int j=0;j<imageOriginal.cols();j++){
               double [] data = imageOriginal.get(i, j);
               data[0] = data[0] *b;
               data[1] = data[1] *g;
               data[2] = data[2] *r;
               median.put(i, j, data);
        }
     
       this.imageViewNueva.setImage(Utils.mat2Image(median));
    
    }
    
}