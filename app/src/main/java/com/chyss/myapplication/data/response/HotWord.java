package com.chyss.myapplication.data.response;

public class HotWord
{
	private int type;
	private String hotName;
	private String imgUrl;
	
	public String getHotName()
	{
		return hotName;
	}
	
	public void setHotName(String hotName)
	{
		this.hotName = hotName;
	}
	
	public String getImgUrl()
	{
		return imgUrl;
	}
	
	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}
}
