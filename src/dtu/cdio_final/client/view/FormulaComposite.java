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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.client.service.TokenAsyncCallback;
import dtu.cdio_final.shared.dto.FormulaCompDTO;
import dtu.cdio_final.shared.dto.FormulaDTO;


public class FormulaComposite extends PageComposite {
	interface MainUiBinder extends UiBinder<Widget, FormulaComposite> {
	}
	
	
	private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);
	private int componentCounter = 2;
	
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
		formulaTable.clear();
		
		
		componentTable.clear();
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
		
		service.getFormulas(new TokenAsyncCallback<List<FormulaDTO>>() {

			@Override
			public void onSuccess(List<FormulaDTO> formulas ) {

				for (int i = 0; i < formulas.size(); i++) {
					formulaTable.setWidget(i + 1, 0, new Label("" + formulas.get(i).getFormulaID()));
					formulaTable.setWidget(i + 1, 1, new Label(formulas.get(i).getFormulaName()));

				}
				
			}

		});
	}

	private void onSuccess(){
		
		formulaTable.getFlexCellFormatter().setColSpan(0, 0, 2);

		FlexTable contentTable = new FlexTable();
		contentTable.setWidget(0, 0, new Label("FormulaID"));
		contentTable.setWidget(0, 1, new Label("FormulaName"));
		contentTable.setWidget(0, 2, new Label("FormulaName"));
		
		formulaTable.setWidget(2, 0, contentTable);
	}
	
	private class removeComponent implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			componentTable.removeRow(componentTable.getCellForEvent(event).getRowIndex());
			componentCounter--;
		}	
	}
	
	@UiHandler("addCompButton")
	void addComponent(ClickEvent event){
		componentTable.setWidget(componentCounter, 1, new MaterialTextBox());
		((MaterialTextBox)componentTable.getWidget(componentCounter, 1)).setPlaceholder("Material ID");
		componentTable.setWidget(componentCounter, 2, new MaterialTextBox());
		((MaterialTextBox)componentTable.getWidget(componentCounter, 2)).setPlaceholder("nom_netto");
		componentTable.setWidget(componentCounter, 3, new MaterialTextBox());
		((MaterialTextBox)componentTable.getWidget(componentCounter, 3)).setPlaceholder("Tolerance");
		componentTable.setWidget(componentCounter, 4, new MaterialButton("mdi-content-clear", "blue", "", "light", ""));
		((MaterialButton)componentTable.getWidget(componentCounter, 4)).addClickHandler(new removeComponent());
		componentCounter++;
	}
	
	@UiHandler("createFormulaButton")
	void createFormula(ClickEvent event){
		List<FormulaCompDTO> components = null;
		FormulaDTO formula = new FormulaDTO(Integer.valueOf(createFormulaID.getText()), createFormulaName.getText());
		for(int i = 1; i < componentCounter; i++){
			components.add(new FormulaCompDTO(formula.getFormulaID(), 
					Integer.valueOf(((MaterialTextBox)componentTable.getWidget(componentCounter, 1)).getText()), 
					Double.valueOf(((MaterialTextBox)componentTable.getWidget(componentCounter, 2)).getText()),
					Double.valueOf(((MaterialTextBox)componentTable.getWidget(componentCounter, 3)).getText())));
		}
		service.createFormualWithComponents(formula, components, new TokenAsyncCallback<Void>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Something went wrong!");
			}

			@Override
			public void onSuccess(Void result) {
				
			}
			
		});
	}

}

