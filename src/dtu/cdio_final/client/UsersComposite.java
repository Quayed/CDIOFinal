package dtu.cdio_final.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
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

				}
			}

		});
	}

}
