package weight_simulator.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import weight_simulator.boundary.IUI;
import weight_simulator.data.WeightDTO;

public class ServerThread implements Runnable {

	private ServerSocket serverSocket;
	private SocketThread socketThread;

	private boolean running;
	private IUI ui;
	private WeightDTO data;

	public ServerThread(IUI ui, WeightDTO data, int port) {
		this.ui = ui;
		this.data = data;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {

			data.setServerIp(InetAddress.getLocalHost().getHostAddress() + ":" + port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		running = true;
		while (running) {
			try {
				// blocking call, the thread will wait here till it gets a
				// connection
				Socket socket = serverSocket.accept();

				socketThread = new SocketThread(socket);
				new Thread(socketThread).start();

				data.setClientIp(socket.getInetAddress().getHostAddress());
				ui.update(data);
			} catch (IOException e) {
				e.printStackTrace();
			}

			while (running) {

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if(!socketThread.isConnected())
					break;
				
			}
			data.setClientIp("");
			ui.update(data);
		}

	}

	public boolean sendMessage(String message) {
		return socketThread.sendMessage(message);
	}

	public void stopThread() {
		this.running = false;
	}

	public boolean hasNext() {
		if (socketThread == null) {
			return false;
		}
		return socketThread.hasNext();
	}

	public String next() {
		return socketThread.next();
	}

}
