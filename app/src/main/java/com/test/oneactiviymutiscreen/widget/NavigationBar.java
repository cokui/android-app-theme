package com.test.oneactiviymutiscreen.widget;

import com.test.oneactiviymutiscreen.R;
import com.test.oneactiviymutiscreen.Setting.SettingViewBase;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

public class NavigationBar extends RelativeLayoutEx implements OnClickListener{

	private ButtonEx mleftBtn;
	private ButtonEx mRightBtn;
	private TextViewEx mTitleView;

	private SettingViewBase mCunntentView;

	public NavigationBar(Context context) {
		super(context);
	}

	public NavigationBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void initialize() {
		mleftBtn = (ButtonEx) findViewById(R.id.bar_return);
		mRightBtn = (ButtonEx) findViewById(R.id.bar_next);
		mTitleView = (TextViewEx) findViewById(R.id.titlebar_title);
		
		mleftBtn.setOnClickListener(this);
		mRightBtn.setOnClickListener(this);
		
	}

	public void setSetupView(SettingViewBase view) {

		mCunntentView = view;

		int titleSourceId = view.getTitle();
		if (titleSourceId != 0) {
			mTitleView.setText(titleSourceId);
		} else {
			mTitleView.setText(view.getTitleVariable());
		}

		boolean visibility = view.isReturnButtonVisibility();
		if (visibility) {
			mleftBtn.setVisibility(View.VISIBLE);
			mleftBtn.setText(view.getTextofReturnButton());
		} else {
			mleftBtn.setVisibility(View.INVISIBLE);
		}

		boolean rightBtnvisibility = view.isNextButtonVisibility();
		if (rightBtnvisibility) {
			mRightBtn.setVisibility(View.VISIBLE);
			mRightBtn.setText(view.getTextofNextButton());
		} else {
			mRightBtn.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onClick(View view) {
		
		if (view.equals(mRightBtn)) {
			mCunntentView.nextButtonClicked();
		} else if (view.equals(mleftBtn)) {
			mCunntentView.retrunButtonClicked();
		} else {
			// TODO
		}		
	}
}
