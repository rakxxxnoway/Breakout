package engine;

import java.awt.Color;
import javax.swing.JLabel;

public abstract class Tweak	
{



	Program program;

	Tweak(Program program)
	{
		this.program = program;
	}

	public String getImgFile(String path) { return Settings.imgRootPath+path; }
	public String getMusicFile(String path) { return Settings.musicRootPath+path; }

	
	// GraphX
	public void setBackground(String path) {}
	
	public void addPanel(JLabel label, String pos, Color c) {}

	
	// Sound
	public void playSound(String path, boolean loop) {}
}
