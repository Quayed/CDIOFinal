package dtu.cdio_final.client.view;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialTextBox;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import dtu.cdio_final.shared.dto.MaterialDTO;
import dtu.cdio_final.shared.dto.MaterialbatchDTO;

public class MaterialBatchComposite extends PageComposite {

	interface MaterialBatchCompositeUiBinder extends
			UiBinder<Widget, MaterialBatchComposite> {
	}

	private static MaterialBatchCompositeUiBinder uiBinder = GWT
			.create(MaterialBatchCompositeUiBinder.class);
	private int editRow = -1;
	private int numberOfRows = 1;

	@UiField
	FlexTable materialBatchTable;
	@UiField
	MaterialButton submitButton;
	@UiField
	MaterialButton cancelButton;

	@UiField
	TextBox materialBatchID;
	@UiField
	TextBox materialID;
	@UiField
	TextBox materialName;
	@UiField
	TextBox quantity;

	@UiField
	MaterialTextBox createMaterialBatchID;
	@UiField
	MaterialTextBox createMaterialID;
	@UiField
	MaterialTextBox createQuantity;
	@UiField
	MaterialButton createMaterialBatchButton;

	DataServiceAsync service;
	ArrayList<Integer> materialsID = new ArrayList<Integer>();

	public MaterialBatchComposite(DataServiceAsync service) {
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
		initTable();
	}

	@Override
	public void reloadPage() {
		// TODO Auto-generated method stub

	}

	private void initTable() {
		materialBatchTable.setWidget(0, 0, new Label("Material Batch ID"));
		materialBatchTable.setWidget(0, 1, new Label("Material ID"));
		materialBatchTable.setWidget(0, 2, new Label("Material Name"));
		materialBatchTable.setWidget(0, 3, new Label("Quantity"));
		materialBatchTable.setWidget(0, 4, new Label(""));
		materialBatchTable.setWidget(0, 5, new Label(""));
		createMaterialBatchButton.addStyleName("fullWidth");

		service.getMaterialBatches(new TokenAsyncCallback<List<MaterialbatchDTO>>() {

			@Override
			public void onSuccess(List<MaterialbatchDTO> materialBatches) {
				for (int i = 0; i < materialBatches.size(); i++) {
					materialBatchTable.setWidget(i + 1, 0, new Label(""
							+ materialBatches.get(i).getMbID()));
					materialBatchTable.setWidget(i + 1, 1, new Label(""
							+ materialBatches.get(i).getMaterialID()));
					materialsID.add(materialBatches.get(i).getMaterialID());
					materialBatchTable.setWidget(i + 1, 3, new Label(""
							+ materialBatches.get(i).getQuantity()));
					materialBatchTable.setWidget(i + 1, 4, new MaterialButton(
							"mdi-content-create", "blue", "", "light", ""));
					((MaterialButton) materialBatchTable.getWidget(i + 1, 4))
							.addClickHandler(new editClick());
					materialBatchTable.getFlexCellFormatter().setStyleName(
							i + 1, 4, "limitWidth");
					materialBatchTable.setWidget(i + 1, 5, new Label(""));
					materialBatchTable.getFlexCellFormatter().setStyleName(
							i + 1, 5, "limitWidth");
					numberOfRows++;
				}
				service.getMaterials(new MaterialCallback(materialsID));

			}

		});
	}

	private class MaterialCallback extends TokenAsyncCallback<List<MaterialDTO>> {

		private final List<Integer> materials;

		MaterialCallback(List<Integer> materials) {
			this.materials = materials;
		}

		@Override
		public void onSuccess(List<MaterialDTO> materialCallBack) {
			for (int i = 0; i < materials.size(); i++) {
				for (int j = 0; j < materialCallBack.size(); j++) {
					if (materials.get(i) == materialCallBack.get(j)
							.getMaterialID())
						materialBatchTable.setWidget(i + 1, 2, new Label(
								materialCallBack.get(j).getMaterialName()));
				}
			}
		}
	}

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

			materialName.setText(getTableLabelText(2));
			materialBatchTable.setWidget(editRow, 2, materialName);

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

	@UiHandler("createMaterialBatchButton")
	void createMaterialBatch(ClickEvent event){
		int materialBatchIDInt2 = Integer.valueOf(createMaterialBatchID.getText());
		int materialIDInt2 = Integer.valueOf(createMaterialID.getText());
		double quantityDouble2 = Double.valueOf(createQuantity.getText());
		final MaterialbatchDTO newMaterialBatch = new MaterialbatchDTO(materialBatchIDInt2, materialIDInt2, quantityDouble2);
		// todo insert : , createMaterialName.getText(),
		
		
		service.createMaterialBatch(newMaterialBatch, new TokenAsyncCallback<Void>(){

			@Override
			public void onSuccess(Void result) {
				// Add a new row to the table, when the database query has been completed.
				materialBatchTable.setWidget(numberOfRows + 1, 0, new Label("" + newMaterialBatch.getMbID()));
				materialBatchTable.setWidget(numberOfRows + 1, 1, new Label("" + newMaterialBatch.getMaterialID()));
				materialBatchTable.setWidget(numberOfRows + 1, 2, new Label("TEST"));
//				materialsID.add(newMaterialBatch.get(numberOfRows + 1).getMaterialID());
				materialBatchTable.setWidget(numberOfRows + 1, 3, new Label("" + newMaterialBatch.getQuantity()));
				materialBatchTable.setWidget(numberOfRows + 1, 4, new MaterialButton("mdi-content-create", "blue", "", "light", ""));
				((MaterialButton)materialBatchTable.getWidget(numberOfRows + 1, 4)).addClickHandler(new editClick());
				materialBatchTable.getFlexCellFormatter().setStyleName(numberOfRows + 1, 4, "limitWidth");
				materialBatchTable.setWidget(numberOfRows + 1, 5, new Label(""));
				materialBatchTable.getFlexCellFormatter().setStyleName(numberOfRows + 1, 5, "limitWidth");
				numberOfRows++;
				
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
}
