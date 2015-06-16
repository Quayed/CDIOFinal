package weight_simulator.old.boundary;

import weight_simulator.old.data.WeightDTO;

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