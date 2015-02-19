package Viewer;

import java.awt.Graphics;
import java.awt.Image;

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
		
		if (parent.tglbtnFill.isSelected()){
			if (parent.tglbtnMaintainAspectRatio.isSelected()){
				int width = parent.image.getWidth();
				int height = parent.image.getHeight();
				
				if (width > this.getWidth()){
					width = this.getWidth();
				}
				
				height = (int) (width /parent.ratio);
				
				if (height > this.getHeight()){
					height = this.getHeight();
					width = (int) (height * parent.ratio);
				}
				
				g1.drawImage(parent.image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
				
			}else{
				g1.drawImage(parent.image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
			}
		}else{
			g1.drawImage(parent.image.getScaledInstance((int) parent.bounds.getWidth(), (int) parent.bounds.getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
		}	
	}
}


