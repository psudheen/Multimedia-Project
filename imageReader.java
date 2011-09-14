
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;



public class imageReader {

  
   public static void main(String[] args) {
   	

	String fileName = args[0];
   	int width = Integer.parseInt(args[1]);
	int height = Integer.parseInt(args[2]);
	
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    try {
	    File file = new File(fileName);
	    InputStream is = new FileInputStream(file);

	    long len = file.length();
	    byte[] bytes = new byte[(int)len];
	    
	    int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
    
    		
    	int ind = 0;
		for(int y = 0; y < height; y++){
	
			for(int x = 0; x < width; x++){
		 
				byte a = 0;
				byte r = bytes[ind];
				byte g = bytes[ind+height*width];
				byte b = bytes[ind+height*width*2]; 
				
				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
				//int pix = ((a << 24) + (r << 16) + (g << 8) + b);
				img.setRGB(x,y,pix);
				ind++;
			}
		}
		
		
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    // Use a label to display the image
    JFrame frame = new JFrame();
    JLabel label = new JLabel(new ImageIcon(img));
    frame.getContentPane().add(label, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);

   }
    
   public void rgbToYuv(String fileName, int width, int height)
    {
        //Add logic to read the image and convert to YUV space
        // This is conversion of analog signal to digital signal
        
        BufferedImage myRGBimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        try {
            File file = new File(fileName);
            InputStream is = new FileInputStream(file);
            
            long len = file.length();
            byte[] bytes = new byte[(int)len];
            // float array to store YUV values
            float[] yuv = new float[(int)len];
            
                        
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
            
    		
            int ind = 0;
            for(int y = 0; y < height; y++){
                
                for(int x = 0; x < width; x++){
                    //Convert byte valued r,g,b to float values
                    
                    /*---------------------------------------------------------
                     Add logic to convert the RGB image to YUV image using below 
                     matrix multiplication
                     y = 0.299r+0.587
                     
                     
                     ---------------------------------------------------------*/
                    
                    

                    float r = bytes[ind];
                    float g = bytes[ind+height*width];
                    float b = bytes[ind+height*width*2]; 
                    
                    
                    yuv[ind] = 0.299*r + 0.587*g + 0.114*b;
                    yuv[ind+height*width] = -0.147*r - 0.287*g + 0.436*b;
                    yuv[ind+height*width*2] = 0.615*r - 0.515*g + 0.312*b;
                    
                    int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
                    //int pix = ((a << 24) + (r << 16) + (g << 8) + b);
                    img.setRGB(x,y,pix);
                    ind++;
                    
                    
                }
            }
            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        

                
    }// end of rgbToYuv()
    
  
}//end of class

