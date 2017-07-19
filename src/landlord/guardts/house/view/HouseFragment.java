package landlord.guardts.house.view;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import landlord.guardts.house.AddHouseInfoActivity;
import landlord.guardts.house.AddRentAttributeActivity;
import landlord.guardts.house.R;
import landlord.guardts.house.model.DataStatusInterface;
import landlord.guardts.house.model.HouseInfoModel;
import landlord.guardts.house.present.HoursePresenter;
import landlord.guardts.house.util.CommonUtil;
import landlord.guardts.house.util.JsonObjectParse;
import landlord.guardts.house.widget.UniversalAdapter;
import landlord.guardts.house.widget.UniversalViewHolder;
//��Ӱ�ʱ���import android.support.v4.app.Fragment; 
public class HouseFragment extends Fragment implements DataStatusInterface, OnItemClickListener, OnItemLongClickListener{
	

	
	private Context mContext;
	private View mRootView;
	private ListView mlistView;
	private View mLoadingView;
	private UniversalAdapter mAdapter;
	private List<HouseInfoModel> mHouseInfoList = new ArrayList<>();
	private HoursePresenter mPresent;
	private LinearLayout mContentLayout;
	private TextView mNoContent;
	private String mUserName = null;
	private String mGetHouseInfoAction = "http://tempuri.org/GetHouseInfoByLoginName";
	private String mDeleteHouseInfoAction = "http://tempuri.org/DeleteHouseInfo";
	private int mDeleteIndex = 0;
	public HouseFragment(String name){
		mUserName = name;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = getActivity().getApplicationContext();
		mPresent = new HoursePresenter(mContext, HouseFragment.this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mRootView = inflater.inflate(R.layout.home_house_fragment, container, false);
		initView();
		return mRootView;
	}
	
	
	
	@Override
	public void onResume() {
		super.onResume();
		showLoadingView();
		mContentLayout.setVisibility(View.INVISIBLE);
		initData();
		mAdapter.notifyDataSetChanged();
	}

	private void initView(){
		mlistView = (ListView)mRootView.findViewById(R.id.id_fragment_house_listview);
		mContentLayout = (LinearLayout)mRootView.findViewById(R.id.id_frament_house_cotent);
		mNoContent = (TextView)mRootView.findViewById(R.id.id_frament_house_no_cotent);
		mLoadingView = (View)mRootView.findViewById(R.id.id_data_loading);
		mContentLayout.setVisibility(View.INVISIBLE);
		initAdapter();
		mlistView.setAdapter(mAdapter);
		mlistView.setOnItemClickListener(this);
		mlistView.setOnItemLongClickListener(this);
	}
	
//	private void showAlertDialog(final TextView text, final String tag, final String[] items) {  
//		  AlertDialog.Builder builder =new AlertDialog.Builder(AddHouseInfoActivity.this);
//		  
//		  builder.setItems(items, new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				mSelectorInfo.get(tag).setHouseSelectValue(items[which]);
//				if (mSelectorInfo.get(tag).getHouseAllId() != null && mSelectorInfo.get(tag).getHouseAllId().length > 0){
//					mSelectorInfo.get(tag).setHouseSelectId(mSelectorInfo.get(tag).getHouseAllId()[which]);
//				}
//				
//				text.setText(mSelectorInfo.get(tag).getHouseOrginText() +"   "+items[which]);
//			}
//		});
//		builder.show();
//}
	
	private void initData(){
		getHouseInfo();
	}
	
	private void initAdapter(){
		mAdapter = new UniversalAdapter<HouseInfoModel>(getActivity(), R.layout.house_fragment_list_item, mHouseInfoList) {

			@Override
			public void convert(UniversalViewHolder holder, HouseInfoModel info) {
				View holderView = holder.getConvertView();
				TextView addressTextView = (TextView)holderView.findViewById(R.id.id_house_address);
				TextView typeTextView = (TextView)holderView.findViewById(R.id.id_house_type);
				TextView directionTextView = (TextView)holderView.findViewById(R.id.id_house_direction);
				TextView floorTextView = (TextView)holderView.findViewById(R.id.id_house_floor);
				TextView statusTextView = (TextView)holderView.findViewById(R.id.id_house_status);
				addressTextView.setText(info.getHouseAddress());
				typeTextView.setText(info.getHouseType());
				directionTextView.setText(info.getHouseDirection());
				floorTextView.setText(info.getHouseCurrentFloor()+"/"+info.getHouseTotalFloor()+getString(R.string.house_floor));
				statusTextView.setText(info.getHouseStatus());
				if (info.getHouseAvailable()){
					statusTextView.setTextColor(Color.parseColor("#0b6cfe"));
				}else{
					
				}
			}
		};
	}
	
	private void showPublicAttributeDialog(final int position){
		new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.arribute_house))//���öԻ������  
		  
	     .setMessage(getString(R.string.arribute_house_whether))//������ʾ������  
	  
	     .setPositiveButton(getString(R.string.button_ok),new DialogInterface.OnClickListener() {//���ȷ����ť  
	         @Override  
	  
	         public void onClick(DialogInterface dialog, int which) {//ȷ����ť����Ӧ�¼�  
	        	 Intent intent = new Intent(mContext, AddRentAttributeActivity.class);
	        	 intent.putExtra("house_id", mHouseInfoList.get(position).getHouseId());
	        	 intent.putExtra("user_name", mUserName);
	        	 intent.putExtra("owner_name", mHouseInfoList.get(position).getHouseOwnerName());
	        	 intent.putExtra("owner_id", mHouseInfoList.get(position).getHouseOwnerIdcard());
	        	 startActivityForResult(intent, 100);
	        	 //startActivity(intent);
	         }  
	  
	     }).setNegativeButton(getString(R.string.button_cancel),new DialogInterface.OnClickListener() {//��ӷ��ذ�ť  
	  
	         @Override  
	  
	         public void onClick(DialogInterface dialog, int which) {//��Ӧ�¼�  
	  
	             // TODO Auto-generated method stub  
	  
	             Log.i("alertdialog"," �뱣�����ݣ�");  
	  
	         }  
	  
	     }).show(); 
	}
	
	private void showDeleteHouseInfoDialog(final int position){
		new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.delete_house))//���öԻ������  
		  
	     .setMessage(getString(R.string.arribute_house_whether))//������ʾ������  
	  
	     .setPositiveButton(getString(R.string.button_ok),new DialogInterface.OnClickListener() {//���ȷ����ť  
	         @Override  
	  
	         public void onClick(DialogInterface dialog, int which) {//ȷ����ť����Ӧ�¼�  
	        	 showLoadingView();
	        	 deleteHouseInfo(mHouseInfoList.get(position).getHouseId());
	         }  
	  
	     }).setNegativeButton(getString(R.string.button_cancel),new DialogInterface.OnClickListener() {//��ӷ��ذ�ť  
	         @Override  
	         public void onClick(DialogInterface dialog, int which) {//��Ӧ�¼�  
	             Log.i("alertdialog"," �뱣�����ݣ�");  
	         }  
	     }).show();//�ڰ�����Ӧ�¼�����ʾ�˶Ի���  
	}
	
	private void getHouseInfo(){
		String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetHouseInfoByLoginName";
		SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mGetHouseInfoAction));
		rpc.addProperty("loginName", mUserName);
		//rpc.addProperty("idcard", "12010519780419061X");
		mPresent.readyPresentServiceParams(mContext, url, mGetHouseInfoAction, rpc);
		mPresent.startPresentServiceTask();
	}
	
	private void deleteHouseInfo(String houseNo){
		String url = "http://qxw2332340157.my3w.com/services.asmx?op=DeleteHouseInfo";
		SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mDeleteHouseInfoAction));
		rpc.addProperty("houseNo", houseNo);
		mPresent.readyPresentServiceParams(mContext, url, mDeleteHouseInfoAction, rpc);
		mPresent.startPresentServiceTask();
	}
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 100){
				getAdapterListData(JsonObjectParse.parseUserHouseInfo((String)msg.obj));
				dismissLoadingView();
				if (mHouseInfoList.size() == 0){
					mContentLayout.setVisibility(View.GONE);
					mNoContent.setVisibility(View.VISIBLE);
				}else{
					mContentLayout.setVisibility(View.VISIBLE);
					mNoContent.setVisibility(View.INVISIBLE);
					Log.w("housefragment", "house list  "+mHouseInfoList.size());
					mAdapter.notifyDataSetChanged();
				}
			}else if (msg.what == 101){
				dismissLoadingView();
				if ("true".equals((String)msg.obj)){
					mHouseInfoList.remove(mDeleteIndex);
					Log.w("housefragment", "delete house   "+mHouseInfoList.size());
					mAdapter.notifyDataSetChanged();
				}else{
					Toast.makeText(mContext, getString(R.string.delete_house_failed), Toast.LENGTH_SHORT).show();
				}
			}
		}
	};
	
	private void getAdapterListData(List<HouseInfoModel> list){
		if (list == null){
			return;
		}
		mHouseInfoList.clear();
		for (int index = 0; index < list.size(); index++){
			HouseInfoModel infoModel = new HouseInfoModel();
			infoModel.setHouseAddress(list.get(index).getHouseAddress());
			infoModel.setHouseDirection(list.get(index).getHouseDirection());
			infoModel.setHouseTotalFloor(list.get(index).getHouseTotalFloor());
			infoModel.setHouseCurrentFloor(list.get(index).getHouseCurrentFloor());
			infoModel.setHouseType(list.get(index).getHouseType());
			infoModel.setHouseStatus(list.get(index).getHouseStatus());
			infoModel.setHouseId(list.get(index).getHouseId());
			infoModel.setHouseAvailable(list.get(index).getHouseAvailable());
			infoModel.setHouseOwnerName(list.get(index).getHouseOwnerName());
			infoModel.setHouseOwnerIdcard(list.get(index).getHouseOwnerIdcard());
			mHouseInfoList.add(infoModel);
		}
	}

	private void showLoadingView(){
		if (mLoadingView != null) {
			mLoadingView.setVisibility(View.VISIBLE);
        	ImageView imageView = (ImageView) mLoadingView.findViewById(R.id.id_progressbar_img);
        	if (imageView != null) {
        		RotateAnimation rotate = (RotateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.anim_rotate);
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
	public void onStatusSuccess(String action, String templateInfo) {
		Log.v("mingguo", "on status success  action  "+action+"  info  "+templateInfo);
		if (action.equals(mGetHouseInfoAction)){
			Message msgMessage = mHandler.obtainMessage();
			msgMessage.what = 100;
			msgMessage.obj = templateInfo;
			msgMessage.sendToTarget();
		}else if (action.equals(mDeleteHouseInfoAction)){
			Message msgMessage = mHandler.obtainMessage();
			msgMessage.what = 101;
			msgMessage.obj = templateInfo;
			msgMessage.sendToTarget();
		}
		
	}

	@Override
	public void onStatusStart() {
		// TODO Auto-generated method stub
		Log.e("housefragment", "on start  ");
	}

	@Override
	public void onStatusError(String action, String error) {
		// TODO Auto-generated method stub
		Log.e("housefragment", "error   "+error);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.e("housefragment", "item click position    "+position+"  list  "+mHouseInfoList.get(position).getHouseAvailable());
		if (mHouseInfoList.get(position).getHouseAvailable()){
			Toast.makeText(mContext, getString(R.string.house_already_rented), Toast.LENGTH_SHORT).show();
		}else{
			showPublicAttributeDialog(position);
		}
		
		
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		Log.e("housefragment", "item long click position    "+position+"  list  "+mHouseInfoList.get(position).getHouseAvailable());
		if (mHouseInfoList.get(position).getHouseAvailable()){
			Toast.makeText(mContext, getString(R.string.house_already_rented_not_delete), Toast.LENGTH_SHORT).show();
		}else{
			
			showDeleteHouseInfoDialog(position);
			
		}
		return true;
	}
	
}
