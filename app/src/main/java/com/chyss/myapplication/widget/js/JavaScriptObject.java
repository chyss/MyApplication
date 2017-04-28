package com.chyss.myapplication.widget.js;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.chyss.myapplication.R;
import com.chyss.myapplication.widget.dialog.CenterPopDialog;

/**
 * JS对应的对象类，用于处理js调用android的方法，方法需标示@JavascriptInterface，否则不能调用到；
 * webview中添加：
 * webView.addJavascriptInterface(new JavaScriptObject(WebViewDef.this), JavaScriptObject.obj);
 * js中调用：
 * javaObj.funFromAndroid();
 * 
 * @author chyss 2016.8.1
 * 
 */
public class JavaScriptObject
{
	public static final String obj = "javaObj";   //统一的类名
	Context mContxt;

	public JavaScriptObject(Context mContxt)
	{
		this.mContxt = mContxt;
	}

	@JavascriptInterface
	public void funFromAndroid()
	{
		Toast.makeText(mContxt, "I am android！", Toast.LENGTH_LONG).show();
	}
	
	@JavascriptInterface
	public void openDialog()
	{
		CenterPopDialog dialog = new CenterPopDialog(mContxt,"标题","人生若只如初见！",2,"cancel","ok")
		{
			@Override
			public void left()
			{
				dismiss();
			}

			@Override
			public void right()
			{
				dismiss();
			}
		};
		dialog.show();
	}
}
