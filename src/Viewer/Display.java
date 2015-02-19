package Viewer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Display extends JPanel{

	private ImageViewer parent;
	
	public Display(ImageViewer parent) {
		this.parent = parent;
	}

	@Override
	protected void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		
		double ratio = parent.ratio;
		BufferedImage image = parent.image;
		
		if (parent.tglbtnFit.isSelected()){
			
			if (parent.tglbtnMaintainAspectRatio.isSelected()){
//				int width = image.getWidth();
//				int height = image.getHeight();
//				
//				if (width > this.getWidth()){
//					width = this.getWidth();
//				}
//				
//				height = (int) (width / ratio);
//				
//				if (height > this.getHeight()){
//					height = this.getHeight();
//					width = (int) (height * ratio);
//				}
//				g1.drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
				
				Bounds scale = scaleBounds(image.getWidth(), image.getHeight(), ratio);
				
				g1.drawImage(image.getScaledInstance(scale.width, scale.height, Image.SCALE_SMOOTH), 0, 0, null);
				
			}else{
				g1.drawImage(image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
			}
		}else{
			Rectangle scaleBounds = parent.scaleBounds;
			Image scaleImage = parent.image.getScaledInstance(scaleBounds.width, scaleBounds.height, Image.SCALE_SMOOTH);
			g1.drawImage(scaleImage, scaleBounds.x, scaleBounds.y, null);
		}
	}
	
	
	private Bounds scaleBounds (int width, int height, double ratio){
		
		if (width > this.getWidth()){
			width = this.getWidth();
		}
		
		height = (int) (width / ratio);
		
		if (height > this.getHeight()){
			height = this.getHeight();
			width = (int) (height * ratio);
		}
		
		return new Bounds(width, height);
	}
	
}

class Bounds{
	
	int width, height;
	
	public Bounds(int width, int height){
		this.width = width;
		this.height = height;
	}
	
}




