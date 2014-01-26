package assync;

import model.UserHasEvenement;
import activities.MyApplication;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

public class UserHasEvenementAsyncTask extends AsyncTask<Void, Void, UserHasEvenement> {
		private Activity activity;
		private String code;
		
		public UserHasEvenementAsyncTask(Activity activity, String code) {
			// TODO Auto-generated constructor stub
			this.activity = activity;
			this.code = code;
		}

		@Override
		protected UserHasEvenement doInBackground(Void... params) {
			return ((MyApplication)activity.getApplication()).getUserController().getUserHasEvenementByCode(code);
		}

		@Override
		protected void onPostExecute(final UserHasEvenement userHasEvenement) {
			Toast.makeText(activity.getApplicationContext(), userHasEvenement.getCode() + " : " + userHasEvenement.getUser().getNom() + " - " + userHasEvenement.getEvenement().getNom(),Toast.LENGTH_LONG).show();
		}
	}