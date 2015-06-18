package ase.ase;

import java.io.IOException;

import ase.shared.SocketHandler;

public class WeightHandler implements IWeightHandler {
	private SocketHandler weightSocket;

	public WeightHandler() {
		connect();
	}

	@Override
	public void connect() {
		while (weightSocket == null) {
			System.out.println("try connect");
			try {
				weightSocket = new SocketHandler("localhost", 8000);
				try {
					getWeight();
				} catch (WeightException e) {
					e.printStackTrace();
				}
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
	public String dialog(String message) throws WeightException, CancelException {
		if (message.length() > 24)
			message = message.substring(0, 24);
		try {
			weightSocket.println("RM20 8 \"" + message + "\" \"\" \"&3\"");
			weightSocket.readLine();
			String msg = weightSocket.readLine();
			if (msg.equals("RM20 C"))
				throw new CancelException();
			if (msg.length() > 9)
				return msg.substring(8, msg.length() - 1);
			return msg;
		} catch (IOException e) {
			weightSocket = null;
			throw new WeightException();
		}
	}

	@Override
	public boolean confirm(String message) throws WeightException {
		try {
			dialog(message);
		} catch (CancelException e) {
			return false;
		}
		return true;
	}

	@Override
	public double getWeight() throws WeightException {
		try {
			weightSocket.println("S");
			String msg = weightSocket.readLine();
			System.out.println(msg);
			if (msg.substring(8, 9).equals("-"))
				return -Double.parseDouble(msg.substring(9, msg.length() - 3));

			else
				return Double.parseDouble(msg.substring(9, msg.length() - 3));

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
			if (msg.substring(8, 9).equals("-")) {
				return -Double.parseDouble(msg.substring(9, msg.length() - 3));
			} else {
				return Double.parseDouble(msg.substring(9, msg.length() - 3));
			}
		} catch (IOException e) {
			weightSocket = null;
			throw new WeightException();
		}
	}

	@Override
	public double weigh(String message) throws WeightException {		
		try {
			weightSocket.println("P111 \""+message+"\"");
			System.out.println(weightSocket.readLine());
			weightSocket.println("ST 1");
			System.out.println(weightSocket.readLine());
			String msg = weightSocket.readLine();
			System.out.println(msg);
			weightSocket.println("P110");
			System.out.println(weightSocket.readLine());
			if (msg.substring(7, 8).equals("-"))
				return -Double.parseDouble(msg.substring(8, msg.length() - 3));
			else
				return Double.parseDouble(msg.substring(8, msg.length() - 3));
		} catch (IOException e) {
			weightSocket = null;
			weightSocket.disconnect();
			throw new WeightException();
		}
	}

}
