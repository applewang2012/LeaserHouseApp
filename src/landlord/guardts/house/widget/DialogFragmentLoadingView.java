package landlord.guardts.house.widget;

import java.lang.reflect.Field;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import landlord.guardts.house.R;

public class DialogFragmentLoadingView extends DialogFragment {
    
	private ImageView mLoadingCircle;
	
	public static DialogFragmentLoadingView newInstance(float msgTextSize) {
		DialogFragmentLoadingView dialog = new DialogFragmentLoadingView();
		Bundle bundle = new Bundle();
		bundle.putFloat("text_size", msgTextSize);
		dialog.setArguments(bundle);
		return dialog;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_loading_dialog, container, false);
		TextView message = (TextView) view.findViewById(R.id.id_message);
		mLoadingCircle = (ImageView) view.findViewById(R.id.id_progressbar_img);
		if (mLoadingCircle != null) {
		    mLoadingCircle.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_rotate));
		}
		return view;
	}
	
	@Override
	public void onStop() {
		if (mLoadingCircle != null) {
			mLoadingCircle.clearAnimation();
		}
		super.onStop();
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		try {
            Field childFragmentManager =  Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
	}
}
