package landlord.guardts.house.widget;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class UniversalAdapter<T> extends BaseAdapter {

	private int mLayoutResId;
	private List<T> mDatas;
	private LayoutInflater mLayoutInflater;
	
	public UniversalAdapter(Context context, int layoutResId, List<T> datas) {
		this.mDatas = datas;
		this.mLayoutResId = layoutResId;
		this.mLayoutInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return mDatas != null ? mDatas.size() : 0;
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		UniversalViewHolder holder = UniversalViewHolder.get(convertView, position, mLayoutResId, mLayoutInflater);
		convert(holder, mDatas.get(position));
		return holder.getConvertView();
	}
	
	
	public void updateDatas(List<T> datas) {
		mDatas = datas;
		notifyDataSetChanged();	
	}
	
	public abstract void convert(UniversalViewHolder holder, T info); 
}
