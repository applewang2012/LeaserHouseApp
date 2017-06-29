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
				rpc.addProperty("realName", "�α���");
				rpc.addProperty("title", "��Ա");
				rpc.addProperty("sex", "��");
				rpc.addProperty("phone", "18888888888");
				rpc.addProperty("fax", "00000000000");
				rpc.addProperty("email", "songxiaobao@126.com");
				rpc.addProperty("idcard", "120110198102098888");
				rpc.addProperty("nickName", "��С��");
				rpc.addProperty("address", "����ʡͨ���л�����");
				rpc.addProperty("status", "0"); //0������1������
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
				//  on status success  false �û�����ע��
				// on status success  true   �û���û��ע��
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
				//  on status success  true ��¼�ɹ�
				// oon status success  false   ��½ʧ��
			}
		});
		
		Button button5 = (Button)findViewById(R.id.id_main_test5);
		button5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String url = "http://qxw2332340157.my3w.com/services.asmx?op=AddRentInfo";
				String soapaction = "http://tempuri.org/AddRentInfo";
				SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(soapaction));
				rpc.addProperty("RentNo", "songxiabao");   //����֤���
				rpc.addProperty("RDName", "1234567");      //���� ���GetDistrictList,��ֵLDID</param>
				rpc.addProperty("RSName", "songxiabao");   //�ֵ� ���GetStreetList��ȡ����ֵLSID</param>
				rpc.addProperty("RRName", "1234567");      //���� ���GetStreetList��ȡ����ֵLSID</param>
				rpc.addProperty("RPSName", "songxiabao");  //�����ɳ�����ѡ�񣬱���  parentstationId��ȡ����ֵPSID
				rpc.addProperty("RAddress", "1234567");    //������ϸ��ַ���û����룬����
				rpc.addProperty("RDoor", "songxiabao");    //���ƺţ��û����룬����
				rpc.addProperty("RTotalDoor", "1234567");  //�����ƺţ��û����룬ѡ��
				
				rpc.addProperty("RRoomType", "songxiabao"); //���ͣ�ѡ�񣬱��GetHouseType ����ȡ����ֵRSOID
				rpc.addProperty("RDirection", "1234567");   //����ѡ�񣬱��GetHouseDirection������ȡ����ֵRSOID
				rpc.addProperty("RStructure", "songxiabao"); //�ṹ��ѡ�񣬱��GetHouseStructure������ȡ����ֵRSOID
				rpc.addProperty("RBuildingType", "1234567"); //�����ṹ��ѡ�񣬱��GetBuildingStructure������ȡ����ֵRSOID
				rpc.addProperty("RFloor", "songxiabao");     //���ݲ������û����룬ѡ��
				rpc.addProperty("RTotalFloor", "1234567");   //�����ܲ������û����룬ѡ��
				rpc.addProperty("RHouseAge", "songxiabao");  //���䣬�û����룬ѡ��
				rpc.addProperty("RRentArea", "1234567");     //����������û����룬����
				
				rpc.addProperty("RProperty", "songxiabao");  //�������ʣ�ѡ�񣬱��GetHouseProperty������ȡ����ֵRSOID
				rpc.addProperty("ROwner", "1234567");        //�������ƣ��û����룬����
				rpc.addProperty("ROwnerTel", "songxiabao");  //�����绰���û����룬ѡ��<
				rpc.addProperty("RIDCard", "1234567");       //�������֤�ţ��û����룬����
				rpc.addProperty("RLocationDescription", "songxiabao");  //�����������û����룬ѡ��
				rpc.addProperty("RPSParentName", "1234567");            //�����ɷ־֣�ѡ�񣬱��GetPoliceStationList��ȡ����ֵPSID
				rpc.addProperty("createdBy", "songxiabao");             //�����ˣ���¼��������
				rpc.addProperty("rentType", "1234567");                 //�������ͣ�ѡ�񣬱��GetHouseRentType������ȡ����ֵRSOID
				rpc.addProperty("ownType", "songxiabao");               //�������ͣ�ѡ�񣬱��GetHouseOwnType������ȡ����ֵRSOID
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
				//  on status success  [{"RSOUrl":"0","IsVisible":null,"RSOName":"�����","RSOParentNo":18,"RSOID":20,"RSONo":0,"RSOOrder":1},{"RSOUrl":"0","IsVisible":null,"RSOName":"������","RSOParentNo":18,"RSOID":21,"RSONo":0,"RSOOrder":2},{"RSOUrl":"0","IsVisible":null,"RSOName":"�Ϻ���","RSOParentNo":18,"RSOID":22,"RSONo":0,"RSOOrder":3}]
				String url = "http://qxw2332340157.my3w.com/services.asmx?op=AddRentInfo";
				String soapaction = "http://tempuri.org/AddRentInfo";
				SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(soapaction));
				rpc.addProperty("RentNo", "123456789");
				rpc.addProperty("RAddress", "����ʡͨ���л�����");
				rpc.addProperty("RRentArea", "60ƽ��");
				rpc.addProperty("RProperty", "��������");
				rpc.addProperty("ROwner", "��С��");
				rpc.addProperty("RIDCard", "120110198102098888");
				rpc.addProperty("RLocationDescription", "songxiabao�ķ���");
				
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
				// [{"LDStatus":true,"LDShortName":"HP","LDIsImport":false,"LDID":1,"LDName":"��ƽ������","LDDescription":"����к�ƽ��","LDMap":"1"},{"LDStatus":true,"LDShortName":"HQ","LDIsImport":false,"LDID":3,"LDName":"������","LDDescription":"����к�����","LDMap":"2"},{"LDStatus":true,"LDShortName":"HX","LDIsImport":false,"LDID":4,"LDName":"������","LDDescription":"����к�����","LDMap":"3"},{"LDStatus":true,"LDShortName":"HD","LDIsImport":false,"LDID":7,"LDName":"�Ӷ���","LDDescription":"1","LDMap":"1"}]
			
				
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
				// [{"LSStatus":true,"LSName":"��ƽ�ֵ�","LSMap":"1","LDName":"��ƽ��","LSIsImport":true,"LSID":1,"LDID":1,"LSDescription":"2"},{"LSStatus":true,"LSName":"��ƽ2","LSMap":"1","LDName":"��ƽ��","LSIsImport":false,"LSID":5,"LDID":1,"LSDescription":"1"}]
//				String url = "http://qxw2332340157.my3w.com/services.asmx?op=GetHouseInfo";
//				String soapaction = "http://tempuri.org/GetHouseInfo";
//				SoapObject rpc = new SoapObject(CommonUtil.NAMESPACE, CommonUtil.getSoapName(soapaction));
//				rpc.addProperty("idcard", "12010519780419061X");
//				presenter.readyPresentServiceParams(getApplicationContext(), url, soapaction, rpc);
//				presenter.startPresentServiceTask();
				//on status success  [{"RID":1,"IsObsoleted":false,"RPSParentName":"�Ͽ��־ֲ���","RModifiedDate":"\/Date(1488589873000)\/","RentNO":"HP-10000001","RHouseAge":1,"day":20,"RBuildingType":"ש��","LDID":1,"ROwnType":"","Latitude":39.1155240,"RRentArea":8.00,"RMapID":0,"RDirection":"������","RCreatedDate":"\/Date(1487520000000)\/","month":2,"RProperty":"˽��","PSID":11,"RBuildArea":0.00,"hour":0,"RCreatedDate1":"\/Date(1487520000000)\/","RPSName":"���������ɳ���","RTotalFloor":89,"RRoomType":"����","RAddress":"����к���������23��","RRentType":"","RTotalDoor":8,"RIDCard":"12010519780419061X","ROwnerTel":"13920887566","year":2017,"RCreatedBy":"������Ա","RLocationDescription":"88888","RDName":"��ƽ������","IsAvailable":"����","RPSID":11,"RStructure":"���","ROwner":"����","RFloor":"121","RModifiedBy":"Admin","RRName":"��ƽ����","RSName":"��ƽ�ֵ�","Longitude":117.2148200,"Available":true,"RDoor":"899"},{"RID":11,"IsObsoleted":false,"RPSParentName":"�Ͽ��־ֲ���","RModifiedDate":null,"RentNO":"HP-10000004","RHouseAge":5,"day":1,"RBuildingType":"ש��","LDID":1,"ROwnType":"","Latitude":39.1248090,"RRentArea":63.20,"RMapID":0,"RDirection":"�ϱ���","RCreatedDate":"\/Date(1488339378000)\/","month":3,"RProperty":"˽��","PSID":16,"RBuildArea":0.00,"hour":11,"RCreatedDate1":"\/Date(1488339378000)\/","RPSName":"�������ɳ���","RTotalFloor":56,"RRoomType":"һ��","RAddress":"����к�ƽ���ƽ16��¥1-202","RRentType":"","RTotalDoor":3,"RIDCard":"12010519780419061X","ROwnerTel":"13920887566","year":2017,"RCreatedBy":"Admin","RLocationDescription":"�鷿����5�꣬��װ��","RDName":"��ƽ������","IsAvailable":"δ��","RPSID":16,"RStructure":"���","ROwner":"ë���","RFloor":"18","RModifiedBy":null,"RRName":"��ƽ����","RSName":"��ƽ�ֵ�","Longitude":117.2028140,"Available":false,"RDoor":"1102"},{"RID":13,"IsObsoleted":false,"RPSParentName":"�Ͽ��־ֲ���","RModifiedDate":"\/Date(1488822651000)\/","RentNO":"JX-2017004","RHouseAge":2,"day":6,"RBuildingType":"","LDID":3,"ROwnType":"","Latitude":39.0844940,"RRentArea":123.00,"RMapID":0,"RDirection":"","RCreatedDate":"\/Date(1488747444000)\/","month":3,"RProperty":"˽��","PSID":16,"RBuildArea":0.00,"hour":4,"RCreatedDate1":"\/Date(1488747444000)\/","RPSName":"�������ɳ���","RTotalFloor":34,"RRoomType":"","RAddress":"����к�����С��¥105��","RRentType":"","RTotalDoor":1,"RIDCard":"12010519780419061X","ROwnerTel":"13920887566","year":2017,"RCreatedBy":"admin","RLocationDescription":"����","RDName":"������","IsAvailable":"����","RPSID":16,"RStructure":"","ROwner":"����","RFloor":"12","RModifiedBy":"Admin","RRName":"����11","RSName":"����1","Longitude":117.2361650,"Available":true,"RDoor":"189"},{"RID":14,"IsObsoleted":false,"RPSParentName":"�Ͽ��־ֲ���","RModifiedDate":"\/Date(1489605813000)\/","RentNO":"JX-20170011","RHouseAge":0,"day":6,"RBuildingType":"ש��","LDID":3,"ROwnType":"","Latitude":39.0844940,"RRentArea":123.00,"RMapID":0,"RDirection":"�ϱ���","RCreatedDate":"\/Date(1488749266000)\/","month":3,"RProperty":"˽��","PSID":11,"RBuildArea":0.00,"hour":5,"RCreatedDate1":"\/Date(1488749266000)\/","RPSName":"���������ɳ���","RTotalFloor":0,"RRoomType":"һ��","RAddress":"����к�����С��¥105��","RRentType":"","RTotalDoor":1,"RIDCard":"12010519780419061X","ROwnerTel":"13920887566","year":2017,"RCreatedBy":"admin","RLocationDescription":"����","RDName":"������","IsAvailable":"δ��","RPSID":11,"RStructure":"���","ROwner":"����","RFloor":"121","RModifiedBy":"Admin","RRName":"����11","RSName":"����1","Longitude":117.2361650,"Available":false,"RDoor":"11111"},{"RID":18,"IsObsoleted":false,"RPSParentName":"��ƽ�־�","RModifiedDate":"\/Date(1488822621000)\/","RentNO":"JX-20170012","RHouseAge":0,"day":6,"RBuildingType":"ש��","LDID":3,"ROwnType":"","Latitude":39.1679570,"RRentArea":123.00,"RMapID":0,"RDirection":"������","RCreatedDate":"\/Date(1488749776000)\/","month":3,"
			
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
				// [{"LSStatus":true,"LSName":"��ƽ�ֵ�","LSMap":"1","LDName":"��ƽ��","LSIsImport":true,"LSID":1,"LDID":1,"LSDescription":"2"},{"LSStatus":true,"LSName":"��ƽ2","LSMap":"1","LDName":"��ƽ��","LSIsImport":false,"LSID":5,"LDID":1,"LSDescription":"1"}]
			
				
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
