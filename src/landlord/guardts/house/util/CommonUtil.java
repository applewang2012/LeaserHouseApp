package landlord.guardts.house.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.content.Context;
import android.content.res.AssetManager;
import landlord.guardts.house.model.CityModel;
import landlord.guardts.house.model.DistrictModel;
import landlord.guardts.house.model.ProvinceModel;
import landlord.guardts.house.widget.XmlParserHandler;

public class CommonUtil {
	public static final String NAMESPACE = "http://tempuri.org/";
	public static final String ADS_PREFIX="http://byw2863630001.my3w.com/";
	/**
     * 记录播放位置
     */
    public static int playPosition=-1;
	/**
	 * ����ʡ
	 */
	public static String[] mProvinceDatas;
	/**
	 * key - ʡ value - ��
	 */
	public static Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - �� values - ��
	 */
	public static Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
	
	/**
	 * key - �� values - �ʱ�
	 */
	public static Map<String, String> mZipcodeDatasMap = new HashMap<String, String>(); 

	/**
	 * ��ǰʡ������
	 */
	public static String mCurrentProviceName;
	/**
	 * ��ǰ�е�����
	 */
	public static String mCurrentCityName;
	/**
	 * ��ǰ��������
	 */
	public static String mCurrentDistrictName ="";
	
	/**
	 * ��ǰ������������
	 */
	public static String mCurrentZipCode ="";
	
	/**
	 * ����ʡ������XML����
	 */
	
	public static String mLoginUser;
	
	public static String getSoapName(String action){
		if (action == null || action.equals("")){
			return null;
		}
		int index = action.lastIndexOf("/");
		return action.substring(index+1);
	}
	
	
	public static void initProvinceDatas(Context context)
	{
		List<ProvinceModel> provinceList = null;
    	AssetManager asset = context.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // ����һ������xml�Ĺ�������
			SAXParserFactory spf = SAXParserFactory.newInstance();
			// ����xml
			SAXParser parser = spf.newSAXParser();
			XmlParserHandler handler = new XmlParserHandler();
			parser.parse(input, handler);
			input.close();
			// ��ȡ��������������
			provinceList = handler.getDataList();
			//*/ ��ʼ��Ĭ��ѡ�е�ʡ���С���
			if (provinceList!= null && !provinceList.isEmpty()) {
				mCurrentProviceName = provinceList.get(0).getName();
				List<CityModel> cityList = provinceList.get(0).getCityList();
				if (cityList!= null && !cityList.isEmpty()) {
					mCurrentCityName = cityList.get(0).getName();
					List<DistrictModel> districtList = cityList.get(0).getDistrictList();
					mCurrentDistrictName = districtList.get(0).getName();
					mCurrentZipCode = districtList.get(0).getZipcode();
				}
			}
			//*/
			mProvinceDatas = new String[provinceList.size()];
        	for (int i=0; i< provinceList.size(); i++) {
        		// ��������ʡ������
        		mProvinceDatas[i] = provinceList.get(i).getName();
        		List<CityModel> cityList = provinceList.get(i).getCityList();
        		String[] cityNames = new String[cityList.size()];
        		for (int j=0; j< cityList.size(); j++) {
        			// ����ʡ����������е�����
        			cityNames[j] = cityList.get(j).getName();
        			List<DistrictModel> districtList = cityList.get(j).getDistrictList();
        			String[] distrinctNameArray = new String[districtList.size()];
        			DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
        			for (int k=0; k<districtList.size(); k++) {
        				// ����������������/�ص�����
        				DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
        				// ��/�ض��ڵ��ʱ࣬���浽mZipcodeDatasMap
        				mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
        				distrinctArray[k] = districtModel;
        				distrinctNameArray[k] = districtModel.getName();
        			}
        			// ��-��/�ص����ݣ����浽mDistrictDatasMap
        			mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
        		}
        		// ʡ-�е����ݣ����浽mCitisDatasMap
        		mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
        	}
        } catch (Throwable e) {  
            e.printStackTrace();  
        } finally {
        	
        } 
	}
}
