package landlord.guardts.house;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import landlord.guardts.house.model.DataStatusInterface;
import landlord.guardts.house.present.HoursePresenter;
import landlord.guardts.house.util.CommonUtil;
import landlord.guardts.house.view.SelectorFragment;

public class MainActivity extends Activity implements DataStatusInterface{
	//private String mUrl = "http://qxw2332340157.my3w.com/services.asmx?op=GetHouseInfo";
	//private String mSoapAction = "http://tempuri.org/GetHouseInfo";
	
	private SelectorFragment mFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final HoursePresenter presenter = new HoursePresenter(getApplicationContext(), this);
		
		
		Button button1 = (Button)findViewById(R.id.id_main_test1);
		button1.setOnClickListener(new OnClickListener() {
			
			

			@Override
			public void onClick(View v) {
//				String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetHouseInfo";
//				String mSoapAction = "http://tempuri.org/GetHouseInfo";
//				SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(mSoapAction));
//				rpc.addProperty("idcard", "idccard");
//				presenter.readyPresentServiceParams(getApplicationContext(), url, mSoapAction, rpc);
//				presenter.startPresentServiceTask();
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				if (mFragment == null){
					mFragment = new SelectorFragment();
					fragmentTransaction.add(R.id.id_selector_fragment_container, mFragment);
					fragmentTransaction.commitAllowingStateLoss();
				}else{
					fragmentTransaction.show(mFragment);
					fragmentTransaction.commitAllowingStateLoss();
				}
			}
		});
		
		
		
		Button button2 = (Button)findViewById(R.id.id_main_test2);
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String url = "http://qxw2332340157.my3w.com/services.asmx?op=AddUserInfo";
				String soapaction = "http://tempuri.org/AddUserInfo";
				SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(soapaction));
				rpc.addProperty("loginName", "songxiabao");
				rpc.addProperty("password", "123456");
				rpc.addProperty("userType", "1");
				rpc.addProperty("realName", "宋宝利");
				rpc.addProperty("title", "演员");
				rpc.addProperty("sex", "男");
				rpc.addProperty("phone", "18888888888");
				rpc.addProperty("fax", "00000000000");
				rpc.addProperty("email", "songxiaobao@126.com");
				rpc.addProperty("idcard", "120110198102098888");
				rpc.addProperty("nickName", "宋小宝");
				rpc.addProperty("address", "吉林省通化市辉南县");
				rpc.addProperty("status", "0"); //0正常，1监视中
				presenter.readyPresentServiceParams(getApplicationContext(), url, soapaction, rpc);
				presenter.startPresentServiceTask();
				
				//  on status success  true
			}
		});
		
		Button button3 = (Button)findViewById(R.id.id_main_test3);
		button3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url = "http://qxw2332340157.my3w.com/services.asmx?op=ValidateLoginName";
				String soapaction = "http://tempuri.org/ValidateLoginName";
				SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(soapaction));
				rpc.addProperty("loginName", "songxiabao2"); 
				presenter.readyPresentServiceParams(getApplicationContext(), url, soapaction, rpc);
				presenter.startPresentServiceTask();
				//  on status success  false 用户名已注册
				// on status success  true   用户名没被注册
			}
		});
		
		Button button4 = (Button)findViewById(R.id.id_main_test4);
		button4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String url = "http://qxw2332340157.my3w.com/services.asmx?op=ValidateLogin";
				String soapaction = "http://tempuri.org/ValidateLogin";
				SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(soapaction));
				rpc.addProperty("username", "songxiabao"); 
				rpc.addProperty("password", "1234567"); 
				presenter.readyPresentServiceParams(getApplicationContext(), url, soapaction, rpc);
				presenter.startPresentServiceTask();
				//  on status success  true 登录成功
				// oon status success  false   登陆失败
			}
		});
		
		Button button5 = (Button)findViewById(R.id.id_main_test5);
		button5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String url = "http://qxw2332340157.my3w.com/services.asmx?op=AddRentInfo";
				String soapaction = "http://tempuri.org/AddRentInfo";
				SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(soapaction));
				rpc.addProperty("RentNo", "songxiabao");   //房产证编号
				rpc.addProperty("RDName", "1234567");      //区划 必填，GetDistrictList,传值LDID</param>
				rpc.addProperty("RSName", "songxiabao");   //街道 必填，GetStreetList获取，传值LSID</param>
				rpc.addProperty("RRName", "1234567");      //街区 必填，GetStreetList获取，传值LSID</param>
				rpc.addProperty("RPSName", "songxiabao");  //所属派出所，选择，必填  parentstationId获取，传值PSID
				rpc.addProperty("RAddress", "1234567");    //房屋详细地址，用户输入，必填
				rpc.addProperty("RDoor", "songxiabao");    //门牌号，用户输入，必填
				rpc.addProperty("RTotalDoor", "1234567");  //总门牌号，用户输入，选填
				
				rpc.addProperty("RRoomType", "songxiabao"); //房型，选择，必填，GetHouseType 法获取，传值RSOID
				rpc.addProperty("RDirection", "1234567");   //朝向，选择，必填，GetHouseDirection方法获取，传值RSOID
				rpc.addProperty("RStructure", "songxiabao"); //结构，选择，必填，GetHouseStructure方法获取，传值RSOID
				rpc.addProperty("RBuildingType", "1234567"); //建筑结构，选择，必填，GetBuildingStructure方法获取，传值RSOID
				rpc.addProperty("RFloor", "songxiabao");     //房屋层数，用户输入，选填
				rpc.addProperty("RTotalFloor", "1234567");   //房屋总层数，用户输入，选填
				rpc.addProperty("RHouseAge", "songxiabao");  //房龄，用户输入，选填
				rpc.addProperty("RRentArea", "1234567");     //房屋面积，用户输入，必填
				
				rpc.addProperty("RProperty", "songxiabao");  //房产性质，选择，必填，GetHouseProperty方法获取，传值RSOID
				rpc.addProperty("ROwner", "1234567");        //房主名称，用户输入，必填
				rpc.addProperty("ROwnerTel", "songxiabao");  //房主电话，用户输入，选填<
				rpc.addProperty("RIDCard", "1234567");       //房主身份证号，用户输入，必填
				rpc.addProperty("RLocationDescription", "songxiabao");  //房屋描述，用户输入，选填
				rpc.addProperty("RPSParentName", "1234567");            //所属派分局，选择，必填，GetPoliceStationList获取，传值PSID
				rpc.addProperty("createdBy", "songxiabao");             //创建人，登录名，必填
				rpc.addProperty("rentType", "1234567");                 //租赁类型，选择，必填，GetHouseRentType方法获取，传值RSOID
				rpc.addProperty("ownType", "songxiabao");               //所有类型，选择，必填，GetHouseOwnType方法获取，传值RSOID
				presenter.readyPresentServiceParams(getApplicationContext(), url, soapaction, rpc);
				presenter.startPresentServiceTask();
				
			}
			
		});
		
		Button button6 = (Button)findViewById(R.id.id_main_test6);
		button6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetProvinceList";
//				String soapaction = "http://tempuri.org/GetProvinceList";
//				SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(soapaction));
//				
//				presenter.readyPresentServiceParams(getApplicationContext(), url, soapaction, rpc);
//				presenter.startPresentServiceTask();
				//  on status success  [{"RSOUrl":"0","IsVisible":null,"RSOName":"天津市","RSOParentNo":18,"RSOID":20,"RSONo":0,"RSOOrder":1},{"RSOUrl":"0","IsVisible":null,"RSOName":"北京市","RSOParentNo":18,"RSOID":21,"RSONo":0,"RSOOrder":2},{"RSOUrl":"0","IsVisible":null,"RSOName":"上海市","RSOParentNo":18,"RSOID":22,"RSONo":0,"RSOOrder":3}]
				String url = "http://qxw2332340157.my3w.com/services.asmx?op=AddRentInfo";
				String soapaction = "http://tempuri.org/AddRentInfo";
				SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(soapaction));
				rpc.addProperty("RentNo", "123456789");
				rpc.addProperty("RAddress", "吉林省通化市辉南县");
				rpc.addProperty("RRentArea", "60平米");
				rpc.addProperty("RProperty", "房产性质");
				rpc.addProperty("ROwner", "宋小宝");
				rpc.addProperty("RIDCard", "120110198102098888");
				rpc.addProperty("RLocationDescription", "songxiabao的房子");
				
				presenter.readyPresentServiceParams(getApplicationContext(), url, soapaction, rpc);
				presenter.startPresentServiceTask();
			}
		});
		
		Button button7 = (Button)findViewById(R.id.id_main_test7);
		button7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetDistrictList";
//				String soapaction = "http://tempuri.org/GetDistrictList";
//				SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(soapaction));
//				presenter.readyPresentServiceParams(getApplicationContext(), url, soapaction, rpc);
//				presenter.startPresentServiceTask();
				// [{"LDStatus":true,"LDShortName":"HP","LDIsImport":false,"LDID":1,"LDName":"和平区测试","LDDescription":"天津市和平区","LDMap":"1"},{"LDStatus":true,"LDShortName":"HQ","LDIsImport":false,"LDID":3,"LDName":"红桥区","LDDescription":"天津市红桥区","LDMap":"2"},{"LDStatus":true,"LDShortName":"HX","LDIsImport":false,"LDID":4,"LDName":"河西区","LDDescription":"天津市河西区","LDMap":"3"},{"LDStatus":true,"LDShortName":"HD","LDIsImport":false,"LDID":7,"LDName":"河东区","LDDescription":"1","LDMap":"1"}]
			
				
				String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetHouseInfoByLoginName";
				String soapaction = "http://tempuri.org/GetHouseInfoByLoginName";
				SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(soapaction));
				rpc.addProperty("loginName", "songxiabao");
				presenter.readyPresentServiceParams(getApplicationContext(), url, soapaction, rpc);
				presenter.startPresentServiceTask();
			
			}
			
		});
		Button button8 = (Button)findViewById(R.id.id_main_test8);
		button8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetStreetList";
//				String soapaction = "http://tempuri.org/GetStreetList";
//				SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(soapaction));
//				rpc.addProperty("district", "1");
//				presenter.readyPresentServiceParams(getApplicationContext(), url, soapaction, rpc);
//				presenter.startPresentServiceTask();
				// [{"LSStatus":true,"LSName":"和平街道","LSMap":"1","LDName":"和平区","LSIsImport":true,"LSID":1,"LDID":1,"LSDescription":"2"},{"LSStatus":true,"LSName":"和平2","LSMap":"1","LDName":"和平区","LSIsImport":false,"LSID":5,"LDID":1,"LSDescription":"1"}]
//				String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetHouseInfo";
//				String soapaction = "http://tempuri.org/GetHouseInfo";
//				SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(soapaction));
//				rpc.addProperty("idcard", "12010519780419061X");
//				presenter.readyPresentServiceParams(getApplicationContext(), url, soapaction, rpc);
//				presenter.startPresentServiceTask();
				//on status success  [{"RID":1,"IsObsoleted":false,"RPSParentName":"南开分局测试","RModifiedDate":"\/Date(1488589873000)\/","RentNO":"HP-10000001","RHouseAge":1,"day":20,"RBuildingType":"砖混","LDID":1,"ROwnType":"","Latitude":39.1155240,"RRentArea":8.00,"RMapID":0,"RDirection":"东西向","RCreatedDate":"\/Date(1487520000000)\/","month":2,"RProperty":"私产","PSID":11,"RBuildArea":0.00,"hour":0,"RCreatedDate1":"\/Date(1487520000000)\/","RPSName":"体育中心派出所","RTotalFloor":89,"RRoomType":"二室","RAddress":"天津市河西区马场道23号","RRentType":"","RTotalDoor":8,"RIDCard":"12010519780419061X","ROwnerTel":"13920887566","year":2017,"RCreatedBy":"测试人员","RLocationDescription":"88888","RDName":"和平区测试","IsAvailable":"已租","RPSID":11,"RStructure":"框架","ROwner":"李三","RFloor":"121","RModifiedBy":"Admin","RRName":"和平测试","RSName":"和平街道","Longitude":117.2148200,"Available":true,"RDoor":"899"},{"RID":11,"IsObsoleted":false,"RPSParentName":"南开分局测试","RModifiedDate":null,"RentNO":"HP-10000004","RHouseAge":5,"day":1,"RBuildingType":"砖混","LDID":1,"ROwnType":"","Latitude":39.1248090,"RRentArea":63.20,"RMapID":0,"RDirection":"南北向","RCreatedDate":"\/Date(1488339378000)\/","month":3,"RProperty":"私产","PSID":16,"RBuildArea":0.00,"hour":11,"RCreatedDate1":"\/Date(1488339378000)\/","RPSName":"王顶堤派出所","RTotalFloor":56,"RRoomType":"一室","RAddress":"天津市和平区瞰和平16号楼1-202","RRentType":"","RTotalDoor":3,"RIDCard":"12010519780419061X","ROwnerTel":"13920887566","year":2017,"RCreatedBy":"Admin","RLocationDescription":"婚房，过5年，精装修","RDName":"和平区测试","IsAvailable":"未租","RPSID":16,"RStructure":"框架","ROwner":"毛乃峥","RFloor":"18","RModifiedBy":null,"RRName":"和平测试","RSName":"和平街道","Longitude":117.2028140,"Available":false,"RDoor":"1102"},{"RID":13,"IsObsoleted":false,"RPSParentName":"南开分局测试","RModifiedDate":"\/Date(1488822651000)\/","RentNO":"JX-2017004","RHouseAge":2,"day":6,"RBuildingType":"","LDID":3,"ROwnType":"","Latitude":39.0844940,"RRentArea":123.00,"RMapID":0,"RDirection":"","RCreatedDate":"\/Date(1488747444000)\/","month":3,"RProperty":"私产","PSID":16,"RBuildArea":0.00,"hour":4,"RCreatedDate1":"\/Date(1488747444000)\/","RPSName":"王顶堤派出所","RTotalFloor":34,"RRoomType":"","RAddress":"天津市河西区小白楼105号","RRentType":"","RTotalDoor":1,"RIDCard":"12010519780419061X","ROwnerTel":"13920887566","year":2017,"RCreatedBy":"admin","RLocationDescription":"独有","RDName":"红桥区","IsAvailable":"已租","RPSID":16,"RStructure":"","ROwner":"李四","RFloor":"12","RModifiedBy":"Admin","RRName":"红桥11","RSName":"红桥1","Longitude":117.2361650,"Available":true,"RDoor":"189"},{"RID":14,"IsObsoleted":false,"RPSParentName":"南开分局测试","RModifiedDate":"\/Date(1489605813000)\/","RentNO":"JX-20170011","RHouseAge":0,"day":6,"RBuildingType":"砖混","LDID":3,"ROwnType":"","Latitude":39.0844940,"RRentArea":123.00,"RMapID":0,"RDirection":"南北向","RCreatedDate":"\/Date(1488749266000)\/","month":3,"RProperty":"私产","PSID":11,"RBuildArea":0.00,"hour":5,"RCreatedDate1":"\/Date(1488749266000)\/","RPSName":"体育中心派出所","RTotalFloor":0,"RRoomType":"一室","RAddress":"天津市河西区小白楼105号","RRentType":"","RTotalDoor":1,"RIDCard":"12010519780419061X","ROwnerTel":"13920887566","year":2017,"RCreatedBy":"admin","RLocationDescription":"独有","RDName":"红桥区","IsAvailable":"未租","RPSID":11,"RStructure":"框架","ROwner":"李四","RFloor":"121","RModifiedBy":"Admin","RRName":"红桥11","RSName":"红桥1","Longitude":117.2361650,"Available":false,"RDoor":"11111"},{"RID":18,"IsObsoleted":false,"RPSParentName":"和平分局","RModifiedDate":"\/Date(1488822621000)\/","RentNO":"JX-20170012","RHouseAge":0,"day":6,"RBuildingType":"砖混","LDID":3,"ROwnType":"","Latitude":39.1679570,"RRentArea":123.00,"RMapID":0,"RDirection":"东西向","RCreatedDate":"\/Date(1488749776000)\/","month":3,"
			
				String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetUserInfo";
				String soapaction = "http://tempuri.org/GetUserInfo";
				SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(soapaction));
				rpc.addProperty("username", "mnz");
				presenter.readyPresentServiceParams(getApplicationContext(), url, soapaction, rpc);
				presenter.startPresentServiceTask();
			}
			
		});
		
		Button button9 = (Button)findViewById(R.id.id_main_test9);
		button9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetRoadList";
//				String soapaction = "http://tempuri.org/GetRoadList";
//				SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(soapaction));
//				rpc.addProperty("district", "1");
//				rpc.addProperty("street", "5");
//				presenter.readyPresentServiceParams(getApplicationContext(), url, soapaction, rpc);
//				presenter.startPresentServiceTask();
				// [{"LSStatus":true,"LSName":"和平街道","LSMap":"1","LDName":"和平区","LSIsImport":true,"LSID":1,"LDID":1,"LSDescription":"2"},{"LSStatus":true,"LSName":"和平2","LSMap":"1","LDName":"和平区","LSIsImport":false,"LSID":5,"LDID":1,"LSDescription":"1"}]
			
				
			}
			
		});
	}


	@Override
	public void onStatusStart() {
		// TODO Auto-generated method stub
		Log.i("mingguo", "on status start  ");
	}

	@Override
	public void onStatusSuccess(String action, String templateInfo) {
		// TODO Auto-generated method stub
		Log.i("mingguo", "on status success    "+templateInfo);
	}

	@Override
	public void onStatusError(String action, String error) {
		// TODO Auto-generated method stub
		
	}
	
	

}
