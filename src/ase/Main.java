package ase;

import java.io.IOException;

import shared.SocketHandler;
import ase.controller.Controller;
import ase.dal.DAL;
import ase.weight.WeightHandler;


public class Main {
	public static void main(String[] args) throws IOException {
		new Controller(new DAL(), new WeightHandler());
	/*	SocketHandler sh = new SocketHandler("169.254.2.3", 8000);
		sh.println("S");
		System.out.println(sh.readLine());;
		sh.println("P111 \"Fill container\"");
		System.out.println(sh.readLine());;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sh.println("P110");
		System.out.println(sh.readLine());;
		*/
	}
}
