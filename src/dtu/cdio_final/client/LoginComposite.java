package dtu.cdio_final.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LoginComposite extends Composite {
	interface MainUiBinder extends UiBinder<Widget, LoginComposite> {
	}

	private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);

	public LoginComposite() {
		initWidget(uiBinder.createAndBindUi(this));

	}
	
}
