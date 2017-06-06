package landlord.guardts.house;

import org.ksoap2.serialization.SoapObject;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import landlord.guardts.house.present.HoursePresenter;
import landlord.guardts.house.util.CommonUtil;

public class RegisterUserActivity extends BaseActivity{

	private TextView mTitleBar;
	private View mLoadingView;
	private HoursePresenter mPresenter;
	private String mValidAction = "http://tempuri.org/ValidateLoginName";
	private String mRegisterAction = "http://tempuri.org/AddUserInfo";
	private String mUserName, mPassword, mRealName, mIdCard, mPhone, mNickName,mAddress, mPosition, mEmail;
	private boolean mUsernameValid = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.register_username); 
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		mTitleBar = (TextView)findViewById(R.id.id_titlebar);
		mTitleBar.setText(getString(R.string.register_user));
		
		initView();
		
	}
	
	private void initView(){
		mPresenter = new HoursePresenter(getApplicationContext(), this);
		mLoadingView = (View)findViewById(R.id.id_data_loading);
		mLoadingView.setVisibility(View.INVISIBLE);
		final EditText userName = (EditText)findViewById(R.id.id_register_username);
		userName.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!hasFocus){
					mUserName = userName.getEditableText().toString();
					if (mUserName != null && mUserName.length() > 0){
						checkUserNameValid(mUserName);
					}
				}
			}
		});
		final EditText password = (EditText)findViewById(R.id.id_register_password);
		
		final EditText realName = (EditText)findViewById(R.id.id_register_realname);
		final EditText idCard = (EditText)findViewById(R.id.id_register_idcard);
		final EditText phone = (EditText)findViewById(R.id.id_register_phone);
		
		final EditText email = (EditText)findViewById(R.id.id_register_email);
		final EditText position = (EditText)findViewById(R.id.id_register_position);
		final EditText address = (EditText)findViewById(R.id.id_register_address);
		final EditText nickName = (EditText)findViewById(R.id.id_register_nickname);
		
		Button registerButton = (Button)findViewById(R.id.id_register_button);
		registerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mUserName = userName.getEditableText().toString();
				mPassword = password.getEditableText().toString();
				
				mRealName = realName.getEditableText().toString();
				mIdCard = idCard.getEditableText().toString();
				mPhone = phone.getEditableText().toString();
				
				mNickName = nickName.getEditableText().toString();
				mAddress = address.getEditableText().toString();
				mPosition = position.getEditableText().toString();
				mEmail = email.getEditableText().toString();
				Log.i("mingguo", "user name  "+mUserName);
				if (mUserName == null || mUserName.equals("")){
					Toast.makeText(RegisterUserActivity.this, getString(R.string.user_name_not_null), Toast.LENGTH_SHORT).show();
					return;
				}
				if (mPassword == null || mPassword.equals("")){
					Toast.makeText(RegisterUserActivity.this, getString(R.string.pwd_not_null), Toast.LENGTH_SHORT).show();
					return;
				}
				if (mRealName == null || mRealName.equals("")){
					Toast.makeText(RegisterUserActivity.this, getString(R.string.surface_name_not_null), Toast.LENGTH_SHORT).show();
					return;
				}
				if (mIdCard == null || mIdCard.equals("")){
					Toast.makeText(RegisterUserActivity.this, getString(R.string.id_card_not_null), Toast.LENGTH_SHORT).show();
					return;
				}
				if (mPhone == null || mPhone.equals("")){
					Toast.makeText(RegisterUserActivity.this, getString(R.string.phone_not_null), Toast.LENGTH_SHORT).show();
					return;
				}
				if (mNickName == null || mNickName.equals("")){
					Toast.makeText(RegisterUserActivity.this, getString(R.string.nickname_not_null), Toast.LENGTH_SHORT).show();
					return;
				}
				if (!mUsernameValid){
					Toast.makeText(RegisterUserActivity.this, getString(R.string.username_register_again), Toast.LENGTH_SHORT).show();
					return;
				}
				showLoadingView();
				registerUserName();
			}
		});
	}
	
	private void checkUserNameValid(String username){
		String url = "http://qxw2332340157.my3w.com/services.asmx?op=ValidateLoginName";
		SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mValidAction));
		rpc.addProperty("loginName", username); 
		mPresenter.readyPresentServiceParams(getApplicationContext(), url, mValidAction, rpc);
		mPresenter.startPresentServiceTask();
	}
	
	private void registerUserName(){
		String url = "http://qxw2332340157.my3w.com/services.asmx?op=AddUserInfo";
		SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mRegisterAction));
		rpc.addProperty("loginName", mUserName);
		rpc.addProperty("password", mPassword);
		rpc.addProperty("userType", "1");
		rpc.addProperty("realName", mRealName);
		rpc.addProperty("title", mPosition);
		rpc.addProperty("sex", "��");
		rpc.addProperty("phone", mPhone);
		rpc.addProperty("fax", "��");
		rpc.addProperty("email", mEmail);
		rpc.addProperty("idcard", mIdCard);
		rpc.addProperty("nickName", mNickName);
		rpc.addProperty("address", mAddress);
		rpc.addProperty("status", "0"); //0������1������
		mPresenter.readyPresentServiceParams(getApplicationContext(), url, mRegisterAction, rpc);
		mPresenter.startPresentServiceTask();
	}
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 100){
				Toast.makeText(RegisterUserActivity.this, getString(R.string.username_register_again), Toast.LENGTH_SHORT).show();
			}else if (msg.what == 101){
				dismissLoadingView();
				SharedPreferences sharedata = getApplicationContext().getSharedPreferences("user_info", 0);
				SharedPreferences.Editor editor = sharedata.edit();
			    editor.putString("user_name", mUserName);
			    editor.putString("user_password", mPassword);
			    editor.commit();
				Toast.makeText(RegisterUserActivity.this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(RegisterUserActivity.this, HomeActivity.class);
				intent.putExtra("user_name", mUserName);
				intent.putExtra("user_password", mPassword);
				startActivity(intent);
				finish();
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
			if (action.equals(mValidAction)){
				Log.i("mingguo", "on success  action valid ");
				if (templateInfo.equals("false")){
					mHandler.sendEmptyMessage(100);
					mUsernameValid = false;
				}else{
					mUsernameValid = true;
				}
			}else if (action.equals(mRegisterAction)){
				if (templateInfo.equals("true")){
					mHandler.sendEmptyMessage(101);
				}
			}
		}
		
	}

	

}
