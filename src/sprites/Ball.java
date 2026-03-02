package sprites;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import engine.Keyboard;
import engine.Sprite;

public class Ball extends Sprite
{
	private Image ballModel;
	
	private int hp;
	
	
	public Ball(int x, int y, int width, int height) {
		super(x, y, width, height);
	
		hp = 3;
		
		ballModel = new ImageIcon("img/ball_model.png").getImage();
		
	}
	

	// overides
	@Override
	public void update(Keyboard keyboard) {}

	@Override
	public void draw(Graphics2D graphics) {
		
		graphics.drawImage(ballModel, getX(), getY(), getWidth(), getHeight(), null);
		
	}
	
	
	// Methods
	public void damage() { this.hp--; }
	
	
	// getters
	public int getHP() { return this.hp; }
	
	
	// setters
}
