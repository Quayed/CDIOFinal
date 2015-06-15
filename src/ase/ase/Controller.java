package ase.ase;

import ase.ase.IWeightHandler.WeightException;
import ase.ase.State;

public class Controller {
	private State currentState;
	private IDAL dal;
	private IWeightHandler weightHandler;

	Controller(IDAL dal, IWeightHandler weightHandler) {
		this.dal = dal;
		this.weightHandler = weightHandler;
		State.initialize(dal, weightHandler);
		start();
	}

	protected void start() {
		while (true) {
			currentState = State.START;
			try {
				fsm();
			} catch (WeightException e) {
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	private void fsm() throws WeightException {
		while (true) {
			currentState = currentState.entry();
		}
	}

}
