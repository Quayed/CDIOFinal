package weight_simulator.boundary;

import weight_simulator.data.WeightDTO;

public interface IUI {
	void start(WeightDTO data);

	void update(WeightDTO data);

	boolean hasNext();

	String next();

	void setRm20State(boolean rm20State);

	void setTextMode(boolean textMode);

	void setInstructionDisplay(String instructionDisplay);

	void setWeightText(String substring);

	void setSecondaryDisplay(String secondaryDisplay);
}