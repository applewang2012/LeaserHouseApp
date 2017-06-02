package landlord.guardts.house.view;

import org.ksoap2.serialization.SoapObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import landlord.guardts.house.AddHouseInfoActivity;
import landlord.guardts.house.LoginUserActivity;
import landlord.guardts.house.ModifyPasswordActivity;
import landlord.guardts.house.R;
import landlord.guardts.house.model.DataStatusInterface;
import landlord.guardts.house.model.UserInfoModel;
import landlord.guardts.house.present.HoursePresenter;
import landlord.guardts.house.util.CommonUtil;
import landlord.guardts.house.util.JsonObjectParse;
//��Ӱ�ʱ���import android.support.v4.app.Fragment; 
public class MyFragment extends Fragment implements DataStatusInterface{
	

	
	private Context mContext;
	private View mRootView;
	private FrameLayout mUserContainer;
	private TextView mUserNickname;
	private TextView mUserId;
	private View mLoadingView;
	private TextView mUserAddress;
	private HoursePresenter mPresent;
	private FrameLayout mPublishHouse;
	private FrameLayout mDeleteHouse;
	private FrameLayout mPassword;
	private FrameLayout mLogout;
	private String mUsername;

	public MyFragment(String user){
		mUsername = user;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = getActivity().getApplicationContext();
		mPresent = new HoursePresenter(mContext, MyFragment.this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i("fragmenttest", "homefragment onCreateView ");
		mRootView = inflater.inflate(R.layout.home_my_fragment, container, false);
		initView();
		initData();
		return mRootView;
	}
	
	private void initView(){
		mUserContainer = (FrameLayout)mRootView.findViewById(R.id.id_userinfo_container);
		mUserNickname = (TextView)mRootView.findViewById(R.id.id_user_nickname);
		mUserId = (TextView)mRootView.findViewById(R.id.id_user_id);
		mUserAddress = (TextView)mRootView.findViewById(R.id.id_user_address);
		mLoadingView = (View)mRootView.findViewById(R.id.id_data_loading);
		showLoadingView();
		mUserContainer.setVisibility(View.INVISIBLE);
		
		mPublishHouse = (FrameLayout)mRootView.findViewById(R.id.id_user_house_publish);
		mDeleteHouse = (FrameLayout)mRootView.findViewById(R.id.id_user_house_delete);
		mPassword = (FrameLayout)mRootView.findViewById(R.id.id_userinfo_password_modify);
		mLogout = (FrameLayout)mRootView.findViewById(R.id.id_userinfo_logout);
		mPassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mContext, ModifyPasswordActivity.class));
			}
		});
		mLogout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showPublicAttributeDialog(0);
			}
		});
		mPublishHouse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, AddHouseInfoActivity.class);
				intent.putExtra("user_name", mUsername);
				startActivity(intent);
			}
		});
		mDeleteHouse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(mContext, getString(R.string.long_press_to_delete), Toast.LENGTH_SHORT).show();
				
			}
		});
		showLoadingView();
	}
	
	private void initData(){
		getUserInfo();
	}
	
	private void getUserInfo(){
		String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetUserInfo";
		String soapaction = "http://tempuri.org/GetUserInfo";
		SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(soapaction));
		rpc.addProperty("username", mUsername);
		mPresent.readyPresentServiceParams(mContext, url, soapaction, rpc);
		mPresent.startPresentServiceTask();
		
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
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			UserInfoModel infoModel = JsonObjectParse.parseUserInfo((String)msg.obj);
			dismissLoadingView();
			if (infoModel != null){
				mUserContainer.setVisibility(View.VISIBLE);
				mUserNickname.setText(infoModel.getUserNickName());
				mUserAddress.setText(infoModel.getUserAddress());
				mUserId.setText(infoModel.getUserLoginName());
			}
		}
	};
	
	private void showPublicAttributeDialog(final int position){
		new AlertDialog.Builder(getActivity()).setTitle("�˳���¼") 
		  
	     .setMessage("��ȷ��Ҫ�˳���¼��")//������ʾ������  
	  
	     .setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {
	         @Override  
	  
	         public void onClick(DialogInterface dialog, int which) {
	        	 SharedPreferences sharedata = mContext.getSharedPreferences("user_info", 0);
					SharedPreferences.Editor editor = sharedata.edit();
				    editor.putString("user_name", "");
				    editor.putString("user_password", "");
				    editor.commit();
				    Intent intent = new Intent(mContext, LoginUserActivity.class);
		            startActivity(intent);    
	        	 
	         }  
	  
	     }).setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {//��ӷ��ذ�ť  
	  
	         @Override  
	  
	         public void onClick(DialogInterface dialog, int which) {//��Ӧ�¼�  
	             Log.i("alertdialog"," �뱣�����ݣ�");  
	         }  
	  
	     }).show();//�ڰ�����Ӧ�¼�����ʾ�˶Ի���  
	}

	@Override
	public void onStatusSuccess(String action, String templateInfo) {
		// TODO Auto-generated method stub
		Log.e("mingguo", "success "+templateInfo);
		Message msgMessage = mHandler.obtainMessage();
		msgMessage.obj = templateInfo;
		msgMessage.sendToTarget();
	}

	@Override
	public void onStatusStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusError(String action, String error) {
		// TODO Auto-generated method stub
		
	}
	
}
