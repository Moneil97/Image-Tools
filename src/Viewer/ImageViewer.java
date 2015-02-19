package Viewer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JToggleButton;

@SuppressWarnings("serial")
public class ImageViewer extends JFrame{
	
	BufferedImage  image;
	 Rectangle bounds;
	double ratio;
	JToggleButton tglbtnFill;
	JToggleButton tglbtnMaintainAspectRatio;

	public ImageViewer() {
		
		setSize(510,536);
		
		try {
			image = ImageIO.read(new URL("http://upload.wikimedia.org/wikipedia/commons/0/07/Emperor_Penguin_Manchot_empereur.jpg").openStream());
			ratio = (double) image.getWidth() / image.getHeight();
			bounds = new Rectangle(0,0, image.getWidth(), image.getHeight());
			say(bounds);
			say(ratio);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		Display display = new Display(this);
		getContentPane().add(display, BorderLayout.CENTER);
		
		JPanel toolbar = new JPanel();
		getContentPane().add(toolbar, BorderLayout.NORTH);
		
		JButton btnOpenImage = new JButton("Open Image");
		toolbar.add(btnOpenImage);
		
		tglbtnFill = new JToggleButton("Fill");
		toolbar.add(tglbtnFill);
		tglbtnFill.setSelected(true);
		
		tglbtnMaintainAspectRatio = new JToggleButton("Maintain Aspect Ratio");
		tglbtnMaintainAspectRatio.setSelected(true);
		toolbar.add(tglbtnMaintainAspectRatio);
		
		JPanel movebar = new JPanel();
		getContentPane().add(movebar, BorderLayout.SOUTH);
		
		JButton btnUp = new JButton("Up");
		movebar.add(btnUp);
		
		JButton btnDown = new JButton("Down");
		movebar.add(btnDown);
		
		JButton btnLeft = new JButton("Left");
		movebar.add(btnLeft);
		
		JButton btnRight = new JButton("Right");
		movebar.add(btnRight);
		
		JButton btnZoomIn = new JButton("Zoom In");
		movebar.add(btnZoomIn);
		
		JButton btnZoomOut = new JButton("Zoom Out");
		movebar.add(btnZoomOut);
		setVisible(true);
	}

	private void say(Object s) {
		System.out.println(s);
	}

	public static void main(String[] args) {
		new ImageViewer();
	}

}
