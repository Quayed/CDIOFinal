package ase;

import ase.controller.Controller;
import ase.dal.DAL;
import ase.weight.WeightHandler;


public class MainASE {
	public static void main(String[] args) {
		
		new Controller(new DAL(), new WeightHandler("169.254.2.3", 8000));
		
	}
}
