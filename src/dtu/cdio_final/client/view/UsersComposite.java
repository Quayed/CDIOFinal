package dtu.cdio_final.client.view;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialDropDown;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialSwitch;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.shared.dto.UserDTO;

public class UsersComposite extends Composite {
	interface MainUiBinder extends UiBinder<Widget, UsersComposite> {
	}

	private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);

	@UiField
	FlexTable usersTable;

	DataServiceAsync service;

	private int editRow = -1;
	
	
	
	public UsersComposite(DataServiceAsync service) {
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
		initTable();
	}

	private void initTable() {
		usersTable.setWidget(0, 0, new Label("UserID"));
		usersTable.setWidget(0, 1, new Label("Name"));
		usersTable.setWidget(0, 2, new Label("Ini"));
		usersTable.setWidget(0, 3, new Label("CPR"));
		usersTable.setWidget(0, 4, new Label("Password"));
		usersTable.setWidget(0, 5, new Label("Role"));
		usersTable.setWidget(0, 6, new Label("Status"));
		usersTable.setWidget(0, 7, new Label(""));

		service.getUsers(new AsyncCallback<List<UserDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(List<UserDTO> users) {
				for (int i = 0; i < users.size(); i++) {
					usersTable.setWidget(i + 1, 0, new Label("" + users.get(i).getUserID()));
					usersTable.setWidget(i + 1, 1, new Label(users.get(i).getUserName()));
					usersTable.setWidget(i + 1, 2, new Label(users.get(i).getIni()));
					usersTable.setWidget(i + 1, 3, new Label(users.get(i).getCpr()));
					usersTable.setWidget(i + 1, 4, new Label(users.get(i).getPassword()));
					usersTable.setWidget(i + 1, 5, new Label(roleToString(users.get(i).getRole())));
					usersTable.setWidget(i + 1, 6, new Label(statusToString(users.get(i).getStatus())));
					usersTable.setWidget(i + 1, 7, new Button("Edit"));
					((Button)usersTable.getWidget(i + 1, 7)).addClickHandler(new editClick());
				}
			}
		
		});
	}
	@UiField TextBox userID;
	@UiField TextBox userName;
	@UiField TextBox userIni;
	@UiField TextBox userCPR;
	@UiField TextBox userPassword;
	@UiField MaterialListBox userRole;
	@UiField MaterialCheckBox userStatus;
	@UiField Button cancelButton;
	
	
	private class editClick implements ClickHandler{

		private String getTableLabelText(int column){
			return ((Label) usersTable.getWidget(editRow+1, column)).getText();
		}
		
		@Override
		public void onClick(ClickEvent event) {
			if (editRow > -1){
				cancelButton.fireEvent(new ClickEvent(){});
			}
			
			editRow = usersTable.getCellForEvent(event).getRowIndex();
			usersTable.insertRow(editRow);
			usersTable.getRowFormatter().setVisible(editRow+1, false);
			
			userID.setText(getTableLabelText(0));
			usersTable.setWidget(editRow, 0, userID);
			
			userName.setText(getTableLabelText(1));
			usersTable.setWidget(editRow, 1, userName);
			
			userIni.setText(getTableLabelText(2));
			usersTable.setWidget(editRow, 2, userIni);
			
			userCPR.setText(getTableLabelText(3));
			usersTable.setWidget(editRow, 3, userCPR);
			
			userPassword.setText(getTableLabelText(4));
			usersTable.setWidget(editRow, 4, userPassword);
			
			userRole.setItemSelected(roleToInt(getTableLabelText(5)) - 1, true);
			usersTable.setWidget(editRow, 5, userRole);
			
			userStatus.setValue(getValueOfStatus(getTableLabelText(6)));
			usersTable.setWidget(editRow, 5, userStatus);
		}
		
	}
	
	private String roleToString(int role){
		if(role == 1){
			return "Administrator";
		} else if(role ==  2){
			return "Farmaceut";
		} else if(role == 3){
			return "Foreman";
		} else if(role == 4){
			return "Operator";
		} else{
			Window.alert("ERROR!! Unknown role!!" );
			return "Error";
		}
	}
	
	private int roleToInt(String role){
		if(role.equals("Administrator")){
			return 1;
		} else if(role.equals("Farmaceut")){
			return 2;
		} else if(role.equals("Foreman")){
			return 3;
		} else if(role.equals("Operator")){
			return 4;
		} else{
			Window.alert("ERROR!! Unknown role!!");
			return -1;
		}
	}
	
	private boolean getValueOfStatus(String status){
		if(status.equals("Active")){
			return true;
		} else{
			return false;
		}
	}
	
	private String statusToString(int status){
		if(status == 1){
			return "Active";
		} else{
			return "Inactive";
		}
	}

}
