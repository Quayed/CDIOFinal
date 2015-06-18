package dtu.cdio_final.client;

import com.google.gwt.user.client.ui.RootPanel;

import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.client.view.FormulaComposite;
import dtu.cdio_final.client.view.LoginComposite;
import dtu.cdio_final.client.view.MainComposite;
import dtu.cdio_final.client.view.MaterialBatchComposite;
import dtu.cdio_final.client.view.MaterialComposite;
import dtu.cdio_final.client.view.ProductBatchComposite;
import dtu.cdio_final.client.view.UsersComposite;
import dtu.cdio_final.shared.dto.UserDTO;

public class Controller {

	public interface LoginEvent
	{
		void login(UserDTO user);
	}
	
	private static DataServiceAsync service;
	private static MainComposite gui;
	private static String token = null;

	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		Controller.token = token;
	}

	
	public Controller(DataServiceAsync service) {
		Controller.service = service;
	}

	public static void start() {
		gui = new MainComposite();
		RootPanel.get().clear();
		RootPanel.get().add(gui);	

		gui.addPage("Login", new LoginComposite(service, new LoginEvent() {			
			@Override
			public void login(UserDTO user)
			{
				if(user.getRole() == 1)
				{
					gui = new MainComposite();
					RootPanel.get().clear();
					RootPanel.get().add(gui);
					gui.login();
					gui.addPage("Users", new UsersComposite(service), true);
					gui.addPage("Materials", new MaterialComposite(service, false));
					gui.addPage("Fomulas", new FormulaComposite(service, false));
					gui.addPage("MaterialBatch", new MaterialBatchComposite(service, false));
					gui.addPage("ProductBatches", new ProductBatchComposite(service, false));
					
				}
				else if (user.getRole() == 2){
					gui = new MainComposite();
					RootPanel.get().clear();
					RootPanel.get().add(gui);
					gui.login();
					gui.addPage("Materials", new MaterialComposite(service,true), true);
					gui.addPage("Fomulas", new FormulaComposite(service,true));
					gui.addPage("MaterialBatch", new MaterialBatchComposite(service, false));
					gui.addPage("ProductBatches", new ProductBatchComposite(service, false));
				}
					
				else if (user.getRole() == 3){
					gui = new MainComposite();
					RootPanel.get().clear();
					RootPanel.get().add(gui);
					gui.login();
					gui.addPage("Materials", new MaterialComposite(service,false), true);
					gui.addPage("Fomulas", new FormulaComposite(service,false));
					gui.addPage("MaterialBatch", new MaterialBatchComposite(service, true));
					gui.addPage("ProductBatches", new ProductBatchComposite(service, true));	
				}				
			}
		}), true);
	}
	
	public static void logout(){		
		setToken(null);
		start();
	}

}
