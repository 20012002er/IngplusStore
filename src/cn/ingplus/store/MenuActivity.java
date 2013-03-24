package cn.ingplus.store;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ExpandableListView;
import cn.ingplus.entity.AccountInfo;
import cn.ingplus.entity.GoodsElement;
import cn.ingplus.entity.TypeExpandAdapter;
import cn.ingplus.entity.TypeGroupElement;
import cn.ingplus.store.R;
import cn.ingplus.util.SoapUtil;


/**
 * menu ac
 * 
 * @author lv
 * 
 */
public class MenuActivity extends Activity {
	private TypeExpandAdapter adapter;
	private ExpandableListView exList;
	private ProgressDialog dialogWait;

	private List<TypeGroupElement> parentList = new ArrayList<TypeGroupElement>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		exList = (ExpandableListView) this.findViewById(R.id.extypelist);
		adapter = new TypeExpandAdapter(MenuActivity.this, parentList);
		exList.setAdapter(adapter);
		exList.setGroupIndicator(null);
		exList.setDivider(null);

		dialogWait = ProgressDialog.show(MenuActivity.this, "",
				"正在加载数据，请稍后...");
		ResourceTask task = new ResourceTask();
		task.execute();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		adapter = new TypeExpandAdapter(MenuActivity.this, parentList);
		exList.setAdapter(adapter);
		super.onNewIntent(intent);
	}

	/**
	 * set ex list value
	 * 
	 * @param result
	 */
	private void setElement(String result) {
		if (result == null) {
			return;
		}
		if (result.trim().length() > 0) {
			try {
				JSONArray ja = new JSONArray(result);
				for (int i = 0; i < ja.length(); i++) {
					TypeGroupElement el = new TypeGroupElement();
					JSONObject item = ja.getJSONObject(i);
					el.setId(item.getInt("id"));
					el.setType(item.getString("type"));
					JSONArray goods = item.getJSONArray("goods");
					for (int j = 0; j < goods.length(); j++) {
						GoodsElement gl = new GoodsElement();
						JSONObject g = goods.getJSONObject(j);
						gl.setId(g.getInt("id"));
						gl.setName(g.getString("name"));
						gl.setCredits(g.getString("credits"));
						gl.setPrice(g.getString("price"));
						el.putGoodsElement(gl);
					}
					this.parentList.add(el);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			adapter = (TypeExpandAdapter) exList.getExpandableListAdapter();
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * get net resource
	 * 
	 * @author lv
	 * 
	 */
	private class ResourceTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			String URL = "http://wsdl.ingplus.cn/service/GoodsWebService";
			String NAMESPACE = "http://goods.webservice.saleplus.com/";
			String METHOD_NAME = "getAllTypeAndGoods";
			try {
				SoapSerializationEnvelope envelope = SoapUtil.getEnvelope(URL,
						NAMESPACE, METHOD_NAME,
						new Object[] { AccountInfo.getHeadShopId() });
				return envelope.getResponse() == null ? "" : envelope
						.getResponse().toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null || result.equals("error")) {
				new AlertDialog.Builder(MenuActivity.this)
						.setMessage("获取商品信息失败，请检查网络连接。")
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
									}
								}).show();
			} else {
				setElement(result);
			}
			dialogWait.dismiss();
		}

	}
	
}
