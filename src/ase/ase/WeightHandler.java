package ase.ase;

import java.io.IOException;

import ase.shared.SocketHandler;

public class WeightHandler implements IWeightHandler {
	private SocketHandler weightSocket;

	public WeightHandler() {
		connect();
	}
	
	@Override
	public void connect(){
		while(weightSocket == null){
			System.out.println("try connect");
			try {
				weightSocket = new SocketHandler("localhost", 8000);
				return;
			} catch (IOException e) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
				}
			}
		}
	}

	@Override
	public void instruction(String message) throws WeightException {
		rm20(message);
	}
	
	@Override
	public String dialog(String message) throws WeightException {
		return rm20(message);
	}

	@Override
	public boolean confirm(String message) throws WeightException {
		String input = rm20(message);

		if (input.equals("0"))
			return false;
		
		return true;
	}

	private String rm20(String message) throws WeightException {
		try {
			weightSocket.println("RM20 8 \""+message+"\" \"\" \"&3\"");
			weightSocket.readLine();
			String msg = weightSocket.readLine();
			return msg.substring(8, msg.length()-1);
		} catch (IOException e) {
			weightSocket = null;
			throw new WeightException();
		}
	}

	@Override
	public double getWeight() throws WeightException {
		try {
			weightSocket.println("S");
			String msg = weightSocket.readLine();
			if(msg.substring(8, 9).equals("-"))
				return -Double.parseDouble(msg.substring(9, msg.length()-3));
			
			else
				return Double.parseDouble(msg.substring(9, msg.length()-3));
			
		} catch (IOException e) {
			weightSocket = null;
			throw new WeightException();
		}
	}
	
	@Override
	public double tare() throws WeightException {
		try {
			weightSocket.println("T");
			String msg = weightSocket.readLine();
			System.out.println(msg);
			if(msg.substring(8, 9).equals("-")){
				return -Double.parseDouble(msg.substring(9, msg.length()-3));
			}
			else{
				return Double.parseDouble(msg.substring(9, msg.length()-3));
			}
		} catch (IOException e) {
			weightSocket = null;
			throw new WeightException();
		}
	}
	
}
