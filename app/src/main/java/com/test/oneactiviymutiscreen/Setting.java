package com.test.oneactiviymutiscreen;

import java.lang.ref.WeakReference;
import java.util.Stack;

import com.test.oneactiviymutiscreen.widget.LinearLayoutEx;
import com.test.oneactiviymutiscreen.widget.NavigationBar;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;

public class Setting extends Activity {

	private static final String TAG = Setting.class.getSimpleName();
	private SettingViewBase mCurrentView;
	private LayoutInflater mFaInflater;
	private NavigationBar mTitleBar;
	
	public static SharedPreferences mSkinpPreferences;

	// private ViewChanger mViewChanger = new Setting().new ViewChanger();方式1
	// 实例化成员内部类的标准方式为方式1，下面的方式一般是实例化静态成员内部类的写法。
	// 至于为什么使用该方式实例化mViewChanger不会报错，需要调查。
	private ViewChanger mViewChanger = new Setting.ViewChanger();
	private LinearLayoutEx mSetupContent;

	public enum SetttingViews {
		VIEW_NONE, VIEW_START_SETTING, VIEW_SETTING_LIST, VIEW_ABOUT;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_main);
		mFaInflater = this.getLayoutInflater();
		mTitleBar = (NavigationBar) findViewById(R.id.navigation_bar);
		mTitleBar.initialize();
		mSetupContent = (LinearLayoutEx) findViewById(R.id.setup_content);
		mViewChanger.showNextView(SetttingViews.VIEW_START_SETTING);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return super.onKeyUp(keyCode, event);
	}

//	public void sendMessage(View view) {
//		Log.d(TAG, "sendMessage");
//		mTitleBar.sendMessage(view);
//	}

	public static abstract class SettingViewBase extends LinearLayoutEx {

		private WeakReference<ViewChanger> mViewChanger;

		public SettingViewBase(Context context) {
			super(context);
		}

		public SettingViewBase(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		private void setViewChanger(ViewChanger viewChanger) {
			mViewChanger = new WeakReference<Setting.ViewChanger>(viewChanger);
		}

		private void initializes() {
			Log.d(TAG, "initializes");
			initialize();
		}

		public abstract void initialize();

		public abstract int getTitle();

		public abstract String getTitleVariable();

		public abstract boolean isReturnButtonVisibility();

		public abstract boolean isNextButtonVisibility();

		public abstract int getTextofNextButton();

		public abstract int getTextofReturnButton();

		public abstract void nextButtonClicked();

		public abstract void retrunButtonClicked();

		protected void showNextView(SetttingViews view) {
			ViewChanger changer = mViewChanger.get();
			if (null != changer) {
				changer.showNextView(view);
			}
		}

		public void showPreviousView() {
			ViewChanger changer = mViewChanger.get();
			if (null != changer) {
				changer.showPreviousView();
			}
		}
	}

	//有一个地方需要注意，调用showNextView push到栈中的数据为上一个画面对应枚举值。
	//这样当调用showPreviousView的时候，才能返回上一个画面对应的枚举值。
	//进入到第一个画面的时候，我们push到栈中数据为VIEW_NONE.
	public class ViewChanger {

		private Stack<SetttingViews> mStack = new Stack<SetttingViews>();

		// 当前画面对应的枚举类型的值。
		private SetttingViews mCurrent = SetttingViews.VIEW_NONE;

		private ViewChanger() {
//			 mStack.push(SetttingViews.VIEW_NONE);
		}

		void close() {
			mStack.clear();
		}

		SetttingViews getCurrent() {
			return mCurrent;
		}

		void showPreviousView() {
			if (null != mStack && !mStack.isEmpty()) {
				SetttingViews view = mStack.pop();
				showView(view);
			}
		}

		void showNextView(SetttingViews view) {
			Log.d(TAG, "showNextView");
			if (null != mStack) {
				mStack.push(mCurrent);
				showView(view);
			}
		}

		void showView(SetttingViews view) {
			Log.d(TAG, "showView");
			// 该if语句的逻辑处理当使用物理返回键从setting主画面返回其他画面时
			// 的处理。1：清空栈。2：关闭当前的activity。
			if (view == SetttingViews.VIEW_NONE) {
				mStack.clear();
				mCurrent = SetttingViews.VIEW_NONE;
				finish();
				return;
			}
			if (view == SetttingViews.VIEW_START_SETTING) {
				if (mCurrent == SetttingViews.VIEW_NONE) {
					view = SetttingViews.VIEW_SETTING_LIST;
				} else {
					view = mCurrent;
				}
			}

			//如果mcurret和将要显示的view是相同的，应该进行pop操作，
			if (mCurrent == view) {
				mStack.pop();
			}

			int layoutId = 0;

			switch (view) {
			case VIEW_SETTING_LIST:
				layoutId = R.layout.setup_list;
				Log.d(TAG, "VIEW_SETTING_LIST");
				break;
			case VIEW_ABOUT:
				layoutId = R.layout.setup_about;
				Log.d(TAG, "VIEW_ABOUT");
				break;

			default:
				throw new IllegalArgumentException();
			}

			mSetupContent.removeAllViews();
//			1. 如果root为null，attachToRoot将失去作用，设置任何值都没有意义。
//			2. 如果root不为null，attachToRoot设为true，则会在加载的布局文件的最外层再嵌套一层root布局。
//			3. 如果root不为null，attachToRoot设为false，则root参数失去作用。
//			4. 在不设置attachToRoot参数的情况下，如果root不为null，attachToRoot参数默认为true。
			mCurrentView = (SettingViewBase) mFaInflater.inflate(layoutId,mSetupContent, false);
			mCurrentView.initializes();
			mCurrentView.setViewChanger(this);
			mTitleBar.setSetupView(mCurrentView);
			mSetupContent.addView(mCurrentView);
			mCurrent = view;
		}
	}



}
