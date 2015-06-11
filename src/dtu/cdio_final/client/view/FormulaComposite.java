package dtu.cdio_final.client.view;



import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextBox;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.shared.dto.FormulaDTO;


public class FormulaComposite extends PageComposite {
	interface MainUiBinder extends UiBinder<Widget, FormulaComposite> {
	}
	
	
	private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);
	private int rowCounter = 2;
	@UiField FlexTable formulaTable;
	
	@UiField MaterialCollapsible collapsible;
	
	@UiField MaterialTextBox createFormulaID;
	@UiField MaterialTextBox createFormulaName;
	@UiField FlexTable componentTable;
	@UiField MaterialButton addCompButton;
	@UiField MaterialButton createFormulaButton;
	
	
	DataServiceAsync service;

	public FormulaComposite(DataServiceAsync service) {
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
		
	}
	
	@Override
	public void reloadPage() {
		// TODO Auto-generated method stub
		initTable();
	}

	private void initTable() {
		componentTable.setWidget(0, 1, new Label("Formula Components:"));
		componentTable.setWidget(1, 1, new MaterialTextBox());
		((MaterialTextBox)componentTable.getWidget(1, 1)).setPlaceholder("Material ID");
		componentTable.setWidget(1, 2, new MaterialTextBox());
		((MaterialTextBox)componentTable.getWidget(1, 2)).setPlaceholder("nom_netto");
		componentTable.setWidget(1, 3, new MaterialTextBox());
		((MaterialTextBox)componentTable.getWidget(1, 3)).setPlaceholder("Tolerance");
		componentTable.setWidget(1, 4, new MaterialButton("mdi-content-clear", "blue", "", "light", ""));
		((MaterialButton)componentTable.getWidget(1, 4)).addClickHandler(new removeComponent());
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
					MaterialCollapsibleItem mCollapsibleItem = new MaterialCollapsibleItem();
					collapsible.addItem(mCollapsibleItem);
					mCollapsibleItem.addHeader(new MaterialLink(formulas.get(i).getFormulaID() + "\t" + formulas.get(i).getFormulaName(), "blue"));
					
					//mCollapsibleItem.addContent(new MaterialTitle(formulas.get(i).getFormulaID() + "\t" + formulas.get(i).getFormulaName()));
					
					
					
					VerticalPanel testPanel = new VerticalPanel();
					
					FlexTable contentTable = new FlexTable();
					contentTable.setWidget(0, 0, new Label("FormulaID"));
					contentTable.setWidget(0, 1, new Label("FormulaName"));
					contentTable.setWidget(0, 2, new Label("FormulaName"));
					
					testPanel.add(contentTable);
					mCollapsibleItem.addContent(testPanel);
					
					
//					service.getFormulaComps(formulas.get(i).getFormulaID(),new AsyncCallback<List<FormulaCompDTO>>(){
//								
//					formulaTable.setWidget(k + 1, 2, new Label(String.valueOf(formulaComps.get(k).getMaterialID())));
//					formulaTable.setWidget(k + 1, 3, new Label(String.valueOf(formulaComps.get(k).getNomNetto())));
//					formulaTable.setWidget(k + 1, 4, new Label(String.valueOf(formulaComps.get(k).getTolerance())));


				}
				
			}

		});
	}

	private class removeComponent implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			componentTable.removeRow(componentTable.getCellForEvent(event).getRowIndex());
			rowCounter--;
		}	
	}
	
	@UiHandler("addCompButton")
	void addComponent(ClickEvent event){
		componentTable.setWidget(rowCounter, 1, new MaterialTextBox());
		((MaterialTextBox)componentTable.getWidget(rowCounter, 1)).setPlaceholder("Material ID");
		componentTable.setWidget(rowCounter, 2, new MaterialTextBox());
		((MaterialTextBox)componentTable.getWidget(rowCounter, 2)).setPlaceholder("nom_netto");
		componentTable.setWidget(rowCounter, 3, new MaterialTextBox());
		((MaterialTextBox)componentTable.getWidget(rowCounter, 3)).setPlaceholder("Tolerance");
		componentTable.setWidget(rowCounter, 4, new MaterialButton("mdi-content-clear", "blue", "", "light", ""));
		((MaterialButton)componentTable.getWidget(rowCounter, 4)).addClickHandler(new removeComponent());
		rowCounter++;
	}
	
	@UiHandler("createFormulaButton")
	void createFormula(ClickEvent event){
		
	}

}

