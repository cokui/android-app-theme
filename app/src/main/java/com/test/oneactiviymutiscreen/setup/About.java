package com.test.oneactiviymutiscreen.setup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.test.oneactiviymutiscreen.R;
import com.test.oneactiviymutiscreen.Setting.SettingViewBase;

public class About extends SettingViewBase {

	private static final String TAG = About.class.getSimpleName();
	
	public About(Context context) {
		super(context);
	}

	public About(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void initialize() {

	}

	@Override
	public int getTitle() {
		return R.string.setup_about;

	}

	@Override
	public String getTitleVariable() {
		return "About";

	}

	@Override
	public boolean isReturnButtonVisibility() {
		return true;

	}

	@Override
	public boolean isNextButtonVisibility() {
		return false;
	}

	@Override
	public int getTextofNextButton() {
		return R.string.btn_return;

	}

	@Override
	public int getTextofReturnButton() {
		return R.string.btn_return;
	}

	@Override
	public void nextButtonClicked() {
		
	}
	
	@Override
	public void retrunButtonClicked() {
		Log.d(TAG, "retrunButtonClicked");
		showPreviousView();
	}
}
