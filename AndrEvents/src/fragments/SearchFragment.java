package fragments;

import model.User;

import com.ig2i.andrevents.R;

import controller.EvenementController;
import controller.UserController;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchFragment extends Fragment {

	public static final String FRAGMENT_NUMBER = "fragment_number";

	private UserController userControler;
	private EvenementController evenementControler;
	private User connectedUser;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.my_events_fragment,
				container, false);
		int i = getArguments().getInt(FRAGMENT_NUMBER);
		String title = getResources().getStringArray(R.array.titles_array)[i];
		getActivity().setTitle(title);
		return rootView;
	}
}
