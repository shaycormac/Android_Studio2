package com.itheima.binghua.smsconback.entities;

/**
 * @author Administrator(Shay-Patrick-Cormac)
 * @datetime 2015-11-19 21:09 GMT+8
 * @email 574583006@qq.com
 */
public class SmsInfomation 
{
    @Override
    public String toString() {
        return "SmsInfomation{" +
                "_id=" + _id +
                ", address='" + address + '\'' +
                ", date=" + date +
                ", type=" + type +
                ", body='" + body + '\'' +
                '}';
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private int _id;
    private String address;
    private long date;
    private int type;
    private String body;

    public SmsInfomation(int _id, String body, int type, long date, String address) {
        this._id = _id;
        this.body = body;
        this.type = type;
        this.date = date;
        this.address = address;
    }

    public SmsInfomation()
    {
        
    }
}
