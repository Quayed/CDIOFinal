package weight_simulator.old;

import weight_simulator.old.boundary.UI;
import weight_simulator.old.controller.Controller;

public class MainSimulator {
	
	public static void main(String[] args) {
		
		Runnable c;

		// args can be set in eclipse in Run > Run Configurations > arguments,
		// under program arguments

		if (args.length > 0)
			c = new Controller(new UI(), Integer.parseInt(args[0]));
		
		else
			c = new Controller(new UI());
		
		new Thread(c).start();
	}
	
}
