package weight_simulator;

import java.io.IOException;
import java.net.ServerSocket;

import shared.SocketHandler;

public class NewServer implements Runnable{

	private final ServerSocket server;
	private SocketHandler client;
	
	public NewServer() throws IOException {
		this(8000);
	}
	
	public NewServer(int port) throws IOException {
		this.server = new ServerSocket(port);
	}
	
	@Override
	public void run() {
		try {
			client = new SocketHandler(server.accept());
			System.out.println("client found!");
			
			try {
				Thread.sleep(100000000);
			} catch (InterruptedException e) {
				
			}
			
			client.disconnect();
			
			
		} catch (IOException e) {
			client.disconnect();
			client = null;
		}
	}
	
	public String readLine(){
		try {
			if (client != null)
				return client.readLine();
		} catch (IOException e) {
			client.disconnect();
			client = null;
		}
		return null;
	}
	
	public void printLn(String msg){
		try {
			if (client != null)
				client.println(msg);
		} catch (IOException e) {
			client.disconnect();
			client = null;
			System.out.println("fail!");
		}
	}

}
