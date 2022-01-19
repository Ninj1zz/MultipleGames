package main.pong.entity.utils;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class Sound{
	private final String path;
	private final String description;
	private final int volume;
	private Clip clip;

	public Sound(String path,String description,int volume)
	{
		this.path = path;
		this.description = description;
		this.volume = volume;
		resetAudioStream();
	}

	public void play(){
		clip.start();
		resetAudioStream();
	}

	private void resetAudioStream(){
		try {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
			assert is != null;
			AudioInputStream aIn = AudioSystem.getAudioInputStream(is);
			clip = AudioSystem.getClip();
			clip.open(aIn);
			FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			control.setValue(-1f * volume);
			aIn.close();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	public String getDescription() {
		return description;
	}
}
