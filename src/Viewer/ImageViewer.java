package Viewer;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

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
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ImageViewer extends JFrame{
	
	BufferedImage  image;
	Rectangle scaleBounds;
	double ratio;
	JToggleButton tglbtnFit;
	JToggleButton tglbtnMaintainAspectRatio;
	private Point lastLocation = null;
	public boolean fast;
	private Display display;
	ImageManager images = new ImageManager();
	private JButton btnNext;
	private JButton btnPrevious;
	private JLabel lblOf;

	public ImageViewer() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){};
		
		setSize(643,584);
		
		try {
			setImage(ImageIO.read(new URL("http://upload.wikimedia.org/wikipedia/commons/0/07/Emperor_Penguin_Manchot_empereur.jpg").openStream()));
			
			say(scaleBounds);
			say(ratio);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		display = new Display(this);
		getContentPane().add(display, BorderLayout.CENTER);
		
		JPanel toolbar = new JPanel();
		getContentPane().add(toolbar, BorderLayout.NORTH);
		
		JButton btnOpenImage = new JButton("Open File(s)");
		btnOpenImage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					
					File file = chooser.getSelectedFile();
					say(file);
					
					images.clear();
					images.addImages(file);
					setImage(images.getFirstImage());
					updateNextAndPrevious();
					repaint();
				}
			}
		});
		
		JButton btnOpenLinkImage = new JButton("Open Link");
		btnOpenLinkImage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String link = JOptionPane.showInputDialog("Image link: (link must end in: .jpg, .png, .gif, .jpeg, .bmp, .wbmp)", "http://upload.wikimedia.org/wikipedia/commons/0/07/Emperor_Penguin_Manchot_empereur.jpg");
				
				if (link != null){
					try {
						setImage(ImageIO.read(new URL(link)));
						images.clear();
						updateNextAndPrevious();
						lblOf.setText("1 of 1" );
						repaint();
						
					} catch (IOException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error Retrieving image");
					}
				}
				
			}
		});
		toolbar.add(btnOpenLinkImage);
		toolbar.add(btnOpenImage);
		
		tglbtnFit = new JToggleButton("Fit");
		toolbar.add(tglbtnFit);
		tglbtnFit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tglbtnMaintainAspectRatio.setEnabled(tglbtnFit.isSelected());
				repaint();
			}
		});
		
		tglbtnMaintainAspectRatio = new JToggleButton("Maintain Aspect Ratio");
		toolbar.add(tglbtnMaintainAspectRatio);
		
		btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setImage(images.previousImage());
				updateNextAndPrevious();
				repaint();
			}
		});
		toolbar.add(btnPrevious);
		
		btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setImage(images.nextImage());
				updateNextAndPrevious();
				repaint();
			}
		});
		
		updateNextAndPrevious();
		
		toolbar.add(btnNext);
		
		lblOf = new JLabel("1 of 1");
		toolbar.add(lblOf);
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
				up(20);
			}
		});
		
		JButton btnDown = new JButton("Down");
		movebar.add(btnDown);
		btnDown.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				down(20);
			}
		});
		
		JButton btnLeft = new JButton("Left");
		movebar.add(btnLeft);
		btnLeft.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				left(20);
			}
		});
		
		JButton btnRight = new JButton("Right");
		movebar.add(btnRight);
		btnRight.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				right(20);
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
				lastLocation = e.getPoint();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				
				int xDist, yDist, scale = 1;
				
				do{
					xDist = lastLocation.x - e.getX();
					yDist = lastLocation.y - e.getY();
	
					if (xDist >= scale) {
						right(scale);
						lastLocation.setLocation(lastLocation.x - scale, lastLocation.y);
					} else if (xDist <= -scale) {
						left(scale);
						lastLocation.setLocation(lastLocation.x + scale, lastLocation.y);
					}
	
					if (yDist >= scale) {
						down(scale);
						lastLocation.setLocation(lastLocation.x, lastLocation.y - scale);
					} else if (yDist <= -scale) {
						up(scale);
						lastLocation.setLocation(lastLocation.x, lastLocation.y + scale);
					}
				}while(xDist >= scale || xDist <= -scale || yDist >= scale || yDist <= -scale);
			}
				
		});
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				fast = false;
				repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				fast = true;
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		
		setVisible(true);
	}
	
	
	private void updateNextAndPrevious(){
		if (images.getCurrentImageNum() >= images.getImageCount()-1)
			btnNext.setEnabled(false);
		else
			btnNext.setEnabled(true);
		if (images.getCurrentImageNum() <= 0)
			btnPrevious.setEnabled(false);
		else
			btnPrevious.setEnabled(true);
		
		if (lblOf != null) lblOf.setText(images.getCurrentImageNum()+1 + " of " + images.getImageCount());
	}
	
	private void zoomIn(){
		scaleBounds.setBounds(scaleBounds.x, scaleBounds.y, scaleBounds.width+40, scaleBounds.height+(int) (40 / ratio));
		//display.updateZoomBounds(scaleBounds);
		repaint();
	}
	
	private void zoomOut(){
		scaleBounds.setBounds(scaleBounds.x, scaleBounds.y, scaleBounds.width-40, scaleBounds.height- (int) (40 / ratio));
		//display.updateZoomBounds(scaleBounds);
		repaint();
	}
	
	private void left(int scale){
		scaleBounds.setLocation(scaleBounds.x + scale, scaleBounds.y);
		repaint();
	}
	
	private void right(int scale){
		scaleBounds.setLocation(scaleBounds.x - scale, scaleBounds.y);
		repaint();
	}
	
	private void up(int scale){
		scaleBounds.setLocation(scaleBounds.x, scaleBounds.y + scale);
		repaint();
	}
	
	private void down(int scale){
		scaleBounds.setLocation(scaleBounds.x, scaleBounds.y - scale);
		repaint();
	}
	
	private void setImage(BufferedImage image){
		this.image = image;
		ratio = (double) image.getWidth() / image.getHeight();
		scaleBounds = new Rectangle(0,0, image.getWidth(), image.getHeight());
	}

	public void say(Object s) {
		System.out.println(s);
	}

	public static void main(String[] args) {
		new ImageViewer();
	}

}
