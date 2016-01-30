package com.test.oneactiviymutiscreen.widget;

import com.test.oneactiviymutiscreen.R;
import com.test.oneactiviymutiscreen.skindb.SkinOperation;
import com.test.oneactiviymutiscreen.skindb.SkinOperation.OnSkinChangedListener;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class RelativeLayoutEx extends RelativeLayout implements OnSkinChangedListener {
    
	/** Background image name */
	private String mGetImgName;
	/** Background color name */
	private String mBgColorName;
	private String mImgPressed;
	/** Control skin changes */
	private SkinOperation mOperation;
	
	/**
	 * Constructor
	 * 
	 * @param context
	 */
	public RelativeLayoutEx(Context context) {
		super(context);
	}


	/**
	 * Constructor
	 * 
	 * @param context
	 * @param attrs
	 */
	public RelativeLayoutEx(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public RelativeLayoutEx(Context context, AttributeSet attrs, int defStyle) {
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
	public void onSkinChanged() {
		setBackgroundResource(mGetImgName);
		if( null == mGetImgName) {
			setBackgroundColor(mBgColorName);
		}
	}

	/**
	 * Get the custom property of RelativeLayout
	 * 
	 * @param context
	 *             the context
	 * @param attrs
	 *             the attributeSet
	 */
	private void getCustomProperty(Context context, AttributeSet attrs) {
		// get custom property
		TypedArray attr = this.getContext().obtainStyledAttributes(attrs,R.styleable.ViewEX);
		mGetImgName = attr.getString(R.styleable.ViewEX_skin_img);
		mBgColorName = attr.getString(R.styleable.ViewEX_bg_color);
		mImgPressed = attr.getString(R.styleable.ViewEX_img_pressed);

		mOperation = SkinOperation.getInstance(context);
		mOperation.add(this);
		if (null != mImgPressed) {
			setBackgroundDrawable(context);
		} else {
			setBackgroundResource(mGetImgName);
			if (null == mGetImgName) {
				setBackgroundColor(mBgColorName);
			}
		}
	}

	/**
	 * Set the image name of background
	 * 
	 * @param getImgName
	 *            background image name
	 */
	private void setBackgroundResource(String getImgName) {
		if (null != getImgName) {
		int resid = mOperation.getResId(getImgName);
		super.setBackgroundResource(resid);
		}
	}
	
	/**
	 * Set the color name of background
	 * 
	 * @param bgColorName
	 *            background color name
	 */
	private void setBackgroundColor(String bgColorName) {
		if (null != bgColorName) {
			int resid = mOperation.getColorId(bgColorName);
			super.setBackgroundColor(resid);
		}
	}
	
	private void setBackgroundDrawable(Context context) {
		Drawable drawable = mOperation.getStateListDrawable(context,mGetImgName, mImgPressed);
		super.setBackgroundDrawable(drawable);
	}

}
