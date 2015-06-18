package ase;

import ase.controller.Controller;
import ase.dal.DAL;
import ase.weight.WeightHandler;


public class Main {
	public static void main(String[] args) {
		
		new Controller(new DAL(), new WeightHandler());
		
	}
}
