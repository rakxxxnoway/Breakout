package engine;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.CardLayout;
import java.awt.event.KeyEvent;

import javax.swing.*;

import game.Game;

public class Program extends JFrame {
	
	private static final String MENU = "menu";
	private static final String GAME = "game";
	private static final String STAT = "stats";

	private CardLayout layout;
	private JPanel root;
	private Tweak gameTweak;

	JPanel menuSceen;
	JPanel gameSceen;
	JPanel statsSceen;

	Font font;
	GameBoard board;
	
	public Program() {
		layout = new CardLayout();
		root = new JPanel(layout);

		setTitle("Breakout: Space Invasion - Launcher");
		setFont(font);
		setResizable(true);
		
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
		menuSceen = new JPanel();
		menuSceen.setLayout(new GridLayout(4, 1));

		ImageIcon ico = new ImageIcon("img/logo.png");
		Image img = ico.getImage().getScaledInstance(100, 40, Image.SCALE_SMOOTH);

		JLabel logo = new JLabel( new ImageIcon(img) );
		logo.setHorizontalAlignment(SwingConstants.CENTER);

		JButton startGame = new JButton("Start game");
		JButton statsButton = new JButton("Scoreboard");
		JButton exitGame = new JButton("Exit");

		startGame.addActionListener(e -> {
    		JFrame gameWindow = new JFrame("Breakout: Space Invasion - Game");
    
    		board = new GameBoard();
    		board.setFocusable(true);
   
			board.setBackground("space2.png");

    		gameWindow.add(board);
    		gameWindow.pack();
    		gameWindow.setLocationRelativeTo(null);
    		gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    		gameWindow.setResizable(false);
    		gameWindow.setVisible(true);
 
    		gameWindow.requestFocus();
    		board.requestFocusInWindow();
    
    		//this.setVisible(false);

			board.start();
		});

		statsButton.addActionListener(e -> layout.show(root, STAT));
		exitGame.addActionListener(e -> System.exit(0));

		menuSceen.add(logo);
		menuSceen.add(startGame);
		menuSceen.add(statsButton);
		menuSceen.add(exitGame);

		root.add(menuSceen, MENU);
	}	

	private void stats()
	{
		statsSceen = new JPanel();
		statsSceen.setLayout(new GridLayout(2, 2));

		JLabel score = new JLabel("Scoreboard");
		JLabel runs = new JLabel("Latest runs");

		JButton back = new JButton("Back");

		statsSceen.add(score);
		statsSceen.add(runs);
		statsSceen.add(back);

		back.addActionListener(e -> layout.show(root, MENU));

		root.add(statsSceen, STAT);
	}

	@Override
	protected void processKeyEvent(KeyEvent e) {
		super.processKeyEvent(e);
		board.processKeyEvent(e);
	}

	public static void main(String[] args)
	{
		Program program = new Program();
		/*
		program.soundTweak = new Sound(program);
	
		program.soundTweak.playSound("ambient.wav", true);
		*/
	}
}
