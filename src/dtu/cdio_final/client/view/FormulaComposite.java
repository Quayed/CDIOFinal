package dtu.cdio_final.client.view;



import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialTextBox;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import dtu.cdio_final.client.Controller;
import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.client.service.TokenAsyncCallback;
import dtu.cdio_final.shared.FieldVerifier;
import dtu.cdio_final.shared.dto.FormulaCompDTO;
import dtu.cdio_final.shared.dto.FormulaDTO;
import dtu.cdio_final.shared.dto.MaterialDTO;


public class FormulaComposite extends PageComposite {
	interface MainUiBinder extends UiBinder<Widget, FormulaComposite> {
	}
	
	
	private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);
	private int componentCounter = 2;
	private int componentIndexCounter;
	private HandlerRegistration tableHandler;
	@UiField FlexTable formulaTable;
	
	private tableClickHandler myTableClickHandler = new tableClickHandler();
	@UiField MaterialTextBox createFormulaID;
	@UiField MaterialTextBox createFormulaName;
	@UiField FlexTable componentTable;
	@UiField MaterialButton addCompButton;
	@UiField MaterialButton createFormulaButton;
	@UiField MaterialCollapsible createBox;
	
	private static int editRow = -1;
	private int numberOfRows;
	DataServiceAsync service;
	private boolean createAccess;
	
	private boolean validFormulaID;
	private boolean validFormulaName;
	private final ArrayList<Boolean> validComps = new ArrayList<Boolean>();
	private List<MaterialDTO> materials;
	private MaterialListBox textBoxMaterialID;
	public FormulaComposite(DataServiceAsync service, boolean create) {
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
		this.createAccess = create;
		reloadPage();
	}
	
	@Override
	public void reloadPage() 
	{
//		editRow = -1;
		componentIndexCounter = 0;
		numberOfRows = 1;
		formulaTable.removeAllRows();
		for(int i = componentCounter-1; i > 1; i--){
			componentTable.removeRow(i);
		}
		componentTable.setWidget(0, 1, new Label("Formula Components:"));
		componentCounter = 2;
		
		textBoxMaterialID = new MaterialListBox();
		
		
		MaterialTextBox textBoxNom_Netto = new MaterialTextBox();
		textBoxNom_Netto.setPlaceholder("nom_netto");
		textBoxNom_Netto.addKeyUpHandler(new Nom_NettoKeyUp(textBoxNom_Netto, componentIndexCounter));
		componentTable.setWidget(1, 2, textBoxNom_Netto);
		
		MaterialTextBox textBoxTolerance = new MaterialTextBox();
		textBoxTolerance.setPlaceholder("Tolerance");
		textBoxTolerance.addKeyUpHandler(new ToleranceKeyUp(textBoxTolerance, componentIndexCounter));
		componentTable.setWidget(1, 3, textBoxTolerance);
		
		createFormulaButton.addStyleName("disableButton");

		if(!createAccess){
			createBox.setVisible(false);
		}
		
		service.getFormulas(Controller.getToken(), new TokenAsyncCallback<List<FormulaDTO>>() {

			@Override
			public void onSuccess(List<FormulaDTO> formulas ) {
				formulaTable.setWidget(0, 0, new Label("Formula ID"));
				formulaTable.setWidget(0, 1, new Label("Formula Name"));
				for (int i = 0; i < formulas.size(); i++)
				{
					addRow(formulas.get(i));
					
				}
				if (tableHandler != null){
					tableHandler.removeHandler();
				}
				tableHandler = formulaTable.addClickHandler(myTableClickHandler);
			}

		});
		
		service.getMaterials(Controller.getToken(), new TokenAsyncCallback<List<MaterialDTO>>(){

			@Override
			public void onSuccess(List<MaterialDTO> result) {
				materials = result;
				
				for(MaterialDTO material : materials){
					textBoxMaterialID.addItem(String.valueOf(material.getMaterialID()) + "(" + material.getMaterialName() + ")");
				}
				componentTable.setWidget(1, 1, textBoxMaterialID);
				Window.alert(String.valueOf(componentTable.getWidget(1, 1).getClass()));
			}
		});	
	}

	private class tableClickHandler implements ClickHandler{		
		FlexTable contentTable = new FlexTable();
		
		tableClickHandler(){
			
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
			} else if(editRow+1 == formulaTable.getCellForEvent(event).getRowIndex()){
				return;
			} else{
				formulaTable.removeRow(editRow+1);
				editRow = formulaTable.getCellForEvent(event).getRowIndex();
				showComponents();
			}
		}
		
		private void showComponents(){
			formulaTable.insertRow(editRow+1);
			formulaTable.getFlexCellFormatter().setColSpan(editRow+1, 0, 2);
			service.getFormulaComps(Controller.getToken(), Integer.valueOf(((Label)formulaTable.getWidget(editRow, 0)).getText()), new TokenAsyncCallback<List<FormulaCompDTO>>(){

				@Override
				public void onSuccess(List<FormulaCompDTO> result) {
					contentTable.removeAllRows();
					contentTable.setWidget(0, 0, new Label("Material ID"));
					contentTable.setWidget(0, 1, new Label("Nom_netto"));
					contentTable.setWidget(0, 2, new Label("Tolerance"));
					for(int i = 0; i < result.size(); i++){
						contentTable.setWidget(i+1, 0, new Label(String.valueOf(result.get(i).getMaterialID())));
						contentTable.setWidget(i+1, 1, new Label(String.valueOf(result.get(i).getNomNetto())));
						contentTable.setWidget(i+1, 2, new Label(String.valueOf(result.get(i).getTolerance())));
					}
					formulaTable.setWidget(editRow+1, 0, contentTable);
				}
			
			});
		}

		
	}
	
	private void addRow(FormulaDTO formulas) {
		formulaTable.setWidget(numberOfRows+1, 0, new Label("" + formulas.getFormulaID()));
		formulaTable.setWidget(numberOfRows+1, 1, new Label(formulas.getFormulaName()));
				
		numberOfRows++;
	
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
		MaterialListBox textBoxMaterialID = new MaterialListBox();
		for(MaterialDTO material : materials){
			textBoxMaterialID.addItem(String.valueOf(material.getMaterialID()) + "(" + material.getMaterialName() + ")");
		}
		
		componentTable.setWidget(componentCounter, 1, textBoxMaterialID);
		
		MaterialTextBox textBoxNom_Netto = new MaterialTextBox();
		textBoxNom_Netto.setPlaceholder("nom_netto");
		textBoxNom_Netto.addKeyUpHandler(new Nom_NettoKeyUp(textBoxNom_Netto, componentIndexCounter));
		componentTable.setWidget(componentCounter, 2, textBoxNom_Netto);
		
		MaterialTextBox textBoxTolerance = new MaterialTextBox();
		textBoxTolerance.setPlaceholder("Tolerance");
		textBoxTolerance.addKeyUpHandler(new ToleranceKeyUp(textBoxTolerance, componentIndexCounter));
		componentTable.setWidget(componentCounter, 3, textBoxTolerance);
		
		MaterialButton componentTableButton = new MaterialButton("mdi-content-clear", "blue", "", "light", "");
		componentTableButton.addClickHandler(new removeComponent());
		componentTable.setWidget(componentCounter, 4, componentTableButton);
		
		createFormulaButton.setStyleName("disableButton");
		
		componentCounter++;
	}
	
	@UiHandler("createFormulaButton")
	void createFormula(ClickEvent event){
		List<FormulaCompDTO> components = new ArrayList<FormulaCompDTO>();
		FormulaDTO formula = new FormulaDTO(Integer.valueOf(createFormulaID.getText()), createFormulaName.getText());
		Window.alert(String.valueOf(componentCounter));
		for(int i = 1; i < componentCounter; i++){
			Window.alert(String.valueOf(componentTable.getWidget(1,  1).getClass()));
			int index = ((MaterialListBox)componentTable.getWidget(i, 1)).getSelectedIndex();
			Window.alert(String.valueOf(index));
			String selected = ((MaterialListBox)componentTable.getWidget(i, 1)).getValue(index);
			Window.alert(selected);
			selected = selected.substring(0, selected.indexOf("("));
			Window.alert(selected);
			components.add(new FormulaCompDTO(formula.getFormulaID(), 				
					Integer.valueOf(selected), 
					Double.valueOf(((MaterialTextBox)componentTable.getWidget(i, 2)).getText()),
					Double.valueOf(((MaterialTextBox)componentTable.getWidget(i, 3)).getText())));
		}
		service.createFormualWithComponents(Controller.getToken(), formula, components, new TokenAsyncCallback<Void>(){

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
	
	private void checkForm(){
		if(validFormulaID && validFormulaName){
			int falses = 0;
			for(int i = 0; i < componentIndexCounter; i++)
				if(!validComps.get(i).booleanValue()){
					falses++;
					
					if(falses == 0){
						createFormulaButton.setStyleName("disableButton", false);
					}
					else {
						Window.alert("SET STYLENAME = DISABLE");
						createFormulaButton.setStyleName("disableButton", true);
					}
				}
		}
	}
	
	private class Nom_NettoKeyUp implements KeyUpHandler{
		
		private final MaterialTextBox textBoxNom_Netto;
		private int index;
		
		private Nom_NettoKeyUp(MaterialTextBox textbox, int componentIndex){
			this.textBoxNom_Netto = textbox;
			this.index = componentIndex;
			
			validComps.add(index, false);
			componentIndexCounter++;
			
		}
		
		@Override
		public void onKeyUp(KeyUpEvent event) {
			if(FieldVerifier.isValidNomNetto(textBoxNom_Netto.getText())){
				textBoxNom_Netto.removeStyleName("invalidEntry");
				validComps.set(index, true);
			} else{
				textBoxNom_Netto.addStyleName("invalidEntry");
				if(validComps.get(index))
				validComps.set(index, false);
			}
			checkForm();
		}
	};
	
	private class ToleranceKeyUp implements KeyUpHandler{
		
		private final MaterialTextBox textBoxTolerance;
		private int index;
		
		private ToleranceKeyUp(MaterialTextBox textbox, int componentIndex){
			this.textBoxTolerance = textbox;
			this.index = componentIndex;
			
			
			validComps.add(index, false);
			componentIndexCounter++;
		}
		
		@Override
		public void onKeyUp(KeyUpEvent event) {
			if(FieldVerifier.isValidTolerance(textBoxTolerance.getText())){
				textBoxTolerance.removeStyleName("invalidEntry");
				validComps.set(index, true);
			} else{
				textBoxTolerance.addStyleName("invalidEntry");
				if(validComps.get(index))
				validComps.set(index, false);
			}
			checkForm();
		}
	};	
}
