package dtu.cdio_final.client.view;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialNavBar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainComposite extends Composite {
	interface MainUiBinder extends UiBinder<Widget, MainComposite> {
	}

	private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);

	@UiField MaterialNavBar navBar;
	
	@UiField HTMLPanel contentPanel;
		
	public MainComposite() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	private void setContent(Composite composite){
		contentPanel.clear();
		contentPanel.add(composite);
	}
	
	public void addPage(String linkText, Composite page){
		addPage(linkText, page, false);
	}
	
	public void addPage(String linkText, Composite page, boolean firstPage){
		MaterialLink navItem = new MaterialLink(linkText, "blue");
		navItem.addClickHandler(new NavClickHandler(page));
		navBar.addWidgetSideNav(navItem);
		if(firstPage)
			setContent(page);
	}
	
	private class NavClickHandler implements ClickHandler{
		private final Composite page;
		public NavClickHandler(Composite page) {
			this.page = page;
		}
		
		@Override
		public void onClick(ClickEvent e) {
			setContent(page);
			MaterialNavBar.hideNav();
		}
	}
	
}
