package com.itheima.binghua.contentprovide.entities;

/**
 * @author Administrator(Shay-Patrick-Cormac)
 * @datetime 2015-11-14 17:21 GMT+8
 * @email 574583006@qq.com
 */
public class Student
{
    public Student() {
    }

    public Student(int id ,String name, char sex, double score) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.score = score;
    }

   

  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String name;
    private char sex;
    private double score;

    @Override
    public String toString() {
        return "id号--" +id+ "姓名：-" + name + "性别：-" + sex + "得分：-" + score;
    }
}
