package dtu.cdio_final.client.view;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import dtu.cdio_final.client.Group13cdio_final;
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
	List<Integer> materialBatchesID;
	Map<Integer, String> materialNames;
	
	private boolean validMaterialBatchID;
	private boolean validQuantity;

	public MaterialBatchComposite(DataServiceAsync service, boolean create) {
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
		this.createAccess = create;
		reloadPage();
	}

	@Override
	public void reloadPage() {
		materialBatchTable.clear();
		numberOfRows = 1;
		materialBatchesID = new ArrayList<Integer>();
		materialNames = new HashMap<Integer, String>();
		materialBatchTable.setWidget(0, 0, new Label("Material Batch ID"));
		materialBatchTable.setWidget(0, 1, new Label("Material ID"));
		materialBatchTable.setWidget(0, 2, new Label("Quantity(KG)"));
		createMaterialBatchButton.addStyleName("fullWidth");

		if(!createAccess){
			createBox.setVisible(false);
		}
		
		service.getMaterialBatches(Group13cdio_final.token, new TokenAsyncCallback<List<MaterialbatchDTO>>() {
			
			@Override
			public void onSuccess(List<MaterialbatchDTO> materialBatches) 
			{
				for (int i = 0; i < materialBatches.size(); i++) {
					addRow(materialBatches.get(i));
				}
			}

		});
		
		service.getMaterials(Group13cdio_final.token, new MaterialCallback(materialBatchesID));
	}

	private class MaterialCallback extends TokenAsyncCallback<List<MaterialDTO>> {

		private final List<Integer> materialBatches;

		MaterialCallback(List<Integer> materialBatches) {
			this.materialBatches = materialBatches;
		}
		
		@Override
		public void onSuccess(List<MaterialDTO> materials) {
			for (MaterialDTO material : materials) {
				materialNames.put(material.getMaterialID(), material.getMaterialID()+" ("+material.getMaterialName()+")");
				createMaterialID.addItem(materialNames.get(material.getMaterialID()), ""+material.getMaterialID());
			}
			for (int i = 0; i < materialBatches.size(); i++) {
				materialBatchTable.setWidget(i + 1, 1, new Label(materialNames.get(materialBatches.get(i))));
			}
		}
	}
	
	private void addRow(MaterialbatchDTO materialBatch) {
		materialBatchesID.add(materialBatch.getMaterialID());
		materialBatchTable.setWidget(numberOfRows, 0, new Label("" + materialBatch.getMbID()));
		materialBatchTable.setWidget(numberOfRows, 1, new Label("" + materialNames.get(materialBatch.getMaterialID())));
		materialBatchTable.setWidget(numberOfRows, 2, new Label("" + materialBatch.getQuantity() + " kg"));
		
		numberOfRows++;
	}
	
	@UiHandler("createMaterialBatchButton")
	void createMaterialBatch(ClickEvent event){
		if(!checkForm())
			return;
		
		int materialBatchIDInt2 = Integer.valueOf(createMaterialBatchID.getText());
		int materialIDInt2 = Integer.valueOf(createMaterialID.getValue(createMaterialID.getSelectedIndex()));
		double quantityDouble2 = Double.valueOf(createQuantity.getText());
		final MaterialbatchDTO newMaterialBatch = new MaterialbatchDTO(materialBatchIDInt2, materialIDInt2, quantityDouble2);
		
		service.createMaterialBatch(Group13cdio_final.token, newMaterialBatch, new TokenAsyncCallback<Void>(){
			@Override
			public void onSuccess(Void result) {
				// Add a new row to the table, when the database query has been completed.
				addRow(newMaterialBatch);
				
				// Clear the create fields
				createMaterialBatchID.setText("");
				createMaterialBatchID.backToDefault();
				createQuantity.setText("");
				createQuantity.backToDefault();
				
				MaterialToast.alert("The user has been created!");
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
	
	private boolean checkForm(){
		if (validMaterialBatchID && validQuantity){
			createMaterialBatchButton.setDisable(false);
			return true;
		} else{
			createMaterialBatchButton.setDisable(true);
			return false;
		}
	}
	private boolean containsElement(MaterialListBox box , String element){
		for(int i = 0; i < box.getItemCount(); i++)
			if(box.getItemText(i).equals(element))
				return true;
		return false;
	}
}
	