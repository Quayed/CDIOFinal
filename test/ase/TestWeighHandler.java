package ase;

import java.math.RoundingMode;
import java.util.Scanner;

import com.ibm.icu.text.DecimalFormat;

import ase.ase.IWeightHandler;

public class TestWeighHandler implements IWeightHandler {
	Scanner scanner = new Scanner(System.in);
	double gross = 0;
	private double tare = 0;
	
	String[] dialogs = {
		"32", "48", "1"
	};
	int di = 0;
	boolean[] confirms = {
		true, true
	};
	int ci = 0;
	int ii = 0;
	
	@Override
	public void connect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void instruction(String message) throws WeightException {
		switch (ii++) {
		case 0:
			
			break;
		case 1:
			gross = 0.2;
			break;
		case 2:
			
			break;
		case 3:
			gross -= 10.2;
			gross = (double)Math.round(gross * 1000) / 1000;
			System.out.println(gross);
			break;
		default:
			break;
		}
	}

	@Override
	public String dialog(String message) throws WeightException {
		return dialogs[di++];
	}

	@Override
	public boolean confirm(String message) throws WeightException {
		return confirms[ci++];
	}

	@Override
	public double getWeight() throws WeightException {
		return gross - tare;
	}

	@Override
	public double tare() throws WeightException {
		tare = gross;
		System.out.println("tare: "+tare);
		System.out.println("gross: "+gross);
		return getWeight();
	}

	@Override
	public double weigh(String message) throws WeightException {
		gross += 10.0;
		return getWeight();
	}

}
