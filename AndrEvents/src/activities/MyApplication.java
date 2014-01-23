package activities;

import com.ig2i.andrevents.R;

import model.User;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import controller.EvenementController;
import controller.UserController;

public class MyApplication extends Application {

	private UserController userController;
	private EvenementController evenementController;
	private String URL;
	private Context context;
	
	public MyApplication(){
		super();
		this.userController = new UserController();
		this.evenementController = new EvenementController();
		
	}

	public void setContext(Context context) {
		this.context = context;
		this.userController.setContext(context);
	}

	public void setURL(String URL){
		this.URL = URL;
		this.userController.setURL(URL);
		this.evenementController.setURL(URL);
	}
	
	public UserController getUserController() {
		return userController;
	}

	public void setUserController(UserController userController) {
		this.userController = userController;
	}

	public EvenementController getEvenementController() {
		return evenementController;
	}

	public void setEvenementController(EvenementController evenementController) {
		this.evenementController = evenementController;
	}

	public void setUserConnected(User user) {
		this.userController.setUserConnected(user);
		SharedPreferences prefs = this.getSharedPreferences(getString(R.string.app_package),Context.MODE_PRIVATE);
		prefs.edit().putInt("loggedUserId", userController.getUserConnected().getId());
		
		
	}

}
