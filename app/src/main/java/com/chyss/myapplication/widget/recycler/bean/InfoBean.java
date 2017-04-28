package com.chyss.myapplication.widget.recycler.bean;

import java.util.List;

/**
 * Created by baiyuliang on 2016-5-27.
 */
public class InfoBean extends Object
{
    private String contents;
    private String title;
    private String imgUrl;
    private String upDateTime;
    private List<Object> imgList;
    private List<Object> zans;
    private List<Comments> comments;

    public List<Comments> getComments()
    {
        return comments;
    }

    public void setComments(List<Comments> comments)
    {
        this.comments = comments;
    }

    public List<Object> getZans()
    {
        return zans;
    }

    public void setZans(List<Object> zans)
    {
        this.zans = zans;
    }

    public String getContents()
    {
        return contents;
    }

    public void setContents(String contents)
    {
        this.contents = contents;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getImgUrl()
    {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public String getUpDateTime()
    {
        return upDateTime;
    }

    public void setUpDateTime(String upDateTime)
    {
        this.upDateTime = upDateTime;
    }

    public List<Object> getImgList() {
        return imgList;
    }

    public void setImgList(List<Object> imgList) {
        this.imgList = imgList;
    }
}
