package dtu.cdio_final.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;

import dtu.cdio_final.client.service.DataService;
import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.client.view.FormulaComposite;
import dtu.cdio_final.client.view.LoginComposite;
import dtu.cdio_final.client.view.MainComposite;
import dtu.cdio_final.client.view.MaterialBatchComposite;
import dtu.cdio_final.client.view.UsersComposite;

public class Group13cdio_final implements EntryPoint
{

	private final DataServiceAsync service = GWT.create(DataService.class);
	public static String token = null;
	public void onModuleLoad()
	{
		((ServiceDefTarget) service).setServiceEntryPoint(GWT.getModuleBaseURL() + "data");

		MainComposite gui = new MainComposite();
		RootPanel.get().add(gui);	
		
//		if(token == null)
//			gui.addPage("Login", new LoginComposite(service), true);
//		else
		{
			gui.addPage("Users", new UsersComposite(service), true);
			gui.addPage("MaterialBatchComposite", new MaterialBatchComposite(service));
			gui.addPage("Fomulas", new FormulaComposite(service));
		}

	}
}
