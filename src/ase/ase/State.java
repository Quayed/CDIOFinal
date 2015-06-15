package ase.ase;

import ase.ase.IWeightHandler.WeightException;
import dtu.cdio_final.server.dal.daointerfaces.*;
import dtu.cdio_final.shared.dto.*;

public enum State {
	START {
		@Override
		State entry() throws WeightException {
			// CONNECT/RECONNECT
			weightHandler.connect();
			dal.connect();
			// RESET
			formulaBatch = null;
			material = null;
			materialBatch = null;
			formula = null;
			formulaComp = null;
			user = null;
			productBatch = null;
			containerWeight = 0;
			nettoWeight = 0;
			return ENTER_USER_ID;
		}
	},
	INVALID_DATABASE {
		@Override
		State entry() throws WeightException {
			weightHandler.instruction("Database error!");
			throw new WeightException();
		}
	},
	ENTER_USER_ID {
		@Override
		State entry() throws WeightException {
			// VARIABLES
			String input = "";
			int userID;
			// DIALOG: DISPLAY MESSAGE AND RECEIVE INPUT
			input = weightHandler.dialog("Enter User ID");
			// VALIDATE INPUT TYPE
			try {
				userID = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				return ENTER_USER_ID;
			}
			// VALIDATE USER
			try {
				user = dal.getUserDao().getUser(userID); // get user from database
				if (user == null)
					return ENTER_USER_ID;
			} catch (DALException e) {
				return INVALID_DATABASE;
			}
			// RETURN NEXT STATE
			return CONFIRM_OPERATOR;
		}
	},
	CONFIRM_OPERATOR {
		@Override
		State entry() throws WeightException {
			// VARIABLES
			boolean input;
			// CONFIRM: DISPLAY MESSAGE AND RECEIVE CONFIRMATION
			input = weightHandler.confirm("Confirm " + State.user.getUserName());
			// VALIDATE CONFIRMATION
			if (!input)
				return ENTER_USER_ID;
			// RETURN NEXT STATE
			return ENTER_PRODUCTBATCH_ID;

		}
	},
	ENTER_PRODUCTBATCH_ID {
		@Override
		State entry() throws WeightException {
			// VARIABLES
			String input = "";
			int productBID;
			// DIALOG: DISPLAY MESSAGE AND RECEIVE INPUT
			input = weightHandler.dialog("Enter Productbatch ID");
			// VALIDATE INPUT TYPE
			try {
				productBID = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				return ENTER_PRODUCTBATCH_ID;
			}
			// VALIDATE PRODUCTBATCH
			try {
				productBatch = dal.getProductBatchDao().getProductbatch(productBID); // get productbatch from database
				if (productBatch == null)
					return ENTER_PRODUCTBATCH_ID;
				formula = dal.getFormulaDao().getFormula(State.productBatch.getFormulaID()); // get formula from database
				productBatch.setStatus(2); // set productbatch status to "under production"
				dal.getProductBatchDao().updateProductbatch(productBatch); // update productbatch on database
			} catch (DALException e) {
				return INVALID_DATABASE;
			}
			// RETURN NEXT STATE
			return START_PROCESS;
		}

	},
	START_PROCESS {
		@Override
		State entry() throws WeightException {
			// VARIABLES
			boolean input;
			// CONFIRM: DISPLAY MESSAGE AND RECEIVE CONFIRMATION
			input = weightHandler.confirm("Confirm " + formula.getFormulaName());
			// VALIDATE CONFIRMATION
			if (!input) {
				try {
					productBatch.setStatus(1); // set productbatch status to "created"
					dal.getProductBatchDao().updateProductbatch(productBatch); // update productbatch on database
					return ENTER_PRODUCTBATCH_ID;
				} catch (DALException e) {
					return INVALID_DATABASE;
				}
			}
			// RETURN NEXT STATE
			return CLEAR_WEIGHT;
		}
	},
	CLEAR_WEIGHT {
		@Override
		State entry() throws WeightException {
			// INSTRUCTION: DISPLAY MESSAGE AND CONTINUE
			weightHandler.instruction("Clear weight");
			weightHandler.tare();
			// RETURN NEXT STATE
			return PLACE_CONTAINER;
		}

	},
	PLACE_CONTAINER {
		@Override
		State entry() throws WeightException {
			// INSTRUCTION: DISPLAY MESSAGE AND CONTINUE
			weightHandler.instruction("Place container");
			State.containerWeight = weightHandler.getWeight();
			weightHandler.tare();
			// RETURN NEXT STATE
			return ENTER_MATERIALBATCH_ID;
		}

	},
	ENTER_MATERIALBATCH_ID {
		@Override
		State entry() throws WeightException {
			// VARIABLES
			String input = "";
			int materialBatchID;

			// IMPLEMENT AUTO-GET-NEXT MATERIAL
			// try {
			// List<FormulaCompDTO> comps = dal.
			// for (FormulaCompDTO comp : comps) {
			// dal.getProductBatchCompDao().get
			// }
			// } catch (DALException e) {
			//
			// }
			// try {
			// material = dal.getMaterialDao().getMaterial(0);
			// } catch (DALException e1) {
			// e1.printStackTrace();
			// }

			// DIALOG: DISPLAY MESSAGE AND RECEIVE INPUT
			weightHandler.instruction("Find material " + State.material.getMaterialID() + " " + State.material.getMaterialName());
			input = weightHandler.dialog("Enter materialbatch ID");
			// VALIDATE INPUT TYPE
			try {
				materialBatchID = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				return ENTER_MATERIALBATCH_ID;
			}
			// VALIDATE MATERIALBATCH
			try {
				materialBatch = dal.getMaterialBatchDao().getMaterialBatch(materialBatchID);
				if (materialBatch == null)
					return ENTER_MATERIALBATCH_ID;
			} catch (DALException e) {
				return INVALID_DATABASE;
			}
			// VALIDATE MATERIALBATCH ID
			if (materialBatchID != State.materialBatch.getMbID())
				return ENTER_MATERIALBATCH_ID;
			// RETURN NEXT STATE
			return WEIGHING;
		}

	},
	WEIGHING {
		@Override
		State entry() throws WeightException {
			// VARIABLES
			double weight;
			// INSTRUCTION: DISPLAY MESSAGE AND CONTINUE
			weightHandler.instruction("Fill container");
			weight = weightHandler.getWeight();
			// CALCULATE TOLERANCE
			double tolerance = State.formulaBatch.getNomNetto() * 0.01 * State.formulaBatch.getTolerance();
			if (weight < State.formulaBatch.getNomNetto() - tolerance || weight > State.formulaBatch.getNomNetto() + tolerance)
				return WEIGHING;
			State.nettoWeight = weight;
			// RETURN NEXT STATE
			return BRUTTO_CONTROL;
		}

	},
	BRUTTO_CONTROL {
		@Override
		State entry() throws WeightException {
			// VARIABLES
			double weight;
			// INSTRUCTION: DISPLAY MESSAGE AND CONTINUE
			weightHandler.instruction("Remove container");
			weight = weightHandler.getWeight();
			// VALIDATE BRUTTO CONTROL
			if (weight != -State.containerWeight)
				return CLEAR_WEIGHT;
			return REGISTER_PRODUCTBATCH_COMP;
		}

	},
	REGISTER_PRODUCTBATCH_COMP {
		@Override
		State entry() {
			ProductbatchCompDTO pbCompDTO = new ProductbatchCompDTO(State.productBatch.getPbID(), materialBatch.getMbID(), State.user.getUserID(), State.nettoWeight, State.containerWeight
					+ State.containerWeight);
			// DISPLAY MESSAGE AND RECEIVE INPUT
			try {
				dal.getProductBatchCompDao().createProductbatchComp(pbCompDTO); // create productbatchcomponent on database
				pbCompDTO = dal.getProductBatchCompDao().getProductbatchComp(pbCompDTO.getPbID(), pbCompDTO.getMbID());
			} catch (DALException e) {
				return INVALID_DATABASE;
			}
			// VALIDATE PRODUCTBATCH COMPONENT
			if (pbCompDTO == null) {
				return CLEAR_WEIGHT;
			}
			return ENTER_USER_ID; // UPDATE STATUS
		}

	},
	;
	private static FormulaCompDTO formulaBatch;
	private static MaterialDTO material;
	private static MaterialbatchDTO materialBatch;
	private static FormulaDTO formula;
	private static FormulaCompDTO formulaComp;
	private static UserDTO user;
	private static ProductbatchDTO productBatch;
	private static IDAL dal;
	private static IWeightHandler weightHandler;
	private static double containerWeight; // tare
	private static double nettoWeight;

	abstract State entry() throws WeightException;

	static void initialize(IDAL dal, IWeightHandler weightHandler) {
		State.dal = dal;
		State.weightHandler = weightHandler;
	}

}