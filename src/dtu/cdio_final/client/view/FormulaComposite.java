package dtu.cdio_final.client.view;



import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTitle;

import java.awt.Panel;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasWidgets.ForIsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.shared.dto.FormulaCompDTO;
import dtu.cdio_final.shared.dto.FormulaDTO;


public class FormulaComposite extends Composite {
	interface MainUiBinder extends UiBinder<Widget, FormulaComposite> {
	}
	
	
	private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);
	
	@UiField FlexTable formulaTable;
	
	@UiField MaterialLink treeItem;
	@UiField MaterialTitle rootItem;
	
	DataServiceAsync service;

	public FormulaComposite(DataServiceAsync service) {
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
		initTable();
	}

	private void initTable() {
		//treeItem.setWidget(new Label("FormulaID423"));
		formulaTable.setWidget(0, 0, new Label("FormulaID"));
		formulaTable.setWidget(0, 1, new Label("FormulaName"));
		
		service.getFormulas(new AsyncCallback<List<FormulaDTO>>() {
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(List<FormulaDTO> formulas ) {

				for (int i = 0; i < formulas.size(); i++) {
//					formulaTable.setWidget(i + 1, 0, new Label("" + formulas.get(i).getFormulaID()));
//					formulaTable.setWidget(i + 1, 1, new Label(formulas.get(i).getFormulaName()));
					treeItem.setWidget(new Label(formulas.get(i).getFormulaID() + "\t" + formulas.get(i).getFormulaName()));
					
					rootItem.setTitle("TEEEST");
					
					DisclosurePanel disc = new DisclosurePanel("Click to disclose something:");
				    disc.setContent(new Label("This widget is is shown and hidden<br>by the "
				        + "disclosure panel that wraps it."));
				    treeItem.setWidget(disc);
				    //.add(disc);
										
//					service.getFormulaComps(formulas.get(i).getFormulaID(),new AsyncCallback<List<FormulaCompDTO>>(){
//								
//					formulaTable.setWidget(k + 1, 2, new Label(String.valueOf(formulaComps.get(k).getMaterialID())));
//					formulaTable.setWidget(k + 1, 3, new Label(String.valueOf(formulaComps.get(k).getNomNetto())));
//					formulaTable.setWidget(k + 1, 4, new Label(String.valueOf(formulaComps.get(k).getTolerance())));


				}
				
			}

		});
	}

}

