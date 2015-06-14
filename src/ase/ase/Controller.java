package ase.ase;

import ase.ase.State;

public class Controller {
	private State currentState;
	private IDAL dal;
	private IWeightHandler weightHandler;

	Controller(IDAL dal, IWeightHandler weightHandler) {
		this.dal = dal;
		this.weightHandler = weightHandler;
		currentState = State.ENTER_USER_ID;
		State.initialize(dal, weightHandler);
		start();
	}

	private void start() {
		while (true) {
			currentState = currentState.entry();
		}
	}

}
