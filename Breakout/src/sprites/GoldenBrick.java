package sprites;

import java.awt.Color;
import java.awt.Graphics2D;

public class GoldenBrick extends Brick 
{
	
	public GoldenBrick(int x, int y, int w, int h) {
		super(x, y, w, h, 1, 3);
	}
	
    @Override
    public void draw(Graphics2D graphics)
    {
        graphics.setColor(Color.ORANGE);
        graphics.fillRect(getX(), getY(), getWidth(), getHeight());
        
        graphics.setColor(Color.YELLOW);
        graphics.drawRect(getX(), getY(), getWidth(), getHeight());
    }
}

