package ase.ase;

import java.util.Scanner;

import dtu.cdio_final.server.dal.daoimpl.FormulaDAO;
import dtu.cdio_final.server.dal.daointerfaces.IFormulaDAO;

public enum State {
	ENTER_USER_ID {
		@Override
		State entry() {
			logic.connect();

			try {
				//userID = Integer.parseInt(in);
			} catch (NumberFormatException e) {
				System.out.println("Not a number");
				return ENTER_USER_ID;
			}
			return CONFIRM;
		}


	},
	CONFIRM {
		@Override
		State entry() {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Andreas lorentzen?");
			String in = scanner.nextLine();

			if (in.equals("0")) {
				return ENTER_USER_ID;
			} else {
				return ENTER_USER_ID;
			}

		}
	};
	static void initialize(ILogic logic) {
		State.logic = logic;
	}

	abstract State entry();

	private static ILogic logic;
}