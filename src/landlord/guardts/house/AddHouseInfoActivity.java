package landlord.guardts.house;

import java.util.HashMap;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import landlord.guardts.house.model.HouseSelectorModel;
import landlord.guardts.house.present.HoursePresenter;
import landlord.guardts.house.util.CommonUtil;
import landlord.guardts.house.util.JsonObjectParse;

public class AddHouseInfoActivity extends BaseActivity{

	private TextView mTitleBar;
	private View mLoadingView;
	private HoursePresenter mPresenter;
	private String mPropertyAction = "http://tempuri.org/GetHouseProperty";
	private String mTypeAction ="http://tempuri.org/GetHouseType";
	private String mDirectionAction ="http://tempuri.org/GetHouseDirection";
	private String mStuctureAction = "http://tempuri.org/GetHouseStructure";
	private String mBuildingStuctureAction = "http://tempuri.org/GetBuildingStructure";
	private String mDistrictAction = "http://tempuri.org/GetDistrictList";
	private String mStreetAction = "http://tempuri.org/GetStreetList";
	private String mRoadAction = "http://tempuri.org/GetRoadList";
	private String mFenjuAction = "http://tempuri.org/GetPoliceStationList";
	private String mPoliceAction = "http://tempuri.org/GetLocalPoliceStationList";
	private String mRentTypeAction = "http://tempuri.org/GetHouseRentType";
	private String mOwnerTypeAction = "http://tempuri.org/GetHouseOwnType";
	private String mAddHouseAction = "http://tempuri.org/AddRentInfo";
	private String mValidHouseIDAction ="http://tempuri.org/ValidateHouseID";
//	private Map<String, String> mSelectedMap = new HashMap<>();
//	private Map<String, String> mOriginText = new HashMap<>();
//	private Map<String, String[]> mAllList = new HashMap<>();
	private Map<String, HouseSelectorModel> mSelectorInfo = new HashMap<>();
	private TextView mPropertryTextView, mTypeTextView;
	private TextView mDirectionTextView;
	private TextView mStructureTextView;
	private TextView mDistrictTextView;
	private TextView mStrictTextView;
	private TextView mStreetTextView;
	private TextView mRoadTextView;
	private TextView mFenjuTextView;
	private TextView mPoliceTextView;
	private TextView mRentTypeTextView;
	private TextView mHouseTypeTextView;
	private TextView mOwnerTypeTextView;
	private String mRentNo;
	private String mRDName;
	private String mRSName;
	private String mRRName;
	private String mRPSName;
	private String mRAddress;
	private String mRDoor;
	private String mRTotalDoor;
	private String mRRoomType;
	private String mRDirection;
	private String mRStructure;
	private String mRTotalFloor;
	private String mRFloor;
	private String mRHouseAge;
	private String mRRentArea;
	private String mRProperty;
	private String mROwner;
	private String mROwnerTel;
	private String mRIDCard;
	private String mRPSParentName;
	private String mrentType, mownType;
	private TextView mBuildingStructureTextView;
	private String mRBuildingType;
	private String mUserName;
	private boolean mValidHouseId = false;
	private EditText mHouseNo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.add_house_info); 
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		mTitleBar = (TextView)findViewById(R.id.id_titlebar);
		mTitleBar.setText("房屋信息");
		
		initView();
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mUserName = getIntent().getStringExtra("user_name");
	}



	private void initView(){
		mPresenter = new HoursePresenter(getApplicationContext(), this);
		mLoadingView = (View)findViewById(R.id.id_data_loading);
		mLoadingView.setVisibility(View.INVISIBLE);
		
		mHouseNo = (EditText)findViewById(R.id.id_add_house_id_number);
		mHouseNo.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus){
					getHouseValidId();
				}
			}
		});
		FrameLayout propertryFrameLayout = (FrameLayout)findViewById(R.id.id_add_house_property);
		mPropertryTextView = (TextView)findViewById(R.id.id_add_house_property_text);
		propertryFrameLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getHouseProperty();
			}
		});
		FrameLayout typeFrameLayout = (FrameLayout)findViewById(R.id.id_add_house_type);
		mTypeTextView = (TextView)findViewById(R.id.id_add_house_type_text);
		typeFrameLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getHouseType();
			}
		});
		FrameLayout directionFrameLayout = (FrameLayout)findViewById(R.id.id_add_house_direction);
		mDirectionTextView = (TextView)findViewById(R.id.id_add_house_direction_text);
		directionFrameLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getHouseDirection();
			}
		});
		FrameLayout structionFrameLayout = (FrameLayout)findViewById(R.id.id_add_house_structure);
		mStructureTextView = (TextView)findViewById(R.id.id_add_house_structure_text);
		structionFrameLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getHouseStructure();
			}
		});
		
		FrameLayout buildStuctFrameLayout = (FrameLayout)findViewById(R.id.id_add_house_building_structure);
		mBuildingStructureTextView = (TextView)findViewById(R.id.id_add_house_building_structure_text);
		buildStuctFrameLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getHouseBuildingStructure();
			}
		});
		
		FrameLayout quyuFrameLayout = (FrameLayout)findViewById(R.id.id_add_house_quyu);
		mDistrictTextView = (TextView)findViewById(R.id.id_add_house_quyu_text);
		quyuFrameLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getHouseDistrict();
			}
		});
		
		FrameLayout streetFrameLayout = (FrameLayout)findViewById(R.id.id_add_house_street);
		mStreetTextView = (TextView)findViewById(R.id.id_add_house_street_text);
		streetFrameLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mSelectorInfo.get("district") != null && mSelectorInfo.get("district").getHouseSelectId() != null){
					getHouseStreet();
				}else{
					Toast.makeText(getApplicationContext(), "请先选择区域", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		FrameLayout roadFrameLayout = (FrameLayout)findViewById(R.id.id_add_house_road);
		mRoadTextView = (TextView)findViewById(R.id.id_add_house_road_text);
		roadFrameLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mSelectorInfo.get("street") != null && mSelectorInfo.get("street").getHouseSelectId() != null){
					getHouseRoad();
				}else{
					Toast.makeText(getApplicationContext(), "请先选择街道", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		FrameLayout fenjuFrameLayout = (FrameLayout)findViewById(R.id.id_add_house_police_fenju);
		mFenjuTextView = (TextView)findViewById(R.id.id_add_house_police_fenju_text);
		fenjuFrameLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getHouseFenju();
			}
		});
		
		FrameLayout policeFrameLayout = (FrameLayout)findViewById(R.id.id_add_house_police);
		mPoliceTextView = (TextView)findViewById(R.id.id_add_house_police_text);
		policeFrameLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mSelectorInfo.get("fenju") != null && mSelectorInfo.get("fenju").getHouseSelectId() != null){
					getHousePolice();	
				}else{
					Toast.makeText(getApplicationContext(), "请先选择分局", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
//		FrameLayout houseTypeFrameLayout = (FrameLayout)findViewById(R.id.id_add_house_owner_type);
//		mHouseTypeTextView = (TextView)findViewById(R.id.id_add_house_owner_type_text);
//		houseTypeFrameLayout.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				//getHouseFenju();
//			}
//		});
		
		FrameLayout rentTypeFrameLayout = (FrameLayout)findViewById(R.id.id_add_house_rent_type);
		mRentTypeTextView = (TextView)findViewById(R.id.id_add_house_rent_type_text);
		rentTypeFrameLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getHouseRentType();
			}
		});
		
		FrameLayout ownerTypeFrameLayout = (FrameLayout)findViewById(R.id.id_add_house_owner_type);
		mOwnerTypeTextView = (TextView)findViewById(R.id.id_add_house_owner_type_text);
		ownerTypeFrameLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getHouseOwnersType();
			}
		});
		
		Button okButton = (Button)findViewById(R.id.id_add_house_info_confirm);
		okButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!mValidHouseId){
					Toast.makeText(getApplicationContext(), "房产证编号输入有误", Toast.LENGTH_SHORT).show();
					return;
				}
				if (checkInputContent()){
					startAddHouseInfo();
				}
			}
		});
	}
	
	private boolean checkInputContent(){
		
		if (mSelectorInfo.get("property") == null || mSelectorInfo.get("property").getHouseSelectId() == null){
			Toast.makeText(getApplicationContext(), "请选择房产性质", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mRProperty = mSelectorInfo.get("property").getHouseSelectId();
		}
		
		if (mSelectorInfo.get("type") == null || mSelectorInfo.get("type").getHouseSelectId() == null){
			Toast.makeText(getApplicationContext(), "请选择房型", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mRRoomType = mSelectorInfo.get("type").getHouseSelectId();
		}
		
		if (mSelectorInfo.get("direction") == null || mSelectorInfo.get("direction").getHouseSelectId() == null){
			Toast.makeText(getApplicationContext(), "请选择朝向", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mRDirection = mSelectorInfo.get("direction").getHouseSelectId();
		}
		
		if (mSelectorInfo.get("structure") == null || mSelectorInfo.get("structure").getHouseSelectId() == null){
			Toast.makeText(getApplicationContext(), "请选择结构", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mRStructure = mSelectorInfo.get("structure").getHouseSelectId();
		}
		
		if (mSelectorInfo.get("buildingstructure") == null || mSelectorInfo.get("buildingstructure").getHouseSelectId() == null){
			Toast.makeText(getApplicationContext(), "请选择建筑结构", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mRBuildingType = mSelectorInfo.get("buildingstructure").getHouseSelectId();
		}
		
		if (mSelectorInfo.get("district") == null || mSelectorInfo.get("district").getHouseSelectId() == null){
			Toast.makeText(getApplicationContext(), "请选择区域", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mRDName = mSelectorInfo.get("district").getHouseSelectId();
		}
		
		if (mSelectorInfo.get("street") == null || mSelectorInfo.get("street").getHouseSelectId() == null){
			Toast.makeText(getApplicationContext(), "请选择街道", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mRSName = mSelectorInfo.get("street").getHouseSelectId();
		}
		
		if (mSelectorInfo.get("police") == null || mSelectorInfo.get("police").getHouseSelectId() == null){
			Toast.makeText(getApplicationContext(), "请选择派出所", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mRPSName = mSelectorInfo.get("police").getHouseSelectId();
		}
		
		if (mSelectorInfo.get("road") == null || mSelectorInfo.get("road").getHouseSelectId() == null){
			Toast.makeText(getApplicationContext(), "请选择道路", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mRRName = mSelectorInfo.get("road").getHouseSelectId();
		}
		
		if (mSelectorInfo.get("fenju") == null || mSelectorInfo.get("fenju").getHouseSelectId() == null){
			Toast.makeText(getApplicationContext(), "请选择分局", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mRPSParentName = mSelectorInfo.get("fenju").getHouseSelectId();
		}
		
		if (mSelectorInfo.get("renttype") == null || mSelectorInfo.get("renttype").getHouseSelectId() == null){
			Toast.makeText(getApplicationContext(), "请选择租赁类型", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mrentType = mSelectorInfo.get("renttype").getHouseSelectId();
		}
		
		if (mSelectorInfo.get("ownertype") == null || mSelectorInfo.get("ownertype").getHouseSelectId() == null){
			Toast.makeText(getApplicationContext(), "请选择房屋类型", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mownType = mSelectorInfo.get("ownertype").getHouseSelectId();
		}
		
		EditText age = (EditText)findViewById(R.id.id_add_house_age);
		if (age.getText().toString() == null || age.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "请输入房龄", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mRHouseAge = age.getText().toString();
		}
		EditText area = (EditText)findViewById(R.id.id_add_house_area);
		if (area.getText().toString() == null || area.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "请输入房屋面积", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mRRentArea = area.getText().toString();
		}
		EditText total_floor = (EditText)findViewById(R.id.id_add_house_total_floor);
		if (total_floor.getText().toString() == null || total_floor.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "请输入总楼层", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mRTotalFloor = total_floor.getText().toString();
		}
		EditText current_floor = (EditText)findViewById(R.id.id_add_house_current_floor);
		if (current_floor.getText().toString() == null || current_floor.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "请输入所在楼层", Toast.LENGTH_SHORT).show();
			return false;
		}else{ 
			mRFloor = current_floor.getText().toString();
		}
		EditText total_num = (EditText)findViewById(R.id.id_add_house_total_num);
		if (total_num.getText().toString() == null || total_num.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "请输入每层户数", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mRTotalDoor = total_num.getText().toString();
		}
		EditText current_num = (EditText)findViewById(R.id.id_add_house_current_num);
		if (current_num.getText().toString() == null || current_num.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "请输入门牌号", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mRDoor = current_num.getText().toString();
		}
		EditText address = (EditText)findViewById(R.id.id_add_house_address);
		if (address.getText().toString() == null || address.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "请输入详细地址", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mRAddress = address.getText().toString();
		}
		
		if (mHouseNo.getText().toString() == null || mHouseNo.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "请输入房产证编号", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mRentNo = mHouseNo.getText().toString();
		}
		EditText owner_name = (EditText)findViewById(R.id.id_add_house_owner_name);
		if (owner_name.getText().toString() == null || owner_name.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "请输入房主姓名", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mROwner = owner_name.getText().toString();
		}
		EditText phone = (EditText)findViewById(R.id.id_add_house_owner_phone);
		if (phone.getText().toString() == null || phone.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "请输入房主手机", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mROwnerTel = phone.getText().toString();
		}
		EditText owner_id = (EditText)findViewById(R.id.id_add_house_owner_id_card);
		if (owner_id.getText().toString() == null || owner_id.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "请输入房主身份证号码", Toast.LENGTH_SHORT).show();
			return false;
		}else{
			mRIDCard = owner_id.getText().toString();
		}
		
		return true;
		
		
//		
//		if (mSelectorInfo.get("property") == null || mSelectorInfo.get("property").getHouseSelectId() == null){
//			Toast.makeText(getApplicationContext(), "请先选择区域", Toast.LENGTH_SHORT).show();
//		}
		
	}
	
	private void getHouseValidId(){
			String url = "http://qxw2332340157.my3w.com/services.asmx?op=ValidateHouseID";
			SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mValidHouseIDAction));
			rpc.addProperty("houseID", mHouseNo.getText().toString());
			mPresenter.readyPresentServiceParams(getApplicationContext(), url, mValidHouseIDAction, rpc);
			mPresenter.startPresentServiceTask();
	}
	
	private void getHouseProperty(){
		if (!mSelectorInfo.containsKey("property")){
			showLoadingView();
			HouseSelectorModel property = new HouseSelectorModel();
			property.setHouseOrginText((String) mPropertryTextView.getText());
			mSelectorInfo.put("property", property);
			String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetHouseProperty";
			SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mPropertyAction));
			mPresenter.readyPresentServiceParams(getApplicationContext(), url, mPropertyAction, rpc);
			mPresenter.startPresentServiceTask();
		}else{
			showAlertDialog(mPropertryTextView, "property", mSelectorInfo.get("property").getHouseAllContent());
		}
		
		//[{"RSOUrl":"0","IsVisible":null,"RSOName":"私产","RSOParentNo":8,"RSOID":15,"RSONo":0,"RSOOrder":1},{"RSOUrl":"0","IsVisible":null,"RSOName":"公产","RSOParentNo":8,"RSOID":16,"RSONo":0,"RSOOrder":2}]
	}
	
	private void getHouseType(){
		if (!mSelectorInfo.containsKey("type")){
			showLoadingView();
			HouseSelectorModel type = new HouseSelectorModel();
			type.setHouseOrginText((String) mTypeTextView.getText());
			mSelectorInfo.put("type", type);
			String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetHouseType";
			SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mTypeAction));
			mPresenter.readyPresentServiceParams(getApplicationContext(), url, mTypeAction, rpc);
			mPresenter.startPresentServiceTask();
		}else{
			showAlertDialog(mTypeTextView, "type", mSelectorInfo.get("type").getHouseAllContent());
		}
	}
	
	private void getHouseDirection(){
		if (!mSelectorInfo.containsKey("direction")){
			showLoadingView();
			HouseSelectorModel direction = new HouseSelectorModel();
			direction.setHouseOrginText((String) mDirectionTextView.getText());
			mSelectorInfo.put("direction", direction);
			String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetHouseDirection";
			SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mDirectionAction));
			mPresenter.readyPresentServiceParams(getApplicationContext(), url, mDirectionAction, rpc);
			mPresenter.startPresentServiceTask();
		}else{
			showAlertDialog(mDirectionTextView, "direction", mSelectorInfo.get("direction").getHouseAllContent());
		}
	}
	
	private void getHouseStructure(){
		if (!mSelectorInfo.containsKey("structure")){
			showLoadingView();
			HouseSelectorModel direction = new HouseSelectorModel();
			direction.setHouseOrginText((String) mStructureTextView.getText());
			mSelectorInfo.put("structure", direction);
			String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetHouseStructure";
			SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mStuctureAction));
			mPresenter.readyPresentServiceParams(getApplicationContext(), url, mStuctureAction, rpc);
			mPresenter.startPresentServiceTask();
		}else{
			showAlertDialog(mStructureTextView, "structure", mSelectorInfo.get("structure").getHouseAllContent());
		}
		
	}
	
	private void getHouseBuildingStructure(){
		if (!mSelectorInfo.containsKey("buildingstructure")){
			showLoadingView();
			HouseSelectorModel direction = new HouseSelectorModel();
			direction.setHouseOrginText((String) mBuildingStructureTextView.getText());
			mSelectorInfo.put("buildingstructure", direction);
			String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetBuildingStructure";
			SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mBuildingStuctureAction));
			mPresenter.readyPresentServiceParams(getApplicationContext(), url, mBuildingStuctureAction, rpc);
			mPresenter.startPresentServiceTask();
		}else{
			showAlertDialog(mBuildingStructureTextView, "buildingstructure", mSelectorInfo.get("buildingstructure").getHouseAllContent());
		}
		
	}
	
	private void getHouseRentType(){
		if (!mSelectorInfo.containsKey("renttype")){
			showLoadingView();
			HouseSelectorModel direction = new HouseSelectorModel();
			direction.setHouseOrginText((String) mRentTypeTextView.getText());
			mSelectorInfo.put("renttype", direction);
			String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetHouseRentType";
			SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mRentTypeAction));
			mPresenter.readyPresentServiceParams(getApplicationContext(), url, mRentTypeAction, rpc);
			mPresenter.startPresentServiceTask();
		}else{
			showAlertDialog(mRentTypeTextView, "renttype", mSelectorInfo.get("renttype").getHouseAllContent());
		}
	}
	
	private void getHouseOwnersType(){
		if (!mSelectorInfo.containsKey("ownertype")){
			showLoadingView();
			HouseSelectorModel direction = new HouseSelectorModel();
			direction.setHouseOrginText((String) mOwnerTypeTextView.getText());
			mSelectorInfo.put("ownertype", direction);
			String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetHouseOwnType";
			SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mOwnerTypeAction));
			mPresenter.readyPresentServiceParams(getApplicationContext(), url, mOwnerTypeAction, rpc);
			mPresenter.startPresentServiceTask();
		}else{
			showAlertDialog(mOwnerTypeTextView, "ownertype", mSelectorInfo.get("ownertype").getHouseAllContent());
		}
		
	}
	
	private void getHouseDistrict(){
		if (mSelectorInfo.containsKey("street")){
			mStreetTextView.setText(mSelectorInfo.get("street").getHouseOrginText());
		}
		if (mSelectorInfo.containsKey("road")){
			mRoadTextView.setText(mSelectorInfo.get("road").getHouseOrginText());
		}
		if (!mSelectorInfo.containsKey("district")){
			showLoadingView();
			HouseSelectorModel direction = new HouseSelectorModel();
			direction.setHouseOrginText((String) mDistrictTextView.getText());
			mSelectorInfo.put("district", direction);
			String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetDistrictList";
			SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mDistrictAction));
			mPresenter.readyPresentServiceParams(getApplicationContext(), url, mDistrictAction, rpc);
			mPresenter.startPresentServiceTask();
		}else{
			showAlertDialog(mDistrictTextView, "district", mSelectorInfo.get("district").getHouseAllContent());
		}
		
	}
	
	private void getHouseRoad(){
		showLoadingView();
			HouseSelectorModel direction = new HouseSelectorModel();
			direction.setHouseOrginText((String) mRoadTextView.getText());
			mSelectorInfo.put("road", direction);
			String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetRoadList";
			SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mRoadAction));
			if (mSelectorInfo.get("street").getHouseSelectId() != null && !mSelectorInfo.get("street").getHouseSelectId().equals("")){
				rpc.addProperty("district", mSelectorInfo.get("district").getHouseSelectId());
				rpc.addProperty("street", mSelectorInfo.get("street").getHouseSelectId());
				mPresenter.readyPresentServiceParams(getApplicationContext(), url, mRoadAction, rpc);
				mPresenter.startPresentServiceTask();
			}
	}
	
	private void startAddHouseInfo(){
		Log.w("mingguo", "add house info mRentNo "+mRentNo+" mRDName "+mRDName+" mRSName "+mRSName+" mRRName "+mRRName+" mRPSName "+mRPSName
				+" mRAddress "+mRAddress+" mRDoor "+mRDoor+" mRTotalDoor "+mRTotalDoor+" mRRoomType "+mRRoomType +" mRDirection "+mRDirection+
				" mRStructure "+mRStructure+" mRFloor "+mRFloor+" mRTotalFloor "+mRTotalFloor+" mRHouseAge "+mRHouseAge+" mRRentArea "+mRRentArea+
				" mRProperty "+mRProperty+" mROwner "+mROwner+" mROwnerTel "+mROwnerTel+" mRIDCard "+mRIDCard+" mRPSParentName "+mRPSParentName+" createdBy "+
				"mnz"+" mrentType "+mrentType+" mowntype "+mownType+"  RBuildingType "+mRBuildingType+"  RLocationDescription "+" desp ");
		showLoadingView();
		String url = "http://qxw2332340157.my3w.com/services.asmx?op=AddRentInfo";
		SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mAddHouseAction));
		rpc.addProperty("RentNo", mRentNo);   
		rpc.addProperty("RDName", mRDName);      
		rpc.addProperty("RSName", mRSName);  
		rpc.addProperty("RRName", mRRName);      
		rpc.addProperty("RPSName", mRPSName);  
		rpc.addProperty("RAddress", mRAddress);   
		rpc.addProperty("RDoor", mRDoor);    
		rpc.addProperty("RTotalDoor", mRTotalDoor); 
		
		rpc.addProperty("RRoomType", mRRoomType); 
		rpc.addProperty("RDirection", mRDirection);   
		rpc.addProperty("RStructure", mRStructure); 
		rpc.addProperty("RBuildingType", mRBuildingType); 
		rpc.addProperty("RFloor", mRFloor);     
		rpc.addProperty("RTotalFloor", mRTotalFloor);   
		rpc.addProperty("RHouseAge", mRHouseAge);  
		rpc.addProperty("RRentArea", mRRentArea);     
		
		rpc.addProperty("RProperty", mRProperty);  
		rpc.addProperty("ROwner", mROwner);        
		rpc.addProperty("ROwnerTel", mROwnerTel);  
		rpc.addProperty("RIDCard", mRIDCard);       
		rpc.addProperty("RLocationDescription", " desp ");  
		rpc.addProperty("RPSParentName", mRPSParentName);            
		rpc.addProperty("createdBy", mUserName);             
		rpc.addProperty("rentType", mrentType);                
		rpc.addProperty("ownType", mownType);         
		
		mPresenter.readyPresentServiceParams(getApplicationContext(), url, mAddHouseAction, rpc);
		mPresenter.startPresentServiceTask();
	}
	
	private void getHouseStreet(){
		if (mSelectorInfo.containsKey("road")){
			mRoadTextView.setText(mSelectorInfo.get("road").getHouseOrginText());
		}
		showLoadingView();
			HouseSelectorModel direction = new HouseSelectorModel();
			direction.setHouseOrginText((String) mStreetTextView.getText());
			mSelectorInfo.put("street", direction);
			String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetStreetList";
			SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mStreetAction));
			if (mSelectorInfo.get("district").getHouseSelectId() != null && !mSelectorInfo.get("district").getHouseSelectId().equals("")){
				rpc.addProperty("district", mSelectorInfo.get("district").getHouseSelectId());
				mPresenter.readyPresentServiceParams(getApplicationContext(), url, mStreetAction, rpc);
				mPresenter.startPresentServiceTask();
			}else{
				
			}
	}
	
	private void getHouseFenju(){
		if (mSelectorInfo.containsKey("police")){
			mPoliceTextView.setText(mSelectorInfo.get("police").getHouseOrginText());
		}
		
		if (!mSelectorInfo.containsKey("fenju")){
			showLoadingView();
			HouseSelectorModel direction = new HouseSelectorModel();
			direction.setHouseOrginText((String) mFenjuTextView.getText());
			mSelectorInfo.put("fenju", direction);
			String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetPoliceStationList";
			SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mFenjuAction));
			mPresenter.readyPresentServiceParams(getApplicationContext(), url, mFenjuAction, rpc);
			mPresenter.startPresentServiceTask();
		}else{
			showAlertDialog(mFenjuTextView, "fenju", mSelectorInfo.get("fenju").getHouseAllContent());
		}
	}
	
	private void getHousePolice(){
		showLoadingView();
			HouseSelectorModel direction = new HouseSelectorModel();
			direction.setHouseOrginText((String) mPoliceTextView.getText());
			mSelectorInfo.put("police", direction);
			String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetLocalPoliceStationList";
			SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mPoliceAction));
			if (mSelectorInfo.get("fenju").getHouseSelectId() != null && !mSelectorInfo.get("fenju").getHouseSelectId().equals("")){
				rpc.addProperty("parentstationId", mSelectorInfo.get("fenju").getHouseSelectId());
				mPresenter.readyPresentServiceParams(getApplicationContext(), url, mPoliceAction, rpc);
				mPresenter.startPresentServiceTask();
			}
	}
	
	private void showAlertDialog(final TextView text, final String tag, final String[] items) {  
			  AlertDialog.Builder builder =new AlertDialog.Builder(AddHouseInfoActivity.this);
			  
			  builder.setItems(items, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mSelectorInfo.get(tag).setHouseSelectValue(items[which]);
					if (mSelectorInfo.get(tag).getHouseAllId() != null && mSelectorInfo.get(tag).getHouseAllId().length > 0){
						mSelectorInfo.get(tag).setHouseSelectId(mSelectorInfo.get(tag).getHouseAllId()[which]);
					}
					
					text.setText(mSelectorInfo.get(tag).getHouseOrginText() +"   "+items[which]);
				}
			});
			builder.show();
	}
		
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 100){
				dismissLoadingView();
				mSelectorInfo.get("property").setHouseAllContent(JsonObjectParse.parseHouseProperty((String)msg.obj).get(1));
				mSelectorInfo.get("property").setHouseAllId(JsonObjectParse.parseHouseProperty((String)msg.obj).get(0));
				showAlertDialog(mPropertryTextView,"property" , JsonObjectParse.parseHouseProperty((String)msg.obj).get(1));
			}else if (msg.what == 101){
				dismissLoadingView();
				mSelectorInfo.get("type").setHouseAllContent(JsonObjectParse.parseHouseProperty((String)msg.obj).get(1));
				mSelectorInfo.get("type").setHouseAllId(JsonObjectParse.parseHouseProperty((String)msg.obj).get(0));
				showAlertDialog(mTypeTextView, "type", JsonObjectParse.parseHouseProperty((String)msg.obj).get(1));
			}else if (msg.what == 102){
				dismissLoadingView();
				mSelectorInfo.get("direction").setHouseAllContent(JsonObjectParse.parseHouseProperty((String)msg.obj).get(1));
				mSelectorInfo.get("direction").setHouseAllId(JsonObjectParse.parseHouseProperty((String)msg.obj).get(0));
				showAlertDialog(mDirectionTextView, "direction", JsonObjectParse.parseHouseProperty((String)msg.obj).get(1));
			}else if (msg.what == 103){
				dismissLoadingView();
				mSelectorInfo.get("structure").setHouseAllContent(JsonObjectParse.parseHouseProperty((String)msg.obj).get(1));
				mSelectorInfo.get("structure").setHouseAllId(JsonObjectParse.parseHouseProperty((String)msg.obj).get(0));
				showAlertDialog(mStructureTextView, "structure", JsonObjectParse.parseHouseProperty((String)msg.obj).get(1));
			}else if (msg.what == 104){
				dismissLoadingView();
				mSelectorInfo.get("district").setHouseAllContent(JsonObjectParse.parseHouseDistrict((String)msg.obj).get(1));
				mSelectorInfo.get("district").setHouseAllId(JsonObjectParse.parseHouseDistrict((String)msg.obj).get(0));
				showAlertDialog(mDistrictTextView, "district", JsonObjectParse.parseHouseDistrict((String)msg.obj).get(1));
			}else if (msg.what == 105){
				dismissLoadingView();
				mSelectorInfo.get("street").setHouseAllContent(JsonObjectParse.parseHouseStreet((String)msg.obj).get(1));
				mSelectorInfo.get("street").setHouseAllId(JsonObjectParse.parseHouseStreet((String)msg.obj).get(0));
				showAlertDialog(mStreetTextView, "street", JsonObjectParse.parseHouseStreet((String)msg.obj).get(1));
			}else if (msg.what == 106){
				dismissLoadingView();
				mSelectorInfo.get("road").setHouseAllContent(JsonObjectParse.parseHouseRoad((String)msg.obj).get(1));
				mSelectorInfo.get("road").setHouseAllId(JsonObjectParse.parseHouseRoad((String)msg.obj).get(0));
				showAlertDialog(mRoadTextView, "road", JsonObjectParse.parseHouseRoad((String)msg.obj).get(1));
			}else if (msg.what == 107){
				dismissLoadingView();
				mSelectorInfo.get("fenju").setHouseAllContent(JsonObjectParse.parseHouseFenju((String)msg.obj).get(1));
				mSelectorInfo.get("fenju").setHouseAllId(JsonObjectParse.parseHouseFenju((String)msg.obj).get(0));
				showAlertDialog(mFenjuTextView, "fenju", JsonObjectParse.parseHouseFenju((String)msg.obj).get(1));
			}else if (msg.what == 108){
				dismissLoadingView();
				mSelectorInfo.get("police").setHouseAllContent(JsonObjectParse.parseHouseFenju((String)msg.obj).get(1));
				mSelectorInfo.get("police").setHouseAllId(JsonObjectParse.parseHouseFenju((String)msg.obj).get(0));
				showAlertDialog(mPoliceTextView, "police", JsonObjectParse.parseHouseFenju((String)msg.obj).get(1));
			}else if (msg.what == 109){
				dismissLoadingView();
				mSelectorInfo.get("renttype").setHouseAllContent(JsonObjectParse.parseHouseProperty((String)msg.obj).get(1));
				mSelectorInfo.get("renttype").setHouseAllId(JsonObjectParse.parseHouseProperty((String)msg.obj).get(0));
				showAlertDialog(mRentTypeTextView, "renttype", JsonObjectParse.parseHouseProperty((String)msg.obj).get(1));
			}else if (msg.what == 110){
				dismissLoadingView();
				mSelectorInfo.get("ownertype").setHouseAllContent(JsonObjectParse.parseHouseProperty((String)msg.obj).get(1));
				mSelectorInfo.get("ownertype").setHouseAllId(JsonObjectParse.parseHouseProperty((String)msg.obj).get(0));
				showAlertDialog(mOwnerTypeTextView, "ownertype", JsonObjectParse.parseHouseProperty((String)msg.obj).get(1));
				
			}else if (msg.what == 111){
				dismissLoadingView();
				mSelectorInfo.get("buildingstructure").setHouseAllContent(JsonObjectParse.parseHouseProperty((String)msg.obj).get(1));
				mSelectorInfo.get("buildingstructure").setHouseAllId(JsonObjectParse.parseHouseProperty((String)msg.obj).get(0));
				showAlertDialog(mBuildingStructureTextView, "buildingstructure", JsonObjectParse.parseHouseProperty((String)msg.obj).get(1));
			}else if (msg.what == 888){
				String value = (String)msg.obj;
				if (value != null && value.equals("true")){
					Log.e("mingguo", "add rent info success ");
					Toast.makeText(getApplicationContext(), "添加租房信息成功", Toast.LENGTH_SHORT).show();
					finish();
				}else{
					Toast.makeText(getApplicationContext(), "添加租房信息失败", Toast.LENGTH_SHORT).show();
				}
			}else if (msg.what == 112){
				String value = (String)msg.obj;
				if (value != null && value.equals("true")){
					mValidHouseId = true;
				}else{
					mValidHouseId = false;
					Toast.makeText(getApplicationContext(), "房产证编号输入有误", Toast.LENGTH_SHORT).show();
				}
			}
		}
		
	};
	
	private void showLoadingView(){
		
		if (mLoadingView != null) {
			mLoadingView.setVisibility(View.VISIBLE);
        	ImageView imageView = (ImageView) mLoadingView.findViewById(R.id.id_progressbar_img);
        	if (imageView != null) {
        		RotateAnimation rotate = (RotateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        		imageView.startAnimation(rotate);
        	}
		}
	}
	private void dismissLoadingView(){
		if (mLoadingView != null) {
			mLoadingView.setVisibility(View.INVISIBLE);
		}
	}

	 @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				if (mLoadingView != null && mLoadingView.getVisibility() == View.VISIBLE){
					mLoadingView.setVisibility(View.INVISIBLE);
					return false;
				}
			}
			return super.onKeyDown(keyCode, event);
		}

	@Override
	public void onStatusStart() {
		
		
	}

	@Override
	public void onStatusSuccess(String action, String templateInfo) {
		Log.i("mingguo", "on success  action "+action+"  msg  "+templateInfo);
		if (action != null && templateInfo != null){
			if (action.equals(mPropertyAction)){
				Message msg = mHandler.obtainMessage();
				msg.what = 100;
				msg.obj = templateInfo;
				msg.sendToTarget();
			}else if (action.equals(mTypeAction)){
				Message msg = mHandler.obtainMessage();
				msg.what = 101;
				msg.obj = templateInfo;
				msg.sendToTarget();
			}else if (action.equals(mDirectionAction)){
				Message msg = mHandler.obtainMessage();
				msg.what = 102;
				msg.obj = templateInfo;
				msg.sendToTarget();
			}else if (action.equals(mStuctureAction)){
				Message msg = mHandler.obtainMessage();
				msg.what = 103;
				msg.obj = templateInfo;
				msg.sendToTarget();
			}else if (action.equals(mDistrictAction)){
				Message msg = mHandler.obtainMessage();
				msg.what = 104;
				msg.obj = templateInfo;
				msg.sendToTarget();
			}else if (action.equals(mStreetAction)){
				Message msg = mHandler.obtainMessage();
				msg.what = 105;
				msg.obj = templateInfo;
				msg.sendToTarget();
			}else if (action.equals(mRoadAction)){
				Message msg = mHandler.obtainMessage();
				msg.what = 106;
				msg.obj = templateInfo;
				msg.sendToTarget();
			}else if (action.equals(mFenjuAction)){
				Message msg = mHandler.obtainMessage();
				msg.what = 107;
				msg.obj = templateInfo;
				msg.sendToTarget();
			}else if (action.equals(mPoliceAction)){
				Message msg = mHandler.obtainMessage();
				msg.what = 108;
				msg.obj = templateInfo;
				msg.sendToTarget();
			}else if (action.equals(mRentTypeAction)){
				Message msg = mHandler.obtainMessage();
				msg.what = 109;
				msg.obj = templateInfo;
				msg.sendToTarget();
			}else if (action.equals(mOwnerTypeAction)){
				Message msg = mHandler.obtainMessage();
				msg.what = 110;
				msg.obj = templateInfo;
				msg.sendToTarget();
			}else if (action.equals(mBuildingStuctureAction)){
				Message msg = mHandler.obtainMessage();
				msg.what = 111;
				msg.obj = templateInfo;
				msg.sendToTarget();
			}else if (action.equals(mAddHouseAction)){
				Message msg = mHandler.obtainMessage();
				msg.what = 888;
				msg.obj = templateInfo;
				msg.sendToTarget();
			}else if (action.equals(mValidHouseIDAction)){
				Message msg = mHandler.obtainMessage();
				msg.what = 112;
				msg.obj = templateInfo;
				msg.sendToTarget();
			}
		}
		
	}

}
