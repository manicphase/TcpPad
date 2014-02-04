package org.manicphase.udppad;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class TCPClient implements Runnable {
	InetAddress serverAddr;
	Socket socket;
	String connstat;
	String portstat;
	boolean unblocked = true;
	
	public TCPClient()
	{
		try {
			serverAddr = InetAddress.getByName("192.168.1.2");
			connstat = "Found IP";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			connstat = "Failed to find IP";
			e.printStackTrace();
		}
		try {
			socket = new Socket(serverAddr, 50008);
			portstat = "socket connected";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			portstat = "socket connect failed";
			e.printStackTrace();
		}
	}
	
	
	public void start(){

	}

	
	private Context getApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}


	public void run() {
		try {			
			//Log.d("TCP", "C: Connecting...");
		}
		catch (Exception e) {
			//Log.e("TCP", "C: Error", e);
		}
	}
	public void send(String message)
	{
		unblocked = false;
		try {
			//Log.d("TCP", "C: Sending: '" + message +"'");
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())),true);
			out.println(message);
			//Log.d("TCP", "C: Sent.");
			//Log.d("TCP", "C: Done.");
			
			BufferedReader fromserver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch(Exception e) {
			//Log.e("TCP", "S: Error", e);
		}
		unblocked = true;
	}
	public void close()
	{
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}