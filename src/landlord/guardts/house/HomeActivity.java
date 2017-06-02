package landlord.guardts.house;

import java.util.HashMap;

import org.kobjects.rss.RssReader;
import org.ksoap2.serialization.SoapObject;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import landlord.guardts.house.present.HoursePresenter;
import landlord.guardts.house.util.CommonUtil;
import landlord.guardts.house.util.JsonObjectParse;
import landlord.guardts.house.util.UniversalUtil;
import landlord.guardts.house.view.HouseFragment;
import landlord.guardts.house.view.MyFragment;

public class HomeActivity extends BaseActivity {

	private TextView mTitleBar;
	private HoursePresenter mPresenter;
	//private String mLoginAction = "http://tempuri.org/ValidateLogin";
	private String mUpdateAction="http://tempuri.org/CheckUpdate";
	private String mUserName, mPassword;
	private HouseFragment mHouseFrament;
	private MyFragment mMyFragment;
	//private String mIdCard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.home_layout); 
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		mTitleBar = (TextView)findViewById(R.id.id_titlebar);
		mTitleBar.setText(getResources().getString(R.string.home_tab_house));
		
		mUserName = getIntent().getStringExtra("user_name");
		mPassword = getIntent().getStringExtra("user_password");
		initView();
		checkVersionUpdate();
	}
	
	private void checkVersionUpdate(){
		String url = "http://byw2863630001.my3w.com/SystemUpgradeService.asmx?op=CheckUpdate";
		SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mUpdateAction));
		rpc.addProperty("appId", "2");
		mPresenter.readyPresentServiceParams(getApplicationContext(), url, mUpdateAction, rpc);
		mPresenter.startPresentServiceTask();
		//{"Result":"1","AppId":"2","Versioncode":"2","IsEnforced":"1","APKUrl":"UpgradeFolder//LeaserHouseApp.apk","IOSUrl":"","MSG":"Success"}
		//{"Result":"0","AppId":"2","MSG":"�Բ�������Ȩ���ô�Web����"}
		//result��1������0������   appid��1ӡ�£�2������
	}
	
	private void initView(){
		mPresenter = new HoursePresenter(getApplicationContext(), this);
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		if (mHouseFrament == null){
			mHouseFrament = new HouseFragment(mUserName);
			fragmentTransaction.add(R.id.id_home_content, mHouseFrament);
			fragmentTransaction.commitAllowingStateLoss();
		}else{
			fragmentTransaction.show(mHouseFrament);
			fragmentTransaction.commitAllowingStateLoss();
		}
		
		final LinearLayout houseLayout = (LinearLayout)findViewById(R.id.id_home_button_house);
		final LinearLayout myLayout = (LinearLayout)findViewById(R.id.id_home_button_my);
		final ImageView houseIcon = (ImageView)findViewById(R.id.id_home_button_house_icon);
		final ImageView myIcon = (ImageView)findViewById(R.id.id_home_button_my_icon);
		final TextView houseText = (TextView)findViewById(R.id.id_home_button_house_text);
		final TextView myText = (TextView)findViewById(R.id.id_home_button_my_text);
		houseLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mTitleBar.setText(getString(R.string.home_tab_house));
				
				houseIcon.setBackgroundResource(R.drawable.chuzu_icon);
				houseText.setTextColor(Color.parseColor("#0b6cfe"));
				myIcon.setBackgroundResource(R.drawable.my_icon_default);
				myText.setTextColor(Color.parseColor("#afaeae"));
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				hideAllFragments(fragmentTransaction);
				if (mHouseFrament == null){
					mHouseFrament = new HouseFragment(mUserName);
					fragmentTransaction.add(R.id.id_home_content, mHouseFrament);
					fragmentTransaction.commitAllowingStateLoss();
				}else{
					fragmentTransaction.show(mHouseFrament);
					fragmentTransaction.commitAllowingStateLoss();
				}
			}
		});
		
		myLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mTitleBar.setText(getString(R.string.home_tab_my));
				houseIcon.setBackgroundResource(R.drawable.chuzu_icon_default);
				houseText.setTextColor(Color.parseColor("#afaeae"));
				myIcon.setBackgroundResource(R.drawable.my_icon);
				myText.setTextColor(Color.parseColor("#0b6cfe"));
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				hideAllFragments(fragmentTransaction);
				if (mMyFragment == null){
					mMyFragment = new MyFragment(mUserName);
					fragmentTransaction.add(R.id.id_home_content, mMyFragment);
					fragmentTransaction.commitAllowingStateLoss();
				}else{
					fragmentTransaction.show(mMyFragment);
					fragmentTransaction.commitAllowingStateLoss();
				}
			}
		});
		
//		final EditText userName = (EditText)findViewById(R.id.id_login_username);
//		final EditText password = (EditText)findViewById(R.id.id_login_password);
//		Button login = (Button)findViewById(R.id.id_login_user_button);
//		login.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				mUserName = userName.getEditableText().toString();
//				mPassword = password.getEditableText().toString();
//				if (mUserName == null || mUserName.equals("")){
//					Toast.makeText(getApplicationContext(), "�û�������Ϊ��", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				if (mPassword == null || mPassword.equals("")){
//					Toast.makeText(getApplicationContext(), "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				showLoadingView();
//				loginUser();
//			}
//		});
//		
//		Button registerButton = (Button)findViewById(R.id.id_login_user_register);
//		Button modifyButton = (Button)findViewById(R.id.id_login_user_modify_password);
//		registerButton.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				startActivity(new Intent(HomeActivity.this, RegisterUserActivity.class));
//			}
//		});
//		modifyButton.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				startActivity(new Intent(HomeActivity.this, ModifyPasswordActivity.class));
//			}
//		});
	}
	
	
	
//	private void loginUser(){
//		String url = "http://qxw2332340157.my3w.com/services.asmx?op=ValidateLogin";
//		SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mLoginAction));
//		rpc.addProperty("username", mUserName);
//		rpc.addProperty("password", mPassword);
//		mPresenter.readyPresentServiceParams(getApplicationContext(), url, mLoginAction, rpc);
//		mPresenter.startPresentServiceTask();
//	}
	
	private void hideAllFragments(FragmentTransaction transaction) {
		if (mHouseFrament != null && !mHouseFrament.isHidden()) {
			transaction.hide(mHouseFrament);
		}
		if (mMyFragment != null && !mMyFragment.isHidden()) {
			transaction.hide(mMyFragment);
		}
	}
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 100){
				showCheckUpdateVersionDialog();
				
			}else if (msg.what == 101){
				
				Toast.makeText(HomeActivity.this, "", Toast.LENGTH_SHORT).show();
			}
			
		}
		
	};
	
	private void showCheckUpdateVersionDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this).setTitle(getString(R.string.check_app_version))//
		  
	     .setMessage(getString(R.string.check_version_click_download))
	  
	     .setPositiveButton(getString(R.string.button_ok),new DialogInterface.OnClickListener() {  
	         @Override  
	  
	         public void onClick(DialogInterface dialog, int which) {
	        	 startActivity(new Intent(HomeActivity.this, DownloadAppActivity.class));
	        	 finish();
	         }  
	  
	     });
		
        builder.setCancelable(false);
		builder.show();
	}
	
	 private long exitTime;
	 @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			if (keyCode == KeyEvent.KEYCODE_BACK) {
					if ((System.currentTimeMillis() - exitTime) > 2000) {
						Toast.makeText(getApplicationContext(), getString(R.string.press_again_to_exit), Toast.LENGTH_SHORT).show();
			            exitTime = System.currentTimeMillis();
			        } else {
			            finish();
			            System.exit(0);
			        }
					return false;
				
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
			if (action.equals(mUpdateAction)){
				HashMap<String, String> updateInfo = (HashMap<String, String>) JsonObjectParse.parseVersionUpdateInfo(templateInfo);
				if (updateInfo != null && updateInfo.size() > 0){
					//{"Result":"1","AppId":"2","Versioncode":"2","IsEnforced":"1","APKUrl":"UpgradeFolder//LeaserHouseApp.apk","IOSUrl":"","MSG":"Success"} 
					if (updateInfo.containsKey("Result")&&updateInfo.containsKey("IsEnforced")&&updateInfo.containsKey("APKUrl")){
						String result = updateInfo.get("Result");
						String enforce = updateInfo.get("IsEnforced");
						String versionCode = updateInfo.get("Versioncode");
						if ("1".equals(result)&&"1".equals(enforce)){
							int updateCode = Integer.parseInt(versionCode);
							int currentCode = UniversalUtil.getAppVersionCode(HomeActivity.this);
							Log.i("mingguo", "update  "+updateCode+"  current  "+currentCode);
							if (updateCode > currentCode){
								Message message = mHandler.obtainMessage();
								message.what = 100;
								message.obj = updateInfo.get("APKUrl");
								mHandler.sendMessage(message);
							}
						}
					}
				}
				
			}
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.e("mingguo", "on activity  result  request code "+requestCode+"  result code  "+resultCode);
//		if (requestCode == 100){
//			if (resultCode == Activity.RESULT_OK){
//				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//				mHouseFrament = null;
//				mHouseFrament = new HouseFragment(mUserName);
//				fragmentTransaction.add(R.id.id_home_content, mHouseFrament);
//				fragmentTransaction.commitAllowingStateLoss();
//			}
//		}
	}
	
	


}
