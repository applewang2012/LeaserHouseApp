package landlord.guardts.house;

import org.ksoap2.serialization.SoapObject;

import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import landlord.guardts.house.present.HoursePresenter;
import landlord.guardts.house.util.CommonUtil;

public class ModifyPasswordActivity extends BaseActivity{

	private TextView mTitleBar;
	private View mLoadingView;
	private HoursePresenter mPresenter;
	private String mModifyAction = "http://tempuri.org/ChangePassword";
	private String mUserName, mOldPassword, mNewPassword, mNewPasswordConfirm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.modify_user_password); 
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		mTitleBar = (TextView)findViewById(R.id.id_titlebar);
		mTitleBar.setText("修改密码");
		
		
		initView();
	}
	
	private void initView(){
		mPresenter = new HoursePresenter(getApplicationContext(), this);
		mLoadingView = (View)findViewById(R.id.id_data_loading);
		mLoadingView.setVisibility(View.INVISIBLE);
		final EditText userName = (EditText)findViewById(R.id.id_password_username);
		final EditText oldpassword = (EditText)findViewById(R.id.id_old_password);
		final EditText newpassword = (EditText)findViewById(R.id.id_new_password);
		final EditText newpasswordConfirm = (EditText)findViewById(R.id.id_new_password_confirml);
		Button mofifyButton = (Button)findViewById(R.id.id_modify_password_confirm);
		mofifyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mUserName = userName.getEditableText().toString();
				mOldPassword = oldpassword.getEditableText().toString();
				mNewPassword = newpassword.getEditableText().toString();
				mNewPasswordConfirm = newpasswordConfirm.getEditableText().toString();
				if (mUserName == null || mUserName.equals("")){
					Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if (mOldPassword == null || mOldPassword.equals("")){
					Toast.makeText(getApplicationContext(), "原密码不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if (mNewPassword == null || mNewPassword.equals("")){
					Toast.makeText(getApplicationContext(), "新密码不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if (mNewPasswordConfirm == null || mNewPasswordConfirm.equals("")){
					Toast.makeText(getApplicationContext(), "确认密码不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if (!mNewPassword.equals(mNewPasswordConfirm)){
					Toast.makeText(getApplicationContext(), "两次输入新密码不一致", Toast.LENGTH_SHORT).show();
					return;
				}
				showLoadingView();
				modifyUserPassword();
			}
		});
		
	}
	
	private void modifyUserPassword(){
		String url = "http://qxw2332340157.my3w.com/services.asmx?op=ChangePassword";
		SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mModifyAction));
		rpc.addProperty("username", mUserName);
		rpc.addProperty("oldPassword", mOldPassword);
		rpc.addProperty("newPassword", mNewPassword);
		mPresenter.readyPresentServiceParams(getApplicationContext(), url, mModifyAction, rpc);
		mPresenter.startPresentServiceTask();
	}
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 100){
				dismissLoadingView();
				SharedPreferences sharedata = getApplicationContext().getSharedPreferences("user_info", 0);
				SharedPreferences.Editor editor = sharedata.edit();
			    editor.putString("user_password", mNewPassword);
			    editor.commit();
				Toast.makeText(ModifyPasswordActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
				finish();
			}else if (msg.what == 101){
				dismissLoadingView();
				Toast.makeText(ModifyPasswordActivity.this, "密码修改失败", Toast.LENGTH_SHORT).show();
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
			if (action.equals(mModifyAction)){
				if (templateInfo.equals("false")){
					mHandler.sendEmptyMessage(101);
				}else if (templateInfo.equals("true")){
					mHandler.sendEmptyMessage(100);
				}
			}
		}
		
	}

	@Override
	public void onStatusError(String action, String error) {
		
	}
	
	

}
