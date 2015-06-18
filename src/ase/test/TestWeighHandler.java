package ase.test;

import java.math.RoundingMode;
import java.util.Scanner;

import com.ibm.icu.text.DecimalFormat;

import ase.weight.IWeightHandler;

public class TestWeighHandler implements IWeightHandler {
	Scanner scanner = new Scanner(System.in);
	double gross = 0;
	private double tare = 0;
	
	String[] dialogs = {
		"32", "48", "1"
	};
	int di = 0;
	boolean[] confirms = {
		true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true
	};
	int ci = 0;
	
	@Override
	public void connect() {
		// TODO Auto-generated method stub

	}

	@Override
	public String dialog(String message) throws WeightException {
		return dialogs[di++];
	}

	@Override
	public boolean confirm(String message) throws WeightException {
		switch (ci) {
			case 3:
				gross = 0.3;
				break;

			case 4:
				gross = 0;
				break;
			default:
				break;
		}
		return confirms[ci++];
	}

	@Override
	public double getWeight() throws WeightException {
		return gross - tare;
	}

	@Override
	public double tare() throws WeightException {
		tare = gross;
		return getWeight();
	}

	@Override
	public double weigh(String message) throws WeightException {
		gross = 1.3;
		return getWeight();
	}

}
