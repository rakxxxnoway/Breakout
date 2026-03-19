package engine;

import java.awt.Font;
import java.awt.Image;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import sprites.Brick;
import sprites.GoldenBrick;
import sprites.NormalBrick;
import sprites.StrongBrick;
import sprites.WeakBrick;


public class Construct
{
	public Construct() {}
	
	public Image setImage(String imgName)
	{
		return new ImageIcon(Settings.imgRootPath+imgName).getImage();
	}
	
	public Font setFont(String fontName)
	{
	    try (InputStream is = getClass().getResourceAsStream(fontName))
	    {
	        Font font = Font.createFont(Font.TRUETYPE_FONT, is);
	        return font.deriveFont((float) Settings.FONT_SIZE);
	    } catch (Exception e)
	    {
	        e.printStackTrace();
	        return null;
	        //return new Font("SansSerif", Font.PLAIN, Settings.FONT_SIZE);
	    }
	}
	
	public List<Box> createEntities()
	{
		List<Box> boxes = new ArrayList<Box>();
		Brick b;
		Box box;
		int x;
		int y;
		
		for(int row=0; row < Settings.FOE_ROWS; row++)
		{
			for(int col=0; col < Settings.FOE_COLUMNS; col++)
			{
				x = Settings.FOE_START_X + col * (Settings.FOE_WIDTH + Settings.FOE_GAP);
				y = Settings.FOE_START_Y + row * (Settings.FOE_HEIGHT + Settings.FOE_GAP);
				
				switch(row)
				{
				case 0:
					b = new StrongBrick(x, y, Settings.FOE_WIDTH, Settings.FOE_HEIGHT);
					break;
					
				case 1:
					b = new GoldenBrick(x, y, Settings.FOE_WIDTH, Settings.FOE_HEIGHT);
					break;
					
				case 2:
					b = new NormalBrick(x, y, Settings.FOE_WIDTH, Settings.FOE_HEIGHT);
					break;
					
				default:
					b = new WeakBrick(x, y, Settings.FOE_WIDTH, Settings.FOE_HEIGHT);
					break;
				}
				
				box = new Box(b);
				box.enableCollision(true);
				boxes.add(box);
			}
		}
		
		return boxes;
	}
	
	public void registerMultipleEntities(List<Box> entities)
	{
		for(Box box : entities)
			Collision.registerObject(box);
	}
}
