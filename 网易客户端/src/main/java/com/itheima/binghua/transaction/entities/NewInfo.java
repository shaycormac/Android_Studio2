package com.itheima.binghua.transaction.entities;

/**
 * @author Administrator(Shay-Patrick-Cormac)
 * @datetime 2015-11-24 22:05 GMT+8
 * @email 574583006@qq.com
 */
public class NewInfo
{
    private String title;
    private String detail;

    public NewInfo() 
    {
        super();
    }

    public NewInfo(String title, String detail, int comment, String imageurl) {
      super();
        this.title = title;
        this.detail = detail;
        this.comment = comment;
        this.imageurl = imageurl;
    }

    private int comment;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    private String imageurl;
    
}
