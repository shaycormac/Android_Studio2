package com.itheima.binghua.smshelper.entities;

/**
 * @author Administrator(Shay-Patrick-Cormac)
 * @datetime 2015-11-30 22:55 GMT+8
 * @email 574583006@qq.com
 */
public class ContactPerson {
    private String name;
    private String email;
    private String tele;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContactPerson(String name, String tele, String email) {
        super();
        this.name = name;
        this.tele = tele;
        this.email = email;
    }

    @Override
    public String toString() {
        return "ContactPerson{" +
                "name='" + name + '\'' +
                ", tele='" + tele + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public ContactPerson ()
    {
        
    }
}
