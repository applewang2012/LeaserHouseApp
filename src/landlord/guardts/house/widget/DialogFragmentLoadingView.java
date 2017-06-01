package landlord.guardts.house.widget;

import java.lang.reflect.Field;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import landlord.guardts.house.R;

public class DialogFragmentLoadingView extends DialogFragment {
    
	private ImageView mLoadingCircle;
	private static int mStyle;
	public static DialogFragmentLoadingView newInstance(int style) {
		DialogFragmentLoadingView dialog = new DialogFragmentLoadingView();
		Bundle bundle = new Bundle();
		dialog.setArguments(bundle);
		mStyle = style;
		return dialog;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_loading_dialog, container, false);
		mLoadingCircle = (ImageView) view.findViewById(R.id.id_progressbar_img);
		if (mLoadingCircle != null) {
		    mLoadingCircle.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_rotate));
		}
		return view;
	}
	
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

        Dialog dialog = new Dialog(getActivity(),mStyle);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
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
