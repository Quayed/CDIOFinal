package dtu.cdio_final.client.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.shared.dto.MaterialDTO;

public class MaterialComposite extends Composite{

	private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);

	interface MainUiBinder extends
			UiBinder<Widget, MaterialComposite> {
	}

	public MaterialComposite() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField FlexTable materialsTable;
	
	DataServiceAsync service;

	public MaterialComposite(DataServiceAsync service) {
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
		initTable();
	}

	private void initTable() {
		materialsTable.setWidget(0, 0, new Label("Material Name"));
		materialsTable.setWidget(0, 1, new Label("MaterialID"));
		materialsTable.setWidget(0, 2, new Label("Provider"));
		
		service.getMaterials(new AsyncCallback<List<MaterialDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(List<MaterialDTO> materials) {
				for (int i = 0; i < materials.size(); i++) {
					materialsTable.setWidget(i + 1, 0, new Label(materials.get(i).getMaterialName()));
					materialsTable.setWidget(i + 1, 1, new Label(Integer.toString(materials.get(i).getMaterialID())));
					materialsTable.setWidget(i + 1, 2, new Label(materials.get(i).getProvider()));
				}
			}
		});
	}
}
