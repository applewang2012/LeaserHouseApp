package landlord.guardts.house.model;

import android.location.Address;

public class UserInfoModel {

	private String loginName;
	private String nickName;
	private String userAddress;
	private String idcard;

	public String getUserLoginName() {
		return loginName;
	}
	public String setUserLoginName(String lgName) {
		return loginName = lgName;
	}

	public void setUserNickName(String nickname) {
		this.nickName = nickname;
	}
	
	public String getUserNickName() {
		return nickName;
	}

	public void setUserAddress(String address) {
		this.userAddress = address;
	}
	
	public String getUserAddress() {
		return userAddress;
	}

	public void setUserIdCard(String idcard) {
		this.idcard = idcard;
	}
	
	public String getUserIdCard() {
		return idcard;
	}
	

}
