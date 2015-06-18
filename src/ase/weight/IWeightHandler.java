package ase.weight;

public interface IWeightHandler {
	void connect();

	String dialog(String message) throws WeightException, CancelException;

	boolean confirm(String message) throws WeightException;

	double getWeight() throws WeightException;

	double tare() throws WeightException;

	double weigh(String message) throws WeightException;

	class WeightException extends Exception {

		private static final long serialVersionUID = 1L;

	}
	
	class CancelException extends Exception {

		private static final long serialVersionUID = 1L;

	}

}
