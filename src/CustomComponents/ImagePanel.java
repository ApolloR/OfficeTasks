package CustomComponents;



import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.JComponent;




public class ImagePanel extends JComponent {
	private Image image;
	
	public ImagePanel(Image image){
		this.image = image;		
		
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);		
		g.drawImage(image, -100, -160, (ImageObserver) this);
		
	}
}
