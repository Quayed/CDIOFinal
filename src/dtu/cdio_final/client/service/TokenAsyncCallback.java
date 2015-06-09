package dtu.cdio_final.client.service;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import dtu.cdio_final.client.Group13cdio_final;
import dtu.cdio_final.shared.TokenException;

public abstract class TokenAsyncCallback<T> implements AsyncCallback<T>
{
	@Override
	public void onFailure(Throwable caught)
	{
		if(caught instanceof TokenException)
		{
			Window.alert(((TokenException)caught).getMessage());
			Group13cdio_final.token = null;
			Window.Location.reload();
		}
	}
}
