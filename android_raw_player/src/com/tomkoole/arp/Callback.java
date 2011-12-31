package com.tomkoole.arp;

public interface Callback {
	public abstract void ParseByte(byte[] data, int length);
}
