package Rummikub;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StoneManager {

	private List<BufferedImage> imageList;
	private String fileLocation;
	public StoneManager(String fileLocation) 
	{
		imageList = new ArrayList<BufferedImage>();
		this.fileLocation = fileLocation;
		load();
	}

	private void load() {
		BufferedImage img = null;
		try 
		{
			img = ImageIO.read(new File(fileLocation));
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		int tilesAcross = 14;
		int tilesDown = 4;
		int width = img.getWidth() / tilesAcross;
		int height = img.getHeight() / tilesDown;
		
		for (int i = 0; i < tilesDown; i++) 
		{
			for (int j = 0; j < tilesAcross; j++) 
			{
				imageList.add(img.getSubimage(j * width, i * height, width, height));
			}
		}
	}

	public List<BufferedImage> getImageList() {
		return imageList;
	}
}
