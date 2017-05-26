/*
 * Copyright (C) 2010 mAPPn.Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package landlord.guardts.house.view;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import landlord.guardts.house.R;



/**
 * Loading Animation Drawable for mAPPn android project.
 *  
 * @author andrew.wang
 * @version 0.0.2
 */
public class LoadingDrawable extends AnimationDrawable {

    /**
     * LoadingDrawable style -- SMALL
     * 
     * (width: 54dp, height: 12dp)
     */
    public static final int SIZE_SMALL = 0;
    
    /**
     * LoadingDrawable style -- MEDIUM
     * 
     * (width: 90dp, height: 20dp)
     */
    public static final int SIZE_MEDIUM = 1;
    
    /**
     * LoadingDrawable style -- LARGE
     * 
     * (width: 180dp, height: 40dp)
     */
    public static final int SIZE_LARGE = 2;
    
    // default animation duration
    private static final int DURATION = 200;
    
    private static final int WIDTH_SMALL = 54;
    private static final int HEIGHT_SMALL = 12;
    private static final int WIDTH_MEDIUM = 90;
    private static final int HEIGHT_MEDIUM = 20;
    private static final int WIDTH_LARGE = 180;
    private static final int HEIGHT_LARGE = 40;
    
    private static final int BLOCK_NUMBER = 4;
    
    // highlight color
    private int color1;
    
    // default color
    private int color2;
    
    // the default loading style is small
    private int mLoadingStyle;
    
    // the animation duration
    private int mDuration;
    
    /**
     * The LoadingDrawable with default settings.<br>
     * 1 --> size style is SMALL<br>
     * 2 --> highlight color is "ffff9600"<br>
     * 3 --> default color is "ffcdcdcd"<br>
     * 4 --> background color is "Color.WHITE"<br>
     * <br>
     *  If you want to customize your own style, please reference 
     * <pre>LoadingDrawable(int style, int highlightColor, int defaultColor, int backgroundColor)</pre>
     */
    public LoadingDrawable(Context context) {
        this(context, SIZE_SMALL, 0, 0, DURATION);
    }
    
    /**
     * The LoadingDrawable with customize style.<br>
     * 
     * @param style The size of loading drawable
     * @param highlightColor The higlight color
     * @param defaultColor The default color
     * @param duration The animation duration of each frame
     */
    @SuppressWarnings("deprecation")
	public LoadingDrawable(Context context, int style, int highlightColor, int defaultColor, int duration) {
        
      android.util.Log.e("shijunxing", "LoadingDrawable");
        // init configuration info
        mLoadingStyle = style;
        if (highlightColor == 0) {
            color1 = Color.parseColor("#80ff9600");
            color2 = Color.parseColor("#30000000");
        } else {
            color1 = context.getResources().getColor(highlightColor);
            color2 = context.getResources().getColor(defaultColor);
        }
        
        mDuration = duration;
        final Resources res = context.getResources();
        // repeat the animation
        setOneShot(false);
        
        float space = 0;
        float halfWidth = 0;
        float width = 0;
        float lightHalfWidth = 0;
        int totalWidth = 0;
        int totalHeight = 0;
        
        switch (mLoadingStyle) {
        case SIZE_SMALL:
            space = res.getDimension(R.dimen.space_small);
            halfWidth = space / 2;
            width = space;
            lightHalfWidth = res.getDimension(R.dimen.space_small_h) / 2;
            totalWidth = (int) res.getDimension(R.dimen.width_small);
            totalHeight = (int) res.getDimension(R.dimen.height_small);
            break;
        case SIZE_MEDIUM:
            space = res.getDimension(R.dimen.space_medium);
            halfWidth = space / 2;
            width = space;
            lightHalfWidth = res.getDimension(R.dimen.space_medium_h) / 2;
            totalWidth = (int) res.getDimension(R.dimen.width_medium);
            totalHeight =  (int) res.getDimension(R.dimen.height_medium);
            break;
        case SIZE_LARGE:
            space = res.getDimension(R.dimen.space_large);
            halfWidth = space / 2;
            width = space;
            lightHalfWidth = res.getDimension(R.dimen.space_large_h) / 2;
            totalWidth = (int) res.getDimension(R.dimen.width_large);
            totalHeight = (int) res.getDimension(R.dimen.height_large);
            break;
        }
        
        Paint highlightColorPaint = new Paint();
        highlightColorPaint.setColor(color1);
        highlightColorPaint.setStyle(Style.FILL);
        highlightColorPaint.setAntiAlias(true);
        Paint normalColorPaint = new Paint();
        normalColorPaint.setColor(color2);
        normalColorPaint.setStyle(Style.FILL);
        normalColorPaint.setAntiAlias(true);
        Canvas canvas = new Canvas();
        
        int frameIndex = 0;
        while (frameIndex < 6) {
            Bitmap frame = null;
            try {
                frame = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_4444);
            } catch (OutOfMemoryError error) {
            }
            if (frame == null) {
                continue;
            }
            canvas.setBitmap(frame);
            canvas.drawColor(Color.TRANSPARENT);
            
            int startPositionX = 0;
            int startPositionY = totalHeight / 2;
            int blockIndex = 0;
            int drawIndex = frameIndex < 4 ? frameIndex : (3 - frameIndex % 3);
            while (blockIndex < BLOCK_NUMBER) {
                // try to draw 4 blocks on the bitmap
                float left = 0;
                float top = 0;
                float right = 0;
                float bottom = 0;

                if (blockIndex == 0) {
                    startPositionX += (space + halfWidth);
                } else {
                    startPositionX += (space + width);
                }
                Paint paint;
                if (blockIndex == drawIndex) {
                    // current loading item is bigger
                    left = startPositionX - lightHalfWidth;
                    top = startPositionY - lightHalfWidth;
                    right = startPositionX + lightHalfWidth;
                    bottom = startPositionY + lightHalfWidth;
                    paint = highlightColorPaint;
                } else {
                    // other item is default size
                    left = startPositionX - halfWidth;
                    top = startPositionY - halfWidth;
                    right = startPositionX + halfWidth;
                    bottom = startPositionY + halfWidth;
                    paint = normalColorPaint;
                }
                canvas.drawRect(left, top, right, bottom, paint);
                
                blockIndex++;
            }
            frameIndex++;
            canvas.save();
            addFrame(new BitmapDrawable(frame), mDuration);
        }
    }

    @Override
    public int getMinimumHeight() {
        switch (mLoadingStyle) {
        case SIZE_SMALL:
            return HEIGHT_SMALL;
        case SIZE_MEDIUM:
            return HEIGHT_MEDIUM;
        case SIZE_LARGE:
            return HEIGHT_LARGE;
        default:
            break;
        }
        return super.getMinimumHeight();
    }

    @Override
    public int getMinimumWidth() {
        switch (mLoadingStyle) {
        case SIZE_SMALL:
            return WIDTH_SMALL;
        case SIZE_MEDIUM:
            return WIDTH_MEDIUM;
        case SIZE_LARGE:
            return WIDTH_LARGE;
        default:
            break;
        }
        return super.getMinimumWidth();
    }

}