package landlord.guardts.house;


import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import landlord.guardts.house.download.DownloadManager;
import landlord.guardts.house.download.DownloadManager.Request;
import landlord.guardts.house.downloadui.DownloadAdapter;
import landlord.guardts.house.downloadui.DownloadSelectListener;
import landlord.guardts.house.util.UniversalUtil;

public class DownloadAppActivity extends Activity{

	private DownloadManager mDownloadManager;
	private DownloadAdapter mSizeSortedAdapter;
	private ListView mSizeOrderedListView;
	private Cursor mSizeSortedCursor;
	private int mIdColumnId;
	private int mStatusColumnId;
	private long mQueuedDownloadId;
	private AlertDialog mQueuedDialog;
	private int mLocalUriColumnId;
	private int mMediaTypeColumnId;
	private int mReasonColumndId;
	private Button donwloadButton;
	private Button mDownloadButton;
	//private int mDownloadId;
	private int mDownloadUrlColumnId;
	private int mCurrentDownloadId;
	private PackageInstallerReceiver mInstallerReceiver = new PackageInstallerReceiver();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download_app_layout);
		registerReceiverData();
		initDownloadAdapter();
		
		checkDownloadFile();
		changeDownloadStatus();
		
	}
	
	private void checkDownloadFile(){
		mDownloadButton = (Button)findViewById(R.id.id_download_button);
		mDownloadButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeDownloadStatus();
			}
		});
		
	}
	
	private void initDownloadAdapter(){
		if (mDownloadManager == null) {
			mDownloadManager = new DownloadManager(
					getApplicationContext().getContentResolver(), getApplicationContext().getPackageName());
		}
		mSizeOrderedListView = (ListView) findViewById(R.id.size_ordered_list);
		mSizeOrderedListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				
			}
			
		});
		mDownloadManager.setAccessAllDownloads(true);
		DownloadManager.Query baseQuery = new DownloadManager.Query()
			.setOnlyIncludeVisibleInDownloadsUi(true);
		mSizeSortedCursor = mDownloadManager.query(baseQuery.orderBy(
				DownloadManager.COLUMN_TOTAL_SIZE_BYTES,
				DownloadManager.Query.ORDER_DESCENDING));
		
		
	    mIdColumnId = mSizeSortedCursor
			    .getColumnIndexOrThrow(DownloadManager.COLUMN_ID);
	    mStatusColumnId = mSizeSortedCursor
			    .getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS);
	    mLocalUriColumnId = mSizeSortedCursor
			    .getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI);
	    mDownloadUrlColumnId = mSizeSortedCursor.getColumnIndexOrThrow(DownloadManager.COLUMN_URI);
	    mMediaTypeColumnId = mSizeSortedCursor
			    .getColumnIndexOrThrow(DownloadManager.COLUMN_MEDIA_TYPE);
		mReasonColumndId = mSizeSortedCursor
			    .getColumnIndexOrThrow(DownloadManager.COLUMN_REASON);
		mSizeSortedAdapter = new DownloadAdapter(DownloadAppActivity.this, mSizeSortedCursor,new DownloadSelectListener() {
			
			@Override
			public void onDownloadSelectionChanged(long downloadId, boolean isSelected) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isDownloadSelected(long id) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void downloadStatus(int status) {
				Log.i("mingguo", "downloadStatu  update  "+status);
				updateDownloadStatus(status);
				
			}
		});
		mSizeOrderedListView.setAdapter(mSizeSortedAdapter);
	}
	
	
	private int queryDownloadStatus(String downloadpath){
		for (mSizeSortedCursor.moveToFirst(); !mSizeSortedCursor.isAfterLast(); mSizeSortedCursor
        		.moveToNext()) {
			String databasepath = mSizeSortedCursor.getString(mLocalUriColumnId);
			String downloadUrl = mSizeSortedCursor.getString(mDownloadUrlColumnId);
			Log.i("mingguo", "database  download path  "+databasepath+"  download url  "+downloadUrl);
			if (downloadUrl != null && downloadUrl.equalsIgnoreCase(UniversalUtil.DOWLOAD_URL)){
				int downloadId =  mSizeSortedCursor.getInt(mIdColumnId);
				if (downloadpath == null){
					Log.e("mingguo", "download path null  remove download id "+downloadId);
					mDownloadManager.remove(downloadId);
				}
				if (databasepath.equalsIgnoreCase("file://"+downloadpath)){
					mCurrentDownloadId =  mSizeSortedCursor.getInt(mIdColumnId);
					if (downloadpath != null && new File(downloadpath).exists()){
						return mSizeSortedCursor.getInt(mStatusColumnId);
					}
				}
			}
			
		}
		if (downloadpath != null && new File(downloadpath).exists()){
			Log.e("mingguo", "download database null delete download file ");
			new File(downloadpath).delete();
		}
		return -1;
	}
	
	private void updateDownloadStatus(int status){
		switch (status) {
		case DownloadManager.STATUS_PENDING:
		case DownloadManager.STATUS_RUNNING:
			mDownloadButton.setText(getString(R.string.downloading_click_pause));
			break;
		case DownloadManager.STATUS_PAUSED:
			mDownloadButton.setText(getString(R.string.pause_click_resume));
			break;
		case DownloadManager.STATUS_SUCCESSFUL:
			mDownloadButton.setText(getString(R.string.downloaded_click_install));
			String downloadfile = UniversalUtil.getDefaultDownloadPath(UniversalUtil.DOWLOAD_URL);
			UniversalUtil.installApk(DownloadAppActivity.this, downloadfile);
			break;
		default:
			mDownloadButton.setText(getString(R.string.no_download_click_download));
			break;
		}
	}
	
	private void changeDownloadStatus(){
		String downloadfile = UniversalUtil.getDefaultDownloadPath(UniversalUtil.DOWLOAD_URL);
		switch (queryDownloadStatus(downloadfile)) {
		case DownloadManager.STATUS_PENDING:
		case DownloadManager.STATUS_RUNNING:
			mDownloadButton.setText(getString(R.string.downloading_click_pause));
			mDownloadManager.pauseDownload(mCurrentDownloadId);
			break;
		case DownloadManager.STATUS_PAUSED:
			mDownloadButton.setText(getString(R.string.pause_click_resume));
			mDownloadManager.resumeDownload(mCurrentDownloadId);
			break;
		case DownloadManager.STATUS_SUCCESSFUL:
			mDownloadButton.setText(getString(R.string.downloaded_click_install));
			UniversalUtil.installApk(DownloadAppActivity.this, downloadfile);
			break;
		case -1:
			mDownloadButton.setText(getString(R.string.no_download_click_download));
			Toast.makeText(getApplicationContext(), getString(R.string.file_not_find_download), Toast.LENGTH_SHORT).show();
			Request request = new Request(Uri.parse(UniversalUtil.DOWLOAD_URL));
			Log.e("mingguo", "package name  "+UniversalUtil.getAppPackageName(DownloadAppActivity.this));
			//request.setPackageName(UniversalUtil.getAppPackageName(DownloadAppActivity.this));
			request.setShowRunningNotification(false);
			request.setMimeType("application/vnd.android.package-archive");
			long id = mDownloadManager.enqueue(request);
			break;
		default:
			break;
		}
	}
	
	public class PackageInstallerReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
        	//PackageManager manager = context.getPackageManager();
        	Log.e("mingguo", "onReceiver  ");
        	if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED) || intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)){
        		String packageName = intent.getData().getSchemeSpecificPart();
        		Log.e("mingguo", "package name "+packageName);
        		if (packageName != null && packageName.equals(UniversalUtil.getAppPackageName(DownloadAppActivity.this))){
        			mDownloadManager.remove(mCurrentDownloadId);
        			new File(UniversalUtil.getDefaultDownloadPath(UniversalUtil.DOWLOAD_URL)).delete();
        			finish();
        		}
        	}
        }
	}
	
	private void registerReceiverData(){
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_MEDIA_MOUNTED);
		intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
		intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
		intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		intentFilter.addDataScheme("package"); 
		 
		this.registerReceiver(mInstallerReceiver, intentFilter);
	}
	
	private void unregisterReceiver(){
		this.unregisterReceiver(mInstallerReceiver);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver();
		super.onDestroy();
	}
	
	

}
