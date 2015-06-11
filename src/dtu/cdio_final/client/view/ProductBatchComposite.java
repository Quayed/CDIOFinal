package dtu.cdio_final.client.view;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModal.TYPE;
import gwt.material.design.client.ui.MaterialModalContent;
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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.client.service.TokenAsyncCallback;
import dtu.cdio_final.shared.dto.FormulaDTO;
import dtu.cdio_final.shared.dto.MaterialDTO;
import dtu.cdio_final.shared.dto.MaterialbatchDTO;
import dtu.cdio_final.shared.dto.ProductbatchDTO;

public class ProductBatchComposite extends PageComposite
{
	interface ProductBatchCompositeUiBinder extends UiBinder<Widget, ProductBatchComposite> {}
	
	private static ProductBatchCompositeUiBinder uiBinder = GWT.create(ProductBatchCompositeUiBinder.class);
//	private int editRow = -1;

	private boolean createAccess;
	
	private int numberOfRows;
	@UiField MaterialCollapsible createBox;
	
	@UiField FlexTable productBatchTable;
	@UiField MaterialButton cancelButton;
	@UiField MaterialButton confirmButton;
	
	@UiField TextBox productBatchID;
	@UiField TextBox formulaID;
	@UiField TextBox productBatchStatus;
	
	@UiField MaterialTextBox  createProductBatchID;
	@UiField MaterialTextBox  createFormulaID;
	@UiField MaterialButton createProductBatchButton;

	ArrayList<Integer> productID;
	private DataServiceAsync service;
	private ModalComposite modal = null;
	//ArrayList<Integer> materialsID = new ArrayList<Integer>();

	public ProductBatchComposite(DataServiceAsync service, boolean create)
	{
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
		this.createAccess = create;
	}
	
	@Override
	public void reloadPage()
	{
		initTable();
	}

	private void initTable()
	{
		numberOfRows = 1;
		productID = new ArrayList<Integer>();
		productBatchTable.setWidget(0, 0, new Label("Product Batch ID"));
		productBatchTable.setWidget(0, 1, new Label("Formula ID"));
		productBatchTable.setWidget(0, 2, new Label("Formula Name"));
		productBatchTable.setWidget(0, 3, new Label("Batch Status"));
		productBatchTable.setWidget(0, 4, new Label(""));
		createProductBatchButton.addStyleName("fullWidth");

		if(!createAccess){
			createBox.setVisible(false);
		}
		
		service.getProductBatches(new TokenAsyncCallback<List<ProductbatchDTO>>()
				{

			@Override
			public void onFailure(Throwable caught)
			{
				super.onFailure(caught);

			}

			@Override
			public void onSuccess(List<ProductbatchDTO> productBatches)
			{
				for (int i = 0; i < productBatches.size(); i++)
				{
					addRow(productBatches.get(i));
					
//					if(productBatches.get(i).getStatus() == 1)
//					{
//						productBatchTable.setWidget(i +1, 4, new MaterialButton("mdi-content-create", "blue", "", "light", "") );
//						((MaterialButton)productBatchTable.getWidget(i + 1, 4)).addClickHandler(new editClick());
//					}
//					productBatchTable.getFlexCellFormatter().setStyleName(i + 1, 4, "limitWidth");
				}
				service.getFormulas(new MaterialCallback(productID));

			}

		});
	}

	private void addRow(ProductbatchDTO productBatches) {
		//TODO materialName
		
		productBatchTable.setWidget(numberOfRows, 0, new Label(""	+ productBatches.getPbID()));
		productBatchTable.setWidget(numberOfRows, 1, new Label(""	+ productBatches.getFormulaID()));
		productID.add(productBatches.getFormulaID());
		productBatchTable.setWidget(numberOfRows, 2, new Label(""	+ productBatches.getFormulaID()));
		productBatchTable.setWidget(numberOfRows, 3, new Label(""	+ productBatches.getStatus()));
		
		numberOfRows++;
	}
	
	private class ModalClickHandler implements ClickHandler
	{

		@Override
		public void onClick(ClickEvent event)
		{
			if(((MaterialButton)event.getSource()).getText() == "Agree")
			{
				MaterialModal.closeModal();
			}
			else if (((MaterialButton) event.getSource()).getText() == "Disagree")
			{
				Window.alert("NOOOOOOOO");
				MaterialModal.closeModal();
			}
		}
		
	}	
	
	private class MaterialCallback extends TokenAsyncCallback<List<FormulaDTO>> {

		private final List<Integer> products;

		MaterialCallback(List<Integer> materials) {
			this.products = materials;
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(List<FormulaDTO> materialCallBack) {
			for (int i = 0; i < products.size(); i++) {
				for (int j = 0; j < materialCallBack.size(); j++) {
					if (products.get(i) == materialCallBack.get(j).getFormulaID())
						productBatchTable.setWidget(i + 1, 2, new Label(materialCallBack.get(j).getFormulaName()));
				}
			}
		}

	}
	
	@UiHandler("createProductBatchButton")
	void createMaterialBatch(ClickEvent event){
		int productBatchIDInt2 = Integer.valueOf(createProductBatchID.getText());
		int productIDInt2 = Integer.valueOf(createFormulaID.getText());
		final ProductbatchDTO newProductBatch = new ProductbatchDTO(productBatchIDInt2, productIDInt2, 1);
		
		
		service.createProductBatch(newProductBatch, new TokenAsyncCallback<Void>(){

			@Override
			public void onSuccess(Void result) {
				// Add a new row to the table, when the database query has been completed.
				addRow(newProductBatch);
				
				// Clear the create fields
				createProductBatchID.setText("");
				createProductBatchID.backToDefault();
				createFormulaID.setText("");
				createFormulaID.backToDefault();
				
				Window.alert("The product has been created!");
			}
		});
	}

	/*
	private class editClick implements ClickHandler
	{
		@Override
		public void onClick(ClickEvent event)
		{
			int eventRow = productBatchTable.getCellForEvent(event).getRowIndex();
			String batch = ((Label)productBatchTable.getWidget(eventRow, 0)).getText();
			String message = "Are you sure you want to delete Product Batch: " + batch;
			modal = new ModalComposite("Delete Batch", message);
			modal.addClickHandler(new ModalClickHandler());
			MaterialModal.showModal(true, modal , TYPE.FIXED_FOOTER);
			
//			if (editRow > -1)
//			{
//				cancelButton.fireEvent(new ClickEvent(){});
//			}
//			
//			editRow = productBatchTable.getCellForEvent(event).getRowIndex();
//			productBatchTable.insertRow(editRow);
//			productBatchTable.getRowFormatter().setVisible(editRow+1, false);
//			
//			productBatchID.setText(getTableLabelText(0));
//			productBatchTable.setWidget(editRow, 0, new Label("Are you sure you want to delete Batch: " + getTableLabelText(0)));
//			
//			confirmButton.addClickHandler(new cancelClickHandler());
//			productBatchTable.setWidget(editRow, 1, confirmButton);
//			
//			cancelButton.addClickHandler(new cancelClickHandler());
//			productBatchTable.setWidget(editRow, 2, cancelButton);
		}
	}
	
	private class cancelClickHandler implements ClickHandler
	{
		@Override
		public void onClick(ClickEvent event)
		{
			productBatchTable.getRowFormatter().setVisible(editRow+1, true);
			productBatchTable.getRowFormatter().setVisible(editRow, false);
			productBatchTable.removeRow(editRow);
			editRow = -1;
		}
	}
	*/
}
