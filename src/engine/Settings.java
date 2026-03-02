package engine;

public class Settings
{
	// Engine
	public static final int WINDOW_WIDTH = 1024;
	public static final int WINDOW_HEIGHT = 768;
	
	public static final int FPS = 60;
	
	public static final float FONT_SIZE = 24f;
	public static final float FONT_SIZE_OTHER = 48f;
	
	
	// Tweak
	public static final String imgRootPath = "img/";
	public static final String musicRootPath = "music/";
	public static final String ttfRootPath = "ttf/";
	
	
	// Player
	public static int playerWidth = 145;
	public static final int playerHeight = 15;
	
	
	// Ball
	public static final int ballSqr = 25;
	
	
	// Foes
	public static final int foeSqr = 50;
	
	
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
}
