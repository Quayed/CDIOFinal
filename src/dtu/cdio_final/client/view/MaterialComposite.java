package dtu.cdio_final.client.view;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialListBox;
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
import com.google.gwt.user.client.ui.Widget;

import dtu.cdio_final.client.Group13cdio_final;
import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.client.service.TokenAsyncCallback;
import dtu.cdio_final.shared.dto.MaterialDTO;
import dtu.cdio_final.shared.dto.ProductbatchDTO;
import dtu.cdio_final.shared.FieldVerifier;

public class MaterialComposite extends PageComposite{

	private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);

	interface MainUiBinder extends
			UiBinder<Widget, MaterialComposite> {
	}

	@UiField FlexTable materialsTable;
	
	@UiField MaterialTextBox createMaterialID;
	@UiField MaterialTextBox createMaterialName;
	@UiField MaterialTextBox createProvider;
	@UiField MaterialButton createMaterialButton;
	@UiField MaterialCollapsible createBox;
	
	DataServiceAsync service;
	
	private int editRow = -1;
	private int numberOfRows = 1;
	private MaterialDTO newMaterial;
	
	private boolean createAccess;
	private boolean validID;
	private boolean validName;
	private boolean validProvider;
	
	
	public MaterialComposite(DataServiceAsync service,boolean create) {
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
		this.createAccess = create;
		
	}

	@Override
	public void reloadPage() {
		initTable();
	}

	private void initTable() {
		numberOfRows = 1;
		materialsTable.clear();
		materialsTable.setWidget(0, 0, new Label("MaterialID"));
		materialsTable.setWidget(0, 1, new Label("Material Name"));
		materialsTable.setWidget(0, 2, new Label("Provider"));
		materialsTable.setWidget(0, 3, new Label(""));
		materialsTable.setWidget(0, 4, new Label(""));
		createMaterialButton.addStyleName("fullWidth");
		
		if(!createAccess){
			createBox.setVisible(false);
		}
		
		service.getMaterials(Group13cdio_final.token, new TokenAsyncCallback<List<MaterialDTO>>() {
			
			@Override
			public void onSuccess(List<MaterialDTO> materials) {
				for (int i = 0; i < materials.size(); i++) {
					
					addRow(materials.get(i));
				}
			}
		});	
		
	}
	
	@UiHandler("createMaterialButton")
	void createUser(ClickEvent event){
		newMaterial = new MaterialDTO(Integer.valueOf(createMaterialID.getText()), createMaterialName.getText(), createProvider.getText());

		service.createMaterial(Group13cdio_final.token, newMaterial, new TokenAsyncCallback<Void>(){

			@Override
			public void onSuccess(Void result) {
				// Add a new row to the table, when the database query has been completed.
				addRow(newMaterial);
				
				// Clear the create fields
				createMaterialID.setText("");
				createMaterialID.backToDefault();
				createMaterialName.setText("");
				createMaterialName.backToDefault();
				createProvider.setText("");
				createProvider.backToDefault();
				Window.alert("The Material has been created!");
			}
		});
	}
	
	@UiHandler("createMaterialID")
	void keyUpID(KeyUpEvent e) {
		if(FieldVerifier.isValidID(createMaterialID.getText())){
			createMaterialID.removeStyleName("invalidEntry");
			validID = true;
		} else{
			createMaterialID.addStyleName("invalidEntry");
			validID = false;
		}
		checkForm();
	}
	
	@UiHandler("createMaterialName")
	void keyUpName(KeyUpEvent e) {
		if(FieldVerifier.isValidName(createMaterialName.getText())){
			createMaterialName.removeStyleName("invalidEntry");
			validName = true;
		} else{
			createMaterialName.addStyleName("invalidEntry");
			validName = false;
		}
		checkForm();
	}
	
	@UiHandler("createProvider")
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
	private void addRow(MaterialDTO materials) {
		//TODO materialName
		
		materialsTable.setWidget(numberOfRows + 1, 0, new Label(Integer.toString(materials.getMaterialID())));
		materialsTable.setWidget(numberOfRows + 1, 1, new Label(materials.getMaterialName()));
		materialsTable.setWidget(numberOfRows + 1, 2, new Label(materials.getProvider()));
//		MaterialButton button = new MaterialButton("mdi-content-create", "blue", "", "light", "");
//		button.addClickHandler(new editClick());
//		materialsTable.setWidget(numberOfRows + 1, 3, button);
//		materialsTable.getFlexCellFormatter().setStyleName(numberOfRows + 1, 3, "limitWidth");
//		materialsTable.setWidget(numberOfRows + 1, 4, new Label(""));
//		materialsTable.getFlexCellFormatter().setStyleName(numberOfRows + 1, 4, "limitWidth");
		numberOfRows++;
	}
	
	/////// ALT NEDENSTÅENDE SKAL SLETTES LIGESÅ SNART DER ER LAVET DROPDOWNS BASERET PÅ KODEN.
	
	/*
	@UiField MaterialListBox materialID;
	@UiField MaterialListBox materialName;
	@UiField MaterialListBox provider;
	@UiField MaterialButton submitButton;
	@UiField MaterialButton cancelButton;
	
	
	
	private class editClick implements ClickHandler{

		private String getTableLabelText(int column){
			return ((Label) materialsTable.getWidget(editRow+1, column)).getText();
		}
		
		private int getIndexSelected(String currentText, MaterialListBox box){
			for(int i = 0; i < box.getItemCount(); i++)
				if(currentText.equals(box.getItemText(i)))
					return i;
			return -1;
		}
		
		private boolean containsElement(MaterialListBox box , String element){
			for(int i = 0; i < box.getItemCount(); i++)
				if(box.getItemText(i).equals(element))
					return true;
			return false;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			if (editRow > -1){
				cancelButton.fireEvent(new ClickEvent(){});
			}
			
			editRow = materialsTable.getCellForEvent(event).getRowIndex();
			
			editRow = materialsTable.insertRow(editRow);
			materialsTable.getRowFormatter().setVisible(editRow+1, false);
			
			service.getMaterials(new TokenAsyncCallback<List<MaterialDTO>>() {

				@Override
				public void onSuccess(List<MaterialDTO> materials) {
					for(int i = 0; i < materials.size(); i++){
						if(!containsElement(materialID, Integer.toString((materials.get(i).getMaterialID()))))
							materialID.addItem(Integer.toString((materials.get(i).getMaterialID())));
						if(!containsElement(materialName, materials.get(i).getMaterialName()))
							materialName.addItem(materials.get(i).getMaterialName());
						if(!containsElement(provider, materials.get(i).getProvider()))
							provider.addItem(materials.get(i).getProvider());
					}
					
					materialID.setItemSelected(getIndexSelected(getTableLabelText(0), materialID), true);
					materialsTable.setWidget(editRow, 0, materialID);
					
					materialName.setItemSelected(getIndexSelected(getTableLabelText(1), materialName), true);
					materialsTable.setWidget(editRow, 1, materialName);
					
					provider.setItemSelected(getIndexSelected(getTableLabelText(2), provider), true);
					materialsTable.setWidget(editRow, 2, provider);
									
					submitButton.addClickHandler(new submitClickHandler());
					materialsTable.setWidget(editRow, 3, submitButton);
					
					cancelButton.addClickHandler(new cancelClickHandler());
					materialsTable.setWidget(editRow, 4, cancelButton);
				}
			});
			
			
			

		}
		
	}
	
	private class submitClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			final int materialIDInt = Integer.valueOf(materialID.getItemText(materialID.getSelectedIndex()));
			final String materialNameSelected = materialName.getItemText(materialName.getSelectedIndex());
			final String providerSelected = provider.getItemText(provider.getSelectedIndex());
			MaterialDTO material = new MaterialDTO(materialIDInt, materialNameSelected , providerSelected);
			service.updateMaterial(material, new TokenAsyncCallback<Void>(){

				@Override
				public void onSuccess(Void result) {
					((Label)materialsTable.getWidget(editRow+1, 0)).setText(Integer.toString(materialIDInt));
					((Label)materialsTable.getWidget(editRow+1, 1)).setText(materialNameSelected);
					((Label)materialsTable.getWidget(editRow+1, 2)).setText(providerSelected);
						
					cancelButton.fireEvent(new ClickEvent(){});
					Window.alert("Material has been updated!");
					
				}
				
			});
		}
		
	}
	
	private class cancelClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			materialsTable.getRowFormatter().setVisible(editRow+1, true);
			materialsTable.getRowFormatter().setVisible(editRow, false);
			materialsTable.removeRow(editRow);
			editRow = -1;
		}
		
	}
	*/
}
	
