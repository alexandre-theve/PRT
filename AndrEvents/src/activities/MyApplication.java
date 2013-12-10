package activities;

import android.app.Application;
import controller.EvenementController;
import controller.UserController;

public class MyApplication extends Application {

	private UserController userController;
	private EvenementController evenementController;
	
	public MyApplication(){
		super();
		this.userController=new UserController();
		this.evenementController = new EvenementController();
		
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
