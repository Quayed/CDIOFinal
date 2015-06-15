package dtu.cdio_final.client.view;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import dtu.cdio_final.client.Group13cdio_final;
import dtu.cdio_final.client.service.DataServiceAsync;
import dtu.cdio_final.client.service.TokenAsyncCallback;
import dtu.cdio_final.shared.FieldVerifier;
import dtu.cdio_final.shared.dto.UserDTO;

public class UsersComposite extends PageComposite {
	interface MainUiBinder extends UiBinder<Widget, UsersComposite> {
	}

	private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);

	@UiField
	FlexTable usersTable;
	
	
	@UiField MaterialTextBox createUserID;
	@UiField MaterialTextBox createUserName;
	@UiField MaterialTextBox createUserIni;
	@UiField MaterialTextBox createUserCPR;
	@UiField MaterialTextBox createUserPassword;
	@UiField MaterialListBox createUserRole;
	@UiField MaterialButton createUserButton;
	
	@UiField TextBox userID;
	@UiField TextBox userName;
	@UiField TextBox userIni;
	@UiField TextBox userCPR;
	@UiField TextBox userPassword;
	@UiField MaterialListBox userRole;
	@UiField MaterialCheckBox userStatus;
	@UiField MaterialButton submitButton;
	@UiField MaterialButton cancelButton;
	
	DataServiceAsync service;

	private int editRow = -1;
	private int numberOfRows = 1;
	private UserDTO newUser;
	
	private boolean validCreateUserID;
	private boolean validCreateUserName;
	private boolean validCreateUserIni;
	private boolean validCreateUserCPR;
	private boolean validCreateUserPassword;
	
	private boolean validUserID;
	private boolean validUserName;
	private boolean validUserIni;
	private boolean validUserCPR;
	private boolean validUserPassword;
	
	
	
	public UsersComposite(DataServiceAsync service) {
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
		reloadPage();
	}
	
	@Override
	public void reloadPage()
	{
		usersTable.setWidget(0, 0, new Label("UserID"));
		usersTable.setWidget(0, 1, new Label("Name"));
		usersTable.setWidget(0, 2, new Label("Ini"));
		usersTable.setWidget(0, 3, new Label("CPR"));
		usersTable.setWidget(0, 4, new Label("Password"));
		usersTable.setWidget(0, 5, new Label("Role"));
		usersTable.setWidget(0, 6, new Label("Status"));
		usersTable.setWidget(0, 7, new Label(""));
		usersTable.setWidget(0, 8, new Label(""));
		createUserButton.addStyleName("fullWidth");
		
		service.getUsers(Group13cdio_final.token, new TokenAsyncCallback<List<UserDTO>>() {

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
					usersTable.setWidget(i + 1, 7, new MaterialButton("mdi-content-create", "blue", "", "light", ""));
					((MaterialButton)usersTable.getWidget(i + 1, 7)).addClickHandler(new editClick());
					usersTable.getFlexCellFormatter().setStyleName(i + 1, 7, "limitWidth");
					usersTable.setWidget(i + 1, 8, new Label(""));
					usersTable.getFlexCellFormatter().setStyleName(i + 1, 8, "limitWidth");
					numberOfRows++;
				}
			}
		
		});
	}
	
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
			usersTable.setText(editRow, 0, userID.getText());
			
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
						
			usersTable.setWidget(editRow, 6, submitButton);
			
			usersTable.setWidget(editRow, 7, cancelButton);
		}
		
	}
	
	@UiHandler("submitButton")
	void submitClickHandler(ClickEvent event){

			int userIDInt = Integer.valueOf(userID.getText());
			int userRoleInt = userRole.getSelectedIndex() +1;
			int userStatusInt;
			if(userStatus.getValue()){
				userStatusInt = 1;
			} else{
				userStatusInt = 0;
			}
			UserDTO user = new UserDTO(userIDInt, userName.getText(), userIni.getText(), userCPR.getText(), userPassword.getText(), userRoleInt, userStatusInt);
			service.updateUser(Group13cdio_final.token, user, new TokenAsyncCallback<Void>(){

				@Override
				public void onSuccess(Void result) {
					((Label)usersTable.getWidget(editRow+1, 0)).setText(userID.getText());
					((Label)usersTable.getWidget(editRow+1, 1)).setText(userName.getText());
					((Label)usersTable.getWidget(editRow+1, 2)).setText(userIni.getText());
					((Label)usersTable.getWidget(editRow+1, 3)).setText(userCPR.getText());
					((Label)usersTable.getWidget(editRow+1, 4)).setText(userPassword.getText());
					((Label)usersTable.getWidget(editRow+1, 5)).setText(roleToString(userRole.getSelectedIndex()+1));
					if(userStatus.getValue()){
						((Label)usersTable.getWidget(editRow+1, 6)).setText("Active");
					} else{
						((Label)usersTable.getWidget(editRow+1, 6)).setText("Inactive");
						
					}
					cancelButton.fireEvent(new ClickEvent(){});
					MaterialToast.alert("User has been updated!");
					
				}
				
			});
		}
	
	@UiHandler("createUserButton")
	void createUser(ClickEvent event){
		newUser = new UserDTO(Integer.valueOf(createUserID.getText()), createUserName.getText(), createUserIni.getText(), createUserCPR.getText(), createUserPassword.getText(), createUserRole.getSelectedIndex()+1, 1);
		service.createUser(Group13cdio_final.token, newUser, new TokenAsyncCallback<Void>(){

			@Override
			public void onSuccess(Void result) {
				// Add a new row to the table, when the database query has been completed.
				usersTable.setWidget(numberOfRows + 1, 0, new Label("" + newUser.getUserID()));
				usersTable.setWidget(numberOfRows + 1, 1, new Label(newUser.getUserName()));
				usersTable.setWidget(numberOfRows + 1, 2, new Label(newUser.getIni()));
				usersTable.setWidget(numberOfRows + 1, 3, new Label(newUser.getCpr()));
				usersTable.setWidget(numberOfRows + 1, 4, new Label(newUser.getPassword()));
				usersTable.setWidget(numberOfRows + 1, 5, new Label(roleToString(newUser.getRole())));
				usersTable.setWidget(numberOfRows + 1, 6, new Label(statusToString(newUser.getStatus())));
				usersTable.setWidget(numberOfRows + 1, 7, new MaterialButton("mdi-content-create", "blue", "", "light", ""));
				((MaterialButton)usersTable.getWidget(numberOfRows + 1, 7)).addClickHandler(new editClick());
				usersTable.getFlexCellFormatter().setStyleName(numberOfRows + 1, 7, "limitWidth");
				usersTable.setWidget(numberOfRows + 1, 8, new Label(""));
				usersTable.getFlexCellFormatter().setStyleName(numberOfRows + 1, 8, "limitWidth");
				numberOfRows++;
				
				// Clear the create fields
				createUserID.setText("");
				createUserID.backToDefault();
				createUserName.setText("");
				createUserName.backToDefault();
				createUserIni.setText("");
				createUserIni.backToDefault();
				createUserCPR.setText("");
				createUserCPR.backToDefault();
				createUserPassword.setText("");
				createUserPassword.backToDefault();
				createUserRole.setSelectedIndex(0);
				
				MaterialToast.alert("The user has been created!");
			}
		});
	}
	
	@UiHandler("cancelButton")
	void cancelClickHandler(ClickEvent event){

		
			usersTable.getRowFormatter().setVisible(editRow+1, true);
			usersTable.getRowFormatter().setVisible(editRow, false);
			usersTable.removeRow(editRow);
			editRow = -1;
		
		
	}
	
	@UiHandler("createUserID")
	void keyUpCreateUserID(KeyUpEvent e) {
		if(FieldVerifier.isValidID(createUserID.getText())){
			createUserID.removeStyleName("invalidEntry");
			validCreateUserID = true;
		} else{
			createUserID.addStyleName("invalidEntry");
			validCreateUserID = false;
		}
		checkForm();
	}
	
	@UiHandler("createUserName")
	void keyUpCreateName(KeyUpEvent e) {
		if(FieldVerifier.isValidName(createUserName.getText())){
			createUserName.removeStyleName("invalidEntry");
			validCreateUserName = true;
		} else{
			createUserName.addStyleName("invalidEntry");
			validCreateUserName = false;
		}
		checkForm();
	}
	
	@UiHandler("createUserIni")
	void keyUpCreateUserIni(KeyUpEvent e) {
		if(FieldVerifier.isValidInitials(createUserIni.getText())){
			createUserIni.removeStyleName("invalidEntry");
			validCreateUserIni = true;
		} else{
			createUserIni.addStyleName("invalidEntry");
			validCreateUserIni = false;
		
		}
		checkForm();
	}
	
	@UiHandler("createUserCPR")
	void keyUpCreateUserCPR(KeyUpEvent e) {
		if(FieldVerifier.isValidCPR(createUserCPR.getText())){
			createUserCPR.removeStyleName("invalidEntry");
			validCreateUserCPR = true;
		} else{
			createUserCPR.addStyleName("invalidEntry");
			validCreateUserCPR = false;
		}
		checkForm();
	}
	
	@UiHandler("createUserPassword")
	void keyUpCreateUserPassword(KeyUpEvent e) {
		if(FieldVerifier.isValidPassword(createUserPassword.getText())){
			createUserPassword.removeStyleName("invalidEntry");
			validCreateUserPassword = true;
		} else{
			createUserPassword.addStyleName("invalidEntry");
			validCreateUserPassword = false;
		}
		checkForm();
	}
	
	private void checkForm(){
		if (validCreateUserID && validCreateUserName && validCreateUserIni && validCreateUserCPR && validCreateUserPassword){
			createUserButton.setDisable(false);
		} else{
			createUserButton.setDisable(true);
		}
	}
	
	//virker ikke lige pt. i flextable.. !
	@UiHandler("userID")
	void keyUpUserID(KeyUpEvent e) {
		if(FieldVerifier.isValidID(userID.getText())){
			userID.removeStyleName("invalidEntry");
			validUserID = true;
		} else{
			userID.addStyleName("invalidEntry");
			validUserID = false;
		}
		checkEditForm();
	}
	
	@UiHandler("userName")
	void keyUpUserName(KeyUpEvent e) {
		if(FieldVerifier.isValidName(userName.getText())){
			userName.removeStyleName("invalidEntry");
			validUserName = true;
		} else{
			userName.addStyleName("invalidEntry");
			validUserName = false;
		}
		checkEditForm();
	}
	
	@UiHandler("userIni")
	void keyUpUserIni(KeyUpEvent e) {
		if(FieldVerifier.isValidInitials(userIni.getText())){
			userIni.removeStyleName("invalidEntry");
			validUserIni = true;
		} else{
			userIni.addStyleName("invalidEntry");
			validUserIni = false;
		
		}
		checkEditForm();
	}
	
	@UiHandler("userCPR")
	void keyUpUserCPR(KeyUpEvent e) {
		if(FieldVerifier.isValidCPR(userCPR.getText())){
			userCPR.removeStyleName("invalidEntry");
			validUserCPR = true;
		} else{
			userCPR.addStyleName("invalidEntry");
			validUserCPR = false;
		}
		checkEditForm();
	}
	
	@UiHandler("userPassword")
	void keyUpUserPassword(KeyUpEvent e) {
		if(FieldVerifier.isValidPassword(userPassword.getText())){
			userPassword.removeStyleName("invalidEntry");
			validUserPassword = true;
		} else{
			userPassword.addStyleName("invalidEntry");
			validUserPassword = false;
		}
		checkEditForm();
	}
	
	private void checkEditForm(){
		if (validUserID && validUserName && validUserIni && validUserCPR && validUserPassword){
			submitButton.setDisable(false);
		} else{
			submitButton.setDisable(true);
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
