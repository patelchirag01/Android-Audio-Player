package com.tomkoole.arp;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Network {
	Socket sock;
	
	public Network(Callback cb) {
		try {
			sock = new Socket("192.168.1.3", 4444);
			new Thread(new NetworkListener(sock.getInputStream(), cb)).start();
		} catch (UnknownHostException e) {} catch (IOException e) {}
	}
	
	public static class NetworkListener implements Runnable {
		InputStream in;
		Callback cb;
		
		public NetworkListener(InputStream input, Callback cb) {
			in = input;
			this.cb = cb;
		}
		
		public void run() {
			byte[] b = new byte[256];
			int n = 0;
			do {
				try {
					n = in.read(b);
					if(n > -1)
						cb.ParseByte(b, n);
				} catch (IOException e) {}
			} while(n != -1);
		}
		
	}
}
