package landlord.guardts.house;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import landlord.guardts.house.model.IdentifyModel;
import landlord.guardts.house.present.HoursePresenter;
import landlord.guardts.house.util.CommonUtil;
import landlord.guardts.house.util.JsonObjectParse;
import landlord.guardts.house.util.UniversalUtil;

public class AddRentAttributeActivity extends BaseActivity{

	private TextView mTitleBar;
	private View mLoadingView;
	private HoursePresenter mPresenter;
	private String mAddRentAction = "http://tempuri.org/AddRentRecord";
	private String mIdentifyUrl = "https://nid.sdtt.com.cn/AppRegSvr/thirdsysauthsvr/houseorder";
	private String mAppIDString = "0000004";
	private String mRandNum = null;
	//ret  1 desc SDT-HOUSE-3435333134303230313730333233313531373331343839
	private String mQueryStatusUrl = null;
//	private Map<String, String> mSelectedMap = new HashMap<>();
//	private Map<String, String> mOriginText = new HashMap<>();
//	private Map<String, String[]> mAllList = new HashMap<>();
	private Calendar cal = Calendar.getInstance();
	private SimpleDateFormat df;
	private TextView mStartTime, mEndTime; 
	private String mSetStartData, mSetEndData;
	private String mOriginStartContent, mOriginEndContent, mOriginNationalContent, mOriginProvinceContent;
	private String mHouseNo;
	private String mUsername;
	private String [] mOwnerType = new String[2];
	private String mOriginTypeText, mTypeIndex = null;
	private String mOwnerName;
	private String mOwnerIdcard;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.add_house_rent_attribute_info); 
		
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		mTitleBar = (TextView)findViewById(R.id.id_titlebar);
		mTitleBar.setText("����������Ϣ");
		
		initView();
		
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mHouseNo = getIntent().getStringExtra("house_id");
		mUsername = getIntent().getStringExtra("user_name");
		mOwnerName = getIntent().getStringExtra("owner_name");
		mOwnerIdcard = getIntent().getStringExtra("owner_id");
		mHouseId.setText(mHouseNo);
	}

	
	private void showAlertDialog(final TextView text,final String[] items) {  
		  AlertDialog.Builder builder =new AlertDialog.Builder(AddRentAttributeActivity.this);
		  builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mTypeIndex = which+"";
				text.setText(mOriginTypeText +"   "+items[which]);
			}
		});
		builder.show();
}

	private void getStartTime(){
		new DatePickerDialog(AddRentAttributeActivity.this , 
				startlistener , 
				cal.get(Calendar.YEAR ), 
				cal .get(Calendar.MONTH ), 
				cal .get(Calendar.DAY_OF_MONTH ) 
				).show(); 
	}
	
	private void getEndTime(){
		new DatePickerDialog(AddRentAttributeActivity.this , 
				endlistener , 
				cal.get(Calendar.YEAR ), 
				cal .get(Calendar.MONTH ), 
				cal .get(Calendar.DAY_OF_MONTH ) 
				).show(); 
	}
	
	private DatePickerDialog.OnDateSetListener startlistener = new DatePickerDialog.OnDateSetListener(){  //
		@Override 
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) { 
		cal .set(Calendar. YEAR , arg1); 
		cal .set(Calendar. MONTH , arg2); 
		cal .set(Calendar. DAY_OF_MONTH , arg3); 
		updateStartDate();
		} 
	};
	
	private DatePickerDialog.OnDateSetListener endlistener = new DatePickerDialog.OnDateSetListener(){  //
		@Override 
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) { 
		cal .set(Calendar. YEAR , arg1); 
		cal .set(Calendar. MONTH , arg2); 
		cal .set(Calendar. DAY_OF_MONTH , arg3); 
		updateEndDate();
		} 
	};
	private TextView mHouseId;
	private EditText mRentIDcard;
	private EditText mRentName;
	private EditText mRentPrice;
	private EditText mRentReadMe;
	private EditText mRentPhone;
	private View mQrcodeView;
	private TextView mTypeTextView;
	
	
	@SuppressLint("SimpleDateFormat")
	private void updateStartDate(){ 
		df = new SimpleDateFormat( "yyyy-MM-dd" ); 
		mSetStartData = df.format(cal.getTime());
		mStartTime.setText(mOriginStartContent + df.format(cal.getTime())); 
	}
	
	private void updateEndDate(){ 
		df = new SimpleDateFormat( "yyyy-MM-dd" ); 
		mSetEndData = df.format(cal.getTime());
		mEndTime.setText(mOriginEndContent + df.format(cal.getTime())); 
	}
	
	private void initView(){
		mPresenter = new HoursePresenter(getApplicationContext(), this);
		mLoadingView = (View)findViewById(R.id.id_data_loading);
		mLoadingView.setVisibility(View.INVISIBLE);
		mQrcodeView = (View)findViewById(R.id.id_qrcode_layout);
		mQrcodeView.setVisibility(View.INVISIBLE);
		mOwnerType[0] = "���ⷿ";
		mOwnerType[1] = "���ⷿ";
		FrameLayout typeFrameLayout = (FrameLayout)findViewById(R.id.id_rent_house_type);
		mTypeTextView = (TextView)findViewById(R.id.id_rent_house_type_text);
		mOriginTypeText = mTypeTextView.getText().toString();
		typeFrameLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showAlertDialog(mTypeTextView, mOwnerType);
			}
		});
		
		FrameLayout startTime = (FrameLayout)findViewById(R.id.id_rent_house_start_date);
		mStartTime = (TextView)findViewById(R.id.id_rent_house_start_date_text);
		mOriginStartContent = (String) mStartTime.getText();
		startTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getStartTime();
			}
		});
		
		FrameLayout endTime = (FrameLayout)findViewById(R.id.id_rent_house_end_date);
		mEndTime = (TextView)findViewById(R.id.id_rent_house_end_date_text);
		mOriginEndContent = (String) mEndTime.getText();
		endTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getEndTime();
			}
		});
		
		mHouseId = (TextView)findViewById(R.id.id_rent_house_number);
		mRentIDcard = (EditText)findViewById(R.id.id_rent_house_idcard);
		mRentName = (EditText)findViewById(R.id.id_rent_house_name);
		mRentPhone = (EditText)findViewById(R.id.id_rent_house_phone);
		mRentPrice = (EditText)findViewById(R.id.id_rent_house_price);
		mRentReadMe = (EditText)findViewById(R.id.id_rent_house_read_me);
		Button okButton = (Button)findViewById(R.id.id_add_rent_confirm);
		okButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (checkInputContent()){
					startAddRentInfo();
				}
			}
		});
	}
	
	private boolean checkInputContent(){
		
		if (mHouseId.getText().toString() == null || mHouseId.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "�������Ȩ���", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (mRentIDcard.getText().toString() == null || mRentIDcard.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "���������֤��Ϣ", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (mRentName.getText().toString() == null || mRentName.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "����������", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (mRentPhone.getText().toString() == null || mRentPhone.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "�������ֻ�����", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (mRentReadMe.getText().toString() == null || mRentReadMe.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "�����뱸ע��Ϣ", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (mRentPrice.getText().toString() == null || mRentPrice.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "���������", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		if (mSetStartData == null || mSetStartData.equals("")){
			Toast.makeText(getApplicationContext(), "�������ⷿ��ʼʱ��", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (mSetEndData == null || mSetEndData.equals("")){
			Toast.makeText(getApplicationContext(), "�������ⷿ����ʱ��", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		return true;
	}
	
	private void startAddRentInfo(){
			Log.w("mingguo", " mHouseId "+mHouseId.getText()+" mRentName "+mRentName.getText()+" mRentPhone "+mRentPhone.getText()+" mRentIDcard.getText() "+mRentIDcard.getText()+" mRentPrice "+mRentPrice.getText()+
					"mSetStartData "+mSetStartData+" mSetEndData "+mSetEndData+" mRentReadMe "+mRentReadMe.getText());
			showLoadingView();
			String url = "http://qxw2332340157.my3w.com/services.asmx?op=AddRentRecord";
			SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mAddRentAction));
			rpc.addProperty("RentNo", mHouseId.getText().toString());   
			rpc.addProperty("RRAContactName", mRentName.getText().toString());      
			rpc.addProperty("RRAContactTel", mRentPhone.getText().toString());  
			rpc.addProperty("RRAIDCard", mRentIDcard.getText().toString());  
			rpc.addProperty("RRentPrice", mRentPrice.getText().toString());     
			rpc.addProperty("RRAStartDate", mSetStartData);  
			rpc.addProperty("RRAEndDate", mSetEndData); 
			rpc.addProperty("RRADescription", mRentReadMe.getText().toString()); 
			rpc.addProperty("createdBy", mUsername);
			mPresenter.readyPresentServiceParams(getApplicationContext(), url, mAddRentAction, rpc);
			mPresenter.startPresentServiceTask();
	}
	
	private void startHttpService(){
		;
		try {
			JSONObject obj = new JSONObject(); 
			HashMap<String, String> hashMap = new HashMap<>();
			obj.put("AppID", "0000004");
			obj.put("FunID", "000");
			obj.put("OrderID", System.currentTimeMillis()+"");
			obj.put("HouseID", mHouseNo);
			obj.put("LessorID", mOwnerIdcard);
			obj.put("LessorName", mOwnerName);
			obj.put("LesseeID", mRentIDcard.getText().toString());
			obj.put("LesseeName", mRentName.getText().toString());
			obj.put("Rent", mRentPrice.getText().toString());
			obj.put("RentType", mTypeIndex);
			obj.put("StartTime",mSetStartData);
			obj.put("EndTime", mSetEndData);
			
			obj.put("AgentFlag", "0");
			obj.put("AgentID", "");
			obj.put("AgentName", "");
			
//			String data = "{'StartTime':'2017-02-01','HouseID':'test002',"
//					+ "'Rent':'1000','AgentID':'110101109892837271','EndTime':'2017-03-01',"
//					+ "'LesseeID':'110101198729838272','RentType':'1','LesseeName':'fangduoduo',"
//					+ "'LessorName':'guolili','AgentName':'fangdongdong','AgentFlag':'1','LessorID':'110101109892837271',"
//					+ "'AppID':'0000004','FunID':'000','OrderID':'02212121212'}";	
			
			hashMap.put("strData", obj.toString());
			mPresenter.readyPresentHttpServiceParams(getApplicationContext(), mIdentifyUrl, hashMap);
			mPresenter.startPresentHttpServiceTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
		
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 100){
				dismissLoadingView();
				String value = (String)msg.obj;
				if (value != null && value.equals("true")){
					//Toast.makeText(getApplicationContext(), "��ӳɹ�", Toast.LENGTH_SHORT).show();
					showLoadingView();
					startHttpService();
				}else{
					Toast.makeText(getApplicationContext(), "���������Ϣʧ��", Toast.LENGTH_SHORT).show();
				}
			}else if (msg.what == 101){
				dismissLoadingView();
				getIndentifyInfo((String)msg.obj);
			}else if (msg.what == 102){
				getQueryStatusInfo((String)msg.obj);
			}
		}
		
	};
	private void queryIdentifyStatus(String randNum){
		String url = "https://nid.sdtt.com.cn/AppRegSvr/thirdsysinforsvr/hauthretquery/";
		String random = "'strRandomNum':'"+randNum+"'";
		String appId = "'AppID':'"+mAppIDString+"',";
		mQueryStatusUrl = url+appId+random;
		mPresenter.readyPresentHttpServiceParams(getApplicationContext(), mQueryStatusUrl, null);
		mPresenter.startPresentHttpServiceTask();
	}
	private void getIndentifyInfo(String value){
		IdentifyModel info = (IdentifyModel) JsonObjectParse.parseIdentifyInfo(value);
		if (info == null){
			return;
		}
		String ret = info.getIdentifyStatus();
		if (ret != null){
			if (ret.equals("0")){
				Toast.makeText(getApplicationContext(), info.getIdentifyInfo(), Toast.LENGTH_SHORT).show();
			}else if (ret.equals("1")){
				String qrUrl = info.getIdentifyInfo();
				mRandNum = qrUrl;
				mQrcodeView.setVisibility(View.VISIBLE);
				ImageView qrImageView = (ImageView) mQrcodeView.findViewById(R.id.id_qrcode_view);
				Log.e("mingguo", "  ret == 1  "+qrImageView.getWidth()+"  height  "+qrImageView.getWidth());
				Bitmap qrBitmap = UniversalUtil.createQRImage(qrUrl, qrImageView, qrImageView.getWidth(), qrImageView.getHeight());
				qrImageView.setImageBitmap(qrBitmap);
				queryIdentifyStatus(mRandNum);
				
			}
		}
	}
	
	private void getQueryStatusInfo(String value){
		IdentifyModel info = (IdentifyModel) JsonObjectParse.parseIdentifyInfo(value);
		if (info == null){
			return;
		}
		String ret = info.getIdentifyStatus();
		if (ret != null){
			if (ret.equals("1")){
				mQrcodeView.setVisibility(View.INVISIBLE);
				Toast.makeText(getApplicationContext(), info.getIdentifyInfo(), Toast.LENGTH_SHORT).show();
				Intent intent= new Intent();
		        setResult(Activity.RESULT_OK, intent);
		        finish();
			}else if (ret.equals("0")){
				queryIdentifyStatus(mRandNum);
			}else {
				Toast.makeText(getApplicationContext(), info.getIdentifyInfo(), Toast.LENGTH_SHORT).show();
			}
		}
	}
	
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
				if (mQrcodeView != null && mQrcodeView.getVisibility() == View.VISIBLE){
					mQrcodeView.setVisibility(View.INVISIBLE);
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
			if (action.equals(mAddRentAction)){
				Message msg = mHandler.obtainMessage();
				msg.what = 100;
				msg.obj = templateInfo;
				msg.sendToTarget();
			}else if (action.equals(mIdentifyUrl)){
				Log.e("mingguo", "identify url "+mIdentifyUrl);
				Message msg = mHandler.obtainMessage();
				msg.what = 101;
				msg.obj = templateInfo;
				msg.sendToTarget();
			}else if (action.equals(mQueryStatusUrl)){
				Message msg = new Message();
				msg.what = 102;
				msg.obj = templateInfo;
				mHandler.sendMessageDelayed(msg, 4000);
			}
		}
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mHandler.removeMessages(102);
	}
	
	

}
