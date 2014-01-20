package fragments;

import java.util.List;

import model.Evenement;
import model.User;
import model.UserHasEvenement;
import views.PullToRefreshListView;
import views.PullToRefreshListView.OnRefreshListener;
import activities.MainActivity;
import activities.MyApplication;
import adapters.EvenementAdapter;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;
import com.ig2i.andrevents.R;

import controller.EvenementController;
import controller.UserController;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class QRCodeFragment extends Fragment implements OnClickListener {
	public static final String FRAGMENT_NUMBER = "fragment_number";

	private UserController userControler;
	
	public QRCodeFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.qrcode_fragment, container,
				false);

		Button button = (Button) rootView.findViewById(R.id.scan_button);
		button.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		this.userControler = ((MyApplication) getActivity()
				.getApplicationContext()).getUserController();
		
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		((MainActivity) getActivity()).setDisplayedFragment(this);	
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) {
			if (resultCode == Activity.RESULT_OK) {
				UserHasEvenementAsyncTask userHasEvenementAsyncTask = new UserHasEvenementAsyncTask(getActivity(), data.getStringExtra("SCAN_RESULT"));
				userHasEvenementAsyncTask.execute();
			} else if (resultCode == Activity.RESULT_CANCELED) {
				Toast.makeText(getActivity(), "Scan cancelled.",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		startActivityForResult(intent, 0);
	}

	public class UserHasEvenementAsyncTask extends AsyncTask<Void, Void, UserHasEvenement> {
		private Context context;
		private String code;
		
		public UserHasEvenementAsyncTask(Context cont, String code) {
			// TODO Auto-generated constructor stub
			this.context = cont;
			this.code = code;
		}

		@Override
		protected UserHasEvenement doInBackground(Void... params) {
			return userControler.getUserHasEvenementByCode(code);
		}

		@Override
		protected void onPostExecute(final UserHasEvenement userHasEvenement) {
			Toast.makeText(context, userHasEvenement.getCode() + " : " + userHasEvenement.getUser().getNom() + " - " + userHasEvenement.getEvenement().getNom(),Toast.LENGTH_LONG).show();
		}
	}
}
