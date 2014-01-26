package assync;

import model.Evenement;
import model.User;
import activities.CreateOrEditUserActivity;
import activities.LoginActivity;
import activities.MainActivity;
import activities.MyApplication;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.ig2i.andrevents.R;

public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
	private User loggingUser;
	private LoginActivity activity;
	private String mLogin;
	private String mPassword;
	private Context context;
	private Evenement evtPushed;
	// REST_COMMUNCATION_ERROR = 0
	// "WRONG_PASSWORD" =1
	// "USER_UNKNOWN_ERROR" =2
	private Integer error = -1;

	public UserLoginTask(LoginActivity activity, String login, String password,
			Evenement evenement) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.context = activity.getApplicationContext();
		this.mLogin = login;
		this.mPassword = password;
		this.evtPushed = evenement;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO: attempt authentication against a network service.

		try {
			MyApplication myapp = (MyApplication) activity.getApplication();
			loggingUser = myapp.getUserController()
					.loginUser(mLogin, mPassword);
			if (loggingUser.getId() == 0) {
				// USER_UNKNOWN_ERROR
				error = 2;
				return false;
			}
			if (loggingUser.getId() == -1) {
				// REST_COMMUNCATION_ERROR
				error = 0;
				return false;
			}
			if (loggingUser.getId() != -1
					&& loggingUser.getPassword().equals(mPassword)) {
				return true;
			}
			if (!loggingUser.getPassword().equals(mLogin)) {
				// "WRONG_PASSWORD"
				error = 1;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	protected void onPostExecute(final Boolean success) {
		activity.mAuthTask = null;
		activity.showProgress(false);

		if (success) {
			MyApplication myApp = (MyApplication) activity.getApplication();
			myApp.setUserConnected(loggingUser);
			Bundle params = new Bundle();
			params.putSerializable("user", loggingUser);
			if (evtPushed != null) {
				params.putSerializable("evenement", evtPushed);
			}
			Intent myIntent = new Intent(context, MainActivity.class);
			myIntent.putExtras(params);

			activity.startActivityForResult(myIntent, 0);
		} else {
			switch (error) {
			case 0:
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
				alertDialogBuilder
						.setTitle("Veuillez rééssayer.")
						.setMessage("Erreur de communication avec le serveur")
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub
									}
								}).setCancelable(false).create().show();

				break;
			case 1:
				activity.setError(R.id.login, R.string.error_incorrect_password);
				break;
			case 2:
				loggingUser = new User(null, mLogin, mPassword, "", "", "", "");
				Bundle params = new Bundle();
				params.putSerializable("user", loggingUser);
				Intent myIntent = new Intent(context,
						CreateOrEditUserActivity.class);
				myIntent.putExtras(params);
				activity.startActivityForResult(myIntent, 0);
			}

		}
	}

	@Override
	protected void onCancelled() {
		activity.mAuthTask = null;
		activity.showProgress(false);
	}
}
