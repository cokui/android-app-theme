package com.test.oneactiviymutiscreen.widget;

import com.test.oneactiviymutiscreen.R;
import com.test.oneactiviymutiscreen.skindb.SkinOperation;
import com.test.oneactiviymutiscreen.skindb.SkinOperation.OnSkinChangedListener;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

public class ButtonEx extends Button implements OnSkinChangedListener {

	private String imageName;
	private String bgColorName;
	private String textColorName;
	private SkinOperation mOperation;

	public ButtonEx(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ButtonEx(Context context) {
		super(context);
	}

	public ButtonEx(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		getCustomProperty(context, attrs);
	}

	@Override
	public void setBackgroundResource(int resId) {
		resId = mOperation.getResId(resId);
		super.setBackgroundResource(resId);
	}

	@Override
	public void setBackgroundDrawable(Drawable d) {
		super.setBackgroundDrawable(d);
	}

	@Override
	public void setBackgroundColor(int color) {
		color = mOperation.getColorId(color);
		super.setBackgroundColor(color);
	}

	@Override
	public void setTextColor(int color) {
		color = mOperation.getColorId(color);
		super.setTextColor(color);
	}

	private void getCustomProperty(Context context, AttributeSet attrs) {
		TypedArray attr = this.getContext().obtainStyledAttributes(attrs,R.styleable.ViewEX);
		imageName = attr.getString(R.styleable.ViewEX_skin_img);
		bgColorName = attr.getString(R.styleable.ViewEX_bg_color);
		textColorName = attr.getString(R.styleable.ViewEX_text_color);
		attr.recycle();
		mOperation = SkinOperation.getInstance(context);
		mOperation.add(this);
		setBackgroundReSource(imageName);
		setBackgroundColor(bgColorName);
		setTextColor(textColorName);
	}

	private void setBackgroundReSource(String imageName) {
		if (null != imageName) {
			int resourceId = mOperation.getResId(imageName);
			super.setBackgroundResource(resourceId);
		}
	}

	private void setBackgroundColor(String bgColorName) {
		if (null != bgColorName) {
			int resid = mOperation.getColorId(bgColorName);
			super.setBackgroundColor(resid);
		}
	}

	private void setTextColor(String textColorName) {
		if (null != textColorName) {
			int resid = mOperation.getColorId(textColorName);
			super.setTextColor(resid);
		}
	}

	@Override
	public void onSkinChanged() {
		setBackgroundReSource(imageName);
		if (null == imageName) {
			setBackgroundColor(bgColorName);
		}
		setTextColor(textColorName);

	}
}
