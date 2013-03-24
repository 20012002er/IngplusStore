package cn.ingplus.entity;

import org.json.JSONArray;
import org.json.JSONException;

public class AccountInfo {

	private static JSONArray ai;
	private static int id;
	private static String name;
	private static int districtId;
	private static String address;
	private static String connectNo;
	private static int markId;
	private static String joinDate;
	private static int headShopId;
	private static int total;
	private static String details;
	private static String pwd;

	public static JSONArray getAi() {
		return ai;
	}

	public static void setAi(JSONArray ai) {
		AccountInfo.ai = ai;
		try {
			AccountInfo.id = ai.getJSONObject(0).getInt("id");
			AccountInfo.name = ai.getJSONObject(0).getString("name");
			AccountInfo.districtId = ai.getJSONObject(0).getInt("districtId");
			AccountInfo.address = ai.getJSONObject(0).getString("address");
			AccountInfo.connectNo = ai.getJSONObject(0).getString("connectNo");
			String tmp = ai.getJSONObject(0).getString("markId");
			if (tmp == null || tmp.equalsIgnoreCase("null"))
				AccountInfo.markId = -1;
			else
				AccountInfo.markId = Integer.parseInt(tmp);
			AccountInfo.joinDate = ai.getJSONObject(0).getString("joinDate");
			AccountInfo.headShopId = ai.getJSONObject(0).getInt("headShopId");
			AccountInfo.total = ai.getJSONObject(0).getInt("total");
			AccountInfo.details = ai.getJSONObject(0).getString("details");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		AccountInfo.id = id;
	}

	public static String getPwd() {
		return pwd;
	}

	public static void setPwd(String pwd) {
		AccountInfo.pwd = pwd;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		AccountInfo.name = name;
	}

	public static int getDistrictId() {
		return districtId;
	}

	public static void setDistrictId(int districtId) {
		AccountInfo.districtId = districtId;
	}

	public static String getAddress() {
		return address;
	}

	public static void setAddress(String address) {
		AccountInfo.address = address;
	}

	public static String getConnectNo() {
		return connectNo;
	}

	public static void setConnectNo(String connectNo) {
		AccountInfo.connectNo = connectNo;
	}

	public static int getMarkId() {
		return markId;
	}

	public static void setMarkId(int markId) {
		AccountInfo.markId = markId;
	}

	public static String getJoinDate() {
		return joinDate;
	}

	public static void setJoinDate(String joinDate) {
		AccountInfo.joinDate = joinDate;
	}

	public static int getHeadShopId() {
		return headShopId;
	}

	public static void setHeadShopId(int headShopId) {
		AccountInfo.headShopId = headShopId;
	}

	public static int getTotal() {
		return total;
	}

	public static void setTotal(int total) {
		AccountInfo.total = total;
	}

	public static String getDetails() {
		return details;
	}

	public static void setDetails(String details) {
		AccountInfo.details = details;
	}

}