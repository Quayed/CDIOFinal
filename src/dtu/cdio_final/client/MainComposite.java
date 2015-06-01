package dtu.cdio_final.client;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainComposite extends Composite {
	interface MainUiBinder extends UiBinder<Widget, MainComposite> {
	}

	private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);

	@UiField MaterialNavBar navBar;
	@UiField MaterialLink userPage;
	@UiField MaterialLink materialPage;
	@UiField MaterialLink formulaPage;
	@UiField MaterialLink materialBatchPage;
	@UiField MaterialLink productBatchPage;
	
	@UiField HTMLPanel contentPanel;
	
	@UiHandler("userPage")
	protected void selectUsers(ClickEvent e) {
		MaterialNavBar.hideNav();
	}
	
	public MainComposite() {
		initWidget(uiBinder.createAndBindUi(this));

	}
}
