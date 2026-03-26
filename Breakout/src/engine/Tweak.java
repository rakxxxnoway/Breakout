package engine;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
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

@SuppressWarnings("serial")
class Tweak extends JPanel
{	
	private final Image background;
    private Construct ui;
    private javax.swing.JList<String> scoreList;
    private javax.swing.JList<String> runsList;
    private Font scoreFont;

    public Tweak(String path)
    {
    	ui = new Construct();
        background = ui.setImage(path);
        Construct conf = new Construct();
		scoreFont = conf.setFont("/ttf/PressStart2P.ttf", Settings.FONT_SIZE_SCOREBOARD);
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

		JPanel left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.setOpaque(false);
		
		JLabel score = newLabel("Scoreboard");
		JLabel runs  = newLabel("Latest runs");

		scoreList = new javax.swing.JList<>();
		scoreList.setBackground(Settings.COLOR_NAVY_BLACK);
		scoreList.setForeground(Settings.COLOR_L_BLUE);
		scoreList.setFont(scoreFont);
		scoreList.setBorder(BorderFactory.createLineBorder(Settings.COLOR_PURPLE, 3));

		
		JScrollPane scoreScroll = new JScrollPane(scoreList);
		scoreScroll.setPreferredSize(Settings.SCROLL_PANE_DIM);

		left.add(score);
		left.add(javax.swing.Box.createVerticalStrut(10));
		left.add(scoreScroll);


		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.setOpaque(false);

		runsList = new javax.swing.JList<>();
		runsList.setBackground(Settings.COLOR_NAVY_BLACK);
		runsList.setForeground(Settings.COLOR_L_BLUE);
		runsList.setFont(scoreFont);
		runsList.setBorder(BorderFactory.createLineBorder(Settings.COLOR_PURPLE, 3));

		JScrollPane runsScroll = new JScrollPane(runsList);
		runsScroll.setPreferredSize(Settings.SCROLL_PANE_DIM);

		right.add(runs);
		right.add(javax.swing.Box.createVerticalStrut(10));
		right.add(runsScroll);


		columns.add(left);
		columns.add(javax.swing.Box.createHorizontalStrut(40));
		columns.add(right);

		scoreScroll.setPreferredSize(Settings.BOX_SIZE);
		runsScroll.setPreferredSize(Settings.BOX_SIZE);

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
	
	private JLabel newLabel(String label)
	{
		JLabel l = new JLabel(label);
		l.setForeground(Settings.COLOR_YELLOW_LEMON);
		l.setFont(scoreFont);
		
		l.setAlignmentX(CENTER_ALIGNMENT);
		
		return l;
	}
}