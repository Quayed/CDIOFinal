package dtu.cdio_final.client.view;

import gwt.material.design.client.ui.MaterialButton;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import dtu.cdio_final.client.Group13cdio_final;
import dtu.cdio_final.client.service.DataServiceAsync;

public class LoginComposite extends Composite
{
	interface LoginUiBinder extends UiBinder<Widget, LoginComposite> {}
	
	private DataServiceAsync service = null;
	
	
	@UiField TextBox usernameTextbox;
	@UiField PasswordTextBox passwordTextbox;
	@UiField MaterialButton loginButton;

	private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

	public LoginComposite(DataServiceAsync service)
	{
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
	}
	@UiHandler("loginButton")
	void login(ClickEvent e)
	{
		try
		{
		service.login(Integer.parseInt(usernameTextbox.getValue()), passwordTextbox.getValue(), new AsyncCallback<String>()
				{
					@Override
					public void onFailure(Throwable caught)
					{
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess(String result)
					{
						if(result != null)
						{
							Group13cdio_final.token = result;
							Window.alert("sucess");

						}
						else
						{
							Window.alert("failure");

						}
					}
				});
		}
		catch (NumberFormatException e2)
		{
			//thrown if the userID field is empty, or contains "non-integer" values
			Window.alert("userID should be a number");
		}
		
		
	}
}
