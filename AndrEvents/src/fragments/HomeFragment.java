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

import controller.EvenementController;
import controller.UserController;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class HomeFragment extends Fragment {
	public static final String FRAGMENT_NUMBER = "fragment_number";
	
	private UserController userControler;
	private EvenementController evenementControler;
	
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
    	this.userControler = ((MyApplication)getActivity().getApplicationContext()).getUserController();
		this.evenementControler = ((MyApplication)getActivity().getApplicationContext()).getEvenementController();
		
    	TextView text = (TextView) getView().findViewById(R.id.textView1);
        text.setText(getResources().getString(R.string.welcome_text)+ this.userControler.getFullname(userControler.getUserConnected()));
    }
}
