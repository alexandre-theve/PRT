package assync;

import model.User;
import activities.LoginActivity;
import activities.MainActivity;
import activities.MyApplication;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class UserLoggedTask extends AsyncTask<Void, Void, Boolean> {
	private User loggingUser;
	private LoginActivity activity;
	private String mLogin;
	private int id;
	private Context context;
	// REST_COMMUNCATION_ERROR = 0
	// "WRONG_PASSWORD" =1
	// "USER_UNKNOWN_ERROR" =2
	private Integer error = -1;

	public UserLoggedTask(LoginActivity activity, int id) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.context = activity.getApplicationContext();

	}

	@Override
	protected Boolean doInBackground(Void... params) {

		try {
			MyApplication myapp = (MyApplication) activity.getApplication();
			loggingUser = myapp.getUserController().getById(id);
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

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	protected void onPostExecute(final Boolean success) {
		if (success) {
			Bundle params = new Bundle();
			params.putSerializable("user", loggingUser);
			Intent myIntent = new Intent(context, MainActivity.class);
			myIntent.putExtras(params);
			activity.startActivityForResult(myIntent, 0);
		}
	}

	@Override
	protected void onCancelled() {
		activity.mAuthTask = null;
		activity.showProgress(false);
	}
}
