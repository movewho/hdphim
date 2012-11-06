/**
 * 
 */
package com.hd.phim;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import javax.net.ssl.SSLPeerUnverifiedException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hd.phim.data.adapter.ListAdaperReview;
import com.hd.phim.network.GetDataJsonFromServer;
import com.movie.hdonline.R;

/**
 * @author nguyenquocchinh
 *
 */
public class Login extends Activity implements OnClickListener{
	
	private EditText edtitUsername;
	private EditText editPassword;
	private EditText editSecurity;
	private Button btnLogin;
	private WebView security;
	private LoginSever mLogin;
	private ProgressDialog mPrgDialog;
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.login);
	
	edtitUsername = (EditText) findViewById(R.id.edit_username);
	editPassword = (EditText) findViewById(R.id.edit_password);
	editSecurity = (EditText) this.findViewById(R.id.edit_security);
	security = (WebView) findViewById(R.id.security);
	mPrgDialog = new ProgressDialog(getApplicationContext());
	mPrgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	
	security.loadUrl("http://hdonline.vn/security.php");
	
	btnLogin = (Button) findViewById(R.id.btn_login);
	btnLogin.setOnClickListener(this);
	
	
}

@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.btn_login:
	{
		if (edtitUsername.getText().length() == 0){
			edtitUsername.setError(getString(R.string.accout_empty));
		}else
		if( editPassword.getText().length() == 0){
			editPassword.setError(getString(R.string.pass_empty));
		}else{
			mLogin = new LoginSever();
			mLogin.execute(getString(R.string.login_url));
		}
	}
		break;
	}
	//mac dinh dang nhap thanh cong
//	Intent i = new Intent(Login.this, HDMovie.class);
//	startActivity(i);
}

private class LoginSever extends AsyncTask<String, Boolean, JSONArray>{
	
	private View mContentView;
	private ListView mListOutstanding;
	private ListAdaperReview mAdapter;
	private List<NameValuePair> listParams;
	private JSONArray jsonArray;
	
	@Override
	protected JSONArray doInBackground(String... params) {
		publishProgress(true);
		for (String param : params) {
			
			try {
				jsonArray = GetDataJsonFromServer.postJSONfromURL(param, listParams, 80, "HD Online version for Android");
			} catch (SSLPeerUnverifiedException e) {
				e.printStackTrace();
			}
		}
		publishProgress(false);
		return jsonArray;
	}
	
	@Override
	protected void onProgressUpdate(Boolean... values) {
		super.onProgressUpdate(values);
	}
	@Override
	protected void onPostExecute(JSONArray result) {
		super.onPostExecute(result);
		if(null != result){
			int len = result.length();
			JSONObject jsonData = new JSONObject();
			try {
				for (int i = 0; i < len; i++) {
						jsonData = result.getJSONObject(i);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			checkLoginCompleted(jsonData);
			}else{
				showToast(getString(R.string.not_json_data));
			}
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mPrgDialog.setMessage(Login.this.getString(R.string.connecting));
		listParams =  new ArrayList<NameValuePair>();
		listParams.add(new BasicNameValuePair("login_box", "true"));
		listParams.add(new BasicNameValuePair("username", edtitUsername.getText().toString()));
		listParams.add(new BasicNameValuePair("pass", editPassword.getText().toString()));
		listParams.add(new BasicNameValuePair("security", editSecurity.getText().toString()));
	}
}
private void showToast(String message){
	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
}
private void checkLoginCompleted(JSONObject jsonData){
	try {
		if(jsonData.getBoolean("success")){
			Intent i = new Intent(Login.this, HDMovie.class);
			startActivity(i);
			this.finish();
		}else{
			showToast(jsonData.getString("message"));
		}
	} catch (JSONException e) {
		Log.v("get json", "loi");
		e.printStackTrace();
	}
}
}
