package dtu.cdio_final.client.service;

import gwt.material.design.client.ui.MaterialToast;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import dtu.cdio_final.shared.ServiceException;
import dtu.cdio_final.shared.TokenException;

public abstract class TokenAsyncCallback<T> implements AsyncCallback<T>
{
	@Override
	public void onFailure(Throwable caught)
	{
		if(caught instanceof TokenException)
		{
			Window.alert("You were inactive too long, and will have to login in again."); 
			Window.Location.reload();
		}
		else if(caught instanceof ServiceException){
			MaterialToast.alert(caught.getMessage());
		}
		else{
			Window.alert(caught.getMessage());
		}
	}
}
