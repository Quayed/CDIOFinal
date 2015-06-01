 package ase.controller;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import ase.shared.SocketHandler;

public class SocketConnector extends SocketHandler{

	public SocketConnector(String host, int port) throws UnknownHostException, IOException {
		super(host, port);
	}
	
	public SocketConnector(Socket socket) throws IOException {
		super(socket);
	}

	public void rm20(String msg) throws IOException {
		println("RM20 8 \""+msg+"\" \"\" \"&3\"");
	}

	public String getRM20() throws IOException, RestartException {
		System.out.println(readLine());
		String msg = readLine();
		System.out.println(msg);
		if(msg.substring(8, msg.length()-1) == "00"){
			throw new RestartException();
		}
		return msg.substring(8, msg.length()-1);
	}
	
	public double read() throws IOException {
		println("S");
		String msg = readLine();
		System.out.println(msg);
		if(msg.substring(8, 9).equals("-")){
			return -Double.parseDouble(msg.substring(9, msg.length()-3));
		}
		else{
			return Double.parseDouble(msg.substring(9, msg.length()-3));
		}
	}

	public double tare() throws IOException {
		println("T");
		String msg = readLine();
		System.out.println(msg);
		if(msg.substring(8, 9).equals("-")){
			return -Double.parseDouble(msg.substring(9, msg.length()-3));
		}
		else{
			return Double.parseDouble(msg.substring(9, msg.length()-3));
		}
	}

	public void zero() throws IOException {
		println("Z");
	}

	public void displayText(String msg) throws IOException {
		println("D " + msg);
		readLine();
	}

	public void displayWeight() throws IOException {
		println("DW");
	}

	public int getAnId(String msg) throws IOException, RestartException{
		rm20(msg);
		try{
			return Integer.parseInt(getRM20());
		} catch (NumberFormatException e){
			return 0;
		}
	}
	
	public boolean confirm(String msg) throws IOException, RestartException{
		rm20(msg);
		if(getRM20().equals("0")){
			return false;
		}
		return true;
	}

	
}