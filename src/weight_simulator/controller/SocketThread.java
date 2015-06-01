package weight_simulator.controller;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Queue;

public class SocketThread implements Runnable {
	private BufferedReader instream;
	private DataOutput outstream;
	private Socket socket;
	private boolean running;
	private Queue<String> queue;

	public SocketThread(Socket socket) {
		queue = new ArrayDeque<String>();
		try {
			this.socket = socket;
			instream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outstream = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		String inLine;
		running = true;
		while (running) {
			try{
				// blocking call, the thread will wait here till it gets a message
				inLine = instream.readLine();
	
				// queue's a new action from this thread.
				queue.add(inLine);
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (IOException e1) {
				running = false;
			}
		}
		try {
			instream.close();
			socket.close();
		} catch (IOException e) {
		}
		
	}

	public boolean sendMessage(String message) {
		try {
			outstream.writeBytes(message + "\r\n");
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean isConnected() {
		return running;
	}

	public void stopThread() {
		this.running = false;
	}

	public boolean hasNext() {
		return !queue.isEmpty();
	}

	public String next() {
		return queue.poll();
	}
}
