package weight_simulator;

import java.io.IOException;
import java.util.Scanner;

import com.mysql.fabric.xmlrpc.Client;

public class NewMainSimulator implements Runnable{
	
	private NewServer server;
	private Scanner scanner;
	private double gross;
	private double tare;
	private boolean rm20State;
	private boolean weighingState;
	
	public NewMainSimulator() {
		try {
			server = new NewServer();
			scanner = new Scanner(System.in);
			new Thread(server).start();
			new Thread(this).start();
			String inline;
			do{
				inline = server.readLine();
				if(inline != null){
					doCall(inline);
				}
				
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			} while (true);
			
		} catch (IOException e) {
		}
	}

	private void doCall(String action) {
		System.out.println(action);
		if (action.equals("S")) {
			double netto = gross - tare;
			server.printLn("S S     "
					+ (netto < 0 ? "" : " ")
					+ netto + " kg");

		} else if (action.equals("T")) {
			tare = gross;
			server.printLn("T S     "
					+ (tare < 0 ? "" : " ")
					+ tare + " kg");

		} else if (action.contains("P111")) {
			server.printLn("P111 A");

		} else if (action.equals("P110")) {
			server.printLn("P110 A");

		} else if(action.equals("ST 1")) {
			server.printLn("ST A");
			weighingState = true;
			
		} else if (action.substring(0, 4).equals("RM20")) {
			server.printLn("RM20 B");
			rm20State = true;
		}
	}

	@Override
	public void run() {
		while(true){
			String line = scanner.nextLine();
			if(line.length() > 2 && line.substring(0, 2).equals("b ")){
				try{
					gross = Double.valueOf(line.substring(2));
				} catch (NumberFormatException e) {}
			}
			else if(rm20State){
				if(line.equals("c")){
					server.printLn("RM20 C");
				}
				else{
					server.printLn("RM20 A \""+line+"\"");
				}
				rm20State = false;
			}
			else if(weighingState){
				if(line.equals("t")){
					double netto = gross - tare;
					server.printLn("S S    "
							+ (netto < 0 ? "" : " ")
							+ netto + " kg");
					weighingState = false;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		
		new NewMainSimulator();
		
	}

}
