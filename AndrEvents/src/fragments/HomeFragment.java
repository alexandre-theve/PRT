package fragments;

import java.util.List;

import model.Evenement;
import model.User;
import views.PullToRefreshListView;
import views.PullToRefreshListView.OnRefreshListener;
import activities.MyApplication;
import adapters.EvenementAdapter;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ig2i.andrevents.R;

import controller.EvenementController;
import controller.UserController;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class HomeFragment extends ListFragment implements
		OnItemClickListener {
	public static final String FRAGMENT_NUMBER = "fragment_number";

	private UserController userControler;
	private EvenementController evenementControler;
	private User connectedUser;

	private PullToRefreshListView liste;
	public HomeFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.my_events_fragment,
				container, false);
		/*int i = getArguments().getInt(FRAGMENT_NUMBER);
		String title = getResources().getStringArray(R.array.titles_array)[i];
		getActivity().setTitle(title);*/
		return rootView;
	}

	 
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		liste = (PullToRefreshListView) getActivity().findViewById(R.id.list);
		
		this.userControler = ((MyApplication) getActivity()
				.getApplicationContext()).getUserController();
		this.evenementControler = ((MyApplication) getActivity()
				.getApplicationContext()).getEvenementController();
		TextView welcomeTextview = (TextView)getActivity().findViewById(R.id.my_events_welcometext);
		welcomeTextview.setText(getActivity().getString(R.string.welcome_text) + " " + userControler.getFullname(userControler.getUserConnected()));
		liste.setLockScrollWhileRefreshing(true);
		liste.setOnItemClickListener(this);
		liste.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				Log.i("com.ig2i.andrevent", "onrefresh");
				EventSuggestionTask eventSuggestionTask = new EventSuggestionTask(getActivity(), liste);
				eventSuggestionTask.execute();
			}
		});
		
		setListShown(true);

		liste.setRefreshing();
	}
	
	public class EventSuggestionTask extends AsyncTask<Void, Void, List<Evenement>> {
		private Context context;
		private ProgressDialog dialog;
		private ListView listView;
		
		public EventSuggestionTask(Context cont, ListView listView) {
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
			return evenementControler.getEventsForUSer(userControler.getUserConnected().getId());
		}

		@Override
		protected void onPostExecute(final List<Evenement> evenements) {
			if (evenements.size() == 0) {
				setEmptyText(getActivity().getResources().getText(R.string.noEventMessage));
				return;
			}
			liste.setAdapter(new EvenementAdapter(getActivity(), evenements));
			
			liste.onRefreshComplete();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View holder, int position,
			long id) {
		if (arg0.getId() == R.id.list) {
			HeaderViewListAdapter evtadpt= (HeaderViewListAdapter)arg0.getAdapter();
			Evenement evenement = (Evenement)(evtadpt.getWrappedAdapter().getItem(position));

			EventDetailFragment fragment = new EventDetailFragment();
			Bundle bundle = new Bundle();
			bundle.putSerializable("evenement", evenement);
			fragment.setArguments(bundle);
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment)
					.addToBackStack("MyEvents").commit();
		}
	}

}
