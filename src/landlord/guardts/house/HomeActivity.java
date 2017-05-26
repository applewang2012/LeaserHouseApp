package landlord.guardts.house;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import landlord.guardts.house.present.HoursePresenter;
import landlord.guardts.house.view.HouseFragment;
import landlord.guardts.house.view.MyFragment;

public class HomeActivity extends BaseActivity{

	private TextView mTitleBar;
	private HoursePresenter mPresenter;
	private String mLoginAction = "http://tempuri.org/ValidateLogin";
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
		mTitleBar.setText("出租屋");
		
		mUserName = getIntent().getStringExtra("user_name");
		mPassword = getIntent().getStringExtra("user_password");
		initView();
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
		
		houseLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mTitleBar.setText("出租屋");
				myLayout.setAlpha((float) 0.5);
				houseLayout.setAlpha((float) 1.0);
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
				mTitleBar.setText("我的");
				myLayout.setAlpha((float) 1.0);
				houseLayout.setAlpha((float) 0.5);
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
//					Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				if (mPassword == null || mPassword.equals("")){
//					Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
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
//			if (msg.what == 100){
//				dismissLoadingView();
//				Toast.makeText(HomeActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//				
//			}else if (msg.what == 101){
//				dismissLoadingView();
//				Toast.makeText(HomeActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
//			}
			
		}
		
	};
	
//	private void showLoadingView(){
//		
//		if (mLoadingView != null) {
//			mLoadingView.setVisibility(View.VISIBLE);
//        	ImageView imageView = (ImageView) mLoadingView.findViewById(R.id.id_progressbar_img);
//        	if (imageView != null) {
//        		RotateAnimation rotate = (RotateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
//        		imageView.startAnimation(rotate);
//        	}
//		}
//	}
//	private void dismissLoadingView(){
//		if (mLoadingView != null) {
//			mLoadingView.setVisibility(View.INVISIBLE);
//		}
//	}
	 private long exitTime;
	 @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			if (keyCode == KeyEvent.KEYCODE_BACK) {
					if ((System.currentTimeMillis() - exitTime) > 2000) {
						Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
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
			if (action.equals(mLoginAction)){
				if (templateInfo.equals("false")){
					mHandler.sendEmptyMessage(101);
				}else if (templateInfo.equals("true")){
					mHandler.sendEmptyMessage(100);
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
