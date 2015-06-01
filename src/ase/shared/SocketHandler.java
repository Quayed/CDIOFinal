package ase.shared;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketHandler {

	private final Socket socket;
	private final BufferedReader in;
	private final DataOutputStream out;

	public SocketHandler(String host, int port) throws IOException {
		this(new Socket(host, port));
	}

	public SocketHandler(SocketHandler socketHandler, int port) throws IOException {
		this(new Socket(socketHandler.socket.getInetAddress(), port));
	}
	
	public SocketHandler(Socket socket) throws IOException {
		this.socket = socket;
		this.out = new DataOutputStream(socket.getOutputStream());
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public void print(String msg) throws IOException {
		out.writeBytes(msg);
	}

	public void println(String msg) throws IOException {
		print(msg + "\r\n");
	}

	public String readLine() throws IOException {
		return in.readLine();
	}

	public final void disconnect() {
		try {
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
		}
	}

}