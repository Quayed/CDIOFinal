package dtu.cdio_final.client.view;

import gwt.material.design.client.ui.MaterialButton;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.client.service.TokenAsyncCallback;
import dtu.cdio_final.shared.dto.ProductbatchDTO;

public class ProductBatchComposite extends PageComposite
{
	interface ProductBatchCompositeUiBinder extends UiBinder<Widget, ProductBatchComposite> {}
	
	private static ProductBatchCompositeUiBinder uiBinder = GWT.create(ProductBatchCompositeUiBinder.class);
	private int editRow = -1;

	@UiField FlexTable productBatchTable;
	@UiField MaterialButton cancelButton;
	@UiField MaterialButton confirmButton;
	
	@UiField TextBox productBatchID;
	@UiField TextBox formulaID;
	@UiField TextBox productBatchStatus;
	
	@UiField MaterialTextBox  createProductBatchID;
	@UiField MaterialTextBox  createFormulaID;
	@UiField MaterialButton createProductBatchButton;

	private DataServiceAsync service;
	private ModalComposite modal = null;
	//ArrayList<Integer> materialsID = new ArrayList<Integer>();

	public ProductBatchComposite(DataServiceAsync service)
	{
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
		initTable();
	}
	
	@Override
	public void reloadPage()
	{
		initTable();
	}

	private void initTable()
	{
		productBatchTable.setWidget(0, 0, new Label("Product Batch ID"));
		productBatchTable.setWidget(0, 1, new Label("Formula ID"));
		productBatchTable.setWidget(0, 2, new Label("Formula Name"));
		productBatchTable.setWidget(0, 3, new Label("Batch Status"));
		productBatchTable.setWidget(0, 4, new Label(""));
		createProductBatchButton.addStyleName("fullWidth");

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
					productBatchTable.setWidget(i + 1, 0, new Label(""	+ productBatches.get(i).getPbID()));
					productBatchTable.setWidget(i + 1, 1, new Label(""	+ productBatches.get(i).getFormulaID()));
					productBatchTable.setWidget(i + 1, 2, new Label(""	+ productBatches.get(i).getFormulaID()));
					productBatchTable.setWidget(i + 1, 3, new Label(""	+ productBatches.get(i).getStatus()));
					if(productBatches.get(i).getStatus() == 1)
					{
						productBatchTable.setWidget(i +1, 4, new MaterialButton("mdi-content-create", "blue", "", "light", "") );
						((MaterialButton)productBatchTable.getWidget(i + 1, 4)).addClickHandler(new editClick());
					}
					productBatchTable.getFlexCellFormatter().setStyleName(i + 1, 4, "limitWidth");
				}
//				service.getMaterials(new MaterialCallback(materialsID));

			}

		});
	}

//	private class MaterialCallback implements AsyncCallback<List<MaterialDTO>> {
//
//		private final List<Integer> materials;
//
//		MaterialCallback(List<Integer> materials) {
//			this.materials = materials;
//		}
//
//		@Override
//		public void onFailure(Throwable caught) {
//			// TODO Auto-generated method stub
//		}
//
//		@Override
//		public void onSuccess(List<MaterialDTO> materialCallBack) {
//			for (int i = 0; i < materials.size(); i++) {
//				for (int j = 0; j < materialCallBack.size(); j++) {
//					if (materials.get(i) == materialCallBack.get(j).getMaterialID())
//						materialBatchTable.setWidget(i + 1, 2, new Label(materialCallBack.get(j).getMaterialName()));
//				}
//			}
//		}
//	}
//	
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
}
