package ase.test;

import java.sql.SQLException;

import org.junit.Test;

import ase.controller.State;
import ase.dal.DAL;
import ase.weight.IWeightHandler.WeightException;
import dtu.cdio_final.server.dal.connector.Connector;
import dtu.cdio_final.server.dal.daointerfaces.DALException;

public class TestState {

	@Test
	public void test() {
		try {
			Connector.doUpdate("UPDATE productbatch SET status = 1 WHERE pb_id = 48;");
			Connector.doUpdate("DELETE FROM productbatch_component WHERE pb_id = 48;");
		} catch (SQLException e1) {
		}
		
		State.initialize(new DAL(), new TestWeighHandler());
		State current = State.START;
		for (int i = 0; i < 13; i++) {
			try {
				current = current.entry();
			} catch (WeightException | DALException e) {
				
			}
		}
	}

}
