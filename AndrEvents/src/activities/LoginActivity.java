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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */

	/**
	 * The default email to populate the email field with.
	 */

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mLogin;
	private String mPassword;

	// UI references.
	private EditText mLoginView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		// Set up the login form.
		mLoginView = (EditText) findViewById(R.id.login);
		mLoginView.setText(mLogin);

		mPasswordView = (EditText) findViewById(R.id.password);
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

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						getWindow().setSoftInputMode(
							      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
						attemptLogin();
						
					}
				});
		mLoginView.setText("admin");
		mPasswordView.setText("admin");
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
		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask(this);
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

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		private User loggingUser;
		private Context context;
		// REST_COMMUNCATION_ERROR = 0
		// "WRONG_PASSWORD" =1
		// "USER_UNKNOWN_ERROR" =2
		private Integer error = -1;

		public UserLoginTask(Context cont) {
			// TODO Auto-generated constructor stub
			this.context = cont;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				MyApplication myapp = (MyApplication) getApplication();
				loggingUser = myapp.getUserController().getUser1(mLogin,mPassword);
				if (loggingUser.getId() != -1
						&& loggingUser.getPassword().equals(mPassword)) {
					return true;
				}
				if (loggingUser.getId() == 0) {
					// USER_UNKNOWN_ERROR
					error = 2;
					return false;
				}
				if (loggingUser.getId() == -1) {
					// REST_COMMUNCATION_ERROR
					error = 0;
					return false;
				}
				if (!loggingUser.getPassword().equals(mPasswordView.getText())) {
					// "WRONG_PASSWORD"
					error = 1;
				}
				return false;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				Bundle params = new Bundle();
				params.putSerializable("user", loggingUser);
				Intent myIntent = new Intent(context, MainActivity.class);
				myIntent.putExtras(params);
				startActivityForResult(myIntent, 0);
			} else {
				switch (error) {
				case 0:
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							context);
					alertDialogBuilder.setTitle("Veuillez r��ssayer.")
					.setMessage("Erreur de communication avec le serveur")
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                    }
					})
					.setCancelable(false)
					.create()
					.show();
				
					break;
				case 1:
					mPasswordView
					.setError(getString(R.string.error_incorrect_password));
			mPasswordView.requestFocus();
					break;
				case 2:
					loggingUser = new User(-1,mLogin,mPassword,"","","","");
					Bundle params = new Bundle();
					params.putSerializable("user", loggingUser);
					Intent myIntent = new Intent(context, CreateUserActivity.class);
					myIntent.putExtras(params);
					startActivityForResult(myIntent, 0);
				}
				
				
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
