package landlord.guardts.house;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import landlord.guardts.house.model.DataStatusInterface;
import landlord.guardts.house.present.HoursePresenter;
import landlord.guardts.house.view.LoadingDrawable;

public class WelcomeActivity extends Activity {
	private LinearLayout mLoading_data;
	private Bundle mDataBundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		
		setContentView(R.layout.welcome_main);
		
		final ProgressBar loading = (ProgressBar) findViewById(R.id.splash_loading);
		loading.setIndeterminateDrawable(new LoadingDrawable(getApplicationContext()));
		
    	mHandler.sendEmptyMessageDelayed(100, 3000);
		//loginUser();
	}
	
	private void loginUser(){
		HoursePresenter mPresenter = new HoursePresenter(getApplicationContext(), new DataStatusInterface() {
			
			@Override
			public void onStatusSuccess(String action, String templateInfo) {
				// TODO Auto-generated method stub
				Log.i("mingguo", "on success  "+action+"  info "+templateInfo);
			}
			
			@Override
			public void onStatusStart() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStatusError(String action, String error) {
				// TODO Auto-generated method stub
				Log.i("mingguo", "on error  "+action+"  info "+error);
			}
		});
		
	}
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 100:
				SharedPreferences sharedata = getApplicationContext().getSharedPreferences("user_info", 0);
				String username = sharedata.getString("user_name", "");
				String password = sharedata.getString("user_password", "");
				//if (username != null && !username.equals("") && password != null && !password.equals("")){
					Intent intent = new Intent(WelcomeActivity.this, LoginUserActivity.class);
					intent.putExtra("user_name", username);
					intent.putExtra("user_password", password);
					startActivity(intent);
//				}else{
//					Intent intent = new Intent(WelcomeActivity.this, RegisterUserActivity.class);
//					startActivity(intent);
//				}
				finish();
				break;

			default:
				break;
			}
			
		}
		
	};

	

	
}
