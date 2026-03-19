package engine;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import game.Game;

public class GameBoard extends JComponent
{
	private Timer timer;
	private Game game;
	private Keyboard keyboard;
	private boolean awaitStart;

	private Image bg;

	public GameBoard() {
		awaitStart = true;
		keyboard = new Keyboard();
		game = new Game(this);
		setOpaque(true);
	}
	
	public void setBackground(String path)
	{
		ImageIcon ico = new ImageIcon("img/"+path);

		if (ico.getIconWidth() < 0) {
        	System.out.println("Image not found: " + path);
        	return;
    	}

    	bg = ico.getImage();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
	}

	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		
		Graphics2D graphics = (Graphics2D)arg0;
		
		if(bg != null)
			graphics.drawImage(bg, 0, 0, getWidth(), getHeight(), this);

		game.draw(graphics);
	}
	
	@Override
	protected void processKeyEvent(KeyEvent e) {
		super.processKeyEvent(e);
		if (e.getID() == KeyEvent.KEY_PRESSED)
		{
			keyboard.processKeyEvent(e.getKeyCode(), true);
			
			if (awaitStart && keyboard.isKeyDown(Key.Space))
			{
                awaitStart = false;
                start();
                return;
            }
			
			if (keyboard.isKeyDown(Key.Escape)) {
                stop();

                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null)
                    window.dispose();
            }
			
		} else if (e.getID() == KeyEvent.KEY_RELEASED)
			keyboard.processKeyEvent(e.getKeyCode(), false);
	}

	
	public void start()
	{
	    int tick = 1000 / Settings.FPS;

	    timer = new Timer(tick, e -> {
	        game.update(keyboard);
	        repaint();

	        if (game.isGameOver())
	            timer.stop();
	    });

	    timer.start();
	}
	
	public void stop()
	{
		if(timer != null)
			timer.stop();
		
		if(game != null)
			game.resetGame();
	}
	
	/*
	public void start()
	{
		int tick = 1000 / Settings.FPS;
		
		timer = new Timer(tick, e -> {
			game.update(keyboard);
			repaint();
			
			if(game.isGameOver())
				timer.stop();
			
		}).start();
	}*/
}
