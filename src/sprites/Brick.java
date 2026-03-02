package sprites;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.Keyboard;
import engine.Sprite;

public class Brick extends Sprite
{
	protected int HP;
	protected int points;
	

	public Brick(int x, int y, int w, int h, int HP, int points)
	{
		super(x, y, w, h);
		
		this.HP = HP;
		
		this.points = points;
	}

	public int hit()
	{
		HP--;
		return points;
	}

	public boolean isDestroyed()
	{
		return HP <= 0;
	}
		
	
	@Override
	public void update(Keyboard keyboard) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D graphics)
	{
	    if (HP >= 3)
	    	graphics.setColor(Color.RED);
	    
	    else if (HP == 2) 
	    	graphics.setColor(Color.ORANGE);
	    
        else 
        	graphics.setColor(Color.MAGENTA);
	    
	    graphics.fillRect(getX(), getY(), getWidth(), getHeight());
        
	    graphics.setColor(Color.WHITE);
	    graphics.drawRect(getX(), getY(), getWidth(), getHeight());
	}
}


