package activities;

import model.User;

import com.ig2i.andrevents.R;
import com.ig2i.andrevents.R.id;
import com.ig2i.andrevents.R.layout;
import com.ig2i.andrevents.R.menu;
import com.ig2i.andrevents.R.string;

import controller.UserController;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class CreateUserActivity extends Activity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private User creatingUser;
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mLogin;
	private String mPassword;
	private String meMail;
	private String mName;
	private String mPrenom;
	private String mPhone;
	

	// UI references.
	private EditText mLoginView;
	private EditText mPrenomView;
	private EditText mNameView;
	private EditText meMailView;
	private EditText mPhoneView;
	private EditText mPasswordView;
	private View mCreateFormView;
	private View mCreateStatusView;
	private TextView mCreateStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		creatingUser = (User)savedInstanceState.getSerializable("user");
		setContentView(R.layout.activity_create);

		// Set up the login form.
		mLoginView = (EditText) findViewById(R.id.loginCreate);
		mLoginView.setText(creatingUser.getLogin());

		
		mPasswordView = (EditText) findViewById(R.id.passwordCreate);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mCreateFormView = findViewById(R.id.login_form);
		mCreateStatusView = findViewById(R.id.login_status);
		mCreateStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		mNameView = (EditText) findViewById(R.id.nameCreate);
		mPrenomView = (EditText) findViewById(R.id.prenomCreate);
		meMailView = (EditText) findViewById(R.id.emailCreate);
		mPasswordView = (EditText) findViewById(R.id.passwordCreate);
		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
		mPasswordView.setText(creatingUser.getPassword());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mLoginView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mLogin = mLoginView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mPrenom = mPrenomView.getText().toString();
		mName = mNameView.getText().toString();
		mPhone = mPhoneView.getText().toString();
		meMail = meMailView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mLogin)) {
			mLoginView.setError(getString(R.string.error_field_required));
			focusView = mLoginView;
			cancel = true;
		}
		if (TextUtils.isEmpty(mName)) {
			mNameView.setError(getString(R.string.error_field_required));
			focusView = mNameView;
			cancel = true;
		}
		if (TextUtils.isEmpty(mPrenom)) {
			mPrenomView.setError(getString(R.string.error_field_required));
			focusView = mPrenomView;
			cancel = true;
		}
		
		if (TextUtils.isEmpty(mPhone)) {
			mPhoneView.setError(getString(R.string.error_field_required));
			focusView = mPhoneView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mCreateStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask(getApplicationContext());
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mCreateStatusView.setVisibility(View.VISIBLE);
			mCreateStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mCreateStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mCreateFormView.setVisibility(View.VISIBLE);
			mCreateFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mCreateFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mCreateStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mCreateFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		private Context context;
		
		public UserLoginTask(Context cont) {
			// TODO Auto-generated constructor stub
			this.context = cont;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			creatingUser.setEmail(meMail);
			creatingUser.setNom(mName);
			creatingUser.setPrenom(mPrenom);
			creatingUser.setPhone(mPhone);
			creatingUser.setLogin(mLogin);
			creatingUser.setPassword(mPassword);
			
			try {
				MyApplication myapp = (MyApplication) getApplication();
				creatingUser = myapp.getUserController().createUser(creatingUser);
				if (creatingUser.getId() != -1) {
					return true;
				}
				return false;
			} catch (Exception e) {
				return false;
			}

		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				Bundle params = new Bundle();
				params.putSerializable("user", creatingUser);
				Intent myIntent = new Intent(context, MainActivity.class);
				startActivityForResult(myIntent, 0);
			} else {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							context);
					alertDialogBuilder.setTitle("Erreur de communication avec le serveur")
					.setMessage("Veuillez rééssayer.")
					.setCancelable(false).create().show();
				
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
