package dtu.cdio_final.client.view;

import java.util.HashMap;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialToast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import dtu.cdio_final.client.Group13cdio_final;
import dtu.cdio_final.client.Group13cdio_final.LoginEvent;
import dtu.cdio_final.client.service.TokenAsyncCallback;
import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.shared.dto.UserDTO;

public class LoginComposite extends PageComposite
{
	interface LoginUiBinder extends UiBinder<Widget, LoginComposite> {}

	private DataServiceAsync service = null;
	private LoginEvent loginEvent;


	@UiField TextBox usernameTextbox;
	@UiField PasswordTextBox passwordTextbox;
	@UiField MaterialButton loginButton;

	private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

	public LoginComposite(DataServiceAsync service, LoginEvent loginEvent)
	{
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
		this.loginEvent = loginEvent;
	}
	@UiHandler("loginButton")
	void login(ClickEvent e)
	{
		try
		{
			service.login(Integer.parseInt(usernameTextbox.getValue()), passwordTextbox.getValue(), new TokenAsyncCallback<HashMap<String, Object>>()
				{
					@Override
					public void onFailure(Throwable caught)
					{
						super.onFailure(caught);
						Window.alert(caught.getMessage());
					}
	
					@Override
					public void onSuccess(HashMap<String, Object> result)
					{
						if(result != null)
						{	
							Group13cdio_final.token = (String)result.get("token");
							
							LoginComposite.this.loginEvent.login((UserDTO)result.get("user"));
						}
						else
						{
							MaterialToast.alert("Wrong login information");
						}
					}
				});
		}
		catch (NumberFormatException e2)
		{
			//thrown if the userID field is empty, or contains "non-integer" values
			MaterialToast.alert("UserID should be a number");
		}
	}
	@Override
	public void reloadPage()
	{
		
	}
}
