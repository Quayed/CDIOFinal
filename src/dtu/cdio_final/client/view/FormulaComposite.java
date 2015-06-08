package dtu.cdio_final.client.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
//import com.google.gwt.user.client.Window; bruger den ikke endnu.
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.shared.dto.FormulaCompDTO;
import dtu.cdio_final.shared.dto.FormulaDTO;


public class FormulaComposite extends Composite {
	interface MainUiBinder extends UiBinder<Widget, FormulaComposite> {
	}

	private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);
	private int tvar = 1;
	private int mvar = 0;
	
	@UiField
	FlexTable formulaTable;

	DataServiceAsync service;

	public FormulaComposite(DataServiceAsync service) {
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
		initTable();
	}

	private void initTable() {
		formulaTable.setWidget(0, 0, new Label("FormulaID"));
		formulaTable.setWidget(0, 1, new Label("FormulaName"));
		formulaTable.setWidget(0, 2, new Label("MaterialID"));
		formulaTable.setWidget(0, 3, new Label("NonNetto"));
		formulaTable.setWidget(0, 4, new Label("Tolerance"));
		
		service.getFormulas(new AsyncCallback<List<FormulaDTO>>() {
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(List<FormulaDTO> formulas ) {

				for (int i = 0; i < formulas.size(); i++) {
//					Window.alert(String.valueOf(tvar));
//					formulaTable.setWidget(i + 1, 0, new Label("" + formulas.get(i).getFormulaID()));
//					formulaTable.setWidget(i + 1, 1, new Label(formulas.get(i).getFormulaName()));
					
					mvar++;
					service.getFormulaComps(formulas.get(i).getFormulaID(),new AsyncCallback<List<FormulaCompDTO>>(){
					
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}

						
						@Override
						public void onSuccess(List<FormulaCompDTO> formulaComps) {	

							Window.alert(String.valueOf(mvar));
							for (int k = 0; k < formulaComps.size(); k++) {
								/*if (k!=0) {
									formulaTable.setWidget(k + 1, 0, new Label("-------"));
									formulaTable.setWidget(k + 1, 1, new Label("-------"));
								}*/
								//formulaTable.insertRow(tvar + 1);
								
//							if(k == 1){
//									formulaTable.setWidget(tvar + 1, 0, new Label("" + formulas.get(mvar).getFormulaID()));
//									formulaTable.setWidget(tvar + 1, 1, new Label(formulas.get(mvar).getFormulaName()));
//							}
								
								formulaTable.setWidget(k + 1, 2, new Label(String.valueOf(formulaComps.get(k).getMaterialID())));
								formulaTable.setWidget(k + 1, 3, new Label(String.valueOf(formulaComps.get(k).getNomNetto())));
								formulaTable.setWidget(k + 1, 4, new Label(String.valueOf(formulaComps.get(k).getTolerance())));
								tvar++;
							}
							
						
						}
						
					});

				}
				
			}

		});
	}

}

