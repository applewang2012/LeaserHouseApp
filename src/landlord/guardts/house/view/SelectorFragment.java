package landlord.guardts.house.view;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import landlord.guardts.house.R;
import landlord.guardts.house.adapter.ArrayWheelAdapter;
import landlord.guardts.house.util.CommonUtil;
import landlord.guardts.house.widget.OnWheelChangedListener;
import landlord.guardts.house.widget.WheelView;

public class SelectorFragment extends Fragment implements OnClickListener, OnWheelChangedListener{
	private String TAG = "VideoControlDialogDialogFragment";
	private Context mContext;
	private Button mBtnConfirm;
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.selector_fragment_layout, container, false);
		setUpViews(v);
		setUpListener();
		setUpData();
		return v;
	}
	
	private void setUpViews(View v) {
		mViewProvince = (WheelView) v.findViewById(R.id.id_province);
		mViewCity = (WheelView) v.findViewById(R.id.id_city);
		mViewDistrict = (WheelView) v.findViewById(R.id.id_district);
		mBtnConfirm = (Button) v.findViewById(R.id.btn_confirm);
	}
	
	private void setUpListener() {
    	// ���change�¼�
    	mViewProvince.addChangingListener(this);
    	// ���change�¼�
    	mViewCity.addChangingListener(this);
    	// ���change�¼�
    	mViewDistrict.addChangingListener(this);
    	// ���onclick�¼�
    	mBtnConfirm.setOnClickListener(this);
    }
	
	private void setUpData() {
		CommonUtil.initProvinceDatas(getActivity().getApplicationContext());
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(getActivity(), CommonUtil.mProvinceDatas));
		// ���ÿɼ���Ŀ����
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas();
		Toast.makeText(getActivity(), "��ǰѡ��:"+CommonUtil.mCurrentProviceName+","+CommonUtil.mCurrentCityName+","
				+CommonUtil.mCurrentDistrictName+","+CommonUtil.mCurrentZipCode, Toast.LENGTH_SHORT).show();
	}
	
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		CommonUtil.mCurrentProviceName = CommonUtil.mProvinceDatas[pCurrent];
		String[] cities = CommonUtil.mCitisDatasMap.get(CommonUtil.mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(getActivity(), cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}
	
	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		CommonUtil.mCurrentCityName = CommonUtil.mCitisDatasMap.get(CommonUtil.mCurrentProviceName)[pCurrent];
		String[] areas = CommonUtil.mDistrictDatasMap.get(CommonUtil.mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(getActivity(), areas));
		mViewDistrict.setCurrentItem(0);
	}


	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			updateCities();
		} else if (wheel == mViewCity) {
			updateAreas();
		} else if (wheel == mViewDistrict) {
			CommonUtil.mCurrentDistrictName = CommonUtil.mDistrictDatasMap.get(CommonUtil.mCurrentCityName)[newValue];
			CommonUtil.mCurrentZipCode = CommonUtil.mZipcodeDatasMap.get(CommonUtil.mCurrentDistrictName);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_confirm:
			showSelectedResult();
			break;
		default:
			break;
		}
	}
	
	private void showSelectedResult() {
		Toast.makeText(getActivity(), "��ǰѡ��:"+CommonUtil.mCurrentProviceName+","+CommonUtil.mCurrentCityName+","
				+CommonUtil.mCurrentDistrictName+","+CommonUtil.mCurrentZipCode, Toast.LENGTH_SHORT).show();
	}
	
}
	
