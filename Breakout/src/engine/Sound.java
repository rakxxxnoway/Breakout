package engine;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound
{
	private Clip clip;

	public String getMusicFile(String path) { return Settings.musicRootPath+path; }

	public void playSound(String path, boolean loop)
	{
		try
		{
			/*
			if(clip != null)
			{
				clip.stop();
				clip.close();
			}*/

			File audioFile = new File(getMusicFile(path));
			AudioInputStream aStream = AudioSystem.getAudioInputStream(audioFile);

			clip = AudioSystem.getClip();
			clip.open(aStream);

			if(loop)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			
			clip.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopSound()
	{
		if (clip != null)
		{
	        if (clip.isRunning())
	            clip.stop();
	       
	        clip.close();
	        clip = null;
	    }
	}
}

/* old
public class Sound //extends Tweak
{
	private Clip clip;
	
	public Sound(Program program)
	{
		super(program);
	}
	
	public Sound(Clip clip)
	{
		this.clip = clip;
	}
	
	public String getMusicFile(String path) { return Settings.musicRootPath+path; }
	
	//@Override
	public void playSound(String path, boolean loop)
	{
		try
		{
			File audioFile = new File( getMusicFile(path) );
			AudioInputStream aStream = AudioSystem.getAudioInputStream(audioFile);
			
			clip = AudioSystem.getClip();
			clip.open(aStream);
			
			if(loop)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stopSound()
	{
        clip.stop();
        clip.close();
	}
}
*/