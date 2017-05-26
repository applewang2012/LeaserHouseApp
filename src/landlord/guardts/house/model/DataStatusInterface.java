package landlord.guardts.house.model;


public interface DataStatusInterface {
	void onStatusSuccess(String action, String templateInfo);
	void onStatusStart();
	void onStatusError(String action, String error);
}
