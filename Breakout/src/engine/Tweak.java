package engine;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class Tweak extends JPanel
{
	private static final String GAME = "game";
	
	private final Image background;
    private Construct ui;
    private javax.swing.JList<String> scoreList;
    private javax.swing.JList<String> runsList;

    public Tweak(String path)
    {
    	ui = new Construct();
        background = ui.setImage(path);
    }
    

    // overides
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
    
    
    // methods
	public GameBoard newGameWindow(String bgFilePath, String title)
	{
		JFrame gameWindow = new JFrame(title);
		GameBoard board = new GameBoard();
		board.setFocusable(true);

		board.setBackground(bgFilePath);

		gameWindow.add(board);
		gameWindow.pack();
		gameWindow.setLocationRelativeTo(null);
		
		gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameWindow.setResizable(false);

		gameWindow.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		        board.stop();
		    }
		});

		gameWindow.setVisible(true);

		gameWindow.requestFocus();
		board.requestFocusInWindow();
		
		return board;
	}
	
	public JLabel logo(String path)
	{
		ImageIcon ico = new ImageIcon(Settings.imgRootPath+path);

		int targetWidth = 340;
		int targetHeight = ico.getIconHeight() * targetWidth / ico.getIconWidth();

		Image img = ico.getImage().getScaledInstance(
		        targetWidth,
		        targetHeight,
		        Image.SCALE_SMOOTH
		);

		JLabel logo = new JLabel(new ImageIcon(img));
		logo.setAlignmentX(CENTER_ALIGNMENT);
		
		return logo;
	}
	
	public JButton createImageButton(String path, int width, int height)
	{
		ui = new Construct();
		
	    Image img = ui.setImage(path);
	    Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

	    JButton button = new JButton(new ImageIcon(scaled));
	    button.setPreferredSize(new Dimension(width, height));
	    button.setMaximumSize(new Dimension(width, height));
	    button.setMinimumSize(new Dimension(width, height));

	    button.setBorderPainted(false);
	    button.setContentAreaFilled(false);
	    button.setFocusPainted(false);
	    button.setOpaque(false);

	    return button;
	}
	
	public void refreshScoreboard()
	{
	    if (scoreList == null || runsList == null)
	        return;

	    HighscoreList highscore = new HighscoreList();
	    LatestRunsList latestRuns = new LatestRunsList();

	    java.util.List<ScoreEntry> allScores = ScoreStorage.loadScores();

	    highscore.setEntries(allScores);

	    for (ScoreEntry entry : allScores)
	    {
	        latestRuns.addRun(entry.getScore());
	    }

	    scoreList.setModel(highscore.getModel());
	    runsList.setModel(latestRuns.getModel());
	}
	
	public JPanel newScoreboardWindow(String bgPath, CardLayout layout, JPanel root)
	{
		JPanel statsSceen = new Tweak("menu1.png");
		statsSceen.setLayout(new BoxLayout(statsSceen, BoxLayout.Y_AXIS));
		statsSceen.setOpaque(false);

		JPanel columns = new JPanel();
		columns.setLayout(new BoxLayout(columns, BoxLayout.X_AXIS));
		columns.setOpaque(false);
		columns.setAlignmentX(CENTER_ALIGNMENT);

		JLabel score = new JLabel("Scoreboard");
		score.setForeground(new Color(220, 200, 10));
		score.setFont(new Font("Arial", Font.BOLD, 24));
		JLabel runs  = new JLabel("Latest runs");
		runs.setForeground(new Color(220, 200, 10));
		runs.setFont(new Font("Arial", Font.BOLD, 24));

		score.setAlignmentX(CENTER_ALIGNMENT);
		runs.setAlignmentX(CENTER_ALIGNMENT);

		JPanel left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.setOpaque(false);
		

		scoreList = new javax.swing.JList<>();
		scoreList.setBackground(new Color(20, 20, 35));
		scoreList.setForeground(new Color(240, 240, 255));
		scoreList.setFont(new Font("Monospaced", Font.BOLD, 20));
		scoreList.setBorder(BorderFactory.createLineBorder(new Color(120, 80, 255), 3));

		
		JScrollPane scoreScroll = new JScrollPane(scoreList);
		scoreScroll.setPreferredSize(new Dimension(250, 200));
		scoreScroll.setMaximumSize(new Dimension(250, 200));
		scoreScroll.setMinimumSize(new Dimension(250, 200));

		left.add(score);
		left.add(javax.swing.Box.createVerticalStrut(10));
		left.add(scoreScroll);


		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.setOpaque(false);

		runsList = new javax.swing.JList<>();
		runsList.setBackground(new Color(20, 20, 35));
		runsList.setForeground(new Color(240, 240, 255));
		runsList.setFont(new Font("Monospaced", Font.BOLD, 20));
		runsList.setBorder(BorderFactory.createLineBorder(new Color(120, 80, 255), 3));

		JScrollPane runsScroll = new JScrollPane(runsList);
		runsScroll.setPreferredSize(new Dimension(250, 200));
		runsScroll.setMaximumSize(new Dimension(250, 200));
		runsScroll.setMinimumSize(new Dimension(250, 200));

		right.add(runs);
		right.add(javax.swing.Box.createVerticalStrut(10));
		right.add(runsScroll);


		columns.add(left);
		columns.add(javax.swing.Box.createHorizontalStrut(40));
		columns.add(right);

		Dimension boxSize = new Dimension(320, 220);
		scoreScroll.setPreferredSize(boxSize);
		runsScroll.setPreferredSize(boxSize);

		JButton back = createImageButton(
		        "back.png",
		        Settings.LAUNCHER_BUTTON_WIDTH,
		        Settings.LAUNCHER_BUTTON_HEIGHT
		);
		
		back.setAlignmentX(CENTER_ALIGNMENT);
		back.addActionListener(e ->
		{
			layout.show(root, Settings.MENU);
			Sound sfx = new Sound();
			sfx.setVolume(Settings.SFX_MENU);
			
			sfx.playSound("plop2.wav", false);
		});

		statsSceen.add(javax.swing.Box.createVerticalStrut(30));
		statsSceen.add(columns);
		statsSceen.add(javax.swing.Box.createVerticalStrut(20));
		statsSceen.add(back);

		refreshScoreboard();
		return statsSceen;
	}
}