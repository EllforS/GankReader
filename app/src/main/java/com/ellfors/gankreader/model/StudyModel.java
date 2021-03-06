package com.ellfors.gankreader.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudyModel implements Serializable
{
    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

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

    public String getSource()
    {
        return source == null ? "" : source;
    }

    public void setSource(String source)
    {
        this.source = source;
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

    public List<String> getImages()
    {
        if (images == null)
        {
            return new ArrayList<>();
        }
        return images;
    }

    public void setImages(List<String> images)
    {
        this.images = images;
    }
}
