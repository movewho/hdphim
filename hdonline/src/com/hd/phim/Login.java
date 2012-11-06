/**
 * 
 */
package com.hd.phim;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.androidquery.AQuery;
import com.hd.phim.data.adapter.ListAdaperReview;
import com.movie.hdonline.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * @author nguyenquocchinh
 *
 */
public class Login extends Activity implements OnClickListener{
	
	private EditText edtitUsername;
	private EditText editPassword;
	private Button btnLogin;
	private WebView security;
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.login);
	
	edtitUsername = (EditText) findViewById(R.id.edit_username);
	editPassword = (EditText) findViewById(R.id.edit_password);
	security = (WebView) findViewById(R.id.security);
	
	security.loadUrl("http://hdonline.vn/security.php");
	
	btnLogin = (Button) findViewById(R.id.btn_login);
	btnLogin.setOnClickListener(this);
	
	
}

@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.btn_login:
	{
		if (edtitUsername.getText().length() == 0)
			edtitUsername.setError("Tên đăng nhập là bắt buộc nhập.");
		if( editPassword.getText().length() == 0)
			editPassword.setError("Mật khẩu là bắt buộc nhập.");
	}
		break;

	default:
		break;
	}
	
}

private class LoginSever extends AsyncTask<String, Void, JSONObject>{
	
	private View mContentView;
	private ListView mListOutstanding;
	private ListAdaperReview mAdapter;
	private List<NameValuePair> listParams;
	
	@Override
	protected JSONObject doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void onPostExecute(JSONObject result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		listParams =  new ArrayList<NameValuePair>();
		listParams.add(new BasicNameValuePair("login_box", "true"));
		listParams.add(new BasicNameValuePair("username", "1"));
		listParams.add(new BasicNameValuePair("pass", "1"));
		listParams.add(new BasicNameValuePair("pass", "1"));
		listParams.add(new BasicNameValuePair("security", "1"));
	}
	
}

}
