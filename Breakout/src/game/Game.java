package game;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

import engine.*;
import sprites.*; 

public class Game
{	
	// test commit
	//++++ UI ++++
	private Font gameFont;
	private Color fontColor;
	private int textX;
	private int scoreY;
	private int hpY;
	
	private Image gameOverImage;
	private Image winnerImage;
	private boolean gameOver;
	private boolean win;
	private int goX;
	private int goY;
	private int goScaleX;
	private int goScaleY;
	
	private Sound music;
	private Sound sfx;
	
	
    private Player plr;
    private Ball ball;

    private final Box plrBox;
    private final Box ballBox;
    
	private ArrayList<Brick> bricks = new ArrayList<>();
	private ArrayList<Box> brickBoxes = new ArrayList<>();
	
	private int bw;
	private int bh;
	private int bStartX;
	private int bStartY;
	private int bColoums; 
	private int bRows;
	private int bGap;
    
    
	public Game(GameBoard board)
	{
		Random rand = new Random();
		
		gameOver = false;
		win = false;
		
		gameOverImage = new ImageIcon(Settings.imgRootPath+"gameover.png").getImage();
		winnerImage = new ImageIcon(Settings.imgRootPath+"winner1.png").getImage();
		
		music = new Sound();
		sfx = new Sound();
		music.playSound("ambient.wav", true);
		
		
		// font
		try
		{
			InputStream iStream = getClass().getResourceAsStream("/ttf/PressStart2P.ttf");
			Font baseFont = Font.createFont(Font.TRUETYPE_FONT, iStream);
			
			gameFont = baseFont.deriveFont(Settings.FONT_SIZE);
			fontColor = Color.WHITE;
			
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(baseFont);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		//==== possitioning ====
		//++++ UI ++++
		textX = 20;
		scoreY = 40;
		hpY = 70;
		
		goScaleX = 500;
		goScaleY = 250;
		goX = Settings.startX(goScaleX);
		goY = Settings.startY(goScaleY);
		
		// player
		int plrWidth = Settings.playerWidth;
		int plrStartX = Settings.startX(plrWidth);
		int plrStartY = 730;
		
		// ball
		int bStartX = Settings.startX(Settings.ballSqr);
		int bStartVec = 2;
		int ballStartX = rand.nextInt( (bStartX+bStartVec) - (bStartX-bStartVec) +1 ) + (bStartX - bStartVec);
		int ballStartY = Settings.startY(Settings.ballSqr);
		int ballXSpeed = 3;
		int ballYSpeed = 6;
		
		// bricks
		bw = 70;
		bh = 25;
		bStartX = 60;
		bStartY = 80;
		bColoums = 11;
		bRows = 6;
		bGap = 12;
		
		//==== sprites ====
        plr = new Player(
    		plrStartX,
    		plrStartY,
    		Settings.playerWidth,
    		Settings.playerHeight
        );
        
        ball = new Ball(
    		ballStartX,
    		ballStartY,
    		Settings.ballSqr,
    		Settings.ballSqr
        );
        
        // foes
        for (int r = 0; r < bRows; r++)
        {
            for (int c = 0; c < bColoums; c++)
            {
                int x = bStartX + c * (bw + bGap);
                int y = bStartY + r * (bh + bGap);

                Brick b;
                if (r == 0) 
                	b = new StrongBrick(x, y, bw, bh);
                
                else if (r == 1)
                    b = new GoldenBrick(x, y, bw, bh);
                
                else if (r == 2)
                	b = new NormalBrick(x, y, bw, bh);
                
                else
                	b = new WeakBrick(x, y, bw, bh);

                bricks.add(b);

                Box bb = new Box(b);
                bb.enableCollision(true);
                brickBoxes.add(bb);
                Collision.registerObject(bb);
            }
        }

        
        //==== boxes ====
        plrBox = new Box(plr);
        ballBox = new Box(ball);
        
        
        //==== box configuration ====
        plrBox.enableCollision(true);
        
        ballBox.enableCollision(true);
        ballBox.setVelocity(ballXSpeed, ballYSpeed);
        
        
        //==== registering all collision objects ==== 			(Boxes only)
        Collision.registerObject(ballBox);
        Collision.registerObject(plrBox);
	}
	
	
	//methods
	public boolean isGameOver() { return gameOver; }

	public void update(Keyboard keyboard)
	{	
		
		if(win)
			return;
		
		if(ball.getHP() <= 0)
		{
			gameOver = true;
			
			music.stopSound();
			sfx.playSound("gameover.wav", false);
		}
		
		plr.update(keyboard);
        plrBox.lockToWorld();
        
        ArrayList<Box> hits = ballBox.physics(Collision.getRegisteredObjects());

        for (Box h : hits)
        {
            if (h.getOwner() instanceof Brick brick)
            {
                plr.increaseScore(brick.hit());
                sfx.playSound("hit1.wav", false);

                if (brick.isDestroyed())
                {
                    int brickID = bricks.indexOf(brick);
                    if (brickID >= 0)
                    {
                        Collision.unregisterObject(brickBoxes.get(brickID));

                        brickBoxes.remove(brickID);
                        bricks.remove(brickID);
                    }
                }
                break;
            }
        }
        
        if(ballBox.didFall())
        {
        	ball.damage();
        	sfx.playSound("damage1.wav", false);
        	ballBox.backOn();
        }
	
	
        if (bricks.isEmpty())
        {
        	win = true;

        	music.stopSound();
        	sfx.playSound("win.wav", false);
        }
	}
	public void draw(Graphics2D graphics)
	{
		//++++ UI ++++
		graphics.setFont(gameFont);
		graphics.setColor(fontColor);
		graphics.drawString("Score: %d".formatted(plr.getScore()), textX, scoreY);
		graphics.drawString("HP: %d".formatted(ball.getHP()), textX, hpY);
		
		if(gameOver)
		{
			graphics.drawImage(gameOverImage,
		        goX,
		        goY,
		        goScaleX,
		        goScaleY,
		        null
			);
			
			String scoreText = "FINAL SCORE: " + plr.getScore();
			int scoreX = Settings.startX(600);
			int scoreY = Settings.startY(400);
		    graphics.drawString(scoreText, scoreX, scoreY);
			
			return;
		}
		
		if(win)
		{
			graphics.drawImage(winnerImage,
		        goX,
		        goY,
		        goScaleX,
		        goScaleY,
		        null
			);
			
		    Font bigFont = gameFont.deriveFont(Settings.FONT_SIZE_OTHER);
		    graphics.setFont(bigFont);
		    graphics.setColor(Color.YELLOW);

		    String winText = "YOU WIN!";
		    int wScaleX = 600;
		    int wScaleY = 400;
		    int winX = Settings.startX(wScaleX);
		    int winY = Settings.startY(wScaleY);

		    graphics.drawString(winText, winX, winY);

		    Font scoreFont = gameFont.deriveFont(32f);
		    graphics.setFont(scoreFont);
		    graphics.setColor(Color.WHITE);

		    String scoreText = "FINAL SCORE: " + plr.getScore();
		    int scoreX = Settings.startX(wScaleX);
		    int scoreY = winY + wScaleY;

		    graphics.drawString(scoreText, scoreX, scoreY);
			return;
		}

		
		plr.draw(graphics);
		ball.draw(graphics);
		
		for (Brick b : bricks)
		{
		    b.draw(graphics);
		}
	}
}
