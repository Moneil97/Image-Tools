package Viewer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JToggleButton;

@SuppressWarnings("serial")
public class ImageViewer extends JFrame{
	
	BufferedImage  image;
	Rectangle scaleBounds;		//Used to setup how the image will be displayed
	double ratio;
	JToggleButton tglbtnFit;
	JToggleButton tglbtnMaintainAspectRatio;
	private boolean mouseHeld;
	private Point start = null;

	public ImageViewer() {
		
		setSize(510,536);
		
		try {
			image = ImageIO.read(new URL("http://upload.wikimedia.org/wikipedia/commons/0/07/Emperor_Penguin_Manchot_empereur.jpg").openStream());
			ratio = (double) image.getWidth() / image.getHeight();
			scaleBounds = new Rectangle(0,0, image.getWidth(), image.getHeight());
			say(scaleBounds);
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
		
		tglbtnFit = new JToggleButton("Fit");
		toolbar.add(tglbtnFit);
		tglbtnFit.setSelected(true);
		tglbtnFit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tglbtnMaintainAspectRatio.setEnabled(tglbtnFit.isSelected());
				repaint();
			}
		});
		
		tglbtnMaintainAspectRatio = new JToggleButton("Maintain Aspect Ratio");
		tglbtnMaintainAspectRatio.setSelected(true);
		toolbar.add(tglbtnMaintainAspectRatio);
		tglbtnMaintainAspectRatio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
		
		JPanel movebar = new JPanel();
		getContentPane().add(movebar, BorderLayout.SOUTH);
		
		JButton btnUp = new JButton("Up");
		movebar.add(btnUp);
		btnUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				up();
			}
		});
		
		JButton btnDown = new JButton("Down");
		movebar.add(btnDown);
		btnDown.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				down();
			}
		});
		
		JButton btnLeft = new JButton("Left");
		movebar.add(btnLeft);
		btnLeft.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				left();
			}
		});
		
		JButton btnRight = new JButton("Right");
		movebar.add(btnRight);
		btnRight.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				right();
			}
		});
		
		
		JButton btnZoomIn = new JButton("Zoom In");
		movebar.add(btnZoomIn);
		btnZoomIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				zoomIn();
			}
		});
		
		JButton btnZoomOut = new JButton("Zoom Out");
		movebar.add(btnZoomOut);
		btnZoomOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				zoomOut();
			}
		});
		
		
		this.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				
				int scrolls = e.getWheelRotation();
				
				if (scrolls > 0)
					for (int i=0; i < scrolls; i++)
						zoomOut();
				else if (scrolls < 0)
					for (int i=0; i < scrolls*-1; i++)
						zoomIn();
			}
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				start = e.getPoint();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {

				int xDist = start.x - e.getX();
				int yDist = start.y - e.getY();

				if (xDist >= 20) {
					say("right");
					right();
					start.setLocation(start.x - 20, start.y);
				} else if (xDist <= -20) {
					say("left");
					left();
					start.setLocation(start.x + 20, start.y);
				}

				if (yDist >= 20) {
					say("down");
					down();
					start.setLocation(start.x, start.y - 20);
				} else if (yDist <= -20) {
					say("up");
					up();
					start.setLocation(start.x, start.y + 20);
				}
			}
				
		});
		
		setVisible(true);
	}
	
	
	
	private void zoomIn(){
		scaleBounds.setBounds(scaleBounds.x, scaleBounds.y, scaleBounds.width+20, scaleBounds.height+20);
		repaint();
	}
	
	private void zoomOut(){
		scaleBounds.setBounds(scaleBounds.x, scaleBounds.y, scaleBounds.width-20, scaleBounds.height-20);
		repaint();
	}
	
	private void left(){
		scaleBounds.setLocation(scaleBounds.x + 20, scaleBounds.y);
		repaint();
	}
	
	private void right(){
		scaleBounds.setLocation(scaleBounds.x - 20, scaleBounds.y);
		repaint();
	}
	
	private void up(){
		scaleBounds.setLocation(scaleBounds.x, scaleBounds.y + 20);
		repaint();
	}
	
	private void down(){
		scaleBounds.setLocation(scaleBounds.x, scaleBounds.y - 20);
		repaint();
	}

	private void say(Object s) {
		System.out.println(s);
	}

	public static void main(String[] args) {
		new ImageViewer();
	}

}
