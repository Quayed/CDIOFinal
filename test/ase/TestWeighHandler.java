package ase;

import ase.ase.IWeightHandler;

public class TestWeighHandler implements IWeightHandler {

	@Override
	public void connect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void instruction(String message) throws WeightException {
		// TODO Auto-generated method stub

	}

	@Override
	public String dialog(String message) throws WeightException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean confirm(String message) throws WeightException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getWeight() throws WeightException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double tare() throws WeightException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double weigh(String message) throws WeightException {
		// TODO Auto-generated method stub
		return 0;
	}

}
