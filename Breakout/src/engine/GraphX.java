package engine;

/*

import java.awt.*;
import javax.swing.*;



public class GraphX extends Tweak
{
	public GraphX(Program program)
	{
		super(program);
	}

	@Override
	public void setBackground(String path)
	{
		ImageIcon ico = new ImageIcon(getImgFile(path));
		
		if (ico.getIconWidth() < 0)
		{
        	System.out.println("Image not found: %s".formatted(getImgFile(path)));
        	return;
    	}

		JLabel bg = new JLabel(ico);
		bg.setLayout(new BorderLayout());
		
		program.remove(program.board);
		program.add(program.board, BorderLayout.CENTER);
		
		program.add(bg, BorderLayout.CENTER);
		program.revalidate();
		program.repaint();
	}
	
	public void addPanel(JLabel label, String pos, Color c)
	{
		JPanel panel = new JPanel();
		
		label.setForeground(Color.YELLOW);
		label.setFont(new Font("Minecraft Rus", Font.PLAIN, 20));
		
		panel.setBackground(c);
		panel.add(label);
		
		panel.setPreferredSize(new Dimension(180, 0));
		program.getContentPane().add(panel, pos);
		program.pack();
	}
}
*/