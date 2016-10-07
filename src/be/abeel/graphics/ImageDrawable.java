package be.abeel.graphics;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

public class ImageDrawable implements Drawable{

	private Image img;

	public ImageDrawable(Image img){
		this.img=img;
	}
	
	@Override
	public void draw(Graphics2D g, Rectangle2D rec) {
		g.drawImage(img,(int) rec.getX(), (int)rec.getY(),(int)( rec.getX()+rec.getWidth()),(int)( rec.getY()+rec.getHeight()), 0, 0, img.getWidth(null), img.getHeight(null), null);
		
	}

}
