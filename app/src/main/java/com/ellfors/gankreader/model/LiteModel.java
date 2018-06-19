package com.ellfors.gankreader.model;


import org.litepal.crud.DataSupport;

public class LiteModel extends DataSupport
{
    private String server_id;
    private String title;
    private String time;
    private String url;
    private String author;

    public String getServer_id()
    {
        return server_id == null ? "" : server_id;
    }

    public void setServer_id(String server_id)
    {
        this.server_id = server_id;
    }

    public String getTitle()
    {
        return title == null ? "" : title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTime()
    {
        return time == null ? "" : time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getUrl()
    {
        return url == null ? "" : url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getAuthor()
    {
        return author == null ? "" : author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }
}
