package com.test.oneactiviymutiscreen.skindb;

import com.test.oneactiviymutiscreen.skindb.SkinOperation.OnSkinChangedListener;

public interface SkinChangerController {
	public void add(OnSkinChangedListener listener);
	public void delete(OnSkinChangedListener listener);  
	public void notifyListeners();  
}
