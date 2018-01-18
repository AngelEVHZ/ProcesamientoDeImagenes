package ia_imagenes;

import ia_imagenes.CLASES.ImagenMetodos;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import ia_imagenes.CLASES.Utils;
import java.io.File;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static org.opencv.videoio.Videoio.CV_CAP_PROP_FRAME_COUNT;
import static org.opencv.videoio.Videoio.CV_CAP_PROP_POS_FRAMES;


 
public class VideoController
{
	@FXML
	private Button button;
	@FXML
	private CheckBox grayscale;
	@FXML
	private CheckBox logoCheckBox;
	@FXML
	private ImageView histogram;
	@FXML
	private ImageView currentFrame;
        @FXML
	private ImageView currentFrame2;
	double count;
	private ScheduledExecutorService timer;
	
	private VideoCapture capture;

	private boolean cameraActive;
	
	private Mat logo;
         private Stage stage;
         private File file;
         private FileChooser fileChooser;
         ImagenMetodos metodos;
	Mat f;
        boolean bandera= true;
	int in=0;
	public void initialize()
	{
		this.capture = new VideoCapture();
                this.f = new Mat();
		this.cameraActive = false;
               metodos = new ImagenMetodos();
	}
	
	
	@FXML
	protected void startCamera()
	{
		
		this.currentFrame.setFitWidth(600);
		
		this.currentFrame.setPreserveRatio(true);
                this.bandera=true;
                this.in=0;
		
		if (!this.cameraActive)
		{
			
                        
                        this.capture.open(file.getAbsolutePath());
                        
                            
                        
			
			 count = capture.get(CV_CAP_PROP_FRAME_COUNT);
                        
                       
			if (this.capture.isOpened())
			{
				this.cameraActive = true;
				
				
				Runnable frameGrabber = new Runnable() {
					
					@Override
					public void run()
					{
                                            Mat frame = grabFrame();
                                            reduceRuido(frame);
                                            Image imageToShow = Utils.mat2Image(f);
                                            updateImageView(currentFrame, imageToShow);
                                           
						
					}
				};
				
				this.timer = Executors.newSingleThreadScheduledExecutor();
				this.timer.scheduleAtFixedRate(frameGrabber, 0,33, TimeUnit.MILLISECONDS);
				
				
				this.button.setText("Parar");
			}
			else
			{
				
				System.err.println("Impossible to open the camera connection...");
			}
		}
		else
		{
			
			this.cameraActive = false;
			
			this.button.setText("Play");
			
			
			this.stopAcquisition();
		}
	}
	
	
	
	
	
	private Mat grabFrame()
	{
		Mat frame = new Mat();
		//Mat aux = new Mat();
		
		if (this.capture.isOpened())
		{
			try
			{
				
				this.capture.read(frame);
				
				
				if (!frame.empty())
				{
                                    
                                      //Imgproc.cvtColor(frame,frame, Imgproc.COLOR_BGR2GRAY);
                                      
					
				}
				
			}
			catch (Exception e)
			{
				
				System.err.println("Exception during the frame elaboration: " + e);
			}
		}
		
		return frame;
	}
        
        private void reduceRuido(Mat frame){
            double e;

            
            if(frame.isContinuous() ){
                if(in<30){
                        if(bandera){
                            f = frame.clone();
                            bandera =false;
                           
                         }else{
                            
                             Mat aux = frame.clone();

                             for(int i=0; i <f.rows();i++){
                                 for(int j=0;j<f.cols();j++){
                                     
                                     double [] data = f.get(i, j);
                                     double [] data2 = aux.get(i, j);
                                    
                                        e = in+1;
                                     data2[0] = ((((in+1)-1)/e) * data[0]) +((1/e)*data2[0]);
                                    
                                    data2[1] = ((((in+1)-1)/e) * data[1]) +((1/e)*data2[1]);
                                     
                                     data2[2] = ((((in+1)-1)/e) * data[2]) +((1/e)*data2[2]);
                                     
                                     aux.put(i, j, data2);
                                 }
                                 
                             }
                             f= aux.clone();
                             
                            }
                                                                   
                }  else if (in==30){
                Imgcodecs.imwrite("sinruido.jpg", f);
                    System.out.println("Imagen sin ruido creada");
            }
                 in++;                
                               
                                                
        }
           
        }
	
        private void reduceRuidoAlineado(Mat frame){
            double e;

             
            if(frame.isContinuous() ){
                if(in<30){
                   
                        if(bandera){
                           
                            f = frame.clone();
                            bandera =false;
                           
                         }else{
                            
                             Mat aux = frame.clone();
                            

                            Mat auxAlin = metodos.alinear2(f, frame); //auxAlin = aux.clone();

                          
                              

                             for(int i=0; i <auxAlin.rows();i++){
                                 for(int j=0;j<auxAlin.cols();j++){
                                     
                                     double [] data = f.get(i, j);
                                     double [] data2 = auxAlin.get(i, j);
                                    
                                        e = in+1;
                                        data2[0] = ((((in+1)-1)/e) * data[0]) +((1/e)*data2[0]);
                                     
                                     /*data2[1] = ((((in+1)-1)/e) * data[1]) +((1/e)*data2[1]);
                                     
                                     data2[2] = ((((in+1)-1)/e) * data[2]) +((1/e)*data2[2]);*/
                                     
                                     auxAlin.put(i, j, data2);
                                 }
                                 
                             }
                             f= auxAlin.clone();
                             
                            }
                        System.out.println("entro:" +in);
                                                                   
                }  else if (in==30){
                Imgcodecs.imwrite("sinruido.jpg", f);
                    System.out.println("Imagen sin ruido creada");
            }
                 
                 in++;                
                               
                                                
        }
        }
	
	private void stopAcquisition()
	{
		if (this.timer != null && !this.timer.isShutdown())
		{
			try
			{
				
				this.timer.shutdown();
				this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
			}
			catch (InterruptedException e)
			{
				
				System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
			}
		}
		
		if (this.capture.isOpened())
		{
			
			this.capture.release();
		}
                
               
	}
        @FXML
        private void img(){
            Imgcodecs.imwrite("camera.jpg", f);
        }
        
        @FXML
        public void abrirArchivo(){
            file = new File("./src/recursos/");
            this.fileChooser = new FileChooser();
            this.fileChooser.setInitialDirectory(file);
        file = this.fileChooser.showOpenDialog(stage);
            
        }
	
	
	private void updateImageView(ImageView view, Image image)
	{
		Utils.onFXThread(view.imageProperty(), image);
	}
	
	
	protected void setClosed()
	{
		this.stopAcquisition();
	}
        
         public void setStage(Stage stage) {
        this.stage = stage;
   }
	
}
