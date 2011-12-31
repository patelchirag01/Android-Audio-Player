package arp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Network {
	ServerSocket sock;
	
	public Network() {
		try {
			sock = new ServerSocket(4444);
		} catch (IOException e) {}
		
		while(true) {
			try {
				new NetworkListener(sock.accept()).start();
			} catch (IOException e) {}
		}
	}

	public static class NetworkListener extends Thread {
		Socket socket;
		
		public NetworkListener(Socket sock) {
			socket = sock;
		}
		
		public void run() {
			final AudioFormat format = getFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			TargetDataLine line = null;
			try {
				line = (TargetDataLine) AudioSystem.getLine(info);
				line.open(format);
				line.start();
			} catch (LineUnavailableException e) {}
			
			int size = (int)format.getSampleRate();
			byte buffer[] = new byte[size];
			 
			OutputStream out = null;
			try {
				out = socket.getOutputStream();
			} catch (IOException e) {}
			
			while(true) {
				int count = 0;
				if(line != null)
					count = line.read(buffer, 0, buffer.length);
				try {
					if(out != null && count > 0)
						out.write(buffer, 0, buffer.length);
				} catch (IOException e) {}
			}
		}

		private AudioFormat getFormat() {
			float sampleRate = 44100;
		    int sampleSizeInBits = 8;
		    int channels = 1;
		    boolean signed = false;
		    boolean bigEndian = true;
		    return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
		}

	}
}
