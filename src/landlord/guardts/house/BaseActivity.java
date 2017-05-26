package landlord.guardts.house;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import landlord.guardts.house.model.DataStatusInterface;
import landlord.guardts.house.present.HoursePresenter;
import landlord.guardts.house.util.CommonUtil;
import landlord.guardts.house.view.SelectorFragment;

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
		//Toast.makeText(getApplicationContext(), "ÍøÂçÒì³££¬Çë¼ì²éÍøÂç£¡", Toast.LENGTH_SHORT).show();
	}
	
	

}
