package fragments;

import helpers.GPSHelper;

import java.util.HashMap;
import java.util.List;

import model.Evenement;
import activities.MyApplication;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ig2i.andrevents.R;

import controller.EvenementController;
import controller.UserController;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class AroundMeFragment extends Fragment implements
		OnInfoWindowClickListener {
	public static final String FRAGMENT_NUMBER = "fragment_number";

	private UserController userControler;
	private EvenementController evenementControler;
	// Google Map
	private GoogleMap googleMap;
	private GPSHelper GPShelper;
	private HashMap<MarkerOptions, Evenement> markerToEvenement;
	Location loc;
	private static View view;

	public AroundMeFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
	try {
			view = inflater.inflate(R.layout.fragment_aroundme, container,
					false);
		} catch (Exception e) {

		}
		return view;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		this.userControler = ((MyApplication) getActivity().getApplicationContext()).getUserController();
		this.evenementControler = ((MyApplication) getActivity().getApplicationContext()).getEvenementController();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		loc = userControler.getUserCurrentPostition();
		

		if (googleMap == null) {
			MapFragment frag = ((MapFragment) getFragmentManager()
					.findFragmentById(R.id.map));
			googleMap = frag.getMap();
			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getActivity().getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
			googleMap.setMyLocationEnabled(true);
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);
			googleMap.setOnInfoWindowClickListener(this);
		}

		GetPOIWorker pois = new GetPOIWorker(this);
		pois.execute(loc);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();

	}

	public void setPOI(List<Evenement> evenements) {
		markerToEvenement = new HashMap<MarkerOptions, Evenement>();
		googleMap.clear();
		for (Evenement evenement : evenements) {
			MarkerOptions marker = new MarkerOptions()
					.position(evenement.getPosition())
					.title(evenement.getLieu())
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
					.snippet(evenement.getNom());

			markerToEvenement.put(marker, evenement);
			googleMap.addMarker(marker);
		}
	}

	private class GetPOIWorker extends
			AsyncTask<Location, Integer, List<Evenement>> {
		private AroundMeFragment frag;
		private double distance;

		public GetPOIWorker(AroundMeFragment frag) {
			// TODO Auto-generated constructor stub
			this.frag = frag;
		}
 
		@Override
		protected List<Evenement> doInBackground(Location... params) {
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
			distance = Double.valueOf(sharedPreferences.getString("distance", "100"));
			return evenementControler.getEventsByLocation(params[0].getLatitude(), params[0].getLongitude(), distance);
		}

		@Override
		protected void onPostExecute(List<Evenement> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			frag.setPOI(result);
			
			CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(loc.getLatitude(), loc.getLongitude()))
				.zoom(calculateZoomLevel(distance)).build();
			googleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));
		}
		
		private int calculateZoomLevel(double distance) {
			DisplayMetrics metrics = new DisplayMetrics();
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
			
		    double equatorLength = 40075004/2; // in meters
		    double widthInPixels = metrics.widthPixels;
		    double metersPerPixel = equatorLength / 256;
		    int zoomLevel = 0;
		    while ((metersPerPixel * widthInPixels) > distance * 1000) {
		        metersPerPixel /= 2;
		        zoomLevel++;
		    }
		    zoomLevel-=2;
		    Log.i("ADNAN", "zoom level = "+zoomLevel);
		    return zoomLevel;
		}
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		Evenement evenement = null;
		for (Evenement evt : markerToEvenement.values()) {
			if (evt.getNom().equals(arg0.getSnippet())
					&& evt.getLieu().equals(arg0.getTitle())) {
				evenement = evt;
				break;
			}
		}

		EventDetailFragment fragment = new EventDetailFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("evenement", evenement);
		fragment.setArguments(bundle);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment)
				.addToBackStack("aroundMe").commit();

	}

	
}
