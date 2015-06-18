package weight_simulator.old.boundary;

import java.util.Scanner;

import weight_simulator.old.data.WeightDTO;

public class UI implements Runnable, IUI {
	private Scanner in;
	private boolean running;
	private boolean textMode = false;
	private String message = null;
	private boolean rm20State = false;

	private String instructionDisplay = "";
	private String weightText;
	private String secondaryDisplay = "";

	public UI() {
		this.in = new Scanner(System.in);
	}

	@Override
	public void start(WeightDTO data) {
		new Thread(this).start();
		update(data);
	}

	@Override
	public void run() {

		this.running = true;
		while (running) {

			// blocking call, the thread waits here until it gets an input.
			if (in.hasNextLine()) {
				if (rm20State)
					message = "RM20 " + in.nextLine();
				else
					message = in.nextLine();
			}

		}
	}

	@Override
	public void update(WeightDTO data) {
		menu(data);
	}

	private void menu(WeightDTO data) {
		String weightToShow;
		if (this.textMode) {
			weightToShow = weightText;
		} else {
			weightToShow = "" + (data.getGross() - data.getTare()) + " kg";
		}
		for (int i = 0; i < 5; i++)
			System.out.println("\n");

		System.out
				.println("----------------------------------------------------");
		System.out.println(" Secondary Display:   " + secondaryDisplay);
		System.out.println(" Weight Display:      " + weightToShow);
		System.out.println(" Instruction Display: " + instructionDisplay);
		System.out
				.println("----------------------------------------------------");
		System.out.println(" Host: " + data.getServerIp());
		System.out.println(" Client: " + data.getClientIp());
		System.out.println(" Brutto: " + (data.getGross()) + " kg");
		System.out.println(" Tare:   " + data.getTare() + " kg"); // added tare
		// System.out.println("                                                 ");
		// System.out.println("Denne vegt simulator lytter på ordrene           ");
		// System.out.println("D, DN, S, T, B, Q                                ");
		// System.out.println("på kommunikationsporten.                        ");
		System.out
				.println("----------------------------------------------------");
		System.out.println(" Press T for tara");
		System.out.println(" Press B for new brutto");
		System.out.println(" Press Q to exit the program");
		System.out
				.println("----------------------------------------------------");
	}

	public void stopThread() {
		this.running = false;
	}

	@Override
	public boolean hasNext() {
		return message != null;
	}

	@Override
	public String next() {
		String tempMessage = message;
		message = null;
		return tempMessage;
	}

	@Override
	public void setRm20State(boolean rm20State) {
		this.rm20State = rm20State;
	}

	@Override
	public void setTextMode(boolean textMode) {
		this.textMode = textMode;
	}

	@Override
	public void setInstructionDisplay(String instructionDisplay) {
		this.instructionDisplay = instructionDisplay;
	}

	@Override
	public void setWeightText(String weightText) {
		this.weightText = weightText;
	}

	@Override
	public void setSecondaryDisplay(String secondaryDisplay) {
		this.secondaryDisplay = secondaryDisplay;
	}

}
