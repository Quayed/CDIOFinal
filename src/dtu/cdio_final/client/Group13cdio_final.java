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
import dtu.cdio_final.client.view.ProductBatchComposite;
import dtu.cdio_final.client.view.MaterialComposite;
import dtu.cdio_final.client.view.UsersComposite;
import dtu.cdio_final.shared.dto.UserDTO;

public class Group13cdio_final implements EntryPoint
{
	private final static DataServiceAsync service = GWT.create(DataService.class);
	private static MainComposite gui = null;
	public static String token = null;
	
	public void onModuleLoad()
	{
		((ServiceDefTarget) service).setServiceEntryPoint(GWT.getModuleBaseURL() + "data");

		gui = new MainComposite();
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
					gui.addPage("Materials", new MaterialComposite(service, false), true);
					gui.addPage("Fomulas", new FormulaComposite(service, false));
					gui.addPage("MaterialBatch", new MaterialBatchComposite(service, false));
					gui.addPage("ProductBatches", new ProductBatchComposite(service, false),true);
					
				}
				else if (user.getRole() == 2){
					gui = new MainComposite();
					RootPanel.get().clear();
					RootPanel.get().add(gui);
					gui.login();
					gui.addPage("Materials", new MaterialComposite(service,true), true);
					gui.addPage("Fomulas", new FormulaComposite(service,true));
					gui.addPage("MaterialBatch", new MaterialBatchComposite(service, false));
					gui.addPage("ProductBatches", new ProductBatchComposite(service, false),true);
				}
					
				else if (user.getRole() == 3){
					gui = new MainComposite();
					RootPanel.get().clear();
					RootPanel.get().add(gui);
					gui.login();
					gui.addPage("Materials", new MaterialComposite(service,false), true);
					gui.addPage("Fomulas", new FormulaComposite(service,false));
					gui.addPage("MaterialBatch", new MaterialBatchComposite(service, true));
					gui.addPage("ProductBatches", new ProductBatchComposite(service, true),true);	
				}
				
					// DELETE BELOW WHEN DONE WITH TESTING.
//					gui.addPage("Users", new UsersComposite(service), true);
//					gui.addPage("Materials", new MaterialComposite(service,true), true);
//					gui.addPage("Fomulas", new FormulaComposite(service,true));
//					gui.addPage("MaterialBatch", new MaterialBatchComposite(service, true));
//					gui.addPage("ProductBatches", new ProductBatchComposite(service, true),true);
				
			}
		}), true);
	}
	
	public static void logout(){
		gui = new MainComposite();
		RootPanel.get().clear();
		RootPanel.get().add(gui);
		token = null;
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
					gui.addPage("Materials", new MaterialComposite(service, false), true);
					gui.addPage("Fomulas", new FormulaComposite(service, false));
					gui.addPage("MaterialBatch", new MaterialBatchComposite(service, false));
					gui.addPage("ProductBatches", new ProductBatchComposite(service, false),true);
				}
				else if (user.getRole() == 2){
					gui = new MainComposite();
					RootPanel.get().clear();
					RootPanel.get().add(gui);
					gui.login();
					gui.addPage("Materials", new MaterialComposite(service,true), true);
					gui.addPage("Fomulas", new FormulaComposite(service,true));
					gui.addPage("MaterialBatch", new MaterialBatchComposite(service, false));
					gui.addPage("ProductBatches", new ProductBatchComposite(service, false),true);
				}
					
				else if (user.getRole() == 3){
					gui = new MainComposite();
					RootPanel.get().clear();
					RootPanel.get().add(gui);
					gui.login();
					gui.addPage("Materials", new MaterialComposite(service,false), true);
					gui.addPage("Fomulas", new FormulaComposite(service,false));
					gui.addPage("MaterialBatch", new MaterialBatchComposite(service, true));
					gui.addPage("ProductBatches", new ProductBatchComposite(service, true),true);	
				}
				
					// DELETE BELOW WHEN DONE WITH TESTING.
//					gui.addPage("Users", new UsersComposite(service), true);
//					gui.addPage("Materials", new MaterialComposite(service,true), true);
//					gui.addPage("Fomulas", new FormulaComposite(service,true));
//					gui.addPage("MaterialBatch", new MaterialBatchComposite(service, true));
//					gui.addPage("ProductBatches", new ProductBatchComposite(service, true),true);
				
			}
		}), true);
	}
	
	public interface LoginEvent
	{
		void login(UserDTO user);
	}
}
