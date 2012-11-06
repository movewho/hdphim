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

import com.hd.phim.Utility.CheckConnectInternet;
import com.hd.phim.network.GetDataJsonFromServer;
import com.movie.hdonline.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * @author hdtua_000
 *
 */
public class Register extends Activity implements OnClickListener {
	private WebView mWebSecurity;
	private LoginSever mConnect;
	private EditText mEditUserName;
	private EditText mEditPass;
	private EditText mEditConfirmPass;
	private EditText mEditEmail;
	private EditText mEditFullName;
	private EditText mEditYear;
	private RadioButton mRdbMale;
//	private RadioButton mRdbFemale;
	private EditText mEditPhone;
	private CheckBox mChxAgree;
	private EditText mEditSecurity;
	private Button mBtnRegis;
	private boolean isValid;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.register);
	initVar();
	initView();
	initActions();
}

private void initVar() {
	isValid = true;
}

private void initView() {
	mEditUserName = (EditText) this.findViewById(R.id.edit_username_regis);
	mEditPass = (EditText) this.findViewById(R.id.edit_password_regis);
	mEditConfirmPass = (EditText) this.findViewById(R.id.edit_confirm_password_regis);
	mWebSecurity = (WebView) findViewById(R.id.webview_security_regis);
	mEditEmail = (EditText) findViewById(R.id.edit_email_regis);
	mEditFullName = (EditText) this.findViewById(R.id.edit_fullname_regis);
	mEditYear = (EditText) this.findViewById(R.id.edit_year_regis);
	mRdbMale = (RadioButton) this.findViewById(R.id.radio_male);
//	mRdbFemale = (RadioButton) this.findViewById(R.id.radio_female);
	mEditPhone = (EditText) this.findViewById(R.id.edit_phone_regis);
	mChxAgree = (CheckBox) this.findViewById(R.id.check_agree_regis);
	mEditSecurity = (EditText) findViewById(R.id.edit_security_regis);
	mBtnRegis = (Button) findViewById(R.id.btn_regis);
}

private void initActions() {
	mWebSecurity.loadUrl(getString(R.string.security_url));
	mRdbMale.setChecked(true);
	mBtnRegis.setOnClickListener(this);
}
@Override
public void onClick(View v) {
	if(mEditUserName.getText().length() <= 0){
		mEditUserName.setError(getString(R.string.edit_empty));
		isValid = false;
	}
	if(mEditPass.getText().length() <= 0){
		mEditPass.setError(getString(R.string.edit_empty));
		isValid = false;
	}
	if(mEditConfirmPass.getText().length() <= 0){
		mEditConfirmPass.setError(getString(R.string.edit_empty));
		isValid = false;
	}
	if(mEditEmail.getText().length() <= 0){
		mEditEmail.setError(getString(R.string.edit_empty));
		isValid = false;
	}
	if(mEditFullName.getText().length() <= 0){
		mEditFullName.setError(getString(R.string.edit_empty));
		isValid = false;
	}
	if(mEditYear.getText().length() <= 0){
		mEditYear.setError(getString(R.string.edit_empty));
		isValid = false;
	}
	if(mEditPhone.getText().length() <= 0){
		mEditPhone.setError(getString(R.string.edit_empty));
		isValid = false;
	}
	if(mEditSecurity.getText().length() <= 0){
		mEditSecurity.setError(getString(R.string.edit_empty));
		isValid = false;
	}
	
	if(isValid)
		if(!mEditPass.getText().toString().equals(mEditConfirmPass.getText().toString())){
			mEditConfirmPass.setError(getString(R.string.confirm_pass_wrong));
		}else
	connectServer();
	else
		isValid = true;
}
private void connectServer(){
	if(CheckConnectInternet.checkInternetConnection(Register.this)){
	mConnect = new LoginSever();
	mConnect.execute(getString(R.string.regis_url));
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
		mPrgDialog = new ProgressDialog(Register.this);
		mPrgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mPrgDialog.setMessage(getString(R.string.connecting));
		String agree = "no";
		int gender = 2;
		if(mRdbMale.isChecked())
			gender = 1;
		if(mChxAgree.isChecked())
			agree = "yes";
		
		listParams =  new ArrayList<NameValuePair>();
		listParams.add(new BasicNameValuePair("reg_page", "true"));
		listParams.add(new BasicNameValuePair("username", mEditUserName.getText().toString()));
		listParams.add(new BasicNameValuePair("pass", mEditPass.getText().toString()));
		listParams.add(new BasicNameValuePair("confirmpass", mEditConfirmPass.getText().toString()));
		listParams.add(new BasicNameValuePair("email", mEditEmail.getText().toString()));
		listParams.add(new BasicNameValuePair("fullname", mEditFullName.getText().toString()));
		listParams.add(new BasicNameValuePair("year", mEditYear.getText().toString()));
		listParams.add(new BasicNameValuePair("gender", ""+gender));
		listParams.add(new BasicNameValuePair("phone", mEditPhone.getText().toString()));
		listParams.add(new BasicNameValuePair("agree", agree));
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
			checkRegistResult(result);
		} else {
			showToast(getString(R.string.not_json_data));
		}
	}
}
private void showToast(String message){
	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
}
private void checkRegistResult(JSONObject result){
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
