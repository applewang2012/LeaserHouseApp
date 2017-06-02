package landlord.guardts.house.util;

import java.io.File;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import android.content.Context;
import android.content.Intent;
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
import android.net.Uri;
import android.os.Environment;
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
	
	public static int getAppVersionCode(Context context) {  
	    //String versionName;
		int versioncode = 0;  
	    try {  
	        // ---get the package info---  
	        PackageManager pm = context.getPackageManager();  
	        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);  
	        //versionName = pi.versionName;  
	        versioncode = pi.versionCode;
//	        if (versionName == null || versionName.length() <= 0) {  
//	            return "";  
//	        }  
	    } catch (Exception e) {  
	        Log.e("VersionInfo", "Exception", e);  
	    }  
	    return versioncode;  
	}  
	
	public static String getAppPackageName(Context context) {    
		return context.getPackageName();
    }  
	
public static int playPosition=-1;
    
    private static  Canvas canvas;
    
    public static final String DOWLOAD_URL = "http://app.znds.com/down/20170427/dangbeimarket_3.9.9.0_115_znds_0425_74934f3.apk";

    public static Canvas getCanvas() {
        return canvas;
    }

    
    public static String getDefaultDownloadPath(String downloadUrl){
    	String path = null;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // ���ڻ�ȡ�ⲿ�ļ�·��
            File root = Environment.getExternalStorageDirectory();
            File base = new File(root.getPath() + "/download");
        	if (!base.isDirectory() && !base.mkdir()) {
        		return null;
        	}else{
        		File[] files = base.listFiles();
        		for (int i = 0; i < files.length; i++) {
        			String filename = files[i].getName();
        			if (filename != null && downloadUrl != null){
        				if (downloadUrl.endsWith(filename)){
        					path = base.getPath()+File.separator+filename;
        					break;
        				}
        			}
        		}
        	}
        } else {
            // �����ڻ�ȡ�ڲ���
            return null;
        }
       return path;
    }
    
    
    /* ��װapk */    
    public static void installApk(Context context, String fileName) {    
        Intent intent = new Intent();    
        intent.setAction(Intent.ACTION_VIEW);    
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    
        intent.setDataAndType(Uri.parse("file://" + fileName),"application/vnd.android.package-archive");    
        context.startActivity(intent);    
    }    
        
    /* ж��apk */    
    public static void uninstallApk(Context context, String packageName) {    
        Uri uri = Uri.parse("package:" + packageName);    
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);    
        context.startActivity(intent);    
    }  
   

}
