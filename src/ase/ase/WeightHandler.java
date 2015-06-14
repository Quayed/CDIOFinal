package ase.ase;

import java.io.IOException;
import java.net.Socket;

import ase.shared.SocketHandler;

public class WeightHandler implements IWeightHandler {
	SocketHandler weightSocket;

	@Override
	public void connect() {
		Socket socket;
		try {
			socket = new Socket("localhost", 3033);
			weightSocket = new SocketHandler(socket);
		} catch (IOException e) {
			System.out.println("WeightHandler socket error!");
		}
	}

	@Override
	public String dialog(String message) throws WeightException {
		String input = null;
		try {
			weightSocket.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			input = weightSocket.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}

	@Override
	public boolean confirm(String message) throws WeightException {
		String input = null;
		try {
			weightSocket.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			input = weightSocket.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean answer = true;
		if (input == "0")
			answer = false;
		return answer;
	}

	@Override
	public void instruction(String message) throws WeightException {
		try {
			weightSocket.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			weightSocket.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
