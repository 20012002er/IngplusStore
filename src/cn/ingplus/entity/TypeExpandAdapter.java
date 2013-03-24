package cn.ingplus.entity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import cn.ingplus.store.R;

public class TypeExpandAdapter extends BaseExpandableListAdapter {
	private LayoutInflater layoutInflater;
	private Context context;

	private List<TypeGroupElement> parentList = new ArrayList<TypeGroupElement>();

	public TypeExpandAdapter(Context context, List<TypeGroupElement> parentList) {
		this.context = context;
		this.parentList = parentList;
		this.layoutInflater = LayoutInflater.from(this.context);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		TypeGroupElement group = parentList.get(groupPosition);
		if (group != null) {
			return group.getGoodsElement(childPosition);
		}
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.type_expand, null);
		}
		TypeGroupElement typeElement = parentList.get(groupPosition);
		final GoodsElement goodsElement = typeElement
				.getGoodsElement(childPosition);
		if (goodsElement == null) {
			return null;
		}
		final TextView goodsname = (TextView) convertView
				.findViewById(R.id.goodsname);
		goodsname.setText(goodsElement.getName());
		final TextView credits = (TextView) convertView
				.findViewById(R.id.credits);
		credits.setText("送" + goodsElement.getCredits() + "积分");
		final TextView price = (TextView) convertView.findViewById(R.id.price);
		price.setText(goodsElement.getPrice() + "元");

		final ImageView add_btn = (ImageView) convertView
				.findViewById(R.id.add_btn);
		final ImageView del_btn = (ImageView) convertView
				.findViewById(R.id.del_btn);
		final TextView goodsNum = (TextView) convertView
				.findViewById(R.id.number);
		goodsNum.setText(String.valueOf(goodsElement.getNum()));
		final CheckBox checkbox = (CheckBox) convertView
				.findViewById(R.id.checkbox);
		checkbox.setChecked(goodsElement.isChecked());

		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (goodsElement.isChecked()) {
					goodsElement.setChecked(false);
				} else {
					goodsElement.setChecked(true);
				}
			}
		});

		add_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goodsElement.setNum(goodsElement.getNum() + 1);
				goodsNum.setText(String.valueOf(goodsElement.getNum()));
			}
		});

		del_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (goodsElement.getNum() > 1) {
					goodsElement.setNum(goodsElement.getNum() - 1);
					goodsNum.setText(String.valueOf(goodsElement.getNum()));
				}
			}
		});
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		TypeGroupElement group = parentList.get(groupPosition);
		if (group != null) {
			return group.getChildSize();
		}
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return parentList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return parentList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.type, null);
		}
		final TextView type = (TextView) convertView.findViewById(R.id.type_tv);
		type.setText(parentList.get(groupPosition).getType());
		final ImageView imageView = (ImageView) convertView
				.findViewById(R.id.list_icon);
		if (isExpanded) {
			imageView.setBackgroundResource(R.drawable.up_arrow);
		} else {
			imageView.setBackgroundResource(R.drawable.down_arrow);
		}
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

}
