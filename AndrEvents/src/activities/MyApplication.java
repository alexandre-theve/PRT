package activities;

import model.User;
import android.app.Application;

public class MyApplication extends Application {

	 private User userConnected;

	public User getUserConnected() {
		return userConnected;
	}

	public void setUserConnected(User userConnected) {
		this.userConnected = userConnected;
	}
	 
	 
	}
