package com.tomkoole.arp;

import java.util.ArrayList;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class Audio implements Callback{
	ArrayList<Integer> buffer;
	AudioTrack audio;

	public Audio() {
		buffer = new ArrayList<Integer>();
		audio = new AudioTrack(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_8BIT, 2400, AudioTrack.MODE_STREAM);
		audio.play();
	}

	public void ParseByte(byte[] data, int length) {
		audio.write(data, 0, length);
	}
}
