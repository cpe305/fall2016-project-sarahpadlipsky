package com.example.sarahpadlipsky.iou;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import io.realm.Realm;

/**
 * Activity to assist with the log-in of a Google user.
 */
public class Login extends AppCompatActivity implements
    GoogleApiClient.OnConnectionFailedListener,
    View.OnClickListener {

  private static final String TAG = "LogInActivity";
  private static final int RC_SIGN_IN = 9001;

  private GoogleApiClient mGoogleApiClient;
  // Textview showing if a user us logged in.
  private TextView mStatusTextView;
  // Dialog to show progress.
  private ProgressDialog mProgressDialog;
  // Database connection.
  private Realm realm;

  /**
   * Android lifecycle function. Called when activity is opened for the first time.
   * @param savedInstanceState Lifecycle parameter
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_activity);

    realm = Realm.getDefaultInstance();

    // Views
    mStatusTextView = (TextView) findViewById(R.id.status);

    // Button listeners
    findViewById(R.id.sign_in_button).setOnClickListener(this);
    findViewById(R.id.sign_out_button).setOnClickListener(this);
    findViewById(R.id.continue_button).setOnClickListener(this);

    // Configure sign-in to request the user's ID, email address, and basic
    // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build();

    // Build a GoogleApiClient with access to the Google Sign-In API and the
    // options specified by gso.
    mGoogleApiClient = new GoogleApiClient.Builder(this)
        .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .build();

    // Set the dimensions of the sign-in button.
    SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
    signInButton.setSize(SignInButton.SIZE_WIDE);
  }

  /**
   * Android lifecycle function. Called when activity is re-opened.
   */
  @Override
  public void onStart() {
    super.onStart();

    OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
    if (opr.isDone()) {
      // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
      // and the GoogleSignInResult will be available instantly.
      Log.d(TAG, "Got cached sign-in");
      GoogleSignInResult result = opr.get();
      handleSignInResult(result);
    } else {
      // If the user has not previously signed in on this device or the sign-in has expired,
      // this asynchronous branch will attempt to sign in the user silently.  Cross-device
      // single sign-on will occur in this branch.
      showProgressDialog();
      opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
        @Override
        public void onResult(GoogleSignInResult googleSignInResult) {
          hideProgressDialog();
          handleSignInResult(googleSignInResult);
        }
      });
    }
  }


  /**
   * Adds logged in user to database if it is a new user.
   * @param acct The account of the signed in user.
   */
  public void addUserToDB(final GoogleSignInAccount acct) {

    User user = realm.where(User.class).equalTo("id", acct.getId()).findFirst();

    if (user == null) {

      realm.executeTransaction(new Realm.Transaction() {
        @Override
        public void execute(Realm realm) {
          User realmUser = realm.createObject(User.class,acct.getId());
          realmUser.setName(acct.getDisplayName());
          realmUser.setEmail(acct.getEmail());
          realmUser.setIsCurrentUser(true);

          CurrentUser.setCurrentUser(realmUser);
        }
      });

    } else {
      CurrentUser.setCurrentUser(user);
    }

  }


  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
    if (requestCode == RC_SIGN_IN) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      handleSignInResult(result);
    }
  }

  private void handleSignInResult(GoogleSignInResult result) {
    Log.d(TAG, "handleSignInResult:" + result.isSuccess());
    if (result.isSuccess()) {
      // Signed in successfully, show authenticated UI.
      GoogleSignInAccount acct = result.getSignInAccount();

      addUserToDB(acct);


      mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
      updateUI(true);
    } else {
      // Signed out, show unauthenticated UI.
      updateUI(false);
    }
  }


  /**
   * On-click method to start up GoogleApiClient sign-up.
   */
  private void signIn() {
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    startActivityForResult(signInIntent, RC_SIGN_IN);
  }

  /**
   * On-click method to log out the user.
   */
  private void signOut() {
    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
        new ResultCallback<Status>() {
          @Override
          public void onResult(Status status) {
            // [START_EXCLUDE]
            updateUI(false);
            // [END_EXCLUDE]
          }
        });
  }

  /**
   * On-click method to continue to application once logged in.
   */
  private void continueToApp() {
    Intent newActivity = new Intent(this, ViewGroups.class);
    startActivity(newActivity);
  }

  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {
    // An unresolvable error has occurred and Google APIs (including Sign-In) will not
    // be available.
    Log.d(TAG, "onConnectionFailed:" + connectionResult);
  }


  /**
   * Method to show progress dialog message.
   */
  private void showProgressDialog() {
    if (mProgressDialog == null) {
      mProgressDialog = new ProgressDialog(this);
      mProgressDialog.setMessage(getString(R.string.loading));
      mProgressDialog.setIndeterminate(true);
    }

    mProgressDialog.show();
  }

  /**
   * Method to hide dialog message.
   */
  private void hideProgressDialog() {
    if (mProgressDialog != null && mProgressDialog.isShowing()) {
      mProgressDialog.hide();
    }
  }

  /**
   * Method to update the UI whether or not a user is logged in.
   * @parm signedIn Boolean stating whether a user is logged in.
   */
  private void updateUI(boolean signedIn) {
    if (signedIn) {
      findViewById(R.id.sign_in_button).setVisibility(View.GONE);
      findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
    } else {
      mStatusTextView.setText(R.string.signed_out);

      findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
      findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
    }
  }

  /**
   * On-Click method for various buttons"
   */
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.sign_in_button:
        signIn();
        break;
      case R.id.sign_out_button:
        signOut();
        break;
      case R.id.continue_button:
        continueToApp();
        break;
    }
  }

  /**
   * Android lifecycle function. Called when activity is closed.
   */
  @Override
  protected void onDestroy() {
    super.onDestroy();
    realm.close();
  }
}
