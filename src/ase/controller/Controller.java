package ase.controller;

import ase.dal.IDAL;
import ase.weight.IWeightHandler;
import ase.weight.IWeightHandler.WeightException;
import dtu.cdio_final.server.dal.daointerfaces.DALException;

public class Controller {
	private State currentState;
	private IDAL dal;
	private IWeightHandler weightHandler;

	public Controller(IDAL dal, IWeightHandler weightHandler) {
		this.dal = dal;
		this.weightHandler = weightHandler;
		State.initialize(dal, weightHandler);
		start();
	}

	protected void start() {
		while (true) {
			currentState = State.START;
			try {
				startStateMachine();
			} catch (WeightException e) {
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	private void startStateMachine() throws WeightException {
		while (true) {
			try {
				currentState = currentState.entry();
			} catch (DALException e) {
				try {
					State.INVALID_DATABASE.entry();
				} catch (DALException e1) {
				}
			}
		}
	}

}
