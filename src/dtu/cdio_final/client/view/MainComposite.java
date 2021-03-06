package dtu.cdio_final.client.view;

import gwt.material.design.client.ui.MaterialButton;
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

import dtu.cdio_final.client.Controller;

public class MainComposite extends Composite {
	interface MainUiBinder extends UiBinder<Widget, MainComposite> {
	}

	private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);

	@UiField MaterialNavBar navBar;
	
	@UiField HTMLPanel contentPanel;
			
	public MainComposite() {
		initWidget(uiBinder.createAndBindUi(this));
		
	}
	
	private void setContent(PageComposite page){
		contentPanel.clear();
		contentPanel.add(page);
		page.reloadPage();
	}
	
	public void addPage(String linkText, PageComposite page){
		addPage(linkText, page, false);
	}
	
	public void addPage(String linkText, PageComposite page, boolean firstPage){
		MaterialLink navItem = new MaterialLink(linkText, "blue");
		navItem.addClickHandler(new NavClickHandler(page));
		navBar.addWidgetSideNav(navItem);
		if(firstPage)
			setContent(page);
	}
	
	public void login(){
		MaterialButton logoutButton = new MaterialButton("Logout", "red", "light");
		logoutButton.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				Controller.logout();
			}
			
		});
		navBar.addWidget(logoutButton);
	}
	
	private class NavClickHandler implements ClickHandler
	{
		private final PageComposite page;
		public NavClickHandler(PageComposite page)
		{
			this.page = page;
		}
		
		@Override
		public void onClick(ClickEvent e)
		{
			setContent(page);
			MaterialNavBar.hideNav();
		}
	}
	
}
