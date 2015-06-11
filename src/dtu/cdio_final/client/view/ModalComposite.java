package dtu.cdio_final.client.view;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialTitle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ModalComposite extends Composite
{
	interface ModalCompositeUiBinder extends UiBinder<Widget, ModalComposite> {}
	
	private static ModalCompositeUiBinder uiBinder = GWT.create(ModalCompositeUiBinder.class);
	
	private ClickHandler clickHandler = null;
	
	@UiField MaterialTitle modalText;
	@UiField MaterialButton btnAgree;
	@UiField MaterialButton btnDisagree;
	

	public ModalComposite(String headder, String message)
	{
		initWidget(uiBinder.createAndBindUi(this));
		modalText.setDescription(message);
	}
	
	public void addClickHandler(ClickHandler ch)
	{
		this.clickHandler = ch;
		btnAgree.addClickHandler(clickHandler);
		btnDisagree.addClickHandler(clickHandler);
	}
}
