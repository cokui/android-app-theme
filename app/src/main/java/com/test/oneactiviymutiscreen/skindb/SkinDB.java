package com.test.oneactiviymutiscreen.skindb;

import com.test.oneactiviymutiscreen.R;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;

public class SkinDB {

	private static final int SKIN1 = 0;
	private static final int SKIN2 = 1;

	private String[] mKeyTBL;
	private TypedArray mImage1TBL;
	private TypedArray mImage2TBL;

	private SkinInfo[] mImageDB;
	private int mImageCount;

	private String[] mColorkeyTBL;
	private int[] mColor1TBL;
	private int[] mColor2TBL;

	private SkinInfo[] mColorDB;
	private int mColorCount;

	private int mLoadSkinMode;

	private static SkinDB mSkinDBInstance;

	private SkinDB(Context context) {
		loadResourceId(context);

		mImageCount = mKeyTBL.length;
		mImageDB = new SkinInfo[mImageCount];

		mColorCount = mColorkeyTBL.length;
		mColorDB = new SkinInfo[mColorCount];

		for (int i = 0; i < mImageCount; i++) {
			mImageDB[i] = new SkinInfo(mKeyTBL[i], mImage1TBL.getResourceId(i,0), mImage2TBL.getResourceId(i, 0));
		}

		for (int i = 0; i < mColorCount; i++) {
			mColorDB[i] = new SkinInfo(mColorkeyTBL[i], mColor1TBL[i],mColor2TBL[i]);
		}
		mLoadSkinMode = SKIN1;
	}

	public static SkinDB getInstance(Context context) {
		if (null == mSkinDBInstance) {
			mSkinDBInstance = new SkinDB(context);
		}
		return mSkinDBInstance;
	}

	private void loadResourceId(Context context) {
		Resources resources = context.getResources();
		mKeyTBL = resources.getStringArray(R.array.image_key);
		mImage1TBL = resources.obtainTypedArray(R.array.skin1_image);
		mImage2TBL = resources.obtainTypedArray(R.array.skin2_image);

		mColorkeyTBL = resources.getStringArray(R.array.color_key);
		mColor1TBL = resources.getIntArray(R.array.skin1_color);
		mColor2TBL = resources.getIntArray(R.array.skin2_color);
	}

	int getResId(String inImageName) {
		int outResId = 0;
		
		for (int i = 0; i < mImageCount && outResId == 0; i++) {
			if (null != inImageName && inImageName.equals(mImageDB[i].getKey())) {

				switch (mLoadSkinMode) {
				case SKIN1:
					outResId = mImageDB[i].getSkin1();
					break;
				case SKIN2:
					outResId = mImageDB[i].getSkin2();
					break;
				default:
					break;
				}
			}
		}
		return outResId;
	}

	int getColorId(String inColorName) {
		int outColorId = 0;

		for (int i = 0; i < mColorCount && outColorId == 0; i++) {

			if (null != inColorName && inColorName.equals(mColorDB[i].getKey())) {

				switch (mLoadSkinMode) {
				case SKIN1:
					outColorId = mColorDB[i].getSkin1();
					break;
				case SKIN2:
					outColorId = mColorDB[i].getSkin2();
					break;
				default:
					break;
				}
			}
		}
		return outColorId;
	}

	int getResId(int inResId) {
		int outResId = inResId;

		for (int i = 0; i < mImageCount; i++) {

			if (inResId == mImageDB[i].getSkin1()) {

				switch (mLoadSkinMode) {
				case SKIN1:
					outResId = mImageDB[i].getSkin1();
					break;
				case SKIN2:
					outResId = mImageDB[i].getSkin2();
					break;
				default:
					break;
				}
			}
		}
		return outResId;
	}

	int getColorId(int inColorId) {
		int outColorId = inColorId;

		for (int i = 0; i < mImageCount; i++) {

			if (inColorId == mImageDB[i].getSkin1()) {

				switch (mLoadSkinMode) {
				case SKIN1:
					outColorId = mImageDB[i].getSkin1();
					break;
				case SKIN2:
					outColorId = mImageDB[i].getSkin2();
					break;
				default:
					break;
				}
			}
		}
		return outColorId;
	}
	
	
	LayerDrawable getLayerDrawable(Context context, String normalImage,String pressedImage) {
		int nomalImg = getResId(normalImage);
		int pressedImg = getResId(pressedImage);
		
        Drawable background = context.getResources().getDrawable(nomalImg);
        Drawable progress = context.getResources().getDrawable(pressedImg);
        ClipDrawable clipProgress = new ClipDrawable(progress, Gravity.LEFT, ClipDrawable.HORIZONTAL);
        
        LayerDrawable layerlist = new LayerDrawable(new Drawable[] { background, clipProgress });
        layerlist.setId(0, android.R.id.background);
        layerlist.setId(1, android.R.id.progress);
		return layerlist;
	}
	
	
    StateListDrawable getStateListDrawable(Context context, String normalImage,String pressedImage) {
		int nomalImg = getResId(normalImage);
		int pressedImg = getResId(pressedImage);
		
		StateListDrawable bg = new StateListDrawable();
		Drawable normal = nomalImg == -1 ? null : context.getResources().getDrawable(nomalImg);
		Drawable pressed = pressedImg == -1 ? null : context.getResources().getDrawable(pressedImg);
		// View.PRESSED_ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_pressed,android.R.attr.state_enabled }, pressed);
		// View.ENABLED_FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled,android.R.attr.state_focused }, pressed);
		// View.ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled }, normal);
		// View.FOCUSED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_focused }, pressed);
		// View.EMPTY_STATE_SET
		bg.addState(new int[] {}, normal);
    	return null;
    }

	

	private class SkinInfo {

		private String key;
		private int skin1;
		private int skin2;

		public SkinInfo(String key, int skin1, int skin2) {
			super();
			this.key = key;
			this.skin1 = skin1;
			this.skin2 = skin2;
		}

		public String getKey() {
			return key;
		}

		public int getSkin1() {
			return skin1;
		}

		public int getSkin2() {
			return skin2;
		}

	}

	public void changeSkinMode(int int1) {
		
		mLoadSkinMode = int1;
		
	}
}
