package engine;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.*;

public class Program extends JFrame
{
	private CardLayout layout;
	private JPanel root;

	JPanel menuSceen;
	JPanel gameSceen;
	JPanel statsSceen;

	Font font;
	GameBoard board;
	Construct ui;
	Tweak tweak;
	Sound sfx;
	
	public Program()
	{
		tweak = new Tweak("menu1.png");
		sfx = new Sound();
		sfx.setVolume(Settings.SFX_MENU);
		
		layout = new CardLayout();
		root = new JPanel(layout);
        root.setPreferredSize(new Dimension( Settings.LAUNCHER_WIDTH, Settings.LAUNCHER_HEIGHT ));

		setTitle("Breakout: Space Invasion - Launcher");
		setFont(font);
		setResizable(false);
		
		menu();
		stats();

		add(root);
    	pack();
    	setLocationRelativeTo(null);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	setVisible(true);
	}
	
	private void menu()
	{
		menuSceen = new Tweak("menu2.png");
		menuSceen.setLayout(new GridBagLayout());
		
		JPanel content = new JPanel();
	    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
	    content.setOpaque(false);

		JLabel logo = tweak.logo("logo.png");
		
		JButton startGame 	= tweak.createImageButton("start_game.png", Settings.LAUNCHER_BUTTON_WIDTH, Settings.LAUNCHER_BUTTON_HEIGHT);
		JButton statsButton = tweak.createImageButton("scoreboard.png", Settings.LAUNCHER_BUTTON_WIDTH, Settings.LAUNCHER_BUTTON_HEIGHT);
		JButton exitGame 	= tweak.createImageButton("exit.png", 		Settings.LAUNCHER_BUTTON_WIDTH, Settings.LAUNCHER_BUTTON_HEIGHT);

		startGame	.setAlignmentX(CENTER_ALIGNMENT);
	    statsButton	.setAlignmentX(CENTER_ALIGNMENT);
	    exitGame	.setAlignmentX(CENTER_ALIGNMENT);
		
		startGame	.addActionListener(e -> {
			board = tweak.newGameWindow("space2.png", "Breakout: Space Invasion - Game");
			sfx.playSound("plop2.wav", false);
		});
		
		statsButton	.addActionListener(e -> {
			sfx.playSound("plop2.wav", false);
			layout.show(root, Settings.STAT);
		});
		
		exitGame	.addActionListener(e -> {
			sfx.playSound("plop2.wav", false);
			System.exit(0);
		});

		content.add(logo);
	    content.add(javax.swing.Box.createVerticalStrut(100));
	    
	    content.add(startGame);
	    content.add(javax.swing.Box.createVerticalStrut(6));
	    
	    content.add(statsButton);
	    content.add(javax.swing.Box.createVerticalStrut(6));
	    
	    content.add(exitGame);

	    menuSceen.add(content);
		root.add(menuSceen, Settings.MENU);
	}	

	private void stats()
	{
		statsSceen = tweak.newScoreboardWindow("menu1.png", layout, root);
		root.add(statsSceen, Settings.STAT);
	}

	public static void main(String[] args)
	{
		Program program = new Program();
	}
}
