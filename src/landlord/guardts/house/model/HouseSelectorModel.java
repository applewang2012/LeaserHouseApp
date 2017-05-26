package landlord.guardts.house.model;

import javax.crypto.Mac;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.AsyncTask;
import android.util.Log;
import landlord.guardts.house.present.HoursePresenter;
import landlord.guardts.house.util.CommonUtil;

public class HouseSelectorModel {



	
	private String[] allValue;
	private String selectValue;
	private String orginText;
	private String[] allValueId;
	private String selectId;

	public String getHouseSelectValue() {
		return selectValue;
	}

	public void setHouseSelectValue(String val) {
		this.selectValue = val;
	}
	
	public String getHouseSelectId() {
		return selectId;
	}

	public void setHouseSelectId(String id) {
		this.selectId = id;
	}

	public String getHouseOrginText() {
		return orginText;
	}

	public void setHouseOrginText(String text) {
		this.orginText = text;
	}
	
	public String [] getHouseAllContent() {
		return allValue;
	}

	public void setHouseAllContent(String [] value) {
		this.allValue = value;
	}
	
	public String [] getHouseAllId() {
		return allValueId;
	}

	public void setHouseAllId(String [] value) {
		this.allValueId = value;
	}
	
}
