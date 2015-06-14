package ase.ase;

public interface IWeightHandler {
	void connect();
	String dialog(String message) throws WeightException;
	boolean confirm(String message) throws WeightException;
	void instruction(String message) throws WeightException;
	class WeightException extends Exception{
		
	}
}
