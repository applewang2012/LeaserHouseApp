package landlord.guardts.house;

import android.app.Activity;
import landlord.guardts.house.model.DataStatusInterface;

public class BaseActivity extends Activity implements DataStatusInterface{

	@Override
	public void onStatusSuccess(String action, String templateInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusError(String action, String error) {
		//Toast.makeText(getApplicationContext(), "�����쳣���������磡", Toast.LENGTH_SHORT).show();
	}
	
	

}
