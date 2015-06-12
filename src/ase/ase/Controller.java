package ase.ase;

import ase.ase.State;

public class Controller {
	private State currentState;
	private ILogic logic;

	Controller(ILogic logic) {
		this.logic = logic;
		currentState = State.ENTER_USER_ID;
		State.initialize(logic);
		start();
	}

	private void start() {
		while (true) {
			currentState = currentState.entry();
		}
	}

}
