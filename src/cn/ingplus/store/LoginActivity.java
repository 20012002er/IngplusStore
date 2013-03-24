package cn.ingplus.store;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import cn.ingplus.entity.AccountInfo;
import cn.ingplus.store.R;
import cn.ingplus.util.SoapUtil;


/**
 * 登录界面
 * 
 * @author lv
 * 
 */
public class LoginActivity extends Activity {
	private Button login_btn;
	private EditText name_txt, pwd_txt;

	ProgressDialog dialogWait;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		this.name_txt = (EditText) this.findViewById(R.id.account_txt);
		this.pwd_txt = (EditText) this.findViewById(R.id.password_txt);
		this.login_btn = (Button) this.findViewById(R.id.login_btn);

		final AlertDialog.Builder alert = new AlertDialog.Builder(this);

		pwd_txt.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (KeyEvent.KEYCODE_ENTER == keyCode
						&& event.getAction() == KeyEvent.ACTION_DOWN) {
					login_btn.performClick();
					return true;
				}
				return false;
			}
		});

		login_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (name_txt.getText().toString().trim().length() == 0) {
					alert.setMessage(getResources().getString(
							R.string.enter_name));
					alert.show();
				} else if (pwd_txt.getText().toString().trim().length() == 0) {
					alert.setMessage(getResources().getString(
							R.string.enter_pwd));
					alert.show();
				} else {
					dialogWait = ProgressDialog.show(LoginActivity.this, "",
							"正在登录，请稍后...");
					LoginTask task = new LoginTask();
					task.execute();
				}
			}
		});
	}

	// 登录任务
	private class LoginTask extends AsyncTask<String, Integer, String> {
		private String name;
		private String pwd;

		public LoginTask() {
			this.name = name_txt.getText().toString().trim();
			this.pwd = pwd_txt.getText().toString().trim();
		}

		@Override
		protected String doInBackground(String... params) {
			String URL = "http://wsdl.ingplus.cn/service/ShopWebService";
			final String NAMESPACE = "http://shop.webservice.saleplus.com/";
			final String METHOD_NAME = "loginByAccountString";
			SoapSerializationEnvelope envelope;
			try {
				envelope = SoapUtil.getEnvelope(URL, NAMESPACE, METHOD_NAME,
						new Object[] { name, pwd });
				if (envelope.bodyIn instanceof SoapFault) {
					return "error";
				}
				return envelope.getResponse() == null ? "" : envelope
						.getResponse().toString();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null || result.equals("error")) {
				new AlertDialog.Builder(LoginActivity.this)
						.setMessage("登录失败，请检查网络连接。")
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
									}
								}).show();
				dialogWait.dismiss();
			} else if (result.equals("-1")) {
				new AlertDialog.Builder(LoginActivity.this)
						.setMessage("登录失败，请检查用户名和密码。")
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
									}
								}).show();
				dialogWait.dismiss();
			} else {
				JSONArray ja;
				try {
					ja = new JSONArray(result);
					AccountInfo.setAi(ja);
					Intent nextIntent = new Intent();
					nextIntent.setClass(LoginActivity.this, MainActivity.class);
					startActivity(nextIntent);
					LoginActivity.this.finish();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				dialogWait.dismiss();
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}
}