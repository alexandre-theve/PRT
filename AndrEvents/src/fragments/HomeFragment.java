package fragments;

import model.User;
import activities.MyApplication;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ig2i.andrevents.R;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class HomeFragment extends Fragment {
	public static final String FRAGMENT_NUMBER = "fragment_number";

	private User connectedUser;

	public HomeFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);

		int i = getArguments().getInt(FRAGMENT_NUMBER);
		String title = getResources().getStringArray(R.array.titles_array)[i];

		getActivity().setTitle(title);

		return rootView;
	}

	@Override
    public void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();	
    	connectedUser = ((MyApplication)getActivity().getApplicationContext()).getUserConnected();
    	TextView text = (TextView) getView().findViewById(R.id.textView1);
        text.setText(getResources().getString(R.string.welcome_text) + connectedUser.getFullname());
    }
}
