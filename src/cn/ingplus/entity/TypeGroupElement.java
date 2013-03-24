package cn.ingplus.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 类别组元素
 * 
 * @author lv
 * 
 */
public class TypeGroupElement {
	private int id;
	private String type;
	private List<GoodsElement> goodslist = new ArrayList<GoodsElement>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<GoodsElement> getGoodslist() {
		return goodslist;
	}

	public void setGoodslist(List<GoodsElement> goodslist) {
		this.goodslist = goodslist;
	}
	
	public GoodsElement getGoodsElement(int childPosition) {
		return goodslist.get(childPosition);
	}
	
	public void putGoodsElement(GoodsElement element) {
		goodslist.add(element);
	}
	
	public int getChildSize() {
		return goodslist.size();
	}

}
