package dtu.cdio_final.client.view;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTextBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import dtu.cdio_final.client.Group13cdio_final;
import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.client.service.TokenAsyncCallback;
import dtu.cdio_final.shared.dto.FormulaDTO;
import dtu.cdio_final.shared.dto.ProductbatchDTO;

public class ProductBatchComposite extends PageComposite
{
	interface ProductBatchCompositeUiBinder extends UiBinder<Widget, ProductBatchComposite> {}
	
	private static ProductBatchCompositeUiBinder uiBinder = GWT.create(ProductBatchCompositeUiBinder.class);

	private DataServiceAsync service;
	private boolean createAccess;
	
	private int numberOfRows;
	@UiField FlexTable productBatchTable;
	
	@UiField MaterialCollapsible createBox;
	@UiField MaterialTextBox  createProductBatchID;
	@UiField MaterialListBox  createFormulaID;
	@UiField MaterialButton createProductBatchButton;

	ArrayList<Integer> productID;
	Map<Integer, String> formulaNames;
	
	public ProductBatchComposite(DataServiceAsync service, boolean create)
	{
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
		this.createAccess = create;
		reloadPage();
	}
	
	@Override
	public void reloadPage()
	{
		productBatchTable.clear();
		numberOfRows = 1;
		productID = new ArrayList<Integer>();
		formulaNames = new HashMap<Integer, String>();
		createFormulaID.clear();
		
		productBatchTable.setWidget(0, 0, new Label("Product Batch ID"));
		productBatchTable.setWidget(0, 1, new Label("Formula ID"));
		productBatchTable.setWidget(0, 2, new Label("Batch Status"));
		createProductBatchButton.addStyleName("fullWidth");

		if(!createAccess){
			createBox.setVisible(false);
		}
		
		service.getProductBatches(Group13cdio_final.token, new TokenAsyncCallback<List<ProductbatchDTO>>()
		{

			@Override
			public void onSuccess(List<ProductbatchDTO> productBatches)
			{
				for (int i = 0; i < productBatches.size(); i++)
				{
					addRow(productBatches.get(i));
					
				}
				
			}

		});
		
		service.getFormulas(Group13cdio_final.token, new MaterialCallback(productID));
	}

	private void addRow(ProductbatchDTO productBatches) {
		productID.add(productBatches.getFormulaID());
		productBatchTable.setWidget(numberOfRows, 0, new Label("" + productBatches.getPbID()));
		productBatchTable.setWidget(numberOfRows, 1, new Label("" + formulaNames.get(productBatches.getFormulaID())));
		productBatchTable.setWidget(numberOfRows, 2, new Label("" + productBatches.getStatus()));
		
		numberOfRows++;
	}
	
	private class MaterialCallback extends TokenAsyncCallback<List<FormulaDTO>> {

		private final List<Integer> productIDs;

		MaterialCallback(List<Integer> productIDs) {
			this.productIDs = productIDs;
		}

		@Override
		public void onSuccess(List<FormulaDTO> formulas) {
			
			for (FormulaDTO formula : formulas) {
				formulaNames.put(formula.getFormulaID(), formula.getFormulaID()+" ("+formula.getFormulaName()+")");
				createFormulaID.addItem(formulaNames.get(formula.getFormulaID()), ""+formula.getFormulaID());
			}
			for (int i = 0; i < productIDs.size(); i++) {
				
				productBatchTable.setWidget(i + 1, 1, new Label(formulaNames.get(productIDs.get(i))));
			}
		}
	
	}
	
	@UiHandler("createProductBatchButton")
	void createMaterialBatch(ClickEvent event){
		int productBatchIDInt2 = Integer.valueOf(createProductBatchID.getText());
		int productIDInt2 = Integer.valueOf(createFormulaID.getSelectedIndex()+1);
		final ProductbatchDTO newProductBatch = new ProductbatchDTO(productBatchIDInt2, productIDInt2, 1);
				
		service.createProductBatch(Group13cdio_final.token, newProductBatch, new TokenAsyncCallback<Void>(){

			@Override
			public void onSuccess(Void result) {
				// Add a new row to the table, when the database query has been completed.
				addRow(newProductBatch);
				
				// Clear the create fields
				createProductBatchID.setText("");
				createProductBatchID.backToDefault();
				
				Window.alert("The product has been created!");
			}
		});
	}
}
