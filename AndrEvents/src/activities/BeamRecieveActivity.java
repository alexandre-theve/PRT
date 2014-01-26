package activities;

import model.UserHasEvenement;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.widget.Toast;
import assync.UserHasEvenementAsyncTask;

import com.ig2i.andrevents.R;

import controller.UserController;

public class BeamRecieveActivity extends Activity {

	UserController userControler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beam_recieve);
		MyApplication andrEvents = ((MyApplication) getApplicationContext());
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		andrEvents.setURL(preferences.getString("urlData", ""));
		userControler = andrEvents.getUserController();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.beam_recieve, menu);
		return true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			processNFCIntent(getIntent());
        }
	}
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		setIntent(intent);
		if(intent.getAction().equals(NfcAdapter.ACTION_NDEF_DISCOVERED)){
			processNFCIntent(intent);
		}
		super.onNewIntent(intent);
	}
	
	private void processNFCIntent(Intent intent) {
		// TODO Auto-generated method stub
			Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			NdefMessage msg =(NdefMessage)rawMsgs[0];
			String code =(new String(msg.getRecords()[0].getPayload()));
			UserHasEvenementAsyncTask userHasEvenementAsyncTask = new UserHasEvenementAsyncTask(this, code);
			userHasEvenementAsyncTask.execute();
	}
	
	
}
