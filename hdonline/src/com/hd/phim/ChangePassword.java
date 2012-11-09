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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hd.phim.Utility.CheckConnectInternet;
import com.hd.phim.network.GetDataJsonFromServer;
import com.movie.hdonline.R;

/**
 * @author hdtua_000
 *
 */
public class ChangePassword extends Activity implements OnClickListener {
	private EditText mEditOldPass;
	private EditText mEditNewPass;
	private EditText mEditConfirmNewPass;
	private Button mBtnChangePass;
	private com.hd.phim.ChangePassword.LoginSever mChangePass;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.change_pass);
	mEditOldPass = (EditText) this.findViewById(R.id.edit_old_pass);
	mEditNewPass = (EditText) this.findViewById(R.id.edit_new_pass);
	mEditConfirmNewPass = (EditText) this.findViewById(R.id.edit_confirm_new_pass);
	mBtnChangePass = (Button) this.findViewById(R.id.btn_change_password);
}
@Override
protected void onResume() {
	super.onResume();
	mBtnChangePass.setOnClickListener(this);
}
@Override
public void onClick(View v) {
	boolean isValid = true;
	if(mEditOldPass.getText().length() <=0){
		mEditOldPass.setError(getString(R.string.edit_empty));
		isValid = false;
	}
	if(mEditConfirmNewPass.getText().length() <=0){
		mEditConfirmNewPass.setError(getString(R.string.edit_empty));
		isValid = false;
	}
	if(mEditNewPass.getText().length() <=0){
		mEditNewPass.setError(getString(R.string.edit_empty));
		isValid = false;
	}
	if(isValid){
		connectServer();
	}
}
private void connectServer(){
	if(CheckConnectInternet.checkInternetConnection(ChangePassword.this)){
		mChangePass = new LoginSever();
		mChangePass.execute(getString(R.string.login_url));
	}else{
		showToast(getString(R.string.not_connect_internet));
	}
}
private class LoginSever extends AsyncTask<String, Boolean, JSONObject>{
	
	private List<NameValuePair> listParams;
	private JSONObject jsonObj;
	private ProgressDialog mPrgDialog;
	
	@Override
	protected JSONObject doInBackground(String... params) {
		publishProgress(true);
		for (String param : params) {
			
			try {
				jsonObj = GetDataJsonFromServer.postJSONfromURL(param, listParams, 80, "HD Online version for Android");
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
		if(values[0]){
			if(!mPrgDialog.isShowing())
				mPrgDialog.show();
		}
	}
	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
		if(mPrgDialog.isShowing())
				mPrgDialog.dismiss();
		if(null != result){
			checkLoginCompleted(result);
		}else{
			showToast(getString(R.string.not_json_data));
		}
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mPrgDialog = new ProgressDialog(ChangePassword.this);
		mPrgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mPrgDialog.setMessage(getString(R.string.connecting));
		
		listParams =  new ArrayList<NameValuePair>();
		listParams.add(new BasicNameValuePair("change_page", "true"));
		listParams.add(new BasicNameValuePair("pass", mEditOldPass.getText().toString()));
		listParams.add(new BasicNameValuePair("newpass", mEditNewPass.getText().toString()));
		listParams.add(new BasicNameValuePair("comfirmpass", mEditConfirmNewPass.getText().toString()));
	}
}
private void showToast(String message){
	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
}
private void checkLoginCompleted(JSONObject jsonData){
	try {
		showToast(jsonData.getString("message"));
		
		if(jsonData.getBoolean("success")){
			this.finish();
		}
	} catch (JSONException e) {
		Log.e("get json", "loi");
		e.printStackTrace();
	}
}
}
