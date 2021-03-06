
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia_imagenes.CLASES;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import static org.opencv.core.CvType.CV_8U;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.CLAHE;
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
    private boolean grayScale =false;
    private Mat picture1, picture2;
    private FileChooser fileChooser;
    private Stage stage;
    
    
    public ImagenMetodos(ImageView imageView,ImageView imageViewNueva, Stage stage){
        this.imageView = imageView;
        this.imageViewNueva = imageViewNueva;
        this.stage = stage;
        
          
    }
    
    public ImagenMetodos(){
        
    }
    
   
    
    
  //tarea 4
   public void cargarImagenT4(){//tarea 1         
       this.fileChooser = new FileChooser();
       this.imageOriginal = new Mat();
       this.imageBlack = new Mat();
        File file = new File("./src/recursos/");
        this.fileChooser.setInitialDirectory(file);
        file = this.fileChooser.showOpenDialog(stage);
        this.imageOriginal = Imgcodecs.imread(file.getAbsolutePath());
        this.imageView.setImage(Utils.mat2Image(this.imageOriginal));
        
   }
   
   
   
    public void histograma(){
        Mat imgBlack= new Mat();
        Imgproc.cvtColor(this.imageOriginal,imgBlack , Imgproc.COLOR_BGR2GRAY);
        calcularHistograma(imgBlack);
        this.imageView.setImage(Utils.mat2Image(this.imageOriginal));
    
    }
    
    public void calcularHistograma(Mat imagen){
            ArrayList<Mat> imgList=new ArrayList<>();
            imgList.add(imagen);
            MatOfInt histSize = new MatOfInt(256);
            final MatOfFloat histRange = new MatOfFloat(0f, 256f);
            Mat b_hist = new  Mat();
            Imgproc.calcHist(imgList, new MatOfInt(0),new Mat(), b_hist, histSize, histRange, false);
            int hist_w = imagen.width();
            int hist_h = imagen.height();

            long bin_w;
            bin_w = Math.round((double) (hist_w / 256));

            Mat histImage = new Mat(hist_h, hist_w, CvType.CV_8UC1);
            Core.normalize(b_hist, b_hist, 3, histImage.rows(), Core.NORM_MINMAX);

            for (int i = 1; i < 256; i++) {         
               Imgproc.line(histImage, new Point(bin_w * (i - 1),hist_h- Math.round(b_hist.get( i-1,0)[0])), 
                   new Point(bin_w * (i), hist_h-Math.round(Math.round(b_hist.get(i, 0)[0]))),
                   new  Scalar(255, 0, 0), 2, 8, 0);
            }
            this.imageViewNueva.setImage(Utils.mat2Image(histImage));
    }
    
    public void ecualizar(){
       Mat originalBlack= new Mat();
       Imgproc.cvtColor(this.imageOriginal,originalBlack , Imgproc.COLOR_BGR2GRAY);
       Mat ecualizada = originalBlack.clone();
       Imgproc.equalizeHist(originalBlack, ecualizada); 
       this.imageView.setImage(Utils.mat2Image(ecualizada));
       calcularHistograma(ecualizada);
    }
   
    public void adaptativa(double cLimit, double rinicio, double rfinal){            
        
        
        if(rinicio<0) rinicio=1.0;
        if(rfinal<rinicio){double aux;aux=rfinal;rfinal=rinicio;rinicio=aux;}
        if(rfinal<0)rfinal=2.0;

       
        Mat originalBlack = new Mat();
        Imgproc.cvtColor(this.imageOriginal,originalBlack , Imgproc.COLOR_BGR2GRAY);
   
        Mat adaptativa =originalBlack.clone();
        CLAHE abc = Imgproc.createCLAHE();
        abc.setClipLimit(cLimit);
        abc.setTilesGridSize(new Size(rinicio,rfinal));
        abc.apply(originalBlack, adaptativa);
        
        
        
        this.imageView.setImage(Utils.mat2Image(adaptativa));
        calcularHistograma(adaptativa);
 
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
               data[0] = Math.pow(data[0] , y);
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
               data[0] = Math.pow(data[0] ,b);
               data[1] = Math.pow(data[1] ,g);
               data[2] = Math.pow(data[2] ,r);
               median.put(i, j, data);
        }
     
       this.imageViewNueva.setImage(Utils.mat2Image(median));
    
    }
    
    
    public double ssd(Mat parteOriginal,Mat parteMuestra){
      
        double ssd=0,result=0;
        
        for(int i=0; i < parteOriginal.rows();i++)
           for(int j=0;j<parteOriginal.cols();j++){
               
               double [] data = parteOriginal.get(i, j);
               double [] data2 = parteMuestra.get(i, j);
               
               
                       result= (data[0]-data2[0]);
                       ssd=ssd+Math.pow(result, 2);
                       //ssd+=result;
              
        }
        
    return ssd;
}

    public void diferencia(){//tarea 3
        Mat diference = new Mat();
      
        Mat alineada = alinear(picture1,picture2);
       
        Mat p1 = new Mat();
        
        //Mat p2 = new Mat();
        Imgproc.cvtColor(picture1,p1 , Imgproc.COLOR_BGR2GRAY);
        //Imgproc.cvtColor(picture2,p2 , Imgproc.COLOR_BGR2GRAY);
        diference = p1.clone();
        
       
       
        for(int i=0; i < p1.rows();i++)
           for(int j=0;j<p1.cols();j++){
               double [] data = p1.get(i, j);
               double [] data2 = alineada.get(i, j);
               
               data[0] = data2[0] - data[0] ;
       //      data[1] = data[1] - data2[1] ;
       //      data[2] = data[2] - data2[2] ;
               
               diference.put(i, j, data);
        
        }
        
        Imgproc.threshold(diference,diference, 5,255, Imgproc.THRESH_BINARY);   
       this.imageViewNueva.setImage(Utils.mat2Image(diference));
       
    
    }
    
    public Mat alinear(Mat imagen1, Mat imagen2){
        int size=5;
         int pos_x=imagen1.rows()/2;
        int pos_y=imagen1.cols()/2;
      
        Mat imagen1_gris = new Mat();
        Mat imagen2_gris = new Mat();
        Imgproc.cvtColor(imagen1,imagen1_gris , Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(imagen2,imagen2_gris , Imgproc.COLOR_BGR2GRAY);
        
        
        Mat recorte = new Mat();
        recorte.create(size,size ,imagen1_gris.type()); 
        int dx = (recorte.rows()-1)/2;
        int dy = (recorte.cols()-1)/2;
        
       
        
        
        llenar_recorte(imagen2_gris,recorte,dx,dy,pos_x,pos_y);
        
/*
        System.out.println("");
        for (int i = 0; i < imagen1_gris.rows(); i++) {
            for (int j = 0; j < imagen1_gris.cols(); j++) {
                System.out.print(imagen1_gris.get(i, j)[0]+" ");
            }
            System.out.println("");    
        }
        System.out.println("");
        System.out.println("RESPUESTA");
        for (int i = 0; i < recorte.rows(); i++) {
            for (int j = 0; j < recorte.cols(); j++) {
                 System.out.print(recorte.get(i, j)[0]+" ");
            }
            System.out.println("");
        }
        
        */
        
        
        
        
        double[][] matriz_resultante =buscarCoincidencia(imagen1_gris,recorte,dx,dy,size);
        
        int[] coordenadas = distanciaDeAlineacion(matriz_resultante,imagen1_gris.rows(),imagen1_gris.cols(),dx,dy);
        
        //System.out.println(coordenadas[0]+"  y "+coordenadas[1]);
        
        int mx =  coordenadas[0]-pos_x;
        int my = coordenadas[1]- pos_y;   
        Mat alineada = nuevaImagen(mx,my,imagen2_gris);
       
        return alineada;
        //return imagen2;
        //System.out.println("diferencia x"+ mx + "  y "+my);
        //System.out.println("termino");
        //this.imageViewNueva.setImage(Utils.mat2Image(recorte));
        
    }
    
    
     public Mat alinear2(Mat imagen1, Mat imagen2){
        int size=31;
        int pos_x=373;
        int pos_y=405;
      
        Mat imagen1_gris = imagen1.clone();
        Mat imagen2_gris = imagen2.clone();
       //Imgproc.cvtColor(imagen1,imagen1_gris , Imgproc.COLOR_BGR2GRAY);
      // Imgproc.cvtColor(imagen2,imagen2_gris , Imgproc.COLOR_BGR2GRAY);
        
        
        Mat recorte = new Mat();
        recorte.create(size,size ,imagen1_gris.type()); 
        int dx = (recorte.rows()-1)/2;
        int dy = (recorte.cols()-1)/2;
        
        
        
        
        llenar_recorte(imagen2_gris,recorte,dx,dy,pos_x,pos_y);
        
/*
        System.out.println("");
        for (int i = 0; i < imagen1_gris.rows(); i++) {
            for (int j = 0; j < imagen1_gris.cols(); j++) {
                System.out.print(imagen1_gris.get(i, j)[0]+" ");
            }
            System.out.println("");    
        }
        System.out.println("");
        System.out.println("RESPUESTA");
        for (int i = 0; i < recorte.rows(); i++) {
            for (int j = 0; j < recorte.cols(); j++) {
                 System.out.print(recorte.get(i, j)[0]+" ");
            }
            System.out.println("");
        }
        
        */
        
        
        
        
        double[][] matriz_resultante =buscarCoincidencia(imagen1_gris,recorte,dx,dy,size);
        
        int[] coordenadas = distanciaDeAlineacion(matriz_resultante,imagen1_gris.rows(),imagen1_gris.cols(),dx,dy);
        
        //System.out.println(coordenadas[0]+"  y "+coordenadas[1]);
        
        int mx =  coordenadas[0]-pos_x;
        int my = coordenadas[1]- pos_y;   
        Mat alineada = nuevaImagen(mx,my,imagen2_gris);
       
        return alineada;
        //return imagen2;
        //System.out.println("diferencia x"+ mx + "  y "+my);
        //System.out.println("termino");
        //this.imageViewNueva.setImage(Utils.mat2Image(recorte));
        
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
    
    public int[] distanciaDeAlineacion(double[][] matriz,int sizex, int sizey,int dx, int dy){
        double menor  =matriz[sizex/2][sizey/2];
        int x=0;
        int y=0;

        for (int i = dx; i < (sizex-dx); i++) {
            for (int j = dy; j < (sizey-dy); j++) {
                if(menor > matriz[i][j]){
                    menor =matriz[i][j];
                    x=i;
                    y=j;
                }
            }
        }
        
        int[] data = new int[2];
        data[0] = x;
        data[1]=  y;
        return data;
    }
            
            
    public double[][] buscarCoincidencia(Mat imagen, Mat recorte,int dx, int dy, int size){
        int posx=0;
        int posy=0;
        Mat parte_original;
        
      int numero=0;
        double [][] resultado = new double[imagen.rows()][imagen.cols()];
        
        double valor=0;
        for ( posx = 0; posx < imagen.rows(); posx++) {
           for ( posy = 0; posy < imagen.cols(); posy++) {
                valor=0;
                if(  ((posx-dx>=0) &&  (posx+dx) < imagen.rows()) && ((posy-dy)>=0 &&  (posy+dy)<imagen.cols())){
                    parte_original = recortar(imagen,dx,dy,size,posx,posy);
      /*              numero++;
                  System.out.println("");
                    System.out.println("parte orignal " + numero); 
                    for (int i = 0; i < parte_original.rows(); i++) {
                        for (int j = 0; j < parte_original.cols(); j++) {
                            System.out.print(parte_original.get(i, j)[0]+" ");
                        }
                        System.out.println("");    
                    }
                    
        */            
                    
                    valor = ssd( parte_original, recorte);
    //                System.out.println("ssd " + valor);
                }              
                resultado[posx][posy]=valor;
           }
        }
        /*
        System.out.println("MATRIZ RESULTADO");
        for (int i = 0; i < imagen.rows(); i++) {
            for (int j = 0; j < imagen.cols(); j++) {
                System.out.print(resultado[i][j]+" ");
            }
            System.out.println("");
        }
  */
       return resultado;
    
    }
    
    public Mat recortar (Mat imagen, int dx,int dy, int size,int x, int y){
        Mat recorte = new Mat();
        recorte.create(size, size, imagen.type());
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
        /*System.out.println("recorte x "+ x+" y "+y);
        for(int i =0;i<3;i++){
            for(int j =0;j<3;j++)System.out.print(recorte.get(i, j)[0] + " ");
            System.out.println("");
        }
        */
        return recorte;
    }

    public void llenar_recorte(Mat imagen, Mat recorte, int dx, int dy,int centro_x, int centro_y) {
        
        //System.out.println(imagen.get(0, 0)[0]); 
        //System.out.println(imagen.get(1, 2)[0]); 

        
        //System.out.println("centro " + (centro_x-dx) + " "+ (centro_y-dy) );
        //System.out.println("centro " + (centro_x+dx) + " "+ (centro_y+dy));
        
        int z=-1;
        int w=-1;
        for (int i = (centro_x-dx); i <= (centro_x+dx); i++) {
            z++;
            w=-1;
            for (int j = (centro_y-dy); j <= (centro_y+dy); j++) {
                w++;
                double [] data = imagen.get(i, j);
               // System.out.println(i + "  "+ j);
                recorte.put(z, w, data);
            }
        }        
    }
}