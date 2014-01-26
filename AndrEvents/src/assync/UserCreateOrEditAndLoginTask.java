package assync;

import model.User;
import activities.CreateOrEditUserActivity;
import activities.MainActivity;
import activities.MyApplication;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class UserCreateOrEditAndLoginTask extends AsyncTask<Void, Void, Boolean> {
	private CreateOrEditUserActivity activity ;
	private Boolean editMode;
	private User creatingUser;
	public UserCreateOrEditAndLoginTask(CreateOrEditUserActivity activity ,Boolean editMode,User creatingUser) {
		// TODO Auto-generated constructor stub
		this.activity =activity;
		this.editMode = editMode;
		this.creatingUser =  creatingUser;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO: attempt authentication against a network service.
		
		
		try {
			MyApplication myapp = (MyApplication) activity.getApplication();
			if (editMode){
			creatingUser = myapp.getUserController().editUser(creatingUser);
			}
			else{
				creatingUser = myapp.getUserController().createUser(creatingUser);
			}
			if (creatingUser.getId() != -1) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	protected void onPostExecute(final Boolean success) {
		activity.mAuthTask = null;
		activity.showProgress(false);

		if (success) {
			Intent myIntent;
			MyApplication myApp = (MyApplication) activity.getApplication();
			myApp.setUserConnected(creatingUser);
			Bundle params = new Bundle();
			params.putSerializable("user", creatingUser);
			if(!editMode){
			myIntent= new Intent(activity.getApplicationContext(), MainActivity.class);
			myIntent.putExtras(params);
			activity.startActivity(myIntent);
			
			}
			else{
				MyApplication myapp = (MyApplication) activity.getApplication();
				myapp.setUserConnected(creatingUser);
				Toast.makeText(activity.getApplicationContext(), "Votre compte a été modifié !", 5000);
			}
			
		} else {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						activity.getApplicationContext());
				alertDialogBuilder.setTitle("Erreur de communication avec le serveur")
				.setMessage("Veuillez rééssayer.")
				.setCancelable(false).create().show();
			
		}
	}

	@Override
	protected void onCancelled() {
		activity.mAuthTask = null;
		activity.showProgress(false);
	}
}