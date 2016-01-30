package com.test.oneactiviymutiscreen.skindb;

import java.util.Vector;

import com.test.oneactiviymutiscreen.Main;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;

public class SkinOperation implements SkinChangerController,OnSharedPreferenceChangeListener {

	private SkinDB mSkinDB;
	private SharedPreferences mPreferences;
	private OnSkinChangedListener mOnSkinChangedlistener = null;

	private static SkinOperation mSkinOperation;
	
	private Vector<OnSkinChangedListener> vector = new Vector<SkinOperation.OnSkinChangedListener>();

	private SkinOperation(Context context) {
		mSkinDB = SkinDB.getInstance(context);
		mPreferences = context.getSharedPreferences(Main.SKIN_OPTION,Context.MODE_WORLD_WRITEABLE);
		mPreferences.registerOnSharedPreferenceChangeListener(this);
	}

	public static SkinOperation getInstance(Context context) {
		if (null == mSkinOperation) {
			mSkinOperation = new SkinOperation(context);
		}
		return mSkinOperation;
	}

	public int getResId(String inImageName) {
		return mSkinDB.getResId(inImageName);
	}

	public int getResId(int inResId) {
		return mSkinDB.getResId(inResId);
	}

	public int getColorId(String inColorName) {
		return mSkinDB.getColorId(inColorName);
	}

	public int getColorId(int inColorId) {
		return mSkinDB.getColorId(inColorId);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (Main.KEY_SKIN.equals(key)) {
			mSkinDB.changeSkinMode(sharedPreferences.getInt(key, 0));
			notifyListeners();
		}
	}

	public StateListDrawable getStateListDrawable(Context context,
			String normalImg, String pressedImg) {
		return mSkinDB.getStateListDrawable(context, normalImg, pressedImg);
	}

	public LayerDrawable getLayerDrawable(Context context, String normalImg,
			String pressedImg) {
		return mSkinDB.getLayerDrawable(context, normalImg, pressedImg);
	}

	public interface OnSkinChangedListener {
		void onSkinChanged();
	}

	@Override
	public void add(OnSkinChangedListener listener) {
		vector.add(listener);
	}

	@Override
	public void delete(OnSkinChangedListener listener) {
		vector.remove(listener);
	}

	@Override
	public void notifyListeners() {
		for (int i = 0; i < vector.size(); i++) {
			OnSkinChangedListener listener = vector.get(i);
			listener.onSkinChanged();
		}
	}
}
