package fragments;

import java.util.ArrayList;
import java.util.List;

import model.Evenement;
import activities.MainActivity;
import activities.MyApplication;
import adapters.EvenementAdapter;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.ig2i.andrevents.R;

import controller.EvenementController;
import controller.UserController;
import fragments.HomeFragment.EventSuggestionTask;

public class SearchFragment extends ListFragment implements OnItemClickListener{

	public static final String FRAGMENT_NUMBER = "fragment_number";

	private UserController userControler;
	private EvenementController evenementControler;
	
	private ListView liste;
	private List<Evenement> evenements;
	private String query = "";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.search_fragment, container,
				false);
		int i = getArguments().getInt(FRAGMENT_NUMBER);
		String title = getResources().getStringArray(R.array.titles_array)[i];
		getActivity().setTitle(title);
		this.userControler = ((MyApplication) getActivity()
				.getApplicationContext()).getUserController();
		this.evenementControler = ((MyApplication) getActivity()
				.getApplicationContext()).getEvenementController();
		return rootView;
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		liste = (ListView) getActivity().findViewById(R.id.list);
		liste.setOnItemClickListener(this);
		setListShown(true);
		EventSearchTask eventSearchTask = new EventSearchTask(getActivity(), liste);
		eventSearchTask.execute();
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((MainActivity) getActivity()).setDisplayedFragment(this);		
		updateQuery(getActivity(), query);
	}
	
	public void updateQuery(Context context, String arg0) {
		this.query =arg0;
		if (evenements != null &&  liste != null){
		List<Evenement> toDisplay = evenementControler.getEventFromQuery(evenements,query);
		liste.setAdapter(new EvenementAdapter(context, toDisplay));
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		if (arg0.getId() == R.id.list) {
			Evenement evenement = (Evenement)(arg0.getAdapter().getItem(position));

			EventDetailFragment fragment = new EventDetailFragment();
			Bundle bundle = new Bundle();
			bundle.putSerializable("evenement", evenement);
			fragment.setArguments(bundle);
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment)
					.addToBackStack("MyEvents").commit();
		}
		InputMethodManager inputManager = 
		        (InputMethodManager) getActivity().getApplicationContext().
		            getSystemService(Context.INPUT_METHOD_SERVICE); 
		inputManager.hideSoftInputFromWindow(
		        this.getActivity().getCurrentFocus().getWindowToken(),
		        InputMethodManager.HIDE_NOT_ALWAYS); 
	}

	public class EventSearchTask extends AsyncTask<Void, Void, List<Evenement>> {
		private Context context;
		private ProgressDialog dialog;
		private ListView listView;
		
		public EventSearchTask(Context cont, ListView listView) {
			// TODO Auto-generated constructor stub
			this.context = cont;
			this.listView = listView;
		}
		
		@Override
		public void onPreExecute() {
			/*dialog = ProgressDialog.show(getActivity(), "Patientez...", 
                    "Chargement de la liste d'evenements", true);*/
		}

		@Override
		protected List<Evenement> doInBackground(Void... params) {
			return evenementControler.getEvents();
		}

		@Override
		protected void onPostExecute(final List<Evenement> results) {
			evenements = results;
			if (evenements.size() == 0) {
				setEmptyText(getActivity().getResources().getText(R.string.noEventFoundForSearchMessage));
				return;
			}
			updateQuery(getActivity(), query);
		}
	}
	
}
