package Viewer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageManager {

	private FileFilter filter;
	private String extensions[] = {".jpg", ".png", ".gif", ".jpeg", ".bmp", ".wbmp"};
	private List<File> photos = new ArrayList<File>();
	private int imageNum = 0;

	public ImageManager() {
		
		filter = new FileFilter(){

			@Override
			public boolean accept(File file) {
				
				for (String extension : extensions)
					if (file.getName().toLowerCase().endsWith(extension))
						return true;
				
				return false;
			}
		};
		
	}

	public void addImages(File file) {
		if (file.isDirectory()){
			for (File f : file.listFiles(filter))
				photos.add(f);
		}
		else if (file.isFile()){
			if (filter.accept(file))
				photos.add(file);
		}
		
		System.out.println(photos);
	}
	
	public BufferedImage nextImage(){
		if (imageNum < photos.size())
			imageNum ++;
		try {
			return ImageIO.read(photos.get(imageNum));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public BufferedImage previousImage(){
		if (imageNum > 0)
			imageNum --;
		try {
			return ImageIO.read(photos.get(imageNum));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public BufferedImage getFirstImage() {
		try {
			return ImageIO.read(photos.get(0));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void clear() {
		photos.clear();
		imageNum = 0;
	}
	
	public int getImageCount(){
		return photos.size();
	}
	
	public int getCurrentImageNum(){
		return imageNum;
	}
}
