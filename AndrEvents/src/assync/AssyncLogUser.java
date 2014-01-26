package assync;

import model.User;
import activities.MainActivity;
import activities.MyApplication;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.ig2i.andrevents.R;

public class AssyncLogUser  extends AsyncTask<Void, Void, Boolean>{

	private MainActivity activity;
	private User logingUser;
	public AssyncLogUser(MainActivity activity){
		this.activity = activity;
		
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		MyApplication andrEvents = (MyApplication)activity.getApplication();
		SharedPreferences prefs = andrEvents.getSharedPreferences(activity.getString(R.string.app_package), Context.MODE_PRIVATE);
		if (andrEvents.getUserController().getUserConnected() != null){
			this.logingUser = andrEvents.getUserController().getUserConnected();
			return true;
		}
		else if(prefs.getInt("loggedUserId", -1) != -1){
			logingUser = andrEvents.getUserController().getById(prefs.getInt("loggedUserId", -1));
			return logingUser.getId() != null;
		}
		else{
			return false;
		}
		
		
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		if(result && logingUser != null){
			MyApplication andrEvents =(MyApplication)activity.getApplication();
			andrEvents.setUserConnected(logingUser);
			activity.userLoggedin();
		}
		super.onPostExecute(result);
	}

}
