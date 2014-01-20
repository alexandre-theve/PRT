package activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.ig2i.andrevents.R;

public class PreferencesActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.andreventpreferences);
	}
}
