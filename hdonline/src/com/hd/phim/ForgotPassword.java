/**
 * 
 */
package com.hd.phim;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLPeerUnverifiedException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hd.phim.Utility.CheckConnectInternet;
import com.hd.phim.network.GetDataJsonFromServer;
import com.movie.hdonline.R;

/**
 * @author tuanhd
 * 
 */
public class ForgotPassword extends Activity implements OnClickListener {
	private WebView mWebSecurity;
	private EditText mEditEmail;
	private EditText mEditSecurity;
	private Button mBtnGetPass;
	private LoginSever mConnect;
	private boolean isValid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgot_password);
		initVar();
		initView();
		initActions();
	}

	private void initVar() {
		isValid = true;
	}

	private void initView() {
		mWebSecurity = (WebView) findViewById(R.id.webview_security_forgot_password);
		mEditEmail = (EditText) findViewById(R.id.edit_email_forgot_password);
		mEditSecurity = (EditText) findViewById(R.id.edit_security_forgot_password);
		mBtnGetPass = (Button) findViewById(R.id.btn_get_password);
	}

	private void initActions() {
		mWebSecurity.loadUrl(getString(R.string.security_url));
		mBtnGetPass.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(mEditEmail.getText().length() <= 0){
			mEditEmail.setError(getString(R.string.edit_empty));
			isValid = false;
		}
		if(mEditSecurity.getText().length() <= 0){
			mEditSecurity.setError(getString(R.string.edit_empty));
			isValid = false;
		}
		if(isValid)
		connectServer();
		else
			isValid = true;
	}
	private void connectServer(){
		if(CheckConnectInternet.checkInternetConnection(ForgotPassword.this)){
		mConnect = new LoginSever();
		mConnect.execute(getString(R.string.forgot_url));
		}else{
			showToast(getString(R.string.not_connect_internet));
		}
}
	
	private class LoginSever extends AsyncTask<String, Boolean, JSONObject> {

		private List<NameValuePair> listParams;
		private JSONObject jsonObj;
		private ProgressDialog mPrgDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mPrgDialog = new ProgressDialog(ForgotPassword.this);
			mPrgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mPrgDialog.setMessage(getString(R.string.connecting));
			
			listParams =  new ArrayList<NameValuePair>();
			listParams.add(new BasicNameValuePair("lost_page", "true"));
			listParams.add(new BasicNameValuePair("email", mEditEmail.getText().toString()));
			listParams.add(new BasicNameValuePair("security", mEditSecurity.getText().toString()));
		}
		
		@Override
		protected JSONObject doInBackground(String... params) {
			publishProgress(true);
			for (String param : params) {

				try {
					jsonObj = GetDataJsonFromServer.postJSONfromURL(param,
							listParams, 80, "HD Online version for Android");
				} catch (SSLPeerUnverifiedException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			publishProgress(false);
			return jsonObj;
		}

		@Override
		protected void onProgressUpdate(Boolean... values) {
			super.onProgressUpdate(values);
			if (values[0]) {
				if (!mPrgDialog.isShowing())
					mPrgDialog.show();
			}
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			if (mPrgDialog.isShowing())
				mPrgDialog.dismiss();
			if (null != result) {
				checkForgotResult(result);
			} else {
				showToast(getString(R.string.not_json_data));
			}
		}
	}
	private void showToast(String message){
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}
	
	private void checkForgotResult(JSONObject result){
		try {
			showToast(result.getString("message"));
			if(result.getBoolean("success")){
				this.finish();
				overridePendingTransition(R.anim.fade_in_left, R.anim.fade_out_left);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.fade_in_left, R.anim.fade_out_left);
	}
}
