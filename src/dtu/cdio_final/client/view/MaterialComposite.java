package dtu.cdio_final.client.view;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialTextBox;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.client.service.TokenAsyncCallback;
import dtu.cdio_final.shared.dto.MaterialDTO;

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
	
	DataServiceAsync service;
	
	private int editRow = -1;
	private int numberOfRows = 1;
	private MaterialDTO newMaterial;
	
	
	public MaterialComposite(DataServiceAsync service) {
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
		
	}

	@Override
	public void reloadPage() {
		initTable();
	}

	private void initTable() {
		materialsTable.clear();
		materialsTable.setWidget(0, 0, new Label("MaterialID"));
		materialsTable.setWidget(0, 1, new Label("Material Name"));
		materialsTable.setWidget(0, 2, new Label("Provider"));
		materialsTable.setWidget(0, 3, new Label(""));
		materialsTable.setWidget(0, 4, new Label(""));
		createMaterialButton.addStyleName("fullWidth");
		
		service.getMaterials(new TokenAsyncCallback<List<MaterialDTO>>() {
			
			@Override
			public void onSuccess(List<MaterialDTO> materials) {
				for (int i = 0; i < materials.size(); i++) {
					
					materialsTable.setWidget(i + 1, 0, new Label(Integer.toString(materials.get(i).getMaterialID())));
					materialsTable.setWidget(i + 1, 1, new Label(materials.get(i).getMaterialName()));
					materialsTable.setWidget(i + 1, 2, new Label(materials.get(i).getProvider()));
					MaterialButton button = new MaterialButton("mdi-content-create", "blue", "", "light", "");
					button.addClickHandler(new editClick());
					materialsTable.setWidget(i + 1, 3, button);
					materialsTable.getFlexCellFormatter().setStyleName(i + 1, 3, "limitWidth");
					materialsTable.setWidget(i + 1, 4, new Label(""));
					materialsTable.getFlexCellFormatter().setStyleName(i + 1, 4, "limitWidth");
					numberOfRows++;
				}
			}
		});	
		
	}
	
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
				public void onFailure(Throwable caught) {
					Window.alert("hejlsdffdsa");
					
				}

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
			service.updateMaterial(material, new AsyncCallback<Void>(){
			
				
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Something went wrong!");
				}

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
	
	@UiHandler("createMaterialButton")
	void createUser(ClickEvent event){
		newMaterial = new MaterialDTO(Integer.valueOf(createMaterialID.getText()), createMaterialName.getText(), createProvider.getText());
		service.createMaterial(newMaterial, new AsyncCallback<Void>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Something went wrong!!");
			}

			@Override
			public void onSuccess(Void result) {
				// Add a new row to the table, when the database query has been completed.
				materialsTable.setWidget(numberOfRows + 1, 0, new Label("" + newMaterial.getMaterialID()));
				materialsTable.setWidget(numberOfRows + 1, 1, new Label(newMaterial.getMaterialName()));
				materialsTable.setWidget(numberOfRows + 1, 2, new Label(newMaterial.getProvider()));
				materialsTable.setWidget(numberOfRows + 1, 3, new MaterialButton("mdi-content-create", "blue", "", "light", ""));
				((MaterialButton)materialsTable.getWidget(numberOfRows + 1, 3)).addClickHandler(new editClick());
				materialsTable.getFlexCellFormatter().setStyleName(numberOfRows + 1, 3, "limitWidth");
				materialsTable.setWidget(numberOfRows + 1, 4, new Label(""));
				materialsTable.getFlexCellFormatter().setStyleName(numberOfRows + 1, 4, "limitWidth");
				numberOfRows++;
				
				// Clear the create fields
				createMaterialID.setText("");
				createMaterialID.backToDefault();
				createMaterialName.setText("");
				createMaterialName.backToDefault();
				createProvider.setText("");
				createProvider.backToDefault();
				Window.alert("The user has been created!");
			}
		});
	}
}
	
