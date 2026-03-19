package game;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import engine.*;
import sprites.*; 

public class Game
{	
	//++++ UI ++++
	private Font gameFont;
	private Color fontColor;
	private Construct game;
	private Image gameOverImage;
	private Image winnerImage;
	
	
	private Sound music;
	private Sound sfx;
	

    private Player plr;
    private Ball ball;

    
    private final Box plrBox;
    private final Box ballBox;
	private List<Box> foes;
    
    
	public Game(GameBoard board)
	{
		game = new Construct();
		foes = new ArrayList<>();
		
		gameOverImage = game.setImage("gameover.png");
		winnerImage = game.setImage("win.png");
		
		music = new Sound();
		sfx = new Sound();
		music.playSound("ambient.wav", true);
		
		// font
		gameFont = game.setFont("/ttf/PressStart2P.ttf");
		fontColor = Color.WHITE;
		
		//==== sprites ====
        plr = new Player(
    		Settings.PLAYER_START_X,
    		Settings.PLAYER_START_Y,
    		Settings.playerWidth,
    		Settings.PLAYER_HEIGHT
        );
        
        ball = new Ball(
    		Settings.BALL_START_X,
    		Settings.BALL_START_Y,
    		Settings.BALL_SQR,
    		Settings.BALL_SQR
        );
        
        //==== boxes ====
        plrBox = new Box(plr);
        ballBox = new Box(ball);
        
        foes = game.createEntities();
        game.registerMultipleEntities(foes);
        
        
        //==== box configuration ====
        plrBox.enableCollision(true);
        
        ballBox.enableCollision(true);
        ballBox.setVelocity(Settings.BALL_SPEED_X, Settings.BALL_SPEED_Y);
        
        //==== registering all collision objects ==== 		(Boxes only)
        Collision.registerObject(ballBox);
        Collision.registerObject(plrBox);
	}
	
	
	//methods
	public boolean isGameOver() { return Settings.gameOver; }
	
	public void audioBreak()
	{
	    if (music != null)
	    	music.stopSound();
	    
	    if (sfx != null)
	    	sfx.stopSound();
	    
	    if(!Collision.getRegisteredObjects().isEmpty())
	    	Collision.resetObjects();
	}

	public void update(Keyboard keyboard)
	{
		if(keyboard.isKeyDown(Key.Escape))
			System.out.println(1);
		
		if(Settings.gameOver)
			return;
		
		if(ball.getHP() <= 0)
		{
			Settings.gameOver = true;
			
			music.stopSound();
			sfx.playSound("gameover.wav", false);
		}
		
		plr.update(keyboard);
        plrBox.lockToWorld();
        
        List<Box> hits = ballBox.physics(Collision.getRegisteredObjects());

        for (Box h : hits)
        {
            if (h.getOwner() instanceof Brick brick)
            {
                plr.increaseScore(brick.hit());
                sfx.playSound("hit1.wav", false);

                if (brick.isDestroyed())
                {
                    int brickID = foes.indexOf(h);
                    if (brickID >= 0)
                    {
                        Collision.unregisterObject(foes.get(brickID));

                        foes.remove(brickID);
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
	
	
        if (foes.isEmpty())
        {
        	Settings.win = true;

        	music.stopSound();
        	sfx.playSound("win.wav", false);
        }
	}
	
	
	public void draw(Graphics2D graphics)
	{
		//++++ UI ++++
		graphics.setFont(gameFont);
		graphics.setColor(fontColor);
		graphics.drawString("Score: %d".formatted(plr.getScore()), Settings.TEXT_X, Settings.SCORE_Y);
		graphics.drawString("HP: %d".formatted(ball.getHP()), Settings.TEXT_X, Settings.HP_Y);
		
		if(Settings.gameOver)
		{
			graphics.drawImage(gameOverImage,
		        Settings.GAMEOVER_X,
		        Settings.GAMEOVER_Y,
		        Settings.GAMEOVER_SCALE_X,
		        Settings.GAMEOVER_SCALE_Y,
		        null
			);
			
			String scoreText = "FINAL SCORE: " + plr.getScore();
			int scoreX = Settings.startX(600);
			int scoreY = Settings.startY(400);
		    graphics.drawString(scoreText, scoreX, scoreY);
			
			return;
		}
		
		if(Settings.win)
		{
			graphics.drawImage(winnerImage,
				Settings.GAMEOVER_X,
		        Settings.GAMEOVER_Y,
		        Settings.GAMEOVER_SCALE_X,
		        Settings.GAMEOVER_SCALE_Y,
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
		
		for (Box box : foes)
		    box.getOwner().draw(graphics);
		
	}
}
