package landlord.guardts.house.util;

import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.widget.ImageView;

/**
 * util for url convert to qrcode, use google zxing jar
 * 
 *
 * @author
 */

public class UniversalUtil {

	private static int QR_WIDTH = 480; // pixel
	private static int QR_HEIGHT = 480; // pixel

	public static Bitmap createQRImage(String url, ImageView imgQrd, int nWidth, int nHeight) {
		QR_WIDTH = nWidth;
		QR_HEIGHT = nHeight;

		return createQRImage(url, imgQrd);
	}

	public static Bitmap createQRImage(String url, ImageView imgQrd) {
		Bitmap bitmap = null;
		try {
			if (url == null || "".equals(url) || url.length() < 1) {
				return null;
			}
			
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			
			for (int y = 0; y < QR_HEIGHT; y++) {
				for (int x = 0; x < QR_WIDTH; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * QR_WIDTH + x] = 0xff000000;
					} else {
						pixels[y * QR_WIDTH + x] = 0xffffffff;
					}
				}
			}
			
			bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
			bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
			imgQrd.setImageBitmap(bitmap);
		} catch (WriterException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
	
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		 try {
		  Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
		    bitmap.getHeight(), Config.ARGB_8888);
		  Canvas canvas = new Canvas(output);    
		  final Paint paint = new Paint();
		  final Rect rect = new Rect(0, 0, bitmap.getWidth(),
		    bitmap.getHeight());  
		  final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
		    bitmap.getHeight()));
		  final float roundPx = 14;
		  paint.setAntiAlias(true);
		  canvas.drawARGB(0, 0, 0, 0);
		  paint.setColor(Color.BLACK);  
		  canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		  paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		  final Rect src = new Rect(0, 0, bitmap.getWidth(),
		    bitmap.getHeight());
		  canvas.drawBitmap(bitmap, src, rect, paint); 
		  return output;
		 } catch (Exception e) {  
		  return bitmap;
		 }
	}
	
	public static String getAppVersionName(Context context) {  
	    String versionName = "";  
	    try {  
	        // ---get the package info---  
	        PackageManager pm = context.getPackageManager();  
	        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);  
	        versionName = pi.versionName;  
	        //versioncode = pi.versionCode;
	        if (versionName == null || versionName.length() <= 0) {  
	            return "";  
	        }  
	    } catch (Exception e) {  
	        Log.e("VersionInfo", "Exception", e);  
	    }  
	    return versionName;  
	}  
	
    
   

}
