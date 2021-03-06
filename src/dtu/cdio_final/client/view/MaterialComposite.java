package dtu.cdio_final.client.view;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import dtu.cdio_final.client.Controller;
import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.client.service.TokenAsyncCallback;
import dtu.cdio_final.shared.dto.MaterialDTO;
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
		reloadPage();
	}

	@Override
	public void reloadPage() {
		numberOfRows = 1;
		materialsTable.clear();
		materialsTable.setWidget(0, 0, new Label("MaterialID"));
		materialsTable.setWidget(0, 1, new Label("Material Name"));
		materialsTable.setWidget(0, 2, new Label("Provider"));
		materialsTable.setWidget(0, 3, new Label(""));
		materialsTable.setWidget(0, 4, new Label(""));
		createMaterialButton.addStyleName("fullWidth");
		createMaterialButton.addStyleName("disableButton");
		checkForm();
		
		if(!createAccess){
			createBox.setVisible(false);
		}
		
		service.getMaterials(Controller.getToken(), new TokenAsyncCallback<List<MaterialDTO>>() {
			
			@Override
			public void onSuccess(List<MaterialDTO> materials) {
				for (int i = 0; i < materials.size(); i++) {
					
					addRow(materials.get(i));
				}
			}
		});	
		
	}
	
	@UiHandler("createMaterialButton")
	void createMaterial(ClickEvent event){
		if(!checkForm())
			return;
		
		newMaterial = new MaterialDTO(Integer.valueOf(createMaterialID.getText()), createMaterialName.getText(), createProvider.getText());

		service.createMaterial(Controller.getToken(), newMaterial, new TokenAsyncCallback<Void>(){

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
				MaterialToast.alert("The Material has been created!");
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
	
	
	
	private boolean checkForm(){
		if (validID && validName && validProvider){
			createMaterialButton.setDisable(false);
			createMaterialButton.setStyleName("disableButton", false);
			return true;
		} else{
			createMaterialButton.setDisable(true);
			createMaterialButton.setStyleName("disableButton", true);
			return false;
		}
	}
	private void addRow(MaterialDTO materials) {
	
		materialsTable.setWidget(numberOfRows + 1, 0, new Label(Integer.toString(materials.getMaterialID())));
		materialsTable.setWidget(numberOfRows + 1, 1, new Label(materials.getMaterialName()));
		materialsTable.setWidget(numberOfRows + 1, 2, new Label(materials.getProvider()));
		numberOfRows++;
	}
}