package com.lols.diaf;

import android.util.Log;

//import com.pof.data.SystemMessageDbAdapter;

public  class Logger {
	
	//***Don't change this*** it's now set on app startup based on the debuggable flag in the manifest
	private static Boolean showlog = false;
	
	public static void setEnabled(Boolean enabled) {
		showlog = enabled;
	}

	public static void v(String tag, String message)
	{
		if (showlog)
		{
			Log.v(tag, message);
			
		}
	}
	public static void w(String tag, String message)
	{
		if (showlog)
		{
			Log.w(tag, message);
		}
	}
	public static void i(String tag, String message)
	{
		if (showlog)
		{
	
			Log.v(tag, message);
		}
	}
}