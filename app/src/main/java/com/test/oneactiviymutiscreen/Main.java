package com.test.oneactiviymutiscreen;

import com.test.oneactiviymutiscreen.skindb.SkinOperation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener {

	public static final String KEY_SKIN ="skin";
	public static final String SKIN_OPTION ="skin_option";
	
	public static SharedPreferences mSkinpPreferences;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.main);
		Button btnAlarm = (Button) findViewById(R.id.alarm_screen);
		Button btnSetup = (Button) findViewById(R.id.setup_screen);
		Button btnChangerSkin = (Button)findViewById(R.id.changeSkin_screen);
		btnAlarm.setOnClickListener(this);
		btnSetup.setOnClickListener(this);
		btnChangerSkin.setOnClickListener(this);
		
		mSkinpPreferences = getSharedPreferences(SKIN_OPTION, MODE_WORLD_WRITEABLE);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		if (v.getId() == R.id.alarm_screen) {
//			intent = new Intent(this, Alarm.class);
			startActivity(intent);
			
		} else if (v.getId() == R.id.setup_screen) {
			intent = new Intent(this, Setting.class);
			startActivity(intent);
			
		} else if (v.getId() == R.id.changeSkin_screen) {
			
			SkinOperation.getInstance(this);
			if (mSkinpPreferences.getInt(KEY_SKIN, 0) == 0) {
				mSkinpPreferences.edit().putInt(KEY_SKIN, 1).commit();
				Toast.makeText(this, "Changed to skin:WHITE", Toast.LENGTH_LONG).show();
			} else {
				mSkinpPreferences.edit().putInt(KEY_SKIN, 0).commit();
				Toast.makeText(this, "Changed to skin:BLACK", Toast.LENGTH_LONG).show();
			}
		}
	}
}
