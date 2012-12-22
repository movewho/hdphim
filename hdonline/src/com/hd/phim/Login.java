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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hd.phim.Utility.CheckConnectInternet;
import com.hd.phim.network.GetDataJsonFromServer;
import com.hd.phim.data.adapter.MD5String;
import com.movie.hdonline.R;

/**
 * @author nguyenquocchinh
 *
 */
public class Login extends Activity implements OnClickListener{
	
	private EditText edtitUsername;
	private EditText editPassword;
	private Button btnLogin;
	private LoginSever mLogin;
	private TextView mTxtForgotPassword;
	private TextView mTxtRegister;
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.login);
	
	edtitUsername = (EditText) findViewById(R.id.edit_username);
	editPassword = (EditText) findViewById(R.id.edit_password);
	mTxtForgotPassword = (TextView) this.findViewById(R.id.txt_forgot);
	mTxtForgotPassword.setOnClickListener(this);
	mTxtRegister = (TextView) this.findViewById(R.id.txt_regis);
	mTxtRegister.setOnClickListener(this);
	
	btnLogin = (Button) findViewById(R.id.btn_login);
	btnLogin.setOnClickListener(this);
	
	
}

@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.btn_login:
	{
		if (edtitUsername.getText().length() == 0){
			edtitUsername.setError(getString(R.string.edit_empty));
		}else
		if( editPassword.getText().length() == 0){
			editPassword.setError(getString(R.string.edit_empty));
		}else{
			connectServer();
			btnLogin.setEnabled(false);
		}
	}
		break;
	case R.id.txt_forgot:
		moveActivity(ForgotPassword.class);
		break;
	case R.id.txt_regis:
		moveActivity(Register.class);
		break;
	}
}

private void connectServer(){
	if(CheckConnectInternet.checkInternetConnection(Login.this)){
		mLogin = new LoginSever();
		
		MD5String md5 = new MD5String();
		String str = md5.ConverStringToMD5();
		Log.w("token la ",str);
		
		String url = getString(R.string.login_url)+"?token="+str+"&username="+edtitUsername.getText().toString()+"&pass="+editPassword.getText().toString();
		Log.w("url la ", url);
		mLogin.execute(url);
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
		btnLogin.setEnabled(true);
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mPrgDialog = new ProgressDialog(Login.this);
		mPrgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mPrgDialog.setMessage(getString(R.string.connecting));
		
		
		
		listParams =  new ArrayList<NameValuePair>();
		//listParams.add(new BasicNameValuePair("token", str));
		//listParams.add(new BasicNameValuePair("username", edtitUsername.getText().toString()));
		//listParams.add(new BasicNameValuePair("pass", editPassword.getText().toString()));
	}
}
private void showToast(String message){
	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
}
private void moveActivity(Class<?> className){
	Intent i = new Intent(Login.this, className);
	startActivity(i);
	overridePendingTransition(R.anim.fade_in_right, R.anim.fade_out_right);
}
private void checkLoginCompleted(JSONObject jsonData){
	
		
		try {
			showToast("Bạn đã đăng nhập thành công vào hệ thống.!");
			if(jsonData.getBoolean("success")){
				moveActivity(HDMovie.class);
				this.finish();
			

}
		} catch (JSONException e) {
			showToast("Bạn đã đăng nhập không thành công.!");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}
