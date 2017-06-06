package landlord.guardts.house;

import java.io.File;
import java.util.HashMap;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import landlord.guardts.house.download.DownloadManager;
import landlord.guardts.house.download.DownloadManager.Request;
import landlord.guardts.house.model.DataStatusInterface;
import landlord.guardts.house.present.HoursePresenter;
import landlord.guardts.house.util.CommonUtil;
import landlord.guardts.house.util.JsonObjectParse;
import landlord.guardts.house.util.UniversalUtil;
import landlord.guardts.house.view.LoadingDrawable;

public class WelcomeActivity extends BaseActivity {
	private LinearLayout mLoading_data;
	private Bundle mDataBundle;
	private String mAdsAction="http://tempuri.org/GetAdvertisement";
	private HoursePresenter mPresenter;
	private DownloadManager mDownloadManager;
	private String mUsername;
	private String mPassword;
	private int mSuccessCount = 0;
	private ImageView mSplashBG;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		
		setContentView(R.layout.welcome_main);
		
		final ProgressBar loading = (ProgressBar) findViewById(R.id.splash_loading);
		loading.setIndeterminateDrawable(new LoadingDrawable(getApplicationContext()));
		
		mSplashBG = (ImageView)findViewById(R.id.id_splash_background);
		
		SharedPreferences sharedata = getApplicationContext().getSharedPreferences("user_info", 0);
		mUsername = sharedata.getString("user_name", "");
		mPassword = sharedata.getString("user_password", "");
		
    	
    	checkAdvertisment();
	}
	
	private void checkAdvertisment(){
		mPresenter = new HoursePresenter(getApplicationContext(), this);
		String url = "http://byw2863630001.my3w.com/AdvertisementService.asmx?op=GetAdvertisement";
		SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mAdsAction));
		rpc.addProperty("AppId", "2");
		rpc.addProperty("userId", mUsername);
		rpc.addProperty("phone", "2");
		rpc.addProperty("regionId", "2");
		rpc.addProperty("r1", "2");
		rpc.addProperty("r2", "2");
		rpc.addProperty("r3", "2");
		rpc.addProperty("r4", "2");
		mPresenter.readyPresentServiceParams(getApplicationContext(), url, mAdsAction, rpc);
		mPresenter.startPresentServiceTask();
		//{"Result":"1","AppId":"2","UserId":"apple","Phone":"2","Region":"2","IsEnforced":"1","Type":"0","SubType":"0","VideoUrl":"","VideoTargetUrl":"http://www.baidu.com","ImageUrl1":"Images/1.png","ImageUrl2":"Images/2.png","ImageUrl3":"Images/3.png","ImageTargetUrl1":"http://www.guardts.com","ImageTargetUrl2":"http://www.sina.com","ImageTargetUrl3":"http://www.163.com"}
	}
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 100:
				
				//if (username != null && !username.equals("") && password != null && !password.equals("")){
					Intent intent = new Intent(WelcomeActivity.this, LoginUserActivity.class);
					intent.putExtra("user_name", mUsername);
					intent.putExtra("user_password", mPassword);
					startActivity(intent);
//				}else{
//					Intent intent = new Intent(WelcomeActivity.this, RegisterUserActivity.class);
//					startActivity(intent);
//				}
				finish();
				break;
			case 101:
				
				Request request = new Request(Uri.parse((String)msg.obj));
				if (mDownloadManager == null) {
					mDownloadManager = new DownloadManager(
							getApplicationContext().getContentResolver(), getApplicationContext().getPackageName());
				}
				request.setShowRunningNotification(false);
				
				request.setVisibleInDownloadsUi(false);
				long id = mDownloadManager.enqueue(request);
				break;
			case 201:
				
			case 202:
				
			case 203:
				mSuccessCount ++;
				if (mSuccessCount == 3){
					String path = UniversalUtil.getDownloadPath()+"/1.png";
					mSplashBG.setImageBitmap(UniversalUtil.getLoacalBitmap(path));
					
				}
				break;
			default:
				break;
			}
			
		}
		
	};
	@Override
	public void onStatusSuccess(String action, String templateInfo) {
		// TODO Auto-generated method stub
		super.onStatusSuccess(action, templateInfo);
		Log.i("mingguo", "action  "+action+"  info  "+templateInfo);
		if (action != null && templateInfo != null){
			if (action.equals(mAdsAction)){
				HashMap<String, String> adsInfo = (HashMap<String, String>) JsonObjectParse.parseAdvertismentInfo(templateInfo);
				String result = adsInfo.get("Result");
				if (result != null && result.equals("1")){
					String type = adsInfo.get("Type");
					if (type != null && type.equals("0")){
						String image1 = adsInfo.get("ImageUrl1");
						String image2 = adsInfo.get("ImageUrl2");
						String image3 = adsInfo.get("ImageUrl3");
						if (UniversalUtil.checkDownloadFileExist(image1)){
							mHandler.sendEmptyMessage(201);
						}else{
							Message message1 = new Message();
							message1.what = 101;
							message1.obj = CommonUtil.ADS_PREFIX+adsInfo.get("ImageUrl1");
							mHandler.sendMessage(message1);
						}
						if (UniversalUtil.checkDownloadFileExist(image2)){
							mHandler.sendEmptyMessage(202);
						}else{
							Message message2 = new Message();
							message2.what = 101;
							message2.obj = CommonUtil.ADS_PREFIX+adsInfo.get("ImageUrl2");
							mHandler.sendMessageDelayed(message2, 400);
						}
						if (UniversalUtil.checkDownloadFileExist(image3)){
							mHandler.sendEmptyMessage(203);
						}else{
							Message message3 = new Message();
							message3.what = 101;
							message3.obj = CommonUtil.ADS_PREFIX+adsInfo.get("ImageUrl3");
							mHandler.sendMessageDelayed(message3, 800);
						}
						
					}else if (type != null && type.equals("1")){
						
					}
					
				}
				mHandler.sendEmptyMessageDelayed(100, 3000);
			}
		}
	}
	
	

	@Override
	public void onStatusStart() {
		// TODO Auto-generated method stub
		super.onStatusStart();
	}

	
	
	
}
