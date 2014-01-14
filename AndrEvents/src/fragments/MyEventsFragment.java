package fragments;

import views.PullToRefreshListView;
import views.PullToRefreshListView.OnRefreshListener;
import model.Evenement;
import model.User;
import activities.MyApplication;
import adapters.EvenementAdapter;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
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
public class MyEventsFragment extends ListFragment implements
		OnItemClickListener {
	public static final String FRAGMENT_NUMBER = "fragment_number";

	private UserController userControler;
	private EvenementController evenementControler;
	private User connectedUser;

	private PullToRefreshListView liste;
	public MyEventsFragment() {
		// Empty constructor required for fragment subclasses
	}

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
		welcomeTextview.setText(getActivity().getString(R.string.welcome_text) + userControler.getFullname(userControler.getUserConnected()));
		liste.setLockScrollWhileRefreshing(true);
		liste.setOnItemClickListener(this);
		liste.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				
				// Make sure you call listView.onRefreshComplete()
				// when the loading is done. This can be done from here or any
				// other place, like on a broadcast receive from your loading
				// service or the onPostExecute of your AsyncTask.

				// For the sake of this sample, the code will pause here to
				// force a delay when invoking the refresh
				liste.postDelayed(new Runnable() {

					
					@Override
					public void run() {
						liste.onRefreshComplete();
					}
				}, 2000);
			}
		});
		fillListView(liste);

	}

	private void fillListView(ListView liste) {
		setListShown(true);
		if (userControler.getEvenementsOfUSer(userControler.getUserConnected()).size() == 0) {
			setEmptyText(getActivity().getResources().getText(
					R.string.noEventMessage));
			return;
		}
		liste.setAdapter(new EvenementAdapter(getActivity(), userControler.getEvenementsOfUSer(userControler
				.getUserConnected())));

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
