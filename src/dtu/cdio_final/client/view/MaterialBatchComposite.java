package dtu.cdio_final.client.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;

import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.shared.dto.MaterialbatchDTO;
import dtu.cdio_final.shared.dto.MaterialDTO;

public class MaterialBatchComposite extends Composite {

	private static MaterialBatchCompositeUiBinder uiBinder = GWT
			.create(MaterialBatchCompositeUiBinder.class);

	interface MaterialBatchCompositeUiBinder extends
			UiBinder<Widget, MaterialBatchComposite> {
	}

	@UiField
	FlexTable materialBatchTable;

	DataServiceAsync service;
	ArrayList<Integer> materialsID = new ArrayList<Integer>();

	public MaterialBatchComposite(DataServiceAsync service) {
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
		initTable();
	}

	private void initTable() {
		materialBatchTable.setWidget(0, 0, new Label("Material Batch ID"));
		materialBatchTable.setWidget(0, 1, new Label("Material ID"));
		materialBatchTable.setWidget(0, 2, new Label("Material Name"));
		materialBatchTable.setWidget(0, 3, new Label("Quantity"));

		service.getMaterialBatches(new AsyncCallback<List<MaterialbatchDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(List<MaterialbatchDTO> materialBatches) {
				for (int i = 0; i < materialBatches.size(); i++) {
					materialBatchTable.setWidget(i + 1, 0, new Label(""	+ materialBatches.get(i).getMbID()));
					materialBatchTable.setWidget(i + 1, 1, new Label(""	+ materialBatches.get(i).getMaterialID()));
					materialsID.add(materialBatches.get(i).getMaterialID());
					materialBatchTable.setWidget(i + 1, 3, new Label(""	+ materialBatches.get(i).getQuantity()));
				}
				service.getMaterials(new MaterialCallback(materialsID));

			}

		});
	}

	private class MaterialCallback implements AsyncCallback<List<MaterialDTO>> {

		private final List<Integer> materials;

		MaterialCallback(List<Integer> materials) {
			this.materials = materials;
		}

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onSuccess(List<MaterialDTO> materialCallBack) {
			for (int i = 0; i < materials.size(); i++) {
				for (int j = 0; j < materialCallBack.size(); j++) {
					if (materials.get(i) == materialCallBack.get(j).getMaterialID())
						materialBatchTable.setWidget(i + 1, 2, new Label(materialCallBack.get(j).getMaterialName()));
				}
			}
		}
	}
}