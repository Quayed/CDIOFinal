package ase.ase;

import java.util.List;

import ase.ase.IWeightHandler.WeightException;
import dtu.cdio_final.server.dal.daointerfaces.*;
import dtu.cdio_final.shared.dto.*;

public enum State {
	ENTER_USER_ID {
		@Override
		State entry() {
			dal.connect();
			weightHandler.connect();
			State.user = null;
			State.formula = null;
			State.productBatch = null;
			String input = "";
			int userID;

			// DISPLAY MESSAGE AND RECEIVE INPUT
			try {
				input = weightHandler.dialog("Enter User ID");
			} catch (WeightException e) {
				e.printStackTrace();
				return ENTER_USER_ID;
			}
			// VALIDATE INPUT TYPE
			try {
				userID = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Not a number");
				return ENTER_USER_ID;
			}
			// GET USER-ENTITY FROM DATABASE
			try {
				user = dal.getUserDao().getUser(userID);
			} catch (DALException e) {
				e.printStackTrace();
				return ENTER_USER_ID;
			}
			
			if(user == null){
				// no user
				return ENTER_USER_ID;
			}
			
			// RETURN NEXT STATE
			return CONFIRM_OPERATOR;
		}
	},
	CONFIRM_OPERATOR {
		@Override
		State entry() {
			boolean input;

			// DISPLAY MESSAGE AND RECEIVE INPUT
			try {
				input = weightHandler.confirm("Confirm " + State.user.getUserName());
			} catch (WeightException e) {
				e.printStackTrace();
				return ENTER_USER_ID;
			}

			// RETURN NEXT STATE
			if (input)
				return ENTER_PRODUCTBATCH_ID;
			
			return ENTER_USER_ID;
		}
	},
	ENTER_PRODUCTBATCH_ID {
		@Override
		State entry() {
			String input = "";
			int productBID;

			// DISPLAY MESSAGE AND RECEIVE INPUT
			try {
				input = weightHandler.dialog("Enter Productbatch ID");
			} catch (WeightException e) {
				e.printStackTrace();
				return ENTER_USER_ID;
			}
			// VALIDATE INPUT TYPE
			try {
				productBID = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Not a number");
				return ENTER_PRODUCTBATCH_ID;
			}
			// GET PRODUCTBATCH-ENTITY AND FORMULA-ENTITY FROM DATABASE
			try {
				productBatch = dal.getProductBatchDao().getProductbatch(productBID);
				if(productBatch == null)
					return ENTER_PRODUCTBATCH_ID;
				formula = dal.getFormulaDao().getFormula(State.productBatch.getFormulaID());
			} catch (DALException e) {
				e.printStackTrace();
				return ENTER_USER_ID;
			}
			// RETURN NEXT STATE
			try {
				productBatch.setStatus(1); // set status "under production"
				dal.getProductBatchDao().updateProductbatch(productBatch);
			} catch (DALException e) {
				e.printStackTrace();
				return ENTER_USER_ID;
			}
			return START_PROCESS;
		}

	},
	START_PROCESS {
		@Override
		State entry() {
			boolean input;

			// DISPLAY MESSAGE AND RECEIVE INPUT
			try {
				input = weightHandler.confirm("Confirm " + formula.getFormulaName());
			} catch (WeightException e) {
				e.printStackTrace();
				return ENTER_USER_ID;
			}

			// RETURN NEXT STATE
			if (!input) {
				try {
					productBatch.setStatus(0); // set status "created"
					dal.getProductBatchDao().updateProductbatch(productBatch);
				} catch (DALException e) {
					e.printStackTrace();
					return ENTER_USER_ID;
				}
				return ENTER_PRODUCTBATCH_ID;
			}
			return CLEAR_WEIGHT;
		}
	},
	CLEAR_WEIGHT {
		@Override
		State entry() {
			// DISPLAY MESSAGE AND RECEIVE INPUT
			try {
				weightHandler.instruction("Clear weight");
				weightHandler.tare();
			} catch (WeightException e) {
				e.printStackTrace();
				return ENTER_USER_ID;
			}
			// RETURN NEXT STATE
			return PLACE_CONTAINER;
		}

	},
	PLACE_CONTAINER {
		@Override
		State entry() {
			// DISPLAY MESSAGE AND RECEIVE INPUT
			try {
				weightHandler.instruction("Place container");
				State.containerWeight = weightHandler.getWeight();
				// State.tare = weightHandler.tare(); // this correct ??
			} catch (WeightException e) {
				e.printStackTrace();
				return ENTER_USER_ID;
			}
			// RETURN NEXT STATE
			return ENTER_MATERIALBATCH_ID;
		}

	},
	ENTER_MATERIALBATCH_ID {

		@Override
		State entry() {
			String input = "";
			int materialBatchID;
			
			try {
				List<FormulaCompDTO> comps = dal.getFormulaCompDao().getFormulaCompList(productBatch.getFormulaID());
				for(FormulaCompDTO comp : comps){
					
				}
					
			} catch (DALException e) {

			}
			
			// DISPLAY MESSAGE AND RECEIVE INPUT
			try {
				
				// IMPLEMENT AUTO-GET-NEXT MATERIAL
				material = dal.getMaterialDao().getMaterial(0);
				materialBatch = dal.getMaterialBatchDao().getMaterialBatch(0);
				formulaBatch = dal.getFormulaCompDao().getFormulaComp(State.productBatch.getFormulaID(), State.material.getMaterialID());
			} catch (DALException e1) {
				e1.printStackTrace();
			}
			try {
				weightHandler.instruction("Find material " + State.material.getMaterialID() + " " + State.material.getMaterialName());
				input = weightHandler.dialog("Enter materialbatch ID");
			} catch (WeightException e) {
				e.printStackTrace();
				return ENTER_USER_ID;
			}
			// VALIDATE INPUT TYPE
			try {
				materialBatchID = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Not a number");
				return ENTER_MATERIALBATCH_ID;
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
		State entry() {
			double weight;

			// DISPLAY MESSAGE AND RECEIVE INPUT
			try {
				weightHandler.instruction("Fill container");
				weight = weightHandler.getWeight();
			} catch (WeightException e) {
				e.printStackTrace();
				return ENTER_USER_ID;
			}

			if (weight < State.formulaBatch.getNomNetto() - State.formulaBatch.getTolerance() || weight > State.formulaBatch.getNomNetto() + State.formulaBatch.getTolerance())
				return WEIGHING;
			else
				return BRUTTO_CONTROL;
		}

	},
	BRUTTO_CONTROL {
		@Override
		State entry() {
			double weight;

			// DISPLAY MESSAGE AND RECEIVE INPUT
			try {
				weightHandler.instruction("Remove container");
				weight = weightHandler.getWeight();
			} catch (WeightException e) {
				e.printStackTrace();
				return ENTER_USER_ID;
			}

			if (weight != State.containerWeight)
				return CLEAR_WEIGHT;
			State.brutto_weight = weight;
			return REGISTER_PRODUCTBATCH_COMP;
		}

	},
	REGISTER_PRODUCTBATCH_COMP {
		@Override
		State entry() {
			ProductbatchCompDTO pbCompDTO = new ProductbatchCompDTO(State.productBatch.getPbID(), materialBatch.getMbID(), State.user.getUserID(), State.brutto_weight, State.brutto_weight
					+ State.containerWeight);

			// DISPLAY MESSAGE AND RECEIVE INPUT
			try {
				dal.getProductBatchCompDao().createProductbatchComp(pbCompDTO);
			} catch (DALException e) {
				e.printStackTrace();
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
	private static double containerWeight;
	private static double brutto_weight;

	abstract State entry();

	static void initialize(IDAL dal, IWeightHandler weightHandler) {
		State.dal = dal;
		State.weightHandler = weightHandler;
	}

}