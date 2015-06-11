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
import com.google.gwt.event.dom.client.KeyUpEvent;
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
import dtu.cdio_final.shared.FieldVerifier;
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
	
	private int editRow = -1;
	DataServiceAsync service;
	
	private boolean validFormulaID;
	private boolean validFormulaName;
	private boolean validMaterialID;
	private boolean validNom_netto;
	private boolean validTolerance;
	
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
				formulaTable.setWidget(0, 0, new Label("Formula ID"));
				formulaTable.setWidget(0, 1, new Label("Formula Name"));
				for (int i = 0; i < formulas.size(); i++) {
					formulaTable.setWidget(i + 1, 0, new Label("" + formulas.get(i).getFormulaID()));
					formulaTable.setWidget(i + 1, 1, new Label(formulas.get(i).getFormulaName()));
				}
				formulaTable.addClickHandler(new tableClickHandler());
			}

		});
	}

	private class tableClickHandler implements ClickHandler{		
		FlexTable contentTable = new FlexTable();
		
		tableClickHandler(){
			contentTable.setWidget(0, 0, new Label("Material ID"));
			contentTable.setWidget(0, 1, new Label("Nom_netto"));
			contentTable.setWidget(0, 2, new Label("Tolerance"));
		}
		
		@Override
		public void onClick(ClickEvent event) {
			if(formulaTable.getCellForEvent(event).getRowIndex() == 0){
				return;
			}
			if(editRow == -1){
				editRow = formulaTable.getCellForEvent(event).getRowIndex();
				showComponents();
			} else if(editRow == formulaTable.getCellForEvent(event).getRowIndex()){
				formulaTable.removeRow(editRow+1);
				editRow = -1;
			} else{
				formulaTable.removeRow(editRow+1);
				editRow = formulaTable.getCellForEvent(event).getRowIndex();
				showComponents();
			}
		}
		
		private void showComponents(){
			formulaTable.insertRow(editRow+1);
			formulaTable.getFlexCellFormatter().setColSpan(editRow+1, 0, 2);
			service.getFormulaComps(Integer.valueOf(((Label)formulaTable.getWidget(editRow, 0)).getText()), new TokenAsyncCallback<List<FormulaCompDTO>>(){

				@Override
				public void onSuccess(List<FormulaCompDTO> result) {
					for(int i = 0; i < result.size(); i++){
						contentTable.setWidget(i+1, 0, new Label(String.valueOf(result.get(i).getMaterialID())));
						contentTable.setWidget(i+1, 1, new Label(String.valueOf(result.get(i).getNomNetto())));
						contentTable.setWidget(i+1, 2, new Label(String.valueOf(result.get(i).getTolerance())));
					}
					formulaTable.setWidget(editRow+1, 1, contentTable);
				}
			
			});
		}

		
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
				// TODO implement this method.
			}
			
		});
	}

	private boolean formulaID;
	private boolean formulaName;
	private boolean materialID;
	private boolean nom_netto;
	private boolean tolerance;
	
	@UiHandler("createFormulaID")
	void keyUpFormulaID(KeyUpEvent e) {
		if(FieldVerifier.isValidID(createFormulaID.getText())){
			createFormulaID.removeStyleName("invalidEntry");
			validFormulaID = true;
		} else{
			createFormulaID.addStyleName("invalidEntry");
			validFormulaID = false;
		}
		checkForm();
	}
	
	@UiHandler("createFormulaName")
	void keyUpFormulaName(KeyUpEvent e) {
		if(FieldVerifier.isValidName(createFormulaName.getText())){
			createFormulaName.removeStyleName("invalidEntry");
			validFormulaName = true;
		} else{
			createFormulaName.addStyleName("invalidEntry");
			validFormulaName = false;
		}
		checkForm();
	}
	
	@UiHandler("componentTable")
	void keyUpProvider(KeyUpEvent e) {
		if(FieldVerifier.isValidProvider(createProvider.getText())){
			createProvider.removeStyleName("invalidEntry");
			validProvider = true;
		} else{
			createProvider.addStyleName("invalidEntry");
			validProvider = false;
		
		}
		checkForm();
	}
	
	
	
	private void checkForm(){
		if (validID && validName && validProvider){
			createMaterialButton.setDisable(false);
		} else{
			createMaterialButton.setDisable(true);
		}
	}
}
	
}

