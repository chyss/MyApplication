package com.chyss.myapplication.widget.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.chyss.myapplication.widget.js.JavaScriptObject;

public class WebView extends BaseActivity
{
	
	private android.webkit.WebView webView;
	private String url = "";
	private String title = "";
	private WebProgress progressBar;
	
	/**
	 * 自定义webview的调用入口
	 * 
	 * @param context
	 * @param url
	 */
	public static void load(Activity context,String url,String title)
	{
		Intent intent = new Intent(context, WebView.class);
		intent.putExtra("url", url);
		intent.putExtra("title", title);
		context.startActivity(intent);
		//context.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
	}
	
	/**
	 * 自定义webview的调用入口,用于notice点击调用
	 *    
	 * @param context
	 * @param url
	 */
	public static void loadFromNotice(Context context,String url,String title)
	{       
		Intent intent = new Intent(context, WebView.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("url", url); 
		intent.putExtra("title", title);
		context.startActivity(intent);
		//context.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.webview_layout);
		addLeftBtn();
		
		if (getIntent() != null) {
			
			url = getIntent().getStringExtra("url");
			title = getIntent().getStringExtra("title");
		}
		setTitle(title);
		initView();
		initWebview();
	}
	
	private void initView() {
		progressBar = (WebProgress) findViewById(R.id.webview_progress);
		
	}

	@SuppressLint({ "SetJavaScriptEnabled", "NewApi", "JavascriptInterface" }) 
	private void initWebview() {
		
		webView = (android.webkit.WebView) findViewById(R.id.webview_def);
		//webview的编码格式
		webView.getSettings().setDefaultTextEncodingName("UTF-8");
		//支持javascript交互
		webView.getSettings().setJavaScriptEnabled(true);
		//设置 缓存模式 ，默认的缓存模式
		webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  
		// 开启 DOM storage API 功能 
		webView.getSettings().setDomStorageEnabled(true); 
		//缩放权限
		webView.getSettings().setSupportZoom(true);
		//android与js交互接口
		webView.addJavascriptInterface(new JavaScriptObject(WebView.this), JavaScriptObject.obj);
		//告诉WebView先不要自动加载图片，等页面finish后再发起图片加载。
		if(Build.VERSION.SDK_INT >= 19) {
	        webView.getSettings().setLoadsImagesAutomatically(true);
	    } else {
	        webView.getSettings().setLoadsImagesAutomatically(false);
	    }
		
		webView.setWebChromeClient(new MyWebChromeClient());
		webView.setWebViewClient(new MyWebViewClient());
		
		webView.loadUrl(url);
	}
	
	/**
	 * WebViewClient会在一些影响内容渲染的动作发生时被调用，
	 * 比如表单的错误提交需要重新提交、页面开始加载及加载完成、资源加载中、接收到https认证需要处理、页面键盘响应、页面中的url打开处理等等
	 */
	class MyWebViewClient extends WebViewClient
	{
		//开始加载页面调用
		@Override
		public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
			//Toast.makeText(WebView.this, "开始加载页面", 2000).show();
			
			progressBar.setVisibility(View.VISIBLE);
			super.onPageStarted(view, url, favicon);
		}
		
		//页面加载完成调用
		@Override
		public void onPageFinished(android.webkit.WebView view, String url) {
			//Toast.makeText(WebView.this, "页面加载完成", 1000).show();
			//告诉WebView先不要自动加载图片，等页面finish后再发起图片加载。
//			if(!webView.getSettings().getLoadsImagesAutomatically()) {
//		        webView.getSettings().setLoadsImagesAutomatically(true);
//		    }
			//progressBar.setVisibility(View.GONE);
		}
		
		//加载页面失败时处理
		@Override
		public void onReceivedError(android.webkit.WebView view, int errorCode,
									String description, String failingUrl) {
			
			super.onReceivedError(view, errorCode, description, failingUrl);
			
			//失败处理
			progressBar.setVisibility(View.GONE);
		}
		
		/*
		 * 处理https请求，为WebView处理ssl证书设置,WebView默认是不处理https请求的，页面显示空白
		 */
		@Override
		public void onReceivedSslError(android.webkit.WebView view, SslErrorHandler handler,
									   SslError error) {
			
			handler.proceed(); // 接受信任所有网站的证书
		}
		
		/*
		 * 复写shouldOverideUrlLoading方法可以使用自己定义的webview加载html页面，否则为拉起本地浏览器加载。
		 * 重写后return true表示让当前程序处理，return false表示让当前webView处理
		 * 
		 * 设置为false可以有效解决打开多个web后由于重定向而无法回退的问题
		 * while return false means the current WebView handles the url.
		 */
		@Override
		public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
			//view.loadUrl(url);
			return false;
		}
	}
	
	/**
	 * WebChromeClient会在一些影响浏览器ui交互动作发生时被调用，
	 * 比如WebView关闭和隐藏、页面加载进展、js确认框和警告框、js加载前、js操作超时、webView获得焦点等等
	 */
	class MyWebChromeClient extends WebChromeClient
	{
		//页面加载进度设置
		@Override
		public void onProgressChanged(android.webkit.WebView view, int newProgress) {
			
			progressBar.setProg(newProgress);
		}
	}

	@Override
	public void leftBtnClick() {

		if (webView.canGoBack()) {  
			//progressBar.setVisibility(View.GONE);
			progressBar.currentPoint = 0;
	        webView.goBack();  
	        return;  
	    } 
		finish();
	}
	
	/*
	 * Activity默认的back键处理为结束当前Activity，WebView查看了很多网页后，希望按back键返回上一次浏览的页面，
	 * 这个时候我们就需要覆盖WebView所在Activity的onKeyDown函数，告诉他如何处理
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (webView.canGoBack() && event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
			//progressBar.setVisibility(View.GONE);
			progressBar.currentPoint = 0;
	        webView.goBack();  
	        return true;  
	    }  
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
		progressBar.cancelAnim();
		super.onDestroy();
	}
}
