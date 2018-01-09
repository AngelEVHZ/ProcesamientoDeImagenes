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
    private boolean grayScale =false;
    private Mat picture1, picture2;
    private FileChooser fileChooser;
    private Stage stage;
    
    
    public ImagenMetodos(ImageView imageView,ImageView imageViewNueva, Stage stage){
        this.imageView = imageView;
        this.imageViewNueva = imageViewNueva;
        this.stage = stage;
        
          
    }
    
   
    
    
  //tarea 4
    private void histograma(Mat frame, boolean gray)
	{
		// split the frames in multiple images
		List<Mat> images = new ArrayList<Mat>();
		Core.split(frame, images);
		
		// set the number of bins at 256
		MatOfInt histSize = new MatOfInt(256);
		// only one channel
		MatOfInt channels = new MatOfInt(0);
		// set the ranges
		MatOfFloat histRange = new MatOfFloat(0, 256);
		
		// compute the histograms for the B, G and R components
		Mat hist_b = new Mat();
		Mat hist_g = new Mat();
		Mat hist_r = new Mat();
		
		// B component or gray image
		Imgproc.calcHist(images.subList(0, 1), channels, new Mat(), hist_b, histSize, histRange, false);
		
		// G and R components (if the image is not in gray scale)
		if (!gray)
		{
			Imgproc.calcHist(images.subList(1, 2), channels, new Mat(), hist_g, histSize, histRange, false);
			Imgproc.calcHist(images.subList(2, 3), channels, new Mat(), hist_r, histSize, histRange, false);
		}
		
		// draw the histogram
		int hist_w = 150; // width of the histogram image
		int hist_h = 150; // height of the histogram image
		int bin_w = (int) Math.round(hist_w / histSize.get(0, 0)[0]);
		
		Mat histImage = new Mat(hist_h, hist_w, CvType.CV_8UC3, new Scalar(0, 0, 0));
		// normalize the result to [0, histImage.rows()]
		Core.normalize(hist_b, hist_b, 0, histImage.rows(), Core.NORM_MINMAX, -1, new Mat());
		
		// for G and R components
		if (!gray)
		{
			Core.normalize(hist_g, hist_g, 0, histImage.rows(), Core.NORM_MINMAX, -1, new Mat());
			Core.normalize(hist_r, hist_r, 0, histImage.rows(), Core.NORM_MINMAX, -1, new Mat());
		}
		
		// effectively draw the histogram(s)
		for (int i = 1; i < histSize.get(0, 0)[0]; i++)
		{
			// B component or gray image
			Imgproc.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(hist_b.get(i - 1, 0)[0])),
					new Point(bin_w * (i), hist_h - Math.round(hist_b.get(i, 0)[0])), new Scalar(255, 0, 0), 2, 8, 0);
			// G and R components (if the image is not in gray scale)
			if (!gray)
			{
				Imgproc.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(hist_g.get(i - 1, 0)[0])),
						new Point(bin_w * (i), hist_h - Math.round(hist_g.get(i, 0)[0])), new Scalar(0, 255, 0), 2, 8,
						0);
				Imgproc.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(hist_r.get(i - 1, 0)[0])),
						new Point(bin_w * (i), hist_h - Math.round(hist_r.get(i, 0)[0])), new Scalar(0, 0, 255), 2, 8,
						0);
			}
		}
		
		// display the histogram...
		Image histImg = Utils.mat2Image(histImage);
                this.imageViewNueva.setImage(histImg);
		//updateImageView(imageViewNueva, histImg);
		
	}
    
    public void refresHistogram() {
        grayScale = !grayScale;
        if(grayScale){
                 Imgproc.cvtColor(imageOriginal,imageBlack , Imgproc.COLOR_BGR2GRAY);
                 this.imageView.setImage(Utils.mat2Image(this.imageBlack));
                  histograma(this.imageBlack,grayScale);
        }else{
             this.imageView.setImage(Utils.mat2Image(this.imageOriginal));
             histograma(this.imageOriginal,grayScale);
        }
    
    }
     public void cargarImagenT4(){//tarea 1
         
       this.fileChooser = new FileChooser();
       this.imageOriginal = new Mat();
       this.imageBlack = new Mat();
        File file = new File("./src/recursos/");
        this.fileChooser.setInitialDirectory(file);
        file = this.fileChooser.showOpenDialog(stage);
        this.imageOriginal = Imgcodecs.imread(file.getAbsolutePath());
        this.imageView.setImage(Utils.mat2Image(this.imageOriginal));
        
        
        histograma(this.imageOriginal,false);
   
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
    public void diferencia(){//tarea 3
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
               data[0] = data[0] * y;
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