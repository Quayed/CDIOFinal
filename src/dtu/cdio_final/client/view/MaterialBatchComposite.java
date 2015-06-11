package dtu.cdio_final.client.view;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialTextBox;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
//import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.client.service.TokenAsyncCallback;
import dtu.cdio_final.shared.FieldVerifier;
import dtu.cdio_final.shared.dto.MaterialDTO;
import dtu.cdio_final.shared.dto.MaterialbatchDTO;

public class MaterialBatchComposite extends PageComposite {

	interface MaterialBatchCompositeUiBinder extends
			UiBinder<Widget, MaterialBatchComposite> {
	}

	private static MaterialBatchCompositeUiBinder uiBinder = GWT
			.create(MaterialBatchCompositeUiBinder.class);
	
//	private boolean updateAccess;
	private boolean createAccess;
	
	
//	private int editRow = -1;
	private int numberOfRows;

	@UiField MaterialCollapsible createBox;
	
	@UiField FlexTable materialBatchTable;
	@UiField MaterialButton submitButton;
	@UiField MaterialButton cancelButton;

	@UiField TextBox materialBatchID;
	@UiField TextBox materialID;
	@UiField TextBox materialName;
	@UiField TextBox quantity;

	@UiField MaterialTextBox createMaterialBatchID;
	@UiField MaterialTextBox createMaterialID;
	@UiField MaterialTextBox createQuantity;
	@UiField MaterialButton createMaterialBatchButton;

	DataServiceAsync service;
	ArrayList<Integer> materialsID;
	
	private boolean validMaterialBatchID;
	private boolean validQuantity;

	public MaterialBatchComposite(DataServiceAsync service, boolean create/*, boolean update*/) {
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
//		this.updatePermission = update;
		this.createAccess = create;
	}

	@Override
	public void reloadPage() {
		initTable();
	}

	private void initTable() {
		numberOfRows = 1;
		materialsID = new ArrayList<Integer>();
		materialBatchTable.setWidget(0, 0, new Label("Material Batch ID"));
		materialBatchTable.setWidget(0, 1, new Label("Material ID"));
		materialBatchTable.setWidget(0, 2, new Label("Material Name"));
		materialBatchTable.setWidget(0, 3, new Label("Quantity"));
		createMaterialBatchButton.addStyleName("fullWidth");
//		if(updatePermission){
//			materialBatchTable.setWidget(0, 4, new Label(""));
//			materialBatchTable.setWidget(0, 5, new Label(""));
//		}


		if(!createAccess){
			createBox.setVisible(false);
		}
		
		service.getMaterialBatches(new TokenAsyncCallback<List<MaterialbatchDTO>>() {
			
			@Override
			public void onFailure(Throwable caught)
			{
				super.onFailure(caught);

			}
			
			@Override
			public void onSuccess(List<MaterialbatchDTO> materialBatches) 
			{
				for (int i = 0; i < materialBatches.size(); i++) 
				{
					addRow(materialBatches.get(i));
				}
				service.getMaterials(new MaterialCallback(materialsID));
				
			}

		});
	}
	
	private void addRow(MaterialbatchDTO materialBatch) {
		//TODO materialName
		materialBatchTable.setWidget(numberOfRows, 0, new Label("" + materialBatch.getMbID()));
		materialBatchTable.setWidget(numberOfRows, 1, new Label("" + materialBatch.getMaterialID() + " : "));
		materialsID.add(materialBatch.getMaterialID());
		materialBatchTable.setWidget(numberOfRows, 3, new Label("" + materialBatch.getQuantity()));
		
		/*
		if(updatePermission){
		materialBatchTable.setWidget(numberOfRows, 4, new MaterialButton(
				"mdi-content-create", "blue", "", "light", ""));
		((MaterialButton) materialBatchTable.getWidget(numberOfRows, 4))
				.addClickHandler(new editClick());
		materialBatchTable.getFlexCellFormatter().setStyleName(
				numberOfRows, 4, "limitWidth");
		
		materialBatchTable.setWidget(numberOfRows, 5, new Label(""));
		materialBatchTable.getFlexCellFormatter().setStyleName(
				numberOfRows, 5, "limitWidth");
		}
		*/
		numberOfRows++;
	}

	private class MaterialCallback extends TokenAsyncCallback<List<MaterialDTO>> {

		private final List<Integer> materials;

		MaterialCallback(List<Integer> materials) {
			this.materials = materials;
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onSuccess(List<MaterialDTO> materialCallBack) {
			for (int i = 0; i < materials.size(); i++) {
				for (int j = 0; j < materialCallBack.size(); j++) {
					if (materials.get(i) == materialCallBack.get(j).getMaterialID())
						materialBatchTable.setWidget(i + 1, 2, new Label(materialCallBack.get(j).getMaterialName()));
				}
			}
		}
	}
	
	@UiHandler("createMaterialBatchButton")
	void createMaterialBatch(ClickEvent event){
		int materialBatchIDInt2 = Integer.valueOf(createMaterialBatchID.getText());
		int materialIDInt2 = Integer.valueOf(createMaterialID.getText());
		double quantityDouble2 = Double.valueOf(createQuantity.getText());
		final MaterialbatchDTO newMaterialBatch = new MaterialbatchDTO(materialBatchIDInt2, materialIDInt2, quantityDouble2);
		
		
		service.createMaterialBatch(newMaterialBatch, new TokenAsyncCallback<Void>(){

			@Override
			public void onSuccess(Void result) {
				// Add a new row to the table, when the database query has been completed.
				addRow(newMaterialBatch);
				
				// Clear the create fields
				createMaterialID.setText("");
				createMaterialID.backToDefault();
				createMaterialBatchID.setText("");
				createMaterialBatchID.backToDefault();
				createQuantity.setText("");
				createQuantity.backToDefault();
				
				Window.alert("The user has been created!");
			}
		});
	}
	
	@UiHandler("createMaterialBatchID")
	void keyUpBatchID(KeyUpEvent e) {
		if(FieldVerifier.isValidID(createMaterialBatchID.getText())){
			createMaterialBatchID.removeStyleName("invalidEntry");
			validMaterialBatchID = true;
		} else{
			createMaterialBatchID.addStyleName("invalidEntry");
			validMaterialBatchID = false;
		}
		checkForm();
	}
	
	@UiHandler("createQuantity")
	void keyUpProvider(KeyUpEvent e) {
		if(FieldVerifier.isValidQuantity(createQuantity.getText())){
			createQuantity.removeStyleName("invalidEntry");
			validQuantity = true;
		} else{
			createQuantity.addStyleName("invalidEntry");
			validQuantity = false;
		
		}
		checkForm();
	}
	
	private void checkForm(){
		if (validMaterialBatchID && validQuantity){
			createMaterialBatchButton.setDisable(false);
		} else{
			createMaterialBatchButton.setDisable(true);
		}
	}
}
	
	/*
	private class editClick implements ClickHandler {

		private String getTableLabelText(int column) {
			return ((Label) materialBatchTable.getWidget(editRow + 1, column))
					.getText();
		}
		
		@Override
		public void onClick(ClickEvent event) {
			if (editRow > -1) {
				cancelButton.fireEvent(new ClickEvent() {
				});
			}

			editRow = materialBatchTable.getCellForEvent(event).getRowIndex();
			materialBatchTable.insertRow(editRow);
			materialBatchTable.getRowFormatter().setVisible(editRow + 1, false);

			materialBatchID.setText(getTableLabelText(0));
			materialBatchTable.setWidget(editRow, 0, materialBatchID);

			materialID.setText(getTableLabelText(1));
			materialBatchTable.setWidget(editRow, 1, materialID);
			materialBatchTable.getFlexCellFormatter().setColSpan(editRow, 1, 2);

//			materialName.setText(getTableLabelText(2));
//			materialBatchTable.setText(editRow, 2, materialName.getText());

			quantity.setText(getTableLabelText(3));
			materialBatchTable.setWidget(editRow, 3, quantity);

			materialBatchTable.setWidget(editRow, 4, submitButton);

			materialBatchTable.setWidget(editRow, 5, cancelButton);
		}

	}
	
	
	@UiHandler("submitButton")
	void submitClickHandler(ClickEvent event) {

		int materialBatchIDInt = Integer.valueOf(materialBatchID.getText());
		int materialIDInt = Integer.valueOf(materialID.getText());
		double quantityDouble = Double.valueOf(quantity.getText());

		MaterialbatchDTO materialBatch = new MaterialbatchDTO(
				materialBatchIDInt, materialIDInt, quantityDouble);
		service.updateMaterialBatch(materialBatch, new TokenAsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				((Label) materialBatchTable.getWidget(editRow + 1, 0))
						.setText(materialBatchID.getText());
				((Label) materialBatchTable.getWidget(editRow + 1, 1))
						.setText(materialID.getText());
				((Label) materialBatchTable.getWidget(editRow + 1, 2))
						.setText(materialName.getText());
				((Label) materialBatchTable.getWidget(editRow + 1, 3))
						.setText(quantity.getText());

				cancelButton.fireEvent(new ClickEvent() {
				});
				Window.alert("Material Batch has been updated!");

			}

		});
	}

	@UiHandler("cancelButton")
	void cancelClickHandler(ClickEvent event) {

		materialBatchTable.getRowFormatter().setVisible(editRow + 1, true);
		materialBatchTable.getRowFormatter().setVisible(editRow, false);
		materialBatchTable.removeRow(editRow);
		editRow = -1;
	}
	*/

