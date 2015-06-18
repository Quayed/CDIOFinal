package dtu.cdio_final.client.view;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialToast;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import dtu.cdio_final.client.Controller;
import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.client.service.TokenAsyncCallback;
import dtu.cdio_final.shared.dto.UserDTO;

public class LoginComposite extends PageComposite
{
	interface LoginUiBinder extends UiBinder<Widget, LoginComposite> {}

	private DataServiceAsync service = null;
	private Controller.LoginEvent loginEvent;


	@UiField TextBox usernameTextbox;
	@UiField PasswordTextBox passwordTextbox;
	@UiField MaterialButton loginButton;

	private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

	public LoginComposite(DataServiceAsync service, Controller.LoginEvent loginEvent)
	{
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
		this.loginEvent = loginEvent;
	}
	
	@Override
	public void reloadPage()
	{
		
	}
	
	@UiHandler("loginButton")
	void login(ClickEvent e)
	{
		try
		{
			login();
		}
		catch (NumberFormatException e2)
		{
			//thrown if the userID field is empty, or contains "non-integer" values
			MaterialToast.alert("UserID should be a number");
		}
	}
	
	@UiHandler("usernameTextbox")
	void keyDownUserName(KeyDownEvent e){
		if(e.getNativeKeyCode() == KeyCodes.KEY_ENTER){
			try{
				login();
			} catch(NumberFormatException error){
				MaterialToast.alert("UserID should be a number");
			}
		}
	}
	@UiHandler("passwordTextbox")
	void keyDownPassword(KeyDownEvent e){
		if(e.getNativeKeyCode() == KeyCodes.KEY_ENTER){
			try{
				login();
			} catch(NumberFormatException error){
				MaterialToast.alert("UserID should be a number");
			}
		}
	}

	
	private void login(){
		service.login(Integer.parseInt(usernameTextbox.getValue()), passwordTextbox.getValue(), new TokenAsyncCallback<HashMap<String, Object>>(){
			
			@Override
			public void onSuccess(HashMap<String, Object> result) {
				if(result != null)
				{	
					Controller.setToken((String)result.get("token"));
					
					LoginComposite.this.loginEvent.login((UserDTO)result.get("user"));
				}
				else
				{
					MaterialToast.alert("Wrong login information");
				}
			}
			
		});
	}
}
