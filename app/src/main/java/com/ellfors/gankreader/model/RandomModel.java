package com.ellfors.gankreader.model;

public class RandomModel
{
    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public String get_id()
    {
        return _id == null ? "" : _id;
    }

    public void set_id(String _id)
    {
        this._id = _id;
    }

    public String getCreatedAt()
    {
        return createdAt == null ? "" : createdAt;
    }

    public void setCreatedAt(String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getDesc()
    {
        return desc == null ? "" : desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getPublishedAt()
    {
        return publishedAt == null ? "" : publishedAt;
    }

    public void setPublishedAt(String publishedAt)
    {
        this.publishedAt = publishedAt;
    }

    public String getType()
    {
        return type == null ? "" : type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getUrl()
    {
        return url == null ? "" : url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public boolean isUsed()
    {
        return used;
    }

    public void setUsed(boolean used)
    {
        this.used = used;
    }

    public String getWho()
    {
        return who == null ? "" : who;
    }

    public void setWho(String who)
    {
        this.who = who;
    }
}
