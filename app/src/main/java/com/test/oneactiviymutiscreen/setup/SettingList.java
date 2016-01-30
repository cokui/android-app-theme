package com.test.oneactiviymutiscreen.setup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.test.oneactiviymutiscreen.R;
import com.test.oneactiviymutiscreen.Setting.SettingViewBase;
import com.test.oneactiviymutiscreen.Setting.SetttingViews;
import com.test.oneactiviymutiscreen.widget.LinearLayoutEx;
import com.test.oneactiviymutiscreen.widget.TextViewEx;

public class SettingList extends SettingViewBase implements OnClickListener {

	private LinearLayoutEx mItems;

	private LayoutInflater mInflater;

	public SettingList(Context context) {
		super(context);
	}

	public SettingList(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void initialize() {
		mInflater = LayoutInflater.from(getContext());
		mItems = (LinearLayoutEx) findViewById(R.id.setup_items);
		createTextArrowView(R.string.setup_about, SetttingViews.VIEW_ABOUT,true);
	}

	@Override
	public int getTitle() {
		return R.string.setup_list;
	}

	@Override
	public String getTitleVariable() {
		return "SETUP";
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
		return R.string.setup_about;
	}

	@Override
	public int getTextofReturnButton() {
		return R.string.setup_about;
	}

	@Override
	public void nextButtonClicked() {

	}

	@Override
	public void retrunButtonClicked() {

	}

	private void createTextArrowView(int text, SetttingViews next,boolean isVisible) {
		View layout = mInflater.inflate(R.layout.setup_item_text_arrow, mItems,false);
		layout.setTag(next);
		layout.setOnClickListener(this);

		TextViewEx item = (TextViewEx) layout.findViewById(R.id.text);
		item.setText(text);
		TextViewEx itemoption = (TextViewEx) layout.findViewById(R.id.text_option);

		if (!isVisible) {
			itemoption.setVisibility(View.GONE);
		}
		mItems.addView(layout);
	}

	@Override
	public void onClick(View v) {
		Object object = v.getTag();
		if (null != object && object instanceof SetttingViews) {
			SetttingViews next = (SetttingViews) object;
			showNextView(next);
		}

	}
}
