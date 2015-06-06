package dtu.cdio_final.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;

import dtu.cdio_final.client.service.DataService;
import dtu.cdio_final.client.service.DataServiceAsync;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Group13cdio_final implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		MainComposite gui = new MainComposite();
		RootPanel.get().add(gui);
		
		
		
		String url = GWT.getModuleBaseURL() + "data";	
		DataServiceAsync service = GWT.create(DataService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(url);
		
		
		gui.setContent(new UsersComposite(service));
	}
}
