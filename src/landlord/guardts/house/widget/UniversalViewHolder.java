package landlord.guardts.house.widget;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UniversalViewHolder {
    
	private SparseArray<View> mViewMap;
	private int mPosition;
	private LayoutInflater mLayoutInflater;
	private View mConvertView;
	
	private static final String TAG = "BitmapUtil";
	
	public UniversalViewHolder(LayoutInflater layoutInflater, int position, int layoutResId) {
		mViewMap = new SparseArray<View>();
		mPosition = position;
		mLayoutInflater = layoutInflater;
		mConvertView = mLayoutInflater.inflate(layoutResId, null);
		mConvertView.setTag(this);
	}
	
	public UniversalViewHolder(View itemView) {
		mViewMap = new SparseArray<View>();
		mConvertView = itemView;
		mConvertView.setTag(this);
	}
	       
	public static UniversalViewHolder get(View convertView, int position, int layoutResId, LayoutInflater layoutInflater) {
	 	if (convertView == null) {
	 		return new UniversalViewHolder(layoutInflater, position, layoutResId);
	 	} else {
	 		UniversalViewHolder holder = (UniversalViewHolder) convertView.getTag();
	 		holder.mPosition = position;
	 		return holder;
	 	}
	}
	
	public View getConvertView() {
		return mConvertView;
	}
	
	public View getViewByKey(int key) {
		View view = mViewMap.get(key);
		if (view == null) {
			view = mConvertView.findViewById(key);
			mViewMap.put(key, view);
		}
		return view;
	}
	
	public int getPosition() {
		return mPosition;
	}
	
	public UniversalViewHolder setImage(int viewId, String url) {
		ImageView imageView = (ImageView) getViewByKey(viewId);
		return this;
	}
	
	public UniversalViewHolder setText(int viewId, String content) {
		((TextView) getViewByKey(viewId)).setText(content);
		return this;
	}
	
//	public UniversalViewHolder setImageWithReflection(final int viewId, String url) {
//		ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
//			@Override
//			public void onLoadingStarted(String arg0, View arg1) {}
//			
//			@Override
//			public void onLoadingFailed(String arg0, View arg1, FailReason reason) {
//				Log.e(TAG, "推荐位海报加载失�? reason : " + reason.toString());
//			} 
//			
//			@Override
//			public void onLoadingComplete(String arg0, View arg1, Bitmap bitmap) {
//				ImageView imageView = (ImageView) getViewByKey(viewId);
//				imageView.setImageBitmap(BitmapUtil.getInstance().getReflectBitmap(bitmap));
//			}
//			
//			@Override
//			public void onLoadingCancelled(String arg0, View arg1) {
//			}
//		});
//		return this;
//	}
}
