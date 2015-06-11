package dtu.cdio_final.client.view;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialTextBox;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
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
	
	private boolean createAccess;
	
	
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
	@UiField MaterialListBox createMaterialID;
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
		materialBatchTable.setWidget(numberOfRows, 0, new Label("" + materialBatch.getMbID()));
		materialBatchTable.setWidget(numberOfRows, 1, new Label("" + materialBatch.getMaterialID() + " : "));
		materialsID.add(materialBatch.getMaterialID());
		materialBatchTable.setWidget(numberOfRows, 3, new Label("" + materialBatch.getQuantity()));
		
		numberOfRows++;
	}

	private class MaterialCallback extends TokenAsyncCallback<List<MaterialDTO>> {

		private final List<Integer> materials;

		MaterialCallback(List<Integer> materials) {
			this.materials = materials;
		}

		@Override
		public void onFailure(Throwable caught) {
			super.onFailure(caught);
			}
		
		@Override
		public void onSuccess(List<MaterialDTO> materialCallBack) {
			for (int i = 0; i < materials.size(); i++) {
				for (int j = 0; j < materialCallBack.size(); j++) {
					if (materials.get(i) == materialCallBack.get(j).getMaterialID())
						materialBatchTable.setWidget(i + 1, 2, new Label(materialCallBack.get(j).getMaterialName()));
				}
			}
			for(int i = 0; i < materials.size(); i++)
			{
				if(!containsElement(createMaterialID, materialCallBack.get(i).getMaterialName()))
					createMaterialID.addItem(materialCallBack.get(i).getMaterialName());
			}
		}
	}
	
	@UiHandler("createMaterialBatchButton")
	void createMaterialBatch(ClickEvent event){
		int materialBatchIDInt2 = Integer.valueOf(createMaterialBatchID.getText());
		int materialIDInt2 = Integer.valueOf(createMaterialID.getSelectedIndex()+1);
		double quantityDouble2 = Double.valueOf(createQuantity.getText());
		final MaterialbatchDTO newMaterialBatch = new MaterialbatchDTO(materialBatchIDInt2, materialIDInt2, quantityDouble2);
		
		
		service.createMaterialBatch(newMaterialBatch, new TokenAsyncCallback<Void>(){

			@Override
			public void onSuccess(Void result) {
				// Add a new row to the table, when the database query has been completed.
				addRow(newMaterialBatch);
				
				// Clear the create fields
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
	private boolean containsElement(MaterialListBox box , String element){
		for(int i = 0; i < box.getItemCount(); i++)
			if(box.getItemText(i).equals(element))
				return true;
		return false;
	}
}
	