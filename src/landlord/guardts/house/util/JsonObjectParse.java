package landlord.guardts.house.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import landlord.guardts.house.model.HouseInfoModel;
import landlord.guardts.house.model.IdentifyModel;
import landlord.guardts.house.model.UserInfoModel;

public class JsonObjectParse {
	
	public static List<HouseInfoModel> parseUserHouseInfo(String value) {
		List<HouseInfoModel> list = new ArrayList<>();
		try{
			JSONArray array = new JSONArray(value);
			if (array != null){
				Log.i("house", "parse house info "+array.length());
				for (int item = 0; item < array.length(); item++){
					
					JSONObject itemJsonObject = array.optJSONObject(item);
					HouseInfoModel houseModel = new HouseInfoModel();
					houseModel.setHouseAddress(itemJsonObject.optString("RAddress"));
					houseModel.setHouseDirection(itemJsonObject.optString("RDirectionDesc"));
					houseModel.setHouseTotalFloor(itemJsonObject.optString("RTotalFloor"));
					houseModel.setHouseCurrentFloor(itemJsonObject.optString("RFloor"));
					houseModel.setHouseType(itemJsonObject.optString("RRoomTypeDesc"));
					houseModel.setHouseStatus(itemJsonObject.optString("IsAvailable"));
					houseModel.setHouseAvailable(itemJsonObject.optBoolean("Available"));
					houseModel.setHouseId(itemJsonObject.optString("RentNO"));
					houseModel.setHouseOwnerName(itemJsonObject.optString("ROwner"));
					houseModel.setHouseOwnerIdcard(itemJsonObject.optString("RIDCard"));
					list.add(houseModel);
				}
			}
			Log.i("house", "for item  "+list.size());
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}
	
	public static UserInfoModel parseUserInfo(String value) {
		UserInfoModel userInfo = null;
		try{
			JSONArray array = new JSONArray(value);
			if (array != null){
				Log.i("house", "parse house info "+array.length());
				//for (int item = 0; item < array.length(); item++){
					
					JSONObject itemJsonObject = array.optJSONObject(0);
					userInfo = new UserInfoModel();
					userInfo.setUserNickName(itemJsonObject.optString("NickName"));
					userInfo.setUserLoginName(itemJsonObject.optString("LoginName"));
					userInfo.setUserAddress(itemJsonObject.optString("Address"));
					userInfo.setUserIdCard(itemJsonObject.optString("IDCard"));
			}
			return userInfo;
		} catch (Exception e) {
			e.printStackTrace();
			return userInfo;
		}
	}
	
	public static List<String[]> parseHouseProperty(String value) {
		String [] property = null;
		String [] nameId;
		List<String[]> list = new ArrayList<>();
		try{
			JSONArray array = new JSONArray(value);
			if (array != null){
				Log.i("house", "parse house info "+array.length());
				property = new String[array.length()];
				nameId = new String[array.length()];
				for (int item = 0; item < array.length(); item++){
					JSONObject itemJsonObject = array.optJSONObject(item);
					property[item] = itemJsonObject.optString("RSOName");
					nameId[item] = itemJsonObject.optString("RSONo");
				}
				list.add(nameId);
				list.add(property);
			}
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}
	
	public static String [] parseHouseType(String value) {
		String [] property = null;
		try{
			JSONArray array = new JSONArray(value);
			if (array != null){
				Log.i("house", "parse house info "+array.length());
				property = new String[array.length()];
				for (int item = 0; item < array.length(); item++){
					
					JSONObject itemJsonObject = array.optJSONObject(item);
					property[item] = itemJsonObject.optString("RSOName");
				}
			}
			
			return property;
		} catch (Exception e) {
			e.printStackTrace();
			return property;
		}
	}
	
	public static List<String[]> parseHouseDistrict(String value) {
		String [] name;
		String [] nameId;
		List<String[]> list = new ArrayList<>();
		try{
			JSONArray array = new JSONArray(value);
			if (array != null){
				Log.i("house", "parse house info "+array.length());
				name = new String[array.length()];
				nameId = new String[array.length()];
				for (int item = 0; item < array.length(); item++){
					
					JSONObject itemJsonObject = array.optJSONObject(item);
					name[item] = itemJsonObject.optString("LDName");
					nameId[item] = itemJsonObject.optString("LDID");
				}
				list.add(nameId);
				list.add(name);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}
	
	public static List<String[]> parseHouseStreet(String value) {
		String [] name;
		String [] nameId;
		List<String[]> list = new ArrayList<>();
		try{
			JSONArray array = new JSONArray(value);
			if (array != null){
				Log.i("house", "parse house info "+array.length());
				name = new String[array.length()];
				nameId = new String[array.length()];
				for (int item = 0; item < array.length(); item++){
					
					JSONObject itemJsonObject = array.optJSONObject(item);
					name[item] = itemJsonObject.optString("LSName");
					nameId[item] = itemJsonObject.optString("LSID");
				}
				list.add(nameId);
				list.add(name);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}
	
	public static List<String[]> parseHouseRoad(String value) {
		String [] name;
		String [] nameId;
		List<String[]> list = new ArrayList<>();
		try{
			JSONArray array = new JSONArray(value);
			if (array != null){
				Log.i("house", "parse house info "+array.length());
				name = new String[array.length()];
				nameId = new String[array.length()];
				for (int item = 0; item < array.length(); item++){
					
					JSONObject itemJsonObject = array.optJSONObject(item);
					name[item] = itemJsonObject.optString("LRName");
					nameId[item] = itemJsonObject.optString("LRID");
				}
				list.add(nameId);
				list.add(name);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}
	
	public static String [] parseHouseId(String value) {
		String [] property = null;
		try{
			JSONArray array = new JSONArray(value);
			if (array != null){
				Log.i("house", "parse house info "+array.length());
				property = new String[array.length()];
				for (int item = 0; item < array.length(); item++){
					
					JSONObject itemJsonObject = array.optJSONObject(item);
					property[item] = itemJsonObject.optString("LDName");
				}
			}
			return property;
		} catch (Exception e) {
			e.printStackTrace();
			return property;
		}
	}
	
	public static List<String[]> parseHouseFenju(String value) {
		String [] name;
		String [] nameId;
		List<String[]> list = new ArrayList<>();
		try{
			JSONArray array = new JSONArray(value);
			if (array != null){
				Log.i("house", "parse house info "+array.length());
				name = new String[array.length()];
				nameId = new String[array.length()];
				for (int item = 0; item < array.length(); item++){
					
					JSONObject itemJsonObject = array.optJSONObject(item);
					name[item] = itemJsonObject.optString("PSName");
					nameId[item] = itemJsonObject.optString("PSID");
				}
				list.add(nameId);
				list.add(name);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
	}
	
	public static String [] parseHousePolice(String value) {
		String [] property = null;
		try{
			JSONArray array = new JSONArray(value);
			if (array != null){
				Log.i("house", "parse house info "+array.length());
				property = new String[array.length()];
				for (int item = 0; item < array.length(); item++){
					
					JSONObject itemJsonObject = array.optJSONObject(item);
					property[item] = itemJsonObject.optString("PSName");
				}
			}
			return property;
		} catch (Exception e) {
			e.printStackTrace();
			return property;
		}
	}
	
	public static String [] parseHouseRentType(String value) {
		String [] property = null;
		try{
			JSONArray array = new JSONArray(value);
			if (array != null){
				Log.i("house", "parse house info "+array.length());
				property = new String[array.length()];
				for (int item = 0; item < array.length(); item++){
					
					JSONObject itemJsonObject = array.optJSONObject(item);
					property[item] = itemJsonObject.optString("RSOName");
				}
			}
			return property;
		} catch (Exception e) {
			e.printStackTrace();
			return property;
		}
	}
	
	public static String [] parseHouseOwnType(String value) {
		String [] property = null;
		try{
			JSONArray array = new JSONArray(value);
			if (array != null){
				Log.i("house", "parse house info "+array.length());
				property = new String[array.length()];
				for (int item = 0; item < array.length(); item++){
					
					JSONObject itemJsonObject = array.optJSONObject(item);
					property[item] = itemJsonObject.optString("RSOName");
				}
			}
			return property;
		} catch (Exception e) {
			e.printStackTrace();
			return property;
		}
	}
	
	public static IdentifyModel parseIdentifyInfo(String value) {
		IdentifyModel model  = new IdentifyModel();
		try{
			JSONObject object = new JSONObject(value);
			Log.e("house", "  object  ");
			if (object != null){
					String ret = object.optString("ret");
					String desc = object.optString("desc");
					model.setIdentifyStatus(ret);
					model.setIdentifyInfo(desc);
					Log.e("house", "  ret  "+ret+" desc "+desc);
			}
			return model;
		} catch (Exception e) {
			e.printStackTrace();
			return model;
		}
	}
	
}
