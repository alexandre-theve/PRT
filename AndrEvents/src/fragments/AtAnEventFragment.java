package fragments;

import java.nio.charset.Charset;

import helpers.QRCodeHelper;
import model.Evenement;
import model.User;
import model.UserHasEvenement;
import activities.MyApplication;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.internal.ee;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.ig2i.andrevents.R;

import controller.EvenementController;
import controller.UserController;

/**
 * Fragment that appears in the "content_frame", shows a planet
 */
public class AtAnEventFragment extends Fragment implements
		CreateNdefMessageCallback {
	public static final String FRAGMENT_NUMBER = "fragment_number";

	private UserController userControler;
	private EvenementController evenementControler;
	private Evenement evenement;
	private UserHasEvenement inscription;

	public AtAnEventFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.at_an_event_fragment,
				container, false);

		/*
		 * int i = getArguments().getInt(FRAGMENT_NUMBER);
		 * 
		 * String title =
		 * getResources().getStringArray(R.array.titles_array)[i];
		 * Log.i("com.ig2i.andrevent", "onCreateView " + i + " - " + title);
		 * getActivity().setTitle(title);
		 */

		return rootView;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		this.evenement = (Evenement) getArguments()
				.getSerializable("evenement");
		this.userControler = ((MyApplication) getActivity()
				.getApplicationContext()).getUserController();
		this.evenementControler = ((MyApplication) getActivity()
				.getApplicationContext()).getEvenementController();

		((TextView) getActivity().findViewById(R.id.AtAnEventTitleTextView))
				.setText(evenement.getNom());
		((TextView) getActivity().findViewById(
				R.id.AtAnEventExplanationTextView))
				.setText(R.string.atAnEventExplanationMessage);
		this.inscription = userControler.getInscription(
				userControler.getUserConnected(), evenement);
		if (this.inscription.getUserHasEvenementPK() != null) {
			NfcAdapter.getDefaultAdapter(getActivity())
					.setNdefPushMessageCallback(this, getActivity());

			Bitmap bm;
			try {
				bm = QRCodeHelper.encodeAsBitmap(this.inscription.getCode(),
						BarcodeFormat.QR_CODE, 150);
				ImageView qrCode = (ImageView) getActivity().findViewById(
						R.id.AtAnEventQRCodeImageView);
				if (bm != null) {
					qrCode.setImageBitmap(bm);
				}

			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		return new NdefMessage( new NdefRecord[]{createMimeRecord("application/com.ig2i.andrevents.LoginActivity", this.inscription.getCode().getBytes())});
		}

	/**
	 * Creates a custom MIME type encapsulated in an NDEF record
	 * 
	 * @param mimeType
	 */
	public NdefRecord createMimeRecord(String mimeType, byte[] payload) {
		byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
		NdefRecord mimeRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
				mimeBytes, new byte[0], payload);
		return mimeRecord;
	}
}
