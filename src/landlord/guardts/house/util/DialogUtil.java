package landlord.guardts.house.util;

import android.app.Activity;
import android.app.DialogFragment;
import landlord.guardts.house.LoginUserActivity;
import landlord.guardts.house.R;
import landlord.guardts.house.widget.DialogFragmentLoadingView;

public class DialogUtil{
	private static DialogFragmentLoadingView mLoadingDialog;
	
	
	public static void showLoadingDialog(Activity activity) {
		mLoadingDialog = DialogFragmentLoadingView.newInstance(R.style.loading_style);
//		mLoadingDialog.setStyle(DialogFragment.STYLE_NORMAL,
//				R.style.loading_style);
		mLoadingDialog.show(activity.getFragmentManager(), "");
	}

	public static void closeLoadingDialog() {
		if (mLoadingDialog != null) {
			mLoadingDialog.dismiss();
		}
	}
	
	public static void showLoadingView(Activity activity){
		
		DialogUtil.showLoadingDialog(activity);
		
//		if (mLoadingView != null) {
//			mLoadingView.setVisibility(View.VISIBLE);
//        	ImageView imageView = (ImageView) mLoadingView.findViewById(R.id.id_progressbar_img);
//        	if (imageView != null) {
//        		RotateAnimation rotate = (RotateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
//        		imageView.startAnimation(rotate);
//        	}
//		}
	}
	public static void dismissLoadingView(){
		DialogUtil.closeLoadingDialog();
//		if (mLoadingView != null) {
//			mLoadingView.setVisibility(View.INVISIBLE);
//		}
	}
	
}
