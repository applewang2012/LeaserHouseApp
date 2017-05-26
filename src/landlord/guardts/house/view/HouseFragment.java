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
import landlord.guardts.house.AddRentAttributeActivity;
import landlord.guardts.house.R;
import landlord.guardts.house.model.DataStatusInterface;
import landlord.guardts.house.model.HouseInfoModel;
import landlord.guardts.house.present.HoursePresenter;
import landlord.guardts.house.util.CommonUtil;
import landlord.guardts.house.util.JsonObjectParse;
import landlord.guardts.house.widget.UniversalAdapter;
import landlord.guardts.house.widget.UniversalViewHolder;
//添加包时添加import android.support.v4.app.Fragment; 
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
				floorTextView.setText(info.getHouseCurrentFloor()+"/"+info.getHouseTotalFloor()+" 层");
				statusTextView.setText(info.getHouseStatus());
				if (info.getHouseAvailable()){
					statusTextView.setTextColor(Color.parseColor("#009966"));
				}else{
					
				}
			}
		};
	}
	
	private void showPublicAttributeDialog(final int position){
		new AlertDialog.Builder(getActivity()).setTitle("租赁房屋")//设置对话框标题  
		  
	     .setMessage("您确定要将该房屋进行租赁吗？")//设置显示的内容  
	  
	     .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮  
	         @Override  
	  
	         public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  
	        	 Intent intent = new Intent(mContext, AddRentAttributeActivity.class);
	        	 intent.putExtra("house_id", mHouseInfoList.get(position).getHouseId());
	        	 intent.putExtra("user_name", mUserName);
	        	 intent.putExtra("owner_name", mHouseInfoList.get(position).getHouseOwnerName());
	        	 intent.putExtra("owner_id", mHouseInfoList.get(position).getHouseOwnerIdcard());
	        	 startActivityForResult(intent, 100);
	        	 //startActivity(intent);
	         }  
	  
	     }).setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加返回按钮  
	  
	         @Override  
	  
	         public void onClick(DialogInterface dialog, int which) {//响应事件  
	  
	             // TODO Auto-generated method stub  
	  
	             Log.i("alertdialog"," 请保存数据！");  
	  
	         }  
	  
	     }).show();//在按键响应事件中显示此对话框  
	}
	
	private void showDeleteHouseInfoDialog(final int position){
		new AlertDialog.Builder(getActivity()).setTitle("删除房屋")//设置对话框标题  
		  
	     .setMessage("您确定要删除该房屋信息吗？")//设置显示的内容  
	  
	     .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮  
	         @Override  
	  
	         public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件  
	        	 showLoadingView();
	        	 deleteHouseInfo(mHouseInfoList.get(position).getHouseId());
	         }  
	  
	     }).setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加返回按钮  
	         @Override  
	         public void onClick(DialogInterface dialog, int which) {//响应事件  
	             Log.i("alertdialog"," 请保存数据！");  
	         }  
	     }).show();//在按键响应事件中显示此对话框  
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
					Toast.makeText(mContext, "删除房屋失败", Toast.LENGTH_SHORT).show();
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
			Toast.makeText(mContext, "该房屋已出租", Toast.LENGTH_SHORT).show();
		}else{
			showPublicAttributeDialog(position);
		}
		
		
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		Log.e("housefragment", "item long click position    "+position+"  list  "+mHouseInfoList.get(position).getHouseAvailable());
		if (mHouseInfoList.get(position).getHouseAvailable()){
			Toast.makeText(mContext, "该房屋已出租, 无法删除", Toast.LENGTH_SHORT).show();
		}else{
			
			showDeleteHouseInfoDialog(position);
			
		}
		return true;
	}
	
}
