package cn.ingplus.store;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.ingplus.store.R;

/**
 * 登陆后跳转主界面
 * 
 * @author lv
 * 
 */
@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup {
	private LinearLayout menuLayout, orderLayout, preorderLayout;
	private TextView menuTv, orderTv, preorderTv;
	private LinearLayout container;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		container = (LinearLayout) findViewById(R.id.containerBody);

		this.menuTv = (TextView) this.findViewById(R.id.menu_tv);
		this.orderTv = (TextView) this.findViewById(R.id.order_tv);
		this.preorderTv = (TextView) this.findViewById(R.id.preorder_tv);

		this.menuLayout = (LinearLayout) this.findViewById(R.id.foot_menu_ly);
		this.menuLayout.setOnClickListener(clickMenu);
		this.menuLayout.setSelected(true);
		this.menuTv.setTextColor(Color.BLACK);

		this.orderLayout = (LinearLayout) this.findViewById(R.id.foot_order_ly);
		this.orderLayout.setOnClickListener(clickOrder);

		this.preorderLayout = (LinearLayout) this
				.findViewById(R.id.foot_preorder_ly);
		this.preorderLayout.setOnClickListener(clickPreorder);

		container.removeAllViews();
		container.addView(getLocalActivityManager().startActivity(
				"Menu",
				new Intent(MainActivity.this, MenuActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView());
	}

	private OnClickListener clickMenu = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (!menuLayout.isSelected()) {
				container.removeAllViews();
				container.addView(getLocalActivityManager().startActivity(
						"Menu",
						new Intent(MainActivity.this, MenuActivity.class)
								.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
						.getDecorView());
			}

			menuLayout.setSelected(true);
			orderLayout.setSelected(false);
			preorderLayout.setSelected(false);
			menuTv.setTextColor(Color.BLACK);
			orderTv.setTextColor(Color.WHITE);
			preorderTv.setTextColor(Color.WHITE);
		}
	};

	private OnClickListener clickOrder = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (!orderLayout.isSelected()) {
				container.removeAllViews();
				container.addView(getLocalActivityManager().startActivity(
						"Order",
						new Intent(MainActivity.this, OrderActivity.class)
								.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
						.getDecorView());
			}

			orderLayout.setSelected(true);
			menuLayout.setSelected(false);
			preorderLayout.setSelected(false);
			menuTv.setTextColor(Color.WHITE);
			orderTv.setTextColor(Color.BLACK);
			preorderTv.setTextColor(Color.WHITE);
		}
	};

	private OnClickListener clickPreorder = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (!preorderLayout.isSelected()) {
				container.removeAllViews();
				container.addView(getLocalActivityManager().startActivity(
						"Preorder",
						new Intent(MainActivity.this, PreorderActivity.class)
								.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
						.getDecorView());
			}

			preorderLayout.setSelected(true);
			orderLayout.setSelected(false);
			menuLayout.setSelected(false);
			menuTv.setTextColor(Color.WHITE);
			orderTv.setTextColor(Color.WHITE);
			preorderTv.setTextColor(Color.BLACK);
		}
	};

}
