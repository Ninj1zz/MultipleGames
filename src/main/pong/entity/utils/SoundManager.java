package main.pong.entity.utils;

import java.util.ArrayList;
import java.util.List;

public class SoundManager {
	private static final List<Sound> soundList = new ArrayList<>();

	public static void addSound(Sound s){
		soundList.add(s);
	}

	public static Sound getSound(String description){
		for(Sound s : soundList){
			if(s.getDescription().equals(description)){
				return s;
			}
		}
		return null;
	}
}
