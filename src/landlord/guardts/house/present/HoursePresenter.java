package landlord.guardts.house.present;

import java.util.HashMap;

import org.ksoap2.serialization.SoapObject;

import android.content.Context;
import android.util.Log;
import landlord.guardts.house.model.DataModel;
import landlord.guardts.house.model.DataStatusInterface;

public class HoursePresenter {
	private Context mContext;
	private DataStatusInterface mDataInterface;
	private DataModel mDataModel;

	public HoursePresenter(Context context, DataStatusInterface statusInterface) {
		mContext = context;
		mDataInterface = statusInterface;
		mDataModel = new DataModel(this);
	}
	
	public void readyPresentServiceParams(Context ctx, String url, String action, SoapObject object){
		mDataModel.setAsyncTaskReady(ctx, url, action, object);
	}
	public void startPresentServiceTask(){
		mDataModel.startDataRequestTask();
		mDataInterface.onStatusStart();
	}
	public void readyPresentHttpServiceParams(Context ctx, String url, HashMap<String, String> data){
		mDataModel.setHttpTaskReady(ctx, url, data);
	}
	
	public void startPresentHttpServiceTask(){
		mDataModel.startHttpRequestTask();
		mDataInterface.onStatusStart();
	}
	
	public void notifyDataRequestSuccess(String action, String value){
		mDataInterface.onStatusSuccess(action, value);
	}
	
	public void notifyDataRequestError(String action, String error){
		mDataInterface.onStatusError(action, error);
	}
}
