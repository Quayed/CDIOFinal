package dtu.cdio_final.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import dtu.cdio_final.client.service.DataService;
import dtu.cdio_final.client.service.DataServiceAsync;

public class Group13cdio_final implements EntryPoint
{
	public void onModuleLoad()
	{
		DataServiceAsync service = GWT.create(DataService.class);
		((ServiceDefTarget) service).setServiceEntryPoint(GWT.getModuleBaseURL() + "data");

		new Controller(service);
		Controller.start();
	}
}
