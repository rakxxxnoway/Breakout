package sprites;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import engine.Key;
import engine.Keyboard;
import engine.Sprite;

public class Player extends Sprite
{
	private int score;
	
	private int playerSpeed;
	
	private Image[] playerModel;
	private int animationTick = 0;
	private int animationSpeed = 10;
	
	public Player(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	
		playerSpeed = 9;
		score = 0;
		
		playerModel = new Image[4];
		playerModel[0] = new ImageIcon("img/player_model_0.png").getImage();
		playerModel[1] = new ImageIcon("img/player_model_1.png").getImage();
		playerModel[2] = new ImageIcon("img/player_model_2.png").getImage();
		playerModel[3] = new ImageIcon("img/player_model_3.png").getImage();
	}
	
	@Override
	public void update(Keyboard keyboard) {	

		if(keyboard.isKeyDown(Key.Left) || keyboard.isKeyDown(Key.A))
			setX(getX() - playerSpeed);
		
		if(keyboard.isKeyDown(Key.Right) || keyboard.isKeyDown(Key.D))
			setX(getX() + playerSpeed);
		
		animationTick++;
	}

	@Override
	public void draw(Graphics2D graphics)
	{
		int frameIndex = (animationTick/animationSpeed) % playerModel.length;
		graphics.drawImage(playerModel[frameIndex], getX(), getY(), getWidth(), getHeight(), null);

		graphics.setColor(Color.black);
		graphics.setFont(new Font("SanSerif", Font.BOLD, 10));
		graphics.drawString("T.O G.P", getX()+55, getY()+getHeight());
		
	}
	
	
	// Methods
	public void increaseScore(int amount) { this.score +=amount; }
	
	
	// getters
	public int getScore() { return this.score; }
	
	
	// setters
}
