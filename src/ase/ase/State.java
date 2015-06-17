package ase.ase;

import ase.ase.IWeightHandler.WeightException;
import dtu.cdio_final.server.dal.daointerfaces.DALException;
import dtu.cdio_final.shared.dto.FormulaCompDTO;
import dtu.cdio_final.shared.dto.FormulaDTO;
import dtu.cdio_final.shared.dto.MaterialDTO;
import dtu.cdio_final.shared.dto.MaterialbatchDTO;
import dtu.cdio_final.shared.dto.ProductbatchCompDTO;
import dtu.cdio_final.shared.dto.ProductbatchDTO;
import dtu.cdio_final.shared.dto.UserDTO;

public enum State {
	START {
		@Override
		public
		State entry() throws WeightException {
			// CONNECT/RECONNECT
			weightHandler.connect();
			dal.connect();
			// RESET

			formulaComp = null;
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
		public
		State entry() throws WeightException {
			weightHandler.instruction("Database error!");
			throw new WeightException();
		}
	},
	ENTER_USER_ID {
		@Override
		public
		State entry() throws WeightException, DALException {
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
			user = dal.getUserDao().getUser(userID); // get user from database
			if (user == null)
				return ENTER_USER_ID;
			if (user.getRole() != 4)
				return ENTER_USER_ID;
			if (user.getStatus() != 1)
				return ENTER_USER_ID;
			// RETURN NEXT STATE
			return CONFIRM_OPERATOR;
		}
	},
	CONFIRM_OPERATOR {
		@Override
		public
		State entry() throws WeightException {
			// VARIABLES
			boolean input;
			// CONFIRM: DISPLAY MESSAGE AND RECEIVE CONFIRMATION
			input = weightHandler.confirm("Confirm User: " + State.user.getIni());
			// VALIDATE CONFIRMATION
			if (!input)
				return ENTER_USER_ID;
			// RETURN NEXT STATE
			return ENTER_PRODUCTBATCH_ID;

		}
	},
	ENTER_PRODUCTBATCH_ID {
		@Override
		public
		State entry() throws WeightException, DALException {
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
			productBatch = dal.getProductBatchDao().getProductbatch(productBID); // get productbatch from database
			if (productBatch == null)
				return ENTER_PRODUCTBATCH_ID;
			
			// UPDATE STATUS
			productBatch.setStatus(2); // set productbatch status to "under production"
			dal.getProductBatchDao().updateProductbatch(productBatch); // update productbatch on database
			// RETURN NEXT STATE
			return START_PROCESS;
		}

	},
	START_PROCESS {
		@Override
		public
		State entry() throws WeightException, DALException {
			// VARIABLES
			boolean input;
			// CONFIRM: DISPLAY MESSAGE AND RECEIVE CONFIRMATION
			formula = dal.getFormulaDao().getFormula(State.productBatch.getFormulaID()); // get formula from database
			input = weightHandler.confirm("Confirm: " + formula.getFormulaName());
			// VALIDATE CONFIRMATION
			if (!input) {
				productBatch.setStatus(1); // set productbatch status to "created"
				dal.getProductBatchDao().updateProductbatch(productBatch); // update productbatch on database
				return ENTER_PRODUCTBATCH_ID;
			}
			material = dal.getProductBatchDao().getNextMaterial(productBatch.getPbID());
			if (material == null)
				return ENTER_PRODUCTBATCH_ID;
			// RETURN NEXT STATE
			return CLEAR_WEIGHT;
		}
	},
	CLEAR_WEIGHT {
		@Override
		public
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
		public
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
		public
		State entry() throws WeightException, DALException {
			// VARIABLES
			String input = "";
			int materialBatchID;
			// DIALOG: DISPLAY MESSAGE AND RECEIVE INPUT
			weightHandler.instruction("Find " + material.getMaterialID());
			input = weightHandler.dialog("Enter MaterialbatchID");
			// VALIDATE INPUT TYPE
			try {
				materialBatchID = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				return ENTER_MATERIALBATCH_ID;
			}
			// VALIDATE MATERIALBATCH
			materialBatch = dal.getMaterialBatchDao().getMaterialBatch(materialBatchID);
			if (materialBatch == null)
				return ENTER_MATERIALBATCH_ID;
			// VALIDATE MATERIALBATCH ID
			if (materialBatch.getMaterialID() != material.getMaterialID())
				return ENTER_MATERIALBATCH_ID;
			formulaComp = dal.getFormulaCompDao().getFormulaComp(State.productBatch.getFormulaID(), material.getMaterialID()); // get formula from database
			if (materialBatch.getQuantity() < formulaComp.getNomNetto())
				return ENTER_MATERIALBATCH_ID;
			// RETURN NEXT STATE
			return WEIGHING;
		}

	},
	WEIGHING {
		@Override
		public
		State entry() throws WeightException, DALException {
			// VARIABLES
			double weight;
			// INSTRUCTION: DISPLAY MESSAGE AND CONTINUE
			weight = weightHandler.weigh("Fill container at " + formulaComp.getNomNetto() + "kg");
			// CALCULATE TOLERANCE
			double tolerance = 10;
			// double tolerance = State.formulaComp.getTolerance();
			
			double diff = Math.abs(weight - State.formulaComp.getNomNetto());
			double acceptedDiff = (State.formulaComp.getNomNetto() / 100) * tolerance;
			
			if (diff > acceptedDiff)
				return WEIGHING;
			
			State.nettoWeight = weight;
			// RETURN NEXT STATE
			return BRUTTO_CONTROL;
		}
	},
	BRUTTO_CONTROL {
		@Override
		public
		State entry() throws WeightException {
			// VARIABLES
			double weight;
			// INSTRUCTION: DISPLAY MESSAGE AND CONTINUE
			weightHandler.instruction("Remove container");
			weight = weightHandler.getWeight();
			System.out.println("BC " + State.containerWeight);
			System.out.println("BC " + State.nettoWeight);
			System.out.println("BC " + weight);
			// VALIDATE BRUTTO CONTROL
			if (weight != -State.containerWeight)
				return INVALID_WEIGHING;
			return REGISTER_PRODUCTBATCH_COMP;
		}
	},
	INVALID_WEIGHING {
		@Override
		public
		State entry() throws WeightException {
			// INSTRUCTION: DISPLAY MESSAGE AND CONTINUE
			weightHandler.instruction("Invalid weighing, re-do!");
			// RETURN NEXT STATE
			return CLEAR_WEIGHT;
		}
	},
	REGISTER_PRODUCTBATCH_COMP {
		@Override
		public
		State entry() throws WeightException, DALException {
			ProductbatchCompDTO pbCompDTO = new ProductbatchCompDTO(State.productBatch.getPbID(), materialBatch.getMbID(), State.user.getUserID(), State.nettoWeight, State.containerWeight
					+ State.containerWeight);
			materialBatch.setQuantity(materialBatch.getQuantity()-State.nettoWeight);
			
			// DISPLAY MESSAGE AND RECEIVE INPUT
			dal.getProductBatchCompDao().createProductbatchComp(pbCompDTO); // create productbatchcomponent on database
			dal.getMaterialBatchDao().updateMaterialBatch(materialBatch);

			material = dal.getProductBatchDao().getNextMaterial(productBatch.getPbID());

			// VALIDATE PRODUCTBATCH COMPONENT
			if (material != null) {
				return CLEAR_WEIGHT;
			}
			productBatch.setStatus(3); // set productbatch status to "done"
			dal.getProductBatchDao().updateProductbatch(productBatch); // update productbatch on database
			return PRODUCTION_DONE; // UPDATE STATUS
		}
	},
	NEXT_WEIGHING {
		@Override
		public
		State entry() throws WeightException {
			// INSTRUCTION: DISPLAY MESSAGE AND CONTINUE
			weightHandler.instruction("Productbatch registered");
			// RETURN NEXT STATE
			return CLEAR_WEIGHT;
		}
	},
	PRODUCTION_DONE {
		@Override
		public State entry() throws WeightException {
			// INSTRUCTION: DISPLAY MESSAGE AND CONTINUE
			weightHandler.instruction("Production done!");
			// RETURN NEXT STATE
			return ENTER_USER_ID;
		}

	};
	
	private static FormulaCompDTO formulaComp;
	private static MaterialDTO material;
	private static MaterialbatchDTO materialBatch;
	private static FormulaDTO formula;
	private static UserDTO user;
	private static ProductbatchDTO productBatch;
	private static IDAL dal;
	private static IWeightHandler weightHandler;
	private static double containerWeight; // tare
	private static double nettoWeight;

	public abstract State entry() throws WeightException, DALException;

	public static void initialize(IDAL dal, IWeightHandler weightHandler) {
		State.dal = dal;
		State.weightHandler = weightHandler;
	}

}