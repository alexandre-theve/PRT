package fragments;

import helpers.EvenementHelper;
import model.Evenement;
import model.User;
import activities.MainActivity;
import activities.MyApplication;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ig2i.andrevents.R;

import controller.EvenementController;
import controller.UserController;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class EventDetailFragment extends Fragment implements OnMapClickListener,OnClickListener {
	public static final String FRAGMENT_NUMBER = "fragment_number";

	private GoogleMap googleMap;
	private Evenement evenement;
	private static View view;
	private ImageView participateIcone;
	
	private UserController userControler;
	private EvenementController evenementControler;
	
	
	public EventDetailFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		try
		{
		view = inflater.inflate(R.layout.event_detail_fragment,
				container, false);
		}
		catch (Exception e){
						
		}
		
		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		this.evenement = (Evenement) getArguments().getSerializable("evenement");
		this.userControler = ((MyApplication) getActivity().getApplicationContext()).getUserController();
		this.evenementControler = ((MyApplication) getActivity().getApplicationContext()).getEvenementController();
		
		
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.Smallmap)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getActivity().getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
					evenement.getPosition(), 14.0f));
			MarkerOptions marker = new MarkerOptions().position(
					evenement.getPosition()).icon(
					BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_RED));
			googleMap.addMarker(marker);
			googleMap.setMyLocationEnabled(true);
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);
			googleMap.setOnMapClickListener(this);
		}
		
		((TextView)getView().findViewById(R.id.textViewDetailEventTitle)).setText(this.evenement.getNom());
		((TextView)getView().findViewById(R.id.textViewDetailEventDate)).setText(EvenementHelper.getFormattedDate(evenement));
		((TextView)getView().findViewById(R.id.textViewDetailEventLocation)).setText(this.evenement.getLieu());
		((TextView)getView().findViewById(R.id.textViewDetailEventDescription)).setText(this.evenement.getDescription());
		((TextView)getView().findViewById(R.id.textViewDetailEventCreator)).setText("Contact : "+ userControler.getFullname(evenement.getCreateur()));
		ImageView phoneIcone = (ImageView) getActivity().findViewById(
				R.id.imageDetailEventPhone);
		phoneIcone.setOnClickListener(this);
		ImageView mailIcone = (ImageView) getActivity().findViewById(
				R.id.imageDetailEventMail);
		mailIcone.setOnClickListener(this);
		participateIcone = (ImageView) getActivity().findViewById(
				R.id.imageViewParticipate);
		participateIcone.setOnClickListener(this);
		participateIcone.setImageResource(R.drawable.participerbutton);	
		if(userControler.isSubscribedTo(userControler.getUserConnected(), evenement)){
			participateIcone.setImageResource(R.drawable.participatingbutton);			
		}

	}
	
	@Override
	public void onResume() {
		super.onResume();
		((MainActivity) getActivity()).setDisplayedFragment(this);	
	}

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub
		Intent i = new Intent(Intent.ACTION_VIEW,
				Uri.parse("google.navigation:q=" + evenement.getLieu()));
		getActivity().startActivity(i);
	}
	
	@Override
	public void onClick(View arg0) {
		if (arg0.getId() == R.id.imageDetailEventMail) {
			Intent email = new Intent(Intent.ACTION_SEND);
			String to = evenement.getCreateur().getEmail();
			email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
			// need this to prompts email client only
			email.setType("message/rfc822");
			email.putExtra(Intent.EXTRA_SUBJECT, "A propos de " + evenement.getNom());
            email.putExtra(Intent.EXTRA_TEXT, "Bonjour, " + userControler.getFullname(evenement.getCreateur()));
			startActivity(Intent.createChooser(email, "Choissez un client e-mail"));
			return;
		}
		if (arg0.getId() == R.id.imageDetailEventPhone){
			String phone = evenement.getCreateur().getPhone();
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone));
			startActivity(intent);
			return;	
		}
		if (arg0.getId() == R.id.imageViewParticipate){
			if (userControler.isSubscribedTo(userControler.getUserConnected(), evenement)) {
				UserUnsubscribeTask userUnsubscribeTask = new UserUnsubscribeTask(getActivity());
				userUnsubscribeTask.execute();
			} else {
				UserSubscribeTask userSubscribeTask = new UserSubscribeTask(getActivity());
				userSubscribeTask.execute();
			}
		}
	}
	
	public class UserSubscribeTask extends AsyncTask<Void, Void, User> {
		private Context context;
		private ProgressDialog dialog;
		
		public UserSubscribeTask(Context cont) {
			// TODO Auto-generated constructor stub
			this.context = cont;
		}
		
		@Override
		public void onPreExecute() {
			dialog = ProgressDialog.show(getActivity(), "Patientez...", 
                    "Inscription en cours", true);
		}

		@Override
		protected User doInBackground(Void... params) {
			return evenementControler.subscribe(userControler.getUserConnected().getId(), evenement.getId());
		}

		@Override
		protected void onPostExecute(final User user) {
			if(user.getId() != null && userControler.isSubscribedTo(user, evenement)){
				userControler.getUserConnected().setUserHasEvenementList(user.getUserHasEvenementList());
				dialog.dismiss();
				AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
				alert.setTitle(getActivity().getResources().getString(R.string.error));
				alert.setMessage(getActivity().getResources().getString(R.string.errorInscription));
				participateIcone.setImageResource(R.drawable.participatingbutton);
				return;
			} else {
				Toast.makeText(getActivity(), "Inscription impossible !", Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();
		}
	}
	
	public class UserUnsubscribeTask extends AsyncTask<Void, Void, User> {
		private Context context;
		private ProgressDialog dialog;
		
		public UserUnsubscribeTask(Context cont) {
			// TODO Auto-generated constructor stub
			this.context = cont;
		}
		
		@Override
		public void onPreExecute() {
			dialog = ProgressDialog.show(getActivity(), "Patientez...", 
                    "Desinscription en cours", true);
		}

		@Override
		protected User doInBackground(Void... params) {
			return evenementControler.unsubscribe(userControler.getUserConnected().getId(), evenement.getId());
		}

		@Override
		protected void onPostExecute(final User user) {
			if(user.getId() != null && !userControler.isSubscribedTo(user, evenement)){
				userControler.getUserConnected().setUserHasEvenementList(user.getUserHasEvenementList());
				dialog.dismiss();
				AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
				alert.setTitle(getActivity().getResources().getString(R.string.error));
				alert.setMessage(getActivity().getResources().getString(R.string.errorInscription));
				participateIcone.setImageResource(R.drawable.participerbutton);
				return;
			} else {
				Toast.makeText(getActivity(), "desinscription impossible !", Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();
		}
	}
}
