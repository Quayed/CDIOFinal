package ase;

import ase.controller.Controller;

public class MainASE {

	public static void main(String[] args) {
		int port;
		if (args.length == 2) {
			
			port = isPort(args[1]);
			if(port != -1){
				new Controller(args[0], port);
			}
			else{
				port = isPort(args[0]);
				if(port != -1){
					new Controller(args[1], port);
				}
			}
			
			new Controller(args[0], Integer.parseInt(args[1]));
		} else if (args.length == 1) {
			
			port = isPort(args[0]);
			
			if(port == -1){
				new Controller(port);
			}
			else{
				new Controller(args[0]);
			}
		}
		else{
			new Controller();
		}
		System.exit(0);
	}

	private static int isPort(String number) {
		try {
			int port = Integer.parseInt(number);
			if (port > 0)
				return port;
		} catch (NumberFormatException e) {
		}
		return -1;
	}

}
