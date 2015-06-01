package weight_simulator.controller;

import weight_simulator.boundary.IUI;
import weight_simulator.data.WeightDTO;

public class Controller implements Runnable {

	private static final int SYSTEM_HERTZ = 1000 / 20;
	private WeightDTO data;
	private IUI ui;
	private ServerThread serverThread;
	private boolean running;
	private boolean rm20State;

	public Controller(IUI ui) {
		this(ui, 8000);
	}

	public Controller(IUI ui, int port) {
		this.ui = ui;
		this.data = new WeightDTO();

		this.serverThread = new ServerThread(ui, data, port);
		new Thread(serverThread).start();
	}

	@Override
	public void run() {
		ui.start(data);
		this.running = true;
		while (running) {
			if (ui.hasNext())
				doAction(ui.next());

			while (serverThread.hasNext())
				doSocketAction(serverThread.next());

			try {
				Thread.sleep(SYSTEM_HERTZ);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	private void doAction(String m) {
		String action = m.toUpperCase();
		System.out.println("action = " + action);

		if (action.equals("T")){
			tare();
			ui.update(data);
		}
		else if (action.startsWith("B ") && action.length() > 2) {
			if (setBrutto(action.substring(2)))
				ui.update(data);

		} else if (action.startsWith("RM20 ") && action.length() > 5) {
			if (action.substring(5).startsWith("B ")) {
				if (setBrutto(action.substring(7)))
					ui.update(data);
			} else {
				serverThread.sendMessage("RM20 A \"" + m.substring(5) + "\"");
				ui.setInstructionDisplay("");
				ui.setRm20State(false);
				ui.update(data);

				// send "RM20 C" if the dialog is canceled
			}
		} else if (action.equals("Q"))
			stopThread();

	}

	private void doSocketAction(String m) {
		String action = m.toUpperCase();

		if (action.equals("S")) {

			serverThread.sendMessage("S S     "
					+ (data.getGross() < 0 ? "" : " ")
					+ data.getGross() + " kg");

		} else if (action.equals("T")) {

			serverThread.sendMessage(tare());

		} else if (action.startsWith("D \"") && action.endsWith("\"")) {

			ui.setTextMode(true);
			ui.setWeightText(action.substring(3, action.length() - 1));
			ui.update(data);
			serverThread.sendMessage("D A");

		} else if (action.equals("DW")) {

			ui.setTextMode(false);
			ui.update(data);
			serverThread.sendMessage("DW A");

		} else if (action.startsWith("P111 \"") && action.endsWith("\"")) {
			action = action.substring(6, action.length() - 1);

			int maxLength = 30;

			if (action.length() > maxLength)
				serverThread.sendMessage("P111 L");

			else {
				ui.setSecondaryDisplay(action);
				ui.update(data);
				serverThread.sendMessage("P111 A");
			}

		} else if (action.startsWith("RM20 ")) {
			action = action.substring(5);

			if (action.startsWith("8 \"") && action.endsWith("\" \"\" \"&3\"")) {
				// see documentation page 4 and 5
				action = action.substring(3, action.length() - 9);

				int maxLength = 24;

				if (action.length() > maxLength)
					serverThread.sendMessage("RM20 L");

				// there is already a RM20 running
				else if (rm20State)
					serverThread.sendMessage("RM20 I");

				else {
					serverThread.sendMessage("RM20 B");
					ui.setInstructionDisplay(action);
					ui.setRm20State(true);
					ui.update(data);
				}

			} else if (action.equals("0")) { // cancel the current RM20

				// there is no RM20 to cancel
				if (!rm20State)
					serverThread.sendMessage("RM20 I");

				else {
					ui.setInstructionDisplay("");
					ui.setRm20State(false);
					ui.update(data);
					serverThread.sendMessage("RM20 A");
				}
			} else
				serverThread.sendMessage("ES");

		} else if (action.startsWith("B ")) {

			if (setBrutto(action.substring(2))) {
				ui.update(data);
				serverThread.sendMessage("DB");
			} else {
				serverThread.sendMessage("B I");
			}

		} else if (action.equals("Q")) {
			stopThread();
		} else {
			serverThread.sendMessage("ES");
		}
	}

	private String tare() {
		String output;
		output = "T S     ";
		data.setTare(data.getGross());
		double tare = data.getTare();

		if (tare >= 0)
			output += " ";
		output += tare;
		output += " kg";
		
		return output;
	}

	private boolean setBrutto(String brutto) {
		try {
			System.out.println(data.getGross());
			System.out.println(Double.valueOf(brutto));
			System.out.println((data.getGross() + Double.valueOf(brutto)));
			data.setGross(data.getGross() + Double.valueOf(brutto));
			return true;
		} catch (NumberFormatException e) {
			System.out.println("Cant input a non number as current Weigth");
			return false;
		}
	}

	public void stopThread() {
		System.exit(0);
		// this.running = false;
	}

}
