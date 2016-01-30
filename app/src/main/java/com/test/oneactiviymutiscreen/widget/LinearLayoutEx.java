package com.test.oneactiviymutiscreen.widget;

import com.test.oneactiviymutiscreen.R;
import com.test.oneactiviymutiscreen.skindb.SkinOperation;
import com.test.oneactiviymutiscreen.skindb.SkinOperation.OnSkinChangedListener;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

public class LinearLayoutEx extends LinearLayout implements OnSkinChangedListener {
		
	private static final String TAG = LinearLayoutEx.class.getSimpleName();
	/** Context of LinearLayoutEx */
	private Context mContext;
	/** Background image name */
	private String mGetImgName;
	/** the Pressed image */
	private String mImgPressed;
	/** Background color name */
	private String mBgColorName;
	/** Control skin changes */
	protected SkinOperation mOperation;

	/**
	 * Constructor
	 * 
	 * @param context
	 */
	public LinearLayoutEx(Context context) {
		super(context);
	}

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param attrs
	 */
	public LinearLayoutEx(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LinearLayoutEx(Context context, AttributeSet attrs, int defStyle) {
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
		if (null != mImgPressed) {
			setBackgroundDrawable(mContext);
		} else {
			super.setBackgroundDrawable(d);
		}
	}

	@Override
	public void setBackgroundColor(int color) {
		color = mOperation.getColorId(color);
		super.setBackgroundColor(color);
	}

	@Override
	public void onSkinChanged() {
		if (null != mImgPressed) {
			setBackgroundDrawable(mContext);
		} else {
			setBackgroundResource(mGetImgName);
			if (null == mGetImgName) {
				setBackgroundColor(mBgColorName);
			}
		}
	}

	/**
	 * Get the custom property of LinearLayout
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attributeSet
	 */
	private void getCustomProperty(Context context, AttributeSet attrs) {
		Log.d(TAG, "getCustomProperty");
		
		mContext = context;
		TypedArray attr = this.getContext().obtainStyledAttributes(attrs,R.styleable.LinearLayoutEx);
		mGetImgName = attr.getString(R.styleable.LinearLayoutEx_skin_img);
		mImgPressed = attr.getString(R.styleable.LinearLayoutEx_img_pressed);
		mBgColorName = attr.getString(R.styleable.LinearLayoutEx_bg_color);
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
	 * Set the background to a given Drawable
	 * 
	 * @param context
	 *            Context of LinearLayoutEx
	 */
	private void setBackgroundDrawable(Context context) {
		Drawable drawable = mOperation.getStateListDrawable(context,
				mGetImgName, mImgPressed);
		super.setBackgroundDrawable(drawable);
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
}
