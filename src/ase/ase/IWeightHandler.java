package ase.ase;

public interface IWeightHandler {
	void connect();

	void instruction(String message) throws WeightException;

	String dialog(String message) throws WeightException;

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
