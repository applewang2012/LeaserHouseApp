package landlord.guardts.house;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import landlord.guardts.house.model.DataStatusInterface;

public class BaseActivity extends Activity implements DataStatusInterface{

	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Toast.makeText(getApplicationContext(), "网络异常，请检查网络！", Toast.LENGTH_SHORT).show();
		}
		
	};
	@Override
	public void onStatusSuccess(String action, String templateInfo) {
		// TODO Auto-generated method stub
		Log.i("mingguo", "on success  action "+action+"  msg  "+templateInfo);
	}

	@Override
	public void onStatusStart() {
		// TODO Auto-generated method stub
		Log.i("mingguo", "on status   start ");
	}

	@Override
	public void onStatusError(String action, String error) {
		Log.e("mingguo", "on   error action  "+action+"  error info  "+error);
		mHandler.sendEmptyMessage(100);
	}
	
	

}
