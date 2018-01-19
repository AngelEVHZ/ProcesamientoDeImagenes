/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia_imagenes.CLASES;

import java.io.File;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Valenzuela
 */
public class Metodos2 {

    private Stage stage;
    private ImageView imageView;
    private FileChooser fileChooser;
    Mat imageOriginal;
    public Metodos2(ImageView imageView, Stage stage) {
        this.stage = stage;
        this.imageView = imageView;
        this.fileChooser = new FileChooser();
        imageOriginal = new Mat();

    }
    
     public void cargarImagen(){//tarea 1
       
        File file = new File("./src/recursos/");
        this.fileChooser.setInitialDirectory(file);
        file = this.fileChooser.showOpenDialog(stage);
        this.imageOriginal = Imgcodecs.imread(file.getAbsolutePath());
        this.imageView.setImage(Utils.mat2Image(this.imageOriginal));
      
    }
    
     public void addBlur(int m, int n){
     
        Mat muestra = new Mat();
        
        int dividir = m*n;
        
      
           
        
        muestra.create(m, n, this.imageOriginal.type());
        double data[] = new double[3];
        data[0]=1;
        data[1]=1;
        data[2]=1;
         for (int i = 0; i < m; i++) {
             for (int j = 0; j < n; j++) {
                 muestra.put(i, j, data);
             }
         }
        
      Mat resultado= new Mat();
        resultado= blurConvolucion(this.imageOriginal,muestra,dividir);
     
        this.imageView.setImage(Utils.mat2Image(resultado));
       /*  System.out.println("imprimir");
         for (int i = 0; i < resultado.rows(); i++) {
             for (int j = 0; j <resultado.cols(); j++) {
                 double[] d = resultado.get(i, j);
                 System.out.print("["+d[0]+","+d[1]+","+d[2]+"]" );
                         
             }
             System.out.println("");
             
         }
         System.out.println("termino");*/
        
     }
     
    public double[] convolucion(Mat parteOriginal,Mat parteMuestra, int dividir){
        double[] convolucion = new double[3];
        double result=0;
        for(int i=0; i < parteOriginal.rows();i++)
           for(int j=0;j<parteOriginal.cols();j++){
               double [] data = parteOriginal.get(i, j);
               double [] data2 = parteMuestra.get(i, j);
                       convolucion[0]+= (data[0]*data2[0]);
                       convolucion[1]+= (data[1]*data2[1]);
                       convolucion[2]+= (data[2]*data2[2]);
                          
        }
         convolucion[0]/=dividir;
         convolucion[1]/=dividir;
         convolucion[2]/=dividir;
        
    return convolucion;
}  
    public Mat blurConvolucion(Mat imagen1, Mat muestra, int dividir){
        int m = muestra.rows();
        int n = muestra.cols();
        
        int dx = (muestra.rows()-1)/2;
        int dy = (muestra.cols()-1)/2;
       // System.out.println(dx);
       // System.out.println(dy);
      
       Mat matriz_resultante =calcularConvolucion(imagen1,muestra,dx,dy,m,n,dividir);
       // return  matriz_resultante;
          
     return matriz_resultante;
   
        
    }
    
    
    public Mat nuevaImagen(int mx,int my, Mat imagen){
        Mat nueva = new Mat();
        nueva.create(imagen.rows(),imagen.cols(),imagen.type());
        int z=-1;
        int w=0;
        for (int i = 0; i < imagen.rows(); i++) {
            for (int j = 0; j < imagen.cols(); j++) {
                if(i+mx>=0 && i+mx< imagen.rows() )
                    if(j+my>=0 && j+my< imagen.cols()){
                        double[] data = imagen.get(i, j);
                        
                        nueva.put(i+mx,j+my, data);
                    }
            }
        }
        return nueva;
    }
   
            
          
    public Mat calcularConvolucion(Mat imagen, Mat recorte,int dx, int dy, int m, int n, int dividir){
        int posx=0;
        int posy=0;
        Mat parte_original;
        double[] valor=new double[3];
        
      int numero=0;
      Mat resultado = new Mat();
      resultado.create(imagen.rows(), imagen.cols(),imagen.type());
   
        for ( posx = 0; posx < imagen.rows(); posx++) {
           for ( posy = 0; posy < imagen.cols(); posy++) {
                
                if(  ((posx-dx>=0) &&  (posx+dx) < imagen.rows()) && ((posy-dy)>=0 &&  (posy+dy)<imagen.cols())){
                    parte_original = recortar(imagen,dx,dy,m,n,posx,posy);       
                    valor = convolucion( parte_original, recorte,dividir);
                }              
                resultado.put(posx,posy,valor);
               
           }
        }

       return resultado;
    
    }
    
    public Mat recortar (Mat imagen, int dx,int dy, int m,int n,int x, int y){
        Mat recorte = new Mat();
        recorte.create(m, n, imagen.type());
        int z=-1;
        int w=-1;
        for(int i = x-dx; i<= x+dx;i++){ 
            z++;
            w=-1;
            for (int j = y-dy; j <= y+dy; j++) {
                w++;
                double[] data = imagen.get(i, j);
                recorte.put(z, w, data);
            }
        }
 
        return recorte;
    }

}
