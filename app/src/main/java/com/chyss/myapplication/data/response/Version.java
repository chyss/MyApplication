package com.chyss.myapplication.data.response;

import java.io.Serializable;

public class Version implements Serializable
{

	private static final long serialVersionUID = 10000L;
	
	private int version; // int 是 最新版本号
	private int update; // int 是 0.选择更新;1,强制更新
	private String url; // String 是 更新地址
	private String uplog; // string 否 更新日志说明

	public int getVersion()
	{
		return version;
	}

	public void setVersion(int version)
	{
		this.version = version;
	}

	public int getUpdate()
	{
		return update;
	}

	public void setUpdate(int update)
	{
		this.update = update;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getUplog()
	{
		return uplog;
	}

	public void setUplog(String uplog)
	{
		this.uplog = uplog;
	}
	
	@Override
	public String toString()
	{
		return "version ："+version+"，update ："+update+"，url ："+url+"，uplog ："+uplog;
	}
}
