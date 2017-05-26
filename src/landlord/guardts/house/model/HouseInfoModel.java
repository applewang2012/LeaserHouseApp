package landlord.guardts.house.model;

public class HouseInfoModel {
	private String houseAddress;
	private String houseType;
	private String houseDirection;
	private String houseStatus;
	private String houseCurrentFloor;
	private String houseTotalFloor;
	private boolean houseAvailable;
	private String houseId;
	private String ownerName;
	private String ownerIdcard;
	
	public String getHouseId() {
		return houseId;
	}

	public void setHouseId(String id) {
		this.houseId = id;
	}

	public String getHouseAddress() {
		return houseAddress;
	}

	public void setHouseAddress(String address) {
		this.houseAddress = address;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String type) {
		this.houseType = type;
	}

	public String getHouseTotalFloor() {
		return houseTotalFloor;
	}

	public void setHouseTotalFloor(String floor) {
		this.houseTotalFloor = floor;
	}
	
	public String getHouseCurrentFloor() {
		return houseCurrentFloor;
	}

	public void setHouseCurrentFloor(String floor) {
		this.houseCurrentFloor = floor;
	}
	
	public String getHouseDirection() {
		return houseDirection;
	}

	public void setHouseDirection(String direction) {
		this.houseDirection = direction;
	}
	
	public String getHouseStatus() {
		return houseStatus;
	}

	public void setHouseStatus(String status) {
		this.houseStatus = status;
	}
	
	public boolean getHouseAvailable() {
		return houseAvailable;
	}

	public void setHouseAvailable(boolean avilable) {
		this.houseAvailable = avilable;
	}
	
	public String getHouseOwnerName() {
		return ownerName;
	}

	public void setHouseOwnerName(String name) {
		this.ownerName = name;
	}
	
	public String getHouseOwnerIdcard() {
		return ownerIdcard;
	}

	public void setHouseOwnerIdcard(String id) {
		this.ownerIdcard = id;
	}

}
