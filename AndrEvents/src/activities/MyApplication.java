package activities;

import android.R;
import android.app.Application;
import controller.EvenementController;
import controller.UserController;

public class MyApplication extends Application {

	private UserController userController;
	private EvenementController evenementController;
	private String URL;
	public MyApplication(){
		super();
		this.userController = new UserController(this.getApplicationContext());
		this.evenementController = new EvenementController();
		
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

}
