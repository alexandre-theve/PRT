package adapters;

import helpers.EvenementHelper;

import java.util.List;

import com.ig2i.andrevents.R;

import model.Evenement;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EvenementAdapter extends BaseAdapter {

	private List<Evenement> evenements;
	private LayoutInflater inflater;

	public EvenementAdapter(Context context, List<Evenement> evenements) {
		inflater = LayoutInflater.from(context);
		this.evenements = evenements;
	}

	@Override
	public int getCount() {
		return evenements.size();
	}

	@Override
	public Object getItem(int arg0) {
		return evenements.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	private class ViewHolder {
		TextView eventTitle;
		TextView eventDate;
		TextView eventLocation;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.listitemsingleevent, null);

			holder.eventTitle = (TextView) convertView
					.findViewById(R.id.EvenementListItemeventTitle);
			holder.eventDate = (TextView) convertView
					.findViewById(R.id.EvenementListItemeventDate);
			holder.eventLocation = (TextView) convertView
					.findViewById(R.id.EvenementListItemeventLocation);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.eventTitle.setText(evenements.get(position).getNom());
		holder.eventDate.setText(EvenementHelper.getFormattedDate(evenements
				.get(position)));
		holder.eventLocation.setText(evenements.get(position).getLieu());

		return convertView;
	}

}
