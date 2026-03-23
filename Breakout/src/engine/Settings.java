package engine;

import java.util.Random;

public class Settings
{
	// Engine
	public static final int WINDOW_WIDTH 			= 1024;
	public static final int WINDOW_HEIGHT 			= 768;
	
	public static final int FPS 					= 60;
	
	public static final float FONT_SIZE 			= 24f;
	public static final float FONT_SIZE_OTHER 		= 48f;
	
	public static final String MENU 				= "menu";
	public static final String GAME 				= "game";
	public static final String STAT 				= "stats";
	
	
	// Audio
	public static final int SFX_PERCENT 			= 35;
	public static final int MUSIC_PERCENT 			= 100;
	public static final int SFX_MENU 				= 20;
	
	
	// UI
	public static final int LAUNCHER_WIDTH 			= 800;
	public static final int LAUNCHER_HEIGHT 		= 600;
	
	public static final int LAUNCHER_BUTTON_WIDTH 	= 340;
	public static final int LAUNCHER_BUTTON_HEIGHT 	= 77;
	
	public static final int TEXT_X					= 20;
	public static final int SCORE_Y					= 40;
	public static final int HP_Y 					= 70;
	
	public static final int GAMEOVER_SCALE_X 		= 500;
	public static final int GAMEOVER_SCALE_Y 		= 250;
	
	public static final int GAMEOVER_X 				= startX(GAMEOVER_SCALE_X);
	public static final int GAMEOVER_Y 				= startY(GAMEOVER_SCALE_Y);
	
	
	// Tweak
	public static final String imgRootPath 			= "img/";
	public static final String musicRootPath 		= "music/";
	public static final String ttfRootPath 			= "ttf/";
	
	
	// Player
	public static 		int playerWidth 			= 145;
	public static final int PLAYER_HEIGHT 			= 15;
	public static final int PLAYER_START_Y 			= 730;
	public static final int PLAYER_START_X 			= startX(playerWidth);
	
	// Ball
	public static final int BALL_SQR 				= 25;
	public static final int BALL_START_X 			= randomBallXPos();
	public static final int BALL_START_Y 			= startY(BALL_SQR);
	public static final int BALL_SPEED_X 			= 3;
	public static final int BALL_SPEED_Y 			= 12;
	
	
	// Foes
	public static final int FOE_SQR 				= 50;
	public static final int FOE_WIDTH 				= 70;
	public static final int FOE_HEIGHT 				= 25;
	public static final int FOE_START_X 			= 60;
	public static final int FOE_START_Y 			= 80;
	public static final int FOE_COLUMNS 			= 11;
	public static final int FOE_ROWS 				= 6;
	public static final int FOE_GAP 				= 12;
	
	
	// Game
	public static boolean gameOver 					= false;
	public static boolean win 						= false;
	public static boolean scoreSaved				= false;
	
	
	// Methods
	public static int startX(int objLen)
	{
		return (WINDOW_WIDTH - objLen) / 2;
	}
	// this two calculates the center of the screen
	public static int startY(int objHei)
	{
		return (WINDOW_HEIGHT - objHei) / 2;
	}
	
	public static int randomBallXPos()
	{
		Random rand = new Random();
		
		int bStartX = startX(BALL_SQR);
		int bStartVec = 2;
		
		return rand.nextInt( (bStartX+bStartVec) - (bStartX-bStartVec) +1 ) + (bStartX - bStartVec);
	}
}
